package tech.tresearchgroup.babygalago.view.endpoints.api;

import io.activej.http.HttpMethod;
import io.activej.http.RoutingServlet;
import io.activej.inject.annotation.Provides;
import io.activej.inject.module.AbstractModule;
import lombok.AllArgsConstructor;
import tech.tresearchgroup.babygalago.controller.endpoints.api.TaskEndpointsController;

@AllArgsConstructor
public class TaskEndpoints extends AbstractModule {
    private final TaskEndpointsController taskEndpointsController;

    /**
     * Creates the endpoints and maps them to their respective methods
     *
     * @return the routing servlet
     */
    @Provides
    public RoutingServlet servlet() {
        return RoutingServlet.create()
            .map(HttpMethod.GET, "/v1/tasks/:taskType", taskEndpointsController::getTask)
            .map(HttpMethod.PUT, "/v1/tasks/:taskType", taskEndpointsController::putTask)
            .map(HttpMethod.DELETE, "/v1/tasks/:taskType", taskEndpointsController::deleteTask)
            .map(HttpMethod.OPTIONS, "/v1/tasks/:taskType", taskEndpointsController::optionsTask);
    }
}
