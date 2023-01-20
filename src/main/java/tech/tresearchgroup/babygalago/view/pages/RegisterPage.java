package tech.tresearchgroup.babygalago.view.pages;

import j2html.tags.specialized.DivTag;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.Nullable;
import tech.tresearchgroup.babygalago.controller.controllers.QueueEntityController;
import tech.tresearchgroup.babygalago.view.components.HeadComponent;
import tech.tresearchgroup.babygalago.view.components.SideBarComponent;
import tech.tresearchgroup.babygalago.view.components.TopBarComponent;
import tech.tresearchgroup.cao.controller.GenericCAO;
import tech.tresearchgroup.palila.controller.components.InputBoxComponent;
import tech.tresearchgroup.palila.model.enums.PermissionGroupEnum;
import tech.tresearchgroup.palila.model.enums.RegistrationErrorsEnum;
import tech.tresearchgroup.palila.view.RenderablePage;

import java.util.LinkedList;
import java.util.List;

import static j2html.TagCreator.*;

@AllArgsConstructor
public class RegisterPage implements RenderablePage {
    /**
     * Creates an error di
     * @param toError whether the error should be generated
     * @param text the text inside the error
     * @return the div with the error in it
     */
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

    /**
     * Renders the page
     * @param registrationErrorsEnum The error to be displayed
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
    public String render(@Nullable RegistrationErrorsEnum registrationErrorsEnum,
                         boolean loggedIn,
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
        );
    }

    /**
     * Renders out the page with dummy data
     * @return the page as a string
     */
    @Override
    public List<String> render() {
        /*try {
            return render(
                    null,
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
