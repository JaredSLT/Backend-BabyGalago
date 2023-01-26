package tech.tresearchgroup.babygalago.view.endpoints.api;

import io.activej.http.HttpMethod;
import io.activej.http.RoutingServlet;
import io.activej.inject.annotation.Provides;
import io.activej.inject.module.AbstractModule;
import lombok.AllArgsConstructor;
import tech.tresearchgroup.babygalago.controller.endpoints.LoginEndpointsController;

@AllArgsConstructor
public class LoginEndpoints extends AbstractModule {
    private final LoginEndpointsController loginEndpointsController;

    /**
     * Creates the endpoints and maps them to their respective methods
     *
     * @return the routing servlet
     */
    @Provides
    public RoutingServlet servlet() {
        return RoutingServlet.create()
            .map(HttpMethod.POST, "/v1/login", loginEndpointsController::apiLogin);
    }
}
