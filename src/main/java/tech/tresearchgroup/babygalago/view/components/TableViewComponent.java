package tech.tresearchgroup.babygalago.view.components;

import j2html.tags.DomContent;
import org.jetbrains.annotations.NotNull;
import tech.tresearchgroup.palila.controller.components.ModalComponent;
import tech.tresearchgroup.palila.controller.components.SelectCheckboxComponent;
import tech.tresearchgroup.palila.model.Card;

import java.util.List;

import static j2html.TagCreator.*;

public class TableViewComponent {
    /**
     * Renders entity lists in table view
     *
     * @param cards the cards to list
     * @return the component
     */
    public static @NotNull DomContent render(List<Card> cards) {
        return table(
            thead(
                tr(
                    th("Select"),
                    th("Poster"),
                    th("Name"),
                    th("Runtime"),
                    th("Genre"),
                    th("MPAA Rating"),
                    th("User Ratings"),
                    th("Language"),
                    th("Release date"),
                    th("Actions")
                )
            ),
            tbody(
                each(cards, TableViewComponent::renderLine)
            )
        ).withClass("table");
    }

    private static @NotNull DomContent renderLine(Card card) {
        return tr(
            td(
                SelectCheckboxComponent.render("checkbox-" + card.getId())
            ),
            td(
                img().withAlt("Poster image").withClass("table-image").withSrc("/assets/poster.webp")
            ),
            td(card.getTitle()),
            td(card.getTopLeft()),
            td("GENRE"),
            td(card.getTopRight()),
            td(card.getBottomLeft()),
            td("LANGUAGE"),
            td(card.getBottomRight()),
            ModalComponent.render(
                "Confirm deletion",
                span(text("Are you sure you want to delete: " + card.getTitle() + "?"),
                    br(),
                    text("This action cannot be undone!")
                ),
                "/delete/movie/" + card.getId(),
                "DELETE",
                "delete-" + card.getId()
            ),
            td(
                a(" Play").withClass("btn btn-link fas fa-play").withHref("/play/" + card.getClassName() + "/" + card.getId()),
                a(" Edit").withClass("btn btn-link fa fa-edit").withHref("/edit/" + card.getClassName() + "/" + card.getId()),
                br(),
                a(" View").withClass("btn btn-link fa fa-eye").withHref("/view/" + card.getClassName() + "/" + card.getId()),
                a(" Delete").withClass("btn btn-link fa fa-trash").withHref("#delete-" + card.getId())
            )
        );
    }
}
