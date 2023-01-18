package tech.tresearchgroup.babygalago.view.pages;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import tech.tresearchgroup.babygalago.controller.SettingsController;
import tech.tresearchgroup.babygalago.view.components.HeadComponent;

import static j2html.TagCreator.*;

@AllArgsConstructor
public class MaintenancePage {
    private final SettingsController settingsController;

    public byte @NotNull [] render() {
        return document(
            html(
                HeadComponent.render(settingsController.getServerName()),
                body(
                    div(
                        div(
                            label("Maintenance mode").withClass("overviewLabel"),
                            br(),
                            br(),
                            label("The system is in maintenance mode. This means the operator of the server is working on the software. Please check back in a while.").withClass("subLabel"),
                            br(),
                            br(),
                            br(),
                            img().withSrc("/assets/maintenance.webp").withWidth("300").withHeight("346")
                        ).withClass("verticalCenter")
                    ).withClass("body")
                )
            )
        ).getBytes();
    }
}
