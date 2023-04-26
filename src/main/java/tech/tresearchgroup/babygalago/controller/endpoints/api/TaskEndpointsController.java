package tech.tresearchgroup.babygalago.controller.endpoints.api;

import io.activej.http.HttpHeaderValue;
import io.activej.http.HttpHeaders;
import io.activej.http.HttpRequest;
import io.activej.http.HttpResponse;
import io.activej.promise.Promisable;
import org.jetbrains.annotations.NotNull;
import tech.tresearchgroup.babygalago.controller.TaskController;
import tech.tresearchgroup.palila.controller.BasicController;
import tech.tresearchgroup.schemas.galago.enums.BaseMediaTypeEnum;

public class TaskEndpointsController extends BasicController {
    private final TaskController scheduleController;

    public TaskEndpointsController(TaskController scheduleController) {
        this.scheduleController = scheduleController;
    }

    /**
     * Gets whether the queue for a media type is running
     *
     * @param httpRequest the request
     * @return the page response
     */
    public Promisable<HttpResponse> getTask(HttpRequest httpRequest) {
        String baseMediaType = httpRequest.getPathParameter("taskType").toUpperCase();
        BaseMediaTypeEnum baseMediaTypeEnum = Enum.valueOf(BaseMediaTypeEnum.class, baseMediaType);
        boolean returnThis = false;
        switch (baseMediaTypeEnum) {
            case BOOK -> {
                returnThis = scheduleController.isBookRunning();
            }
            case GAME -> {
                returnThis = scheduleController.isGameRunning();
            }
            case MOVIE -> {
                returnThis = scheduleController.isMovieRunning();
            }
            case MUSIC -> {
                returnThis = scheduleController.isMusicRunning();
            }
            case TVSHOW -> {
                returnThis = scheduleController.isTvShowRunning();
            }
        }
        if (returnThis) {
            return ok();
        } else {
            return error();
        }
    }

    /**
     * Starts the queue for a media type
     *
     * @param httpRequest the request
     * @return the page response
     */
    public Promisable<HttpResponse> putTask(HttpRequest httpRequest) {
        String baseMediaType = httpRequest.getPathParameter("taskType").toUpperCase();
        BaseMediaTypeEnum baseMediaTypeEnum = Enum.valueOf(BaseMediaTypeEnum.class, baseMediaType);
        boolean returnThis = false;
        switch (baseMediaTypeEnum) {
            case BOOK -> {
                returnThis = scheduleController.startBookJob();
            }
            case GAME -> {
                returnThis = scheduleController.startGameJob();
            }
            case MOVIE -> {
                returnThis = scheduleController.startMovieJob();
            }
            case MUSIC -> {
                returnThis = scheduleController.startMusicJob();
            }
            case TVSHOW -> {
                returnThis = scheduleController.startTvShowJob();
            }
        }
        if (returnThis) {
            return ok();
        } else {
            return error();
        }
    }

    /**
     * Stops the queue for a media type
     *
     * @param httpRequest the request
     * @return the page response
     */
    public Promisable<HttpResponse> deleteTask(HttpRequest httpRequest) {
        String baseMediaType = httpRequest.getPathParameter("taskType").toUpperCase();
        BaseMediaTypeEnum baseMediaTypeEnum = Enum.valueOf(BaseMediaTypeEnum.class, baseMediaType);
        boolean returnThis = false;
        switch (baseMediaTypeEnum) {
            case BOOK -> {
                returnThis = scheduleController.stopBookJob();
            }
            case GAME -> {
                returnThis = scheduleController.stopGameJob();
            }
            case MOVIE -> {
                returnThis = scheduleController.stopMovieJob();
            }
            case MUSIC -> {
                returnThis = scheduleController.stopMusicJob();
            }
            case TVSHOW -> {
                returnThis = scheduleController.stopTvShowJob();
            }
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
    public Promisable<HttpResponse> optionsTask(@NotNull HttpRequest httpRequest) {
        return HttpResponse.ok200().withHeader(HttpHeaders.ALLOW, HttpHeaderValue.of("GET, PUT, DELETE"));
    }
}
