package tech.tresearchgroup.babygalago.controller.modules;


import com.google.gson.Gson;
import com.meilisearch.sdk.Client;
import com.zaxxer.hikari.HikariDataSource;
import io.activej.inject.annotation.Provides;
import io.activej.inject.module.AbstractModule;
import io.activej.serializer.SerializerBuilder;
import org.quartz.SchedulerException;
import tech.tresearchgroup.babygalago.controller.SettingsController;
import tech.tresearchgroup.babygalago.controller.TaskController;
import tech.tresearchgroup.babygalago.controller.controllers.*;
import tech.tresearchgroup.babygalago.controller.endpoints.AssetEndpointController;
import tech.tresearchgroup.babygalago.controller.endpoints.LoginEndpointsController;
import tech.tresearchgroup.babygalago.controller.endpoints.SearchEndpointsController;
import tech.tresearchgroup.babygalago.controller.endpoints.api.*;
import tech.tresearchgroup.babygalago.controller.endpoints.ui.CRUDEndpointsController;
import tech.tresearchgroup.babygalago.controller.endpoints.ui.MainEndpointsController;
import tech.tresearchgroup.babygalago.controller.endpoints.ui.PlayEndpointsController;
import tech.tresearchgroup.babygalago.view.pages.*;
import tech.tresearchgroup.dao.model.LockType;
import tech.tresearchgroup.palila.controller.GenericController;
import tech.tresearchgroup.palila.model.entities.*;
import tech.tresearchgroup.palila.model.enums.PermissionGroupEnum;
import tech.tresearchgroup.palila.model.enums.PlaybackQualityEnum;
import tech.tresearchgroup.schemas.galago.entities.*;
import tech.tresearchgroup.schemas.galago.enums.*;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * This class is used for dependency injection. It sets up controllers to be used elsewhere
 */
public class ControllerModule extends AbstractModule {
    @Provides
    TaskController scheduleController() throws SchedulerException {
        return new TaskController();
    }

    @Provides
    AssetEndpointController assetEndpointController(SettingsController settingsController) {
        return new AssetEndpointController(settingsController);
    }

    @Provides
    CRUDEndpointsController endpointsController(Map<String, GenericController> controllers,
                                                ExtendedUserEntityController extendedUserEntityController,
                                                SettingsController settingsController,
                                                ViewPage viewPage,
                                                EntityPage entityPage,
                                                NotificationEntityController notificationEntityController) {
        return new CRUDEndpointsController(
            controllers,
            extendedUserEntityController,
            settingsController,
            viewPage,
            entityPage,
            notificationEntityController
        );
    }

    @Provides
    MainEndpointsController mainEndpointsController(MovieEntityController movieEntityController,
                                                    TvShowEntityController tvShowEntityController,
                                                    GameEntityController gameEntityController,
                                                    SongEntityController songEntityController,
                                                    BookEntityController bookEntityController,
                                                    NotificationEntityController notificationEntityController,
                                                    NewsArticleEntityController newsArticleEntityController,
                                                    QueueEntityController queueEntityController,
                                                    ExtendedUserEntityController extendedUserEntityController,
                                                    SettingsController settingsController,
                                                    LoginEndpointsController loginEndpointsController,
                                                    AboutPage aboutPage,
                                                    IndexPage indexPage,
                                                    LoginPage loginPage,
                                                    ResetPage resetPage,
                                                    RegisterPage registerPage,
                                                    LicensesPage licensesPage,
                                                    NewsPage newsPage,
                                                    NotificationsPage notificationsPage,
                                                    ProfilePage profilePage,
                                                    QueuePage queuePage,
                                                    SearchPage searchPage,
                                                    SettingsPage settingsPage,
                                                    UploadPage uploadPage,
                                                    DeniedPage deniedPage,
                                                    DisabledPage disabledPage,
                                                    ErrorPage errorPage,
                                                    NotFoundPage notFoundPage,
                                                    UnderConstructionPage underConstructionPage,
                                                    Map<String, GenericController> controllers) {
        return new MainEndpointsController(
            movieEntityController,
            tvShowEntityController,
            gameEntityController,
            songEntityController,
            bookEntityController,
            notificationEntityController,
            newsArticleEntityController,
            queueEntityController,
            extendedUserEntityController,
            settingsController,
            loginEndpointsController,
            aboutPage,
            indexPage,
            loginPage,
            resetPage,
            registerPage,
            licensesPage,
            newsPage,
            notificationsPage,
            profilePage,
            queuePage,
            settingsPage,
            uploadPage,
            deniedPage,
            disabledPage,
            errorPage,
            notFoundPage,
            underConstructionPage,
            controllers
        );
    }

