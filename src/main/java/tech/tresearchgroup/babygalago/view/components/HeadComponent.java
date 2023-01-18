package tech.tresearchgroup.babygalago.view.components;

import j2html.tags.DomContent;
import org.jetbrains.annotations.NotNull;
import tech.tresearchgroup.palila.controller.cache.StaticDomContentCAO;

import static j2html.TagCreator.*;

public class HeadComponent {
    public static @NotNull DomContent render(String title) {
        DomContent cached = StaticDomContentCAO.read("headComponent");
        if (cached != null) {
            return cached;
        }
        DomContent data = head(
            meta().withCharset("UTF-8"),
            link().withHref("/assets/spectre.min.css").withRel("stylesheet"),
            link().withHref("/assets/gen/styles.min.css").withRel("stylesheet"),

            meta().withCharset("UTF-8").withContent("width=device-width, initial-scale=1.0").withName("viewport"),
            meta().withContent(title).withName("description"),
            title(title),
            link().withRel("icon").withType("image/x-icon").withHref("/assets/favicon.ico")
        );
        StaticDomContentCAO.create("headComponent", data);
        return data;
    }
}
