package tech.tresearchgroup.babygalago.view.pages;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import tech.tresearchgroup.babygalago.controller.SettingsController;
import tech.tresearchgroup.babygalago.controller.controllers.NotificationEntityController;
import tech.tresearchgroup.babygalago.controller.controllers.QueueEntityController;
import tech.tresearchgroup.babygalago.view.components.HeadComponent;
import tech.tresearchgroup.babygalago.view.components.SideBarComponent;
import tech.tresearchgroup.babygalago.view.components.TopBarComponent;
import tech.tresearchgroup.palila.model.enums.PermissionGroupEnum;
import tech.tresearchgroup.schemas.galago.entities.ExtendedUserEntity;

import java.sql.SQLException;

import static j2html.TagCreator.*;

@AllArgsConstructor
public class LoginPage {
    private final SettingsController settingsController;
    private final NotificationEntityController notificationEntityController;

    public byte @NotNull [] render(boolean isError, boolean loggedIn, ExtendedUserEntity userEntity) throws SQLException {
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
        ).getBytes();
    }
}
