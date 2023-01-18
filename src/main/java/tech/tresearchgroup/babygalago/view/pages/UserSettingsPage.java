package tech.tresearchgroup.babygalago.view.pages;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import tech.tresearchgroup.babygalago.controller.SettingsController;
import tech.tresearchgroup.babygalago.controller.controllers.NotificationEntityController;
import tech.tresearchgroup.babygalago.controller.controllers.QueueEntityController;
import tech.tresearchgroup.babygalago.view.components.HeadComponent;
import tech.tresearchgroup.babygalago.view.components.SideBarComponent;
import tech.tresearchgroup.babygalago.view.components.TopBarComponent;
import tech.tresearchgroup.palila.controller.components.CheckboxComponent;
import tech.tresearchgroup.palila.controller.components.DropDownBoxComponent;
import tech.tresearchgroup.palila.controller.components.InputBoxComponent;
import tech.tresearchgroup.palila.controller.components.PopoverComponent;
import tech.tresearchgroup.palila.model.EnumValuePair;
import tech.tresearchgroup.palila.model.enums.PermissionGroupEnum;
import tech.tresearchgroup.palila.model.enums.PlaybackQualityEnum;
import tech.tresearchgroup.schemas.galago.entities.ExtendedUserEntity;
import tech.tresearchgroup.schemas.galago.entities.UserSettingsEntity;
import tech.tresearchgroup.schemas.galago.enums.DisplayModeEnum;
import tech.tresearchgroup.schemas.galago.enums.InterfaceMethodEnum;

import java.sql.SQLException;
import java.util.List;

import static j2html.TagCreator.*;

@AllArgsConstructor
public class UserSettingsPage {
    private final SettingsController settingsController;
    private final NotificationEntityController notificationEntityController;


