package tech.tresearchgroup.babygalago.controller.controllers;

import com.google.gson.Gson;
import com.meilisearch.sdk.Client;
import com.zaxxer.hikari.HikariDataSource;
import io.activej.serializer.BinarySerializer;
import tech.tresearchgroup.palila.controller.GenericController;
import tech.tresearchgroup.palila.model.entities.GameFileEntity;
import tech.tresearchgroup.palila.model.enums.PermissionGroupEnum;

public class GameFileEntityController extends GenericController {
    public GameFileEntityController(HikariDataSource hikariDataSource,
                                    Gson gson,
                                    Client client,
                                    BinarySerializer<GameFileEntity> serializer,
                                    int reindexSize,
                                    Object sample,
                                    UserEntityController userEntityController) throws Exception {
        super(
            hikariDataSource,
            gson,
            client,
            GameFileEntity.class,
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
