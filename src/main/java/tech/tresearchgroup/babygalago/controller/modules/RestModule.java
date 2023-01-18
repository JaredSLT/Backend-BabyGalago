package tech.tresearchgroup.babygalago.controller.modules;


import com.google.gson.Gson;
import io.activej.inject.annotation.Provides;
import io.activej.inject.module.AbstractModule;
import tech.tresearchgroup.babygalago.controller.SettingsController;
import tech.tresearchgroup.babygalago.controller.controllers.UserSettingsEntityController;
import tech.tresearchgroup.babygalago.controller.endpoints.AssetEndpointController;
import tech.tresearchgroup.babygalago.controller.endpoints.LoginEndpointsController;
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
import tech.tresearchgroup.babygalago.view.pages.UserSettingsPage;
import tech.tresearchgroup.colobus.controller.IndexController;

public class RestModule extends AbstractModule {
    @Provides
    RatingEndpoints ratingEndpoints(RatingEndpointsController ratingEndpointsController,
                                    SettingsController settingsController) {
        return new RatingEndpoints(ratingEndpointsController, settingsController);
    }

    @Provides
    GeneralEndpoints generalEndpoints(GeneralEndpointsController generalEndpointsController,
                                      SettingsController settingsController) {
        return new GeneralEndpoints(
            generalEndpointsController,
            settingsController
        );
    }

    @Provides
    MediaTypeEndpoints mediaTypeEndpoints(MediaTypeEndpointsController mediaTypeEndpointsController,
                                          SettingsController settingsController) {
        return new MediaTypeEndpoints(
            settingsController,
            mediaTypeEndpointsController
        );
    }

    @Provides
    NewsEndpoints newsEndpoints(NewsEndpointsController newsEndpointsController,
                                SettingsController settingsController) {
        return new NewsEndpoints(newsEndpointsController, settingsController);
    }

    @Provides
    NotificationsEndpoints notificationsEndpoints(NotificationsEndpointsController notificationsEndpointsController,
                                                  SettingsController settingsController) {
        return new NotificationsEndpoints(notificationsEndpointsController, settingsController);
    }

    @Provides
    QueueEndpoints queueEndpoints(QueueEndpointsController queueEndpointsController) {
        return new QueueEndpoints(queueEndpointsController);
    }

    @Provides
    SettingsEndpoints settingsEndpoints(SettingsEndpointsController settingsEndpointsController,
                                        SettingsController settingsController) {
        return new SettingsEndpoints(settingsEndpointsController, settingsController);
    }

    @Provides
    UserEndpoints userEndpoints(UserEndpointsController userEndpointsController,
                                SettingsController settingsController,
                                SettingsEndpointsController settingsEndpointsController) {
        return new UserEndpoints(userEndpointsController, settingsController, settingsEndpointsController);
    }

    @Provides
    LoginEndpoints loginEndpoints(LoginEndpointsController loginEndpointsController,
                                  SettingsController settingsController, Gson gson) {
        return new LoginEndpoints(loginEndpointsController, settingsController, gson);
    }

    @Provides
    CRUDEndpoints addEndpoints(CRUDEndpointsController CRUDEndpointsController,
                               SettingsController settingsController) {
        return new CRUDEndpoints(CRUDEndpointsController, settingsController);
    }

    @Provides
    MainEndpoints mainEndpoints(MainEndpointsController mainEndpointsController,
                                IndexController indexController,
                                SettingsController settingsController,
                                LoginEndpointsController loginEndpointsController) {
        return new MainEndpoints(mainEndpointsController, indexController, settingsController, loginEndpointsController);
    }

    @Provides
    PlayEndpoints playEndpoints(PlayEndpointsController playEndpointsController,
                                SettingsController settingsController) {
        return new PlayEndpoints(playEndpointsController, settingsController);
    }

    @Provides
    AssetEndpoint assetEndpoint(AssetEndpointController assetEndpointController,
                                SettingsController settingsController) {
        return new AssetEndpoint(assetEndpointController, settingsController);
    }

    @Provides
    TaskEndpoints taskEndpoints(TaskEndpointsController taskEndpointsController) {
        return new TaskEndpoints(taskEndpointsController);
    }

    @Provides
    UIUserEndpoints uiUserEndpoints(UserSettingsEntityController userSettingsEntityController,
                                    SettingsController settingsController,
                                    UserSettingsPage userSettingsPage) {
        return new UIUserEndpoints(userSettingsEntityController, settingsController, userSettingsPage);
    }
}
