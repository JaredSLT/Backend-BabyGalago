package tech.tresearchgroup.babygalago.view.pages;

import lombok.AllArgsConstructor;
import tech.tresearchgroup.babygalago.controller.controllers.QueueEntityController;
import tech.tresearchgroup.babygalago.view.components.HeadComponent;
import tech.tresearchgroup.babygalago.view.components.SideBarComponent;
import tech.tresearchgroup.babygalago.view.components.TopBarComponent;
import tech.tresearchgroup.babygalago.view.components.ViewMoreComponent;
import tech.tresearchgroup.cao.controller.GenericCAO;
import tech.tresearchgroup.palila.controller.components.EditableScrollingComponent;
import tech.tresearchgroup.palila.model.Card;
import tech.tresearchgroup.palila.model.enums.PermissionGroupEnum;
import tech.tresearchgroup.palila.view.RenderablePage;

import java.util.LinkedList;
import java.util.List;

import static j2html.TagCreator.*;

@AllArgsConstructor
public class IndexPage implements RenderablePage {
    /**
     * Renders the page
     *
     * @param loggedIn              whether the user is logged in
     * @param newBooks              the list of new book entities
     * @param popBooks              the list of popular book entities
     * @param newGames              the list of new game entities
     * @param popGames              the list of popular game entities
     * @param newMovies             the list of new movie entities
     * @param popMovies             the list of popular movie entities
     * @param newMusic              the list of new music entities
     * @param popMusic              the list of popular music entities
     * @param popTvShows            the list of popular tv show entities
     * @param newTvShows            the list of new tv show entities
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
                         int size,
                         List<Card> newBooks,
                         List<Card> popBooks,
                         List<Card> newGames,
                         List<Card> popGames,
                         List<Card> newMovies,
                         List<Card> popMovies,
                         List<Card> newMusic,
                         List<Card> popMusic,
                         List<Card> newTvShows,
                         List<Card> popTvShows,
                         long unreadCount,
                         PermissionGroupEnum permissionGroupEnum,
                         String serverName,
                         boolean isEnableUpload,
                         boolean isMovieLibraryEnable,
                         boolean isTvShowLibraryEnable,
                         boolean isGameLibraryEnable,
                         boolean isMusicLibraryEnable,
                         boolean isBookLibraryEnable,
                         GenericCAO genericCAO) {
        boolean isHomePageShowNewBook = newBooks != null;
        boolean isHomePageShowPopularBook = popBooks != null;
        boolean isHomePageShowNewGame = newGames != null;
        boolean isHomePageShowPopularGame = popGames != null;
        boolean isHomePageShowNewMovie = newMovies != null;
        boolean isHomePageShowPopularMovie = popMovies != null;
        boolean isHomePageShowNewMusic = newMusic != null;
        boolean isHomePageShowPopularMusic = popMusic != null;
        boolean isHomePageShowNewTvShow = newTvShows != null;
        boolean isHomePageShowPopularTvShow = popTvShows != null;
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
                        iff(isHomePageShowNewBook,
                            div(
                                ViewMoreComponent.render("new", "bookentity"),
                                br(),
                                EditableScrollingComponent.render(false, "New books", newBooks, "/add/bookentity", true, size),
                                br(),
                                br()
                            )
                        ),

                        iff(isHomePageShowPopularBook,
                            div(
                                ViewMoreComponent.render("popular", "bookentity"),
                                br(),
                                EditableScrollingComponent.render(false, "Popular books", popBooks, "/add/bookentity", true, size),
                                br(),
                                br()
                            )
                        ),

                        iff(isHomePageShowNewGame,
                            div(
                                ViewMoreComponent.render("new", "gameentity"),
                                br(),
                                EditableScrollingComponent.render(false, "New games", newGames, "/add/gameentity", true, size),
                                br(),
                                br()
                            )
                        ),

                        iff(isHomePageShowPopularGame,
                            div(
                                ViewMoreComponent.render("popular", "gameentity"),
                                br(),
                                EditableScrollingComponent.render(false, "Popular games", popGames, "/add/gameentity", true, size),
                                br(),
                                br()
                            )
                        ),

                        iff(isHomePageShowNewMovie,
                            div(
                                ViewMoreComponent.render("new", "movieentity"),
                                br(),
                                EditableScrollingComponent.render(false, "New movies", newMovies, "/add/movieentity", true, size),
                                br(),
                                br()
                            )
                        ),

                        iff(isHomePageShowPopularMovie,
                            div(
                                ViewMoreComponent.render("popular", "movieentity"),
                                br(),
                                EditableScrollingComponent.render(false, "Popular movies", popMovies, "/add/movieentity", true, size),
                                br(),
                                br()
                            )
                        ),

                        iff(isHomePageShowNewMusic,
                            div(
                                ViewMoreComponent.render("new", "musicentity"),
                                br(),
                                EditableScrollingComponent.render(false, "New music", newMusic, "/add/songentity", true, size),
                                br(),
                                br()
                            )
                        ),

                        iff(isHomePageShowPopularMusic,
                            div(
                                ViewMoreComponent.render("popular", "movieentity"),
                                br(),
                                EditableScrollingComponent.render(false, "Popular music", popMusic, "/add/songentity", true, size),
                                br(),
                                br()
                            )
                        ),

                        iff(isHomePageShowNewTvShow,
                            div(
                                ViewMoreComponent.render("new", "tvshowentity"),
                                br(),
                                EditableScrollingComponent.render(false, "New tv shows", newTvShows, "/add/tvshowentity", true, size),
                                br(),
                                br()
                            )
                        ),

                        iff(isHomePageShowPopularTvShow,
                            div(
                                ViewMoreComponent.render("popular", "tvshowentity"),
                                br(),
                                EditableScrollingComponent.render(false, "Popular tv shows", popTvShows, "/add/tvshowentity", true, size),
                                br(),
                                br()
                            )
                        ),
                        br()
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
        List<String> pages = new LinkedList<>();
        /*try {
            return render(
            loggedIn,
            settingsController.getCardWidth(userSettingsEntity),
            newBooksCards,
            popularBooksCards,
            newGamesCards,
            popularGamesCards,
            newMoviesCards,
            popularMoviesCards,
            newMusicCards,
            popularMusicCards,
            newTvShowsCards,
            popularTvShowsCards,
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
        return pages;
    }
}
