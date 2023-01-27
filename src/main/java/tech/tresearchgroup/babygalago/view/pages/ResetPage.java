package tech.tresearchgroup.babygalago.view.pages;

import lombok.AllArgsConstructor;
import tech.tresearchgroup.babygalago.controller.controllers.QueueEntityController;
import tech.tresearchgroup.babygalago.view.components.HeadComponent;
import tech.tresearchgroup.babygalago.view.components.SideBarComponent;
import tech.tresearchgroup.babygalago.view.components.TopBarComponent;
import tech.tresearchgroup.palila.model.enums.PermissionGroupEnum;
import tech.tresearchgroup.palila.view.RenderablePage;

import java.util.LinkedList;
import java.util.List;

import static j2html.TagCreator.*;

@AllArgsConstructor
public class ResetPage implements RenderablePage {
    /**
     * Renders the page
     *
     * @param loggedIn              whether the user is logged in
     * @param isError               whether an error should be displayed
     * @param isSuccess             whether the page was successfully submitted
     * @param wasConfirmed          whether the reset was confirmed
     * @param confirmation          the confirmation
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
                         boolean isError,
                         boolean isSuccess,
                         boolean wasConfirmed,
                         String confirmation,
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
                            label("Reset password").withClass("overviewLabel"),
                            br(),
                            br(),
                            iff(isError,
                                div(
                                    div(
                                        text("Invalid credentials")
                                    ).withClass("toast toast-error"),
                                    br()
                                )
                            ),
                            iff(isSuccess,
                                div(
                                    div(
                                        text("Successfully reset password. You may now login")
                                    ).withClass("toast toast-success"),
                                    br()
                                )
                            ),
                            iffElse(!isSuccess,
                                form(
                                    input().withValue(confirmation).isHidden().withId("confirmation").withName("confirmation"),
                                    iffElse(!wasConfirmed,
                                        span(
                                            label().withText("Email: "),
                                            input().withType("text").withName("email").withId("email"),
                                            br(),
                                            br()
                                        ),
                                        span(
                                            label().withText("New password: "),
                                            input().withType("password").withName("password").withId("password"),
                                            br(),
                                            br(),
                                            label().withText("New password again: "),
                                            input().withType("password").withName("passwordConfirm").withId("passwordConfirm"),
                                            br(),
                                            br()
                                        )
                                    ),
                                    button("Reset").withType("submit").withId("login")
                                )
                                    .withMethod("POST")
                                    .withAction("/reset"),
                                a("Login").withHref("/login")
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
        List<String> pages = new LinkedList<>();
        /*try {
            return render(
                loggedIn,
                isError,
                isSuccess,
                wasConfirmed,
                confirmationData,
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
