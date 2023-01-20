package tech.tresearchgroup.babygalago.controller.modules;


import io.activej.inject.annotation.Provides;
import io.activej.inject.module.AbstractModule;
import tech.tresearchgroup.babygalago.controller.SettingsController;
import tech.tresearchgroup.babygalago.controller.controllers.UserSettingsEntityController;
import tech.tresearchgroup.babygalago.controller.endpoints.AssetEndpointController;
import tech.tresearchgroup.babygalago.controller.endpoints.LoginEndpointsController;
import tech.tresearchgroup.babygalago.controller.endpoints.SearchEndpointsController;
import tech.tresearchgroup.babygalago.controller.endpoints.api.*;
import tech.tresearchgroup.babygalago.controller.endpoints.ui.CRUDEndpointsController;
import tech.tresearchgroup.babygalago.controller.endpoints.ui.MainEndpointsController;
import tech.tresearchgroup.babygalago.controller.endpoints.ui.PlayEndpointsController;
import tech.tresearchgroup.babygalago.view.endpoints.AssetEndpoint;
import tech.tresearchgroup.babygalago.view.endpoints.api.*;
import tech.tresearchgroup.babygalago.view.endpoints.ui.CRUDEndpoints;
import tech.tresearchgroup.babygalago.view.endpoints.ui.MainEndpoints;
import tech.tresearchgroup.babygalago.view.endpoints.ui.PlayEndpoints;
import tech.tresearchgroup.babygalago.view.endpoints.ui.UIUserEndpoints;
import tech.tresearchgroup.colobus.controller.IndexController;

/**
 * This class is used for dependency injection. It sets up the rest endpoints to be used elsewhere
 */
public class RestModule extends AbstractModule {
    @Provides
    RatingEndpoints ratingEndpoints(RatingEndpointsController ratingEndpointsController) {
        return new RatingEndpoints(ratingEndpointsController);
    }

    @Provides
    GeneralEndpoints generalEndpoints(GeneralEndpointsController generalEndpointsController,
                                      SearchEndpointsController searchEndpointsController) {
        return new GeneralEndpoints(
            generalEndpointsController,
            searchEndpointsController
        );
    }

    @Provides
    MediaTypeEndpoints mediaTypeEndpoints(MediaTypeEndpointsController mediaTypeEndpointsController,
                                          SearchEndpointsController searchEndpointsController) {
        return new MediaTypeEndpoints(
            mediaTypeEndpointsController,
            searchEndpointsController
        );
    }

    @Provides
    NewsEndpoints newsEndpoints(NewsEndpointsController newsEndpointsController) {
        return new NewsEndpoints(
            newsEndpointsController
        );
    }

    @Provides
    NotificationsEndpoints notificationsEndpoints(NotificationsEndpointsController notificationsEndpointsController) {
        return new NotificationsEndpoints(
            notificationsEndpointsController
        );
    }

    @Provides
    QueueEndpoints queueEndpoints(QueueEndpointsController queueEndpointsController) {
        return new QueueEndpoints(
            queueEndpointsController
        );
    }

    @Provides
    SettingsEndpoints settingsEndpoints(SettingsEndpointsController settingsEndpointsController) {
        return new SettingsEndpoints(
            settingsEndpointsController
        );
    }

    @Provides
    UserEndpoints userEndpoints(UserEndpointsController userEndpointsController,
                                SettingsEndpointsController settingsEndpointsController) {
        return new UserEndpoints(
            userEndpointsController,
            settingsEndpointsController
        );
    }

    @Provides
    LoginEndpoints loginEndpoints(LoginEndpointsController loginEndpointsController,
                                  SettingsController settingsController) {
        return new LoginEndpoints(
            loginEndpointsController
        );
    }

    @Provides
    CRUDEndpoints addEndpoints(CRUDEndpointsController CRUDEndpointsController) {
        return new CRUDEndpoints(CRUDEndpointsController);
    }

    @Provides
    MainEndpoints mainEndpoints(MainEndpointsController mainEndpointsController,
                                IndexController indexController,
                                SearchEndpointsController searchEndpointsController) {
        return new MainEndpoints(
            mainEndpointsController,
            indexController,
            searchEndpointsController
        );
    }

    @Provides
    PlayEndpoints playEndpoints(PlayEndpointsController playEndpointsController) {
        return new PlayEndpoints(
            playEndpointsController
        );
    }

    @Provides
    AssetEndpoint assetEndpoint(AssetEndpointController assetEndpointController) {
        return new AssetEndpoint(assetEndpointController);
    }

    @Provides
    TaskEndpoints taskEndpoints(TaskEndpointsController taskEndpointsController) {
        return new TaskEndpoints(taskEndpointsController);
    }

    @Provides
    UIUserEndpoints uiUserEndpoints(UserSettingsEntityController userSettingsEntityController) {
        return new UIUserEndpoints(
            userSettingsEntityController
        );
    }
}
