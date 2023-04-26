package tech.tresearchgroup.babygalago.controller.endpoints.api;

import io.activej.csp.file.ChannelFileWriter;
import io.activej.http.*;
import io.activej.inject.annotation.Provides;
import io.activej.promise.Promisable;
import org.jetbrains.annotations.NotNull;
import tech.tresearchgroup.babygalago.Main;
import tech.tresearchgroup.babygalago.controller.SettingsController;
import tech.tresearchgroup.babygalago.controller.controllers.ExtendedUserEntityController;
import tech.tresearchgroup.palila.controller.BasicController;
import tech.tresearchgroup.palila.model.enums.PermissionGroupEnum;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.Executor;

import static java.util.concurrent.Executors.newSingleThreadExecutor;

public class GeneralEndpointsController extends BasicController {
    private final ExtendedUserEntityController extendedUserEntityController;
    private final SettingsController settingsController;

    public GeneralEndpointsController(ExtendedUserEntityController extendedUserEntityController,
                                      SettingsController settingsController) {
        this.extendedUserEntityController = extendedUserEntityController;
        this.settingsController = settingsController;
    }

    /**
     * Provides an executor for file uploads
     *
     * @return the executor
     */
    @Provides
    static Executor executor() {
        return newSingleThreadExecutor();
    }

    /**
     * Gets the latest version
     *
     * @param httpRequest the request
     * @return the response page
     * @throws IOException if it fails to check
     */
    public Promisable<HttpResponse> getLatest(HttpRequest httpRequest) throws IOException {
        //Todo return the latest version from Mama Galago
        return ok(Main.VERSION.getBytes());
    }

    /**
     * Requests for the server to update to the newest version
     *
     * @param httpRequest the request
     * @return the response page
     */
    public Promisable<HttpResponse> putUpdate(HttpRequest httpRequest) {
        //Todo attempt to update from Mama Galago
        return notImplemented();
    }

    /**
     * Uploads a file
     *
     * @param httpRequest the request
     * @return the response page
     * @throws SQLException              if it fails to create the sql query
     * @throws InvocationTargetException if it fails to parse
     * @throws NoSuchMethodException     if the orm fails
     * @throws IllegalAccessException    if the orm fails
     * @throws InstantiationException    if the orm fails
     * @throws IOException               if the orm fails
     */
    public @NotNull Promisable<HttpResponse> postUpload(HttpRequest httpRequest) throws SQLException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException, IOException {
        if (settingsController.isEnableUpload()) {
            if (canAccess(httpRequest, PermissionGroupEnum.USER, extendedUserEntityController)) {
                UUID uuid = UUID.randomUUID();
                Path file = new File("temp/" + uuid + ".tmp").toPath();
                return httpRequest.handleMultipart(MultipartDecoder.MultipartDataHandler.file(fileName ->
                        ChannelFileWriter.open(executor(), file)))
                    .map($ -> ok(String.valueOf(file.getFileName()).getBytes()));
            }
            return unauthorized();
        }
        return unavailable();
    }

    /**
     * Gets the operating version
     *
     * @param httpRequest the request
     * @return the response page
     * @throws IOException if it fails
     */
    public Promisable<HttpResponse> getVersion(HttpRequest httpRequest) throws IOException {
        return ok(Main.VERSION.getBytes());
    }

    /**
     * Gets which methods are available for this endpoint
     *
     * @param httpRequest the request
     * @return the response page
     */
    @NotNull
    public Promisable<HttpResponse> optionsVersion(@NotNull HttpRequest httpRequest) {
        try {
            return HttpResponse.ok200().withHeader(HttpHeaders.ALLOW, HttpHeaderValue.of("GET"));
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
            return error();
        }
    }

    /**
     * Gets which methods are available for this endpoint
     *
     * @param httpRequest the request
     * @return the response page
     */
    @NotNull
    public Promisable<HttpResponse> optionsVersionLatest(@NotNull HttpRequest httpRequest) {
        try {
            return HttpResponse.ok200().withHeader(HttpHeaders.ALLOW, HttpHeaderValue.of("GET"));
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
            return error();
        }
    }

    /**
     * Gets which methods are available for this endpoint
     *
     * @param httpRequest the request
     * @return the response page
     */
    public Promisable<HttpResponse> optionsUpdate(@NotNull HttpRequest httpRequest) {
        try {
            return HttpResponse.ok200().withHeader(HttpHeaders.ALLOW, HttpHeaderValue.of("PUT"));
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
            return error();
        }
    }

    /**
     * Gets which methods are available for this endpoint
     *
     * @param httpRequest the request
     * @return the response page
     */
    @NotNull
    public Promisable<HttpResponse> optionsUpload(@NotNull HttpRequest httpRequest) {
        try {
            return HttpResponse.ok200().withHeader(HttpHeaders.ALLOW, HttpHeaderValue.of("POST"));
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
            return error();
        }
    }

    /**
     * Gets which methods are available for this endpoint
     *
     * @param httpRequest the request
     * @return the response page
     */
    @NotNull
    public Promisable<HttpResponse> optionsSearch(@NotNull HttpRequest httpRequest) {
        try {
            return HttpResponse.ok200().withHeader(HttpHeaders.ALLOW, HttpHeaderValue.of("GET"));
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
            return error();
        }
    }
}
