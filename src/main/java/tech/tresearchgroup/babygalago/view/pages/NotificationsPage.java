package tech.tresearchgroup.babygalago.view.pages;

import j2html.tags.specialized.TrTag;
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
import tech.tresearchgroup.schemas.galago.entities.NotificationEntity;

import java.sql.SQLException;
import java.util.List;

import static j2html.TagCreator.*;

@AllArgsConstructor
public class NotificationsPage {
    private final SettingsController settingsController;
    private final NotificationEntityController notificationEntityController;

    private static TrTag generateNotification(NotificationEntity notificationEntity) {
        return tr(
            td(SelectCheckboxComponent.render("checkbox-" + notificationEntity.getId())),
            td(String.valueOf(notificationEntity.getNotificationErrorTypeEnum())),
            td(String.valueOf(notificationEntity.getCreated())),
            td(notificationEntity.getName()),
            td(notificationEntity.getBody()),
            td(
                a(" View").withClass("btn btn-link fa fa-eye").withHref("/"),
                a(" Delete").withClass("btn btn-link fa fa-trash").withHref("/delete/notification/" + notificationEntity.getId())
            )
        ).withClass("active");
    }

    public byte @NotNull [] render(int currentPage, Long maxPage, List<NotificationEntity> notificationEntityList, boolean loggedIn, ExtendedUserEntity userEntity) throws SQLException {
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
                        div(
                            br(),
                            BulkActionsComponent.render("notification"),
                            label("Notifications").withClass("overviewLabel"),
                            br(),
                            br(),
                            table(
                                thead(
                                    tr(
                                        th("Select"),
                                        th("Type"),
                                        th("Timestamp"),
                                        th("Name"),
                                        th("Description"),
                                        th("Actions")
                                    )
                                ),
                                tbody(
                                    each(notificationEntityList, NotificationsPage::generateNotification)
                                )
                            ).withClass("table")
                        ).withClass("container"),
                        div().withClass("divider"),
                        PaginationComponent.render(currentPage, maxPage, "/notifications")
                    ).withClass("body")
                )
            )
        ).getBytes();
    }
}
