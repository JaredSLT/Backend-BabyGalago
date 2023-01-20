package tech.tresearchgroup.babygalago.view.pages;

import lombok.AllArgsConstructor;
import tech.tresearchgroup.babygalago.controller.controllers.QueueEntityController;
import tech.tresearchgroup.babygalago.view.components.HeadComponent;
import tech.tresearchgroup.babygalago.view.components.SideBarComponent;
import tech.tresearchgroup.babygalago.view.components.TopBarComponent;
import tech.tresearchgroup.cao.controller.GenericCAO;
import tech.tresearchgroup.palila.controller.components.CheckboxComponent;
import tech.tresearchgroup.palila.controller.components.DropDownBoxComponent;
import tech.tresearchgroup.palila.controller.components.InputBoxComponent;
import tech.tresearchgroup.palila.controller.components.PopoverComponent;
import tech.tresearchgroup.palila.model.EnumValuePair;
import tech.tresearchgroup.palila.model.enums.PermissionGroupEnum;
import tech.tresearchgroup.palila.model.enums.PlaybackQualityEnum;
import tech.tresearchgroup.palila.view.RenderablePage;
import tech.tresearchgroup.schemas.galago.enums.DisplayModeEnum;
import tech.tresearchgroup.schemas.galago.enums.InterfaceMethodEnum;

import java.util.LinkedList;
import java.util.List;

import static j2html.TagCreator.*;

