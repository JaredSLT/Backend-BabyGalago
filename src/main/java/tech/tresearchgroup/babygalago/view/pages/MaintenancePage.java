package tech.tresearchgroup.babygalago.view.pages;

import lombok.AllArgsConstructor;
import tech.tresearchgroup.babygalago.view.components.HeadComponent;
import tech.tresearchgroup.palila.view.RenderablePage;

import java.util.LinkedList;
import java.util.List;

import static j2html.TagCreator.*;

@AllArgsConstructor
public class MaintenancePage implements RenderablePage {
    /**
     * Renders the page
     *
     * @param serverName the name of the server
     * @return the page as a string
     */
    public String render(String serverName) {
        return document(
            html(
                HeadComponent.render(serverName),
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
        //pages.add(render("Baby galago server"));
        return pages;
    }
}
