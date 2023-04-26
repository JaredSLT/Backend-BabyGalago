package tech.tresearchgroup.babygalago.view.endpoints.api;

import io.activej.http.HttpMethod;
import io.activej.http.RoutingServlet;
import io.activej.inject.annotation.Provides;
import io.activej.inject.module.AbstractModule;
import tech.tresearchgroup.babygalago.controller.endpoints.api.TaskEndpointsController;
import tech.tresearchgroup.palila.controller.EndpointsRouter;
import tech.tresearchgroup.palila.controller.RoutingServletBuilder;
import tech.tresearchgroup.palila.model.endpoints.Endpoint;

public class TaskEndpoints extends AbstractModule implements EndpointsRouter {
    private TaskEndpointsController taskEndpointsController;

    public TaskEndpoints() {
    }

    public TaskEndpoints(TaskEndpointsController taskEndpointsController) {
        this.taskEndpointsController = taskEndpointsController;
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
            new Endpoint(HttpMethod.GET, "/v1/tasks/:taskType", taskEndpointsController::getTask),
            new Endpoint(HttpMethod.PUT, "/v1/tasks/:taskType", taskEndpointsController::putTask),
            new Endpoint(HttpMethod.DELETE, "/v1/tasks/:taskType", taskEndpointsController::deleteTask),
            new Endpoint(HttpMethod.OPTIONS, "/v1/tasks/:taskType", taskEndpointsController::optionsTask)
        };
    }
}
