package tech.tresearchgroup.babygalago.view.components;

import j2html.tags.DomContent;
import org.jetbrains.annotations.NotNull;
import tech.tresearchgroup.palila.controller.cache.StaticDomContentCAO;

import static j2html.TagCreator.*;

public class BulkActionsComponent {
    public static @NotNull DomContent render(String mediaType) {
        DomContent cached = StaticDomContentCAO.read("bulkActionsComponent-" + mediaType);
        if (cached != null) {
            return cached;
        }
        DomContent data = form(
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
        StaticDomContentCAO.create("bulkActionsComponent-" + mediaType, data);
        return data;
    }
}
