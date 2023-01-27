package tech.tresearchgroup.babygalago.view.endpoints.api;

import io.activej.http.HttpMethod;
import io.activej.http.RoutingServlet;
import io.activej.inject.annotation.Provides;
import io.activej.inject.module.AbstractModule;
import lombok.AllArgsConstructor;
import tech.tresearchgroup.babygalago.controller.endpoints.api.SettingsEndpointsController;
import tech.tresearchgroup.babygalago.controller.endpoints.api.UserEndpointsController;
import tech.tresearchgroup.palila.controller.EndpointsRouter;
import tech.tresearchgroup.palila.controller.RoutingServletBuilder;
import tech.tresearchgroup.palila.model.endpoints.Endpoint;

@AllArgsConstructor
public class UserEndpoints extends AbstractModule implements EndpointsRouter {
    private final UserEndpointsController userEndpointsController;
    private final SettingsEndpointsController settingsEndpointsController;

    /**
     * Creates the endpoints and maps them to their respective methods
     *
     * @return the routing servlet
     */
    @Provides
    public RoutingServlet servlet() {
        return RoutingServletBuilder.build(getEndpoints());
    }

    @Override
    public Endpoint[] getEndpoints() {
        return new Endpoint[]{
            new Endpoint(HttpMethod.GET, "/v1/user", userEndpointsController::getUsers),
            new Endpoint(HttpMethod.POST, "/v1/user", userEndpointsController::postUser),
            new Endpoint(HttpMethod.PUT, "/v1/user", userEndpointsController::putUser),
            new Endpoint(HttpMethod.OPTIONS, "/v1/user", userEndpointsController::optionsUsers),
            new Endpoint(HttpMethod.GET, "/v1/user/sample", userEndpointsController::getSample),
            new Endpoint(HttpMethod.GET, "/v1/user/:userId/settings", settingsEndpointsController::getUserSettings),
            new Endpoint(HttpMethod.POST, "/v1/user/:userId/settings", settingsEndpointsController::createUserSettings),
            new Endpoint(HttpMethod.PATCH, "/v1/user/:userId/settings", settingsEndpointsController::patchUserSettings),
            new Endpoint(HttpMethod.DELETE, "/v1/user/:userId/settings", settingsEndpointsController::deleteUserSettings),
            new Endpoint(HttpMethod.OPTIONS, "/v1/user/:userId/settings", settingsEndpointsController::optionsSettingsById),
            new Endpoint(HttpMethod.GET, "/v1/user/:userId", userEndpointsController::getUserById),
            new Endpoint(HttpMethod.DELETE, "/v1/user/:userId", userEndpointsController::deleteUserById),
            new Endpoint(HttpMethod.POST, "/v1/user/:userId", userEndpointsController::postUserById),
            new Endpoint(HttpMethod.PATCH, "/v1/user/:userId", userEndpointsController::patchUser),
            new Endpoint(HttpMethod.OPTIONS, "/v1/user/:userId", userEndpointsController::optionsUserById)
        };
    }
}