    public byte @NotNull [] render(boolean loggedIn, UserSettingsEntity userSettingsEntity, ExtendedUserEntity userEntity) throws SQLException {
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
                        label(settingsController.getServerName() + " Server Settings").withClass("overviewLabel"),
                        br(),
                        br(),
                        form(
                            div(
                                DropDownBoxComponent.render(
                                    "Interface method: ",
                                    "interfaceNetworkUsage",
                                    settingsController.getInterfaceMethod(userSettingsEntity),
                                    List.of(
                                        new EnumValuePair(InterfaceMethodEnum.MODAL, "Modal (high)"),
                                        new EnumValuePair(InterfaceMethodEnum.REDIRECT, "Redirect (low)")
                                    )
                                ),
                                PopoverComponent.renderRight("Using modals requires the server to send a lot more code, whereas redirects don't (useful for slow connections)"),
                                br(),
                                DropDownBoxComponent.render(
                                    "Default playback quality: ",
                                    "defaultPlaybackQuality",
                                    settingsController.getDefaultPlaybackQuality(userSettingsEntity),
                                    List.of(new EnumValuePair(PlaybackQualityEnum.oneFourFourP, "144P"),
                                        new EnumValuePair(PlaybackQualityEnum.twoFourZeroP, "240P"),
                                        new EnumValuePair(PlaybackQualityEnum.threeSixZeroP, "360P"),
                                        new EnumValuePair(PlaybackQualityEnum.fourEightZeroP, "480P"),
                                        new EnumValuePair(PlaybackQualityEnum.sevenTwoZeroP, "720P"),
                                        new EnumValuePair(PlaybackQualityEnum.oneZeroEightZeroP, "1080P"),
                                        new EnumValuePair(PlaybackQualityEnum.twoK, "2K"),
                                        new EnumValuePair(PlaybackQualityEnum.fourK, "4K"),
                                        new EnumValuePair(PlaybackQualityEnum.eightK, "8K"),
                                        new EnumValuePair(PlaybackQualityEnum.ORIGINAL, "Original")
                                    )
                                ),
                                PopoverComponent.renderRight("When you visit an item to play it, the player will automatically run at this quality"),
                                br(),
                                DropDownBoxComponent.render(
                                    "Display mode: ",
                                    "displayMode",
                                    settingsController.getDisplayMode(userSettingsEntity),
                                    List.of(
                                        new EnumValuePair(DisplayModeEnum.POSTER, "Poster"),
                                        new EnumValuePair(DisplayModeEnum.TABLE, "Table")
                                    )
                                ),
                                PopoverComponent.renderRight("The default is poster, table provides a spreadsheet like page"),
                                br(),
                                label("Show in table view:").withClass("subLabel"),
                                PopoverComponent.renderRight("When you're using the table view, these checkboxes customize the displayed columns"),
                                br(),
                                CheckboxComponent.render("showPoster", "Poster: ", settingsController.isTableShowPoster(userSettingsEntity)),
                                br(),
                                CheckboxComponent.render("showName", "Name: ", settingsController.isTableShowName(userSettingsEntity)),
                                br(),
                                CheckboxComponent.render("showRuntime", "Runtime: ", settingsController.isTableShowRuntime(userSettingsEntity)),
                                br(),
                                CheckboxComponent.render("showGenre", "Genre: ", settingsController.isTableShowGenre(userSettingsEntity)),
                                br(),
                                CheckboxComponent.render("showMpaaRating", "MPAA rating: ", settingsController.isTableShowMpaaRating(userSettingsEntity)),
                                br(),
                                CheckboxComponent.render("showUserRating", "User rating: ", settingsController.isTableShowUserRating(userSettingsEntity)),
                                br(),
                                CheckboxComponent.render("showLanguage", "Language: ", settingsController.isTableShowLanguage(userSettingsEntity)),
                                br(),
                                CheckboxComponent.render("showReleaseDate", "Release date: ", settingsController.isTableShowReleaseDate(userSettingsEntity)),
                                br(),
                                CheckboxComponent.render("showActions", "Actions: ", settingsController.isTableShowActions(userSettingsEntity)),
                                br(),
                                label("Home page:").withClass("topicLabel"),
                                PopoverComponent.renderRight("What to show on the home page"),
                                br(),
                                br(),
                                label("New:").withClass("subLabel"),
                                br(),
                                CheckboxComponent.render("showNewBooks", "Books: ", settingsController.isHomePageShowNewBook(userSettingsEntity)),
                                CheckboxComponent.render("showNewGames", "Games: ", settingsController.isHomePageShowNewGame(userSettingsEntity)),
                                CheckboxComponent.render("showNewMovies", "Movies: ", settingsController.isHomePageShowNewMovie(userSettingsEntity)),
                                CheckboxComponent.render("showNewMusic", "Music: ", settingsController.isHomePageShowNewMusic(userSettingsEntity)),
                                CheckboxComponent.render("showNewTvShows", "TV Shows: ", settingsController.isHomePageShowNewTvShow(userSettingsEntity)),
                                br(),
                                label("Popular:").withClass("subLabel"),
                                br(),
                                CheckboxComponent.render("showPopularBooks", "Books: ", settingsController.isHomePageShowPopularBook(userSettingsEntity)),
                                CheckboxComponent.render("showPopularGames", "Games: ", settingsController.isHomePageShowPopularGame(userSettingsEntity)),
                                CheckboxComponent.render("showPopularMovies", "Movies: ", settingsController.isHomePageShowPopularMovie(userSettingsEntity)),
                                CheckboxComponent.render("showPopularMusic", "Music: ", settingsController.isHomePageShowPopularMusic(userSettingsEntity)),
                                CheckboxComponent.render("showPopularTvShows", "TV Shows: ", settingsController.isHomePageShowPopularTvShow(userSettingsEntity)),
                                br(),
                                InputBoxComponent.render("maxSearchResults", "Max results: ", String.valueOf(settingsController.getMaxSearchResults(userSettingsEntity))),
                                PopoverComponent.renderRight("The amount of items to display per page"),
                                br(),
                                label("Browse:").withClass("topicLabel"),
                                br(),
                                br(),
                                InputBoxComponent.render("maxBrowseResults", "Max results: ", String.valueOf(settingsController.getMaxBrowseResults(userSettingsEntity))),
                                PopoverComponent.renderRight("The amount of items to display when browsing"),
                                br(),
                                InputBoxComponent.render("cardWidth", "Card width: ", String.valueOf(settingsController.getCardWidth(userSettingsEntity))),
                                PopoverComponent.renderRight("The width of item cards"),
                                br(),
                                CheckboxComponent.render("stickTopMenu", "Sticky top menu: ", settingsController.isStickyTopMenu(userSettingsEntity)),
                                PopoverComponent.renderRight("Whether to keep the top menu always accessible, or make it stay at the top of the page")
                            ),
                            br(),
                            button("Save").withType("submit")
                        ).withAction("/user/settings").withMethod("POST"),
                        br(),
                        br()
                    ).withClass("body")
                )
            )
        ).getBytes();
    }
}