    @Provides
    PlayEndpointsController playEndpointsController(Map<String, GenericController> controllers,
                                                    ExtendedUserEntityController extendedUserEntityController,
                                                    SettingsController settingsController,
                                                    VideoFileEntityController videoFileEntityController,
                                                    UserSettingsEntityController userSettingsEntityController,
                                                    PlayPage playPage,
                                                    NotificationEntityController notificationEntityController) {
        return new PlayEndpointsController(
            controllers,
            extendedUserEntityController,
            settingsController,
            videoFileEntityController,
            userSettingsEntityController,
            playPage,
            notificationEntityController
        );
    }

    @Provides
    ExtendedUserEntityController extendedUserEntityController(HikariDataSource hikariDataSource,
                                                              Gson gson,
                                                              Client client) throws Exception {
        return new ExtendedUserEntityController(
            hikariDataSource,
            gson,
            client,
            SerializerBuilder.create().build(ExtendedUserEntity.class),
            20,
            new ExtendedUserEntity(
                new Date(),
                new Date(),
                1L,
                null,
                PermissionGroupEnum.USER,
                "username",
                "user@email.com",
                "password",
                "yourapikey",
                new UserSettingsEntity()
            )
        );
    }

    @Provides
    TaskEndpointsController taskEndpointsController(TaskController scheduleController) {
        return new TaskEndpointsController(
            scheduleController
        );
    }

    @Provides
    SettingsEndpointsController settingsEndpointsController(ExtendedUserEntityController extendedUserEntityController,
                                                            UserSettingsEntityController userSettingsEntityController) {
        return new SettingsEndpointsController(
            extendedUserEntityController,
            userSettingsEntityController
        );
    }

    @Provides
    QueueEndpointsController queueEndpointsController(QueueEntityController queueEntityController,
                                                      Gson gson) {
        return new QueueEndpointsController(
            queueEntityController,
            gson
        );
    }

    @Provides
    MovieEntityController movieController(HikariDataSource hikariDataSource,
                                          Gson gson,
                                          Client client,
                                          ExtendedUserEntityController extendedUserEntityController) throws Exception {
        return new MovieEntityController(
            hikariDataSource,
            gson,
            client,
            SerializerBuilder.create().build(MovieEntity.class),
            20,
            new MovieEntity(
                new Date(),
                new Date(),
                1L,
                LockType.BAN,
                "The title",
                new LinkedList<>(),
                new ImageEntity(),
                new LinkedList<>(),
                new VideoFileEntity(),
                new LinkedList<>(),
                new LinkedList<>(),
                "2022-10-31",
                3600,
                MPAARatingEnum.G,
                3,
                MovieGenreEnum.ADVENTURE,
                new LinkedList<>(),
                new LinkedList<>(),
                new LinkedList<>(),
                new LinkedList<>(),
                "This is the story of billy joe and bobby sue",
                "EN",
                10000000L,
                99999999999L,
                999999999991L,
                1L,
                "16:9",
                CountryEnum.CANADA,
                1000L
            ),
            extendedUserEntityController
        );
    }

    @Provides
    SubtitleEntityController subtitleController(HikariDataSource hikariDataSource,
                                                Gson gson,
                                                Client client,
                                                ExtendedUserEntityController extendedUserEntityController) throws Exception {
        return new SubtitleEntityController(
            hikariDataSource,
            gson,
            client,
            SerializerBuilder.create().build(SubtitleEntity.class),
            20,
            new SubtitleEntity(
                new Date(),
                new Date(),
                1L,
                null,
                "EN",
                "These are subtitles",
                1L
            ),
            extendedUserEntityController
        );
    }

