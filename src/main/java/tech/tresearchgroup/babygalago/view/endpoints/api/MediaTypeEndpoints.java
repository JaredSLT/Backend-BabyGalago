package tech.tresearchgroup.babygalago.view.endpoints.api;

import io.activej.http.HttpMethod;
import io.activej.http.RoutingServlet;
import io.activej.inject.annotation.Provides;
import io.activej.inject.module.AbstractModule;
import lombok.AllArgsConstructor;
import tech.tresearchgroup.babygalago.controller.endpoints.SearchEndpointsController;
import tech.tresearchgroup.babygalago.controller.endpoints.api.MediaTypeEndpointsController;

@AllArgsConstructor
public class MediaTypeEndpoints extends AbstractModule {
    private final MediaTypeEndpointsController mediaTypeEndpointsController;
    private final SearchEndpointsController searchEndpointsController;

    /**
     * Creates the endpoints and maps them to their respective methods
     *
     * @return the routing servlet
     */
    @Provides
    public RoutingServlet servlet() {
        return RoutingServlet.create()
            .map(HttpMethod.GET, "/v1/:mediaType", mediaTypeEndpointsController::getMedia)
            .map(HttpMethod.POST, "/v1/:mediaType", mediaTypeEndpointsController::postMedia)
            .map(HttpMethod.PUT, "/v1/:mediaType", mediaTypeEndpointsController::putMedia)
            .map(HttpMethod.OPTIONS, "/v1/:mediaType", mediaTypeEndpointsController::optionsMediaType)
            .map(HttpMethod.GET, "/v1/:mediaType/sample", mediaTypeEndpointsController::getMediaSample)
            .map(HttpMethod.PUT, "/v1/:mediaType/deleteindex", mediaTypeEndpointsController::putDeleteIndex)
            .map(HttpMethod.OPTIONS, "/v1/:mediaType/deleteindex", mediaTypeEndpointsController::optionsDeleteIndex)
            .map(HttpMethod.PUT, "/v1/:mediaType/reindex", mediaTypeEndpointsController::putReindex)
            .map(HttpMethod.OPTIONS, "/v1/:mediaType/reindex", mediaTypeEndpointsController::optionsReIndex)
            .map(HttpMethod.GET, "/v1/:mediaType/search", searchEndpointsController::searchByType)
            .map(HttpMethod.OPTIONS, "/v1/:mediaType/search", mediaTypeEndpointsController::optionsSearch)
            .map(HttpMethod.GET, "/v1/:mediaType/:mediaId", mediaTypeEndpointsController::getMediaById)
            .map(HttpMethod.PATCH, "/v1/:mediaType/:mediaId", mediaTypeEndpointsController::patchMediaById)
            .map(HttpMethod.DELETE, "/v1/:mediaType/:mediaId", mediaTypeEndpointsController::deleteMediaById)
            .map(HttpMethod.OPTIONS, "/v1/:mediaType/:mediaId", mediaTypeEndpointsController::optionsMediaTypeById)
            .map(HttpMethod.GET, "/v1/:mediaType/:mediaId/play", mediaTypeEndpointsController::getMediaSubEntityById)
            .map(HttpMethod.GET, "/v1/:mediaType/:mediaId/ratings", mediaTypeEndpointsController::getMediaRatings)
            .map(HttpMethod.OPTIONS, "/v1/:mediaType/:mediaId/ratings", mediaTypeEndpointsController::optionsMediaTypeRatingsById);
    }
}
