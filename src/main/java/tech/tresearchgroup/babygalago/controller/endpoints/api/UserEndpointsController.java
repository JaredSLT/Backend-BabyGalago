package tech.tresearchgroup.babygalago.controller.endpoints.api;

import io.activej.http.HttpRequest;
import io.activej.http.HttpResponse;
import io.activej.promise.Promisable;
import lombok.AllArgsConstructor;
import tech.tresearchgroup.babygalago.controller.SettingsController;
import tech.tresearchgroup.babygalago.controller.controllers.UserEntityController;
import tech.tresearchgroup.palila.controller.BasicController;
import tech.tresearchgroup.schemas.galago.entities.ExtendedUserEntity;

import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.Objects;

@AllArgsConstructor
public class UserEndpointsController extends BasicController {
    private final UserEntityController userEntityController;
    private final SettingsController settingsController;

    public Promisable<HttpResponse> getUsers(HttpRequest httpRequest) {
        int page = httpRequest.getQueryParameter("page") != null ? Integer.parseInt(Objects.requireNonNull(httpRequest.getQueryParameter("page"))) : 0;
        int pageSize = httpRequest.getQueryParameter("pageSize") != null ? Integer.parseInt(Objects.requireNonNull(httpRequest.getQueryParameter("pageSize"))) : 24;
        return okResponseCompressed(userEntityController.readPaginatedAPIResponse(page, pageSize, true, httpRequest));
    }

    public Promisable<HttpResponse> postUser(HttpRequest httpRequest) {
        if (settingsController.isAllowRegistration()) {
            String data = httpRequest.loadBody().getResult().asString(Charset.defaultCharset());
            return okResponseCompressed(userEntityController.createSecureAPIResponse(data, httpRequest));
        }
        return unavailable();
    }

    public Promisable<HttpResponse> putUser(HttpRequest httpRequest) throws SQLException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        if (settingsController.isAllowRegistration()) {
            String data = httpRequest.loadBody().getResult().asString(Charset.defaultCharset());
            return ok(userEntityController.createSecureResponse(data, httpRequest) != null);
        }
        return unavailable();
    }

    public Promisable<HttpResponse> getUserById(HttpRequest httpRequest) {
        int id = Integer.parseInt(Objects.requireNonNull(httpRequest.getPathParameter("userId")));
        return okResponseCompressed(userEntityController.readSecureAPIResponse(id, httpRequest));
    }

    public Promisable<HttpResponse> patchUser(HttpRequest httpRequest) throws Exception {
        String data = httpRequest.loadBody().getResult().asString(Charset.defaultCharset());
        long id = Long.parseLong(httpRequest.getPathParameter("userId"));
        return ok(userEntityController.update(id, data, httpRequest));
    }

    public Promisable<HttpResponse> deleteUserById(HttpRequest httpRequest) throws Exception {
        int id = Integer.parseInt(httpRequest.getPathParameter("userId"));
        return ok(userEntityController.delete(id, httpRequest));
    }

    public HttpResponse postUserById(HttpRequest httpRequest) throws SQLException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        ExtendedUserEntity extendedUserEntity = null;
        //Todo get the entity from the form
        return userEntityController.createUIResponse(extendedUserEntity, httpRequest);
    }
}
