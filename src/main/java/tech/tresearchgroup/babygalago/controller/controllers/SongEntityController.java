package tech.tresearchgroup.babygalago.controller.controllers;

import com.google.gson.Gson;
import com.meilisearch.sdk.Client;
import com.zaxxer.hikari.HikariDataSource;
import io.activej.serializer.BinarySerializer;
import tech.tresearchgroup.palila.controller.GenericController;
import tech.tresearchgroup.palila.model.enums.PermissionGroupEnum;
import tech.tresearchgroup.schemas.galago.entities.SongEntity;

public class SongEntityController extends GenericController {
    public SongEntityController(HikariDataSource hikariDataSource,
                                Gson gson,
                                Client client,
                                BinarySerializer<SongEntity> serializer,
                                int reindexSize,
                                Object sample,
                                UserEntityController userEntityController) throws Exception {
        super(
            hikariDataSource,
            gson,
            client,
            SongEntity.class,
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
