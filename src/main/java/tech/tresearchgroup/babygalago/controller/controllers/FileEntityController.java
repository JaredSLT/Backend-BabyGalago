package tech.tresearchgroup.babygalago.controller.controllers;

import com.google.gson.Gson;
import com.meilisearch.sdk.Client;
import com.zaxxer.hikari.HikariDataSource;
import io.activej.serializer.BinarySerializer;
import tech.tresearchgroup.palila.controller.GenericController;
import tech.tresearchgroup.palila.model.entities.FileEntity;
import tech.tresearchgroup.palila.model.enums.PermissionGroupEnum;

public class FileEntityController extends GenericController {
    public FileEntityController(HikariDataSource hikariDataSource,
                                Gson gson,
                                Client client,
                                BinarySerializer<FileEntity> serializer,
                                int reindexSize,
                                Object sample,
                                UserEntityController userEntityController) throws Exception {
        super(
            hikariDataSource,
            gson,
            client,
            FileEntity.class,
            serializer,
            reindexSize,
            null,
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
