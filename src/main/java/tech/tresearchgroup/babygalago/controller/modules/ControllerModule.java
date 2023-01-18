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
import tech.tresearchgroup.babygalago.controller.endpoints.api.*;
import tech.tresearchgroup.babygalago.controller.endpoints.ui.CRUDEndpointsController;
import tech.tresearchgroup.babygalago.controller.endpoints.ui.MainEndpointsController;
import tech.tresearchgroup.babygalago.controller.endpoints.ui.PlayEndpointsController;
import tech.tresearchgroup.babygalago.view.pages.*;
import tech.tresearchgroup.palila.controller.GenericController;
import tech.tresearchgroup.palila.model.entities.*;
import tech.tresearchgroup.schemas.galago.entities.*;

import java.util.HashMap;
import java.util.Map;

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
                                                UserEntityController userEntityController,
                                                SettingsController settingsController,
                                                NotificationEntityController notificationEntityController,
                                                ViewPage viewPage) {
        return new CRUDEndpointsController(
            controllers,
            userEntityController,
            settingsController,
            notificationEntityController,
            viewPage
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
                                                    UserEntityController userEntityController,
                                                    SettingsController settingsController,
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
                                                    DisabledPage disabledPage) {
        return new MainEndpointsController(
            movieEntityController,
            tvShowEntityController,
            gameEntityController,
            songEntityController,
            bookEntityController,
            notificationEntityController,
            newsArticleEntityController,
            queueEntityController,
            userEntityController,
            settingsController,
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
            searchPage,
            settingsPage,
            uploadPage,
            deniedPage,
            disabledPage);
    }

    @Provides
    PlayEndpointsController playEndpointsController(Map<String, GenericController> controllers,
                                                    UserEntityController userEntityController,
                                                    SettingsController settingsController,
                                                    VideoFileEntityController videoFileEntityController,
                                                    UserSettingsEntityController userSettingsEntityController,
                                                    PlayPage playPage) {
        return new PlayEndpointsController(
            controllers,
            userEntityController,
            settingsController,
            videoFileEntityController,
            userSettingsEntityController,
            playPage
        );
    }

    @Provides
    UserEntityController userController(HikariDataSource hikariDataSource,
                                        Gson gson,
                                        Client client) throws Exception {
        return new UserEntityController(
            hikariDataSource,
            gson,
            client,
            SerializerBuilder.create().build(ExtendedUserEntity.class),
            20,
            new ExtendedUserEntity()
        );
    }

    @Provides
    TaskEndpointsController taskEndpointsController(TaskController scheduleController,
                                                    UserEntityController userEntityController) {
        return new TaskEndpointsController(
            scheduleController,
            userEntityController
        );
    }

    @Provides
    SettingsEndpointsController settingsEndpointsController(UserEntityController userEntityController,
                                                            UserSettingsEntityController userSettingsEntityController) {
        return new SettingsEndpointsController(
            userEntityController,
            userSettingsEntityController
        );
    }

    @Provides
    QueueEndpointsController queueEndpointsController(QueueEntityController queueEntityController,
                                                      UserEntityController userEntityController) {
        return new QueueEndpointsController(
            queueEntityController,
            userEntityController
        );
    }

    @Provides
    MovieEntityController movieController(HikariDataSource hikariDataSource,
                                          Gson gson,
                                          Client client,
                                          UserEntityController userEntityController) throws Exception {
        return new MovieEntityController(
            hikariDataSource,
            gson,
            client,
            SerializerBuilder.create().build(MovieEntity.class),
            20,
            new MovieEntity(),
            userEntityController
        );
    }

    @Provides
    SubtitleEntityController subtitleController(HikariDataSource hikariDataSource,
                                                Gson gson,
                                                Client client,
                                                UserEntityController userEntityController) throws Exception {
        return new SubtitleEntityController(
            hikariDataSource,
            gson,
            client,
            SerializerBuilder.create().build(SubtitleEntity.class),
            20,
            new SubtitleEntity(),
            userEntityController
        );
    }

    @Provides
    GamePlatformReleaseEntityController gamePlatformReleaseController(HikariDataSource hikariDataSource,
                                                                      Gson gson,
                                                                      Client client,
                                                                      UserEntityController userEntityController) throws Exception {
        return new GamePlatformReleaseEntityController(
            hikariDataSource,
            gson,
            client,
            SerializerBuilder.create().build(GamePlatformReleaseEntity.class),
            20,
            new GamePlatformReleaseEntity(),
            userEntityController
        );
    }

    @Provides
    SongEntityController songController(HikariDataSource hikariDataSource,
                                        Gson gson,
                                        Client client,
                                        UserEntityController userEntityController) throws Exception {
        return new SongEntityController(
            hikariDataSource,
            gson,
            client,
            SerializerBuilder.create().build(SongEntity.class),
            20,
            new SongEntity(),
            userEntityController
        );
    }

    @Provides
    RatingEntityController ratingController(HikariDataSource hikariDataSource,
                                            Gson gson,
                                            Client client,
                                            UserEntityController userEntityController) throws Exception {
        return new RatingEntityController(
            hikariDataSource,
            gson,
            client,
            SerializerBuilder.create().build(RatingEntity.class),
            20,
            new RatingEntity(),
            userEntityController
        );
    }

    @Provides
    BookEntityController bookController(HikariDataSource hikariDataSource,
                                        Gson gson,
                                        Client client,
                                        UserEntityController userEntityController) throws Exception {
        return new BookEntityController(
            hikariDataSource,
            gson,
            client,
            SerializerBuilder.create().build(BookEntity.class),
            20,
            new BookEntity(),
            userEntityController
        );
    }

    @Provides
    GameSeriesEntityController gameSeriesController(HikariDataSource hikariDataSource,
                                                    Gson gson,
                                                    Client client,
                                                    UserEntityController userEntityController) throws Exception {
        return new GameSeriesEntityController(
            hikariDataSource,
            gson,
            client,
            SerializerBuilder.create().build(GameSeriesEntity.class),
            20,
            new GameSeriesEntity(),
            userEntityController
        );
    }

    @Provides
    GameEngineEntityController gameEngineController(HikariDataSource hikariDataSource,
                                                    Gson gson,
                                                    Client client,
                                                    UserEntityController userEntityController) throws Exception {
        return new GameEngineEntityController(
            hikariDataSource,
            gson,
            client,
            SerializerBuilder.create().build(GameEngineEntity.class),
            20,
            new GameEngineEntity(),
            userEntityController
        );
    }

    @Provides
    AlbumEntityController albumController(HikariDataSource hikariDataSource,
                                          Gson gson,
                                          Client client,
                                          UserEntityController userEntityController) throws Exception {
        return new AlbumEntityController(
            hikariDataSource,
            gson,
            client,
            SerializerBuilder.create().build(AlbumEntity.class),
            20,
            new AlbumEntity(),
            userEntityController
        );
    }

    @Provides
    TvShowEntityController tvShowController(HikariDataSource hikariDataSource,
                                            Gson gson,
                                            Client client,
                                            UserEntityController userEntityController) throws Exception {
        return new TvShowEntityController(
            hikariDataSource,
            gson,
            client,
            SerializerBuilder.create().build(TvShowEntity.class),
            20,
            new TvShowEntity(),
            userEntityController
        );
    }

    @Provides
    PersonEntityController personController(HikariDataSource hikariDataSource,
                                            Gson gson,
                                            Client client,
                                            UserEntityController userEntityController) throws Exception {
        return new PersonEntityController(
            hikariDataSource,
            gson,
            client,
            SerializerBuilder.create().build(PersonEntity.class),
            20,
            new PersonEntity(),
            userEntityController
        );
    }

    @Provides
    SeasonEntityController seasonController(HikariDataSource hikariDataSource,
                                            Gson gson,
                                            Client client,
                                            UserEntityController userEntityController) throws Exception {
        return new SeasonEntityController(
            hikariDataSource,
            gson,
            client,
            SerializerBuilder.create().build(SeasonEntity.class),
            20,
            new SeasonEntity(),
            userEntityController
        );
    }

    @Provides
    GameEntityController gameController(HikariDataSource hikariDataSource,
                                        Gson gson,
                                        Client client,
                                        UserEntityController userEntityController) throws Exception {
        return new GameEntityController(
            hikariDataSource,
            gson,
            client,
            SerializerBuilder.create().build(GameEntity.class),
            20,
            new GameEntity(),
            userEntityController
        );
    }

    @Provides
    ArtistEntityController artistController(HikariDataSource hikariDataSource,
                                            Gson gson,
                                            Client client,
                                            UserEntityController userEntityController) throws Exception {
        return new ArtistEntityController(
            hikariDataSource,
            gson,
            client,
            SerializerBuilder.create().build(ArtistEntity.class),
            20,
            new ArtistEntity(),
            userEntityController
        );
    }

    @Provides
    ImageEntityController imageController(HikariDataSource hikariDataSource,
                                          Gson gson,
                                          Client client,
                                          UserEntityController userEntityController) throws Exception {
        return new ImageEntityController(
            hikariDataSource,
            gson,
            client,
            SerializerBuilder.create().build(ImageEntity.class),
            20,
            new ImageEntity(),
            userEntityController
        );
    }

    @Provides
    NotificationEntityController notificationController(HikariDataSource hikariDataSource,
                                                        Gson gson,
                                                        Client client,
                                                        UserEntityController userEntityController) throws Exception {
        return new NotificationEntityController(
            hikariDataSource,
            gson,
            client,
            SerializerBuilder.create().build(NotificationEntity.class),
            20,
            new NotificationEntity(),
            userEntityController
        );
    }

    @Provides
    LyricsEntityController lyricsController(HikariDataSource hikariDataSource,
                                            Gson gson,
                                            Client client,
                                            UserEntityController userEntityController) throws Exception {
        return new LyricsEntityController(
            hikariDataSource,
            gson,
            client,
            SerializerBuilder.create().build(LyricsEntity.class),
            20,
            new LyricsEntity(),
            userEntityController
        );
    }

    @Provides
    CompanyEntityController companyController(HikariDataSource hikariDataSource,
                                              Gson gson,
                                              Client client,
                                              UserEntityController userEntityController) throws Exception {
        return new CompanyEntityController(
            hikariDataSource,
            gson,
            client,
            SerializerBuilder.create().build(CompanyEntity.class),
            20,
            new CompanyEntity(),
            userEntityController
        );
    }

    @Provides
    LoginEndpointsController loginEndpointsController(UserEntityController userEntityController,
                                                      Gson gson) {
        return new LoginEndpointsController(
            userEntityController,
            gson
        );
    }

    @Provides
    NewsArticleEntityController newsArticleController(HikariDataSource hikariDataSource,
                                                      Gson gson,
                                                      Client client,
                                                      UserEntityController userEntityController) throws Exception {
        return new NewsArticleEntityController(
            hikariDataSource,
            gson,
            client,
            SerializerBuilder.create().build(NewsArticleEntity.class),
            20,
            new NewsArticleEntity(),
            userEntityController
        );
    }

    @Provides
    LocationEntityController locationController(HikariDataSource hikariDataSource,
                                                Gson gson,
                                                Client client,
                                                UserEntityController userEntityController) throws Exception {
        return new LocationEntityController(
            hikariDataSource,
            gson,
            client,
            SerializerBuilder.create().build(LocationEntity.class),
            20,
            new LocationEntity(),
            userEntityController
        );
    }

    @Provides
    CharacterEntityController characterController(HikariDataSource hikariDataSource,
                                                  Gson gson,
                                                  Client client,
                                                  UserEntityController userEntityController) throws Exception {
        return new CharacterEntityController(
            hikariDataSource,
            gson,
            client,
            SerializerBuilder.create().build(CharacterEntity.class),
            20,
            new CharacterEntity(),
            userEntityController
        );
    }

    @Provides
    GeneralEndpointsController generalEndpointsController(ImageEntityController imageEntityController,
                                                          FileEntityController fileEntityController,
                                                          UserEntityController userEntityController,
                                                          SettingsController settingsController,
                                                          Map<String, GenericController> controllers,
                                                          Gson gson) {
        return new GeneralEndpointsController(
            userEntityController,
            settingsController,
            controllers,
            gson
        );
    }

    @Provides
    QueueEntityController queueController(HikariDataSource hikariDataSource,
                                          Gson gson,
                                          Client client,
                                          SettingsController settingsController,
                                          UserEntityController userEntityController) throws Exception {
        return new QueueEntityController(
            hikariDataSource,
            gson,
            client,
            SerializerBuilder.create().build(QueueEntity.class),
            20,
            settingsController,
            userEntityController
        );
    }

    @Provides
    EpisodeEntityController episodeController(HikariDataSource hikariDataSource,
                                              Gson gson,
                                              Client client,
                                              UserEntityController userEntityController) throws Exception {
        return new EpisodeEntityController(
            hikariDataSource,
            gson,
            client,
            SerializerBuilder.create().build(EpisodeEntity.class),
            20,
            new EpisodeEntity(),
            userEntityController
        );
    }

    @Provides
    FileEntityController fileController(HikariDataSource hikariDataSource,
                                        Gson gson,
                                        Client client,
                                        UserEntityController userEntityController) throws Exception {
        return new FileEntityController(
            hikariDataSource,
            gson,
            client,
            SerializerBuilder.create().build(FileEntity.class),
            20,
            new FileEntity(),
            userEntityController
        );
    }

    @Provides
    AudioFileEntityController audioFileEntityController(HikariDataSource hikariDataSource,
                                                        Gson gson,
                                                        Client client,
                                                        UserEntityController userEntityController) throws Exception {
        return new AudioFileEntityController(
            hikariDataSource,
            gson,
            client,
            SerializerBuilder.create().build(AudioFileEntity.class),
            20,
            new AudioFileEntity(),
            userEntityController
        );
    }

    @Provides
    BookFileEntityController bookFileEntityController(HikariDataSource hikariDataSource,
                                                      Gson gson,
                                                      Client client,
                                                      UserEntityController userEntityController) throws Exception {
        return new BookFileEntityController(
            hikariDataSource,
            gson,
            client,
            SerializerBuilder.create().build(BookFileEntity.class),
            20,
            new BookFileEntity(),
            userEntityController
        );
    }

    @Provides
    GameFileEntityController gameFileEntityController(HikariDataSource hikariDataSource,
                                                      Gson gson,
                                                      Client client,
                                                      UserEntityController userEntityController) throws Exception {
        return new GameFileEntityController(
            hikariDataSource,
            gson,
            client,
            SerializerBuilder.create().build(GameFileEntity.class),
            20,
            new GameFileEntity(),
            userEntityController
        );
    }

    @Provides
    ImageFileEntityController imageFileEntityController(HikariDataSource hikariDataSource,
                                                        Gson gson,
                                                        Client client,
                                                        UserEntityController userEntityController) throws Exception {
        return new ImageFileEntityController(
            hikariDataSource,
            gson,
            client,
            SerializerBuilder.create().build(ImageFileEntity.class),
            20,
            new ImageFileEntity(),
            userEntityController
        );
    }

    @Provides
    VideoFileEntityController videoFileEntityController(SettingsController settingsController,
                                                        HikariDataSource hikariDataSource,
                                                        Gson gson,
                                                        Client client,
                                                        UserEntityController userEntityController) throws Exception {
        return new VideoFileEntityController(
            hikariDataSource,
            gson,
            client,
            SerializerBuilder.create().build(VideoFileEntity.class),
            20,
            new VideoFileEntity(),
            userEntityController
        );
    }

    @Provides
    UserSettingsEntityController userSettingsController(HikariDataSource hikariDataSource,
                                                        Gson gson,
                                                        Client client,
                                                        UserEntityController userEntityController) throws Exception {
        return new UserSettingsEntityController(
            hikariDataSource,
            gson,
            client,
            SerializerBuilder.create().build(UserSettingsEntity.class),
            20,
            new UserSettingsEntity(),
            userEntityController);
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
                                               UserSettingsEntityController userSettingsEntityController) {
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
                                                              SettingsController settingsController) {
        return new MediaTypeEndpointsController(
            controllers,
            settingsController
        );
    }

    @Provides
    UserEndpointsController userEndpointsController(UserEntityController userEntityController,
                                                    SettingsController settingsController) {
        return new UserEndpointsController(userEntityController, settingsController);
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
}
