package tech.tresearchgroup.babygalago.view.endpoints.api;

import com.google.gson.Gson;
import io.activej.http.HttpMethod;
import io.activej.http.HttpRequest;
import io.activej.http.HttpResponse;
import io.activej.http.RoutingServlet;
import io.activej.inject.annotation.Provides;
import io.activej.promise.Promisable;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import tech.tresearchgroup.babygalago.controller.SettingsController;
import tech.tresearchgroup.babygalago.controller.endpoints.LoginEndpointsController;
import tech.tresearchgroup.palila.controller.HttpResponses;
import tech.tresearchgroup.schemas.galago.entities.ExtendedUserEntity;

import java.nio.charset.Charset;

@AllArgsConstructor
public class LoginEndpoints extends HttpResponses {
    private final LoginEndpointsController loginEndpointsController;
    private final SettingsController settingsController;
    private final Gson gson;

    @Provides
    public RoutingServlet servlet() {
        return RoutingServlet.create()
            .map(HttpMethod.POST, "/v1/login", this::apiLogin);
    }

    private @NotNull Promisable<HttpResponse> apiLogin(@NotNull HttpRequest httpRequest) {
        try {
            String data = httpRequest.loadBody().getResult().asString(Charset.defaultCharset());
            ExtendedUserEntity jsonUser = gson.fromJson(data, ExtendedUserEntity.class);
            ExtendedUserEntity userEntity = loginEndpointsController.getUser(jsonUser.getUsername(), jsonUser.getPassword(), httpRequest);
            if (userEntity != null) {
                return HttpResponse.ok200().withBody(loginEndpointsController.login(userEntity));
            }
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
        }
        return error();
    }
}
