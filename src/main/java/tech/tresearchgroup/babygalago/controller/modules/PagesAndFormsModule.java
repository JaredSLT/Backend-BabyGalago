package tech.tresearchgroup.babygalago.controller.modules;

import io.activej.inject.annotation.Provides;
import io.activej.inject.module.AbstractModule;
import tech.tresearchgroup.babygalago.controller.SettingsController;
import tech.tresearchgroup.babygalago.controller.controllers.NotificationEntityController;
import tech.tresearchgroup.babygalago.view.pages.*;

public class PagesAndFormsModule extends AbstractModule {
    @Provides
    AboutPage aboutPage(SettingsController settingsController, NotificationEntityController notificationEntityController) {
        return new AboutPage(settingsController, notificationEntityController);
    }

    @Provides
    DeniedPage deniedPage(SettingsController settingsController, NotificationEntityController notificationEntityController) {
        return new DeniedPage(settingsController, notificationEntityController);
    }

    @Provides
    DisabledPage disabledPage(SettingsController settingsController, NotificationEntityController notificationEntityController) {
        return new DisabledPage(settingsController, notificationEntityController);
    }

    @Provides
    IndexPage indexPage(SettingsController settingsController, NotificationEntityController notificationEntityController) {
        return new IndexPage(settingsController, notificationEntityController);
    }

    @Provides
    LicensesPage licensesPage(SettingsController settingsController, NotificationEntityController notificationEntityController) {
        return new LicensesPage(settingsController, notificationEntityController);
    }

    @Provides
    LoginPage loginPage(SettingsController settingsController, NotificationEntityController notificationEntityController) {
        return new LoginPage(settingsController, notificationEntityController);
    }

    @Provides
    MaintenancePage maintenancePage(SettingsController settingsController) {
        return new MaintenancePage(settingsController);
    }

    @Provides
    NewsPage newsPage(SettingsController settingsController, NotificationEntityController notificationEntityController) {
        return new NewsPage(settingsController, notificationEntityController);
    }

    @Provides
    NotificationsPage notificationsPage(SettingsController settingsController, NotificationEntityController notificationEntityController) {
        return new NotificationsPage(settingsController, notificationEntityController);
    }

    @Provides
    ProfilePage profilePage(SettingsController settingsController, NotificationEntityController notificationEntityController) {
        return new ProfilePage(settingsController, notificationEntityController);
    }

    @Provides
    QueuePage queuePage(SettingsController settingsController, NotificationEntityController notificationEntityController) {
        return new QueuePage(settingsController, notificationEntityController);
    }

    @Provides
    RegisterPage registerPage(SettingsController settingsController, NotificationEntityController notificationEntityController) {
        return new RegisterPage(settingsController, notificationEntityController);
    }

    @Provides
    ResetPage resetPage(SettingsController settingsController, NotificationEntityController notificationEntityController) {
        return new ResetPage(settingsController, notificationEntityController);
    }

    @Provides
    SearchPage searchPage(SettingsController settingsController, NotificationEntityController notificationEntityController) {
        return new SearchPage(settingsController, notificationEntityController);
    }

    @Provides
    UploadPage uploadPage(SettingsController settingsController, NotificationEntityController notificationEntityController) {
        return new UploadPage(settingsController, notificationEntityController);
    }

    @Provides
    ViewPage viewPage(SettingsController settingsController, NotificationEntityController notificationEntityController) {
        return new ViewPage(settingsController, notificationEntityController);
    }

    @Provides
    PlayPage playBookPage(SettingsController settingsController, NotificationEntityController notificationEntityController) {
        return new PlayPage(settingsController, notificationEntityController);
    }

    @Provides
    SettingsPage settingsPage(NotificationEntityController notificationEntityController, SettingsController settingsController) {
        return new SettingsPage(notificationEntityController, settingsController);
    }

    @Provides
    UserSettingsPage userSettingsPage(SettingsController settingsController, NotificationEntityController notificationEntityController) {
        return new UserSettingsPage(settingsController, notificationEntityController);
    }

    @Provides
    tech.tresearchgroup.colobus.view.IndexPage colobusIndexPage(SettingsController settingsController) {
        return new tech.tresearchgroup.colobus.view.IndexPage(settingsController);
    }
}
