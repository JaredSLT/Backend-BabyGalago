package tech.tresearchgroup.babygalago.view.endpoints.api;

import io.activej.http.*;
import io.activej.inject.annotation.Provides;
import io.activej.promise.Promisable;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import tech.tresearchgroup.babygalago.controller.SettingsController;
import tech.tresearchgroup.babygalago.controller.endpoints.api.MediaTypeEndpointsController;
import tech.tresearchgroup.palila.controller.HttpResponses;

@AllArgsConstructor
public class MediaTypeEndpoints extends HttpResponses {
    private final SettingsController settingsController;
    private final MediaTypeEndpointsController mediaTypeEndpointsController;

    @Provides
    public RoutingServlet servlet() {
        return RoutingServlet.create()
            .map(HttpMethod.GET, "/v1/:mediaType", this::getMedia)
            .map(HttpMethod.POST, "/v1/:mediaType", this::postMedia)
            .map(HttpMethod.PUT, "/v1/:mediaType", this::putMedia)
            .map(HttpMethod.OPTIONS, "/v1/:mediaType", this::optionsMediaType)
            .map(HttpMethod.GET, "/v1/:mediaType/sample", this::getMediaSample)
            .map(HttpMethod.PUT, "/v1/:mediaType/deleteindex", this::putDeleteIndex)
            .map(HttpMethod.OPTIONS, "/v1/:mediaType/deleteindex", this::optionsDeleteIndex)
            .map(HttpMethod.PUT, "/v1/:mediaType/reindex", this::putReindex)
            .map(HttpMethod.OPTIONS, "/v1/:mediaType/reindex", this::optionsReIndex)
            .map(HttpMethod.GET, "/v1/:mediaType/search", this::search)
            .map(HttpMethod.OPTIONS, "/v1/:mediaType/search", this::optionsSearch)
            .map(HttpMethod.GET, "/v1/:mediaType/:mediaId", this::getMediaById)
            .map(HttpMethod.PATCH, "/v1/:mediaType/:mediaId", this::patchMediaById)
            .map(HttpMethod.DELETE, "/v1/:mediaType/:mediaId", this::deleteMediaById)
            .map(HttpMethod.OPTIONS, "/v1/:mediaType/:mediaId", this::optionsMediaTypeById)
            .map(HttpMethod.GET, "/v1/:mediaType/:mediaId/play", this::getPlayableMediaById)
            .map(HttpMethod.GET, "/v1/:mediaType/:mediaId/ratings", this::getMediaRatings)
            .map(HttpMethod.OPTIONS, "/v1/:mediaType/:mediaId/ratings", this::optionsMediaTypeRatingsById);
    }

    private @NotNull Promisable<HttpResponse> getMedia(@NotNull HttpRequest httpRequest) {
        try {
            return mediaTypeEndpointsController.getMedia(httpRequest);
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
        }
        return error();
    }

    private @NotNull Promisable<HttpResponse> postMedia(@NotNull HttpRequest httpRequest) {
        try {
            return mediaTypeEndpointsController.postMedia(httpRequest);
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
        }
        return error();
    }

    private @NotNull Promisable<HttpResponse> putMedia(@NotNull HttpRequest httpRequest) {
        try {
            return mediaTypeEndpointsController.putMedia(httpRequest);
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
        }
        return error();
    }

    private @NotNull Promisable<HttpResponse> optionsMediaType(@NotNull HttpRequest httpRequest) {
        return HttpResponse.ok200().withHeader(HttpHeaders.ALLOW, HttpHeaderValue.of("GET, POST, PUT"));
    }

    private @NotNull Promisable<HttpResponse> getMediaSample(@NotNull HttpRequest httpRequest) {
        try {
            return mediaTypeEndpointsController.getMediaSample(httpRequest);
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
        }
        return error();
    }

    private @NotNull Promisable<HttpResponse> putDeleteIndex(@NotNull HttpRequest httpRequest) {
        try {
            return mediaTypeEndpointsController.putDeleteIndex(httpRequest);
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
        }
        return error();
    }

    private @NotNull Promisable<HttpResponse> optionsDeleteIndex(@NotNull HttpRequest httpRequest) {
        return HttpResponse.ok200().withHeader(HttpHeaders.ALLOW, HttpHeaderValue.of("PUT"));
    }

    private @NotNull Promisable<HttpResponse> putReindex(@NotNull HttpRequest httpRequest) {
        try {
            return mediaTypeEndpointsController.putReindex(httpRequest);
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
        }
        return error();
    }

    private @NotNull Promisable<HttpResponse> optionsReIndex(@NotNull HttpRequest httpRequest) {
        return HttpResponse.ok200().withHeader(HttpHeaders.ALLOW, HttpHeaderValue.of("PUT"));
    }

    private @NotNull Promisable<HttpResponse> search(@NotNull HttpRequest httpRequest) {
        try {
            return mediaTypeEndpointsController.search(httpRequest);
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
        }
        return error();
    }

    private @NotNull Promisable<HttpResponse> optionsSearch(@NotNull HttpRequest httpRequest) {
        return HttpResponse.ok200().withHeader(HttpHeaders.ALLOW, HttpHeaderValue.of("GET"));
    }

    private @NotNull Promisable<HttpResponse> getMediaById(@NotNull HttpRequest httpRequest) {
        try {
            return mediaTypeEndpointsController.getMediaById(httpRequest);
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
        }
        return error();
    }

    private @NotNull Promisable<HttpResponse> patchMediaById(@NotNull HttpRequest httpRequest) {
        try {
            return mediaTypeEndpointsController.patchMediaById(httpRequest);
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
        }
        return error();
    }

    private @NotNull Promisable<HttpResponse> deleteMediaById(@NotNull HttpRequest httpRequest) {
        try {
            return mediaTypeEndpointsController.deleteMediaById(httpRequest);
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
        }
        return error();
    }

    private @NotNull Promisable<HttpResponse> optionsMediaTypeById(@NotNull HttpRequest httpRequest) {
        return HttpResponse.ok200().withHeader(HttpHeaders.ALLOW, HttpHeaderValue.of("GET, PATCH, DELETE"));
    }

    private @NotNull Promisable<HttpResponse> getMediaRatings(@NotNull HttpRequest httpRequest) {
        try {
            return mediaTypeEndpointsController.getMediaRatings(httpRequest);
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
        }
        return error();
    }

    private @NotNull Promisable<HttpResponse> optionsMediaTypeRatingsById(@NotNull HttpRequest httpRequest) {
        return HttpResponse.ok200().withHeader(HttpHeaders.ALLOW, HttpHeaderValue.of("GET"));
    }

    private Promisable<HttpResponse> getPlayableMediaById(@NotNull HttpRequest httpRequest) {
        try {
            return mediaTypeEndpointsController.getMediaSubEntityById(httpRequest);
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
        }
        return error();
    }
}
