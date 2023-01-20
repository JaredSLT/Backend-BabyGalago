package tech.tresearchgroup.babygalago.controller.controllers;

import com.google.gson.Gson;
import com.meilisearch.sdk.Client;
import com.zaxxer.hikari.HikariDataSource;
import io.activej.serializer.BinarySerializer;
import tech.tresearchgroup.palila.controller.GenericController;
import tech.tresearchgroup.palila.model.Card;
import tech.tresearchgroup.palila.model.entities.BookFileEntity;
import tech.tresearchgroup.palila.model.enums.PermissionGroupEnum;

/**
 * Sets up the book file entity controller. To understand this class better, have a look at the class it extends (GenericController)
 */
public class BookFileEntityController extends GenericController {
    public BookFileEntityController(HikariDataSource hikariDataSource,
                                    Gson gson,
                                    Client client,
                                    BinarySerializer<BookFileEntity> serializer,
                                    int reindexSize,
                                    Object sample,
                                    ExtendedUserEntityController extendedUserEntityController) throws Exception {
        super(
            hikariDataSource,
            gson,
            client,
            BookFileEntity.class,
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
