package tech.tresearchgroup.babygalago.view.pages;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import tech.tresearchgroup.babygalago.controller.SettingsController;
import tech.tresearchgroup.babygalago.controller.controllers.NotificationEntityController;
import tech.tresearchgroup.babygalago.controller.controllers.QueueEntityController;
import tech.tresearchgroup.babygalago.view.components.BulkActionsComponent;
import tech.tresearchgroup.babygalago.view.components.HeadComponent;
import tech.tresearchgroup.babygalago.view.components.SideBarComponent;
import tech.tresearchgroup.babygalago.view.components.TopBarComponent;
import tech.tresearchgroup.palila.controller.components.PaginationComponent;
import tech.tresearchgroup.palila.controller.components.SelectCheckboxComponent;
import tech.tresearchgroup.palila.model.enums.PermissionGroupEnum;
import tech.tresearchgroup.schemas.galago.entities.ExtendedUserEntity;

import java.sql.SQLException;

import static j2html.TagCreator.*;

@AllArgsConstructor
public class NewsPage {
    private final SettingsController settingsController;
    private final NotificationEntityController notificationEntityController;

    public byte @NotNull [] render(boolean loggedIn, int currentPage, long maxPage, ExtendedUserEntity userEntity) throws SQLException {
        PermissionGroupEnum permissionGroupEnum = PermissionGroupEnum.ALL;
        if (userEntity != null) {
            permissionGroupEnum = userEntity.getPermissionGroup();
        }
        int newsId = 1;
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
                        br(),
                        BulkActionsComponent.render("news"),
                        div(
                            label("News").withClass("overviewLabel"),
                            br(),
                            br(),
                            table(
                                thead(
                                    tr(
                                        th(),
                                        th("Status"),
                                        th("Added timestamp"),
                                        th("Title"),
                                        th("Preview"),
                                        th("Actions")
                                    )
                                ),
                                tbody(
                                    tr(
                                        td(SelectCheckboxComponent.render("checkbox-" + newsId)),
                                        td(
                                            i().withClass("far fa-envelope-open fa-lg")
                                        ),
                                        td("14 October 1994"),
                                        td("Update"),
                                        td("Baby Galago has been updated to include some awesome feature"),
                                        td(
                                            a(" View").withClass("btn btn-link far fa-eye").withHref("/news"),
                                            a(" Remove").withClass("btn btn-link fa fa-trash").withHref("/news"),
                                            a(" Mark unread").withClass("btn btn-link fas fa-book").withHref("/news")
                                        )
                                    ).withClass("active"),
                                    tr(
                                        td(SelectCheckboxComponent.render("checkbox-" + newsId)),
                                        td(
                                            i().withClass("far fa-envelope fa-lg")
                                        ),
                                        td("14 October 1994"),
                                        td("Update"),
                                        td("Baby Galago has been updated to include some awesome feature"),
                                        td(
                                            a(" View").withClass("btn btn-link far fa-eye").withHref("/news"),
                                            a(" Remove").withClass("btn btn-link fa fa-trash").withHref("/news"),
                                            a(" Mark read").withClass("btn btn-link fas fa-book-open").withHref("/news")
                                        )
                                    ).withClass("active")
                                )
                            ).withClass("table")
                        ).withClass("container"),
                        div().withClass("divider"),
                        PaginationComponent.render(currentPage, maxPage, "/news")
                    ).withClass("body")
                )
            )
        ).getBytes();
    }
}
