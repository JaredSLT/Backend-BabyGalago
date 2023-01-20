package tech.tresearchgroup.babygalago.view.pages;

import lombok.AllArgsConstructor;
import tech.tresearchgroup.babygalago.controller.controllers.QueueEntityController;
import tech.tresearchgroup.babygalago.view.components.HeadComponent;
import tech.tresearchgroup.babygalago.view.components.SideBarComponent;
import tech.tresearchgroup.babygalago.view.components.TopBarComponent;
import tech.tresearchgroup.cao.controller.GenericCAO;
import tech.tresearchgroup.palila.model.enums.PermissionGroupEnum;
import tech.tresearchgroup.palila.view.RenderablePage;

import java.util.LinkedList;
import java.util.List;

import static j2html.TagCreator.*;

@AllArgsConstructor
public class DisabledPage implements RenderablePage {
    /**
     * Renders the page
     * @param loggedIn whether the user is logged in
     * @param unreadCount the number of unread notifications
     * @param permissionGroupEnum the permission group which the user belongs to
     * @param serverName the name of the server
     * @param isEnableUpload if file upload is enabled
     * @param isMovieLibraryEnable if the movie library is enabled
     * @param isTvShowLibraryEnable if the tv show library is enabled
     * @param isGameLibraryEnable if the game library is enabled
     * @param isMusicLibraryEnable if the music library is enabled
     * @param isBookLibraryEnable if the book library is enabled
     * @return the page as a string
     */
    public String render(boolean loggedIn,
                         long unreadCount,
                         PermissionGroupEnum permissionGroupEnum,
                         String serverName,
                         boolean isEnableUpload,
                         boolean isMovieLibraryEnable,
                         boolean isTvShowLibraryEnable,
                         boolean isGameLibraryEnable,
                         boolean isMusicLibraryEnable,
                         boolean isBookLibraryEnable,
                         GenericCAO genericCAO) {
        return document(
            html(
                HeadComponent.render(serverName, genericCAO),
                TopBarComponent.render(unreadCount, QueueEntityController.getQueueSize(), loggedIn, permissionGroupEnum, isEnableUpload),
                SideBarComponent.render(
                    loggedIn,
                    isMovieLibraryEnable,
                    isTvShowLibraryEnable,
                    isGameLibraryEnable,
                    isMusicLibraryEnable,
                    isBookLibraryEnable,
                    genericCAO
                ),
                body(
                    div(
                        div(
                            label("This section has been disabled").withClass("subLabel verticalCenter")
                        )
                    ).withClass("body")
                )
            )
        );
    }

    /**
     * Renders out the page with dummy data
     * @return the page as a string
     */
    @Override
    public List<String> render() {
        List<String> pages = new LinkedList<>();
        /*
        pages.add(
            render(
            true,
            0,
            PermissionGroupEnum.ADMINISTRATOR,
            "Baby galago server",
            true,
            true,
            true,
            true,
            true,
            true
            )
        );*/
        return pages;
    }
}
