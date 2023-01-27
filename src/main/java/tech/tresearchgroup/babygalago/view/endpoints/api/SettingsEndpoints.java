package tech.tresearchgroup.babygalago.view.endpoints.api;

import io.activej.http.HttpMethod;
import io.activej.http.RoutingServlet;
import io.activej.inject.annotation.Provides;
import io.activej.inject.module.AbstractModule;
import lombok.AllArgsConstructor;
import tech.tresearchgroup.babygalago.controller.endpoints.api.SettingsEndpointsController;
import tech.tresearchgroup.palila.controller.EndpointsRouter;
import tech.tresearchgroup.palila.controller.RoutingServletBuilder;
import tech.tresearchgroup.palila.model.endpoints.Endpoint;

@AllArgsConstructor
public class SettingsEndpoints extends AbstractModule implements EndpointsRouter {
    private final SettingsEndpointsController settingsEndpointsController;

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
            new Endpoint(HttpMethod.GET, "/v1/settings", settingsEndpointsController::getSettings),
            new Endpoint(HttpMethod.PATCH, "/v1/settings", settingsEndpointsController::patchSettings),
            new Endpoint(HttpMethod.OPTIONS, "/v1/settings", settingsEndpointsController::optionsSettings)
        };
    }
}
