package tech.tresearchgroup.babygalago.view.endpoints.ui;

import io.activej.http.HttpMethod;
import io.activej.http.RoutingServlet;
import io.activej.inject.annotation.Provides;
import io.activej.inject.module.AbstractModule;
import lombok.AllArgsConstructor;
import tech.tresearchgroup.babygalago.controller.endpoints.SearchEndpointsController;
import tech.tresearchgroup.babygalago.controller.endpoints.ui.MainEndpointsController;
import tech.tresearchgroup.colobus.controller.IndexController;
import tech.tresearchgroup.palila.controller.EndpointsRouter;
import tech.tresearchgroup.palila.controller.RoutingServletBuilder;
import tech.tresearchgroup.palila.model.endpoints.Endpoint;

@AllArgsConstructor
public class MainEndpoints extends AbstractModule implements EndpointsRouter {
    private final MainEndpointsController mainEndpointsController;
    private final IndexController indexController;
    private final SearchEndpointsController searchEndpointsController;

    /**
     * Creates the endpoints and maps them to their respective methods
     * @return the routing servlet
     */
    @Provides
    public RoutingServlet servlet() {
        return RoutingServletBuilder.build(getEndpoints());
    }

    @Override
    public Endpoint[] getEndpoints() {
        return new Endpoint[]{
            new Endpoint(HttpMethod.GET, "/about", mainEndpointsController::about),
            new Endpoint(HttpMethod.GET, "/", mainEndpointsController::index),
            new Endpoint(HttpMethod.GET, "/login", mainEndpointsController::login),
            new Endpoint(HttpMethod.POST, "/login", mainEndpointsController::loginUI),
            new Endpoint(HttpMethod.GET, "/logout", mainEndpointsController::logout),
            new Endpoint(HttpMethod.GET, "/reset", mainEndpointsController::reset),
            new Endpoint(HttpMethod.GET, "/register", mainEndpointsController::register),
            new Endpoint(HttpMethod.POST, "/register", mainEndpointsController::postRegister),
            new Endpoint(HttpMethod.POST, "/reset", mainEndpointsController::postReset),
            new Endpoint(HttpMethod.GET, "/licenses", mainEndpointsController::licenses),
            new Endpoint(HttpMethod.GET, "/forum/index", indexController::index),
            new Endpoint(HttpMethod.GET, "/news", mainEndpointsController::news),
            new Endpoint(HttpMethod.GET, "/notifications", mainEndpointsController::notifications),
            new Endpoint(HttpMethod.GET, "/profile", mainEndpointsController::profile),
            new Endpoint(HttpMethod.POST, "/profile", mainEndpointsController::postProfile),
            new Endpoint(HttpMethod.GET, "/queue", mainEndpointsController::queue),
            new Endpoint(HttpMethod.GET, "/search", searchEndpointsController::searchNotFound),
            new Endpoint(HttpMethod.POST, "/search", searchEndpointsController::searchUIResponse),
            new Endpoint(HttpMethod.GET, "/settings", mainEndpointsController::settings),
            new Endpoint(HttpMethod.POST, "/settings", mainEndpointsController::saveSettings),
            new Endpoint(HttpMethod.GET, "/upload", mainEndpointsController::upload),
            new Endpoint(HttpMethod.POST, "/upload/:mediaType", mainEndpointsController::postUpload),
            new Endpoint(HttpMethod.GET, "/denied", mainEndpointsController::denied),
            new Endpoint(HttpMethod.GET, "/disabled", mainEndpointsController::disabled),
            new Endpoint(HttpMethod.GET, "/error", mainEndpointsController::error),
            new Endpoint(HttpMethod.GET, "/notfound", mainEndpointsController::notFound),
            new Endpoint(HttpMethod.GET, "/underconstruction", mainEndpointsController::underConstruction)
        };
    }
}
