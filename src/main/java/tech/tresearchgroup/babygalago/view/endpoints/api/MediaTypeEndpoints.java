package tech.tresearchgroup.babygalago.view.endpoints.api;

import io.activej.http.HttpMethod;
import io.activej.http.RoutingServlet;
import io.activej.inject.annotation.Provides;
import io.activej.inject.module.AbstractModule;
import tech.tresearchgroup.babygalago.controller.endpoints.SearchEndpointsController;
import tech.tresearchgroup.babygalago.controller.endpoints.api.MediaTypeEndpointsController;
import tech.tresearchgroup.palila.controller.EndpointsRouter;
import tech.tresearchgroup.palila.controller.RoutingServletBuilder;
import tech.tresearchgroup.palila.model.endpoints.Endpoint;

public class MediaTypeEndpoints extends AbstractModule implements EndpointsRouter {
    private MediaTypeEndpointsController mediaTypeEndpointsController;
    private SearchEndpointsController searchEndpointsController;

    public MediaTypeEndpoints() {
    }

    public MediaTypeEndpoints(MediaTypeEndpointsController mediaTypeEndpointsController,
                              SearchEndpointsController searchEndpointsController) {
        this.mediaTypeEndpointsController = mediaTypeEndpointsController;
        this.searchEndpointsController = searchEndpointsController;
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
            new Endpoint(HttpMethod.GET, "/v1/:mediaType", mediaTypeEndpointsController::getMedia),
            new Endpoint(HttpMethod.POST, "/v1/:mediaType", mediaTypeEndpointsController::postMedia),
            new Endpoint(HttpMethod.PUT, "/v1/:mediaType", mediaTypeEndpointsController::putMedia),
            new Endpoint(HttpMethod.OPTIONS, "/v1/:mediaType", mediaTypeEndpointsController::optionsMediaType),
            new Endpoint(HttpMethod.GET, "/v1/:mediaType/sample", mediaTypeEndpointsController::getMediaSample),
            new Endpoint(HttpMethod.PUT, "/v1/:mediaType/deleteindex", mediaTypeEndpointsController::putDeleteIndex),
            new Endpoint(HttpMethod.OPTIONS, "/v1/:mediaType/deleteindex", mediaTypeEndpointsController::optionsDeleteIndex),
            new Endpoint(HttpMethod.PUT, "/v1/:mediaType/reindex", mediaTypeEndpointsController::putReindex),
            new Endpoint(HttpMethod.OPTIONS, "/v1/:mediaType/reindex", mediaTypeEndpointsController::optionsReIndex),
            new Endpoint(HttpMethod.GET, "/v1/:mediaType/search", searchEndpointsController::searchByType),
            new Endpoint(HttpMethod.OPTIONS, "/v1/:mediaType/search", mediaTypeEndpointsController::optionsSearch),
            new Endpoint(HttpMethod.GET, "/v1/:mediaType/:mediaId", mediaTypeEndpointsController::getMediaById),
            new Endpoint(HttpMethod.PATCH, "/v1/:mediaType/:mediaId", mediaTypeEndpointsController::patchMediaById),
            new Endpoint(HttpMethod.DELETE, "/v1/:mediaType/:mediaId", mediaTypeEndpointsController::deleteMediaById),
            new Endpoint(HttpMethod.OPTIONS, "/v1/:mediaType/:mediaId", mediaTypeEndpointsController::optionsMediaTypeById),
            new Endpoint(HttpMethod.GET, "/v1/:mediaType/:mediaId/play", mediaTypeEndpointsController::getMediaSubEntityById),
            new Endpoint(HttpMethod.GET, "/v1/:mediaType/:mediaId/ratings", mediaTypeEndpointsController::getMediaRatings),
            new Endpoint(HttpMethod.OPTIONS, "/v1/:mediaType/:mediaId/ratings", mediaTypeEndpointsController::optionsMediaTypeRatingsById)
        };
    }
}
