package tech.tresearchgroup.babygalago.view.components;

import j2html.tags.DomContent;
import org.jetbrains.annotations.NotNull;
import tech.tresearchgroup.palila.controller.cache.StaticDomContentCAO;

import static j2html.TagCreator.*;

public class FilterByComponent {
    public static @NotNull DomContent render() {
        DomContent cached = StaticDomContentCAO.read("filterByComponent");
        if (cached != null) {
            return cached;
        }
        DomContent data = form(
            ul(
                li(
                    label("Filter by:").withClass("subLabel"),
                    ul(
                        li(
                            label("Genres").withClass("subLabel"),
                            i().withClass("dropdownIcon fas fa-arrow-circle-right"),
                            ul(
                                li(
                                    label("A").withClass("subLabel"),
                                    i().withClass("dropdownIcon fas fa-arrow-circle-right"),
                                    ul(
                                        li(
                                            label("Action:").withClass("subLabel"),
                                            input().withType("checkbox")
                                        ),
                                        li(
                                            label("Adventure:").withClass("subLabel"),
                                            input().withType("checkbox")
                                        ),
                                        li(
                                            label("Animation:").withClass("subLabel"),
                                            input().withType("checkbox")
                                        )
                                    )
                                ),
                                li(
                                    label("C").withClass("subLabel"),
                                    i().withClass("dropdownIcon fas fa-arrow-circle-right"),
                                    ul(
                                        li(
                                            label("Comedy:").withClass("subLabel"),
                                            input().withType("checkbox")
                                        ),
                                        li(
                                            label("Crime:").withClass("subLabel"),
                                            input().withType("checkbox")
                                        )
                                    )
                                ),
                                li(
                                    label("D").withClass("subLabel"),
                                    i().withClass("dropdownIcon fas fa-arrow-circle-right"),
                                    ul(
                                        li(
                                            label("Drama:").withClass("subLabel"),
                                            input().withType("checkbox")
                                        )
                                    )
                                ),
                                li(
                                    label("F").withClass("subLabel"),
                                    i().withClass("dropdownIcon fas fa-arrow-circle-right"),
                                    ul(
                                        li(
                                            label("Fantasy:").withClass("subLabel"),
                                            input().withType("checkbox")
                                        ),
                                        li(
                                            label("Fiction:").withClass("subLabel"),
                                            input().withType("checkbox")
                                        )
                                    )
                                ),
                                li(
                                    label("H").withClass("subLabel"),
                                    i().withClass("dropdownIcon fas fa-arrow-circle-right"),
                                    ul(
                                        li(
                                            label("Historic:").withClass("subLabel"),
                                            input().withType("checkbox")
                                        ),
                                        li(
                                            label("Horror:").withClass("subLabel"),
                                            input().withType("checkbox")
                                        )
                                    )
                                ),
                                li(
                                    label("I").withClass("subLabel"),
                                    i().withClass("dropdownIcon fas fa-arrow-circle-right"),
                                    ul(
                                        li(
                                            label("Indie:").withClass("subLabel"),
                                            input().withType("checkbox")
                                        )
                                    )
                                ),
                                li(
                                    label("M").withClass("subLabel"),
                                    i().withClass("dropdownIcon fas fa-arrow-circle-right"),
                                    ul(
                                        li(
                                            label("Mystery:").withClass("subLabel"),
                                            input().withType("checkbox")
                                        )
                                    )
                                ),
                                li(
                                    label("R").withClass("subLabel"),
                                    i().withClass("dropdownIcon fas fa-arrow-circle-right"),
                                    ul(
                                        li(
                                            label("Romance:").withClass("subLabel"),
                                            input().withType("checkbox")
                                        ),
                                        li(
                                            label("Romantic Comedy:").withClass("subLabel"),
                                            input().withType("checkbox")
                                        )
                                    )
                                ),
                                li(
                                    label("S").withClass("subLabel"),
                                    i().withClass("dropdownIcon fas fa-arrow-circle-right"),
                                    ul(
                                        li(
                                            label("Science Fiction:").withClass("subLabel"),
                                            input().withType("checkbox")
                                        ),
                                        li(
                                            label("Sports:").withClass("subLabel"),
                                            input().withType("checkbox")
                                        )
                                    )
                                ),
                                li(
                                    label("T").withClass("subLabel"),
                                    i().withClass("dropdownIcon fas fa-arrow-circle-right"),
                                    ul(
                                        li(
                                            label("Thriller:").withClass("subLabel"),
                                            input().withType("checkbox")
                                        )
                                    )
                                ),
                                li(
                                    label("W").withClass("subLabel"),
                                    i().withClass("dropdownIcon fas fa-arrow-circle-right"),
                                    ul(
                                        li(
                                            label("War:").withClass("subLabel"),
                                            input().withType("checkbox")
                                        ),
                                        li(
                                            label("Western:").withClass("subLabel"),
                                            input().withType("checkbox")
                                        )
                                    )
                                )
                            )
                        ),
                        li(
                            label("Years").withClass("subLabel"),
                            i().withClass("dropdownIcon fas fa-arrow-circle-right"),
                            ul(
                                li(
                                    label("Start year: ").withClass("subLabel"),
                                    input().withType("text")
                                ),
                                li(
                                    label("End year: ").withClass("subLabel"),
                                    input().withType("text")
                                )
                            )
                        ),
                        li(
                            label("Rating").withClass("subLabel"),
                            i().withClass("dropdownIcon fas fa-arrow-circle-right"),
                            ul(
                                li(
                                    label("5:").withClass("subLabel"),
                                    input().withType("checkbox")
                                ),
                                li(
                                    label("4:").withClass("subLabel"),
                                    input().withType("checkbox")
                                ),
                                li(
                                    label("3:").withClass("subLabel"),
                                    input().withType("checkbox")
                                ),
                                li(
                                    label("2:").withClass("subLabel"),
                                    input().withType("checkbox")
                                ),
                                li(
                                    label("1:").withClass("subLabel"),
                                    input().withType("checkbox")
                                )
                            )
                        )
                    )
                )
            ).withClass("multidropdown")
        ).withId("filterByForm");
        StaticDomContentCAO.create("filterByComponent", data);
        return data;
    }
}
