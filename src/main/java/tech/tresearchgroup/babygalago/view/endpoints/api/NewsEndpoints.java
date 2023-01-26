package tech.tresearchgroup.babygalago.view.endpoints.api;

import io.activej.http.HttpMethod;
import io.activej.http.RoutingServlet;
import io.activej.inject.annotation.Provides;
import io.activej.inject.module.AbstractModule;
import lombok.AllArgsConstructor;
import tech.tresearchgroup.babygalago.controller.endpoints.api.NewsEndpointsController;

@AllArgsConstructor
public class NewsEndpoints extends AbstractModule {
    private final NewsEndpointsController newsEndpointsController;

    /**
     * Creates the endpoints and maps them to their respective methods
     *
     * @return the routing servlet
     */
    @Provides
    public RoutingServlet servlet() {
        return RoutingServlet.create()
            .map(HttpMethod.PUT, "/v1/news", newsEndpointsController::putNews)
            .map(HttpMethod.POST, "/v1/news", newsEndpointsController::postNews)
            .map(HttpMethod.GET, "/v1/news", newsEndpointsController::getNews)
            .map(HttpMethod.OPTIONS, "/v1/news", newsEndpointsController::optionsNews)
            .map(HttpMethod.GET, "/v1/news/:newsId", newsEndpointsController::getNewsById)
            .map(HttpMethod.DELETE, "/v1/news/:newsId", newsEndpointsController::deleteNewsById)
            .map(HttpMethod.PATCH, "/v1/news/:newsId", newsEndpointsController::patchNews)
            .map(HttpMethod.OPTIONS, "/v1/news/:newsId", newsEndpointsController::optionsNewsById);
    }
}
