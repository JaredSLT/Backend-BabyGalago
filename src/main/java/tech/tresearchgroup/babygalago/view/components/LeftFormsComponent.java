package tech.tresearchgroup.babygalago.view.components;

import j2html.tags.DomContent;
import org.jetbrains.annotations.NotNull;

import static j2html.TagCreator.div;

public class LeftFormsComponent {
    /**
     * Renders the left forms component
     *
     * @param mediaType the type of media to generate it for
     * @return the component
     */
    public static @NotNull DomContent render(String mediaType) {
        return div(
            FilterByComponent.render(),
            BulkActionsComponent.render(mediaType)
        ).withClass("leftForms");
    }
}
