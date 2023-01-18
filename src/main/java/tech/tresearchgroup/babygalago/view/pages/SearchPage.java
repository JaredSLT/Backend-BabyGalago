package tech.tresearchgroup.babygalago.view.pages;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import tech.tresearchgroup.babygalago.controller.SettingsController;
import tech.tresearchgroup.babygalago.controller.controllers.NotificationEntityController;
import tech.tresearchgroup.babygalago.controller.controllers.QueueEntityController;
import tech.tresearchgroup.babygalago.view.components.HeadComponent;
import tech.tresearchgroup.babygalago.view.components.SideBarComponent;
import tech.tresearchgroup.babygalago.view.components.TopBarComponent;
import tech.tresearchgroup.palila.controller.components.EditableScrollingComponent;
import tech.tresearchgroup.palila.model.Card;
import tech.tresearchgroup.palila.model.enums.PermissionGroupEnum;
import tech.tresearchgroup.schemas.galago.entities.ExtendedUserEntity;

import java.sql.SQLException;
import java.util.List;

import static j2html.TagCreator.*;

@AllArgsConstructor
public class SearchPage {
    private final SettingsController settingsController;
    private final NotificationEntityController notificationEntityController;

    public byte @NotNull [] render(boolean loggedIn,
                                   List<Card> movieCards,
                                   List<Card> tvShowCards,
                                   List<Card> gameCards,
                                   List<Card> musicCards,
                                   List<Card> bookCards,
                                   long timeTaken,
                                   int size,
                                   ExtendedUserEntity userEntity) throws SQLException {
        PermissionGroupEnum permissionGroupEnum = PermissionGroupEnum.ALL;
        if (userEntity != null) {
            permissionGroupEnum = userEntity.getPermissionGroup();
        }
        return document(
            html(
                HeadComponent.render(settingsController.getServerName()),
                TopBarComponent.render(notificationEntityController.getNumberOfUnread(userEntity), QueueEntityController.getQueueSize(), loggedIn, permissionGroupEnum, settingsController.isEnableUpload()),
                SideBarComponent.render(loggedIn,
                    settingsController.isMovieLibraryEnable(),
                    settingsController.isTvShowLibraryEnable(),
                    settingsController.isGameLibraryEnable(),
                    settingsController.isMusicLibraryEnable(),
                    settingsController.isBookLibraryEnable()),
                body(
                    div(
                        label("Search results for: SEARCH").withClass("overviewLabel"),
                        br(),
                        br(),
                        EditableScrollingComponent.render(false, "Movies", movieCards, "/add/movieentity", size),
                        br(),
                        EditableScrollingComponent.render(false, "TV Shows", tvShowCards, "/add/tvshowentity", size),
                        br(),
                        EditableScrollingComponent.render(false, "Games", gameCards, "/add/gameentity", size),
                        br(),
                        EditableScrollingComponent.render(false, "Music", musicCards, "/add/songentity", size),
                        br(),
                        EditableScrollingComponent.render(false, "Books", bookCards, "/add/bookentity", size),
                        label("Time taken: " + timeTaken + "ms").withClass("subLabel"),
                        br(),
                        br(),
                        br()
                    ).withClass("body")
                )
            )
        ).getBytes();
    }
}
