package tech.tresearchgroup.babygalago.controller.endpoints.api;

import io.activej.http.HttpHeaderValue;
import io.activej.http.HttpHeaders;
import io.activej.http.HttpRequest;
import io.activej.http.HttpResponse;
import io.activej.promise.Promisable;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import tech.tresearchgroup.babygalago.controller.controllers.NotificationEntityController;
import tech.tresearchgroup.palila.controller.BasicController;
import tech.tresearchgroup.palila.model.enums.ReturnType;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.Objects;

@AllArgsConstructor
public class NotificationsEndpointsController extends BasicController {
    private final NotificationEntityController notificationsController;

    /**
     * Creates a notification entity
     *
     * @param httpRequest the request
     * @return the page response
     * @throws Exception if its fails
     */
    public Promisable<HttpResponse> putNotification(HttpRequest httpRequest) throws Exception {
        String data = httpRequest.loadBody().getResult().asString(Charset.defaultCharset());
        return okResponseCompressed((byte[]) notificationsController.createSecureResponse(data, ReturnType.JSON, httpRequest));
    }

    /**
     * Creates a notification entity
     *
     * @param httpRequest the request
     * @return the page response
     * @throws Exception if its fails
     */
    public Promisable<HttpResponse> postNotification(HttpRequest httpRequest) throws Exception {
        String data = httpRequest.loadBody().getResult().asString(Charset.defaultCharset());
        return okResponseCompressed((byte[]) notificationsController.createSecureResponse(data, ReturnType.JSON, httpRequest));
    }

    /**
     * Gets a list of notification entities
     *
     * @param httpRequest the request
     * @return the page response
     * @throws SQLException              if its fails
     * @throws IOException               if its fails
     * @throws InvocationTargetException if its fails
     * @throws NoSuchMethodException     if its fails
     * @throws IllegalAccessException    if its fails
     * @throws InstantiationException    if its fails
     */
    public Promisable<HttpResponse> getNotifications(HttpRequest httpRequest) throws SQLException, IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        int page = httpRequest.getQueryParameter("page") != null ? Integer.parseInt(Objects.requireNonNull(httpRequest.getQueryParameter("page"))) : 0;
        int pageSize = httpRequest.getQueryParameter("pageSize") != null ? Integer.parseInt(Objects.requireNonNull(httpRequest.getQueryParameter("pageSize"))) : 0;
        byte[] notifications = (byte[]) notificationsController.readPaginatedResponse(page, pageSize, true, ReturnType.JSON, httpRequest);
        if (notifications != null) {
            return okResponseCompressed(notifications);
        }
        return notFound();
    }

    /**
     * Deletes a notification entity by id
     *
     * @param httpRequest the request
     * @return the page response
     * @throws Exception if its fails
     */
    public Promisable<HttpResponse> deleteNotificationById(HttpRequest httpRequest) throws Exception {
        int notificationId = Integer.parseInt(httpRequest.getPathParameter("notificationId"));
        return ok(notificationsController.delete(notificationId, httpRequest));
    }

    /**
     * Gets a notification entity by id
     *
     * @param httpRequest the request
     * @return the page response
     * @throws SQLException              if its fails
     * @throws IOException               if its fails
     * @throws InvocationTargetException if its fails
     * @throws NoSuchMethodException     if its fails
     * @throws InstantiationException    if its fails
     * @throws IllegalAccessException    if its fails
     */
    public Promisable<HttpResponse> getNotificationById(HttpRequest httpRequest) throws SQLException, IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        long notificationId = Long.parseLong(httpRequest.getPathParameter("notificationId"));
        byte[] data = (byte[]) notificationsController.readSecureResponse(notificationId, ReturnType.JSON, httpRequest);
        if (data != null) {
            return okResponseCompressed(data);
        }
        return notFound();
    }

    /**
     * Gets which methods are available for this endpoint
     *
     * @param httpRequest the request
     * @return the response page
     */
    @NotNull
    public Promisable<HttpResponse> optionsNotifications(@NotNull HttpRequest httpRequest) {
        return HttpResponse.ok200().withHeader(HttpHeaders.ALLOW, HttpHeaderValue.of("PUT, POST, DELETE"));
    }

    /**
     * Gets which methods are available for this endpoint
     *
     * @param httpRequest the request
     * @return the response page
     */
    @NotNull
    public Promisable<HttpResponse> optionsNotificationsById(@NotNull HttpRequest httpRequest) {
        return HttpResponse.ok200().withHeader(HttpHeaders.ALLOW, HttpHeaderValue.of("DELETE, GET"));
    }
}
