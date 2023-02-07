package tech.tresearchgroup.babygalago.view.endpoints.api;

import io.activej.http.HttpMethod;
import io.activej.http.RoutingServlet;
import io.activej.inject.annotation.Provides;
import io.activej.inject.module.AbstractModule;
import tech.tresearchgroup.babygalago.controller.endpoints.api.RatingEndpointsController;
import tech.tresearchgroup.palila.controller.EndpointsRouter;
import tech.tresearchgroup.palila.controller.RoutingServletBuilder;
import tech.tresearchgroup.palila.model.endpoints.Endpoint;

public class RatingEndpoints extends AbstractModule implements EndpointsRouter {
    private RatingEndpointsController ratingEndpointsController;

    public RatingEndpoints() {
    }

    public RatingEndpoints(RatingEndpointsController ratingEndpointsController) {
        this.ratingEndpointsController = ratingEndpointsController;
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
            new Endpoint(HttpMethod.GET, "/v1/ratings/:ratingId", ratingEndpointsController::getRating),
            new Endpoint(HttpMethod.PATCH, "/v1/ratings/:ratingId", ratingEndpointsController::patchRating),
            new Endpoint(HttpMethod.DELETE, "/v1/ratings/:ratingId", ratingEndpointsController::deleteRating),
            new Endpoint(HttpMethod.PUT, "/v1/ratings/:ratingId", ratingEndpointsController::putRating),
            new Endpoint(HttpMethod.POST, "/v1/ratings/:ratingId", ratingEndpointsController::postRating),
            new Endpoint(HttpMethod.OPTIONS, "/v1/ratings/:ratingId", ratingEndpointsController::optionsRatingById)
        };
    }
}
