package tech.tresearchgroup.babygalago.controller.endpoints;

import com.google.gson.Gson;
import io.activej.bytebuf.ByteBuf;
import io.activej.http.HttpRequest;
import io.activej.http.HttpResponse;
import io.activej.promise.Promisable;
import io.activej.promise.Promise;
import lombok.AllArgsConstructor;
import org.bouncycastle.crypto.generators.BCrypt;
import org.bouncycastle.util.encoders.Hex;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.tresearchgroup.babygalago.controller.SettingsController;
import tech.tresearchgroup.babygalago.controller.controllers.ExtendedUserEntityController;
import tech.tresearchgroup.palila.controller.BasicController;
import tech.tresearchgroup.schemas.galago.entities.ExtendedUserEntity;

import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

@AllArgsConstructor
public class LoginEndpointsController extends BasicController {
    private static final Logger logger = LoggerFactory.getLogger(LoginEndpointsController.class);
    private final ExtendedUserEntityController extendedUserEntityController;
    private final SettingsController settingsController;
    private final Gson gson;

    /**
     * Returns the users entity as way of logging in using the API
     *
     * @param userEntity the user attempting to login
     * @return the user entity fetched from the database, with the password removed
     * @throws SQLException              if it fails
     * @throws InvocationTargetException if it fails
     * @throws IllegalAccessException    if it fails
     * @throws InstantiationException    if it fails
     */
    public byte[] login(ExtendedUserEntity userEntity) throws SQLException, InvocationTargetException, IllegalAccessException, InstantiationException {
        ExtendedUserEntity databaseUser = extendedUserEntityController.getUserByUsernameAndPassword(userEntity.getUsername(), userEntity.getPassword());
        databaseUser.setPassword(null);
        return gson.toJson(databaseUser).getBytes();
    }

    /**
     * Returns a user entity
     *
     * @param username    the username
     * @param password    the password
     * @param httpRequest the request
     * @return the user entity
     * @throws Exception if it fails
     */
    public ExtendedUserEntity getUser(String username, String password, HttpRequest httpRequest) throws Exception {
        String hashedPassword = hashPassword(password);
        ExtendedUserEntity databaseUser = extendedUserEntityController.getUserByUsernameAndPassword(username, hashedPassword);
        if (databaseUser != null) {
            if (databaseUser.getApiKey() == null) {
                databaseUser.setApiKey(generateKey(databaseUser.getId()));
                extendedUserEntityController.update(databaseUser.getId(), databaseUser, httpRequest);
            }
            return databaseUser;
        }
        return null;
    }

    /**
     * Creates a password hash using BCrypt
     *
     * @param password the plain text password
     * @return the hashed password
     */
    private String hashPassword(String password) {
        byte[] salt = new byte[16];
        return new String(Hex.encode(BCrypt.generate(password.getBytes(StandardCharsets.UTF_8), salt, 8)));
    }

    /**
     * Logs in using the API
     *
     * @param httpRequest
     * @return
     */
    @NotNull
    public Promisable<HttpResponse> apiLogin(@NotNull HttpRequest httpRequest) {
        try {
            Promise<ByteBuf> bodyLoaded = httpRequest.loadBody();
            if (bodyLoaded != null) {
                ByteBuf body = bodyLoaded.getResult();
                if (body != null) {
                    String data = body.asString(Charset.defaultCharset());
                    ExtendedUserEntity jsonUser = gson.fromJson(data, ExtendedUserEntity.class);
                    ExtendedUserEntity userEntity = getUser(jsonUser.getUsername(), jsonUser.getPassword(), httpRequest);
                    if (userEntity != null) {
                        if (settingsController.isDebug()) {
                            logger.info("Successfully logged in: " + userEntity.getUsername());
                        }
                        return HttpResponse.ok200().withBody(login(userEntity));
                    }
                } else {
                    if (settingsController.isDebug()) {
                        logger.info("The body was null");
                    }
                    return error();
                }
            } else {
                if (settingsController.isDebug()) {
                    logger.info("The login body could not load");
                }
                return error();
            }
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
        }
        return error();
    }
}
