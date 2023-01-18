package tech.tresearchgroup.colobus.view;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import tech.tresearchgroup.babygalago.controller.SettingsController;
import tech.tresearchgroup.babygalago.controller.controllers.QueueEntityController;
import tech.tresearchgroup.babygalago.view.components.HeadComponent;
import tech.tresearchgroup.babygalago.view.components.SideBarComponent;
import tech.tresearchgroup.babygalago.view.components.TopBarComponent;
import tech.tresearchgroup.palila.model.enums.PermissionGroupEnum;

import static j2html.TagCreator.*;

@AllArgsConstructor
public class IndexPage {
    private final SettingsController settingsController;

    public byte @NotNull [] render(PermissionGroupEnum permissionGroupEnum) {
        return document(
            html(
                HeadComponent.render(settingsController.getServerName()),
                //Todo load notifications
                TopBarComponent.render(2L, QueueEntityController.getQueueSize(), true, permissionGroupEnum, settingsController.isEnableUpload()),
                SideBarComponent.render(true,
                    settingsController.isMovieLibraryEnable(),
                    settingsController.isTvShowLibraryEnable(),
                    settingsController.isGameLibraryEnable(),
                    settingsController.isMusicLibraryEnable(),
                    settingsController.isBookLibraryEnable()),
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
