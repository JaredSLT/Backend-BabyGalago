package tech.tresearchgroup.babygalago.view.pages;

import tech.tresearchgroup.babygalago.controller.controllers.QueueEntityController;
import tech.tresearchgroup.babygalago.view.components.HeadComponent;
import tech.tresearchgroup.babygalago.view.components.SideBarComponent;
import tech.tresearchgroup.babygalago.view.components.TopBarComponent;
import tech.tresearchgroup.palila.controller.components.EditableScrollingComponent;
import tech.tresearchgroup.palila.model.enums.PermissionGroupEnum;
import tech.tresearchgroup.palila.view.RenderablePage;
import tech.tresearchgroup.sao.model.GlobalSearchResultEntity;

import java.util.LinkedList;
import java.util.List;

import static j2html.TagCreator.*;

public class SearchPage implements RenderablePage {
    /**
     * Renders the page
     *
     * @param loggedIn                 whether the user is logged in
     * @param globalSearchResultEntity the search result
     * @param timeTaken                the amount in ms that was taken to run the search
     * @param size                     the card size
     * @param unreadCount              the number of unread notifications
     * @param permissionGroupEnum      the permission group which the user belongs to
     * @param serverName               the name of the server
     * @param isEnableUpload           if file upload is enabled
     * @param isMovieLibraryEnable     if the movie library is enabled
     * @param isTvShowLibraryEnable    if the tv show library is enabled
     * @param isGameLibraryEnable      if the game library is enabled
     * @param isMusicLibraryEnable     if the music library is enabled
     * @param isBookLibraryEnable      if the book library is enabled
     * @return the page as a string
     */
    public String render(boolean loggedIn,
                         GlobalSearchResultEntity globalSearchResultEntity,
                         long timeTaken,
                         int size,
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
                        label("Search results for: SEARCH").withClass("overviewLabel"),
                        br(),
                        br(),
                        each(globalSearchResultEntity.getSearchResultList(), searchResultEntity ->
                            html(
                                EditableScrollingComponent.render(
                                    false,
                                    searchResultEntity.getName(),
                                    searchResultEntity.getList(),
                                    "/add/" + searchResultEntity.getClass().getSimpleName().toLowerCase(),
                                    size),
                                br()
                            )
                        ),
                        label("Time taken: " + timeTaken + "ms").withClass("subLabel"),
                        br(),
                        br(),
                        br()
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
                                loggedIn,
                                ResultEntity,
                                timeTaken,
                                settingsController.getCardWidth(extendedUserEntity.getUserSettings()),
                                notificationEntityController.getNumberOfUnread(extendedUserEntity),
                                extendedUserEntity.getPermissionGroup(),
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
