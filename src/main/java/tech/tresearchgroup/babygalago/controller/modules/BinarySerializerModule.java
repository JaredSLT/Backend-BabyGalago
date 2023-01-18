package tech.tresearchgroup.babygalago.controller.modules;

import io.activej.inject.annotation.Provides;
import io.activej.inject.module.AbstractModule;
import io.activej.serializer.BinarySerializer;
import io.activej.serializer.SerializerBuilder;
import tech.tresearchgroup.palila.model.entities.FileEntity;
import tech.tresearchgroup.schemas.galago.entities.*;

public class BinarySerializerModule extends AbstractModule {
    @Provides
    BinarySerializer<AlbumEntity> albumEntityBinarySerializer() {
        return SerializerBuilder.create().build(AlbumEntity.class);
    }

    @Provides
    BinarySerializer<ArtistEntity> artistEntityBinarySerializer() {
        return SerializerBuilder.create().build(ArtistEntity.class);
    }

    @Provides
    BinarySerializer<BookEntity> bookEntityBinarySerializer() {
        return SerializerBuilder.create().build(BookEntity.class);
    }

    @Provides
    BinarySerializer<CharacterEntity> characterEntityBinarySerializer() {
        return SerializerBuilder.create().build(CharacterEntity.class);
    }

    @Provides
    BinarySerializer<CompanyEntity> companyEntityBinarySerializer() {
        return SerializerBuilder.create().build(CompanyEntity.class);
    }

    @Provides
    BinarySerializer<EpisodeEntity> episodeEntityBinarySerializer() {
        return SerializerBuilder.create().build(EpisodeEntity.class);
    }

    @Provides
    BinarySerializer<FileEntity> fileEntityBinarySerializer() {
        return SerializerBuilder.create().build(FileEntity.class);
    }

    @Provides
    BinarySerializer<GameEngineEntity> gameEngineEntityBinarySerializer() {
        return SerializerBuilder.create().build(GameEngineEntity.class);
    }

    @Provides
    BinarySerializer<GameEntity> gameEntityBinarySerializer() {
        return SerializerBuilder.create().build(GameEntity.class);
    }

    @Provides
    BinarySerializer<GamePlatformReleaseEntity> gamePlatformReleaseEntityBinarySerializer() {
        return SerializerBuilder.create().build(GamePlatformReleaseEntity.class);
    }

    @Provides
    BinarySerializer<GameSeriesEntity> gameSeriesEntityBinarySerializer() {
        return SerializerBuilder.create().build(GameSeriesEntity.class);
    }

    @Provides
    BinarySerializer<ImageEntity> imageEntityBinarySerializer() {
        return SerializerBuilder.create().build(ImageEntity.class);
    }

    @Provides
    BinarySerializer<LocationEntity> locationEntityBinarySerializer() {
        return SerializerBuilder.create().build(LocationEntity.class);
    }

    @Provides
    BinarySerializer<LyricsEntity> lyricsEntityBinarySerializer() {
        return SerializerBuilder.create().build(LyricsEntity.class);
    }

    @Provides
    BinarySerializer<MovieEntity> movieEntityBinarySerializer() {
        return SerializerBuilder.create().build(MovieEntity.class);
    }

    @Provides
    BinarySerializer<NewsArticleEntity> newsArticleEntityBinarySerializer() {
        return SerializerBuilder.create().build(NewsArticleEntity.class);
    }

    @Provides
    BinarySerializer<NotificationEntity> notificationEntityBinarySerializer() {
        return SerializerBuilder.create().build(NotificationEntity.class);
    }

    @Provides
    BinarySerializer<PersonEntity> personEntityBinarySerializer() {
        return SerializerBuilder.create().build(PersonEntity.class);
    }

    @Provides
    BinarySerializer<QueueEntity> queueEntityBinarySerializer() {
        return SerializerBuilder.create().build(QueueEntity.class);
    }

    @Provides
    BinarySerializer<RatingEntity> ratingEntityBinarySerializer() {
        return SerializerBuilder.create().build(RatingEntity.class);
    }

    @Provides
    BinarySerializer<SeasonEntity> seasonEntityBinarySerializer() {
        return SerializerBuilder.create().build(SeasonEntity.class);
    }

    @Provides
    BinarySerializer<SongEntity> songEntityBinarySerializer() {
        return SerializerBuilder.create().build(SongEntity.class);
    }

    @Provides
    BinarySerializer<SubtitleEntity> subtitleEntityBinarySerializer() {
        return SerializerBuilder.create().build(SubtitleEntity.class);
    }

    @Provides
    BinarySerializer<TvShowEntity> tvShowEntityBinarySerializer() {
        return SerializerBuilder.create().build(TvShowEntity.class);
    }

    @Provides
    BinarySerializer<ExtendedUserEntity> extendedUserEntityBinarySerializer() {
        return SerializerBuilder.create().build(ExtendedUserEntity.class);
    }

    @Provides
    BinarySerializer<UserSettingsEntity> userSettingsEntityBinarySerializer() {
        return SerializerBuilder.create().build(UserSettingsEntity.class);
    }
}
