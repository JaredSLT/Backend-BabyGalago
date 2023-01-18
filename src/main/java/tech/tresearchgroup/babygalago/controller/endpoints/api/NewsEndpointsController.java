package tech.tresearchgroup.babygalago.controller.endpoints.api;

import io.activej.http.HttpRequest;
import io.activej.http.HttpResponse;
import io.activej.promise.Promisable;
import lombok.AllArgsConstructor;
import tech.tresearchgroup.babygalago.controller.controllers.NewsArticleEntityController;
import tech.tresearchgroup.palila.controller.BasicController;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.Objects;

@AllArgsConstructor
public class NewsEndpointsController extends BasicController {
    private final NewsArticleEntityController newsArticleEntityController;

    public Promisable<HttpResponse> putNews(HttpRequest httpRequest) throws Exception {
        String data = httpRequest.loadBody().getResult().asString(Charset.defaultCharset());
        return ok(newsArticleEntityController.createSecureAPIResponse(data, httpRequest) != null);
    }

    public Promisable<HttpResponse> postNews(HttpRequest httpRequest) throws Exception {
        String data = httpRequest.loadBody().getResult().asString(Charset.defaultCharset());
        return okResponseCompressed(newsArticleEntityController.createSecureAPIResponse(data, httpRequest));
    }

    public Promisable<HttpResponse> getNews(HttpRequest httpRequest) throws SQLException, IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        int page = httpRequest.getQueryParameter("page") != null ? Integer.parseInt(Objects.requireNonNull(httpRequest.getQueryParameter("page"))) : 0;
        int pageSize = httpRequest.getQueryParameter("pageSize") != null ? Integer.parseInt(Objects.requireNonNull(httpRequest.getQueryParameter("pageSize"))) : 0;
        byte[] data = newsArticleEntityController.readPaginatedAPIResponse(page, pageSize, true, httpRequest);
        if (data != null) {
            if (data.length > 0) {
                return okResponseCompressed(data);
            }
        }
        return notFound();
    }

    public Promisable<HttpResponse> patchNews(HttpRequest httpRequest) throws Exception {
        String data = httpRequest.loadBody().getResult().asString(Charset.defaultCharset());
        long id = Long.parseLong(httpRequest.getPathParameter("newsId"));
        return ok(newsArticleEntityController.update(id, data, httpRequest));
    }

    public Promisable<HttpResponse> getNewsById(HttpRequest httpRequest) throws SQLException, IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        long newsId = Long.parseLong(httpRequest.getPathParameter("newsId"));
        byte[] data = newsArticleEntityController.readSecureAPIResponse(newsId, httpRequest);
        if (data != null) {
            return okResponseCompressed(data);
        }
        return notFound();
    }

    public Promisable<HttpResponse> deleteNewsById(HttpRequest httpRequest) throws Exception {
        int newsId = Integer.parseInt(httpRequest.getPathParameter("newsId"));
        return ok(newsArticleEntityController.delete(newsId, httpRequest));
    }
}
