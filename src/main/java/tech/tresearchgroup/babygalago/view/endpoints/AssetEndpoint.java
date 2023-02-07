package tech.tresearchgroup.babygalago.view.endpoints;

import io.activej.http.HttpMethod;
import io.activej.http.RoutingServlet;
import io.activej.inject.annotation.Provides;
import io.activej.inject.module.AbstractModule;
import tech.tresearchgroup.babygalago.controller.endpoints.AssetEndpointController;
import tech.tresearchgroup.palila.controller.EndpointsRouter;
import tech.tresearchgroup.palila.controller.RoutingServletBuilder;
import tech.tresearchgroup.palila.model.endpoints.Endpoint;

public class AssetEndpoint extends AbstractModule implements EndpointsRouter {
    private final AssetEndpointController assetEndpointController;

    public AssetEndpoint(AssetEndpointController assetEndpointController) {
        this.assetEndpointController = assetEndpointController;
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
            new Endpoint(HttpMethod.GET, "/assets/:file", assetEndpointController::getAsset),
            new Endpoint(HttpMethod.GET, "/assets/gen/styles.min.css", assetEndpointController::getCombinedCSS),
            new Endpoint(HttpMethod.GET, "/assets/webfonts/:file", assetEndpointController::getAsset)
        };
    }
}
