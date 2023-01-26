package tech.tresearchgroup.babygalago.view.endpoints.api;

import io.activej.http.HttpMethod;
import io.activej.http.RoutingServlet;
import io.activej.inject.annotation.Provides;
import io.activej.inject.module.AbstractModule;
import lombok.AllArgsConstructor;
import tech.tresearchgroup.babygalago.controller.endpoints.SearchEndpointsController;
import tech.tresearchgroup.babygalago.controller.endpoints.api.GeneralEndpointsController;

@AllArgsConstructor
public class GeneralEndpoints extends AbstractModule {
    private final GeneralEndpointsController generalEndpointsController;
    private final SearchEndpointsController searchEndpointsController;

    /**
     * Creates the endpoints and maps them to their respective methods
     *
     * @return the routing servlet
     */
    @Provides
    public RoutingServlet servlet() {
        RoutingServlet routingServlet = RoutingServlet.create();
        routingServlet.map(HttpMethod.GET, "/v1/version", generalEndpointsController::getVersion);
        routingServlet.map(HttpMethod.OPTIONS, "/v1/version", generalEndpointsController::optionsVersion);
        return RoutingServlet.create()
            .map(HttpMethod.GET, "/v1/version", generalEndpointsController::getVersion)
            .map(HttpMethod.OPTIONS, "/v1/version", generalEndpointsController::optionsVersion)
            .map(HttpMethod.GET, "/v1/version/latest", generalEndpointsController::getLatest)
            .map(HttpMethod.OPTIONS, "/v1/version/latest", generalEndpointsController::optionsVersionLatest)
            .map(HttpMethod.PUT, "/v1/update", generalEndpointsController::putUpdate)
            .map(HttpMethod.OPTIONS, "/v1/update", generalEndpointsController::optionsUpdate)
            .map(HttpMethod.POST, "/v1/upload", generalEndpointsController::postUpload)
            .map(HttpMethod.OPTIONS, "/v1/upload", generalEndpointsController::optionsUpload)
            .map(HttpMethod.GET, "/v1/search", searchEndpointsController::searchAPIResponse)
            .map(HttpMethod.OPTIONS, "/v1/search", generalEndpointsController::optionsSearch);
    }
}
