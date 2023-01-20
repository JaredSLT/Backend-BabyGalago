package tech.tresearchgroup.babygalago.controller.endpoints.api;

import io.activej.http.HttpHeaderValue;
import io.activej.http.HttpHeaders;
import io.activej.http.HttpRequest;
import io.activej.http.HttpResponse;
import io.activej.promise.Promisable;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import tech.tresearchgroup.babygalago.controller.TaskController;
import tech.tresearchgroup.palila.controller.BasicController;
import tech.tresearchgroup.schemas.galago.enums.BaseMediaTypeEnum;

@AllArgsConstructor
public class TaskEndpointsController extends BasicController {
    private final TaskController scheduleController;

    /**
     * Gets whether the queue for a media type is running
     * @param httpRequest the request
     * @return the page response
     */
    public Promisable<HttpResponse> getTask(HttpRequest httpRequest) {
        String baseMediaType = httpRequest.getPathParameter("taskType").toUpperCase();
        BaseMediaTypeEnum baseMediaTypeEnum = Enum.valueOf(BaseMediaTypeEnum.class, baseMediaType);
        boolean returnThis = false;
        switch (baseMediaTypeEnum) {
            case BOOK: {
                returnThis = scheduleController.isBookRunning();
                break;
            }
            case GAME: {
                returnThis = scheduleController.isGameRunning();
                break;
            }
            case MOVIE: {
                returnThis = scheduleController.isMovieRunning();
                break;
            }
            case MUSIC: {
                returnThis = scheduleController.isMusicRunning();
                break;
            }
            case TVSHOW: {
                returnThis = scheduleController.isTvShowRunning();
                break;
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
     * @param httpRequest the request
     * @return the page response
     */
    public Promisable<HttpResponse> putTask(HttpRequest httpRequest) {
        String baseMediaType = httpRequest.getPathParameter("taskType").toUpperCase();
        BaseMediaTypeEnum baseMediaTypeEnum = Enum.valueOf(BaseMediaTypeEnum.class, baseMediaType);
        boolean returnThis = false;
        switch (baseMediaTypeEnum) {
            case BOOK: {
                returnThis = scheduleController.startBookJob();
                break;
            }
            case GAME: {
                returnThis = scheduleController.startGameJob();
                break;
            }
            case MOVIE: {
                returnThis = scheduleController.startMovieJob();
                break;
            }
            case MUSIC: {
                returnThis = scheduleController.startMusicJob();
                break;
            }
            case TVSHOW: {
                returnThis = scheduleController.startTvShowJob();
                break;
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
     * @param httpRequest the request
     * @return the page response
     */
    public Promisable<HttpResponse> deleteTask(HttpRequest httpRequest) {
        String baseMediaType = httpRequest.getPathParameter("taskType").toUpperCase();
        BaseMediaTypeEnum baseMediaTypeEnum = Enum.valueOf(BaseMediaTypeEnum.class, baseMediaType);
        boolean returnThis = false;
        switch (baseMediaTypeEnum) {
            case BOOK: {
                returnThis = scheduleController.stopBookJob();
                break;
            }
            case GAME: {
                returnThis = scheduleController.stopGameJob();
                break;
            }
            case MOVIE: {
                returnThis = scheduleController.stopMovieJob();
                break;
            }
            case MUSIC: {
                returnThis = scheduleController.stopMusicJob();
                break;
            }
            case TVSHOW: {
                returnThis = scheduleController.stopTvShowJob();
                break;
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
     * @param httpRequest the request
     * @return the response page
     */
    @NotNull
    public Promisable<HttpResponse> optionsTask(@NotNull HttpRequest httpRequest) {
        return HttpResponse.ok200().withHeader(HttpHeaders.ALLOW, HttpHeaderValue.of("GET, PUT, DELETE"));
    }
}
