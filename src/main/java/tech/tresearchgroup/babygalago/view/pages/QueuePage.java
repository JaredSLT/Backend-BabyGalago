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
import tech.tresearchgroup.schemas.galago.entities.QueueEntity;
import tech.tresearchgroup.schemas.galago.enums.QueueStateEnum;

import java.sql.SQLException;
import java.util.List;

import static j2html.TagCreator.*;

@AllArgsConstructor
public class QueuePage {
    private final SettingsController settingsController;
    private final NotificationEntityController notificationEntityController;

    private static TrTag generateQueueEntity(QueueEntity queueEntity) {
        boolean isRunning = queueEntity.getStatus().equals(QueueStateEnum.RUNNING);
        return tr(
            td(
                iff(isRunning,
                    div().withClass("loading")
                )
            ),
            td(SelectCheckboxComponent.render("checkbox-" + queueEntity.getId())),
            td(String.valueOf(queueEntity.getId())),
            td(String.valueOf(queueEntity.getCreated())),
            td(String.valueOf(queueEntity.getUpdated())),
            td(String.valueOf(queueEntity.getStatus())),
            td(
                a(" Remove").withClass("btn btn-link fa fa-trash").withHref("/news")
            )
        ).withClass("active");
    }

    public byte @NotNull [] render(boolean loggedIn, int currentPage, long maxPage, List<QueueEntity> queueEntityList, ExtendedUserEntity userEntity) throws SQLException {
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
                        br(),
                        BulkActionsComponent.render("queue"),
                        div(
                            label("Process queue").withClass("overviewLabel"),
                            br(),
                            br(),
                            table(
                                thead(
                                    tr(
                                        th(),
                                        th(),
                                        th("ID"),
                                        th("Added"),
                                        th("Updated"),
                                        th("Status"),
                                        th("Actions")
                                    )
                                ),
                                tbody(
                                    each(queueEntityList, QueuePage::generateQueueEntity)
                                )
                            ).withClass("table")
                        ).withClass("container"),
                        div().withClass("divider"),
                        PaginationComponent.render(currentPage, maxPage, "/queue")
                    ).withClass("body")
                )
            )
        ).getBytes();
    }
}
