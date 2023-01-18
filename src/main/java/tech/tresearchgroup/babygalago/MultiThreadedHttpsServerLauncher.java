package tech.tresearchgroup.babygalago;

import io.activej.config.Config;
import io.activej.config.ConfigModule;
import io.activej.eventloop.Eventloop;
import io.activej.eventloop.inspector.ThrottlingController;
import io.activej.http.AsyncHttpServer;
import io.activej.http.AsyncServlet;
import io.activej.inject.annotation.Inject;
import io.activej.inject.annotation.Provides;
import io.activej.inject.binding.OptionalDependency;
import io.activej.inject.module.Module;
import io.activej.launcher.Launcher;
import io.activej.net.PrimaryServer;
import io.activej.service.ServiceGraphModule;
import io.activej.worker.WorkerPool;
import io.activej.worker.WorkerPoolModule;
import io.activej.worker.WorkerPools;
import io.activej.worker.annotation.Worker;

import javax.net.ssl.KeyManager;
import javax.net.ssl.TrustManager;
import java.io.File;
import java.io.IOException;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.security.SecureRandom;
import java.util.stream.Stream;

import static io.activej.config.Config.ofClassPathProperties;
import static io.activej.config.Config.ofSystemProperties;
import static io.activej.config.converter.ConfigConverters.ofInetSocketAddress;
import static io.activej.config.converter.ConfigConverters.ofInteger;
import static io.activej.inject.module.Modules.combine;
import static io.activej.launchers.initializers.Initializers.*;
import static java.util.concurrent.Executors.newCachedThreadPool;
import static java.util.stream.Collectors.joining;
import static tech.tresearchgroup.babygalago.SslUtils.*;

public class MultiThreadedHttpsServerLauncher extends Launcher {
    public static final int WORKERS = 16;
    public static final String PROPERTIES_FILE = "http-server.properties";
    public static int port = 60842;
    public static KeyManager[] keyManagers;

    public static boolean https = true;
    public static TrustManager[] trustManagers;

    static {
        try {
            File file = new File("./keystore.jks");
            String store = System.getenv("STORE");
            String key = System.getenv("KEY");
            if (store != null && System.getenv("KEY") != null && file.exists()) {
                keyManagers = createKeyManagers(file, store, key);
            } else {
                https = false;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static {
        try {
            File file = new File("./keystore.jks");
            String store = System.getenv("STORE");
            if (store != null && file.exists()) {
                trustManagers = createTrustManagers(file, store);
            } else {
                https = false;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Inject
    PrimaryServer primaryServer;

    public static synchronized int getFreePort() {
        while (++port < 65536) {
            if (!probeBindAddress(new InetSocketAddress(port))) continue;
            if (!probeBindAddress(new InetSocketAddress("localhost", port))) continue;
            if (!probeBindAddress(new InetSocketAddress("127.0.0.1", port))) continue;
            return port;
        }
        throw new AssertionError();
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private static boolean probeBindAddress(InetSocketAddress inetSocketAddress) {
        try (ServerSocket s = new ServerSocket()) {
            s.bind(inetSocketAddress);
        } catch (BindException e) {
            return false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Provides
    Eventloop primaryEventloop(Config config) {
        return Eventloop.create()
            .withInitializer(ofEventloop(config.getChild("eventloop.primary")));
    }

    @Provides
    @Worker
    Eventloop workerEventloop(Config config, OptionalDependency<ThrottlingController> throttlingController) {
        return Eventloop.create()
            .withInitializer(ofEventloop(config.getChild("eventloop.worker")))
            .withInitializer(eventloop -> eventloop.withInspector(throttlingController.orElse(null)));
    }

    @Provides
    WorkerPool workerPool(WorkerPools workerPools, Config config) {
        return workerPools.createPool(config.get(ofInteger(), "workers", WORKERS));
    }

    @Provides
    PrimaryServer primaryServer(Eventloop primaryEventloop, WorkerPool.Instances<AsyncHttpServer> workerServers, Config config) throws Exception {
        if (https) {
            return PrimaryServer.create(primaryEventloop, workerServers.getList())
                .withSslListenPort(createSslContext("TLSv1", keyManagers, trustManagers, new SecureRandom()), newCachedThreadPool(), 443)
                .withInitializer(ofPrimaryServer(config.getChild("http")));
        } else {
            return PrimaryServer.create(primaryEventloop, workerServers.getList())
                .withListenPort(80);
        }
    }

    @Provides
    @Worker
    AsyncHttpServer workerServer(Eventloop eventloop, AsyncServlet servlet, Config config) throws Exception {
        if (https) {
            return AsyncHttpServer.create(eventloop, servlet)
                .withSslListenPort(createSslContext("TLSv1", keyManagers, trustManagers, new SecureRandom()), newCachedThreadPool(), getFreePort())
                .withInitializer(ofHttpWorker(config.getChild("http")));
        } else {
            return AsyncHttpServer.create(eventloop, servlet).withListenPort(getFreePort());
        }
    }

    @Provides
    Config config() {
        return Config.create()
            .with("http.listenAddresses", Config.ofValue(ofInetSocketAddress(), new InetSocketAddress(port)))
            .with("workers", "" + WORKERS)
            .overrideWith(ofClassPathProperties(PROPERTIES_FILE, true))
            .overrideWith(ofSystemProperties("config"));
    }

    @Override
    protected final Module getModule() {
        return combine(
            ServiceGraphModule.create(),
            WorkerPoolModule.create(),
            ConfigModule.create()
                .withEffectiveConfigLogger(),
            getBusinessLogicModule()
        );
    }

    protected Module getBusinessLogicModule() {
        return Module.empty();
    }

    @Override
    protected void run() throws Exception {
        logger.info("HTTP Server is listening on {}", Stream.concat(
                primaryServer.getListenAddresses().stream().map(address -> "http://" + ("0.0.0.0".equals(address.getHostName()) ? "localhost" : address.getHostName()) + (address.getPort() != 80 ? ":" + address.getPort() : "") + "/"),
                primaryServer.getSslListenAddresses().stream().map(address -> "https://" + ("0.0.0.0".equals(address.getHostName()) ? "localhost" : address.getHostName()) + (address.getPort() != 80 ? ":" + address.getPort() : "") + "/"))
            .collect(joining(" ")));
        awaitShutdown();
    }
}
