package tech.tresearchgroup.babygalago.view.endpoints.api;

import io.activej.http.*;
import io.activej.inject.annotation.Provides;
import io.activej.promise.Promisable;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import tech.tresearchgroup.babygalago.controller.SettingsController;
import tech.tresearchgroup.babygalago.controller.endpoints.api.NewsEndpointsController;
import tech.tresearchgroup.palila.controller.HttpResponses;

@AllArgsConstructor
public class NewsEndpoints extends HttpResponses {
    private final NewsEndpointsController newsEndpointsController;
    private final SettingsController settingsController;

    @Provides
    public RoutingServlet servlet() {
        return RoutingServlet.create()
            .map(HttpMethod.PUT, "/v1/news", this::putNews)
            .map(HttpMethod.POST, "/v1/news", this::postNews)
            .map(HttpMethod.GET, "/v1/news", this::getNews)
            .map(HttpMethod.OPTIONS, "/v1/news", this::optionsNews)
            .map(HttpMethod.GET, "/v1/news/:newsId", this::getNewsById)
            .map(HttpMethod.DELETE, "/v1/news/:newsId", this::deleteNewsById)
            .map(HttpMethod.PATCH, "/v1/news/:newsId", this::patchNews)
            .map(HttpMethod.OPTIONS, "/v1/news/:newsId", this::optionsNewsById);
    }

    private @NotNull Promisable<HttpResponse> putNews(@NotNull HttpRequest httpRequest) {
        try {
            return newsEndpointsController.putNews(httpRequest);
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
        }
        return error();
    }

    private @NotNull Promisable<HttpResponse> postNews(@NotNull HttpRequest httpRequest) {
        try {
            return newsEndpointsController.postNews(httpRequest);
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
        }
        return error();
    }

    private @NotNull Promisable<HttpResponse> getNews(@NotNull HttpRequest httpRequest) {
        try {
            return newsEndpointsController.getNews(httpRequest);
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
        }
        return error();
    }

    private @NotNull Promisable<HttpResponse> patchNews(@NotNull HttpRequest httpRequest) {
        try {
            return newsEndpointsController.patchNews(httpRequest);
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
        }
        return error();
    }

    private @NotNull Promisable<HttpResponse> optionsNews(@NotNull HttpRequest httpRequest) {
        return HttpResponse.ok200().withHeader(HttpHeaders.ALLOW, HttpHeaderValue.of("PUT, POST, PATCH, GET"));
    }

    private @NotNull Promisable<HttpResponse> getNewsById(@NotNull HttpRequest httpRequest) {
        try {
            return newsEndpointsController.getNewsById(httpRequest);
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
        }
        return error();
    }

    private @NotNull Promisable<HttpResponse> deleteNewsById(@NotNull HttpRequest httpRequest) {
        try {
            return newsEndpointsController.deleteNewsById(httpRequest);
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
        }
        return error();
    }

    private @NotNull Promisable<HttpResponse> optionsNewsById(@NotNull HttpRequest httpRequest) {
        return HttpResponse.ok200().withHeader(HttpHeaders.ALLOW, HttpHeaderValue.of("GET, DELETE"));
    }
}
