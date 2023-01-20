package tech.tresearchgroup.babygalago.view.endpoints.api;

import io.activej.http.HttpMethod;
import io.activej.http.RoutingServlet;
import io.activej.inject.annotation.Provides;
import io.activej.inject.module.AbstractModule;
import lombok.AllArgsConstructor;
import tech.tresearchgroup.babygalago.controller.endpoints.api.NotificationsEndpointsController;

@AllArgsConstructor
public class NotificationsEndpoints extends AbstractModule {
    private final NotificationsEndpointsController notificationsEndpointsController;

    /**
     * Creates the endpoints and maps them to their respective methods
     * @return the routing servlet
     */
    @Provides
    public RoutingServlet servlet() {
        return RoutingServlet.create()
            .map(HttpMethod.PUT, "/v1/notifications", notificationsEndpointsController::putNotification)
            .map(HttpMethod.POST, "/v1/notifications", notificationsEndpointsController::postNotification)
            .map(HttpMethod.GET, "/v1/notifications", notificationsEndpointsController::getNotifications)
            .map(HttpMethod.OPTIONS, "/v1/notifications", notificationsEndpointsController::optionsNotifications)
            .map(HttpMethod.DELETE, "/v1/notifications/:notificationId", notificationsEndpointsController::deleteNotificationById)
            .map(HttpMethod.GET, "/v1/notifications/:notificationId", notificationsEndpointsController::getNotificationById)
            .map(HttpMethod.OPTIONS, "/v1/notifications/:notificationId", notificationsEndpointsController::optionsNotificationsById);
    }
}
