package tech.tresearchgroup.babygalago.controller.controllers;

import com.google.gson.Gson;
import com.meilisearch.sdk.Client;
import com.zaxxer.hikari.HikariDataSource;
import io.activej.serializer.BinarySerializer;
import tech.tresearchgroup.palila.controller.GenericController;
import tech.tresearchgroup.palila.model.Card;
import tech.tresearchgroup.palila.model.entities.ImageFileEntity;
import tech.tresearchgroup.palila.model.enums.PermissionGroupEnum;

public class ImageFileEntityController extends GenericController {
    /**
     * Sets up the image file entity controller. To understand this class better, have a look at the class it extends (GenericController)
     */
    public ImageFileEntityController(HikariDataSource hikariDataSource,
                                     Gson gson,
                                     Client client,
                                     BinarySerializer<ImageFileEntity> serializer,
                                     int reindexSize,
                                     Object sample,
                                     ExtendedUserEntityController extendedUserEntityController) throws Exception {
        super(
            hikariDataSource,
            gson,
            client,
            ImageFileEntity.class,
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