    @Provides
    GamePlatformReleaseEntityController gamePlatformReleaseController(HikariDataSource hikariDataSource,
                                                                      Gson gson,
                                                                      Client client,
                                                                      ExtendedUserEntityController extendedUserEntityController) throws Exception {
        return new GamePlatformReleaseEntityController(
            hikariDataSource,
            gson,
            client,
            SerializerBuilder.create().build(GamePlatformReleaseEntity.class),
            20,
            new GamePlatformReleaseEntity(
                new Date(),
                new Date(),
                1L,
                null,
                GamePlatformEnum.PC,
                "2022-10-31",
                100L
            ),
            extendedUserEntityController
        );
    }

    @Provides
    SongEntityController songController(HikariDataSource hikariDataSource,
                                        Gson gson,
                                        Client client,
                                        ExtendedUserEntityController extendedUserEntityController) throws Exception {
        return new SongEntityController(
            hikariDataSource,
            gson,
            client,
            SerializerBuilder.create().build(SongEntity.class),
            20,
            new SongEntity(
                new Date(),
                new Date(),
                2L,
                null,
                "The title of the song",
                new LinkedList<>(),
                new LinkedList<>(),
                "2022-10-31",
                new LinkedList<>(),
                1L
            ),
            extendedUserEntityController
        );
    }

    @Provides
    RatingEntityController ratingController(HikariDataSource hikariDataSource,
                                            Gson gson,
                                            Client client,
                                            ExtendedUserEntityController extendedUserEntityController) throws Exception {
        return new RatingEntityController(
            hikariDataSource,
            gson,
            client,
            SerializerBuilder.create().build(RatingEntity.class),
            20,
            new RatingEntity(
                new Date(),
                new Date(),
                1L,
                null,
                1L,
                MediaTypeEnum.MOVIE,
                1L,
                3,
                100L
            ),
            extendedUserEntityController
        );
    }

    @Provides
    BookEntityController bookController(HikariDataSource hikariDataSource,
                                        Gson gson,
                                        Client client,
                                        ExtendedUserEntityController extendedUserEntityController) throws Exception {
        return new BookEntityController(
            hikariDataSource,
            gson,
            client,
            SerializerBuilder.create().build(BookEntity.class),
            20,
            new BookEntity(
                new Date(),
                new Date(),
                1L,
                null,
                new ImageEntity(),
                new LinkedList<>(),
                "Book title",
                "This is the description of the most awesome book in the world",
                new LinkedList<>(),
                100,
                new LinkedList<>(),
                1L
            ),
            extendedUserEntityController
        );
    }

    @Provides
    GameSeriesEntityController gameSeriesController(HikariDataSource hikariDataSource,
                                                    Gson gson,
                                                    Client client,
                                                    ExtendedUserEntityController extendedUserEntityController) throws Exception {
        return new GameSeriesEntityController(
            hikariDataSource,
            gson,
            client,
            SerializerBuilder.create().build(GameSeriesEntity.class),
            20,
            new GameSeriesEntity(
                new Date(),
                new Date(),
                1L,
                null,
                "Game series",
                1L
            ),
            extendedUserEntityController
        );
    }

    @Provides
    GameEngineEntityController gameEngineController(HikariDataSource hikariDataSource,
                                                    Gson gson,
                                                    Client client,
                                                    ExtendedUserEntityController extendedUserEntityController) throws Exception {
        return new GameEngineEntityController(
            hikariDataSource,
            gson,
            client,
            SerializerBuilder.create().build(GameEngineEntity.class),
            20,
            new GameEngineEntity(
                new Date(),
                new Date(),
                1L,
                null,
                "Game engine name",
                1L
            ),
            extendedUserEntityController
        );
    }

    @Provides
    AlbumEntityController albumController(HikariDataSource hikariDataSource,
                                          Gson gson,
                                          Client client,
                                          ExtendedUserEntityController extendedUserEntityController) throws Exception {
        return new AlbumEntityController(
            hikariDataSource,
            gson,
            client,
            SerializerBuilder.create().build(AlbumEntity.class),
            20,
            new AlbumEntity(
                new Date(),
                new Date(),
                1L,
                null,
                "Album",
                new ImageEntity(),
                new LinkedList<>(),
                "2022-10-31",
                new LinkedList<>(),
                new LinkedList<>(),
                1L
            ),
            extendedUserEntityController
        );
    }

