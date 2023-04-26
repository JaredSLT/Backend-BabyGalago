package tech.tresearchgroup.babygalago.controller.endpoints.ui;

import io.activej.http.HttpRequest;
import io.activej.http.HttpResponse;
import io.activej.promise.Promisable;
import j2html.tags.DomContent;
import tech.tresearchgroup.babygalago.controller.SettingsController;
import tech.tresearchgroup.babygalago.controller.controllers.*;
import tech.tresearchgroup.babygalago.view.pages.PlayPage;
import tech.tresearchgroup.palila.controller.*;
import tech.tresearchgroup.palila.model.entities.VideoFileEntity;
import tech.tresearchgroup.palila.model.enums.PlaybackQualityEnum;
import tech.tresearchgroup.palila.model.enums.ReturnType;
import tech.tresearchgroup.schemas.galago.entities.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Map;

public class PlayEndpointsController extends BasicController {
    private final Map<String, GenericController> controllers;
    private final ExtendedUserEntityController extendedUserEntityController;
    private final SettingsController settingsController;
    private final VideoFileEntityController videoFileEntityController;
    private final UserSettingsEntityController userSettingsEntityController;
    private final PlayPage playPage;
    private final NotificationEntityController notificationEntityController;

    public PlayEndpointsController(Map<String, GenericController> controllers,
                                   ExtendedUserEntityController extendedUserEntityController,
                                   SettingsController settingsController,
                                   VideoFileEntityController videoFileEntityController,
                                   UserSettingsEntityController userSettingsEntityController,
                                   PlayPage playPage,
                                   NotificationEntityController notificationEntityController) {
        this.controllers = controllers;
        this.extendedUserEntityController = extendedUserEntityController;
        this.settingsController = settingsController;
        this.videoFileEntityController = videoFileEntityController;
        this.userSettingsEntityController = userSettingsEntityController;
        this.playPage = playPage;
        this.notificationEntityController = notificationEntityController;
    }

    /**
     * Renders the player page for a media type
     *
     * @param httpRequest the request
     * @return the page
     * @throws IOException               if it fails
     * @throws SQLException              if it fails
     * @throws InvocationTargetException if it fails
     * @throws NoSuchMethodException     if it fails
     * @throws IllegalAccessException    if it fails
     * @throws InstantiationException    if it fails
     * @throws ClassNotFoundException    if it fails
     */
    public Promisable<HttpResponse> play(HttpRequest httpRequest) throws IOException, SQLException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        if (!settingsController.isBookLibraryEnable()) {
            return redirect("/disabled");
        }
        String mediaType = httpRequest.getPathParameter("mediaType");
        long id = Integer.parseInt(httpRequest.getPathParameter("id"));
        Class className = ReflectionMethods.findClass(mediaType, settingsController.getEntityPackages());
        if (className == null) {
            return redirect("/error");
        }
        GenericController genericController = getController(className, controllers);
        Object object = genericController.readSecureResponse(id, ReturnType.OBJECT, httpRequest);
        boolean loggedIn = verifyApiKey(httpRequest);
        ExtendedUserEntity userEntity = (ExtendedUserEntity) getUser(httpRequest, extendedUserEntityController);
        Class<? extends Object> objectClass = object.getClass();
        DomContent domContent;
        if (MovieEntity.class.equals(objectClass)) {
            MovieEntityController controller = (MovieEntityController) getController(MovieEntity.class, controllers);
            MovieEntity movieEntity = (MovieEntity) object;
            PlaybackQualityEnum playbackQualityEnum = PlaybackQualityEnum.ORIGINAL;
            if (userEntity.getUserSettings() != null) {
                playbackQualityEnum = settingsController.getDefaultPlaybackQuality((UserSettingsEntity) userSettingsEntityController.readSecureResponse(userEntity.getUserSettings().getId(), ReturnType.OBJECT, httpRequest));
            }
            VideoFileEntity videoFileEntity = controller.getVideo(movieEntity, videoFileEntityController, playbackQualityEnum, httpRequest);
            domContent = MediaPlayerController.getPlayer(videoFileEntity, "/v1/videofileentity/" + videoFileEntity.getId() + "/play");
        } else if (EpisodeEntity.class.equals(objectClass)) {
            return redirect("/underconstruction");
        } else if (SongEntity.class.equals(objectClass)) {
            return redirect("/underconstruction");
        } else if (BookEntity.class.equals(objectClass)) {
            return redirect("/underconstruction");
        } else if (GameEntity.class.equals(objectClass)) {
            return redirect("/underconstruction");
        } else {
            domContent = MediaPlayerController.getPlayer(object, "/v1/" + className.getSimpleName().toLowerCase() + "/" + id + "/play");
        }
        byte[] data = playPage.render(
            loggedIn,
            domContent,
            notificationEntityController.getNumberOfUnread(userEntity),
            userEntity.getPermissionGroup(),
            settingsController.getServerName(),
            settingsController.isEnableUpload(),
            settingsController.isMovieLibraryEnable(),
            settingsController.isTvShowLibraryEnable(),
            settingsController.isGameLibraryEnable(),
            settingsController.isMusicLibraryEnable(),
            settingsController.isBookLibraryEnable()
        ).getBytes();
        byte[] compressed = CompressionController.compress(data);
        return okResponseCompressed(compressed);
    }
}
