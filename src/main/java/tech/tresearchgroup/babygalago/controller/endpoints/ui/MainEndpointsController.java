package tech.tresearchgroup.babygalago.controller.endpoints.ui;

import io.activej.bytebuf.ByteBuf;
import io.activej.http.HttpCookie;
import io.activej.http.HttpRequest;
import io.activej.http.HttpResponse;
import io.activej.promise.Promisable;
import lombok.AllArgsConstructor;
import org.bouncycastle.crypto.generators.BCrypt;
import org.bouncycastle.util.encoders.Hex;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.tresearchgroup.babygalago.controller.SettingsController;
import tech.tresearchgroup.babygalago.controller.controllers.*;
import tech.tresearchgroup.babygalago.controller.endpoints.LoginEndpointsController;
import tech.tresearchgroup.babygalago.view.pages.*;
import tech.tresearchgroup.cao.controller.GenericCAO;
import tech.tresearchgroup.dao.model.DatabaseTypeEnum;
import tech.tresearchgroup.palila.controller.BasicController;
import tech.tresearchgroup.palila.controller.CompressionController;
import tech.tresearchgroup.palila.controller.GenericController;
import tech.tresearchgroup.palila.model.Card;
import tech.tresearchgroup.palila.model.enums.*;
import tech.tresearchgroup.sao.model.SearchMethodEnum;
import tech.tresearchgroup.schemas.galago.entities.*;
import tech.tresearchgroup.schemas.galago.enums.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@AllArgsConstructor
public class MainEndpointsController extends BasicController {
    private static final Logger logger = LoggerFactory.getLogger(MainEndpointsController.class);
    private final MovieEntityController movieEntityController;
    private final TvShowEntityController tvShowEntityController;
    private final GameEntityController gameEntityController;
    private final SongEntityController songEntityController;
    private final BookEntityController bookEntityController;
    private final NotificationEntityController notificationEntityController;
    private final NewsArticleEntityController newsArticleEntityController;
    private final QueueEntityController queueEntityController;
    private final ExtendedUserEntityController extendedUserEntityController;
    private final SettingsController settingsController;
    private final LoginEndpointsController loginEndpointsController;
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
    private final SettingsPage settingsPage;
    private final UploadPage uploadPage;
    private final DeniedPage deniedPage;
    private final DisabledPage disabledPage;
    private final ErrorPage errorPage;
    private final NotFoundPage notFoundPage;
    private final UnderConstructionPage underConstructionPage;
    private final Map<String, GenericController> controllers;
    private final GenericCAO genericCAO;

    /**
     * Renders the about section
     *
     * @param httpRequest the request
     * @return the about page
     * @throws IOException               if it fails
     * @throws SQLException              if it fails
     * @throws InvocationTargetException if it fails
     * @throws IllegalAccessException    if it fails
     * @throws InstantiationException    if it fails
     */
    public Promisable<HttpResponse> about(HttpRequest httpRequest) throws IOException, SQLException, InvocationTargetException, IllegalAccessException, InstantiationException {
        boolean loggedIn = verifyApiKey(httpRequest);
        ExtendedUserEntity userEntity = (ExtendedUserEntity) getUser(httpRequest, extendedUserEntityController);
        return ok(
            aboutPage.render(
                loggedIn,
                notificationEntityController.getNumberOfUnread(userEntity),
                userEntity.getPermissionGroup(),
                settingsController.getServerName(),
                settingsController.isEnableUpload(),
                settingsController.isMovieLibraryEnable(),
                settingsController.isTvShowLibraryEnable(),
                settingsController.isGameLibraryEnable(),
                settingsController.isMusicLibraryEnable(),
                settingsController.isBookLibraryEnable(),
                genericCAO
            ).getBytes()
        );
    }

