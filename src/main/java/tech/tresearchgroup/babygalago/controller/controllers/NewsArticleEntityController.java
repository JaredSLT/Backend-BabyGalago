package tech.tresearchgroup.babygalago.controller.controllers;

import com.google.gson.Gson;
import com.meilisearch.sdk.Client;
import com.zaxxer.hikari.HikariDataSource;
import io.activej.serializer.BinarySerializer;
import tech.tresearchgroup.palila.controller.GenericController;
import tech.tresearchgroup.palila.model.enums.PermissionGroupEnum;
import tech.tresearchgroup.schemas.galago.entities.NewsArticleEntity;

public class NewsArticleEntityController extends GenericController {
    public NewsArticleEntityController(HikariDataSource hikariDataSource,
                                       Gson gson,
                                       Client client,
                                       BinarySerializer<NewsArticleEntity> serializer,
                                       int reindexSize,
                                       Object sample,
                                       UserEntityController userEntityController) throws Exception {
        super(
            hikariDataSource,
            gson,
            client,
            NewsArticleEntity.class,
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
