package tech.tresearchgroup.babygalago.view.endpoints.api;

import io.activej.http.HttpMethod;
import io.activej.http.RoutingServlet;
import io.activej.inject.annotation.Provides;
import io.activej.inject.module.AbstractModule;
import lombok.AllArgsConstructor;
import tech.tresearchgroup.babygalago.controller.endpoints.api.SettingsEndpointsController;

@AllArgsConstructor
public class SettingsEndpoints extends AbstractModule {
    private final SettingsEndpointsController settingsEndpointsController;

    /**
     * Creates the endpoints and maps them to their respective methods
     * @return the routing servlet
     */
    @Provides
    public RoutingServlet servlet() {
        return RoutingServlet.create()
            .map(HttpMethod.GET, "/v1/settings", settingsEndpointsController::getSettings)
            .map(HttpMethod.PATCH, "/v1/settings", settingsEndpointsController::patchSettings)
            .map(HttpMethod.OPTIONS, "/v1/settings", settingsEndpointsController::optionsSettings);
    }
}
