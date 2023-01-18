package tech.tresearchgroup.babygalago.view.components;

import j2html.tags.DomContent;
import org.jetbrains.annotations.NotNull;
import tech.tresearchgroup.palila.controller.ReflectionMethods;
import tech.tresearchgroup.palila.controller.StringController;

import java.util.List;

import static j2html.TagCreator.*;

public class SortByFormComponent {
    public static @NotNull DomContent render(Class theClass, boolean ascending, String sortBy) {
        List<String> names = ReflectionMethods.getNonDefaultFieldNames(theClass);
        if (sortBy == null) {
            sortBy = "none";
        }
        String finalSortBy = sortBy;
        return form(
            label("Sort by:").withFor("sortBy"),
            select(
                option("None").withValue("none"),
                each(names, name ->
                    iffElse(finalSortBy.equals(name),
                        option(StringController.splitCamelCase(name)).withValue(name).isSelected(),
                        option(StringController.splitCamelCase(name)).withValue(name)
                    )
                )
            ).withId("sortBy").withName("sortBy"),
            label("ASC: "),
            iffElse(ascending,
                input().withType("checkbox").withName("ascending").isChecked(),
                input().withType("checkbox").withName("ascending")
            ),
            button("Go").withType("submit")
        ).withId("sortByForm");
    }
}
