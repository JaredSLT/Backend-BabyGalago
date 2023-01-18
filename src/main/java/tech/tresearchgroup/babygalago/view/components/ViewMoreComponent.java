package tech.tresearchgroup.babygalago.view.components;

import j2html.tags.DomContent;
import org.jetbrains.annotations.NotNull;

import static j2html.TagCreator.a;

public class ViewMoreComponent {
    public static @NotNull DomContent render(String type, String mediaType) {
        return a().withClass("viewMore fa fa-eye").withHref(type + "/" + mediaType).withText(" View more");
    }
}
