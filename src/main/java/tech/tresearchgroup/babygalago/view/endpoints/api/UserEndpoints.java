package tech.tresearchgroup.babygalago.view.endpoints.api;

import io.activej.http.*;
import io.activej.inject.annotation.Provides;
import io.activej.promise.Promisable;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import tech.tresearchgroup.babygalago.controller.SettingsController;
import tech.tresearchgroup.babygalago.controller.endpoints.api.SettingsEndpointsController;
import tech.tresearchgroup.babygalago.controller.endpoints.api.UserEndpointsController;
import tech.tresearchgroup.palila.controller.HttpResponses;

@AllArgsConstructor
public class UserEndpoints extends HttpResponses {
    private final UserEndpointsController userEndpointsController;
    private final SettingsController settingsController;
    private final SettingsEndpointsController settingsEndpointsController;

    @Provides
    public RoutingServlet servlet() {
        return RoutingServlet.create()
            .map(HttpMethod.GET, "/v1/user", this::getUsers)
            .map(HttpMethod.POST, "/v1/user", this::postUser)
            .map(HttpMethod.PUT, "/v1/user", this::putUser)
            .map(HttpMethod.OPTIONS, "/v1/user", this::optionsUsers)
            .map(HttpMethod.GET, "/v1/user/:userId/settings", this::getUserSettings)
            .map(HttpMethod.POST, "/v1/user/:userId/settings", this::createUserSettings)
            .map(HttpMethod.PATCH, "/v1/user/:userId/settings", this::patchUserSettings)
            .map(HttpMethod.DELETE, "/v1/user/:userId/settings", this::deleteUserSettings)
            .map(HttpMethod.OPTIONS, "/v1/user/:userId/settings", this::optionsSettingsById)
            .map(HttpMethod.GET, "/v1/user/:userId", this::getUserById)
            .map(HttpMethod.DELETE, "/v1/user/:userId", this::deleteUserById)
            .map(HttpMethod.POST, "/v1/user/:userId", this::postUserById)
            .map(HttpMethod.PATCH, "/v1/user/:userId", this::patchUser)
            .map(HttpMethod.OPTIONS, "/v1/user/:userId", this::optionsUserById);
    }

    private @NotNull Promisable<HttpResponse> getUsers(@NotNull HttpRequest httpRequest) {
        try {
            return userEndpointsController.getUsers(httpRequest);
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
        }
        return error();
    }

    private @NotNull Promisable<HttpResponse> postUser(@NotNull HttpRequest httpRequest) {
        try {
            return userEndpointsController.postUser(httpRequest);
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
        }
        return error();
    }

    private @NotNull Promisable<HttpResponse> putUser(@NotNull HttpRequest httpRequest) {
        try {
            return userEndpointsController.putUser(httpRequest);
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
        }
        return error();
    }

    private @NotNull Promisable<HttpResponse> getUserById(@NotNull HttpRequest httpRequest) {
        try {
            return userEndpointsController.getUserById(httpRequest);
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
        }
        return error();
    }

    private @NotNull Promisable<HttpResponse> patchUser(@NotNull HttpRequest httpRequest) {
        try {
            return userEndpointsController.patchUser(httpRequest);
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
        }
        return error();
    }

    private @NotNull Promisable<HttpResponse> optionsUsers(@NotNull HttpRequest httpRequest) {
        return HttpResponse.ok200().withHeader(HttpHeaders.ALLOW, HttpHeaderValue.of("GET, POST, PUT, PATCH"));
    }

    private @NotNull Promisable<HttpResponse> getUserSettings(@NotNull HttpRequest httpRequest) {
        try {
            return settingsEndpointsController.getUserSettings(httpRequest);
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
        }
        return error();
    }

    private @NotNull Promisable<HttpResponse> createUserSettings(@NotNull HttpRequest httpRequest) {
        try {
            return settingsEndpointsController.createUserSettings(httpRequest);
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
        }
        return error();
    }

    private @NotNull Promisable<HttpResponse> patchUserSettings(@NotNull HttpRequest httpRequest) {
        try {
            return settingsEndpointsController.patchUserSettings(httpRequest);
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
        }
        return error();
    }

    private @NotNull Promisable<HttpResponse> deleteUserSettings(@NotNull HttpRequest httpRequest) {
        try {
            return settingsEndpointsController.deleteUserSettings(httpRequest);
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
        }
        return error();
    }

    private @NotNull Promisable<HttpResponse> optionsSettingsById(@NotNull HttpRequest httpRequest) {
        return HttpResponse.ok200().withHeader(HttpHeaders.ALLOW, HttpHeaderValue.of("GET, POST, PATCH, DELETE"));
    }

    private @NotNull Promisable<HttpResponse> deleteUserById(@NotNull HttpRequest httpRequest) {
        try {
            return userEndpointsController.deleteUserById(httpRequest);
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
        }
        return error();
    }

    private @NotNull Promisable<HttpResponse> postUserById(@NotNull HttpRequest httpRequest) {
        try {
            userEndpointsController.postUserById(httpRequest);
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
        }
        return error();
    }

    private @NotNull Promisable<HttpResponse> optionsUserById(@NotNull HttpRequest httpRequest) {
        return HttpResponse.ok200().withHeader(HttpHeaders.ALLOW, HttpHeaderValue.of("GET, DELETE, POST"));
    }
}
