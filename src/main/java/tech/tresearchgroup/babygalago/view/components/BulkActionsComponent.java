package tech.tresearchgroup.babygalago.view.components;

import j2html.tags.DomContent;
import org.jetbrains.annotations.NotNull;

import static j2html.TagCreator.*;

public class BulkActionsComponent {
    /**
     * Renders the bulk action component
     *
     * @param mediaType the type of media to create the component for
     * @return the component
     */
    public static @NotNull DomContent render(String mediaType) {
        return form(
            ul(
                li(
                    label("Actions").withClass("subLabel"),
                    ul(
                        li(
                            a(
                                i().withClass("subLabel fas fa-edit").withText(" Create new")
                            ).withHref("/add/" + mediaType)
                        ),
                        li(
                            a(
                                i(" Select all").withClass("subLabel fas fa-check")
                            ).withHref("#")
                        ),
                        li(
                            a(
                                i(" Delete selected").withClass("subLabel fa fa-trash")
                            ).withHref("#")
                        )
                    )
                )
            ).withClass("multidropdown")
        ).withId("bulkActions");
    }
}
