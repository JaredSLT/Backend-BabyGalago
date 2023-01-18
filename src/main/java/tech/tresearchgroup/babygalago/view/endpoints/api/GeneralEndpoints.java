package tech.tresearchgroup.babygalago.view.endpoints.api;

import io.activej.http.*;
import io.activej.inject.annotation.Provides;
import io.activej.promise.Promisable;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import tech.tresearchgroup.babygalago.controller.SettingsController;
import tech.tresearchgroup.babygalago.controller.endpoints.api.GeneralEndpointsController;
import tech.tresearchgroup.palila.controller.HttpResponses;

@AllArgsConstructor
public class GeneralEndpoints extends HttpResponses {
    private final GeneralEndpointsController generalEndpointsController;
    private final SettingsController settingsController;

    @Provides
    public RoutingServlet servlet() {
        return RoutingServlet.create()
            .map(HttpMethod.GET, "/v1/version", this::getVersion)
            .map(HttpMethod.OPTIONS, "/v1/version", this::optionsVersion)
            .map(HttpMethod.GET, "/v1/version/latest", this::getLatest)
            .map(HttpMethod.OPTIONS, "/v1/version/latest", this::optionsVersionLatest)
            .map(HttpMethod.PUT, "/v1/update", this::putUpdate)
            .map(HttpMethod.OPTIONS, "/v1/update", this::optionsUpdate)
            .map(HttpMethod.POST, "/v1/upload", this::postUpload)
            .map(HttpMethod.OPTIONS, "/v1/upload", this::optionsUpload)
            .map(HttpMethod.GET, "/v1/search", this::getSearch)
            .map(HttpMethod.OPTIONS, "/v1/search", this::optionsSearch);
    }

    private @NotNull Promisable<HttpResponse> getVersion(@NotNull HttpRequest httpRequest) {
        try {
            return generalEndpointsController.getVersion(httpRequest);
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
            return error();
        }
    }

    private @NotNull Promisable<HttpResponse> optionsVersion(@NotNull HttpRequest httpRequest) {
        try {
            return HttpResponse.ok200().withHeader(HttpHeaders.ALLOW, HttpHeaderValue.of("GET"));
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
            return error();
        }
    }

    private @NotNull Promisable<HttpResponse> getLatest(@NotNull HttpRequest httpRequest) {
        try {
            return generalEndpointsController.getLatest(httpRequest);
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
            return error();
        }
    }

    private @NotNull Promisable<HttpResponse> optionsVersionLatest(@NotNull HttpRequest httpRequest) {
        try {
            return HttpResponse.ok200().withHeader(HttpHeaders.ALLOW, HttpHeaderValue.of("GET"));
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
            return error();
        }
    }

    private Promisable<HttpResponse> putUpdate(@NotNull HttpRequest httpRequest) {
        try {
            return generalEndpointsController.putUpdate(httpRequest);
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
            return error();
        }
    }

    private Promisable<HttpResponse> optionsUpdate(@NotNull HttpRequest httpRequest) {
        try {
            return HttpResponse.ok200().withHeader(HttpHeaders.ALLOW, HttpHeaderValue.of("PUT"));
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
            return error();
        }
    }

    private @NotNull Promisable<HttpResponse> postUpload(@NotNull HttpRequest httpRequest) {
        try {
            return generalEndpointsController.postUpload(httpRequest);
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
            return error();
        }
    }

    private @NotNull Promisable<HttpResponse> optionsUpload(@NotNull HttpRequest httpRequest) {
        try {
            return HttpResponse.ok200().withHeader(HttpHeaders.ALLOW, HttpHeaderValue.of("POST"));
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
            return error();
        }
    }

    private @NotNull Promisable<HttpResponse> getSearch(@NotNull HttpRequest httpRequest) {
        try {
            String query = httpRequest.getQueryParameter("query");
            String mediaType = httpRequest.getQueryParameter("type");
            return generalEndpointsController.getSearch(mediaType, query, httpRequest);
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
            return error();
        }
    }

    private @NotNull Promisable<HttpResponse> optionsSearch(@NotNull HttpRequest httpRequest) {
        try {
            return HttpResponse.ok200().withHeader(HttpHeaders.ALLOW, HttpHeaderValue.of("GET"));
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
            return error();
        }
    }
}
