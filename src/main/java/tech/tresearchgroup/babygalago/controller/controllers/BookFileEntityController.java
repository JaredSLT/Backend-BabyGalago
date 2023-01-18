package tech.tresearchgroup.babygalago.controller.controllers;

import com.google.gson.Gson;
import com.meilisearch.sdk.Client;
import com.zaxxer.hikari.HikariDataSource;
import io.activej.serializer.BinarySerializer;
import tech.tresearchgroup.palila.controller.GenericController;
import tech.tresearchgroup.palila.model.entities.BookFileEntity;
import tech.tresearchgroup.palila.model.enums.PermissionGroupEnum;

public class BookFileEntityController extends GenericController {
    public BookFileEntityController(HikariDataSource hikariDataSource,
                                    Gson gson,
                                    Client client,
                                    BinarySerializer<BookFileEntity> serializer,
                                    int reindexSize,
                                    Object sample,
                                    UserEntityController userEntityController) throws Exception {
        super(
            hikariDataSource,
            gson,
            client,
            BookFileEntity.class,
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
