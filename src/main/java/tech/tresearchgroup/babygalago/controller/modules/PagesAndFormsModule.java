package tech.tresearchgroup.babygalago.controller.modules;

import io.activej.inject.annotation.Provides;
import io.activej.inject.module.AbstractModule;
import tech.tresearchgroup.babygalago.view.pages.*;

/**
 * This class is used for dependency injection. It sets up pages and forms to be used elsewhere
 */
public class PagesAndFormsModule extends AbstractModule {
    @Provides
    AboutPage aboutPage() {
        return new AboutPage();
    }

    @Provides
    DeniedPage deniedPage() {
        return new DeniedPage();
    }

    @Provides
    DisabledPage disabledPage() {
        return new DisabledPage();
    }

    @Provides
    IndexPage indexPage() {
        return new IndexPage();
    }

    @Provides
    LicensesPage licensesPage() {
        return new LicensesPage();
    }

    @Provides
    LoginPage loginPage() {
        return new LoginPage();
    }

    @Provides
    MaintenancePage maintenancePage() {
        return new MaintenancePage();
    }

    @Provides
    NewsPage newsPage() {
        return new NewsPage();
    }

    @Provides
    NotificationsPage notificationsPage() {
        return new NotificationsPage();
    }

    @Provides
    ProfilePage profilePage() {
        return new ProfilePage();
    }

    @Provides
    QueuePage queuePage() {
        return new QueuePage();
    }

    @Provides
    RegisterPage registerPage() {
        return new RegisterPage();
    }

    @Provides
    ResetPage resetPage() {
        return new ResetPage();
    }

    @Provides
    SearchPage searchPage() {
        return new SearchPage();
    }

    @Provides
    UploadPage uploadPage() {
        return new UploadPage();
    }

    @Provides
    ViewPage viewPage() {
        return new ViewPage();
    }

    @Provides
    PlayPage playBookPage() {
        return new PlayPage();
    }

    @Provides
    SettingsPage settingsPage() {
        return new SettingsPage();
    }

    @Provides
    UserSettingsPage userSettingsPage() {
        return new UserSettingsPage();
    }

    @Provides
    tech.tresearchgroup.colobus.view.IndexPage colobusIndexPage() {
        return new tech.tresearchgroup.colobus.view.IndexPage();
    }

    @Provides
    EntityPage entityPage() {
        return new EntityPage();
    }

    @Provides
    ErrorPage errorPage() {
        return new ErrorPage();
    }

    @Provides
    NotFoundPage notFoundPage() {
        return new NotFoundPage();
    }

    @Provides
    UnderConstructionPage underConstructionPage() {
        return new UnderConstructionPage();
    }

    @Provides
    EmptySearchPage emptySearchPage() {
        return new EmptySearchPage();
    }
}
