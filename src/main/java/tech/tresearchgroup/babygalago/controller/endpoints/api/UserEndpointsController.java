package tech.tresearchgroup.babygalago.controller.endpoints.api;

import io.activej.http.HttpHeaderValue;
import io.activej.http.HttpHeaders;
import io.activej.http.HttpRequest;
import io.activej.http.HttpResponse;
import io.activej.promise.Promisable;
import org.jetbrains.annotations.NotNull;
import tech.tresearchgroup.babygalago.controller.SettingsController;
import tech.tresearchgroup.babygalago.controller.controllers.ExtendedUserEntityController;
import tech.tresearchgroup.palila.controller.BasicController;
import tech.tresearchgroup.palila.model.enums.PermissionGroupEnum;
import tech.tresearchgroup.palila.model.enums.ReturnType;
import tech.tresearchgroup.schemas.galago.entities.ExtendedUserEntity;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.Objects;

public class UserEndpointsController extends BasicController {
    private final ExtendedUserEntityController extendedUserEntityController;
    private final SettingsController settingsController;

    public UserEndpointsController(ExtendedUserEntityController extendedUserEntityController,
                                   SettingsController settingsController) {
        this.extendedUserEntityController = extendedUserEntityController;
        this.settingsController = settingsController;
    }

    /**
     * Gets a list of extended user entities
     *
     * @param httpRequest the request
     * @return the page response
     * @throws SQLException              if its fails
     * @throws IOException               if its fails
     * @throws InvocationTargetException if its fails
     * @throws InstantiationException    if its fails
     * @throws IllegalAccessException    if its fails
     */
    public Promisable<HttpResponse> getUsers(HttpRequest httpRequest) throws SQLException, IOException, InvocationTargetException, InstantiationException, IllegalAccessException {
        ExtendedUserEntity extendedUserEntity = (ExtendedUserEntity) getUser(httpRequest, extendedUserEntityController);
        if (extendedUserEntity.getPermissionGroup().equals(PermissionGroupEnum.ADMINISTRATOR)) {
            int page = httpRequest.getQueryParameter("page") != null ? Integer.parseInt(Objects.requireNonNull(httpRequest.getQueryParameter("page"))) : 0;
            int pageSize = httpRequest.getQueryParameter("pageSize") != null ? Integer.parseInt(Objects.requireNonNull(httpRequest.getQueryParameter("pageSize"))) : 24;
            return okResponseCompressed((byte[]) extendedUserEntityController.readPaginatedResponse(page, pageSize, true, ReturnType.JSON, httpRequest));
        } else {
            return unauthorized();
        }
    }

