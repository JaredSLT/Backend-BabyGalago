package tech.tresearchgroup.babygalago.view.endpoints;

import io.activej.http.HttpMethod;
import io.activej.http.RoutingServlet;
import io.activej.inject.annotation.Provides;
import io.activej.inject.module.AbstractModule;
import lombok.AllArgsConstructor;
import tech.tresearchgroup.babygalago.controller.endpoints.AssetEndpointController;

@AllArgsConstructor
public class AssetEndpoint extends AbstractModule {
    private final AssetEndpointController assetEndpointController;

    /**
     * Adds the endpoints
     *
     * @return the routing servlet
     */
    @Provides
    public RoutingServlet servlet() {
        return RoutingServlet.create()
            .map(HttpMethod.GET, "/assets/:file", assetEndpointController::getAsset)
            .map(HttpMethod.GET, "/assets/gen/styles.min.css", assetEndpointController::getCombinedCSS)
            .map(HttpMethod.GET, "/assets/webfonts/:file", assetEndpointController::getAsset);
    }
}
