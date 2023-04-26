package tech.tresearchgroup.babygalago.controller.controllers;

import com.google.gson.Gson;
import com.meilisearch.sdk.Client;
import com.zaxxer.hikari.HikariDataSource;
import io.activej.http.HttpRequest;
import io.activej.http.HttpResponse;
import io.activej.promise.Promisable;
import io.activej.serializer.BinarySerializer;
import tech.tresearchgroup.babygalago.controller.SettingsController;
import tech.tresearchgroup.babygalago.view.pages.UserSettingsPage;
import tech.tresearchgroup.palila.controller.GenericController;
import tech.tresearchgroup.palila.model.Card;
import tech.tresearchgroup.palila.model.enums.PermissionGroupEnum;
import tech.tresearchgroup.palila.model.enums.PlaybackQualityEnum;
import tech.tresearchgroup.schemas.galago.entities.ExtendedUserEntity;
import tech.tresearchgroup.schemas.galago.entities.UserSettingsEntity;
import tech.tresearchgroup.schemas.galago.enums.DisplayModeEnum;
import tech.tresearchgroup.schemas.galago.enums.InterfaceMethodEnum;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Objects;

public class UserSettingsEntityController extends GenericController {
    private final ExtendedUserEntityController extendedUserEntityController;
    private final NotificationEntityController notificationEntityController;
    private final SettingsController settingsController;
    private final UserSettingsPage userSettingsPage;

    /**
     * Sets up the user settings entity controller. To understand this class better, have a look at the class it extends (GenericController)
     */

    public UserSettingsEntityController(HikariDataSource hikariDataSource,
                                        Gson gson,
                                        Client client,
                                        BinarySerializer<UserSettingsEntity> serializer,
                                        int reindexSize,
                                        Object object,
                                        ExtendedUserEntityController extendedUserEntityController,
                                        NotificationEntityController notificationEntityController,
                                        SettingsController settingsController,
                                        UserSettingsPage userSettingsPage) throws Exception {
        super(
            hikariDataSource,
            gson,
            client,
            UserSettingsEntity.class,
            serializer,
            reindexSize,
            null,
            object,
            PermissionGroupEnum.USER,
            PermissionGroupEnum.USER,
            PermissionGroupEnum.USER,
            PermissionGroupEnum.USER,
            PermissionGroupEnum.USER,
            extendedUserEntityController,
            new Card()
        );
        this.extendedUserEntityController = extendedUserEntityController;
        this.notificationEntityController = notificationEntityController;
        this.settingsController = settingsController;
        this.userSettingsPage = userSettingsPage;
    }

    /**
     * Reads the users settings
     *
     * @param httpRequest the request
     * @return the response page
     * @throws SQLException              if it fails to load from the database
     * @throws InvocationTargetException if it fails to parse
     * @throws InstantiationException    orm failure
     * @throws IllegalAccessException    orm failure
     * @throws IOException               orm failure
     */
    public Promisable<HttpResponse> read(HttpRequest httpRequest) throws SQLException, InvocationTargetException, InstantiationException, IllegalAccessException, IOException {
        ExtendedUserEntity user = (ExtendedUserEntity) getUser(httpRequest, extendedUserEntityController);
        UserSettingsEntity userSettingsEntity = user.getUserSettings();
        if (userSettingsEntity == null) {
            userSettingsEntity = new UserSettingsEntity();
        }
        return ok(
            userSettingsPage.render(
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
            ).getBytes()
        );
    }

    /**
     * Creates a users settings
     *
     * @param httpRequest the request
     * @return the response page
     */
    public Promisable<HttpResponse> create(HttpRequest httpRequest) {
        try {
            InterfaceMethodEnum interfaceNetworkUsage = InterfaceMethodEnum.valueOf(httpRequest.getPostParameter("interfaceNetworkUsage"));
            PlaybackQualityEnum defaultPlaybackQuality = PlaybackQualityEnum.valueOf(httpRequest.getPostParameter("defaultPlaybackQuality"));
            DisplayModeEnum displayMode = DisplayModeEnum.valueOf(httpRequest.getPostParameter("displayMode"));
            boolean showPoster = Objects.equals(httpRequest.getPostParameter("showPoster"), "on");
            boolean showName = Objects.equals(httpRequest.getPostParameter("showName"), "on");
            boolean showRuntime = Objects.equals(httpRequest.getPostParameter("showRuntime"), "on");
            boolean showGenre = Objects.equals(httpRequest.getPostParameter("showGenre"), "on");
            boolean showMpaaRating = Objects.equals(httpRequest.getPostParameter("showMpaaRating"), "on");
            boolean showUserRating = Objects.equals(httpRequest.getPostParameter("showUserRating"), "on");
            boolean showLanguage = Objects.equals(httpRequest.getPostParameter("showLanguage"), "on");
            boolean showReleaseDate = Objects.equals(httpRequest.getPostParameter("showReleaseDate"), "on");
            boolean showActions = Objects.equals(httpRequest.getPostParameter("showActions"), "on");
            boolean showNewBooks = Objects.equals(httpRequest.getPostParameter("showNewBooks"), "on");
            boolean showNewGames = Objects.equals(httpRequest.getPostParameter("showNewGames"), "on");
            boolean showNewMovies = Objects.equals(httpRequest.getPostParameter("showNewMovies"), "on");
            boolean showNewMusic = Objects.equals(httpRequest.getPostParameter("showNewMusic"), "on");
            boolean showNewTvShows = Objects.equals(httpRequest.getPostParameter("showNewTvShows"), "on");
            boolean showPopularBooks = Objects.equals(httpRequest.getPostParameter("showPopularBooks"), "on");
            boolean showPopularGames = Objects.equals(httpRequest.getPostParameter("showPopularGames"), "on");
            boolean showPopularMovies = Objects.equals(httpRequest.getPostParameter("showPopularMovies"), "on");
            boolean showPopularMusic = Objects.equals(httpRequest.getPostParameter("showPopularMusic"), "on");
            boolean showPopularTvShows = Objects.equals(httpRequest.getPostParameter("showPopularTvShows"), "on");
            int maxSearchResults = Integer.parseInt(Objects.requireNonNull(httpRequest.getPostParameter("maxSearchResults")));
            int maxBrowseResults = Integer.parseInt(Objects.requireNonNull(httpRequest.getPostParameter("maxBrowseResults")));
            int cardWidth = Integer.parseInt(Objects.requireNonNull(httpRequest.getPostParameter("cardWidth")));
            boolean stickyTopMenu = Boolean.parseBoolean(httpRequest.getPostParameter("stickTopMenu"));

            UserSettingsEntity userSettingsEntity = new UserSettingsEntity(
                interfaceNetworkUsage,
                defaultPlaybackQuality,
                displayMode,
                showPoster,
                showName,
                showRuntime,
                showGenre,
                showMpaaRating,
                showUserRating,
                showLanguage,
                showReleaseDate,
                showActions,
                showNewMovies,
                showNewTvShows,
                showNewGames,
                showNewBooks,
                showNewMusic,
                showPopularMovies,
                showPopularTvShows,
                showPopularGames,
                showPopularBooks,
                showPopularMusic,
                maxSearchResults,
                maxBrowseResults,
                cardWidth,
                stickyTopMenu
            );
            //Todo create settings for the specified user and display form
            return null;
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
            return redirect("/error");
        }
    }
}