    /**
     * Renders the home page
     *
     * @param httpRequest the request
     * @return the home page
     * @throws IOException               if it fails
     * @throws SQLException              if it fails
     * @throws InvocationTargetException if it fails
     * @throws NoSuchMethodException     if it fails
     * @throws IllegalAccessException    if it fails
     * @throws InstantiationException    if it fails
     */
    public Promisable<HttpResponse> index(HttpRequest httpRequest) throws IOException, SQLException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        ExtendedUserEntity userEntity = (ExtendedUserEntity) getUser(httpRequest, extendedUserEntityController);
        UserSettingsEntity userSettingsEntity = null;
        List<Card> newBooksCards = new LinkedList<>();
        List<Card> popularBooksCards = new LinkedList<>();
        List<Card> newGamesCards = new LinkedList<>();
        List<Card> popularGamesCards = new LinkedList<>();
        List<Card> newMoviesCards = new LinkedList<>();
        List<Card> popularMoviesCards = new LinkedList<>();
        List<Card> newMusicCards = new LinkedList<>();
        List<Card> popularMusicCards = new LinkedList<>();
        List<Card> newTvShowsCards = new LinkedList<>();
        List<Card> popularTvShowsCards = new LinkedList<>();
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
            for (Object object : newBooks) {
                newBooksCards.add(bookEntityController.toCard(object, "browse"));
            }
            for (Object object : popularBooks) {
                popularBooksCards.add(bookEntityController.toCard(object, "browse"));
            }
            for (Object object : newGames) {
                newGamesCards.add(gameEntityController.toCard(object, "browse"));
            }
            for (Object object : popularGames) {
                popularGamesCards.add(gameEntityController.toCard(object, "browse"));
            }
            for (Object object : newMovies) {
                newMoviesCards.add(movieEntityController.toCard(object, "browse"));
            }
            for (Object object : popularMovies) {
                popularMoviesCards.add(movieEntityController.toCard(object, "browse"));
            }
            for (Object object : newMusic) {
                newMusicCards.add(songEntityController.toCard(object, "browse"));
            }
            for (Object object : popularMusic) {
                popularMusicCards.add(songEntityController.toCard(object, "browse"));
            }
            for (Object object : newTv) {
                newTvShowsCards.add(tvShowEntityController.toCard(object, "browse"));
            }
            for (Object object : popularTv) {
                popularTvShowsCards.add(tvShowEntityController.toCard(object, "browse"));
            }
        }
        boolean loggedIn = verifyApiKey(httpRequest);
        PermissionGroupEnum permissionGroupEnum = PermissionGroupEnum.ALL;
        if (userEntity != null) {
            permissionGroupEnum = userEntity.getPermissionGroup();
        }
        byte[] data = indexPage.render(
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
            permissionGroupEnum,
            settingsController.getServerName(),
            settingsController.isEnableUpload(),
            settingsController.isMovieLibraryEnable(),
            settingsController.isTvShowLibraryEnable(),
            settingsController.isGameLibraryEnable(),
            settingsController.isMusicLibraryEnable(),
            settingsController.isBookLibraryEnable(),
            genericCAO
        ).getBytes();
        byte[] compressed = CompressionController.compress(data);
        return okResponseCompressed(compressed);
    }

    /**
     * Handles login using the API
     *
     * @param httpRequest the request
     * @return the login page
     */
    public Promisable<HttpResponse> login(HttpRequest httpRequest) {
        try {
            String error = httpRequest.getQueryParameter("error");
            boolean isError = false;
            if (error != null) {
                if (error.equals("")) {
                    isError = true;
                }
            }
            Object userData = getUser(httpRequest, extendedUserEntityController);
            ExtendedUserEntity userEntity = new ExtendedUserEntity();
            boolean loggedIn = false;
            if (userData != null) {
                userEntity = (ExtendedUserEntity) userData;
                loggedIn = userEntity.getId() != 0L;
            }
            return ok(
                loginPage.render(
                    isError,
                    loggedIn,
                    notificationEntityController.getNumberOfUnread(userEntity),
                    userEntity.getPermissionGroup(),
                    settingsController.getServerName(),
                    settingsController.isEnableUpload(),
                    settingsController.isMovieLibraryEnable(),
                    settingsController.isTvShowLibraryEnable(),
                    settingsController.isGameLibraryEnable(),
                    settingsController.isMusicLibraryEnable(),
                    settingsController.isBookLibraryEnable(),
                    genericCAO
                ).getBytes()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return error();
    }

    /**
     * Logs the user in using the form
     *
     * @param httpRequest the request
     * @return redirects to index with authorization cookie set
     */
    public Promisable<HttpResponse> loginUI(HttpRequest httpRequest) {
        try {
            ByteBuf data = httpRequest.loadBody().getResult();
            if (data != null) {
                if (data.canRead()) {
                    String username = httpRequest.getPostParameter("username");
                    String password = httpRequest.getPostParameter("password");
                    ExtendedUserEntity userEntity = loginEndpointsController.getUser(username, password, httpRequest);
                    if (userEntity != null) {
                        return HttpResponse.redirect301("/").withCookie(HttpCookie.of("authorization", userEntity.getApiKey()));
                    }
                }
            } else {
                if (settingsController.isDebug()) {
                    logger.info("No data submitted during ui login. Redirecting to logout...");
                }
                redirect("/logout");
            }
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
        }
        return HttpResponse.redirect301("/login?error");
    }

    /**
     * Logs out the user
     *
     * @param httpRequest the request
     * @return redirects to login page
     */
    @NotNull
    public Promisable<HttpResponse> logout(@NotNull HttpRequest httpRequest) {
        try {
            HttpCookie cookie = HttpCookie.of("authorization", "123");
            cookie.setMaxAge(0);
            return HttpResponse.redirect301("/login").withCookie(cookie);
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
        }
        return redirect("/error");
    }

    /**
     * Renders the password reset page
     *
     * @param httpRequest the request
     * @return the page
     * @throws IOException               if it fails
     * @throws SQLException              if it fails
     * @throws InvocationTargetException if it fails
     * @throws IllegalAccessException    if it fails
     * @throws InstantiationException    if it fails
     */
    public Promisable<HttpResponse> reset(HttpRequest httpRequest) throws IOException, SQLException, InvocationTargetException, IllegalAccessException, InstantiationException {
        boolean loggedIn = verifyApiKey(httpRequest);
        ExtendedUserEntity userEntity = (ExtendedUserEntity) getUser(httpRequest, extendedUserEntityController);
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
        return ok(
            resetPage.render(
                loggedIn,
                isError,
                isSuccess,
                wasConfirmed,
                confirmationData,
                notificationEntityController.getNumberOfUnread(userEntity),
                userEntity.getPermissionGroup(),
                settingsController.getServerName(),
                settingsController.isEnableUpload(),
                settingsController.isMovieLibraryEnable(),
                settingsController.isTvShowLibraryEnable(),
                settingsController.isGameLibraryEnable(),
                settingsController.isMusicLibraryEnable(),
                settingsController.isBookLibraryEnable(),
                genericCAO
            ).getBytes()
        );
    }

    /**
     * Resets a users password using the form
     *
     * @param httpRequest the request
     * @return the page
     * @throws SQLException              if it fails
     * @throws InvocationTargetException if it fails
     * @throws IllegalAccessException    if it fails
     * @throws InstantiationException    if it fails
     * @throws IOException               if it fails
     */
    public Promisable<HttpResponse> postReset(HttpRequest httpRequest) throws SQLException, InvocationTargetException, IllegalAccessException, InstantiationException, IOException {
        boolean loggedIn = verifyApiKey(httpRequest);
        ExtendedUserEntity userEntity = (ExtendedUserEntity) getUser(httpRequest, extendedUserEntityController);
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

    /**
     * Renders the registration page
     *
     * @param httpRequest the request
     * @return the page
     * @throws IOException               if it fails
     * @throws SQLException              if it fails
     * @throws InvocationTargetException if it fails
     * @throws IllegalAccessException    if it fails
     * @throws InstantiationException    if it fails
     */
    public Promisable<HttpResponse> register(HttpRequest httpRequest) throws IOException, SQLException, InvocationTargetException, IllegalAccessException, InstantiationException {
        String error = httpRequest.getQueryParameter("error");
        ExtendedUserEntity userEntity = (ExtendedUserEntity) getUser(httpRequest, extendedUserEntityController);
        boolean loggedIn = verifyApiKey(httpRequest);
        PermissionGroupEnum permissionGroupEnum = PermissionGroupEnum.ALL;
        if (userEntity != null) {
            permissionGroupEnum = userEntity.getPermissionGroup();
        }
        if (error == null) {
            return ok(
                registerPage.render(
                    null,
                    loggedIn,
                    notificationEntityController.getNumberOfUnread(userEntity),
                    permissionGroupEnum,
                    settingsController.getServerName(),
                    settingsController.isEnableUpload(),
                    settingsController.isMovieLibraryEnable(),
                    settingsController.isTvShowLibraryEnable(),
                    settingsController.isGameLibraryEnable(),
                    settingsController.isMusicLibraryEnable(),
                    settingsController.isBookLibraryEnable(),
                    genericCAO
                ).getBytes()
            );
        }
        return ok(
            registerPage.render(
                RegistrationErrorsEnum.valueOf(error),
                loggedIn,
                notificationEntityController.getNumberOfUnread(userEntity),
                userEntity.getPermissionGroup(),
                settingsController.getServerName(),
                settingsController.isEnableUpload(),
                settingsController.isMovieLibraryEnable(),
                settingsController.isTvShowLibraryEnable(),
                settingsController.isGameLibraryEnable(),
                settingsController.isMusicLibraryEnable(),
                settingsController.isBookLibraryEnable(),
                genericCAO
            ).getBytes()
        );
    }

    /**
     * Registers a user from the form
     *
     * @param httpRequest the request
     * @return redirects to index if successful
     * @throws SQLException              if it fails
     * @throws InvocationTargetException if it fails
     * @throws NoSuchMethodException     if it fails
     * @throws IllegalAccessException    if it fails
     * @throws InstantiationException    if it fails
     * @throws IOException               if it fails
     */
    public @NotNull Promisable<HttpResponse> postRegister(@NotNull HttpRequest httpRequest) throws SQLException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException, IOException {
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
                if (extendedUserEntityController.getUserByUsername(username) != null) {
                    return redirect("/register?error=USERNAME_TAKEN");
                }
                ExtendedUserEntity userEntity = new ExtendedUserEntity();
                userEntity.setUsername(username);
                userEntity.setPassword(hashPassword(password));
                userEntity.setEmail(email);
                userEntity.setPermissionGroup(PermissionGroupEnum.USER);
                if (extendedUserEntityController.createSecureResponse(userEntity, ReturnType.OBJECT, httpRequest) != null) {
                    return HttpResponse.redirect301("/");
                }
                return redirect("/register?error=ERROR_500");
            }
            return redirect("/register?error=EMAIL_MATCH");
        }
        return redirect("/register?error=PASSWORD_MATCH");
    }

    /**
     * Hashes the password using BCrypt
     *
     * @param password the plain text password
     * @return the hashed password
     */
    private String hashPassword(String password) {
        byte[] salt = new byte[16];
        return new String(Hex.encode(BCrypt.generate(password.getBytes(StandardCharsets.UTF_8), salt, 8)));
    }

    /**
     * Displays the licenses page
     *
     * @param httpRequest the request
     * @return the page
     * @throws IOException               if it fails
     * @throws SQLException              if it fails
     * @throws InvocationTargetException if it fails
     * @throws IllegalAccessException    if it fails
     * @throws InstantiationException    if it fails
     */
    public Promisable<HttpResponse> licenses(HttpRequest httpRequest) throws IOException, SQLException, InvocationTargetException, IllegalAccessException, InstantiationException {
        boolean loggedIn = verifyApiKey(httpRequest);
        ExtendedUserEntity userEntity = (ExtendedUserEntity) getUser(httpRequest, extendedUserEntityController);
        return ok(
            licensesPage.render(
                loggedIn,
                notificationEntityController.getNumberOfUnread(userEntity),
                userEntity.getPermissionGroup(),
                settingsController.getServerName(),
                settingsController.isEnableUpload(),
                settingsController.isMovieLibraryEnable(),
                settingsController.isTvShowLibraryEnable(),
                settingsController.isGameLibraryEnable(),
                settingsController.isMusicLibraryEnable(),
                settingsController.isBookLibraryEnable(),
                genericCAO
            ).getBytes()
        );
    }

    /**
     * Renders the news page
     *
     * @param httpRequest the request
     * @return the page
     * @throws IOException               if it fails
     * @throws SQLException              if it fails
     * @throws InvocationTargetException if it fails
     * @throws NoSuchMethodException     if it fails
     * @throws IllegalAccessException    if it fails
     * @throws InstantiationException    if it fails
     */
    public Promisable<HttpResponse> news(HttpRequest httpRequest) throws IOException, SQLException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        UserSettingsEntity userSettingsEntity = null;
        ExtendedUserEntity userEntity = (ExtendedUserEntity) getUser(httpRequest, extendedUserEntityController);
        if (userEntity != null) {
            userSettingsEntity = userEntity.getUserSettings();
        }
        int maxResults = settingsController.getMaxBrowseResults(userSettingsEntity);
        int page = httpRequest.getQueryParameter("page") != null ? Integer.parseInt(Objects.requireNonNull(httpRequest.getQueryParameter("page"))) : 0;
        Long maxPage = newsArticleEntityController.getTotalPages(maxResults, httpRequest);
        boolean loggedIn = verifyApiKey(httpRequest);
        return ok(
            newsPage.render(
                loggedIn,
                page,
                maxPage,
                notificationEntityController.getNumberOfUnread(userEntity),
                userEntity.getPermissionGroup(),
                settingsController.getServerName(),
                settingsController.isEnableUpload(),
                settingsController.isMovieLibraryEnable(),
                settingsController.isTvShowLibraryEnable(),
                settingsController.isGameLibraryEnable(),
                settingsController.isMusicLibraryEnable(),
                settingsController.isBookLibraryEnable(),
                genericCAO
            ).getBytes()
        );
    }

    /**
     * Renders the notifications page
     *
     * @param httpRequest the request
     * @return the page
     * @throws IOException               if it fails
     * @throws SQLException              if it fails
     * @throws InvocationTargetException if it fails
     * @throws NoSuchMethodException     if it fails
     * @throws IllegalAccessException    if it fails
     * @throws InstantiationException    if it fails
     */
    public Promisable<HttpResponse> notifications(HttpRequest httpRequest) throws IOException, SQLException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        int page = httpRequest.getQueryParameter("page") != null ? Integer.parseInt(Objects.requireNonNull(httpRequest.getQueryParameter("page"))) : 0;
        ExtendedUserEntity userEntity = (ExtendedUserEntity) getUser(httpRequest, extendedUserEntityController);
        if (userEntity != null) {
            long maxPage = notificationEntityController.getTotalPages(settingsController.getMaxBrowseResults(userEntity.getUserSettings()), httpRequest);
            List<NotificationEntity> notificationEntityList = (List<NotificationEntity>) notificationEntityController.readPaginatedResponse((int) maxPage, page, false, ReturnType.OBJECT, httpRequest);
            boolean loggedIn = verifyApiKey(httpRequest);
            return ok(
                notificationsPage.render(
                    page,
                    maxPage,
                    notificationEntityList,
                    loggedIn,
                    notificationEntityController.getNumberOfUnread(userEntity),
                    userEntity.getPermissionGroup(),
                    settingsController.getServerName(),
                    settingsController.isEnableUpload(),
                    settingsController.isMovieLibraryEnable(),
                    settingsController.isTvShowLibraryEnable(),
                    settingsController.isGameLibraryEnable(),
                    settingsController.isMusicLibraryEnable(),
                    settingsController.isBookLibraryEnable(),
                    genericCAO
                ).getBytes()
            );
        } else {
            return redirect("/error");
        }
    }

    /**
     * Renders the profile page
     *
     * @param httpRequest the request
     * @return the page
     * @throws IOException               if it fails
     * @throws SQLException              if it fails
     * @throws InvocationTargetException if it fails
     * @throws IllegalAccessException    if it fails
     * @throws InstantiationException    if it fails
     */
    public Promisable<HttpResponse> profile(HttpRequest httpRequest) throws IOException, SQLException, InvocationTargetException, IllegalAccessException, InstantiationException {
        boolean loggedIn = verifyApiKey(httpRequest);
        ExtendedUserEntity userEntity = (ExtendedUserEntity) getUser(httpRequest, extendedUserEntityController);
        return ok(
            profilePage.render(
                loggedIn,
                notificationEntityController.getNumberOfUnread(userEntity),
                userEntity.getPermissionGroup(),
                settingsController.getServerName(),
                settingsController.isEnableUpload(),
                settingsController.isMovieLibraryEnable(),
                settingsController.isTvShowLibraryEnable(),
                settingsController.isGameLibraryEnable(),
                settingsController.isMusicLibraryEnable(),
                settingsController.isBookLibraryEnable(),
                genericCAO
            ).getBytes()
        );
    }

    /**
     * Updates the user profile using the form
     *
     * @param httpRequest the request
     * @return the page
     * @throws Exception if it fails
     */
    public Promisable<HttpResponse> postProfile(HttpRequest httpRequest) throws Exception {
        ExtendedUserEntity userEntity = (ExtendedUserEntity) getUser(httpRequest, extendedUserEntityController);
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
        if (extendedUserEntityController.update(userEntity.getId(), userEntity, httpRequest)) {
            boolean loggedIn = verifyApiKey(httpRequest);
            return ok(
                profilePage.render(
                    loggedIn,
                    notificationEntityController.getNumberOfUnread(userEntity),
                    userEntity.getPermissionGroup(),
                    settingsController.getServerName(),
                    settingsController.isEnableUpload(),
                    settingsController.isMovieLibraryEnable(),
                    settingsController.isTvShowLibraryEnable(),
                    settingsController.isGameLibraryEnable(),
                    settingsController.isMusicLibraryEnable(),
                    settingsController.isBookLibraryEnable(),
                    genericCAO
                ).getBytes()
            );
        }
        return redirect("/error");
    }

    /**
     * Renders the queue page
     *
     * @param httpRequest the request
     * @return the page
     * @throws IOException               if it fails
     * @throws SQLException              if it fails
     * @throws InvocationTargetException if it fails
     * @throws NoSuchMethodException     if it fails
     * @throws IllegalAccessException    if it fails
     * @throws InstantiationException    if it fails
     */
    public Promisable<HttpResponse> queue(HttpRequest httpRequest) throws IOException, SQLException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        UserSettingsEntity userSettingsEntity = null;
        ExtendedUserEntity userEntity = (ExtendedUserEntity) getUser(httpRequest, extendedUserEntityController);
        if (userEntity != null) {
            userSettingsEntity = userEntity.getUserSettings();
        }
        int page = httpRequest.getQueryParameter("page") != null ? Integer.parseInt(Objects.requireNonNull(httpRequest.getQueryParameter("page"))) : 0;
        int maxResults = settingsController.getMaxBrowseResults(userSettingsEntity);
        long maxPage = queueEntityController.getTotalPages(maxResults, httpRequest);
        List<QueueEntity> queueEntityList = (List<QueueEntity>) queueEntityController.readPaginatedResponse(maxResults, page, false, ReturnType.OBJECT, httpRequest);
        boolean loggedIn = verifyApiKey(httpRequest);
        return ok(
            queuePage.render(
                loggedIn,
                page,
                maxPage,
                queueEntityList,
                notificationEntityController.getNumberOfUnread(userEntity),
                userEntity.getPermissionGroup(),
                settingsController.getServerName(),
                settingsController.isEnableUpload(),
                settingsController.isMovieLibraryEnable(),
                settingsController.isTvShowLibraryEnable(),
                settingsController.isGameLibraryEnable(),
                settingsController.isMusicLibraryEnable(),
                settingsController.isBookLibraryEnable(),
                genericCAO
            ).getBytes()
        );
    }

    /**
     * Renders the settings page
     *
     * @param httpRequest the request
     * @return the page
     * @throws IOException               if it fails
     * @throws SQLException              if it fails
     * @throws InvocationTargetException if it fails
     * @throws IllegalAccessException    if it fails
     * @throws InstantiationException    if it fails
     */
    public Promisable<HttpResponse> settings(HttpRequest httpRequest) throws IOException, SQLException, InvocationTargetException, IllegalAccessException, InstantiationException {
        boolean loggedIn = verifyApiKey(httpRequest);
        ExtendedUserEntity userEntity = (ExtendedUserEntity) getUser(httpRequest, extendedUserEntityController);
        return ok(
            settingsPage.render(
                loggedIn,
                notificationEntityController.getNumberOfUnread(userEntity),
                userEntity.getPermissionGroup(),
                settingsController.getServerName(),
                settingsController.isEnableUpload(),
                settingsController.isMovieLibraryEnable(),
                settingsController.isTvShowLibraryEnable(),
                settingsController.isGameLibraryEnable(),
                settingsController.isMusicLibraryEnable(),
                settingsController.isBookLibraryEnable(),
                genericCAO
            ).getBytes()
        );
    }

    /**
     * Saves the server settings from a POST request
     *
     * @param httpRequest the request
     * @return redirects to the settings page
     */
    public Promisable<HttpResponse> saveSettings(HttpRequest httpRequest) {
        InterfaceMethodEnum interfaceNetworkUsage = InterfaceMethodEnum.valueOf(httpRequest.getPostParameter("interfaceNetworkUsage"));
        PlaybackQualityEnum defaultPlaybackQuality = PlaybackQualityEnum.valueOf(httpRequest.getPostParameter("defaultPlaybackQuality"));
        boolean debugEnabled = Objects.equals(httpRequest.getPostParameter("debugEnabled"), "on");
        boolean maintenanceMode = Objects.equals(httpRequest.getPostParameter("maintenanceMode"), "on");
        boolean securityEnabled = Objects.equals(httpRequest.getPostParameter("securityEnabled"), "on");
        CompressionMethodEnum compressionMethod = CompressionMethodEnum.valueOf(httpRequest.getPostParameter("compressionMethod"));
        int compressionQuality = Integer.parseInt(httpRequest.getPostParameter("compressionQuality"));
        String securityIssuer = httpRequest.getPostParameter("securityIssuer");
        String securitySecretKey = httpRequest.getPostParameter("securitySecretKey");
        String searchHost = httpRequest.getPostParameter("searchHost");
        String searchKey = httpRequest.getPostParameter("searchKey");
        DisplayModeEnum displayMode = DisplayModeEnum.valueOf(httpRequest.getPostParameter("displayMode"));
        EncoderProgramEnum encoderProgram = EncoderProgramEnum.valueOf(httpRequest.getPostParameter("encoderProgram"));
        InspectorProgramEnum inspectorProgram = InspectorProgramEnum.valueOf(httpRequest.getPostParameter("inspectorProgram"));
        AudioCodecEnum audioCodec = AudioCodecEnum.valueOf(httpRequest.getPostParameter("audioCodec"));
        AudioRateEnum audioRate = AudioRateEnum.valueOf(httpRequest.getPostParameter("audioRate"));
        EncoderPresetEnum audioPreset = EncoderPresetEnum.valueOf(httpRequest.getPostParameter("audioPreset"));
        VideoContainerEnum videoContainer = VideoContainerEnum.valueOf(httpRequest.getPostParameter("videoContainer"));
        VideoCodecEnum videoCodec = VideoCodecEnum.valueOf(httpRequest.getPostParameter("videoCodec"));
        EncoderPresetEnum videoPreset = EncoderPresetEnum.valueOf(httpRequest.getPostParameter("videoPreset"));
        boolean tuneFilm = Objects.equals(httpRequest.getPostParameter("tuneFilm"), "on");
        boolean tuneAnimation = Objects.equals(httpRequest.getPostParameter("tuneAnimation"), "on");
        boolean tuneGrain = Objects.equals(httpRequest.getPostParameter("tuneGrain"), "on");
        boolean stillImage = Objects.equals(httpRequest.getPostParameter("stillImage"), "on");
        boolean fastDecode = Objects.equals(httpRequest.getPostParameter("fastDecode"), "on");
        boolean zeroLatency = Objects.equals(httpRequest.getPostParameter("zeroLatency"), "on");
        boolean fastStart = Objects.equals(httpRequest.getPostParameter("fastStart"), "on");
        boolean tunePsnr = Objects.equals(httpRequest.getPostParameter("tunePsnr"), "on");
        boolean tuneSsnr = Objects.equals(httpRequest.getPostParameter("tuneSsnr"), "on");
        int videoCrf = Integer.parseInt(Objects.requireNonNull(httpRequest.getPostParameter("videoCrf")));
        boolean blackBorderRemoval = Objects.equals(httpRequest.getPostParameter("blackBorderRemoval"), "on");
        boolean cudaAcceleration = Objects.equals(httpRequest.getPostParameter("cudaAcceleration"), "on");
        int oneFourFourPTranscodeBitrate = Integer.parseInt(Objects.requireNonNull(httpRequest.getPostParameter("oneFourFourPTranscodeBitrate")));
        int twoFourZeroPTranscodeBitrate = Integer.parseInt(Objects.requireNonNull(httpRequest.getPostParameter("twoFourZeroPTranscodeBitrate")));
        int threeSixZeroPTranscodeBitrate = Integer.parseInt(Objects.requireNonNull(httpRequest.getPostParameter("threeSixZeroPTranscodeBitrate")));
        int fourEightZeroPTranscodeBitrate = Integer.parseInt(Objects.requireNonNull(httpRequest.getPostParameter("fourEightZeroPTranscodeBitrate")));
        int sevenTwoZeroPTranscodeBitrate = Integer.parseInt(Objects.requireNonNull(httpRequest.getPostParameter("sevenTwoZeroPTranscodeBitrate")));
        int oneZeroEightZeroPTranscodeBitrate = Integer.parseInt(Objects.requireNonNull(httpRequest.getPostParameter("oneZeroEightZeroPTranscodeBitrate")));
        int twoKTranscodeBitrate = Integer.parseInt(Objects.requireNonNull(httpRequest.getPostParameter("twoKTranscodeBitrate")));
        int fourKTranscodeBitrate = Integer.parseInt(Objects.requireNonNull(httpRequest.getPostParameter("fourKTranscodeBitrate")));
        int eightKTranscodeBitrate = Integer.parseInt(Objects.requireNonNull(httpRequest.getPostParameter("eightKTranscodeBitrate")));
        boolean showPoster = Objects.equals(httpRequest.getPostParameter("showPoster"), "on");
        boolean showName = Objects.equals(httpRequest.getPostParameter("showName"), "on");
        boolean showRuntime = Objects.equals(httpRequest.getPostParameter("showRuntime"), "on");
        boolean showGenre = Objects.equals(httpRequest.getPostParameter("showGenre"), "on");
        boolean showMpaaRating = Objects.equals(httpRequest.getPostParameter("showMpaaRating"), "on");
        boolean showUserRating = Objects.equals(httpRequest.getPostParameter("showUserRating"), "on");
        boolean showLanguage = Objects.equals(httpRequest.getPostParameter("showLanguage"), "on");
        boolean showReleaseDate = Objects.equals(httpRequest.getPostParameter("showReleaseDate"), "on");
        boolean showActions = Objects.equals(httpRequest.getPostParameter("showActions"), "on");
        boolean bookLibraryEnable = Objects.equals(httpRequest.getPostParameter("bookLibraryEnable"), "on");
        String bookLibraryPath = httpRequest.getPostParameter("bookLibraryPath");
        boolean bookScanEnable = Objects.equals(httpRequest.getPostParameter("bookScanEnable"), "on");
        int bookScanFrequencyTime = Integer.parseInt(Objects.requireNonNull(httpRequest.getPostParameter("bookScanFrequencyTime")));
        ScanFrequencyEnum bookScanFrequencyType = ScanFrequencyEnum.valueOf(httpRequest.getPostParameter("bookScanFrequencyType"));
        boolean gameLibraryEnable = Objects.equals(httpRequest.getPostParameter("gameLibraryEnable"), "on");
        String gameLibraryPath = httpRequest.getPostParameter("gameLibraryPath");
        boolean gameScanEnable = Objects.equals(httpRequest.getPostParameter("gameScanEnable"), "on");
        int gameScanFrequencyTime = Integer.parseInt(Objects.requireNonNull(httpRequest.getPostParameter("gameScanFrequencyTime")));
        ScanFrequencyEnum gameScanFrequencyType = ScanFrequencyEnum.valueOf(httpRequest.getPostParameter("gameScanFrequencyType"));
        boolean movieLibraryEnable = Objects.equals(httpRequest.getPostParameter("movieLibraryEnable"), "on");
        String movieLibraryPath = httpRequest.getPostParameter("movieLibraryPath");
        boolean movieScanEnable = Objects.equals(httpRequest.getPostParameter("movieScanEnable"), "on");
        boolean moviePreTranscodeEnable = Objects.equals(httpRequest.getPostParameter("moviePreTranscodeEnable"), "on");
        boolean moviePreTranscodeOneFourFourP = Objects.equals(httpRequest.getPostParameter("moviePreTranscodeOneFourFourP"), "on");
        boolean moviePreTranscodeTwoFourZeroP = Objects.equals(httpRequest.getPostParameter("moviePreTranscodeTwoFourZeroP"), "on");
        boolean moviePreTranscodeThreeSixZeroP = Objects.equals(httpRequest.getPostParameter("moviePreTranscodeThreeSixZeroP"), "on");
        boolean moviePreTranscodeFourEightZeroP = Objects.equals(httpRequest.getPostParameter("moviePreTranscodeFourEightZeroP"), "on");
        boolean moviePreTranscodeSevenTwoZeroP = Objects.equals(httpRequest.getPostParameter("moviePreTranscodeSevenTwoZeroP"), "on");
        boolean moviePreTranscodeOneZeroEightZeroP = Objects.equals(httpRequest.getPostParameter("moviePreTranscodeOneZeroEightZeroP"), "on");
        boolean moviePreTranscodeTwoK = Objects.equals(httpRequest.getPostParameter("moviePreTranscodeTwoK"), "on");
        boolean moviePreTranscodeFourK = Objects.equals(httpRequest.getPostParameter("moviePreTranscodeFourK"), "on");
        boolean moviePreTranscodeEightK = Objects.equals(httpRequest.getPostParameter("moviePreTranscodeEightK"), "on");
        int movieScanFrequencyTime = Integer.parseInt(Objects.requireNonNull(httpRequest.getPostParameter("movieScanFrequencyTime")));
        ScanFrequencyEnum movieScanFrequencyType = ScanFrequencyEnum.valueOf(httpRequest.getPostParameter("movieScanFrequencyType"));
        String movieLibraryPreTranscodePath = httpRequest.getPostParameter("movieLibraryPreTranscodePath");
        boolean musicLibraryEnable = Objects.equals(httpRequest.getPostParameter("musicLibraryEnable"), "on");
        String musicLibraryPath = httpRequest.getPostParameter("musicLibraryPath");
        boolean musicScanEnable = Objects.equals(httpRequest.getPostParameter("musicScanEnable"), "on");
        boolean musicPreTranscodeEnable = Objects.equals(httpRequest.getPostParameter("musicPreTranscodeEnable"), "on");
        boolean musicPreTranscodeSixFourK = Objects.equals(httpRequest.getPostParameter("musicPreTranscodeSixFourK"), "on");
        boolean musicPreTranscodeNineSixK = Objects.equals(httpRequest.getPostParameter("musicPreTranscodeNineSixK"), "on");
        boolean musicPreTranscodeOneTwoEightK = Objects.equals(httpRequest.getPostParameter("musicPreTranscodeOneTwoEightK"), "on");
        boolean musicPreTranscodeThreeTwoZeroK = Objects.equals(httpRequest.getPostParameter("musicPreTranscodeThreeTwoZeroK"), "on");
        boolean musicPreTranscodeOneFourOneOneK = Objects.equals(httpRequest.getPostParameter("musicPreTranscodeOneFourOneOneK"), "on");
        int musicScanFrequencyTime = Integer.parseInt(Objects.requireNonNull(httpRequest.getPostParameter("musicScanFrequencyTime")));
        ScanFrequencyEnum musicScanFrequencyType = ScanFrequencyEnum.valueOf(httpRequest.getPostParameter("musicScanFrequencyType"));
        String musicPreTranscodeLibraryPath = httpRequest.getPostParameter("musicPreTranscodeLibrary");
        boolean tvShowLibraryEnable = Objects.equals(httpRequest.getPostParameter("musicPreTranscodeOneFourOneOneK"), "on");
        String tvShowLibraryPath = httpRequest.getPostParameter("tvShowLibraryPath");
        boolean tvShowScanEnable = Objects.equals(httpRequest.getPostParameter("tvShowScanEnable"), "on");
        boolean tvShowPreTranscodeEnable = Objects.equals(httpRequest.getPostParameter("tvShowPreTranscodeEnable"), "on");
        boolean tvPreTranscodeOneFourFourP = Objects.equals(httpRequest.getPostParameter("tvPreTranscodeOneFourFourP"), "on");
        boolean tvPreTranscodeTwoFourZeroP = Objects.equals(httpRequest.getPostParameter("tvPreTranscodeTwoFourZeroP"), "on");
        boolean tvPreTranscodeThreeSixZeroP = Objects.equals(httpRequest.getPostParameter("tvPreTranscodeThreeSixZeroP"), "on");
        boolean tvPreTranscodeFourEightZeroP = Objects.equals(httpRequest.getPostParameter("tvPreTranscodeFourEightZeroP"), "on");
        boolean tvPreTranscodeSevenTwoZeroP = Objects.equals(httpRequest.getPostParameter("tvPreTranscodeSevenTwoZeroP"), "on");
        boolean tvPreTranscodeOneZeroEightZeroP = Objects.equals(httpRequest.getPostParameter("tvPreTranscodeOneZeroEightZeroP"), "on");
        boolean tvPreTranscodeTwoK = Objects.equals(httpRequest.getPostParameter("tvPreTranscodeTwoK"), "on");
        boolean tvPreTranscodeFourK = Objects.equals(httpRequest.getPostParameter("tvPreTranscodeFourK"), "on");
        boolean tvPreTranscodeEightK = Objects.equals(httpRequest.getPostParameter("tvPreTranscodeEightK"), "on");
        int tvShowScanFrequencyTime = Integer.parseInt(Objects.requireNonNull(httpRequest.getPostParameter("tvShowScanFrequencyTime")));
        ScanFrequencyEnum tvShowScanFrequencyType = ScanFrequencyEnum.valueOf(httpRequest.getPostParameter("tvShowScanFrequencyType"));
        String tvShowLibraryPreTranscodePath = httpRequest.getPostParameter("tvShowLibraryPreTranscodePath");
        String serverName = httpRequest.getPostParameter("serverName");
        boolean allowRegistration = Objects.equals(httpRequest.getPostParameter("allowRegistration"), "on");
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
        SearchMethodEnum searchMethod = SearchMethodEnum.valueOf(httpRequest.getPostParameter("searchMethod"));
        int maxSearchResults = Integer.parseInt(Objects.requireNonNull(httpRequest.getPostParameter("maxSearchResults")));
        int maxUIBrowseResults = Integer.parseInt(Objects.requireNonNull(httpRequest.getPostParameter("maxUIBrowseResults")));
        int maxAPIBrowseResults = Integer.parseInt(Objects.requireNonNull(httpRequest.getPostParameter("maxAPIBrowseResults")));
        int cardWidth = Integer.parseInt(Objects.requireNonNull(httpRequest.getPostParameter("cardWidth")));
        boolean stickyTopMenu = Objects.equals(httpRequest.getPostParameter("stickyTopMenu"), "on");
        boolean cacheEnable = Objects.equals(httpRequest.getPostParameter("cacheEnable"), "on");
        int apiCacheSize = Integer.parseInt(Objects.requireNonNull(httpRequest.getPostParameter("apiCacheSize")));
        int databaseCacheSize = Integer.parseInt(Objects.requireNonNull(httpRequest.getPostParameter("databaseCacheSize")));
        int pageCacheSize = Integer.parseInt(Objects.requireNonNull(httpRequest.getPostParameter("pageCacheSize")));
        int maxAssetCacheAge = Integer.parseInt(Objects.requireNonNull(httpRequest.getPostParameter("maxAssetCacheAge")));
        DatabaseTypeEnum databaseType = DatabaseTypeEnum.valueOf(httpRequest.getPostParameter("databaseType"));
        String databaseName = httpRequest.getPostParameter("databaseName");
        int minDatabaseConnections = Integer.parseInt(Objects.requireNonNull(httpRequest.getPostParameter("minDatabaseConnections")));
        int maxDatabaseConnections = Integer.parseInt(Objects.requireNonNull(httpRequest.getPostParameter("maxDatabaseConnections")));
        boolean loggingEnable = Objects.equals(httpRequest.getPostParameter("loggingEnable"), "on");
        String baseLibraryPath = httpRequest.getPostParameter("baseLibraryPath");
        String[] entityPackages = httpRequest.getPostParameter("entityPackages").split(":");
        boolean enableHistory = Objects.equals(httpRequest.getPostParameter("enableHistory"), "on");
        boolean enableUpload = Objects.equals(httpRequest.getPostParameter("enableUpload"), "on");
        String profilePhotoFolder = httpRequest.getPostParameter("profilePhotoFolder");
        int chunk = Integer.parseInt(Objects.requireNonNull(httpRequest.getPostParameter("chunk")));
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
                profilePhotoFolder,
                chunk
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
            settingsController.setChunk(chunk);
        }
        return redirect("/settings");
    }

    /**
     * Renders the upload page
     *
     * @param httpRequest the request
     * @return the page
     * @throws IOException               if it fails
     * @throws SQLException              if it fails
     * @throws InvocationTargetException if it fails
     * @throws IllegalAccessException    if it fails
     * @throws InstantiationException    if it fails
     */
    public Promisable<HttpResponse> upload(HttpRequest httpRequest) throws IOException, SQLException, InvocationTargetException, IllegalAccessException, InstantiationException {
        ExtendedUserEntity userEntity = (ExtendedUserEntity) getUser(httpRequest, extendedUserEntityController);
        boolean loggedIn = verifyApiKey(httpRequest);
        if (settingsController.isEnableUpload()) {
            return ok(uploadPage.render(
                    true,
                    loggedIn,
                    notificationEntityController.getNumberOfUnread(userEntity),
                    userEntity.getPermissionGroup(),
                    settingsController.getServerName(),
                    settingsController.isEnableUpload(),
                    settingsController.isMovieLibraryEnable(),
                    settingsController.isTvShowLibraryEnable(),
                    settingsController.isGameLibraryEnable(),
                    settingsController.isMusicLibraryEnable(),
                    settingsController.isBookLibraryEnable(),
                    genericCAO
                ).getBytes()
            );
        }
        return redirect("/disabled");
    }

    /**
     * Renders the denied page
     *
     * @param httpRequest the request
     * @return the page
     */
    public @NotNull Promisable<HttpResponse> denied(HttpRequest httpRequest) {
        try {
            boolean loggedIn = verifyApiKey(httpRequest);
            ExtendedUserEntity userEntity = (ExtendedUserEntity) getUser(httpRequest, extendedUserEntityController);
            return ok(deniedPage.render(
                    loggedIn,
                    notificationEntityController.getNumberOfUnread(userEntity),
                    userEntity.getPermissionGroup(),
                    settingsController.getServerName(),
                    settingsController.isEnableUpload(),
                    settingsController.isMovieLibraryEnable(),
                    settingsController.isTvShowLibraryEnable(),
                    settingsController.isGameLibraryEnable(),
                    settingsController.isMusicLibraryEnable(),
                    settingsController.isBookLibraryEnable(),
                    genericCAO
                ).getBytes()
            );
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
            return redirect("/error");
        }
    }

    /**
     * Renders the disabled page
     *
     * @param httpRequest the request
     * @return the page
     */
    public @NotNull Promisable<HttpResponse> disabled(HttpRequest httpRequest) {
        try {
            boolean loggedIn = verifyApiKey(httpRequest);
            ExtendedUserEntity userEntity = (ExtendedUserEntity) getUser(httpRequest, extendedUserEntityController);
            return ok(disabledPage.render(
                    loggedIn,
                    notificationEntityController.getNumberOfUnread(userEntity),
                    userEntity.getPermissionGroup(),
                    settingsController.getServerName(),
                    settingsController.isEnableUpload(),
                    settingsController.isMovieLibraryEnable(),
                    settingsController.isTvShowLibraryEnable(),
                    settingsController.isGameLibraryEnable(),
                    settingsController.isMusicLibraryEnable(),
                    settingsController.isBookLibraryEnable(),
                    genericCAO
                ).getBytes()
            );
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
        }
        return redirect("/error");
    }

    /**
     * Renders the error page
     *
     * @param httpRequest the request
     * @return the page
     */
    public Promisable<HttpResponse> error(HttpRequest httpRequest) {
        try {
            boolean loggedIn = verifyApiKey(httpRequest);
            ExtendedUserEntity userEntity = (ExtendedUserEntity) getUser(httpRequest, extendedUserEntityController);
            return ok(
                errorPage.render(
                    loggedIn,
                    notificationEntityController.getNumberOfUnread(userEntity),
                    userEntity.getPermissionGroup(),
                    settingsController.getServerName(),
                    settingsController.isEnableUpload(),
                    settingsController.isMovieLibraryEnable(),
                    settingsController.isTvShowLibraryEnable(),
                    settingsController.isGameLibraryEnable(),
                    settingsController.isMusicLibraryEnable(),
                    settingsController.isBookLibraryEnable(),
                    genericCAO
                ).getBytes()
            );
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
        }
        return redirect("/error");
    }

    /**
     * Renders the not found page
     *
     * @param httpRequest the request
     * @return the page
     */
    public Promisable<HttpResponse> notFound(HttpRequest httpRequest) {
        try {
            boolean loggedIn = verifyApiKey(httpRequest);
            ExtendedUserEntity userEntity = (ExtendedUserEntity) getUser(httpRequest, extendedUserEntityController);
            return ok(notFoundPage.render(
                    loggedIn,
                    notificationEntityController.getNumberOfUnread(userEntity),
                    userEntity.getPermissionGroup(),
                    settingsController.getServerName(),
                    settingsController.isEnableUpload(),
                    settingsController.isMovieLibraryEnable(),
                    settingsController.isTvShowLibraryEnable(),
                    settingsController.isGameLibraryEnable(),
                    settingsController.isMusicLibraryEnable(),
                    settingsController.isBookLibraryEnable(),
                    genericCAO
                ).getBytes()
            );
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
        }
        return redirect("/error");
    }

    /**
     * Renders the under construction page
     *
     * @param httpRequest the request
     * @return the page
     */
    public Promisable<HttpResponse> underConstruction(HttpRequest httpRequest) {
        try {
            boolean loggedIn = verifyApiKey(httpRequest);
            ExtendedUserEntity userEntity = (ExtendedUserEntity) getUser(httpRequest, extendedUserEntityController);
            return ok(underConstructionPage.render(
                    loggedIn,
                    notificationEntityController.getNumberOfUnread(userEntity),
                    userEntity.getPermissionGroup(),
                    settingsController.getServerName(),
                    settingsController.isEnableUpload(),
                    settingsController.isMovieLibraryEnable(),
                    settingsController.isTvShowLibraryEnable(),
                    settingsController.isGameLibraryEnable(),
                    settingsController.isMusicLibraryEnable(),
                    settingsController.isBookLibraryEnable(),
                    genericCAO
                ).getBytes()
            );
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
        }
        return redirect("/error");
    }

    /**
     * Uploads a file using the POST method
     *
     * @param httpRequest the request
     * @return the response (page or code)
     * @throws ClassNotFoundException the entity type doesn't exist
     */
    public Promisable<HttpResponse> postUpload(HttpRequest httpRequest) throws ClassNotFoundException {
        String mediaType = httpRequest.getPathParameter("mediaType");
        switch (mediaType) {
            case "movieentity", "bookentity", "gameentity", "musicentity", "tvshowentity" -> {
                return handleUpload(mediaType, "files", settingsController.getEntityPackages(), settingsController.getBaseLibraryPath(), controllers, httpRequest, genericCAO);
            }
        }
        return error();
    }
}
