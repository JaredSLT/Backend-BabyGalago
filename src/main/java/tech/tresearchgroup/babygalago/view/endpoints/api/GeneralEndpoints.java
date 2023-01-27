package tech.tresearchgroup.babygalago.view.endpoints.api;

import io.activej.http.HttpMethod;
import io.activej.http.RoutingServlet;
import io.activej.inject.annotation.Provides;
import io.activej.inject.module.AbstractModule;
import lombok.AllArgsConstructor;
import tech.tresearchgroup.babygalago.controller.endpoints.SearchEndpointsController;
import tech.tresearchgroup.babygalago.controller.endpoints.api.GeneralEndpointsController;
import tech.tresearchgroup.palila.controller.EndpointsRouter;
import tech.tresearchgroup.palila.controller.RoutingServletBuilder;
import tech.tresearchgroup.palila.model.endpoints.Endpoint;

@AllArgsConstructor
public class GeneralEndpoints extends AbstractModule implements EndpointsRouter {
    private final GeneralEndpointsController generalEndpointsController;
    private final SearchEndpointsController searchEndpointsController;

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
            new Endpoint(HttpMethod.GET, "/v1/version", generalEndpointsController::getVersion),
            new Endpoint(HttpMethod.OPTIONS, "/v1/version", generalEndpointsController::optionsVersion),
            new Endpoint(HttpMethod.GET, "/v1/version/latest", generalEndpointsController::getLatest),
            new Endpoint(HttpMethod.OPTIONS, "/v1/version/latest", generalEndpointsController::optionsVersionLatest),
            new Endpoint(HttpMethod.PUT, "/v1/update", generalEndpointsController::putUpdate),
            new Endpoint(HttpMethod.OPTIONS, "/v1/update", generalEndpointsController::optionsUpdate),
            new Endpoint(HttpMethod.POST, "/v1/upload", generalEndpointsController::postUpload),
            new Endpoint(HttpMethod.OPTIONS, "/v1/upload", generalEndpointsController::optionsUpload),
            new Endpoint(HttpMethod.GET, "/v1/search", searchEndpointsController::searchAPIResponse),
            new Endpoint(HttpMethod.OPTIONS, "/v1/search", generalEndpointsController::optionsSearch)
        };
    }
}
