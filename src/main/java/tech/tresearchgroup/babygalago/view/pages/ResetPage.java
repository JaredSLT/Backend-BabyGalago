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
public class ResetPage {
    private final SettingsController settingsController;
    private final NotificationEntityController notificationEntityController;

    public byte @NotNull [] render(boolean loggedIn, ExtendedUserEntity userEntity, boolean isError, boolean isSuccess, boolean wasConfirmed, String confirmation) throws SQLException {
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
        ).getBytes();
    }
}
