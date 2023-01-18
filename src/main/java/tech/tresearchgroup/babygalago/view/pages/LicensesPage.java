package tech.tresearchgroup.babygalago.view.pages;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import tech.tresearchgroup.babygalago.controller.SettingsController;
import tech.tresearchgroup.babygalago.controller.controllers.NotificationEntityController;
import tech.tresearchgroup.babygalago.controller.controllers.QueueEntityController;
import tech.tresearchgroup.babygalago.view.components.HeadComponent;
import tech.tresearchgroup.babygalago.view.components.SideBarComponent;
import tech.tresearchgroup.babygalago.view.components.TopBarComponent;
import tech.tresearchgroup.palila.model.enums.PermissionGroupEnum;
import tech.tresearchgroup.schemas.galago.entities.ExtendedUserEntity;

import java.sql.SQLException;

import static j2html.TagCreator.*;

@AllArgsConstructor
public class LicensesPage {
    private final SettingsController settingsController;
    private final NotificationEntityController notificationEntityController;

    public byte @NotNull [] render(boolean loggedIn, ExtendedUserEntity userEntity) throws SQLException {
        PermissionGroupEnum permissionGroupEnum = PermissionGroupEnum.ALL;
        if (userEntity != null) {
            permissionGroupEnum = userEntity.getPermissionGroup();
        }
        return document(
            html(
                HeadComponent.render(settingsController.getServerName()),
                TopBarComponent.render(notificationEntityController.getNumberOfUnread(userEntity), QueueEntityController.getQueueSize(), loggedIn, permissionGroupEnum, settingsController.isEnableUpload()),
                SideBarComponent.render(loggedIn,
                    settingsController.isMovieLibraryEnable(),
                    settingsController.isTvShowLibraryEnable(),
                    settingsController.isGameLibraryEnable(),
                    settingsController.isMusicLibraryEnable(),
                    settingsController.isBookLibraryEnable()),
                body(
                    div(
                        label("Technologies used:").withClass("overviewLabel"),
                        br(),
                        br(),

                        a("SpectreCSS").withHref("https://picturepan2.github.io/spectre/"),
                        text(" - "),
                        a("License").withHref("https://github.com/picturepan2/spectre/blob/master/LICENSE"),
                        br(),

                        a("J2HTML").withHref("https://j2html.com/"),
                        text(" - "),
                        a("License").withHref("https://github.com/tipsy/j2html/blob/master/LICENSE"),
                        br(),

                        a("MariaDB").withHref("https://mariadb.org/"),
                        text(" - "),
                        a("License").withHref("https://mariadb.com/kb/en/licensing-faq/"),
                        br(),

                        a("SQLite").withHref("https://www.sqlite.org/index.html"),
                        text(" - "),
                        a("License").withHref("https://www.sqlite.org/copyright.html"),
                        br(),

                        a("HikariCP").withHref("https://github.com/brettwooldridge/HikariCP"),
                        text(" - "),
                        a("License").withHref("https://github.com/brettwooldridge/HikariCP/blob/dev/LICENSE"),
                        br(),

                        a("Lombok").withHref("https://projectlombok.org/"),
                        text(" - "),
                        a("License").withHref("https://github.com/projectlombok/lombok/blob/master/LICENSE"),
                        br(),

                        a("GSON").withHref("https://github.com/google/gson"),
                        text(" - "),
                        a("License").withHref("https://github.com/google/gson/blob/master/LICENSE"),
                        br(),

                        a("Retrofit").withHref("https://square.github.io/retrofit/"),
                        text(" - "),
                        a("License").withHref("https://github.com/square/retrofit/blob/master/LICENSE.txt"),
                        br(),

                        a("ActiveJ").withHref("https://activej.io/"),
                        text(" - "),
                        a("License").withHref("https://github.com/activej/activej/blob/master/LICENSE"),
                        br(),

                        a("Java-JWT").withHref("https://github.com/auth0/java-jwt"),
                        text(" - "),
                        a("License").withHref("https://github.com/auth0/java-jwt/blob/master/LICENSE"),
                        br(),

                        a("BouncyCastle").withHref("https://www.bouncycastle.org/java.html"),
                        text(" - "),
                        a("License").withHref("https://www.bouncycastle.org/license.html"),
                        br(),

                        a("Brotli4j").withHref("https://github.com/hyperxpro/Brotli4j"),
                        text(" - "),
                        a("License").withHref("https://github.com/hyperxpro/Brotli4j/blob/main/LICENSE"),
                        br(),

                        a("Caffeine").withHref("https://github.com/ben-manes/caffeine"),
                        text(" - "),
                        a("License").withHref("https://github.com/ben-manes/caffeine/blob/master/LICENSE"),
                        br(),

                        a("MeiliSearch").withHref("https://github.com/meilisearch/meilisearch"),
                        text(" - "),
                        a("License").withHref("https://github.com/meilisearch/meilisearch/blob/main/LICENSE"),
                        br(),

                        a("Thumbnailator").withHref("https://github.com/coobird/thumbnailator"),
                        text(" - "),
                        a("License").withHref("https://github.com/coobird/thumbnailator/blob/master/LICENSE"),
                        br(),

                        a("Quartz").withHref("https://github.com/quartz-scheduler/quartz"),
                        text(" - "),
                        a("License").withHref("https://github.com/quartz-scheduler/quartz/blob/master/docs/license.adoc"),
                        br(),

                        a("Junit5").withHref("https://github.com/junit-team/junit5"),
                        text(" - "),
                        a("License").withHref("https://github.com/junit-team/junit5/blob/main/LICENSE.md"),
                        br(),

                        a("Palila").withHref("https://github.com/ThorneResearchGroup/Palila"),
                        text(" - "),
                        a("License").withHref("https://www.gnu.org/licenses/gpl-3.0.en.html"),
                        br(),
                        br(),
                        br()
                    ).withClass("body")
                )
            )
        ).getBytes();
    }
}
