package tech.tresearchgroup.babygalago.controller.controllers;

import com.google.gson.Gson;
import com.meilisearch.sdk.Client;
import com.zaxxer.hikari.HikariDataSource;
import io.activej.serializer.BinarySerializer;
import tech.tresearchgroup.palila.controller.GenericController;
import tech.tresearchgroup.palila.model.enums.PermissionGroupEnum;
import tech.tresearchgroup.schemas.galago.entities.ExtendedUserEntity;
import tech.tresearchgroup.schemas.galago.entities.NotificationEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NotificationEntityController extends GenericController {
    public NotificationEntityController(HikariDataSource hikariDataSource,
                                        Gson gson,
                                        Client client,
                                        BinarySerializer<NotificationEntity> serializer,
                                        int reindexSize,
                                        Object sample,
                                        UserEntityController userEntityController) throws Exception {
        super(
            hikariDataSource,
            gson,
            client,
            NotificationEntity.class,
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

    public Long getNumberOfUnread(ExtendedUserEntity userEntity) throws SQLException {
        if (userEntity == null) {
            return null;
        }
        Connection connection = hikariDataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) AS COUNT FROM " + NotificationEntity.class.getSimpleName().toLowerCase() + " WHERE userEntity=? AND unread=?");
        preparedStatement.setLong(1, userEntity.getId());
        preparedStatement.setBoolean(2, true);
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getResultSet();
        connection.close();
        if (resultSet.next()) {
            return resultSet.getLong("COUNT");
        }
        return null;
    }
}
