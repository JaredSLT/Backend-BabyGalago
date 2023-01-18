package tech.tresearchgroup.babygalago.view.endpoints.api;

import io.activej.http.*;
import io.activej.inject.annotation.Provides;
import io.activej.promise.Promisable;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import tech.tresearchgroup.babygalago.controller.SettingsController;
import tech.tresearchgroup.babygalago.controller.endpoints.api.RatingEndpointsController;
import tech.tresearchgroup.palila.controller.HttpResponses;

@AllArgsConstructor
public class RatingEndpoints extends HttpResponses {
    private final RatingEndpointsController ratingEndpointsController;
    private final SettingsController settingsController;

    @Provides
    public RoutingServlet servlet() {
        return RoutingServlet.create()
            .map(HttpMethod.GET, "/v1/ratings/:ratingId", this::getRating)
            .map(HttpMethod.PATCH, "/v1/ratings/:ratingId", this::patchRating)
            .map(HttpMethod.DELETE, "/v1/ratings/:ratingId", this::deleteRating)
            .map(HttpMethod.PUT, "/v1/ratings/:ratingId", this::putRating)
            .map(HttpMethod.POST, "/v1/ratings/:ratingId", this::postRating)
            .map(HttpMethod.OPTIONS, "/v1/ratings/:ratingId", this::optionsRatingById);
    }

    private @NotNull Promisable<HttpResponse> getRating(@NotNull HttpRequest httpRequest) {
        try {
            return ratingEndpointsController.getRating(httpRequest);
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
        }
        return error();
    }

    private @NotNull Promisable<HttpResponse> patchRating(@NotNull HttpRequest httpRequest) {
        try {
            return ratingEndpointsController.patchRating(httpRequest);
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
        }
        return error();
    }

    private @NotNull Promisable<HttpResponse> deleteRating(@NotNull HttpRequest httpRequest) {
        try {
            return ratingEndpointsController.deleteRating(httpRequest);
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
        }
        return error();
    }

    private @NotNull Promisable<HttpResponse> putRating(@NotNull HttpRequest httpRequest) {
        try {
            return ratingEndpointsController.putRating(httpRequest);
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
        }
        return error();
    }

    private @NotNull Promisable<HttpResponse> postRating(@NotNull HttpRequest httpRequest) {
        try {
            return ratingEndpointsController.postRating(httpRequest);
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                return error(e);
            }
        }
        return error();
    }

    private @NotNull Promisable<HttpResponse> optionsRatingById(@NotNull HttpRequest httpRequest) {
        return HttpResponse.ok200().withHeader(HttpHeaders.ALLOW, HttpHeaderValue.of("GET, PATCH, DELETE, PUT, POST"));
    }
}
