package tech.tresearchgroup.babygalago.view.components;

import j2html.tags.DomContent;
import org.jetbrains.annotations.NotNull;
import tech.tresearchgroup.palila.controller.cache.StaticDomContentCAO;

import static j2html.TagCreator.div;

public class LeftFormsComponent {
    public static @NotNull DomContent render(String mediaType) {
        DomContent cached = StaticDomContentCAO.read("leftFormsComponent-" + mediaType);
        if (cached != null) {
            return cached;
        }
        DomContent data = div(
            FilterByComponent.render(),
            BulkActionsComponent.render(mediaType)
        ).withClass("leftForms");
        StaticDomContentCAO.create("leftFormsComponent-" + mediaType, data);
        return data;
    }
}
