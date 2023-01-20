package tech.tresearchgroup.babygalago.view.endpoints.api;

import io.activej.http.HttpMethod;
import io.activej.http.RoutingServlet;
import io.activej.inject.annotation.Provides;
import io.activej.inject.module.AbstractModule;
import lombok.AllArgsConstructor;
import tech.tresearchgroup.babygalago.controller.endpoints.api.SettingsEndpointsController;
import tech.tresearchgroup.babygalago.controller.endpoints.api.UserEndpointsController;

@AllArgsConstructor
public class UserEndpoints extends AbstractModule {
    private final UserEndpointsController userEndpointsController;
    private final SettingsEndpointsController settingsEndpointsController;

    /**
     * Creates the endpoints and maps them to their respective methods
     * @return the routing servlet
     */
    @Provides
    public RoutingServlet servlet() {
        return RoutingServlet.create()
            .map(HttpMethod.GET, "/v1/user", userEndpointsController::getUsers)
            .map(HttpMethod.POST, "/v1/user", userEndpointsController::postUser)
            .map(HttpMethod.PUT, "/v1/user", userEndpointsController::putUser)
            .map(HttpMethod.OPTIONS, "/v1/user", userEndpointsController::optionsUsers)
            .map(HttpMethod.GET, "/v1/user/sample", userEndpointsController::getSample)
            .map(HttpMethod.GET, "/v1/user/:userId/settings", settingsEndpointsController::getUserSettings)
            .map(HttpMethod.POST, "/v1/user/:userId/settings", settingsEndpointsController::createUserSettings)
            .map(HttpMethod.PATCH, "/v1/user/:userId/settings", settingsEndpointsController::patchUserSettings)
            .map(HttpMethod.DELETE, "/v1/user/:userId/settings", settingsEndpointsController::deleteUserSettings)
            .map(HttpMethod.OPTIONS, "/v1/user/:userId/settings", settingsEndpointsController::optionsSettingsById)
            .map(HttpMethod.GET, "/v1/user/:userId", userEndpointsController::getUserById)
            .map(HttpMethod.DELETE, "/v1/user/:userId", userEndpointsController::deleteUserById)
            .map(HttpMethod.POST, "/v1/user/:userId", userEndpointsController::postUserById)
            .map(HttpMethod.PATCH, "/v1/user/:userId", userEndpointsController::patchUser)
            .map(HttpMethod.OPTIONS, "/v1/user/:userId", userEndpointsController::optionsUserById);
    }
}
