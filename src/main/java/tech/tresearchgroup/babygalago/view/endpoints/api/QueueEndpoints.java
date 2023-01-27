package tech.tresearchgroup.babygalago.view.endpoints.api;

import io.activej.http.HttpMethod;
import io.activej.http.RoutingServlet;
import io.activej.inject.annotation.Provides;
import io.activej.inject.module.AbstractModule;
import lombok.AllArgsConstructor;
import tech.tresearchgroup.babygalago.controller.endpoints.api.QueueEndpointsController;
import tech.tresearchgroup.palila.controller.EndpointsRouter;
import tech.tresearchgroup.palila.controller.RoutingServletBuilder;
import tech.tresearchgroup.palila.model.endpoints.Endpoint;

@AllArgsConstructor
public class QueueEndpoints extends AbstractModule implements EndpointsRouter {
    private final QueueEndpointsController queueEndpointsController;

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
            new Endpoint(HttpMethod.GET, "/v1/queues", queueEndpointsController::getQueues),
            new Endpoint(HttpMethod.GET, "/v1/queue/:queueType", queueEndpointsController::getQueue),
            new Endpoint(HttpMethod.PUT, "/v1/queue/:queueType", queueEndpointsController::putQueue),
            new Endpoint(HttpMethod.DELETE, "/v1/queue/:queueType", queueEndpointsController::deleteQueue),
            new Endpoint(HttpMethod.OPTIONS, "/v1/queue/:queueType", queueEndpointsController::optionsQueue)
        };
    }
}
