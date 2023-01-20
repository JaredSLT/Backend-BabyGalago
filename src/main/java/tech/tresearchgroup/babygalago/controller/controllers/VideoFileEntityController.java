package tech.tresearchgroup.babygalago.controller.controllers;

import com.google.gson.Gson;
import com.meilisearch.sdk.Client;
import com.zaxxer.hikari.HikariDataSource;
import io.activej.serializer.BinarySerializer;
import tech.tresearchgroup.palila.controller.GenericController;
import tech.tresearchgroup.palila.model.Card;
import tech.tresearchgroup.palila.model.entities.VideoFileEntity;
import tech.tresearchgroup.palila.model.enums.PermissionGroupEnum;

public class VideoFileEntityController extends GenericController {
    /**
     * Sets up the video file entity controller. To understand this class better, have a look at the class it extends (GenericController)
     */
    public VideoFileEntityController(HikariDataSource hikariDataSource,
                                     Gson gson,
                                     Client client,
                                     BinarySerializer<VideoFileEntity> serializer,
                                     int reindexSize,
                                     Object sample,
                                     ExtendedUserEntityController extendedUserEntityController) throws Exception {
        super(
            hikariDataSource,
            gson,
            client,
            VideoFileEntity.class,
            serializer,
            reindexSize,
            "path",
            sample,
            PermissionGroupEnum.USER,
            PermissionGroupEnum.USER,
            PermissionGroupEnum.USER,
            PermissionGroupEnum.USER,
            PermissionGroupEnum.USER,
            extendedUserEntityController,
            new Card()
        );
    }
}
