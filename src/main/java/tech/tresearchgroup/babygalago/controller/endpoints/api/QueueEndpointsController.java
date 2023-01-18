package tech.tresearchgroup.babygalago.controller.endpoints.api;

import io.activej.http.HttpRequest;
import io.activej.http.HttpResponse;
import lombok.AllArgsConstructor;
import tech.tresearchgroup.babygalago.controller.controllers.QueueEntityController;
import tech.tresearchgroup.babygalago.controller.controllers.UserEntityController;
import tech.tresearchgroup.palila.controller.BasicController;
import tech.tresearchgroup.schemas.galago.enums.QueueTypeEnum;

@AllArgsConstructor
public class QueueEndpointsController extends BasicController {
    private final QueueEntityController queueEntityController;
    private final UserEntityController userEntityController;

    public HttpResponse getTask(QueueTypeEnum queueTypeEnum, HttpRequest httpRequest) {
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

    public HttpResponse putTask(QueueTypeEnum queueTypeEnum, HttpRequest httpRequest) {
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

    public HttpResponse deleteTask(QueueTypeEnum queueTypeEnum, HttpRequest httpRequest) {
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
}
