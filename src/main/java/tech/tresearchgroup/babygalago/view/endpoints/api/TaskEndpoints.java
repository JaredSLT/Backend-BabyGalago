package tech.tresearchgroup.babygalago.view.endpoints.api;

import io.activej.http.*;
import io.activej.inject.annotation.Provides;
import io.activej.promise.Promisable;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import tech.tresearchgroup.babygalago.controller.endpoints.api.TaskEndpointsController;
import tech.tresearchgroup.palila.controller.HttpResponses;
import tech.tresearchgroup.schemas.galago.enums.BaseMediaTypeEnum;

@AllArgsConstructor
public class TaskEndpoints extends HttpResponses {
    private final TaskEndpointsController taskEndpointsController;

    @Provides
    public RoutingServlet servlet() {
        return RoutingServlet.create()
            .map(HttpMethod.GET, "/v1/tasks/:taskType", this::getTask)
            .map(HttpMethod.PUT, "/v1/tasks/:taskType", this::putTask)
            .map(HttpMethod.DELETE, "/v1/tasks/:taskType", this::deleteTask)
            .map(HttpMethod.OPTIONS, "/v1/tasks/:taskType", this::optionsTask);
    }

    private @NotNull Promisable<HttpResponse> getTask(@NotNull HttpRequest httpRequest) {
        String baseMediaType = httpRequest.getPathParameter("taskType").toUpperCase();
        BaseMediaTypeEnum baseMediaTypeEnum = Enum.valueOf(BaseMediaTypeEnum.class, baseMediaType);
        return taskEndpointsController.getTask(baseMediaTypeEnum, httpRequest);
    }

    private @NotNull Promisable<HttpResponse> putTask(@NotNull HttpRequest httpRequest) {
        String baseMediaType = httpRequest.getPathParameter("taskType").toUpperCase();
        BaseMediaTypeEnum baseMediaTypeEnum = Enum.valueOf(BaseMediaTypeEnum.class, baseMediaType);
        return taskEndpointsController.putTask(baseMediaTypeEnum, httpRequest);
    }

    private @NotNull Promisable<HttpResponse> deleteTask(@NotNull HttpRequest httpRequest) {
        String baseMediaType = httpRequest.getPathParameter("taskType").toUpperCase();
        BaseMediaTypeEnum baseMediaTypeEnum = Enum.valueOf(BaseMediaTypeEnum.class, baseMediaType);
        return taskEndpointsController.deleteTask(baseMediaTypeEnum, httpRequest);
    }

    private @NotNull Promisable<HttpResponse> optionsTask(@NotNull HttpRequest httpRequest) {
        return HttpResponse.ok200().withHeader(HttpHeaders.ALLOW, HttpHeaderValue.of("GET, PUT, DELETE"));
    }
}
