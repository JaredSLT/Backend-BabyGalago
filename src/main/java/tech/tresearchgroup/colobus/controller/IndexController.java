package tech.tresearchgroup.colobus.controller;

import io.activej.http.HttpRequest;
import io.activej.http.HttpResponse;
import io.activej.promise.Promisable;
import tech.tresearchgroup.babygalago.controller.SettingsController;
import tech.tresearchgroup.babygalago.controller.controllers.ExtendedUserEntityController;
import tech.tresearchgroup.babygalago.controller.controllers.NotificationEntityController;
import tech.tresearchgroup.colobus.view.IndexPage;
import tech.tresearchgroup.palila.controller.BasicController;
import tech.tresearchgroup.palila.model.enums.PermissionGroupEnum;
import tech.tresearchgroup.schemas.galago.entities.ExtendedUserEntity;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public class IndexController extends BasicController {
    private final ExtendedUserEntityController extendedUserEntityController;
    private final IndexPage indexPage;
    private final NotificationEntityController notificationEntityController;
    private final SettingsController settingsController;

    public IndexController(ExtendedUserEntityController extendedUserEntityController,
                           IndexPage indexPage,
                           NotificationEntityController notificationEntityController,
                           SettingsController settingsController) {
        this.extendedUserEntityController = extendedUserEntityController;
        this.indexPage = indexPage;
        this.notificationEntityController = notificationEntityController;
        this.settingsController = settingsController;
    }

    public Promisable<HttpResponse> index(HttpRequest httpRequest) throws IOException, SQLException, InvocationTargetException, IllegalAccessException, InstantiationException {
        ExtendedUserEntity userEntity = (ExtendedUserEntity) getUser(httpRequest, extendedUserEntityController);
        PermissionGroupEnum permissionGroupEnum = PermissionGroupEnum.ALL;
        if (userEntity != null) {
            permissionGroupEnum = userEntity.getPermissionGroup();
        }
        boolean loggedIn = verifyApiKey(httpRequest);
        return ok(
            indexPage.render(
                loggedIn,
                notificationEntityController.getNumberOfUnread(userEntity),
                permissionGroupEnum,
                settingsController.getServerName(),
                settingsController.isEnableUpload(),
                settingsController.isMovieLibraryEnable(),
                settingsController.isTvShowLibraryEnable(),
                settingsController.isGameLibraryEnable(),
                settingsController.isMusicLibraryEnable(),
                settingsController.isBookLibraryEnable()
            )
        );
    }
}
