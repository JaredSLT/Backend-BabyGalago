package tech.tresearchgroup.babygalago.view.endpoints.api;

import io.activej.http.HttpMethod;
import io.activej.http.RoutingServlet;
import io.activej.inject.annotation.Provides;
import io.activej.inject.module.AbstractModule;
import lombok.AllArgsConstructor;
import tech.tresearchgroup.babygalago.controller.endpoints.api.RatingEndpointsController;

@AllArgsConstructor
public class RatingEndpoints extends AbstractModule {
    private final RatingEndpointsController ratingEndpointsController;

    /**
     * Creates the endpoints and maps them to their respective methods
     *
     * @return the routing servlet
     */
    @Provides
    public RoutingServlet servlet() {
        return RoutingServlet.create()
            .map(HttpMethod.GET, "/v1/ratings/:ratingId", ratingEndpointsController::getRating)
            .map(HttpMethod.PATCH, "/v1/ratings/:ratingId", ratingEndpointsController::patchRating)
            .map(HttpMethod.DELETE, "/v1/ratings/:ratingId", ratingEndpointsController::deleteRating)
            .map(HttpMethod.PUT, "/v1/ratings/:ratingId", ratingEndpointsController::putRating)
            .map(HttpMethod.POST, "/v1/ratings/:ratingId", ratingEndpointsController::postRating)
            .map(HttpMethod.OPTIONS, "/v1/ratings/:ratingId", ratingEndpointsController::optionsRatingById);
    }
}
