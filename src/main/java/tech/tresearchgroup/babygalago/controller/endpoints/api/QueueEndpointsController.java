package tech.tresearchgroup.babygalago.controller.endpoints.api;

import com.google.gson.Gson;
import io.activej.http.HttpHeaderValue;
import io.activej.http.HttpHeaders;
import io.activej.http.HttpRequest;
import io.activej.http.HttpResponse;
import io.activej.promise.Promisable;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import tech.tresearchgroup.babygalago.controller.controllers.QueueEntityController;
import tech.tresearchgroup.palila.controller.BasicController;
import tech.tresearchgroup.schemas.galago.enums.QueueTypeEnum;

import java.io.IOException;

@AllArgsConstructor
public class QueueEndpointsController extends BasicController {
    private final QueueEntityController queueEntityController;
    private final Gson gson;

    /**
     * Gets the possible queue values
     *
     * @param httpRequest the request
     * @return the page response
     * @throws IOException if its fails
     */
    @NotNull
    public Promisable<HttpResponse> getQueues(@NotNull HttpRequest httpRequest) throws IOException {
        return ok(gson.toJson(QueueTypeEnum.values()).getBytes());
    }

    /**
     * Gets the entities inside a queue
     *
     * @param httpRequest the request
     * @return the page response
     */
    public Promisable<HttpResponse> getQueue(HttpRequest httpRequest) {
        String baseMediaType = httpRequest.getPathParameter("queueType").toUpperCase();
        QueueTypeEnum queueTypeEnum = Enum.valueOf(QueueTypeEnum.class, baseMediaType);
        boolean returnThis = false;
        if (queueTypeEnum == QueueTypeEnum.CONVERTER) {
            returnThis = queueEntityController.isConverterQueueRunning();
        }
        if (returnThis) {
            return ok();
        } else {
            return error();
        }
    }

    /**
     * Creates a queue entity
     *
     * @param httpRequest the request
     * @return the page response
     */
    public Promisable<HttpResponse> putQueue(HttpRequest httpRequest) {
        String baseMediaType = httpRequest.getPathParameter("queueType").toUpperCase();
        QueueTypeEnum queueTypeEnum = Enum.valueOf(QueueTypeEnum.class, baseMediaType);
        boolean returnThis = false;
        if (queueTypeEnum == QueueTypeEnum.CONVERTER) {
            returnThis = queueEntityController.startConverterQueue();
        }
        if (returnThis) {
            return ok();
        } else {
            return error();
        }
    }

    /**
     * Stops the queue
     *
     * @param httpRequest the request
     * @return the page request
     */
    public Promisable<HttpResponse> deleteQueue(HttpRequest httpRequest) {
        String baseMediaType = httpRequest.getPathParameter("queueType").toUpperCase();
        QueueTypeEnum queueTypeEnum = Enum.valueOf(QueueTypeEnum.class, baseMediaType);
        boolean returnThis = false;
        if (queueTypeEnum == QueueTypeEnum.CONVERTER) {
            returnThis = queueEntityController.stopConverterQueue();
        }
        if (returnThis) {
            return ok();
        } else {
            return error();
        }
    }

    /**
     * Gets which methods are available for this endpoint
     *
     * @param httpRequest the request
     * @return the response page
     */
    @NotNull
    public Promisable<HttpResponse> optionsQueue(@NotNull HttpRequest httpRequest) {
        return HttpResponse.ok200().withHeader(HttpHeaders.ALLOW, HttpHeaderValue.of("GET, PUT, DELETE"));
    }
}
