package tech.tresearchgroup.colobus.controller;

import io.activej.http.HttpRequest;
import io.activej.http.HttpResponse;
import io.activej.promise.Promisable;
import lombok.AllArgsConstructor;
import tech.tresearchgroup.babygalago.controller.SettingsController;
import tech.tresearchgroup.babygalago.controller.controllers.ExtendedUserEntityController;
import tech.tresearchgroup.babygalago.controller.controllers.NotificationEntityController;
import tech.tresearchgroup.cao.controller.GenericCAO;
import tech.tresearchgroup.colobus.view.IndexPage;
import tech.tresearchgroup.palila.controller.BasicController;
import tech.tresearchgroup.schemas.galago.entities.ExtendedUserEntity;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

@AllArgsConstructor
public class IndexController extends BasicController {
    private final ExtendedUserEntityController extendedUserEntityController;
    private final IndexPage indexPage;
    private final NotificationEntityController notificationEntityController;
    private final SettingsController settingsController;
    private final GenericCAO genericCAO;

    public Promisable<HttpResponse> index(HttpRequest httpRequest) throws IOException, SQLException, InvocationTargetException, IllegalAccessException, InstantiationException {
        ExtendedUserEntity userEntity = (ExtendedUserEntity) getUser(httpRequest, extendedUserEntityController);
        boolean loggedIn = verifyApiKey(httpRequest);
        return ok(
            indexPage.render(
                loggedIn,
                notificationEntityController.getNumberOfUnread(userEntity),
                userEntity.getPermissionGroup(),
                settingsController.getServerName(),
                settingsController.isEnableUpload(),
                settingsController.isMovieLibraryEnable(),
                settingsController.isTvShowLibraryEnable(),
                settingsController.isGameLibraryEnable(),
                settingsController.isMusicLibraryEnable(),
                settingsController.isBookLibraryEnable(),
                genericCAO
            )
        );
    }
}
