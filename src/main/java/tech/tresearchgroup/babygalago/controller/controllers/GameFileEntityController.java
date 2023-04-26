package tech.tresearchgroup.babygalago.controller.controllers;

import com.google.gson.Gson;
import com.meilisearch.sdk.Client;
import com.zaxxer.hikari.HikariDataSource;
import io.activej.serializer.BinarySerializer;
import tech.tresearchgroup.palila.controller.GenericController;
import tech.tresearchgroup.palila.model.Card;
import tech.tresearchgroup.palila.model.entities.GameFileEntity;
import tech.tresearchgroup.palila.model.enums.PermissionGroupEnum;

public class GameFileEntityController extends GenericController {
    /**
     * Sets up the game file entity controller. To understand this class better, have a look at the class it extends (GenericController)
     */
    public GameFileEntityController(HikariDataSource hikariDataSource,
                                    Gson gson,
                                    Client client,
                                    BinarySerializer<GameFileEntity> serializer,
                                    int reindexSize,
                                    Object sample,
                                    ExtendedUserEntityController extendedUserEntityController) throws Exception {
        super(
            hikariDataSource,
            gson,
            client,
            GameFileEntity.class,
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
