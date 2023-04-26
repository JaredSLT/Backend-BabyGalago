package tech.tresearchgroup.babygalago.controller.controllers;

import com.google.gson.Gson;
import com.meilisearch.sdk.Client;
import com.zaxxer.hikari.HikariDataSource;
import io.activej.http.HttpRequest;
import io.activej.http.HttpResponse;
import io.activej.promise.Promisable;
import io.activej.serializer.BinarySerializer;
import tech.tresearchgroup.palila.controller.BasicUserController;
import tech.tresearchgroup.palila.model.Card;
import tech.tresearchgroup.palila.model.enums.PermissionGroupEnum;
import tech.tresearchgroup.palila.model.enums.ReturnType;
import tech.tresearchgroup.schemas.galago.entities.ExtendedUserEntity;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExtendedUserEntityController extends BasicUserController {
    private final HikariDataSource hikariDataSource;

    /**
     * Sets up the extended user entity controller. To understand this class better, have a look at the class it extends (GenericController)
     */
    public ExtendedUserEntityController(HikariDataSource hikariDataSource,
                                        Gson gson,
                                        Client client,
                                        BinarySerializer<ExtendedUserEntity> serializer,
                                        int reindexSize,
                                        Object sample) throws Exception {
        super(
            hikariDataSource,
            gson,
            client,
            ExtendedUserEntity.class,
            reindexSize,
            serializer,
            null,
            sample,
            PermissionGroupEnum.OPERATOR,
            PermissionGroupEnum.USER,
            PermissionGroupEnum.USER,
            PermissionGroupEnum.USER,
            PermissionGroupEnum.OPERATOR,
            new Card()
        );
        this.hikariDataSource = hikariDataSource;
    }

    /**
     * Looks up a user entity by username and password
     *
     * @param username the username
     * @param password the password
     * @return the extended user entity
     * @throws SQLException              if there is a failure with the database
     * @throws InvocationTargetException if there is a failure with the ORM
     * @throws IllegalAccessException    if there is a failure with the ORM
     * @throws InstantiationException    if there is a failure with the ORM
     */
    public ExtendedUserEntity getUserByUsernameAndPassword(String username, String password) throws SQLException, InvocationTargetException, IllegalAccessException, InstantiationException {
        Connection connection = hikariDataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + theClass.getSimpleName().toLowerCase() + " WHERE username=? and password=?");
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getResultSet();
        connection.close();
        if (resultSet.next()) {
            ExtendedUserEntity extendedUserEntity = new ExtendedUserEntity();
            Object object = genericDAO.getFromResultSet(resultSet, extendedUserEntity);
            return (ExtendedUserEntity) object;
        }
        return null;
    }

    /**
     * Gets a user by their username
     *
     * @param username the username
     * @return the extended user entity
     * @throws SQLException              if there is an issue with the database
     * @throws InvocationTargetException if there is a failure with the ORM
     * @throws IllegalAccessException    if there is a failure with the ORM
     * @throws InstantiationException    if there is a failure with the ORM
     */
    public ExtendedUserEntity getUserByUsername(String username) throws SQLException, InvocationTargetException, IllegalAccessException, InstantiationException {
        Connection connection = hikariDataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + theClass.getSimpleName().toLowerCase() + " WHERE username=?");
        preparedStatement.setString(1, username);
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getResultSet();
        connection.close();
        if (resultSet.next()) {
            return (ExtendedUserEntity) genericDAO.getFromResultSet(resultSet, new ExtendedUserEntity());
        }
        return null;
    }

    /**
     * Responds to a UI request to create an entity
     *
     * @param extendedUserEntity the user to create
     * @param httpRequest        the request to create
     * @return the response page
     * @throws SQLException              if there is a failure with the database
     * @throws InvocationTargetException if there is a failure with the ORM
     * @throws NoSuchMethodException     if there is a failure with the ORM
     * @throws IllegalAccessException    if there is a failure with the ORM
     * @throws InstantiationException    if there is a failure with the ORM
     * @throws IOException               if there is a failure with the ORM
     */
    public Promisable<HttpResponse> createUIResponse(ExtendedUserEntity extendedUserEntity, HttpRequest httpRequest) throws SQLException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException, IOException {
        if (createSecureResponse(extendedUserEntity, ReturnType.OBJECT, httpRequest) != null) {
            return ok();
        }
        return error();
    }
}
