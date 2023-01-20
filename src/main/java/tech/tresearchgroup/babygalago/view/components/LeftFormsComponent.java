package tech.tresearchgroup.babygalago.view.components;

import j2html.tags.DomContent;
import org.jetbrains.annotations.NotNull;
import tech.tresearchgroup.cao.controller.GenericCAO;
import tech.tresearchgroup.cao.model.CacheTypesEnum;

import static j2html.TagCreator.div;

public class LeftFormsComponent {
    /**
     * Renders the left forms component
     * @param mediaType the type of media to generate it for
     * @return the component
     */
    public static @NotNull DomContent render(String mediaType, GenericCAO genericCAO) {
        DomContent cached = (DomContent) genericCAO.read(CacheTypesEnum.DOM, "leftFormsComponent-" + mediaType);
        if (cached != null) {
            return cached;
        }
        DomContent data = div(
            FilterByComponent.render(genericCAO),
            BulkActionsComponent.render(mediaType, genericCAO)
        ).withClass("leftForms");
        genericCAO.create(CacheTypesEnum.DOM, "leftFormsComponent-" + mediaType, data);
        return data;
    }
}
