package tech.tresearchgroup.babygalago.controller.endpoints.ui;

import io.activej.csp.file.ChannelFileWriter;
import io.activej.http.HttpRequest;
import io.activej.http.HttpResponse;
import io.activej.http.MultipartDecoder;
import io.activej.promise.Promisable;
import lombok.AllArgsConstructor;
import org.bouncycastle.crypto.generators.BCrypt;
import org.bouncycastle.util.encoders.Hex;
import org.jetbrains.annotations.NotNull;
import tech.tresearchgroup.babygalago.controller.CardConverter;
import tech.tresearchgroup.babygalago.controller.SettingsController;
import tech.tresearchgroup.babygalago.controller.controllers.*;
import tech.tresearchgroup.babygalago.view.pages.*;
import tech.tresearchgroup.palila.controller.BasicController;
import tech.tresearchgroup.palila.controller.CompressionController;
import tech.tresearchgroup.palila.model.Card;
import tech.tresearchgroup.palila.model.RegistrationErrorsEnum;
import tech.tresearchgroup.palila.model.entities.AudioFileEntity;
import tech.tresearchgroup.palila.model.entities.BookFileEntity;
import tech.tresearchgroup.palila.model.entities.GameFileEntity;
import tech.tresearchgroup.palila.model.entities.VideoFileEntity;
import tech.tresearchgroup.palila.model.enums.*;
import tech.tresearchgroup.schemas.galago.entities.*;
import tech.tresearchgroup.schemas.galago.enums.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static java.util.concurrent.Executors.newSingleThreadExecutor;

@AllArgsConstructor
public class MainEndpointsController extends BasicController {
    private final MovieEntityController movieEntityController;
    private final TvShowEntityController tvShowEntityController;
    private final GameEntityController gameEntityController;
    private final SongEntityController songEntityController;
    private final BookEntityController bookEntityController;
    private final NotificationEntityController notificationEntityController;
    private final NewsArticleEntityController newsArticleEntityController;
    private final QueueEntityController queueEntityController;
    private final UserEntityController userEntityController;
    private final SettingsController settingsController;
    private final AboutPage aboutPage;
    private final IndexPage indexPage;
    private final LoginPage loginPage;
    private final ResetPage resetPage;
    private final RegisterPage registerPage;
    private final LicensesPage licensesPage;
    private final NewsPage newsPage;
    private final NotificationsPage notificationsPage;
    private final ProfilePage profilePage;
    private final QueuePage queuePage;
    private final SearchPage searchPage;
    private final SettingsPage settingsPage;
    private final UploadPage uploadPage;
    private final DeniedPage deniedPage;
    private final DisabledPage disabledPage;

