package tech.tresearchgroup.babygalago.view.pages;

import j2html.tags.specialized.TrTag;
import tech.tresearchgroup.babygalago.controller.controllers.QueueEntityController;
import tech.tresearchgroup.babygalago.view.components.BulkActionsComponent;
import tech.tresearchgroup.babygalago.view.components.HeadComponent;
import tech.tresearchgroup.babygalago.view.components.SideBarComponent;
import tech.tresearchgroup.babygalago.view.components.TopBarComponent;
import tech.tresearchgroup.palila.controller.components.PaginationComponent;
import tech.tresearchgroup.palila.controller.components.SelectCheckboxComponent;
import tech.tresearchgroup.palila.model.enums.PermissionGroupEnum;
import tech.tresearchgroup.palila.view.RenderablePage;
import tech.tresearchgroup.schemas.galago.entities.NotificationEntity;

import java.util.LinkedList;
import java.util.List;

import static j2html.TagCreator.*;

public class NotificationsPage implements RenderablePage {
    /**
     * Generates a notification item
     *
     * @param notificationEntity the entity to render
     * @return the rendered notification as a TrTag
     */
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

    /**
     * Renders the page
     *
     * @param currentPage            the current page number
     * @param maxPage                the maximum page number
     * @param notificationEntityList the list of notification entities to render
     * @param loggedIn               whether the user is logged in
     * @param unreadCount            the number of unread notifications
     * @param permissionGroupEnum    the permission group which the user belongs to
     * @param serverName             the name of the server
     * @param isEnableUpload         if file upload is enabled
     * @param isMovieLibraryEnable   if the movie library is enabled
     * @param isTvShowLibraryEnable  if the tv show library is enabled
     * @param isGameLibraryEnable    if the game library is enabled
     * @param isMusicLibraryEnable   if the music library is enabled
     * @param isBookLibraryEnable    if the book library is enabled
     * @return the page as a string
     */
    public String render(int currentPage,
                         Long maxPage,
                         List<NotificationEntity> notificationEntityList,
                         boolean loggedIn,
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
        );
    }

    /**
     * Renders out the page with dummy data
     *
     * @return the page as a string
     */
    @Override
    public List<String> render() {
        /*try {
            return render(
                    page,
                    maxPage,
                    notificationEntityList,
                    loggedIn,
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
        return new LinkedList<>();
    }
}
