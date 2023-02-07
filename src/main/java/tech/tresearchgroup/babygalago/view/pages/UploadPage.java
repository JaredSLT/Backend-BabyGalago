package tech.tresearchgroup.babygalago.view.pages;

import tech.tresearchgroup.babygalago.controller.controllers.QueueEntityController;
import tech.tresearchgroup.babygalago.view.components.HeadComponent;
import tech.tresearchgroup.babygalago.view.components.SideBarComponent;
import tech.tresearchgroup.babygalago.view.components.TopBarComponent;
import tech.tresearchgroup.palila.controller.components.UploadComponent;
import tech.tresearchgroup.palila.model.enums.PermissionGroupEnum;
import tech.tresearchgroup.palila.view.RenderablePage;

import java.util.LinkedList;
import java.util.List;

import static j2html.TagCreator.*;

public class UploadPage implements RenderablePage {
    /**
     * Renders the page
     *
     * @param editable              whether the page is editable or not (inputs instead of labels)
     * @param loggedIn              whether the user is logged in
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
    public String render(boolean editable,
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
                script().withSrc("/assets/upload.js"),
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
                        label("Upload file to server").withClass("overviewLabel"),
                        br(),
                        br(),
                        br(),
                        form(
                            label("Upload to:"),
                            select(
                                option("Books").withValue("bookentity"),
                                option("Games").withValue("gameentity"),
                                option("Movies").withValue("movieentity"),
                                option("Music").withValue("musicentity"),
                                option("TV Shows").withValue("tvshowentity")
                            ).withName("mediaType").withId("mediaType"),
                            br(),
                            UploadComponent.render(editable, null, "file1")
                        ).withId("upload_form").withEnctype("multipart/form-data").withMethod("POST")
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
                    true,
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
        return pages;
    }
}
