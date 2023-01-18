package tech.tresearchgroup.babygalago.controller.controllers;

import com.google.gson.Gson;
import com.meilisearch.sdk.Client;
import com.zaxxer.hikari.HikariDataSource;
import io.activej.serializer.BinarySerializer;
import tech.tresearchgroup.palila.controller.GenericController;
import tech.tresearchgroup.palila.model.entities.VideoFileEntity;
import tech.tresearchgroup.palila.model.enums.PermissionGroupEnum;

public class VideoFileEntityController extends GenericController {
    public VideoFileEntityController(HikariDataSource hikariDataSource,
                                     Gson gson,
                                     Client client,
                                     BinarySerializer<VideoFileEntity> serializer,
                                     int reindexSize,
                                     Object sample,
                                     UserEntityController userEntityController) throws Exception {
        super(
            hikariDataSource,
            gson,
            client,
            VideoFileEntity.class,
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
}
