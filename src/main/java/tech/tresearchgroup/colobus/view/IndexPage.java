package tech.tresearchgroup.colobus.view;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import tech.tresearchgroup.babygalago.controller.controllers.QueueEntityController;
import tech.tresearchgroup.babygalago.view.components.HeadComponent;
import tech.tresearchgroup.babygalago.view.components.SideBarComponent;
import tech.tresearchgroup.babygalago.view.components.TopBarComponent;
import tech.tresearchgroup.cao.controller.GenericCAO;
import tech.tresearchgroup.palila.model.enums.PermissionGroupEnum;

import static j2html.TagCreator.*;

@AllArgsConstructor
public class IndexPage {
    public byte @NotNull [] render(boolean loggedIn,
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
                //Todo load notifications
                TopBarComponent.render(unreadCount, QueueEntityController.getQueueSize(), true, permissionGroupEnum, isEnableUpload),
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
                            text("The forum is still being developed. Please check back later.")
                        ).withClass("verticalCenter subLabel")
                    ).withClass("body")
                )
            )
        ).getBytes();
    }
}
