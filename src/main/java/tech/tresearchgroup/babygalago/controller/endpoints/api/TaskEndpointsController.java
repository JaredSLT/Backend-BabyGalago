package tech.tresearchgroup.babygalago.controller.endpoints.api;

import io.activej.http.HttpRequest;
import io.activej.http.HttpResponse;
import lombok.AllArgsConstructor;
import tech.tresearchgroup.babygalago.controller.TaskController;
import tech.tresearchgroup.babygalago.controller.controllers.UserEntityController;
import tech.tresearchgroup.palila.controller.BasicController;
import tech.tresearchgroup.schemas.galago.enums.BaseMediaTypeEnum;

@AllArgsConstructor
public class TaskEndpointsController extends BasicController {
    private final TaskController scheduleController;
    private final UserEntityController userEntityController;

    public HttpResponse getTask(BaseMediaTypeEnum baseMediaTypeEnum, HttpRequest httpRequest) {
        boolean returnThis = false;
        switch (baseMediaTypeEnum) {
            case BOOK -> returnThis = scheduleController.isBookRunning();
            case GAME -> returnThis = scheduleController.isGameRunning();
            case MOVIE -> returnThis = scheduleController.isMovieRunning();
            case MUSIC -> returnThis = scheduleController.isMusicRunning();
            case TVSHOW -> returnThis = scheduleController.isTvShowRunning();
        }
        if (returnThis) {
            return ok();
        } else {
            return error();
        }
    }

    public HttpResponse putTask(BaseMediaTypeEnum baseMediaTypeEnum, HttpRequest httpRequest) {
        boolean returnThis = false;
        switch (baseMediaTypeEnum) {
            case BOOK -> returnThis = scheduleController.startBookJob();
            case GAME -> returnThis = scheduleController.startGameJob();
            case MOVIE -> returnThis = scheduleController.startMovieJob();
            case MUSIC -> returnThis = scheduleController.startMusicJob();
            case TVSHOW -> returnThis = scheduleController.startTvShowJob();
        }
        if (returnThis) {
            return ok();
        } else {
            return error();
        }
    }

    public HttpResponse deleteTask(BaseMediaTypeEnum baseMediaTypeEnum, HttpRequest httpRequest) {
        boolean returnThis = false;
        switch (baseMediaTypeEnum) {
            case BOOK -> returnThis = scheduleController.stopBookJob();
            case GAME -> returnThis = scheduleController.stopGameJob();
            case MOVIE -> returnThis = scheduleController.stopMovieJob();
            case MUSIC -> returnThis = scheduleController.stopMusicJob();
            case TVSHOW -> returnThis = scheduleController.stopTvShowJob();
        }
        if (returnThis) {
            return ok();
        } else {
            return error();
        }
    }
}