    @Provides
    TvShowEntityController tvShowController(HikariDataSource hikariDataSource,
                                            Gson gson,
                                            Client client,
                                            ExtendedUserEntityController extendedUserEntityController) throws Exception {
        return new TvShowEntityController(
            hikariDataSource,
            gson,
            client,
            SerializerBuilder.create().build(TvShowEntity.class),
            20,
            new TvShowEntity(
                new Date(),
                new Date(),
                1L,
                null,
                "Tv Show title",
                "It's a show about nothing.",
                new LinkedList<>(),
                ShowStatusEnum.FINISHED,
                "2022-10-31",
                "Thursday 9:00PM EST",
                3600,
                new LinkedList<>(),
                new LinkedList<>(),
                "EN",
                new LinkedList<>(),
                new VideoFileEntity(),
                new LinkedList<>(),
                new ImageEntity(),
                new LinkedList<>(),
                1L
            ),
            extendedUserEntityController
        );
    }

    @Provides
    PersonEntityController personController(HikariDataSource hikariDataSource,
                                            Gson gson,
                                            Client client,
                                            ExtendedUserEntityController extendedUserEntityController) throws Exception {
        return new PersonEntityController(
            hikariDataSource,
            gson,
            client,
            SerializerBuilder.create().build(PersonEntity.class),
            20,
            new PersonEntity(
                new Date(),
                new Date(),
                1L,
                null,
                new ImageEntity(),
                "Person entity",
                "first",
                "middle(s)",
                "last",
                "2022-10-31",
                new LocationEntity(),
                "betty lou",
                1L
            ),
            extendedUserEntityController
        );
    }

    @Provides
    SeasonEntityController seasonController(HikariDataSource hikariDataSource,
                                            Gson gson,
                                            Client client,
                                            ExtendedUserEntityController extendedUserEntityController) throws Exception {
        return new SeasonEntityController(
            hikariDataSource,
            gson,
            client,
            SerializerBuilder.create().build(SeasonEntity.class),
            20,
            new SeasonEntity(
                new Date(),
                new Date(),
                1L,
                null,
                "Season 1",
                new ImageEntity(),
                100,
                1L
            ),
            extendedUserEntityController
        );
    }

    @Provides
    GameEntityController gameController(HikariDataSource hikariDataSource,
                                        Gson gson,
                                        Client client,
                                        ExtendedUserEntityController extendedUserEntityController) throws Exception {
        return new GameEntityController(
            hikariDataSource,
            gson,
            client,
            SerializerBuilder.create().build(GameEntity.class),
            20,
            new GameEntity(
                new Date(),
                new Date(),
                1L,
                null,
                "Awesome game",
                "Wicked description",
                new LinkedList<>(),
                new ImageEntity(),
                new LinkedList<>(),
                new VideoFileEntity(),
                new LinkedList<>(),
                new LinkedList<>(),
                ESRBRatingEnum.EVERYONE,
                new LinkedList<>(),
                new LinkedList<>(),
                new LinkedList<>(),
                new LinkedList<>(),
                new LinkedList<>(),
                new GameSeriesEntity(),
                new GameEngineEntity(),
                new LinkedList<>(),
                1,
                true,
                true,
                28800,
                1L
            ),
            extendedUserEntityController
        );
    }

    @Provides
    ArtistEntityController artistController(HikariDataSource hikariDataSource,
                                            Gson gson,
                                            Client client,
                                            ExtendedUserEntityController extendedUserEntityController) throws Exception {
        return new ArtistEntityController(
            hikariDataSource,
            gson,
            client,
            SerializerBuilder.create().build(ArtistEntity.class),
            20,
            new ArtistEntity(
                new Date(),
                new Date(),
                1L,
                null,
                "The Bee Gees",
                new ImageEntity(),
                new LinkedList<>(),
                new LinkedList<>(),
                new LinkedList<>(),
                1L
            ),
            extendedUserEntityController
        );
    }

    @Provides
    ImageEntityController imageController(HikariDataSource hikariDataSource,
                                          Gson gson,
                                          Client client,
                                          ExtendedUserEntityController extendedUserEntityController) throws Exception {
        return new ImageEntityController(
            hikariDataSource,
            gson,
            client,
            SerializerBuilder.create().build(ImageEntity.class),
            20,
            new ImageEntity(
                new Date(),
                new Date(),
                1L,
                null,
                "A movie poster",
                new ImageFileEntity(),
                "This is a poster that would have been posted at movie theaters",
                1L
            ),
            extendedUserEntityController
        );
    }

