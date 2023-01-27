package tech.tresearchgroup.babygalago.view.pages;

import lombok.AllArgsConstructor;
import tech.tresearchgroup.babygalago.controller.controllers.QueueEntityController;
import tech.tresearchgroup.babygalago.view.components.*;
import tech.tresearchgroup.palila.controller.components.PaginationComponent;
import tech.tresearchgroup.palila.controller.components.PosterViewComponent;
import tech.tresearchgroup.palila.model.Card;
import tech.tresearchgroup.palila.model.enums.PermissionGroupEnum;
import tech.tresearchgroup.palila.view.RenderablePage;

import java.util.LinkedList;
import java.util.List;

import static j2html.TagCreator.*;

@AllArgsConstructor
public class ViewPage implements RenderablePage {
    /**
     * Renders the page
     *
     * @param loggedIn              whether the user is logged in
     * @param title                 the title at the top of the page
     * @param type                  the type of entity
     * @param function              the method of displaying
     * @param cards                 the cards being displayed
     * @param size                  the size of the cards
     * @param currentPage           the current page number
     * @param maxPage               the max number of pages
     * @param theClass              the class of the entities
     * @param ascending             whether its ascending
     * @param posterView            whether to render as cards or table
     * @param sortBy                how to sort the items
     * @param enableSortBy          whether it can be sorted
     * @param unreadCount           the number of unread notifications
     * @param permissionGroupEnum   the permission group which the user belongs to
     * @param serverName            the name of the server
     * @param isEnableUpload        if file upload is enabled
     * @param isMovieLibraryEnable  if the movie library is enabled
     * @param isTvShowLibraryEnable if the tv show library is enabled
     * @param isGameLibraryEnable   if the game library is enabled
     * @param isMusicLibraryEnable  if the music library is enabled
     * @param isBookLibraryEnable   if the book library is enabled
     * @return the page as a string
     */
    public String render(boolean loggedIn,
                         String title,
                         String type,
                         String function,
                         List<Card> cards,
                         int size,
                         int currentPage,
                         long maxPage,
                         Class theClass,
                         boolean ascending,
                         boolean posterView,
                         String sortBy,
                         boolean enableSortBy,
                         long unreadCount,
                         PermissionGroupEnum permissionGroupEnum,
                         String serverName,
                         boolean isEnableUpload,
                         boolean isMovieLibraryEnable,
                         boolean isTvShowLibraryEnable,
                         boolean isGameLibraryEnable,
                         boolean isMusicLibraryEnable,
                         boolean isBookLibraryEnable) {
        return document(
            html(
                HeadComponent.render(serverName),
                TopBarComponent.render(unreadCount, QueueEntityController.getQueueSize(), loggedIn, permissionGroupEnum, isEnableUpload),
                SideBarComponent.render(
                    loggedIn,
                    isMovieLibraryEnable,
                    isTvShowLibraryEnable,
                    isGameLibraryEnable,
                    isMusicLibraryEnable,
                    isBookLibraryEnable
                ),
                body(
                    div(
                        label(title).withClass("overviewLabel"),
                        br(),
                        iff(enableSortBy,
                            SortByFormComponent.render(theClass, ascending, sortBy)
                        ),
                        iff(cards.size() > 0,
                            LeftFormsComponent.render(type)
                        ),
                        br(),
                        br(),
                        br(),
                        div(
                            iffElse(cards.size() > 0,
                                iffElse(posterView,
                                    each(cards, card -> PosterViewComponent.render(card, size)),
                                    TableViewComponent.render(cards)
                                ),
                                label("Nothing to show").withClass("subLabel")
                            )
                        ).withClass("container"),
                        br(),
                        br(),
                        iff(cards.size() > 0,
                            div(
                                div().withClass("divider"),
                                PaginationComponent.render(currentPage, maxPage, "/" + function + "/" + type)
                            )
                        )
                    ).withClass("body")
                )
            )
        );
    }

    /**
     * Renders out the page with dummy data
     *
     * @return the page as a string
     */
    @Override
    public List<String> render() {
        /*try {
            return render(
            loggedIn,
            theClass.getSimpleName(),
            theClass.getSimpleName().toLowerCase(),
            "browse",
            cards,
            settingsController.getCardWidth(userSettingsEntity),
            page,
            maxPage,
            theClass,
            ascending,
            settingsController.getDisplayMode(userSettingsEntity).equals(DisplayModeEnum.POSTER),
            sortBy,
            true,
            notificationEntityController.getNumberOfUnread(userEntity),
            userEntity.getPermissionGroup(),
            settingsController.getServerName(),
            settingsController.isEnableUpload(),
            settingsController.isMovieLibraryEnable(),
            settingsController.isTvShowLibraryEnable(),
            settingsController.isGameLibraryEnable(),
            settingsController.isMusicLibraryEnable(),
            settingsController.isBookLibraryEnable()
        );
        } catch (SQLException e) {
            return null;
        }*/
        return new LinkedList<>();
    }
}
