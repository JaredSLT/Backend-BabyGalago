package tech.tresearchgroup.babygalago.view.endpoints.api;

import io.activej.http.*;
import io.activej.inject.annotation.Provides;
import io.activej.promise.Promisable;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import tech.tresearchgroup.babygalago.controller.SettingsController;
import tech.tresearchgroup.babygalago.controller.endpoints.api.NotificationsEndpointsController;
import tech.tresearchgroup.palila.controller.HttpResponses;

@AllArgsConstructor
public class NotificationsEndpoints extends HttpResponses {
    private final NotificationsEndpointsController notificationsEndpointsController;
    private final SettingsController settingsController;

    @Provides
    public RoutingServlet servlet() {
        return RoutingServlet.create()
            .map(HttpMethod.PUT, "/v1/notifications", this::putNotification)
            .map(HttpMethod.POST, "/v1/notifications", this::postNotification)
            .map(HttpMethod.GET, "/v1/notifications", this::getNotifications)
            .map(HttpMethod.OPTIONS, "/v1/notifications", this::optionsNotifications)
            .map(HttpMethod.DELETE, "/v1/notifications/:notificationId", this::deleteNotificationById)
            .map(HttpMethod.GET, "/v1/notifications/:notificationId", this::getNotificationById)
            .map(HttpMethod.OPTIONS, "/v1/notifications/:notificationId", this::optionsNotificationsById);
    }

    private @NotNull Promisable<HttpResponse> putNotification(@NotNull HttpRequest httpRequest) {
        try {
            return notificationsEndpointsController.putNotification(httpRequest);
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
        }
        return error();
    }

    private @NotNull Promisable<HttpResponse> postNotification(@NotNull HttpRequest httpRequest) {
        try {
            return notificationsEndpointsController.postNotification(httpRequest);
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
        }
        return error();
    }

    private @NotNull Promisable<HttpResponse> getNotifications(@NotNull HttpRequest httpRequest) {
        try {
            return notificationsEndpointsController.getNotifications(httpRequest);
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
        }
        return error();
    }

    private @NotNull Promisable<HttpResponse> optionsNotifications(@NotNull HttpRequest httpRequest) {
        return HttpResponse.ok200().withHeader(HttpHeaders.ALLOW, HttpHeaderValue.of("PUT, POST, DELETE"));
    }

    private @NotNull Promisable<HttpResponse> deleteNotificationById(@NotNull HttpRequest httpRequest) {
        try {
            return notificationsEndpointsController.deleteNotificationById(httpRequest);
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
        }
        return error();
    }

    private @NotNull Promisable<HttpResponse> getNotificationById(@NotNull HttpRequest httpRequest) {
        try {
            return notificationsEndpointsController.getNotificationById(httpRequest);
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
        }
        return error();
    }

    private @NotNull Promisable<HttpResponse> optionsNotificationsById(@NotNull HttpRequest httpRequest) {
        return HttpResponse.ok200().withHeader(HttpHeaders.ALLOW, HttpHeaderValue.of("DELETE, GET"));
    }
}