    @Provides
    NotificationEntityController notificationController(HikariDataSource hikariDataSource,
                                                        Gson gson,
                                                        Client client,
                                                        ExtendedUserEntityController extendedUserEntityController) throws Exception {
        return new NotificationEntityController(
            hikariDataSource,
            gson,
            client,
            SerializerBuilder.create().build(NotificationEntity.class),
            20,
            new NotificationEntity(
                new Date(),
                new Date(),
                1L,
                null,
                1L,
                NotificationErrorTypeEnum.ERROR,
                true,
                "File not found",
                "Someone is trying to play: The Princess Bride (1987) but the file is no longer on disk!"
            ),
            extendedUserEntityController
        );
    }

    @Provides
    LyricsEntityController lyricsController(HikariDataSource hikariDataSource,
                                            Gson gson,
                                            Client client,
                                            ExtendedUserEntityController extendedUserEntityController) throws Exception {
        return new LyricsEntityController(
            hikariDataSource,
            gson,
            client,
            SerializerBuilder.create().build(LyricsEntity.class),
            20,
            new LyricsEntity(
                new Date(),
                new Date(),
                1L,
                null,
                "Tragedy, when you lose control and you got no soul.",
                1L,
                LanguagesEnum.ENGLISH
            ),
            extendedUserEntityController
        );
    }

    @Provides
    CompanyEntityController companyController(HikariDataSource hikariDataSource,
                                              Gson gson,
                                              Client client,
                                              ExtendedUserEntityController extendedUserEntityController) throws Exception {
        return new CompanyEntityController(
            hikariDataSource,
            gson,
            client,
            SerializerBuilder.create().build(CompanyEntity.class),
            20,
            new CompanyEntity(
                new Date(),
                new Date(),
                1L,
                null,
                new ImageEntity(),
                "TRG",
                1L
            ),
            extendedUserEntityController
        );
    }

    @Provides
    LoginEndpointsController loginEndpointsController(ExtendedUserEntityController extendedUserEntityController,
                                                      SettingsController settingsController,
                                                      Gson gson) {
        return new LoginEndpointsController(
            extendedUserEntityController,
            settingsController,
            gson
        );
    }

    @Provides
    NewsArticleEntityController newsArticleController(HikariDataSource hikariDataSource,
                                                      Gson gson,
                                                      Client client,
                                                      ExtendedUserEntityController extendedUserEntityController) throws Exception {
        return new NewsArticleEntityController(
            hikariDataSource,
            gson,
            client,
            SerializerBuilder.create().build(NewsArticleEntity.class),
            20,
            new NewsArticleEntity(
                new Date(),
                new Date(),
                1L,
                null,
                true,
                "New release",
                "Update X.X.X was released. It includes the following...",
                "Update X.X.X was released. It includes performance the following updates, feature additions and bug fixes:"
            ),
            extendedUserEntityController
        );
    }

    @Provides
    LocationEntityController locationController(HikariDataSource hikariDataSource,
                                                Gson gson,
                                                Client client,
                                                ExtendedUserEntityController extendedUserEntityController) throws Exception {
        return new LocationEntityController(
            hikariDataSource,
            gson,
            client,
            SerializerBuilder.create().build(LocationEntity.class),
            20,
            new LocationEntity(
                new Date(),
                new Date(),
                1L,
                null,
                "Stratford",
                43.372122,
                -80.985744,
                1L
            ),
            extendedUserEntityController
        );
    }

    @Provides
    CharacterEntityController characterController(HikariDataSource hikariDataSource,
                                                  Gson gson,
                                                  Client client,
                                                  ExtendedUserEntityController extendedUserEntityController) throws Exception {
        return new CharacterEntityController(
            hikariDataSource,
            gson,
            client,
            SerializerBuilder.create().build(CharacterEntity.class),
            20,
            new CharacterEntity(
                new Date(),
                new Date(),
                1L,
                null,
                "Mr",
                "Ronald",
                "Ulysses",
                "Swanson",
                "1967-05-20",
                "Ron",
                new PersonEntity(),
                1L
            ),
            extendedUserEntityController
        );
    }

