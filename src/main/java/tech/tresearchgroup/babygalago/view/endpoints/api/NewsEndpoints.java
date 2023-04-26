package tech.tresearchgroup.babygalago.view.endpoints.api;

import io.activej.http.HttpMethod;
import io.activej.http.RoutingServlet;
import io.activej.inject.annotation.Provides;
import io.activej.inject.module.AbstractModule;
import tech.tresearchgroup.babygalago.controller.endpoints.api.NewsEndpointsController;
import tech.tresearchgroup.palila.controller.EndpointsRouter;
import tech.tresearchgroup.palila.controller.RoutingServletBuilder;
import tech.tresearchgroup.palila.model.endpoints.Endpoint;

public class NewsEndpoints extends AbstractModule implements EndpointsRouter {
    private NewsEndpointsController newsEndpointsController;

    public NewsEndpoints() {
    }

    public NewsEndpoints(NewsEndpointsController newsEndpointsController) {
        this.newsEndpointsController = newsEndpointsController;
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
            new Endpoint(HttpMethod.PUT, "/v1/news", newsEndpointsController::putNews),
            new Endpoint(HttpMethod.POST, "/v1/news", newsEndpointsController::postNews),
            new Endpoint(HttpMethod.GET, "/v1/news", newsEndpointsController::getNews),
            new Endpoint(HttpMethod.OPTIONS, "/v1/news", newsEndpointsController::optionsNews),
            new Endpoint(HttpMethod.GET, "/v1/news/:newsId", newsEndpointsController::getNewsById),
            new Endpoint(HttpMethod.DELETE, "/v1/news/:newsId", newsEndpointsController::deleteNewsById),
            new Endpoint(HttpMethod.PATCH, "/v1/news/:newsId", newsEndpointsController::patchNews),
            new Endpoint(HttpMethod.OPTIONS, "/v1/news/:newsId", newsEndpointsController::optionsNewsById)
        };
    }
}
