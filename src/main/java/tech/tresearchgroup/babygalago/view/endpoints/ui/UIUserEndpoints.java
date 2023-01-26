package tech.tresearchgroup.babygalago.view.endpoints.ui;

import io.activej.http.HttpMethod;
import io.activej.http.RoutingServlet;
import io.activej.inject.annotation.Provides;
import io.activej.inject.module.AbstractModule;
import lombok.AllArgsConstructor;
import tech.tresearchgroup.babygalago.controller.controllers.UserSettingsEntityController;
import tech.tresearchgroup.palila.controller.EndpointsRouter;
import tech.tresearchgroup.palila.controller.RoutingServletBuilder;
import tech.tresearchgroup.palila.model.endpoints.Endpoint;

@AllArgsConstructor
public class UIUserEndpoints extends AbstractModule implements EndpointsRouter {
    private final UserSettingsEntityController userSettingsEntityController;

    /**
     * Creates the endpoints and maps them to their respective methods
     *
     * @return the routing servlet
     */
    @Override
    @Provides
    public RoutingServlet servlet() {
        return RoutingServletBuilder.build(getEndpoints());
    }

    @Override
    public Endpoint[] getEndpoints() {
        return new Endpoint[]{
            new Endpoint(HttpMethod.GET, "/user/settings", userSettingsEntityController::read),
            new Endpoint(HttpMethod.POST, "/user/settings", userSettingsEntityController::create)
        };
    }
}