    @Provides
    GeneralEndpointsController generalEndpointsController(ImageEntityController imageEntityController,
                                                          FileEntityController fileEntityController,
                                                          ExtendedUserEntityController extendedUserEntityController,
                                                          SettingsController settingsController) {
        return new GeneralEndpointsController(
            extendedUserEntityController,
            settingsController
        );
    }

    @Provides
    QueueEntityController queueController(HikariDataSource hikariDataSource,
                                          Gson gson,
                                          Client client,
                                          SettingsController settingsController,
                                          ExtendedUserEntityController extendedUserEntityController) throws Exception {
        return new QueueEntityController(
            hikariDataSource,
            gson,
            client,
            SerializerBuilder.create().build(QueueEntity.class),
            20,
            settingsController,
            extendedUserEntityController
        );
    }

    @Provides
    EpisodeEntityController episodeController(HikariDataSource hikariDataSource,
                                              Gson gson,
                                              Client client,
                                              ExtendedUserEntityController extendedUserEntityController) throws Exception {
        return new EpisodeEntityController(
            hikariDataSource,
            gson,
            client,
            SerializerBuilder.create().build(EpisodeEntity.class),
            20,
            new EpisodeEntity(
                new Date(),
                new Date(),
                1L,
                null,
                "That episode where",
                "Things happened",
                new ImageEntity(),
                "2022-10-31",
                3600L,
                new LinkedList<>(),
                "EN",
                new LinkedList<>(),
                new LinkedList<>()
            ),
            extendedUserEntityController
        );
    }

    @Provides
    FileEntityController fileController(HikariDataSource hikariDataSource,
                                        Gson gson,
                                        Client client,
                                        ExtendedUserEntityController extendedUserEntityController) throws Exception {
        return new FileEntityController(
            hikariDataSource,
            gson,
            client,
            SerializerBuilder.create().build(FileEntity.class),
            20,
            new FileEntity(
                new Date(),
                new Date(),
                1L,
                null,
                1L,
                "/home/c3hYVj8lTv0/yt"
            ),
            extendedUserEntityController
        );
    }

    @Provides
    AudioFileEntityController audioFileEntityController(HikariDataSource hikariDataSource,
                                                        Gson gson,
                                                        Client client,
                                                        ExtendedUserEntityController extendedUserEntityController) throws Exception {
        return new AudioFileEntityController(
            hikariDataSource,
            gson,
            client,
            SerializerBuilder.create().build(AudioFileEntity.class),
            20,
            new AudioFileEntity(
                new Date(),
                new Date(),
                1L,
                null,
                1L,
                "/home/hD8LgbJVNGQ/yt"
            ),
            extendedUserEntityController
        );
    }

    @Provides
    BookFileEntityController bookFileEntityController(HikariDataSource hikariDataSource,
                                                      Gson gson,
                                                      Client client,
                                                      ExtendedUserEntityController extendedUserEntityController) throws Exception {
        return new BookFileEntityController(
            hikariDataSource,
            gson,
            client,
            SerializerBuilder.create().build(BookFileEntity.class),
            20,
            new BookFileEntity(
                new Date(),
                new Date(),
                1L,
                null,
                1L,
                "/home/zIE-5hg7FoA/yt"
            ),
            extendedUserEntityController
        );
    }

    @Provides
    GameFileEntityController gameFileEntityController(HikariDataSource hikariDataSource,
                                                      Gson gson,
                                                      Client client,
                                                      ExtendedUserEntityController extendedUserEntityController) throws Exception {
        return new GameFileEntityController(
            hikariDataSource,
            gson,
            client,
            SerializerBuilder.create().build(GameFileEntity.class),
            20,
            new GameFileEntity(
                new Date(),
                new Date(),
                1L,
                null,
                1L,
                "/home/rmpo0csiIMs/yt"
            ),
            extendedUserEntityController
        );
    }

    @Provides
    ImageFileEntityController imageFileEntityController(HikariDataSource hikariDataSource,
                                                        Gson gson,
                                                        Client client,
                                                        ExtendedUserEntityController extendedUserEntityController) throws Exception {
        return new ImageFileEntityController(
            hikariDataSource,
            gson,
            client,
            SerializerBuilder.create().build(ImageFileEntity.class),
            20,
            new ImageFileEntity(
                new Date(),
                new Date(),
                1L,
                null,
                1L,
                "/home/78emGJzXo7Q/yt"
            ),
            extendedUserEntityController
        );
    }

