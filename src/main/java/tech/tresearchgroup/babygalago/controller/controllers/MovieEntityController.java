package tech.tresearchgroup.babygalago.controller.controllers;

import com.google.gson.Gson;
import com.meilisearch.sdk.Client;
import com.zaxxer.hikari.HikariDataSource;
import io.activej.http.HttpRequest;
import io.activej.serializer.BinarySerializer;
import tech.tresearchgroup.palila.controller.GenericController;
import tech.tresearchgroup.palila.model.Card;
import tech.tresearchgroup.palila.model.entities.VideoFileEntity;
import tech.tresearchgroup.palila.model.enums.PermissionGroupEnum;
import tech.tresearchgroup.palila.model.enums.PlaybackQualityEnum;
import tech.tresearchgroup.palila.model.enums.ReturnType;
import tech.tresearchgroup.schemas.galago.entities.MovieEntity;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public class MovieEntityController extends GenericController {
    /**
     * Sets up the movie entity controller. To understand this class better, have a look at the class it extends (GenericController)
     */
    public MovieEntityController(HikariDataSource hikariDataSource,
                                 Gson gson,
                                 Client client,
                                 BinarySerializer<MovieEntity> serializer,
                                 int reindexSize,
                                 Object sample,
                                 ExtendedUserEntityController extendedUserEntityController) throws Exception {
        super(
            hikariDataSource,
            gson,
            client,
            MovieEntity.class,
            serializer,
            reindexSize,
            "title",
            sample,
            PermissionGroupEnum.USER,
            PermissionGroupEnum.USER,
            PermissionGroupEnum.USER,
            PermissionGroupEnum.USER,
            PermissionGroupEnum.USER,
            extendedUserEntityController,
            new Card(
                null,
                "title",
                "releaseDate",
                "runtime",
                "mpaaRating",
                "userRating"
            )
        );
    }

    /**
     * Gets the video file entity for this movie
     * @param movieEntity the movie to lookup
     * @param videoFileEntityController the video file controller
     * @param playbackQualityEnum the quality to select
     * @param httpRequest the request
     * @return the video file
     * @throws SQLException  if there is a failure with the database
     * @throws InvocationTargetException if there is a failure with the ORM
     * @throws NoSuchMethodException if there is a failure with the ORM
     * @throws InstantiationException if there is a failure with the ORM
     * @throws IllegalAccessException if there is a failure with the ORM
     * @throws IOException if there is a failure with the file
     */
    public VideoFileEntity getVideo(MovieEntity movieEntity, VideoFileEntityController videoFileEntityController, PlaybackQualityEnum playbackQualityEnum, HttpRequest httpRequest) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException {
        if (movieEntity.getFiles() != null) {
            for (VideoFileEntity video : movieEntity.getFiles()) {
                VideoFileEntity videoFileEntity = (VideoFileEntity) videoFileEntityController.readSecureResponse(video.getId(), ReturnType.OBJECT, httpRequest);
                if (videoFileEntity.getPlaybackQualityEnum().equals(playbackQualityEnum)) {
                    return video;
                }
            }
        }
        return null;
    }
}