    public HttpResponse about(HttpRequest httpRequest) throws IOException, SQLException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        boolean loggedIn = verifyApiKey(httpRequest);
        ExtendedUserEntity userEntity = (ExtendedUserEntity) getUser(httpRequest, userEntityController);
        return ok(aboutPage.render(loggedIn, userEntity));
    }

    public HttpResponse index(HttpRequest httpRequest) throws IOException, SQLException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        ExtendedUserEntity userEntity = (ExtendedUserEntity) getUser(httpRequest, userEntityController);
        UserSettingsEntity userSettingsEntity = null;
        List<Card> newBooksCards = null;
        List<Card> popularBooksCards = null;
        List<Card> newGamesCards = null;
        List<Card> popularGamesCards = null;
        List<Card> newMoviesCards = null;
        List<Card> popularMoviesCards = null;
        List<Card> newMusicCards = null;
        List<Card> popularMusicCards = null;
        List<Card> newTvShowsCards = null;
        List<Card> popularTvShowsCards = null;
        if (userEntity != null) {
            userSettingsEntity = userEntity.getUserSettings();
            List<String> orderBy = new LinkedList<>();
            orderBy.add("id");
            orderBy.add("views");
            List<Class> theClassList = new LinkedList<>();
            theClassList.add(BookEntity.class);
            theClassList.add(GameEntity.class);
            theClassList.add(MovieEntity.class);
            theClassList.add(SongEntity.class);
            theClassList.add(TvShowEntity.class);
            List<String> data = bookEntityController.readManyOrderByPaginated(settingsController.getMaxBrowseResults(userSettingsEntity), 0, orderBy, theClassList, false, httpRequest);
            List<BookEntity> newBooks = (List<BookEntity>) bookEntityController.getFromReadMany("id", BookEntity.class, data, false);
            List<BookEntity> popularBooks = (List<BookEntity>) bookEntityController.getFromReadMany("views", BookEntity.class, data, false);
            List<GameEntity> newGames = (List<GameEntity>) bookEntityController.getFromReadMany("id", BookEntity.class, data, false);
            List<GameEntity> popularGames = (List<GameEntity>) bookEntityController.getFromReadMany("views", GameEntity.class, data, false);
            List<MovieEntity> newMovies = (List<MovieEntity>) movieEntityController.getFromReadMany("id", MovieEntity.class, data, false);
            List<MovieEntity> popularMovies = (List<MovieEntity>) movieEntityController.getFromReadMany("views", MovieEntity.class, data, false);
            List<SongEntity> newMusic = (List<SongEntity>) bookEntityController.getFromReadMany("id", SongEntity.class, data, false);
            List<SongEntity> popularMusic = (List<SongEntity>) bookEntityController.getFromReadMany("views", SongEntity.class, data, false);
            List<TvShowEntity> newTv = (List<TvShowEntity>) bookEntityController.getFromReadMany("id", TvShowEntity.class, data, false);
            List<TvShowEntity> popularTv = (List<TvShowEntity>) bookEntityController.getFromReadMany("views", TvShowEntity.class, data, false);
            newBooksCards = CardConverter.convertBooks(newBooks, "id");
            popularBooksCards = CardConverter.convertBooks(popularBooks, "views");
            newGamesCards = CardConverter.convertGames(newGames, "id");
            popularGamesCards = CardConverter.convertGames(popularGames, "views");
            newMoviesCards = CardConverter.convertMovies(newMovies, "id");
            popularMoviesCards = CardConverter.convertMovies(popularMovies, "views");
            newMusicCards = CardConverter.convertSongs(newMusic, "id");
            popularMusicCards = CardConverter.convertSongs(popularMusic, "views");
            newTvShowsCards = CardConverter.convertTvShows(newTv, "id");
            popularTvShowsCards = CardConverter.convertTvShows(popularTv, "views");
        }
        boolean loggedIn = verifyApiKey(httpRequest);
        byte[] data = indexPage.render(loggedIn, settingsController.getCardWidth(userSettingsEntity), newBooksCards, popularBooksCards, newGamesCards, popularGamesCards, newMoviesCards, popularMoviesCards, newMusicCards, popularMusicCards, newTvShowsCards, popularTvShowsCards, userEntity);
        byte[] compressed = CompressionController.compress(data);
        return okResponseCompressed(compressed);
    }

    public HttpResponse login(HttpRequest httpRequest) throws IOException, SQLException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        String error = httpRequest.getQueryParameter("error");
        boolean isError = false;
        if (error != null) {
            if (error.equals("")) {
                isError = true;
            }
        }
        boolean loggedIn = verifyApiKey(httpRequest);
        ExtendedUserEntity userEntity = (ExtendedUserEntity) getUser(httpRequest, userEntityController);
        return ok(loginPage.render(isError, loggedIn, userEntity));
    }

    public HttpResponse reset(HttpRequest httpRequest) throws IOException, SQLException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        boolean loggedIn = verifyApiKey(httpRequest);
        ExtendedUserEntity userEntity = (ExtendedUserEntity) getUser(httpRequest, userEntityController);
        String error = httpRequest.getQueryParameter("error");
        String success = httpRequest.getQueryParameter("success");
        String confirmationData = httpRequest.getQueryParameter("confirmation");
        boolean isError = false;
        boolean isSuccess = false;
        boolean wasConfirmed = false;
        if (error != null) {
            if (error.equals("")) {
                isError = true;
            }
        }
        if (success != null) {
            if (success.equals("")) {
                isSuccess = true;
            }
        }
        if (confirmationData != null) {
            if (!confirmationData.equals("")) {
                wasConfirmed = true;
            }
        }
        return ok(resetPage.render(loggedIn, userEntity, isError, isSuccess, wasConfirmed, confirmationData));
    }

    public HttpResponse postReset(HttpRequest httpRequest) throws SQLException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        boolean loggedIn = verifyApiKey(httpRequest);
        ExtendedUserEntity userEntity = (ExtendedUserEntity) getUser(httpRequest, userEntityController);
        String email = httpRequest.getPostParameter("email");
        String password = httpRequest.getPostParameter("password");
        String passwordConfirm = httpRequest.getPostParameter("passwordConfirm");
        String confirmationData = httpRequest.getPostParameter("confirmation");
        if (confirmationData != null) {
            if (!confirmationData.equals("")) {
                return redirect("/reset?confirmation=" + confirmationData + "&success");
            }
        }
        return redirect("/reset?confirmation=123");
    }

    public HttpResponse register(HttpRequest httpRequest) throws IOException, SQLException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        String error = httpRequest.getQueryParameter("error");
        ExtendedUserEntity userEntity = (ExtendedUserEntity) getUser(httpRequest, userEntityController);
        if (error == null) {
            return ok(registerPage.render(null, userEntity));
        }
        return ok(registerPage.render(RegistrationErrorsEnum.valueOf(error), userEntity));
    }

    public @NotNull Promisable<HttpResponse> postRegister(@NotNull HttpRequest httpRequest) throws SQLException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        String username = httpRequest.getPostParameter("username");
        String email = httpRequest.getPostParameter("email");
        String emailConfirm = httpRequest.getPostParameter("emailConfirm");
        String password = httpRequest.getPostParameter("password");
        String passwordConfirm = httpRequest.getPostParameter("passwordConfirm");
        if (password.equals(passwordConfirm)) {
            if (password.length() < 10) {
                return redirect("/register?error=PASSWORD_LENGTH");
            }
            if (email.equals(emailConfirm)) {
                String[] atParts = email.split("@");
                String[] periodParts = email.split("\\.");
                if (!email.contains("@") || !email.contains(".") || atParts.length != 2 || periodParts.length > 2) {
                    return redirect("/register?error=INCORRECT_EMAIL");
                }
                if (userEntityController.getUserByUsername(username) != null) {
                    return redirect("/register?error=USERNAME_TAKEN");
                }
                ExtendedUserEntity userEntity = new ExtendedUserEntity();
                userEntity.setUsername(username);
                userEntity.setPassword(hashPassword(password));
                userEntity.setEmail(email);
                userEntity.setPermissionGroup(PermissionGroupEnum.USER);
                if (userEntityController.createSecureResponse(userEntity, httpRequest) != null) {
                    return HttpResponse.redirect301("/");
                }
                return redirect("/register?error=ERROR_500");
            }
            return redirect("/register?error=EMAIL_MATCH");
        }
        return redirect("/register?error=PASSWORD_MATCH");
    }

    private String hashPassword(String password) {
        byte[] salt = new byte[16];
        return new String(Hex.encode(BCrypt.generate(password.getBytes(StandardCharsets.UTF_8), salt, 8)));
    }

    public HttpResponse licenses(HttpRequest httpRequest) throws IOException, SQLException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        boolean loggedIn = verifyApiKey(httpRequest);
        ExtendedUserEntity userEntity = (ExtendedUserEntity) getUser(httpRequest, userEntityController);
        return ok(licensesPage.render(loggedIn, userEntity));
    }

    public HttpResponse news(HttpRequest httpRequest) throws IOException, SQLException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        UserSettingsEntity userSettingsEntity = null;
        ExtendedUserEntity userEntity = (ExtendedUserEntity) getUser(httpRequest, userEntityController);
        if (userEntity != null) {
            userSettingsEntity = userEntity.getUserSettings();
        }
        int maxResults = settingsController.getMaxBrowseResults(userSettingsEntity);
        int page = httpRequest.getQueryParameter("page") != null ? Integer.parseInt(Objects.requireNonNull(httpRequest.getQueryParameter("page"))) : 0;
        Long maxPage = newsArticleEntityController.getTotalPages(maxResults, httpRequest);
        boolean loggedIn = verifyApiKey(httpRequest);
        return ok(newsPage.render(loggedIn, page, maxPage, userEntity));
    }

    public HttpResponse notifications(HttpRequest httpRequest) throws IOException, SQLException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        int page = httpRequest.getQueryParameter("page") != null ? Integer.parseInt(Objects.requireNonNull(httpRequest.getQueryParameter("page"))) : 0;
        ExtendedUserEntity userEntity = (ExtendedUserEntity) getUser(httpRequest, userEntityController);
        if (userEntity != null) {
            long maxPage = notificationEntityController.getTotalPages(settingsController.getMaxBrowseResults(userEntity.getUserSettings()), httpRequest);
            List<NotificationEntity> notificationEntityList = notificationEntityController.readPaginatedResponse((int) maxPage, page, false, httpRequest);
            boolean loggedIn = verifyApiKey(httpRequest);
            return ok(notificationsPage.render(page, maxPage, notificationEntityList, loggedIn, userEntity));
        } else {
            return error();
        }
    }

    public HttpResponse profile(HttpRequest httpRequest) throws IOException, SQLException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        boolean loggedIn = verifyApiKey(httpRequest);
        ExtendedUserEntity userEntity = (ExtendedUserEntity) getUser(httpRequest, userEntityController);
        return ok(profilePage.render(loggedIn, userEntity));
    }

    public HttpResponse postProfile(HttpRequest httpRequest) throws Exception {
        ExtendedUserEntity userEntity = (ExtendedUserEntity) getUser(httpRequest, userEntityController);
        String formUsername = httpRequest.getPostParameter("username");
        String email = httpRequest.getPostParameter("email");
        String password = httpRequest.getPostParameter("password");
        String passwordConfirm = httpRequest.getPostParameter("passwordConfirm");

        if (formUsername != null) {
            userEntity.setUsername(userEntity.getUsername());
        }
        if (email != null) {
            userEntity.setEmail(email);
        }
        if (password != null && passwordConfirm != null) {
            userEntity.setPassword(hashPassword(password));
        }
        if (userEntityController.update(userEntity.getId(), userEntity, httpRequest)) {
            boolean loggedIn = verifyApiKey(httpRequest);
            return ok(profilePage.render(loggedIn, userEntity));
        }
        return error();
    }

    public HttpResponse queue(HttpRequest httpRequest) throws IOException, SQLException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        UserSettingsEntity userSettingsEntity = null;
        ExtendedUserEntity userEntity = (ExtendedUserEntity) getUser(httpRequest, userEntityController);
        if (userEntity != null) {
            userSettingsEntity = userEntity.getUserSettings();
        }
        int page = httpRequest.getQueryParameter("page") != null ? Integer.parseInt(Objects.requireNonNull(httpRequest.getQueryParameter("page"))) : 0;
        int maxResults = settingsController.getMaxBrowseResults(userSettingsEntity);
        long maxPage = queueEntityController.getTotalPages(maxResults, httpRequest);
        List<QueueEntity> queueEntityList = queueEntityController.readPaginatedResponse(maxResults, page, false, httpRequest);
        boolean loggedIn = verifyApiKey(httpRequest);
        return ok(queuePage.render(loggedIn, page, maxPage, queueEntityList, userEntity));
    }

    public HttpResponse search(HttpRequest httpRequest) throws Exception {
        UserSettingsEntity userSettingsEntity = null;
        ExtendedUserEntity userEntity = (ExtendedUserEntity) getUser(httpRequest, userEntityController);
        if (userEntity != null) {
            userSettingsEntity = userEntity.getUserSettings();
        }
        httpRequest.loadBody();
        long start = System.currentTimeMillis();
        String query = httpRequest.getPostParameter("query");

        List<MovieEntity> movieEntities = movieEntityController.search(query, "*", httpRequest);
        List<TvShowEntity> tvShowEntities = tvShowEntityController.search(query, "*", httpRequest);
        List<GameEntity> gameEntities = gameEntityController.search(query, "*", httpRequest);
        List<SongEntity> songEntities = songEntityController.search(query, "*", httpRequest);
        List<BookEntity> bookEntities = bookEntityController.search(query, "*", httpRequest);

        List<Card> movieCards = CardConverter.convertMovies(movieEntities, "search");
        List<Card> tvShowCards = CardConverter.convertTvShows(tvShowEntities, "search");
        List<Card> gameCards = CardConverter.convertGames(gameEntities, "search");
        List<Card> songCards = CardConverter.convertSongs(songEntities, "search");
        List<Card> bookCards = CardConverter.convertBooks(bookEntities, "search");

        long timeTaken = System.currentTimeMillis() - start;
        boolean loggedIn = verifyApiKey(httpRequest);
        return ok(searchPage.render(loggedIn, movieCards, tvShowCards, gameCards, songCards, bookCards, timeTaken, settingsController.getCardWidth(userSettingsEntity), userEntity));
    }

    public HttpResponse settings(HttpRequest httpRequest) throws IOException, SQLException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        boolean loggedIn = verifyApiKey(httpRequest);
        ExtendedUserEntity userEntity = (ExtendedUserEntity) getUser(httpRequest, userEntityController);
        return ok(settingsPage.render(loggedIn, userEntity));
    }

    public Promisable<HttpResponse> saveSettings(InterfaceMethodEnum interfaceNetworkUsage,
                                                 PlaybackQualityEnum defaultPlaybackQuality,
                                                 boolean debugEnabled,
                                                 boolean maintenanceMode,
                                                 boolean securityEnabled,
                                                 CompressionMethodEnum compressionMethod,
                                                 int compressionQuality,
                                                 String securityIssuer,
                                                 String securitySecretKey,
                                                 String searchHost,
                                                 String searchKey,
                                                 DisplayModeEnum displayMode,
                                                 EncoderProgramEnum encoderProgram,
                                                 InspectorProgramEnum inspectorProgram,
                                                 AudioCodecEnum audioCodec,
                                                 AudioRateEnum audioRate,
                                                 EncoderPresetEnum audioPreset,
                                                 VideoContainerEnum videoContainer,
                                                 VideoCodecEnum videoCodec,
                                                 EncoderPresetEnum videoPreset,
                                                 boolean tuneFilm,
                                                 boolean tuneAnimation,
                                                 boolean tuneGrain,
                                                 boolean stillImage,
                                                 boolean fastDecode,
                                                 boolean zeroLatency,
                                                 boolean fastStart,
                                                 boolean tunePsnr,
                                                 boolean tuneSsnr,
                                                 int videoCrf,
                                                 boolean blackBorderRemoval,
                                                 boolean cudaAcceleration,
                                                 int oneFourFourPTranscodeBitrate,
                                                 int twoFourZeroPTranscodeBitrate,
                                                 int threeSixZeroPTranscodeBitrate,
                                                 int fourEightZeroPTranscodeBitrate,
                                                 int sevenTwoZeroPTranscodeBitrate,
                                                 int oneZeroEightZeroPTranscodeBitrate,
                                                 int twoKTranscodeBitrate,
                                                 int fourKTranscodeBitrate,
                                                 int eightKTranscodeBitrate,
                                                 boolean showPoster,
                                                 boolean showName,
                                                 boolean showRuntime,
                                                 boolean showGenre,
                                                 boolean showMpaaRating,
                                                 boolean showUserRating,
                                                 boolean showLanguage,
                                                 boolean showReleaseDate,
                                                 boolean showActions,
                                                 boolean bookLibraryEnable,
                                                 String bookLibraryPath,
                                                 boolean bookScanEnable,
                                                 int bookScanFrequencyTime,
                                                 ScanFrequencyEnum bookScanFrequencyType,
                                                 boolean gameLibraryEnable,
                                                 String gameLibraryPath,
                                                 boolean gameScanEnable,
                                                 int gameScanFrequencyTime,
                                                 ScanFrequencyEnum gameScanFrequencyType,
                                                 boolean movieLibraryEnable,
                                                 String movieLibraryPath,
                                                 boolean movieScanEnable,
                                                 boolean moviePreTranscodeEnable,
                                                 boolean moviePreTranscodeOneFourFourP,
                                                 boolean moviePreTranscodeTwoFourZeroP,
                                                 boolean moviePreTranscodeThreeSixZeroP,
                                                 boolean moviePreTranscodeFourEightZeroP,
                                                 boolean moviePreTranscodeSevenTwoZeroP,
                                                 boolean moviePreTranscodeOneZeroEightZeroP,
                                                 boolean moviePreTranscodeTwoK,
                                                 boolean moviePreTranscodeFourK,
                                                 boolean moviePreTranscodeEightK,
                                                 int movieScanFrequencyTime,
                                                 ScanFrequencyEnum movieScanFrequencyType,
                                                 String movieLibraryPreTranscodePath,
                                                 boolean musicLibraryEnable,
                                                 String musicLibraryPath,
                                                 boolean musicScanEnable,
                                                 boolean musicPreTranscodeEnable,
                                                 boolean musicPreTranscodeSixFourK,
                                                 boolean musicPreTranscodeNineSixK,
                                                 boolean musicPreTranscodeOneTwoEightK,
                                                 boolean musicPreTranscodeThreeTwoZeroK,
                                                 boolean musicPreTranscodeOneFourOneOneK,
                                                 int musicScanFrequencyTime,
                                                 ScanFrequencyEnum musicScanFrequencyType,
                                                 String musicPreTranscodeLibraryPath,
                                                 boolean tvShowLibraryEnable,
                                                 String tvShowLibraryPath,
                                                 boolean tvShowScanEnable,
                                                 boolean tvShowPreTranscodeEnable,
                                                 boolean tvPreTranscodeOneFourFourP,
                                                 boolean tvPreTranscodeTwoFourZeroP,
                                                 boolean tvPreTranscodeThreeSixZeroP,
                                                 boolean tvPreTranscodeFourEightZeroP,
                                                 boolean tvPreTranscodeSevenTwoZeroP,
                                                 boolean tvPreTranscodeOneZeroEightZeroP,
                                                 boolean tvPreTranscodeTwoK,
                                                 boolean tvPreTranscodeFourK,
                                                 boolean tvPreTranscodeEightK,
                                                 int tvShowScanFrequencyTime,
                                                 ScanFrequencyEnum tvShowScanFrequencyType,
                                                 String tvShowLibraryPreTranscodePath,
                                                 String serverName,
                                                 boolean allowRegistration,
                                                 boolean showNewBooks,
                                                 boolean showNewGames,
                                                 boolean showNewMovies,
                                                 boolean showNewMusic,
                                                 boolean showNewTvShows,
                                                 boolean showPopularBooks,
                                                 boolean showPopularGames,
                                                 boolean showPopularMovies,
                                                 boolean showPopularMusic,
                                                 boolean showPopularTvShows,
                                                 SearchMethodEnum searchMethod,
                                                 int maxSearchResults,
                                                 int maxUIBrowseResults,
                                                 int maxAPIBrowseResults,
                                                 int cardWidth,
                                                 boolean stickyTopMenu,
                                                 boolean cacheEnable,
                                                 long apiCacheSize,
                                                 long databaseCacheSize,
                                                 long pageCacheSize,
                                                 int maxAssetCacheAge,
                                                 DatabaseTypeEnum databaseType,
                                                 String databaseName,
                                                 int minDatabaseConnections,
                                                 int maxDatabaseConnections,
                                                 boolean loggingEnable,
                                                 String baseLibraryPath,
                                                 String[] entityPackages,
                                                 boolean enableHistory,
                                                 boolean enableUpload,
                                                 String profilePhotoFolder) {
        if (SettingsController.saveSettings(
            new SettingsFileEntity(
                interfaceNetworkUsage,
                defaultPlaybackQuality,
                debugEnabled,
                maintenanceMode,
                securityEnabled,
                compressionMethod,
                compressionQuality,
                securityIssuer,
                securitySecretKey,
                searchHost,
                searchKey,
                displayMode,
                encoderProgram,
                inspectorProgram,
                audioCodec,
                audioRate,
                audioPreset,
                videoContainer,
                videoCodec,
                videoPreset,
                tuneFilm,
                tuneAnimation,
                tuneGrain,
                stillImage,
                fastDecode,
                zeroLatency,
                fastStart,
                tunePsnr,
                tuneSsnr,
                videoCrf,
                blackBorderRemoval,
                cudaAcceleration,
                oneFourFourPTranscodeBitrate,
                twoFourZeroPTranscodeBitrate,
                threeSixZeroPTranscodeBitrate,
                fourEightZeroPTranscodeBitrate,
                sevenTwoZeroPTranscodeBitrate,
                oneZeroEightZeroPTranscodeBitrate,
                twoKTranscodeBitrate,
                fourKTranscodeBitrate,
                eightKTranscodeBitrate,
                showPoster,
                showName,
                showRuntime,
                showGenre,
                showMpaaRating,
                showUserRating,
                showLanguage,
                showReleaseDate,
                showActions,
                bookLibraryEnable,
                bookLibraryPath,
                bookScanEnable,
                bookScanFrequencyTime,
                bookScanFrequencyType,
                gameLibraryEnable,
                gameLibraryPath,
                gameScanEnable,
                gameScanFrequencyTime,
                gameScanFrequencyType,
                movieLibraryEnable,
                movieLibraryPath,
                movieScanEnable,
                moviePreTranscodeEnable,
                moviePreTranscodeOneFourFourP,
                moviePreTranscodeTwoFourZeroP,
                moviePreTranscodeThreeSixZeroP,
                moviePreTranscodeFourEightZeroP,
                moviePreTranscodeSevenTwoZeroP,
                moviePreTranscodeOneZeroEightZeroP,
                moviePreTranscodeTwoK,
                moviePreTranscodeFourK,
                moviePreTranscodeEightK,
                movieScanFrequencyTime,
                movieScanFrequencyType,
                movieLibraryPreTranscodePath,
                musicLibraryEnable,
                musicLibraryPath,
                musicScanEnable,
                musicPreTranscodeEnable,
                musicPreTranscodeSixFourK,
                musicPreTranscodeNineSixK,
                musicPreTranscodeOneTwoEightK,
                musicPreTranscodeThreeTwoZeroK,
                musicPreTranscodeOneFourOneOneK,
                musicScanFrequencyTime,
                musicScanFrequencyType,
                musicPreTranscodeLibraryPath,
                tvShowLibraryEnable,
                tvShowLibraryPath,
                tvShowScanEnable,
                tvShowPreTranscodeEnable,
                tvPreTranscodeOneFourFourP,
                tvPreTranscodeTwoFourZeroP,
                tvPreTranscodeThreeSixZeroP,
                tvPreTranscodeFourEightZeroP,
                tvPreTranscodeSevenTwoZeroP,
                tvPreTranscodeOneZeroEightZeroP,
                tvPreTranscodeTwoK,
                tvPreTranscodeFourK,
                tvPreTranscodeEightK,
                tvShowScanFrequencyTime,
                tvShowScanFrequencyType,
                tvShowLibraryPreTranscodePath,
                serverName,
                allowRegistration,
                showNewBooks,
                showNewGames,
                showNewMovies,
                showNewMusic,
                showNewTvShows,
                showPopularBooks,
                showPopularGames,
                showPopularMovies,
                showPopularMusic,
                showPopularTvShows,
                searchMethod,
                maxSearchResults,
                maxUIBrowseResults,
                maxAPIBrowseResults,
                cardWidth,
                stickyTopMenu,
                cacheEnable,
                apiCacheSize,
                databaseCacheSize,
                pageCacheSize,
                maxAssetCacheAge,
                databaseType,
                databaseName,
                minDatabaseConnections,
                maxDatabaseConnections,
                loggingEnable,
                baseLibraryPath,
                entityPackages,
                enableHistory,
                enableUpload,
                profilePhotoFolder
            )
        )) {
            settingsController.setInterfaceMethod(interfaceNetworkUsage);
            settingsController.setDefaultPlaybackQuality(defaultPlaybackQuality);
            settingsController.setDebug(debugEnabled);
            settingsController.setMaintenanceMode(maintenanceMode);
            settingsController.setEnableSecurity(securityEnabled);
            settingsController.setCompressionMethod(compressionMethod);
            settingsController.setIssuer(securityIssuer);
            settingsController.setSecretKey(securitySecretKey);
            settingsController.setSearchHost(searchHost);
            settingsController.setSearchKey(searchKey);
            settingsController.setDisplayMode(displayMode);
            settingsController.setEncoderProgram(encoderProgram);
            settingsController.setInspectorProgram(inspectorProgram);
            settingsController.setAudioCodec(audioCodec);
            settingsController.setAudioRate(audioRate);
            settingsController.setAudioPreset(audioPreset);
            settingsController.setVideoContainer(videoContainer);
            settingsController.setVideoCodec(videoCodec);
            settingsController.setEncoderPreset(videoPreset);
            settingsController.setVideoTuneFilm(tuneFilm);
            settingsController.setVideoTuneAnimation(tuneAnimation);
            settingsController.setVideoTuneGrain(tuneGrain);
            settingsController.setVideoTuneStillImage(stillImage);
            settingsController.setVideoTuneFastDecode(fastDecode);
            settingsController.setVideoTuneZeroLatency(zeroLatency);
            settingsController.setVideoFastStart(fastStart);
            settingsController.setVideoTunePsnr(tunePsnr);
            settingsController.setVideoTuneSsnr(tuneSsnr);
            settingsController.setVideoCrf(videoCrf);
            settingsController.setVideoBlackBorder(blackBorderRemoval);
            settingsController.setVideoCudaAcceleration(cudaAcceleration);
            settingsController.setOneFourFourVideoTranscodeBitrate(oneFourFourPTranscodeBitrate);
            settingsController.setTwoFourZeroVideoTranscodeBitrate(twoFourZeroPTranscodeBitrate);
            settingsController.setThreeSixZeroVideoTranscodeBitrate(threeSixZeroPTranscodeBitrate);
            settingsController.setFourEightZeroVideoTranscodeBitrate(fourEightZeroPTranscodeBitrate);
            settingsController.setSevenTwoZeroVideoTranscodeBitrate(sevenTwoZeroPTranscodeBitrate);
            settingsController.setOneZeroEightZeroVideoTranscodeBitrate(oneZeroEightZeroPTranscodeBitrate);
            settingsController.setTwoKVideoTranscodeBitrate(twoKTranscodeBitrate);
            settingsController.setFourKVideoTranscodeBitrate(fourKTranscodeBitrate);
            settingsController.setEightKVideoTranscodeBitrate(eightKTranscodeBitrate);
            settingsController.setTableShowPoster(showPoster);
            settingsController.setTableShowName(showName);
            settingsController.setTableShowRuntime(showRuntime);
            settingsController.setTableShowGenre(showGenre);
            settingsController.setTableShowMpaaRating(showMpaaRating);
            settingsController.setTableShowUserRating(showUserRating);
            settingsController.setTableShowLanguage(showLanguage);
            settingsController.setTableShowReleaseDate(showReleaseDate);
            settingsController.setTableShowActions(showActions);
            settingsController.setBookLibraryEnable(bookLibraryEnable);
            settingsController.setBookLibraryPath(bookLibraryPath);
            settingsController.setBookScanEnable(bookScanEnable);
            settingsController.setBookScanFrequencyTime(bookScanFrequencyTime);
            settingsController.setBookScanFrequencyType(bookScanFrequencyType);
            settingsController.setGameLibraryEnable(gameLibraryEnable);
            settingsController.setGameLibraryPath(gameLibraryPath);
            settingsController.setGameScanEnable(gameScanEnable);
            settingsController.setGameScanFrequencyTime(gameScanFrequencyTime);
            settingsController.setGameScanFrequencyType(gameScanFrequencyType);
            settingsController.setMovieLibraryEnable(movieLibraryEnable);
            settingsController.setMovieLibraryPath(movieLibraryPath);
            settingsController.setMovieScanEnable(movieScanEnable);
            settingsController.setMoviePreTranscodeEnable(moviePreTranscodeEnable);
            settingsController.setMoviePreTranscode144p(moviePreTranscodeOneFourFourP);
            settingsController.setMoviePreTranscode240p(moviePreTranscodeTwoFourZeroP);
            settingsController.setMoviePreTranscode360p(moviePreTranscodeThreeSixZeroP);
            settingsController.setMoviePreTranscode480p(moviePreTranscodeFourEightZeroP);
            settingsController.setMoviePreTranscode720p(moviePreTranscodeSevenTwoZeroP);
            settingsController.setMoviePreTranscode1080p(moviePreTranscodeOneZeroEightZeroP);
            settingsController.setMoviePreTranscode2k(moviePreTranscodeTwoK);
            settingsController.setMoviePreTranscode4k(moviePreTranscodeFourK);
            settingsController.setMoviePreTranscode8k(moviePreTranscodeEightK);
            settingsController.setMovieScanFrequencyTime(movieScanFrequencyTime);
            settingsController.setMovieScanFrequencyType(movieScanFrequencyType);
            settingsController.setMoviePreTranscodeLibraryPath(movieLibraryPreTranscodePath);
            settingsController.setMusicLibraryEnable(musicLibraryEnable);
            settingsController.setMusicLibraryPath(musicLibraryPath);
            settingsController.setMusicScanEnable(musicScanEnable);
            settingsController.setMusicPreTranscodeEnable(musicPreTranscodeEnable);
            settingsController.setMusicPreTranscode64k(musicPreTranscodeSixFourK);
            settingsController.setMusicPreTranscode96k(musicPreTranscodeNineSixK);
            settingsController.setMusicPreTranscode128k(musicPreTranscodeOneTwoEightK);
            settingsController.setMusicPreTranscode320k(musicPreTranscodeThreeTwoZeroK);
            settingsController.setMusicPreTranscode1411k(musicPreTranscodeOneFourOneOneK);
            settingsController.setMusicScanFrequencyTime(musicScanFrequencyTime);
            settingsController.setMusicScanFrequencyType(musicScanFrequencyType);
            settingsController.setMusicPreTranscodeLibraryPath(musicPreTranscodeLibraryPath);
            settingsController.setTvShowLibraryEnable(tvShowLibraryEnable);
            settingsController.setTvShowLibraryPath(tvShowLibraryPath);
            settingsController.setTvShowScanEnable(tvShowScanEnable);
            settingsController.setTvShowPreTranscodeEnable(tvShowPreTranscodeEnable);
            settingsController.setTvShowPreTranscode144p(tvPreTranscodeOneFourFourP);
            settingsController.setTvShowPreTranscode240p(tvPreTranscodeTwoFourZeroP);
            settingsController.setTvShowPreTranscode360p(tvPreTranscodeThreeSixZeroP);
            settingsController.setTvShowPreTranscode480p(tvPreTranscodeFourEightZeroP);
            settingsController.setTvShowPreTranscode720p(tvPreTranscodeSevenTwoZeroP);
            settingsController.setTvShowPreTranscode1080p(tvPreTranscodeOneZeroEightZeroP);
            settingsController.setTvShowPreTranscode2k(tvPreTranscodeTwoK);
            settingsController.setTvShowPreTranscode4k(tvPreTranscodeFourK);
            settingsController.setTvShowPreTranscode8k(tvPreTranscodeEightK);
            settingsController.setTvShowScanFrequencyTime(tvShowScanFrequencyTime);
            settingsController.setTvShowScanFrequencyType(tvShowScanFrequencyType);
            settingsController.setTvShowPreTranscodeLibraryPath(tvShowLibraryPreTranscodePath);
            settingsController.setServerName(serverName);
            settingsController.setAllowRegistration(allowRegistration);
            settingsController.setHomePageShowNewBook(showNewBooks);
            settingsController.setHomePageShowNewGame(showNewGames);
            settingsController.setHomePageShowNewMovie(showNewMovies);
            settingsController.setHomePageShowNewMusic(showNewMusic);
            settingsController.setHomePageShowNewTvShow(showNewTvShows);
            settingsController.setHomePageShowPopularBook(showPopularBooks);
            settingsController.setHomePageShowPopularGame(showPopularGames);
            settingsController.setHomePageShowPopularMovie(showPopularMovies);
            settingsController.setHomePageShowPopularMusic(showPopularMusic);
            settingsController.setHomePageShowPopularTvShow(showPopularTvShows);
            settingsController.setSearchMethod(searchMethod);
            settingsController.setMaxSearchResults(maxSearchResults);
            settingsController.setMaxUIBrowseResults(maxUIBrowseResults);
            settingsController.setMaxAPIBrowseResults(maxAPIBrowseResults);
            settingsController.setCardWidth(cardWidth);
            settingsController.setStickyTopMenu(stickyTopMenu);
            settingsController.setCacheEnable(cacheEnable);
            settingsController.setMaxAssetCacheAge(maxAssetCacheAge);
            settingsController.setEntityPackage(entityPackages);
            settingsController.setEnableHistory(enableHistory);
            settingsController.setEnableUpload(enableUpload);
            settingsController.setProfilePhotoFolder(profilePhotoFolder);
        }
        return redirect("/settings");
    }

    public HttpResponse upload(HttpRequest httpRequest) throws IOException, SQLException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        ExtendedUserEntity userEntity = (ExtendedUserEntity) getUser(httpRequest, userEntityController);
        boolean loggedIn = verifyApiKey(httpRequest);
        if (settingsController.isEnableUpload()) {
            return ok(uploadPage.render(true, loggedIn, userEntity));
        }
        return redirect("/disabled");
    }

    public @NotNull Promisable<HttpResponse> postUpload(@NotNull HttpRequest httpRequest) {
        String mediaType = httpRequest.getQueryParameter("mediaType");
        String library = "temp";
        switch (Objects.requireNonNull(mediaType).toUpperCase()) {
            case "BOOK" ->
                library = settingsController.getBaseLibraryPath() + "/" + settingsController.getBookLibraryPath();
            case "MOVIE" ->
                library = settingsController.getBaseLibraryPath() + "/" + settingsController.getMovieLibraryPath();
            case "TVSHOW" ->
                library = settingsController.getBaseLibraryPath() + "/" + settingsController.getTvShowLibraryPath();
            case "GAME" ->
                library = settingsController.getBaseLibraryPath() + "/" + settingsController.getGameLibraryPath();
            case "MUSIC" ->
                library = settingsController.getBaseLibraryPath() + "/" + settingsController.getMusicLibraryPath();
        }
        UUID uuid = UUID.randomUUID();
        Path file = new File(library + "/" + uuid + ".tmp").toPath();
        String finalLibrary = library;
        if (settingsController.isDebug()) {
            System.out.println("Saving: " + finalLibrary + "/" + uuid + ".tmp");
        }
        return httpRequest.handleMultipart(MultipartDecoder.MultipartDataHandler.file(fileName ->
                ChannelFileWriter.open(newSingleThreadExecutor(), new File(finalLibrary + "/" + uuid + ".tmp").toPath())))
            .map($ -> finalizeUpload(httpRequest, mediaType, file));
    }

    private HttpResponse finalizeUpload(HttpRequest httpRequest, String mediaType, Path file) {
        try {
            switch (Objects.requireNonNull(mediaType).toUpperCase()) {
                case "BOOK" -> {
                    BookEntity book = new BookEntity();
                    book.setTitle(String.valueOf(file.getFileName()));
                    List<BookFileEntity> fileEntities = new LinkedList<>();
                    BookFileEntity fileEntity = new BookFileEntity();
                    fileEntity.setPath(String.valueOf(file.getParent().toAbsolutePath()));
                    fileEntities.add(fileEntity);
                    book.setFiles(fileEntities);
                    if (bookEntityController.createSecureResponse(book, httpRequest) != null) {
                        return ok();
                    } else {
                        if (settingsController.isDebug()) {
                            System.out.println("Failed to insert `book entity");
                        }
                        return error();
                    }
                }
                case "MOVIE" -> {
                    VideoFileEntity videoFileEntity = new VideoFileEntity();
                    videoFileEntity.setPath(settingsController.getBaseLibraryPath() + "/" + settingsController.getMovieLibraryPath() + "/" + file.getFileName());
                    videoFileEntity.setPlaybackQualityEnum(PlaybackQualityEnum.ORIGINAL);
                    MovieEntity movie = new MovieEntity();
                    movie.setTitle(String.valueOf(file.getFileName()));
                    List<VideoFileEntity> fileEntities = new LinkedList<>();
                    fileEntities.add(videoFileEntity);
                    movie.setFiles(fileEntities);
                    if (movieEntityController.createSecureResponse(movie, httpRequest) != null) {
                        return ok();
                    } else {
                        if (settingsController.isDebug()) {
                            System.out.println("Failed to insert movie entity");
                        }
                        return error();
                    }
                }
                case "TVSHOW" -> {
                    TvShowEntity tvShow = new TvShowEntity();
                    tvShow.setTitle(String.valueOf(file.getFileName()));
                    List<VideoFileEntity> fileEntities = new LinkedList<>();
                    VideoFileEntity videoFileEntity = new VideoFileEntity();
                    videoFileEntity.setPath(String.valueOf(file.toAbsolutePath()));
                    videoFileEntity.setPlaybackQualityEnum(PlaybackQualityEnum.ORIGINAL);
                    fileEntities.add(videoFileEntity);
                    tvShow.setFiles(fileEntities);
                    if (tvShowEntityController.createSecureResponse(tvShow, httpRequest) != null) {
                        return ok();
                    } else {
                        if (settingsController.isDebug()) {
                            System.out.println("Failed to insert tv entity");
                        }
                        return error();
                    }
                }
                case "GAME" -> {
                    GameEntity game = new GameEntity();
                    game.setTitle(String.valueOf(file.getFileName()));
                    List<GameFileEntity> fileEntities = new LinkedList<>();
                    GameFileEntity fileEntity = new GameFileEntity();
                    fileEntity.setPath(String.valueOf(file.getParent().toAbsolutePath()));
                    fileEntities.add(fileEntity);
                    game.setFiles(fileEntities);
                    if (gameEntityController.createSecureResponse(game, httpRequest) != null) {
                        return ok();
                    } else {
                        if (settingsController.isDebug()) {
                            System.out.println("Failed to insert game entity");
                        }
                        return error();
                    }
                }
                case "MUSIC" -> {
                    SongEntity song = new SongEntity();
                    song.setTitle(String.valueOf(file.getFileName()));
                    AudioFileEntity fileEntity = new AudioFileEntity();
                    fileEntity.setPath(String.valueOf(file.toAbsolutePath()));
                    song.setFile(fileEntity);
                    if (songEntityController.createSecureResponse(song, httpRequest) != null) {
                        return ok();
                    } else {
                        if (settingsController.isDebug()) {
                            System.out.println("Failed to insert song entity");
                        }
                        return error();
                    }
                }
            }
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
            if (!file.toFile().delete()) {
                System.err.println("Failed to delete: " + file.toFile().getAbsolutePath());
            }
        }
        return error();
    }

    public @NotNull Promisable<HttpResponse> getDeniedPage(@NotNull HttpRequest httpRequest) throws IOException, SQLException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        boolean loggedIn = verifyApiKey(httpRequest);
        ExtendedUserEntity userEntity = (ExtendedUserEntity) getUser(httpRequest, userEntityController);
        return ok(deniedPage.render(loggedIn, userEntity));
    }

    public @NotNull Promisable<HttpResponse> getDisabledPage(@NotNull HttpRequest httpRequest) throws IOException, SQLException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        boolean loggedIn = verifyApiKey(httpRequest);
        ExtendedUserEntity userEntity = (ExtendedUserEntity) getUser(httpRequest, userEntityController);
        return ok(disabledPage.render(loggedIn, userEntity));
    }
}
