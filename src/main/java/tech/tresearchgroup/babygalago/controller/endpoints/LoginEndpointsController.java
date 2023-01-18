package tech.tresearchgroup.babygalago.controller.endpoints;

import com.google.gson.Gson;
import io.activej.http.HttpRequest;
import lombok.AllArgsConstructor;
import org.bouncycastle.crypto.generators.BCrypt;
import org.bouncycastle.util.encoders.Hex;
import tech.tresearchgroup.babygalago.controller.controllers.UserEntityController;
import tech.tresearchgroup.palila.controller.BasicController;
import tech.tresearchgroup.schemas.galago.entities.ExtendedUserEntity;

import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

@AllArgsConstructor
public class LoginEndpointsController extends BasicController {
    private final UserEntityController userEntityController;
    private final Gson gson;

    public byte[] login(ExtendedUserEntity userEntity) throws SQLException, InvocationTargetException, IllegalAccessException, InstantiationException {
        //Todo get from cache
        ExtendedUserEntity databaseUser = userEntityController.getUserByUsernameAndPassword(userEntity.getUsername(), userEntity.getPassword());
        databaseUser.setPassword(null);
        return gson.toJson(databaseUser).getBytes();
    }

    public ExtendedUserEntity getUser(String username, String password, HttpRequest httpRequest) throws Exception {
        String hashedPassword = hashPassword(password);
        //Todo get from cache
        ExtendedUserEntity databaseUser = userEntityController.getUserByUsernameAndPassword(username, hashedPassword);
        if (databaseUser != null) {
            if (databaseUser.getApiKey() == null) {
                databaseUser.setApiKey(generateKey(databaseUser.getId()));
                userEntityController.update(databaseUser.getId(), databaseUser, httpRequest);
            }
            return databaseUser;
        }
        return null;
    }

    private String hashPassword(String password) {
        byte[] salt = new byte[16];
        return new String(Hex.encode(BCrypt.generate(password.getBytes(StandardCharsets.UTF_8), salt, 8)));
    }
}
