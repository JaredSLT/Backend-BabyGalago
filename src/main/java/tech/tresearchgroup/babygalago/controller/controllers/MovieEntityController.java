package tech.tresearchgroup.babygalago.controller.controllers;

import com.google.gson.Gson;
import com.meilisearch.sdk.Client;
import com.zaxxer.hikari.HikariDataSource;
import io.activej.http.HttpRequest;
import io.activej.serializer.BinarySerializer;
import tech.tresearchgroup.palila.controller.GenericController;
import tech.tresearchgroup.palila.model.entities.VideoFileEntity;
import tech.tresearchgroup.palila.model.enums.PermissionGroupEnum;
import tech.tresearchgroup.palila.model.enums.PlaybackQualityEnum;
import tech.tresearchgroup.schemas.galago.entities.MovieEntity;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public class MovieEntityController extends GenericController {
    public MovieEntityController(HikariDataSource hikariDataSource,
                                 Gson gson,
                                 Client client,
                                 BinarySerializer<MovieEntity> serializer,
                                 int reindexSize,
                                 Object sample,
                                 UserEntityController userEntityController) throws Exception {
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
            userEntityController
        );
    }

    public VideoFileEntity getVideo(MovieEntity movieEntity, VideoFileEntityController videoFileEntityController, PlaybackQualityEnum playbackQualityEnum, HttpRequest httpRequest) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (movieEntity.getFiles() != null) {
            for (VideoFileEntity video : movieEntity.getFiles()) {
                VideoFileEntity videoFileEntity = (VideoFileEntity) videoFileEntityController.readSecureResponse(video.getId(), httpRequest);
                if (videoFileEntity.getPlaybackQualityEnum().equals(playbackQualityEnum)) {
                    return video;
                }
            }
        }
        return null;
    }
}