    @Provides
    VideoFileEntityController videoFileEntityController(SettingsController settingsController,
                                                        HikariDataSource hikariDataSource,
                                                        Gson gson,
                                                        Client client,
                                                        ExtendedUserEntityController extendedUserEntityController) throws Exception {
        return new VideoFileEntityController(
            hikariDataSource,
            gson,
            client,
            SerializerBuilder.create().build(VideoFileEntity.class),
            20,
            new VideoFileEntity(
                new Date(),
                new Date(),
                1L,
                null,
                1L,
                "/home/efmiutIr97c/yt",
                PlaybackQualityEnum.ORIGINAL
            ),
            extendedUserEntityController
        );
    }

    @Provides
    UserSettingsEntityController userSettingsController(HikariDataSource hikariDataSource,
                                                        Gson gson,
                                                        Client client,
                                                        ExtendedUserEntityController extendedUserEntityController,
                                                        NotificationEntityController notificationEntityController,
                                                        SettingsController settingsController,
                                                        UserSettingsPage userSettingsPage) throws Exception {
        return new UserSettingsEntityController(
            hikariDataSource,
            gson,
            client,
            SerializerBuilder.create().build(UserSettingsEntity.class),
            20,
            new UserSettingsEntity(
                new Date(),
                new Date(),
                1L,
                null,
                InterfaceMethodEnum.MODAL,
                PlaybackQualityEnum.ORIGINAL,
                DisplayModeEnum.POSTER,
                true,
                true,
                true,
                true,
                true,
                true,
                true,
                true,
                true,
                true,
                true,
                true,
                true,
                true,
                true,
                true,
                true,
                true,
                true,
                20,
                20,
                240,
                true
            ),
            extendedUserEntityController,
            notificationEntityController,
            settingsController,
            userSettingsPage);
    }

