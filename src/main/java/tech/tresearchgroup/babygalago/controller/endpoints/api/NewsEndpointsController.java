package tech.tresearchgroup.babygalago.controller.endpoints.api;

import io.activej.http.HttpHeaderValue;
import io.activej.http.HttpHeaders;
import io.activej.http.HttpRequest;
import io.activej.http.HttpResponse;
import io.activej.promise.Promisable;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import tech.tresearchgroup.babygalago.controller.controllers.NewsArticleEntityController;
import tech.tresearchgroup.palila.controller.BasicController;
import tech.tresearchgroup.palila.model.enums.ReturnType;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.Objects;

@AllArgsConstructor
public class NewsEndpointsController extends BasicController {
    private final NewsArticleEntityController newsArticleEntityController;

    /**
     * Creates a news entity
     * @param httpRequest the request
     * @return the page response
     * @throws Exception if its fails
     */
    public Promisable<HttpResponse> putNews(HttpRequest httpRequest) throws Exception {
        String data = httpRequest.loadBody().getResult().asString(Charset.defaultCharset());
        return ok(newsArticleEntityController.createSecureResponse(data, ReturnType.JSON, httpRequest) != null);
    }

    /**
     * Creates a news entity
     * @param httpRequest the request
     * @return the page response
     * @throws Exception if its fails
     */
    public Promisable<HttpResponse> postNews(HttpRequest httpRequest) throws Exception {
        String data = httpRequest.loadBody().getResult().asString(Charset.defaultCharset());
        return okResponseCompressed((byte[]) newsArticleEntityController.createSecureResponse(data, ReturnType.JSON, httpRequest));
    }

    /**
     * Gets a list of news entities
     * @param httpRequest the request
     * @return the page response
     * @throws SQLException if its fails
     * @throws IOException if its fails
     * @throws InvocationTargetException if its fails
     * @throws NoSuchMethodException if its fails
     * @throws IllegalAccessException if its fails
     * @throws InstantiationException if its fails
     */
    public Promisable<HttpResponse> getNews(HttpRequest httpRequest) throws SQLException, IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        int page = httpRequest.getQueryParameter("page") != null ? Integer.parseInt(Objects.requireNonNull(httpRequest.getQueryParameter("page"))) : 0;
        int pageSize = httpRequest.getQueryParameter("pageSize") != null ? Integer.parseInt(Objects.requireNonNull(httpRequest.getQueryParameter("pageSize"))) : 0;
        byte[] data = (byte[]) newsArticleEntityController.readPaginatedResponse(page, pageSize, true, ReturnType.JSON, httpRequest);
        if (data != null) {
            if (data.length > 0) {
                return okResponseCompressed(data);
            }
        }
        return notFound();
    }

    /**
     * Patches the news entity
     * @param httpRequest the request
     * @return the page response
     * @throws Exception if its fails
     */
    public Promisable<HttpResponse> patchNews(HttpRequest httpRequest) throws Exception {
        String data = httpRequest.loadBody().getResult().asString(Charset.defaultCharset());
        long id = Long.parseLong(httpRequest.getPathParameter("newsId"));
        return ok(newsArticleEntityController.update(id, data, httpRequest));
    }

    /**
     * Gets a news entity by the id
     * @param httpRequest the request
     * @return the page response
     * @throws SQLException if its fails
     * @throws IOException if its fails
     * @throws InvocationTargetException if its fails
     * @throws NoSuchMethodException if its fails
     * @throws InstantiationException if its fails
     * @throws IllegalAccessException if its fails
     */
    public Promisable<HttpResponse> getNewsById(HttpRequest httpRequest) throws SQLException, IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        long newsId = Long.parseLong(httpRequest.getPathParameter("newsId"));
        byte[] data = (byte[]) newsArticleEntityController.readSecureResponse(newsId, ReturnType.JSON, httpRequest);
        if (data != null) {
            return okResponseCompressed(data);
        }
        return notFound();
    }

    /**
     * Delete a news entity by id
     * @param httpRequest the request
     * @return the page response
     * @throws Exception if its fails
     */
    public Promisable<HttpResponse> deleteNewsById(HttpRequest httpRequest) throws Exception {
        int newsId = Integer.parseInt(httpRequest.getPathParameter("newsId"));
        return ok(newsArticleEntityController.delete(newsId, httpRequest));
    }

    /**
     * Gets which methods are available for this endpoint
     * @param httpRequest the request
     * @return the response page
     */
    @NotNull
    public Promisable<HttpResponse> optionsNews(@NotNull HttpRequest httpRequest) {
        return HttpResponse.ok200().withHeader(HttpHeaders.ALLOW, HttpHeaderValue.of("PUT, POST, PATCH, GET"));
    }

    /**
     * Gets which methods are available for this endpoint
     * @param httpRequest the request
     * @return the response page
     */
    @NotNull
    public Promisable<HttpResponse> optionsNewsById(@NotNull HttpRequest httpRequest) {
        return HttpResponse.ok200().withHeader(HttpHeaders.ALLOW, HttpHeaderValue.of("GET, DELETE"));
    }
}
