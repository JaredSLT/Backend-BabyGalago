package tech.tresearchgroup.babygalago.controller.controllers;

import com.google.gson.Gson;
import com.meilisearch.sdk.Client;
import com.zaxxer.hikari.HikariDataSource;
import io.activej.http.HttpRequest;
import io.activej.http.HttpResponse;
import io.activej.serializer.BinarySerializer;
import tech.tresearchgroup.babygalago.view.pages.UserSettingsPage;
import tech.tresearchgroup.palila.controller.GenericController;
import tech.tresearchgroup.palila.model.enums.PermissionGroupEnum;
import tech.tresearchgroup.schemas.galago.entities.ExtendedUserEntity;
import tech.tresearchgroup.schemas.galago.entities.UserSettingsEntity;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public class UserSettingsEntityController extends GenericController {
    private final UserEntityController userEntityController;

    public UserSettingsEntityController(HikariDataSource hikariDataSource,
                                        Gson gson,
                                        Client client,
                                        BinarySerializer<UserSettingsEntity> serializer,
                                        int reindexSize,
                                        Object object,
                                        UserEntityController userEntityController) throws Exception {
        super(
            hikariDataSource,
            gson,
            client,
            UserSettingsEntity.class,
            serializer,
            reindexSize,
            null,
            object,
            PermissionGroupEnum.USER,
            PermissionGroupEnum.USER,
            PermissionGroupEnum.USER,
            PermissionGroupEnum.USER,
            PermissionGroupEnum.USER,
            userEntityController
        );
        this.userEntityController = userEntityController;
    }

    public HttpResponse read(UserSettingsPage userSettingsPage, HttpRequest httpRequest) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException {
        ExtendedUserEntity user = (ExtendedUserEntity) getUser(httpRequest, userEntityController);
        UserSettingsEntity userSettingsEntity = user.getUserSettings();
        if (userSettingsEntity == null) {
            userSettingsEntity = new UserSettingsEntity();
        }
        return ok(userSettingsPage.render(true, userSettingsEntity, user));
    }

    public HttpResponse create(UserSettingsEntity userSettingsEntity, HttpRequest httpRequest) {
        //Todo create settings for the specified user and display form
        return null;
    }
}
