package tech.tresearchgroup.babygalago.controller.controllers;

import com.google.gson.Gson;
import com.meilisearch.sdk.Client;
import com.zaxxer.hikari.HikariDataSource;
import io.activej.serializer.BinarySerializer;
import tech.tresearchgroup.palila.controller.GenericController;
import tech.tresearchgroup.palila.model.Card;
import tech.tresearchgroup.palila.model.enums.PermissionGroupEnum;
import tech.tresearchgroup.schemas.galago.entities.SubtitleEntity;

public class SubtitleEntityController extends GenericController {
    /**
     * Sets up the subtitle entity controller. To understand this class better, have a look at the class it extends (GenericController)
     */
    public SubtitleEntityController(HikariDataSource hikariDataSource,
                                    Gson gson,
                                    Client client,
                                    BinarySerializer<SubtitleEntity> serializer,
                                    int reindexSize,
                                    Object sample,
                                    ExtendedUserEntityController extendedUserEntityController) throws Exception {
        super(
            hikariDataSource,
            gson,
            client,
            SubtitleEntity.class,
            serializer,
            reindexSize,
            null,
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
