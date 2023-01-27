package tech.tresearchgroup.babygalago.view.endpoints;

import io.activej.http.HttpMethod;
import io.activej.inject.annotation.Provides;
import io.activej.inject.module.AbstractModule;
import lombok.AllArgsConstructor;
import tech.tresearchgroup.babygalago.controller.endpoints.AssetEndpointController;
import tech.tresearchgroup.palila.model.endpoints.Endpoint;

@AllArgsConstructor
public class AssetEndpoint extends AbstractModule {
    private final AssetEndpointController assetEndpointController;

    /**
     * Adds the endpoints
     *
     * @return the routing servlet
     */
    @Provides
    public Endpoint[] servlet() {
        return new Endpoint[]{
            new Endpoint(HttpMethod.GET, "/assets/:file", assetEndpointController::getAsset),
            new Endpoint(HttpMethod.GET, "/assets/gen/styles.min.css", assetEndpointController::getCombinedCSS),
            new Endpoint(HttpMethod.GET, "/assets/webfonts/:file", assetEndpointController::getAsset)
        };
    }
}
