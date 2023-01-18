package tech.tresearchgroup.babygalago.view.pages;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import tech.tresearchgroup.babygalago.controller.SettingsController;
import tech.tresearchgroup.babygalago.controller.controllers.NotificationEntityController;
import tech.tresearchgroup.babygalago.controller.controllers.QueueEntityController;
import tech.tresearchgroup.babygalago.view.components.HeadComponent;
import tech.tresearchgroup.babygalago.view.components.SideBarComponent;
import tech.tresearchgroup.babygalago.view.components.TopBarComponent;
import tech.tresearchgroup.babygalago.view.components.ViewMoreComponent;
import tech.tresearchgroup.palila.controller.components.EditableScrollingComponent;
import tech.tresearchgroup.palila.model.Card;
import tech.tresearchgroup.palila.model.enums.PermissionGroupEnum;
import tech.tresearchgroup.schemas.galago.entities.ExtendedUserEntity;
import tech.tresearchgroup.schemas.galago.entities.UserSettingsEntity;

import java.sql.SQLException;
import java.util.List;

import static j2html.TagCreator.*;

@AllArgsConstructor
public class IndexPage {
    private final SettingsController settingsController;
    private final NotificationEntityController notificationEntityController;

    public byte @NotNull [] render(boolean loggedIn,
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
                                   ExtendedUserEntity userEntity) throws SQLException {
        PermissionGroupEnum permissionGroupEnum = PermissionGroupEnum.ALL;
        UserSettingsEntity userSettingsEntity = null;
        if (userEntity != null) {
            permissionGroupEnum = userEntity.getPermissionGroup();
            userSettingsEntity = userEntity.getUserSettings();
        }
        return document(
            html(
                HeadComponent.render(settingsController.getServerName()),
                SideBarComponent.render(loggedIn,
                    settingsController.isMovieLibraryEnable(),
                    settingsController.isTvShowLibraryEnable(),
                    settingsController.isGameLibraryEnable(),
                    settingsController.isMusicLibraryEnable(),
                    settingsController.isBookLibraryEnable()),
                TopBarComponent.render(notificationEntityController.getNumberOfUnread(userEntity), QueueEntityController.getQueueSize(), loggedIn, permissionGroupEnum, settingsController.isEnableUpload()),
                body(
                    div(
                        iff(settingsController.isHomePageShowNewBook(userSettingsEntity),
                            div(
                                ViewMoreComponent.render("new", "bookentity"),
                                br(),
                                EditableScrollingComponent.render(false, "New books", newBooks, "/add/bookentity", true, size),
                                br(),
                                br()
                            )
                        ),

                        iff(settingsController.isHomePageShowPopularBook(userSettingsEntity),
                            div(
                                ViewMoreComponent.render("popular", "bookentity"),
                                br(),
                                EditableScrollingComponent.render(false, "Popular books", popBooks, "/add/bookentity", true, size),
                                br(),
                                br()
                            )
                        ),

                        iff(settingsController.isHomePageShowNewGame(userSettingsEntity),
                            div(
                                ViewMoreComponent.render("new", "gameentity"),
                                br(),
                                EditableScrollingComponent.render(false, "New games", newGames, "/add/gameentity", true, size),
                                br(),
                                br()
                            )
                        ),

                        iff(settingsController.isHomePageShowPopularGame(userSettingsEntity),
                            div(
                                ViewMoreComponent.render("popular", "gameentity"),
                                br(),
                                EditableScrollingComponent.render(false, "Popular games", popGames, "/add/gameentity", true, size),
                                br(),
                                br()
                            )
                        ),

                        iff(settingsController.isHomePageShowNewMovie(userSettingsEntity),
                            div(
                                ViewMoreComponent.render("new", "movieentity"),
                                br(),
                                EditableScrollingComponent.render(false, "New movies", newMovies, "/add/movieentity", true, size),
                                br(),
                                br()
                            )
                        ),

                        iff(settingsController.isHomePageShowPopularMovie(userSettingsEntity),
                            div(
                                ViewMoreComponent.render("popular", "movieentity"),
                                br(),
                                EditableScrollingComponent.render(false, "Popular movies", popMovies, "/add/movieentity", true, size),
                                br(),
                                br()
                            )
                        ),

                        iff(settingsController.isHomePageShowNewMusic(userSettingsEntity),
                            div(
                                ViewMoreComponent.render("new", "musicentity"),
                                br(),
                                EditableScrollingComponent.render(false, "New music", newMusic, "/add/songentity", true, size),
                                br(),
                                br()
                            )
                        ),

                        iff(settingsController.isHomePageShowPopularMusic(userSettingsEntity),
                            div(
                                ViewMoreComponent.render("popular", "movieentity"),
                                br(),
                                EditableScrollingComponent.render(false, "Popular music", popMusic, "/add/songentity", true, size),
                                br(),
                                br()
                            )
                        ),

                        iff(settingsController.isHomePageShowNewTvShow(userSettingsEntity),
                            div(
                                ViewMoreComponent.render("new", "tvshowentity"),
                                br(),
                                EditableScrollingComponent.render(false, "New tv shows", newTvShows, "/add/tvshowentity", true, size),
                                br(),
                                br()
                            )
                        ),

                        iff(settingsController.isHomePageShowPopularTvShow(userSettingsEntity),
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
        ).getBytes();
    }
}