@AllArgsConstructor
public class UserSettingsPage implements RenderablePage {
    /**
     * Renders the settings for the user account
     * @param loggedIn whether the user is logged in
     * @param unreadCount the number of unread notifications
     * @param permissionGroupEnum the permission group of the user account
     * @param serverName the name of the server
     * @param isEnableUpload if file upload is enabled
     * @param isMovieLibraryEnable if the movie library is enabled
     * @param isTvShowLibraryEnable if the tv show library is enabled
     * @param isGameLibraryEnable if the game library is enabled
     * @param isMusicLibraryEnable if the music library is enabled
     * @param isBookLibraryEnable if the book library is enabled
     * @param interfaceMethodEnum the interface method to use
     * @param playbackQualityEnum the default video playback quality
     * @param displayModeEnum the display mode (poster, table)
     * @param isTableShowPoster whether to show the poster in the table
     * @param isTableShowName whether to show the name in the table
     * @param isTableShowRuntime whether to show the runtime in the table
     * @param isTableShowGenre whether to show the genre in the table
     * @param isTableShowMpaaRating whether to show the MPAA rating in the table
     * @param isTableShowUserRating whether to show the user rating in the table
     * @param isTableShowLanguage whether to show the language in the table
     * @param isTableShowReleaseDate whether to show the release date in the table
     * @param isTableShowActions whether to show the actions in the table
     * @param isHomePageShowNewBook whether to show new books on the home page
     * @param isHomePageShowNewGame whether to show new games on the home page
     * @param isHomePageShowNewMovie whether to show new movies on the home page
     * @param isHomePageShowNewMusic whether to show new music on the home page
     * @param isHomePageShowNewTvShow whether to show new tv shows on the home page
     * @param isHomePageShowPopularBook whether to show popular books on the home page
     * @param isHomePageShowPopularGame whether to show popular games on the home page
     * @param isHomePageShowPopularMovie whether to show popular movies on the home page
     * @param isHomePageShowPopularMusic whether to show popular music on the home page
     * @param isHomePageShowPopularTvShow whether to show popular tv shows on the home page
     * @param maxSearchResults the number of entities to show while searching
     * @param maxBrowseResults the number of entities to show while browsing
     * @param cardWidth the width of the card in pixels
     * @param isStickyTopMenu whether the top menu bar should stick to the top of the page
     * @return the settings page as a string
     */
    public String render(boolean loggedIn,
                         long unreadCount,
                         PermissionGroupEnum permissionGroupEnum,
                         String serverName,
                         boolean isEnableUpload,
                         boolean isMovieLibraryEnable,
                         boolean isTvShowLibraryEnable,
                         boolean isGameLibraryEnable,
                         boolean isMusicLibraryEnable,
                         boolean isBookLibraryEnable,
                         InterfaceMethodEnum interfaceMethodEnum,
                         PlaybackQualityEnum playbackQualityEnum,
                         DisplayModeEnum displayModeEnum,
                         boolean isTableShowPoster,
                         boolean isTableShowName,
                         boolean isTableShowRuntime,
                         boolean isTableShowGenre,
                         boolean isTableShowMpaaRating,
                         boolean isTableShowUserRating,
                         boolean isTableShowLanguage,
                         boolean isTableShowReleaseDate,
                         boolean isTableShowActions,
                         boolean isHomePageShowNewBook,
                         boolean isHomePageShowNewGame,
                         boolean isHomePageShowNewMovie,
                         boolean isHomePageShowNewMusic,
                         boolean isHomePageShowNewTvShow,
                         boolean isHomePageShowPopularBook,
                         boolean isHomePageShowPopularGame,
                         boolean isHomePageShowPopularMovie,
                         boolean isHomePageShowPopularMusic,
                         boolean isHomePageShowPopularTvShow,
                         String maxSearchResults,
                         String maxBrowseResults,
                         String cardWidth,
                         boolean isStickyTopMenu,
                         GenericCAO genericCAO) {
        return document(
            html(
                HeadComponent.render(serverName, genericCAO),
                TopBarComponent.render(unreadCount, QueueEntityController.getQueueSize(), loggedIn, permissionGroupEnum, isEnableUpload),
                SideBarComponent.render(
                    loggedIn,
                    isMovieLibraryEnable,
                    isTvShowLibraryEnable,
                    isGameLibraryEnable,
                    isMusicLibraryEnable,
                    isBookLibraryEnable,
                    genericCAO
                ),
                body(
                    div(
                        label(serverName + " Server Settings").withClass("overviewLabel"),
                        br(),
                        br(),
                        form(
                            div(
                                DropDownBoxComponent.render(
                                    "Interface method: ",
                                    "interfaceNetworkUsage",
                                    interfaceMethodEnum,
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
                                    playbackQualityEnum,
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
                                    displayModeEnum,
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
                                CheckboxComponent.render("showPoster", "Poster: ", isTableShowPoster),
                                br(),
                                CheckboxComponent.render("showName", "Name: ", isTableShowName),
                                br(),
                                CheckboxComponent.render("showRuntime", "Runtime: ", isTableShowRuntime),
                                br(),
                                CheckboxComponent.render("showGenre", "Genre: ", isTableShowGenre),
                                br(),
                                CheckboxComponent.render("showMpaaRating", "MPAA rating: ", isTableShowMpaaRating),
                                br(),
                                CheckboxComponent.render("showUserRating", "User rating: ", isTableShowUserRating),
                                br(),
                                CheckboxComponent.render("showLanguage", "Language: ", isTableShowLanguage),
                                br(),
                                CheckboxComponent.render("showReleaseDate", "Release date: ", isTableShowReleaseDate),
                                br(),
                                CheckboxComponent.render("showActions", "Actions: ", isTableShowActions),
                                br(),
                                label("Home page:").withClass("topicLabel"),
                                PopoverComponent.renderRight("What to show on the home page"),
                                br(),
                                br(),
                                label("New:").withClass("subLabel"),
                                br(),
                                CheckboxComponent.render("showNewBooks", "Books: ", isHomePageShowNewBook),
                                CheckboxComponent.render("showNewGames", "Games: ", isHomePageShowNewGame),
                                CheckboxComponent.render("showNewMovies", "Movies: ", isHomePageShowNewMovie),
                                CheckboxComponent.render("showNewMusic", "Music: ", isHomePageShowNewMusic),
                                CheckboxComponent.render("showNewTvShows", "TV Shows: ", isHomePageShowNewTvShow),
                                br(),
                                label("Popular:").withClass("subLabel"),
                                br(),
                                CheckboxComponent.render("showPopularBooks", "Books: ", isHomePageShowPopularBook),
                                CheckboxComponent.render("showPopularGames", "Games: ", isHomePageShowPopularGame),
                                CheckboxComponent.render("showPopularMovies", "Movies: ", isHomePageShowPopularMovie),
                                CheckboxComponent.render("showPopularMusic", "Music: ", isHomePageShowPopularMusic),
                                CheckboxComponent.render("showPopularTvShows", "TV Shows: ", isHomePageShowPopularTvShow),
                                br(),
                                InputBoxComponent.render("maxSearchResults", "Max results: ", maxSearchResults),
                                PopoverComponent.renderRight("The amount of items to display per page"),
                                br(),
                                label("Browse:").withClass("topicLabel"),
                                br(),
                                br(),
                                InputBoxComponent.render("maxBrowseResults", "Max results: ", maxBrowseResults),
                                PopoverComponent.renderRight("The amount of items to display when browsing"),
                                br(),
                                InputBoxComponent.render("cardWidth", "Card width: ", cardWidth),
                                PopoverComponent.renderRight("The width of item cards"),
                                br(),
                                CheckboxComponent.render("stickTopMenu", "Sticky top menu: ", isStickyTopMenu),
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
        );
    }

    /**
     * Renders out the page with dummy data
     * @return the page as a string
     */
    @Override
    public List<String> render() {
        /*try {
            return render(
                true,
                notificationEntityController.getNumberOfUnread(user),
                user.getPermissionGroup(),
                settingsController.getServerName(),
                settingsController.isEnableUpload(),
                settingsController.isMovieLibraryEnable(),
                settingsController.isTvShowLibraryEnable(),
                settingsController.isGameLibraryEnable(),
                settingsController.isMusicLibraryEnable(),
                settingsController.isBookLibraryEnable(),
                settingsController.getInterfaceMethod(userSettingsEntity),
                settingsController.getDefaultPlaybackQuality(userSettingsEntity),
                settingsController.getDisplayMode(userSettingsEntity),
                settingsController.isTableShowPoster(userSettingsEntity),
                settingsController.isTableShowName(userSettingsEntity),
                settingsController.isTableShowRuntime(userSettingsEntity),
                settingsController.isTableShowGenre(userSettingsEntity),
                settingsController.isTableShowMpaaRating(userSettingsEntity),
                settingsController.isTableShowUserRating(userSettingsEntity),
                settingsController.isTableShowLanguage(userSettingsEntity),
                settingsController.isTableShowReleaseDate(userSettingsEntity),
                settingsController.isTableShowActions(userSettingsEntity),
                settingsController.isHomePageShowNewBook(userSettingsEntity),
                settingsController.isHomePageShowNewGame(userSettingsEntity),
                settingsController.isHomePageShowNewMovie(userSettingsEntity),
                settingsController.isHomePageShowNewMusic(userSettingsEntity),
                settingsController.isHomePageShowPopularTvShow(userSettingsEntity),
                settingsController.isHomePageShowPopularBook(userSettingsEntity),
                settingsController.isHomePageShowPopularGame(userSettingsEntity),
                settingsController.isHomePageShowPopularMovie(userSettingsEntity),
                settingsController.isHomePageShowPopularMusic(userSettingsEntity),
                settingsController.isHomePageShowNewTvShow(userSettingsEntity),
                String.valueOf(settingsController.getMaxSearchResults(userSettingsEntity)),
                String.valueOf(settingsController.getMaxBrowseResults(userSettingsEntity)),
                String.valueOf(settingsController.getCardWidth(userSettingsEntity)),
                settingsController.isStickyTopMenu(userSettingsEntity)
            );
        } catch (SQLException e) {
            return null;
        }*/
        return new LinkedList<>();
    }
}
