package tech.tresearchgroup.babygalago.controller.endpoints.api;

import io.activej.http.HttpHeaderValue;
import io.activej.http.HttpHeaders;
import io.activej.http.HttpRequest;
import io.activej.http.HttpResponse;
import io.activej.promise.Promisable;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import tech.tresearchgroup.babygalago.controller.controllers.UserSettingsEntityController;
import tech.tresearchgroup.palila.controller.BasicController;
import tech.tresearchgroup.palila.controller.BasicUserController;
import tech.tresearchgroup.palila.model.enums.ReturnType;

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

    /**
     * Loads and returns the Settings.json file
     * @param httpRequest the request
     * @return the page response
     * @throws IOException if its fails
     */
    public Promisable<HttpResponse> getSettings(HttpRequest httpRequest) throws IOException {
        return ok(Files.readAllBytes(Path.of("Settings.json")));
    }

    /**
     * Patches Settings.json
     * @param httpRequest the request
     * @return the page response
     */
    public Promisable<HttpResponse> patchSettings(HttpRequest httpRequest) {
        String jwt = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        //Todo implement
        return HttpResponse.ok200();
    }

    /**
     * Gets the users settings
     * @param httpRequest the request
     * @return the page response
     * @throws SQLException if its fails
     * @throws IOException if its fails
     * @throws InvocationTargetException if its fails
     * @throws NoSuchMethodException if its fails
     * @throws InstantiationException if its fails
     * @throws IllegalAccessException if its fails
     */
    public Promisable<HttpResponse> getUserSettings(HttpRequest httpRequest) throws SQLException, IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        long userId = userController.getUserId(httpRequest);
        return okResponseCompressed((byte[]) userSettingsEntityController.readSecureResponse(userId, ReturnType.JSON, httpRequest));
    }

    /**
     * Creates a user settings entity
     * @param httpRequest the request
     * @return the page response
     * @throws Exception if its fails
     */
    public Promisable<HttpResponse> createUserSettings(HttpRequest httpRequest) throws Exception {
        String data = httpRequest.loadBody().getResult().asString(Charset.defaultCharset());
        return okResponseCompressed((byte[]) userSettingsEntityController.createSecureResponse(data, ReturnType.JSON, httpRequest));
    }

    /**
     * Patches user settings entity
     * @param httpRequest the request
     * @return the page response
     * @throws Exception if its fails
     */
    public Promisable<HttpResponse> patchUserSettings(HttpRequest httpRequest) throws Exception {
        String data = httpRequest.loadBody().getResult().asString(Charset.defaultCharset());
        long userId = getUserId(httpRequest);
        return ok(userSettingsEntityController.update(userId, data, httpRequest));
    }

    /**
     * Deletes a users settings
     * @param httpRequest the request
     * @return the page response
     * @throws Exception if its fails
     */
    public Promisable<HttpResponse> deleteUserSettings(HttpRequest httpRequest) throws Exception {
        int settingsId = httpRequest.getQueryParameter("settingsId") != null ? Integer.parseInt(Objects.requireNonNull(httpRequest.getQueryParameter("settingsId"))) : 0;
        return ok(userSettingsEntityController.delete(settingsId, httpRequest));
    }

    /**
     * Gets which methods are available for this endpoint
     * @param httpRequest the request
     * @return the response page
     */
    @NotNull
    public Promisable<HttpResponse> optionsSettings(@NotNull HttpRequest httpRequest) {
        return HttpResponse.ok200().withHeader(HttpHeaders.ALLOW, HttpHeaderValue.of("GET, PATCH"));
    }

    /**
     * Gets which methods are available for this endpoint
     * @param httpRequest the request
     * @return the response page
     */
    @NotNull
    public Promisable<HttpResponse> optionsSettingsById(@NotNull HttpRequest httpRequest) {
        return HttpResponse.ok200().withHeader(HttpHeaders.ALLOW, HttpHeaderValue.of("GET, POST, PATCH, DELETE"));
    }
}
