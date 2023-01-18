package tech.tresearchgroup.babygalago.controller.endpoints.api;

import io.activej.http.HttpRequest;
import io.activej.http.HttpResponse;
import io.activej.promise.Promisable;
import lombok.AllArgsConstructor;
import tech.tresearchgroup.babygalago.controller.controllers.NotificationEntityController;
import tech.tresearchgroup.palila.controller.BasicController;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.Objects;

@AllArgsConstructor
public class NotificationsEndpointsController extends BasicController {
    private final NotificationEntityController notificationsController;

    public Promisable<HttpResponse> putNotification(HttpRequest httpRequest) throws Exception {
        String data = httpRequest.loadBody().getResult().asString(Charset.defaultCharset());
        return okResponseCompressed(notificationsController.createSecureAPIResponse(data, httpRequest));
    }

    public Promisable<HttpResponse> postNotification(HttpRequest httpRequest) throws Exception {
        String data = httpRequest.loadBody().getResult().asString(Charset.defaultCharset());
        return okResponseCompressed(notificationsController.createSecureAPIResponse(data, httpRequest));
    }

    public Promisable<HttpResponse> getNotifications(HttpRequest httpRequest) throws SQLException, IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        int page = httpRequest.getQueryParameter("page") != null ? Integer.parseInt(Objects.requireNonNull(httpRequest.getQueryParameter("page"))) : 0;
        int pageSize = httpRequest.getQueryParameter("pageSize") != null ? Integer.parseInt(Objects.requireNonNull(httpRequest.getQueryParameter("pageSize"))) : 0;
        byte[] notifications = notificationsController.readPaginatedAPIResponse(page, pageSize, true, httpRequest);
        if (notifications != null) {
            return okResponseCompressed(notifications);
        }
        return notFound();
    }

    public Promisable<HttpResponse> deleteNotificationById(HttpRequest httpRequest) throws Exception {
        int notificationId = Integer.parseInt(httpRequest.getPathParameter("notificationId"));
        return ok(notificationsController.delete(notificationId, httpRequest));
    }

    public Promisable<HttpResponse> getNotificationById(HttpRequest httpRequest) throws SQLException, IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        long notificationId = Long.parseLong(httpRequest.getPathParameter("notificationId"));
        byte[] data = notificationsController.readSecureAPIResponse(notificationId, httpRequest);
        if (data != null) {
            return okResponseCompressed(data);
        }
        return notFound();
    }
}
