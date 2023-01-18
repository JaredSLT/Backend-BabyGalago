package tech.tresearchgroup.babygalago.view.pages;

import j2html.tags.specialized.DivTag;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tech.tresearchgroup.babygalago.controller.SettingsController;
import tech.tresearchgroup.babygalago.controller.controllers.NotificationEntityController;
import tech.tresearchgroup.babygalago.controller.controllers.QueueEntityController;
import tech.tresearchgroup.babygalago.view.components.HeadComponent;
import tech.tresearchgroup.babygalago.view.components.SideBarComponent;
import tech.tresearchgroup.babygalago.view.components.TopBarComponent;
import tech.tresearchgroup.palila.controller.components.InputBoxComponent;
import tech.tresearchgroup.palila.model.RegistrationErrorsEnum;
import tech.tresearchgroup.palila.model.enums.PermissionGroupEnum;
import tech.tresearchgroup.schemas.galago.entities.ExtendedUserEntity;

import java.sql.SQLException;

import static j2html.TagCreator.*;

@AllArgsConstructor
public class RegisterPage {
    private final SettingsController settingsController;
    private final NotificationEntityController notificationEntityController;

    public static DivTag getError(boolean toError, String text) {
        return iff(toError,
            div(
                div(
                    text(text)
                ).withClass("toast toast-error"),
                br()
            )
        );
    }

    public byte @NotNull [] render(@Nullable RegistrationErrorsEnum registrationErrorsEnum, ExtendedUserEntity userEntity) throws SQLException {
        PermissionGroupEnum permissionGroupEnum = PermissionGroupEnum.ALL;
        if (userEntity != null) {
            permissionGroupEnum = userEntity.getPermissionGroup();
        }
        boolean emailError = false;
        boolean passwordError = false;
        boolean usernameError = false;
        boolean serverError = false;
        boolean passwordLengthError = false;
        boolean incorrectEmail = false;
        if (registrationErrorsEnum != null) {
            if (registrationErrorsEnum.equals(RegistrationErrorsEnum.EMAIL_MATCH)) {
                emailError = true;
            }
            if (registrationErrorsEnum.equals(RegistrationErrorsEnum.PASSWORD_MATCH)) {
                passwordError = true;
            }
            if (registrationErrorsEnum.equals(RegistrationErrorsEnum.USERNAME_TAKEN)) {
                usernameError = true;
            }
            if (registrationErrorsEnum.equals(RegistrationErrorsEnum.ERROR_500)) {
                serverError = true;
            }
            if (registrationErrorsEnum.equals(RegistrationErrorsEnum.PASSWORD_LENGTH)) {
                passwordLengthError = true;
            }
            if (registrationErrorsEnum.equals(RegistrationErrorsEnum.INCORRECT_EMAIL)) {
                incorrectEmail = true;
            }
        }
        return document(
            html(
                HeadComponent.render(settingsController.getServerName()),
                TopBarComponent.render(notificationEntityController.getNumberOfUnread(userEntity), QueueEntityController.getQueueSize(), false, permissionGroupEnum, settingsController.isEnableUpload()),
                SideBarComponent.render(false,
                    settingsController.isMovieLibraryEnable(),
                    settingsController.isTvShowLibraryEnable(),
                    settingsController.isGameLibraryEnable(),
                    settingsController.isMusicLibraryEnable(),
                    settingsController.isBookLibraryEnable()),
                body(
                    div(
                        div(
                            getError(emailError, "Emails are not the same"),
                            getError(passwordError, "Passwords are not the same"),
                            getError(usernameError, "That username is already taken"),
                            getError(serverError, "Server error"),
                            getError(passwordLengthError, "Password requirements not met"),
                            getError(incorrectEmail, "Incorrect email"),
                            form(
                                InputBoxComponent.render("username", "Username: "),
                                br(),
                                InputBoxComponent.render("email", "Email: "),
                                br(),
                                InputBoxComponent.render("emailConfirm", "Email confirm: "),
                                br(),
                                InputBoxComponent.render("password", "Password: "),
                                br(),
                                InputBoxComponent.render("passwordConfirm", "Password confirm: "),
                                br(),
                                br(),
                                button("Submit").withType("submit")
                            ).withMethod("POST").withAction("/register")
                        ).withClass("verticalCenter")
                    ).withClass("body")
                )
            )
        ).getBytes();
    }
}
