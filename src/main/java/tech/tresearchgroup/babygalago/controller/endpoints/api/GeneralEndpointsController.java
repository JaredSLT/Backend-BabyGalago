package tech.tresearchgroup.babygalago.controller.endpoints.api;

import com.google.gson.Gson;
import io.activej.csp.file.ChannelFileWriter;
import io.activej.http.HttpRequest;
import io.activej.http.HttpResponse;
import io.activej.http.MultipartDecoder;
import io.activej.inject.annotation.Provides;
import io.activej.promise.Promisable;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import tech.tresearchgroup.babygalago.Main;
import tech.tresearchgroup.babygalago.controller.SettingsController;
import tech.tresearchgroup.babygalago.controller.controllers.UserEntityController;
import tech.tresearchgroup.palila.controller.BasicController;
import tech.tresearchgroup.palila.controller.GenericController;
import tech.tresearchgroup.palila.controller.ReflectionMethods;
import tech.tresearchgroup.palila.model.enums.PermissionGroupEnum;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executor;

import static java.util.concurrent.Executors.newSingleThreadExecutor;

@AllArgsConstructor
public class GeneralEndpointsController extends BasicController {
    private final UserEntityController userEntityController;
    private final SettingsController settingsController;
    private final Map<String, GenericController> controllers;
    private final Gson gson;

    @Provides
    static Executor executor() {
        return newSingleThreadExecutor();
    }

    public HttpResponse getLatest(HttpRequest httpRequest) throws IOException {
        //Todo return the latest version from Mama Galago
        return ok(Main.VERSION.getBytes());
    }

    public HttpResponse putUpdate(HttpRequest httpRequest) {
        //Todo attempt to update from Mama Galago
        return notImplemented();
    }

    public @NotNull Promisable<HttpResponse> postUpload(HttpRequest httpRequest) throws SQLException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        if (settingsController.isEnableUpload()) {
            if (canAccess(httpRequest, PermissionGroupEnum.USER, userEntityController)) {
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

    public HttpResponse getSearch(String mediaType, String query, HttpRequest httpRequest) throws Exception {
        if (canAccess(httpRequest, PermissionGroupEnum.USER, userEntityController)) {
            Class theClass = ReflectionMethods.findClass(mediaType, settingsController.getEntityPackages());
            GenericController genericController = getController(theClass, controllers);
            return ok(gson.toJson(genericController.search(query, "*", httpRequest)).getBytes());
        }
        return unauthorized();
    }

    public HttpResponse getVersion(HttpRequest httpRequest) throws IOException {
        return ok(Main.VERSION.getBytes());
    }
}
