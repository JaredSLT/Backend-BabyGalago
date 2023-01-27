package tech.tresearchgroup.babygalago.view.pages;

import j2html.tags.specialized.TrTag;
import lombok.AllArgsConstructor;
import tech.tresearchgroup.babygalago.controller.controllers.QueueEntityController;
import tech.tresearchgroup.babygalago.view.components.BulkActionsComponent;
import tech.tresearchgroup.babygalago.view.components.HeadComponent;
import tech.tresearchgroup.babygalago.view.components.SideBarComponent;
import tech.tresearchgroup.babygalago.view.components.TopBarComponent;
import tech.tresearchgroup.palila.controller.components.PaginationComponent;
import tech.tresearchgroup.palila.controller.components.SelectCheckboxComponent;
import tech.tresearchgroup.palila.model.enums.PermissionGroupEnum;
import tech.tresearchgroup.palila.view.RenderablePage;
import tech.tresearchgroup.schemas.galago.entities.QueueEntity;
import tech.tresearchgroup.schemas.galago.enums.QueueStateEnum;

import java.util.LinkedList;
import java.util.List;

import static j2html.TagCreator.*;

@AllArgsConstructor
public class QueuePage implements RenderablePage {
    /**
     * Generates a queue entity item
     *
     * @param queueEntity the rendered queue entity as a TrTag
     * @return the queue entity
     */
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

    /**
     * Renders the page
     *
     * @param loggedIn              whether the user is logged in
     * @param currentPage           the current page number
     * @param maxPage               the max number of viewable pages
     * @param queueEntityList       the entities to be displayed
     * @param unreadCount           the number of unread notifications
     * @param permissionGroupEnum   the permission group which the user belongs to
     * @param serverName            the name of the server
     * @param isEnableUpload        if file upload is enabled
     * @param isMovieLibraryEnable  if the movie library is enabled
     * @param isTvShowLibraryEnable if the tv show library is enabled
     * @param isGameLibraryEnable   if the game library is enabled
     * @param isMusicLibraryEnable  if the music library is enabled
     * @param isBookLibraryEnable   if the book library is enabled
     * @return the page as a string
     */
    public String render(boolean loggedIn,
                         int currentPage,
                         long maxPage,
                         List<QueueEntity> queueEntityList,
                         long unreadCount,
                         PermissionGroupEnum permissionGroupEnum,
                         String serverName,
                         boolean isEnableUpload,
                         boolean isMovieLibraryEnable,
                         boolean isTvShowLibraryEnable,
                         boolean isGameLibraryEnable,
                         boolean isMusicLibraryEnable,
                         boolean isBookLibraryEnable) {
        return document(
            html(
                HeadComponent.render(serverName),
                TopBarComponent.render(unreadCount, QueueEntityController.getQueueSize(), loggedIn, permissionGroupEnum, isEnableUpload),
                SideBarComponent.render(
                    loggedIn,
                    isMovieLibraryEnable,
                    isTvShowLibraryEnable,
                    isGameLibraryEnable,
                    isMusicLibraryEnable,
                    isBookLibraryEnable
                ),
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
        );
    }

    /**
     * Renders out the page with dummy data
     *
     * @return the page as a string
     */
    @Override
    public List<String> render() {
        List<String> pages = new LinkedList<>();
        /*try {
            return render(
                loggedIn,
                page,
                maxPage,
                queueEntityList,
                notificationEntityController.getNumberOfUnread(userEntity),
                userEntity.getPermissionGroup(),
                settingsController.getServerName(),
                settingsController.isEnableUpload(),
                settingsController.isMovieLibraryEnable(),
                settingsController.isTvShowLibraryEnable(),
                settingsController.isGameLibraryEnable(),
                settingsController.isMusicLibraryEnable(),
                settingsController.isBookLibraryEnable()
            );
        } catch (SQLException e) {
            return null;
        }*/
        return pages;
    }
}
