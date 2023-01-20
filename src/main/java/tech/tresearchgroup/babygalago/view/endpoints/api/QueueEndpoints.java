package tech.tresearchgroup.babygalago.view.endpoints.api;

import io.activej.http.HttpMethod;
import io.activej.http.RoutingServlet;
import io.activej.inject.annotation.Provides;
import io.activej.inject.module.AbstractModule;
import lombok.AllArgsConstructor;
import tech.tresearchgroup.babygalago.controller.endpoints.api.QueueEndpointsController;

@AllArgsConstructor
public class QueueEndpoints extends AbstractModule {
    private final QueueEndpointsController queueEndpointsController;

    /**
     * Creates the endpoints and maps them to their respective methods
     * @return the routing servlet
     */
    @Provides
    public RoutingServlet servlet() {
        return RoutingServlet.create()
            .map(HttpMethod.GET, "/v1/queues", queueEndpointsController::getQueues)
            .map(HttpMethod.GET, "/v1/queue/:queueType", queueEndpointsController::getQueue)
            .map(HttpMethod.PUT, "/v1/queue/:queueType", queueEndpointsController::putQueue)
            .map(HttpMethod.DELETE, "/v1/queue/:queueType", queueEndpointsController::deleteQueue)
            .map(HttpMethod.OPTIONS, "/v1/queue/:queueType", queueEndpointsController::optionsQueue);
    }
}
