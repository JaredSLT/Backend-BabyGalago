package tech.tresearchgroup.babygalago.view.endpoints.api;

import io.activej.http.HttpMethod;
import io.activej.http.RoutingServlet;
import io.activej.inject.annotation.Provides;
import io.activej.inject.module.AbstractModule;
import tech.tresearchgroup.babygalago.controller.endpoints.api.NotificationsEndpointsController;
import tech.tresearchgroup.palila.controller.EndpointsRouter;
import tech.tresearchgroup.palila.controller.RoutingServletBuilder;
import tech.tresearchgroup.palila.model.endpoints.Endpoint;

public class NotificationsEndpoints extends AbstractModule implements EndpointsRouter {
    private NotificationsEndpointsController notificationsEndpointsController;

    public NotificationsEndpoints() {
    }

    public NotificationsEndpoints(NotificationsEndpointsController notificationsEndpointsController) {
        this.notificationsEndpointsController = notificationsEndpointsController;
    }

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
            new Endpoint(HttpMethod.PUT, "/v1/notifications", notificationsEndpointsController::putNotification),
            new Endpoint(HttpMethod.POST, "/v1/notifications", notificationsEndpointsController::postNotification),
            new Endpoint(HttpMethod.GET, "/v1/notifications", notificationsEndpointsController::getNotifications),
            new Endpoint(HttpMethod.OPTIONS, "/v1/notifications", notificationsEndpointsController::optionsNotifications),
            new Endpoint(HttpMethod.DELETE, "/v1/notifications/:notificationId", notificationsEndpointsController::deleteNotificationById),
            new Endpoint(HttpMethod.GET, "/v1/notifications/:notificationId", notificationsEndpointsController::getNotificationById),
            new Endpoint(HttpMethod.OPTIONS, "/v1/notifications/:notificationId", notificationsEndpointsController::optionsNotificationsById)
        };
    }
}
