package tech.tresearchgroup.babygalago.controller.endpoints.api;

import io.activej.http.HttpHeaders;
import io.activej.http.HttpRequest;
import io.activej.http.HttpResponse;
import io.activej.promise.Promisable;
import lombok.AllArgsConstructor;
import tech.tresearchgroup.babygalago.controller.controllers.UserSettingsEntityController;
import tech.tresearchgroup.palila.controller.BasicController;
import tech.tresearchgroup.palila.controller.BasicUserController;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.Objects;

@AllArgsConstructor
public class SettingsEndpointsController extends BasicController {
    private final BasicUserController userController;
    private final UserSettingsEntityController userSettingsEntityController;

    public HttpResponse getSettings(HttpRequest httpRequest) throws IOException {
        return ok(Files.readAllBytes(Path.of("Settings.json")));
    }

    public Promisable<HttpResponse> patchSettings(HttpRequest httpRequest) {
        String jwt = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        //Todo implement
        return HttpResponse.ok200();
    }

    public Promisable<HttpResponse> getUserSettings(HttpRequest httpRequest) throws SQLException, IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        long userId = userController.getUserId(httpRequest);
        return okResponseCompressed(userSettingsEntityController.readSecureAPIResponse(userId, httpRequest));
    }

    public Promisable<HttpResponse> createUserSettings(HttpRequest httpRequest) throws Exception {
        String data = httpRequest.loadBody().getResult().asString(Charset.defaultCharset());
        return okResponseCompressed(userSettingsEntityController.createSecureAPIResponse(data, httpRequest));
    }

    public Promisable<HttpResponse> patchUserSettings(HttpRequest httpRequest) throws Exception {
        String data = httpRequest.loadBody().getResult().asString(Charset.defaultCharset());
        long userId = getUserId(httpRequest);
        return ok(userSettingsEntityController.update(userId, data, httpRequest));
    }

    public Promisable<HttpResponse> deleteUserSettings(HttpRequest httpRequest) throws Exception {
        int settingsId = httpRequest.getQueryParameter("settingsId") != null ? Integer.parseInt(Objects.requireNonNull(httpRequest.getQueryParameter("settingsId"))) : 0;
        return ok(userSettingsEntityController.delete(settingsId, httpRequest));
    }
}
