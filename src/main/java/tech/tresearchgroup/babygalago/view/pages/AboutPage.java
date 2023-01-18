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
public class AboutPage {
    private final SettingsController settingsController;
    private final NotificationEntityController notificationEntityController;

    public byte @NotNull [] render(boolean loggedIn, ExtendedUserEntity userEntity) throws SQLException {
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
                    settingsController.isBookLibraryEnable()
                ),
                body(
                    div(
                        div(
                            label("About us").withClass("overviewLabel"),
                            br(),
                            br(),
                            text("Galago is developed by Thorne Research Group, a group of passionate open-source " +
                                "software developers. Our goal is to give power back to the private archive operators by " +
                                "open-sourcing as much of our software as possible, making it so that you don’t run any " +
                                "proprietary code on your server. You can have complete confidence that your system is " +
                                "doing what you ask, only when you ask and is not sending any data back to the software " +
                                "developers."),
                            br(),
                            br(),
                            text("We are dedicated to long-term support and development as we also use this system for " +
                                "our archive. We will never:"),
                            br(),
                            br(),
                            text("- Force users to pay more by increasing monthly subscription payments"),
                            br(),
                            text("- Make previously open-source software proprietary"),
                            br(),
                            text("- Send any non-essential data to our servers"),
                            br(),
                            text("- Perform any actions on your data without being requested"),
                            br(),
                            br(),
                            text("In these ways and more, we aim to empower the next generation of open-source software " +
                                "developers to continue bearing the open-source torch into the future.")
                        ).withClass("aboutSection")
                    ).withClass("body")
                )
            )
        ).getBytes();
    }
}
