package tech.tresearchgroup.babygalago.controller.endpoints.ui;

import io.activej.http.HttpRequest;
import io.activej.http.HttpResponse;
import j2html.tags.DomContent;
import lombok.AllArgsConstructor;
import tech.tresearchgroup.babygalago.controller.SettingsController;
import tech.tresearchgroup.babygalago.controller.controllers.MovieEntityController;
import tech.tresearchgroup.babygalago.controller.controllers.UserEntityController;
import tech.tresearchgroup.babygalago.controller.controllers.UserSettingsEntityController;
import tech.tresearchgroup.babygalago.controller.controllers.VideoFileEntityController;
import tech.tresearchgroup.babygalago.view.pages.PlayPage;
import tech.tresearchgroup.palila.controller.*;
import tech.tresearchgroup.palila.model.entities.VideoFileEntity;
import tech.tresearchgroup.palila.model.enums.PlaybackQualityEnum;
import tech.tresearchgroup.schemas.galago.entities.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Map;

@AllArgsConstructor
public class PlayEndpointsController extends BasicController {
    private final Map<String, GenericController> controllers;
    private final UserEntityController userEntityController;
    private final SettingsController settingsController;
    private final VideoFileEntityController videoFileEntityController;
    private final UserSettingsEntityController userSettingsEntityController;
    private final PlayPage playPage;

    public HttpResponse play(HttpRequest httpRequest) throws IOException, SQLException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        if (!settingsController.isBookLibraryEnable()) {
            return redirect("/disabled");
        }
        String mediaType = httpRequest.getPathParameter("mediaType");
        long id = Integer.parseInt(httpRequest.getPathParameter("id"));
        Class className = ReflectionMethods.findClass(mediaType, settingsController.getEntityPackages());
        if (className == null) {
            return error();
        }
        GenericController genericController = getController(className, controllers);
        Object object = genericController.readSecureResponse(id, httpRequest);
        boolean loggedIn = verifyApiKey(httpRequest);
        ExtendedUserEntity userEntity = (ExtendedUserEntity) getUser(httpRequest, userEntityController);
        Class objectClass = object.getClass();
        DomContent domContent;
        if (MovieEntity.class.equals(objectClass)) {
            MovieEntityController controller = (MovieEntityController) getController(MovieEntity.class, controllers);
            MovieEntity movieEntity = (MovieEntity) object;
            PlaybackQualityEnum playbackQualityEnum = PlaybackQualityEnum.ORIGINAL;
            if (userEntity.getUserSettings() != null) {
                playbackQualityEnum = settingsController.getDefaultPlaybackQuality((UserSettingsEntity) userSettingsEntityController.readSecureResponse(userEntity.getUserSettings().getId(), httpRequest));
            }
            VideoFileEntity videoFileEntity = controller.getVideo(movieEntity, videoFileEntityController, playbackQualityEnum, httpRequest);
            domContent = MediaPlayerController.getPlayer(videoFileEntity, "/v1/videofileentity/" + videoFileEntity.getId() + "/play");
        } else if (EpisodeEntity.class.equals(objectClass)) {
            return notImplemented();
        } else if (SongEntity.class.equals(objectClass)) {
            return notImplemented();
        } else if (BookEntity.class.equals(objectClass)) {
            return notImplemented();
        } else if (GameEntity.class.equals(objectClass)) {
            return notImplemented();
        } else {
            domContent = MediaPlayerController.getPlayer(object, "/v1/" + className.getSimpleName().toLowerCase() + "/" + id + "/play");
        }
        byte[] data = playPage.render(loggedIn, domContent, userEntity);
        byte[] compressed = CompressionController.compress(data);
        return okResponseCompressed(compressed);
    }
}
