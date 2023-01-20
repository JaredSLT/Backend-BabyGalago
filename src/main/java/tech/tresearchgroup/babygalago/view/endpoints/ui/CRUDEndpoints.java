package tech.tresearchgroup.babygalago.view.endpoints.ui;

import io.activej.http.HttpMethod;
import io.activej.http.RoutingServlet;
import io.activej.inject.annotation.Provides;
import io.activej.inject.module.AbstractModule;
import lombok.AllArgsConstructor;
import tech.tresearchgroup.babygalago.controller.endpoints.ui.CRUDEndpointsController;
import tech.tresearchgroup.palila.controller.EndpointsRouter;
import tech.tresearchgroup.palila.controller.RoutingServletBuilder;
import tech.tresearchgroup.palila.model.endpoints.Endpoint;

@AllArgsConstructor
public class CRUDEndpoints extends AbstractModule implements EndpointsRouter {
    private final CRUDEndpointsController CRUDEndpointsController;

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
            new Endpoint(HttpMethod.GET, "/add/:mediaType", CRUDEndpointsController::add),
            new Endpoint(HttpMethod.POST, "/add/:mediaType", CRUDEndpointsController::createObjectFromForm),
            new Endpoint(HttpMethod.GET, "/browse/:mediaType", CRUDEndpointsController::browse),
            new Endpoint(HttpMethod.GET, "/edit/:mediaType/:id", CRUDEndpointsController::edit),
            new Endpoint(HttpMethod.POST, "/edit/:mediaType/:id", CRUDEndpointsController::updateObjectFromForm),
            new Endpoint(HttpMethod.GET, "/new/:mediaType", CRUDEndpointsController::newEntities),
            new Endpoint(HttpMethod.GET, "/popular/:mediaType", CRUDEndpointsController::popularEntities),
            new Endpoint(HttpMethod.GET, "/view/:mediaType/:id", CRUDEndpointsController::view),
            new Endpoint(HttpMethod.GET, "/delete/:mediaType/:id", CRUDEndpointsController::delete),
            new Endpoint(HttpMethod.POST, "/delete/:mediaType/:id", CRUDEndpointsController::postDelete),
            new Endpoint(HttpMethod.POST, "/add/:mediaType/upload/:varName", CRUDEndpointsController::postUpload),
        };
    }
}