    @Provides
    Map<String, GenericController> controllers(AlbumEntityController albumEntityController,
                                               ArtistEntityController artistEntityController,
                                               BookEntityController bookEntityController,
                                               CharacterEntityController characterEntityController,
                                               CompanyEntityController companyEntityController,
                                               EpisodeEntityController episodeEntityController,
                                               FileEntityController fileEntityController,
                                               AudioFileEntityController audioFileEntityController,
                                               BookFileEntityController bookFileEntityController,
                                               GameFileEntityController gameFileEntityController,
                                               ImageFileEntityController imageFileEntityController,
                                               VideoFileEntityController videoFileEntityController,
                                               GameEngineEntityController gameEngineEntityController,
                                               GameEntityController gameEntityController,
                                               GamePlatformReleaseEntityController gamePlatformReleaseEntityController,
                                               GameSeriesEntityController gameSeriesEntityController,
                                               ImageEntityController imageEntityController,
                                               LocationEntityController locationEntityController,
                                               LyricsEntityController lyricsEntityController,
                                               MovieEntityController movieEntityController,
                                               NewsArticleEntityController newsArticleEntityController,
                                               NotificationEntityController notificationEntityController,
                                               PersonEntityController personEntityController,
                                               QueueEntityController queueEntityController,
                                               RatingEntityController ratingEntityController,
                                               SeasonEntityController seasonEntityController,
                                               SongEntityController songEntityController,
                                               SubtitleEntityController subtitleEntityController,
                                               TvShowEntityController tvShowEntityController,
                                               UserSettingsEntityController userSettingsEntityController,
                                               ExtendedUserEntityController extendedUserEntityController) {
        Map<String, GenericController> list = new HashMap<>();
        list.put(albumEntityController.getClass().getSimpleName().toLowerCase(), albumEntityController);
        list.put(artistEntityController.getClass().getSimpleName().toLowerCase(), artistEntityController);
        list.put(bookEntityController.getClass().getSimpleName().toLowerCase(), bookEntityController);
        list.put(characterEntityController.getClass().getSimpleName().toLowerCase(), characterEntityController);
        list.put(companyEntityController.getClass().getSimpleName().toLowerCase(), companyEntityController);
        list.put(episodeEntityController.getClass().getSimpleName().toLowerCase(), episodeEntityController);
        list.put(fileEntityController.getClass().getSimpleName().toLowerCase(), fileEntityController);
        list.put(audioFileEntityController.getClass().getSimpleName().toLowerCase(), audioFileEntityController);
        list.put(bookFileEntityController.getClass().getSimpleName().toLowerCase(), bookFileEntityController);
        list.put(gameFileEntityController.getClass().getSimpleName().toLowerCase(), gameFileEntityController);
        list.put(imageFileEntityController.getClass().getSimpleName().toLowerCase(), imageFileEntityController);
        list.put(videoFileEntityController.getClass().getSimpleName().toLowerCase(), videoFileEntityController);
        list.put(gameEngineEntityController.getClass().getSimpleName().toLowerCase(), gameEngineEntityController);
        list.put(gameEntityController.getClass().getSimpleName().toLowerCase(), gameEntityController);
        list.put(gamePlatformReleaseEntityController.getClass().getSimpleName().toLowerCase(), gamePlatformReleaseEntityController);
        list.put(gameSeriesEntityController.getClass().getSimpleName().toLowerCase(), gameSeriesEntityController);
        list.put(imageEntityController.getClass().getSimpleName().toLowerCase(), imageEntityController);
        list.put(locationEntityController.getClass().getSimpleName().toLowerCase(), locationEntityController);
        list.put(lyricsEntityController.getClass().getSimpleName().toLowerCase(), lyricsEntityController);
        list.put(movieEntityController.getClass().getSimpleName().toLowerCase(), movieEntityController);
        list.put(newsArticleEntityController.getClass().getSimpleName().toLowerCase(), newsArticleEntityController);
        list.put(notificationEntityController.getClass().getSimpleName().toLowerCase(), notificationEntityController);
        list.put(personEntityController.getClass().getSimpleName().toLowerCase(), personEntityController);
        list.put(queueEntityController.getClass().getSimpleName().toLowerCase(), queueEntityController);
        list.put(ratingEntityController.getClass().getSimpleName().toLowerCase(), ratingEntityController);
        list.put(seasonEntityController.getClass().getSimpleName().toLowerCase(), seasonEntityController);
        list.put(songEntityController.getClass().getSimpleName().toLowerCase(), songEntityController);
        list.put(subtitleEntityController.getClass().getSimpleName().toLowerCase(), subtitleEntityController);
        list.put(tvShowEntityController.getClass().getSimpleName().toLowerCase(), tvShowEntityController);
        list.put(userSettingsEntityController.getClass().getSimpleName().toLowerCase(), userSettingsEntityController);
        return list;
    }

    @Provides
    MediaTypeEndpointsController mediaTypeEndpointsController(Map<String, GenericController> controllers,
                                                              SettingsController settingsController,
                                                              ExtendedUserEntityController extendedUserEntityController) {
        return new MediaTypeEndpointsController(
            controllers,
            settingsController,
            extendedUserEntityController
        );
    }

    @Provides
    UserEndpointsController userEndpointsController(ExtendedUserEntityController extendedUserEntityController,
                                                    SettingsController settingsController) {
        return new UserEndpointsController(extendedUserEntityController, settingsController);
    }

    @Provides
    NewsEndpointsController newsEndpointsController(NewsArticleEntityController newsArticleEntityController) {
        return new NewsEndpointsController(newsArticleEntityController);
    }

    @Provides
    RatingEndpointsController ratingEndpointsController(RatingEntityController ratingEntityController) {
        return new RatingEndpointsController(ratingEntityController);
    }

    @Provides
    NotificationsEndpointsController notificationsEndpointsController(NotificationEntityController notificationEntityController) {
        return new NotificationsEndpointsController(notificationEntityController);
    }

    @Provides
    SearchEndpointsController searchEndpointsController(ExtendedUserEntityController extendedUserEntityController,
                                                        SettingsController settingsController,
                                                        Map<String, GenericController> controllers,
                                                        Gson gson,
                                                        SearchPage searchPage,
                                                        EmptySearchPage emptySearchPage,
                                                        NotificationEntityController notificationEntityController) {
        return new SearchEndpointsController(
            extendedUserEntityController,
            settingsController,
            controllers,
            gson,
            searchPage,
            emptySearchPage,
            notificationEntityController
        );
    }
}
