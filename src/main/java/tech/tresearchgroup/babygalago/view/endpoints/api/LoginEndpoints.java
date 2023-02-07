package tech.tresearchgroup.babygalago.view.endpoints.api;

import io.activej.http.HttpMethod;
import io.activej.http.RoutingServlet;
import io.activej.inject.annotation.Provides;
import io.activej.inject.module.AbstractModule;
import tech.tresearchgroup.babygalago.controller.endpoints.LoginEndpointsController;
import tech.tresearchgroup.palila.controller.EndpointsRouter;
import tech.tresearchgroup.palila.controller.RoutingServletBuilder;
import tech.tresearchgroup.palila.model.endpoints.Endpoint;

public class LoginEndpoints extends AbstractModule implements EndpointsRouter {
    private LoginEndpointsController loginEndpointsController;

    public LoginEndpoints() {
    }

    public LoginEndpoints(LoginEndpointsController loginEndpointsController) {
        this.loginEndpointsController = loginEndpointsController;
    }

    /**
     * Creates the endpoints and maps them to their respective methods
     *
     * @return the routing servlet
     */
    @Provides
    public RoutingServlet servlet() {
        return RoutingServletBuilder.build(getEndpoints());
    }

    @Override
    public Endpoint[] getEndpoints() {
        return new Endpoint[]{
            new Endpoint(HttpMethod.POST, "/v1/login", loginEndpointsController::apiLogin)
        };
    }
}