    /**
     * Creates an extended user entity
     *
     * @param httpRequest the request
     * @return the page response
     * @throws SQLException              if its fails
     * @throws IOException               if its fails
     * @throws InvocationTargetException if its fails
     * @throws NoSuchMethodException     if its fails
     * @throws IllegalAccessException    if its fails
     * @throws InstantiationException    if its fails
     */
    public Promisable<HttpResponse> postUser(HttpRequest httpRequest) throws SQLException, IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        if (settingsController.isAllowRegistration()) {
            String data = httpRequest.loadBody().getResult().asString(Charset.defaultCharset());
            return okResponseCompressed((byte[]) extendedUserEntityController.createSecureResponse(data, ReturnType.JSON, httpRequest));
        }
        return unavailable();
    }

    /**
     * Creates an extended user entity
     *
     * @param httpRequest the request
     * @return the page response
     * @throws SQLException              if its fails
     * @throws InvocationTargetException if its fails
     * @throws NoSuchMethodException     if its fails
     * @throws IllegalAccessException    if its fails
     * @throws InstantiationException    if its fails
     * @throws IOException               if its fails
     */
    public Promisable<HttpResponse> putUser(HttpRequest httpRequest) throws SQLException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException, IOException {
        if (settingsController.isAllowRegistration()) {
            String data = httpRequest.loadBody().getResult().asString(Charset.defaultCharset());
            return ok(extendedUserEntityController.createSecureResponse(data, ReturnType.JSON, httpRequest) != null);
        }
        return unavailable();
    }

    /**
     * Gets an extended user entity by its id
     *
     * @param httpRequest the request
     * @return the page response
     * @throws SQLException              if its fails
     * @throws IOException               if its fails
     * @throws InvocationTargetException if its fails
     * @throws InstantiationException    if its fails
     * @throws IllegalAccessException    if its fails
     */
    public Promisable<HttpResponse> getUserById(HttpRequest httpRequest) throws SQLException, IOException, InvocationTargetException, InstantiationException, IllegalAccessException {
        ExtendedUserEntity extendedUserEntity = (ExtendedUserEntity) getUser(httpRequest, extendedUserEntityController);
        int id = Integer.parseInt(Objects.requireNonNull(httpRequest.getPathParameter("userId")));
        if (extendedUserEntity.getId() == id || extendedUserEntity.getPermissionGroup().equals(PermissionGroupEnum.ADMINISTRATOR)) {
            return okResponseCompressed((byte[]) extendedUserEntityController.readSecureResponse(id, ReturnType.JSON, httpRequest));
        } else {
            return unauthorized();
        }
    }

    /**
     * Patches an extended user entity
     *
     * @param httpRequest the request
     * @return the page response
     * @throws Exception if its fails
     */
    public Promisable<HttpResponse> patchUser(HttpRequest httpRequest) throws Exception {
        String data = httpRequest.loadBody().getResult().asString(Charset.defaultCharset());
        long id = Long.parseLong(httpRequest.getPathParameter("userId"));
        ExtendedUserEntity extendedUserEntity = (ExtendedUserEntity) getUser(httpRequest, extendedUserEntityController);
        if (extendedUserEntity.getId() == id || extendedUserEntity.getPermissionGroup().equals(PermissionGroupEnum.ADMINISTRATOR)) {
            return ok(extendedUserEntityController.update(id, data, httpRequest));
        } else {
            return unauthorized();
        }
    }

    /**
     * Deletes a user entity by its id
     *
     * @param httpRequest the request
     * @return the page response
     * @throws Exception if its fails
     */
    public Promisable<HttpResponse> deleteUserById(HttpRequest httpRequest) throws Exception {
        int id = Integer.parseInt(httpRequest.getPathParameter("userId"));
        ExtendedUserEntity extendedUserEntity = (ExtendedUserEntity) getUser(httpRequest, extendedUserEntityController);
        if (extendedUserEntity.getId() == id || extendedUserEntity.getPermissionGroup().equals(PermissionGroupEnum.ADMINISTRATOR)) {
            return ok(extendedUserEntityController.delete(id, httpRequest));
        } else {
            return unauthorized();
        }
    }

    /**
     * Creates a user entity
     *
     * @param httpRequest the request
     * @return the page response
     * @throws SQLException              if its fails
     * @throws InvocationTargetException if its fails
     * @throws NoSuchMethodException     if its fails
     * @throws IllegalAccessException    if its fails
     * @throws InstantiationException    if its fails
     * @throws IOException               if its fails
     */
    public Promisable<HttpResponse> postUserById(HttpRequest httpRequest) throws SQLException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException, IOException {
        ExtendedUserEntity extendedUserEntity = (ExtendedUserEntity) getUser(httpRequest, extendedUserEntityController);
        int id = Integer.parseInt(httpRequest.getPathParameter("userId"));
        if (extendedUserEntity.getId() == id || extendedUserEntity.getPermissionGroup().equals(PermissionGroupEnum.ADMINISTRATOR)) {
            ExtendedUserEntity formUser = null;
            //Todo get the entity from the form
            return extendedUserEntityController.createUIResponse(formUser, httpRequest);
        } else {
            return null;
        }
    }

    /**
     * Returns a sample extended user entity
     *
     * @param httpRequest the request
     * @return the page response
     */
    public Promisable<HttpResponse> getSample(HttpRequest httpRequest) {
        return okResponseCompressed(extendedUserEntityController.getSample(httpRequest));
    }

    /**
     * Gets which methods are available for this endpoint
     *
     * @param httpRequest the request
     * @return the response page
     */
    @NotNull
    public Promisable<HttpResponse> optionsUsers(@NotNull HttpRequest httpRequest) {
        return HttpResponse.ok200().withHeader(HttpHeaders.ALLOW, HttpHeaderValue.of("GET, POST, PUT, PATCH"));
    }

    /**
     * Gets which methods are available for this endpoint
     *
     * @param httpRequest the request
     * @return the response page
     */
    @NotNull
    public Promisable<HttpResponse> optionsUserById(@NotNull HttpRequest httpRequest) {
        return HttpResponse.ok200().withHeader(HttpHeaders.ALLOW, HttpHeaderValue.of("GET, DELETE, POST"));
    }
}
