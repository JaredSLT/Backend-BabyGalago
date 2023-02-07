package tech.tresearchgroup.babygalago.view.pages;

import tech.tresearchgroup.babygalago.controller.controllers.QueueEntityController;
import tech.tresearchgroup.babygalago.view.components.HeadComponent;
import tech.tresearchgroup.babygalago.view.components.SideBarComponent;
import tech.tresearchgroup.babygalago.view.components.TopBarComponent;
import tech.tresearchgroup.palila.model.enums.PermissionGroupEnum;
import tech.tresearchgroup.palila.view.RenderablePage;

import java.util.LinkedList;
import java.util.List;

import static j2html.TagCreator.*;

public class LoginPage implements RenderablePage {
    /**
     * Renders the page
     *
     * @param isError               whether an error message should be displayed on the page
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
    public String render(boolean isError,
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
                            iff(isError,
                                div(
                                    div(
                                        text("Invalid credentials")
                                    ).withClass("toast toast-error"),
                                    br()
                                )
                            ),
                            form()
                                .withMethod("POST")
                                .withAction("/login")
                                .with(
                                    label("Login").withClass("overviewLabel"),
                                    br(),
                                    br(),
                                    label().withText("Username: "),
                                    input()
                                        .withType("text")
                                        .withId("username")
                                        .withName("username"),
                                    br(),
                                    br(),
                                    label().withText("Password: "),
                                    input()
                                        .withType("password")
                                        .withId("password")
                                        .withName("password"),
                                    br(),
                                    br(),
                                    button("Login").withType("submit").withId("login"),
                                    br(),
                                    a("Reset password").withHref("/reset").withId("reset"),
                                    br(),
                                    a().withHref("/register").withText("Register new account")
                                )
                        ).withClass("verticalCenter")
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
                isError,
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
