package tech.tresearchgroup.babygalago.view.endpoints;

import io.activej.http.HttpMethod;
import io.activej.http.HttpRequest;
import io.activej.http.HttpResponse;
import io.activej.http.RoutingServlet;
import io.activej.inject.annotation.Provides;
import io.activej.inject.module.AbstractModule;
import io.activej.promise.Promisable;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import tech.tresearchgroup.babygalago.controller.SettingsController;
import tech.tresearchgroup.babygalago.controller.endpoints.AssetEndpointController;

@AllArgsConstructor
public class AssetEndpoint extends AbstractModule {
    private final AssetEndpointController assetEndpointController;
    private final SettingsController settingsController;

    @Provides
    public RoutingServlet servlet() {
        return RoutingServlet.create()
            .map(HttpMethod.GET, "/assets/:file", this::getAsset)
            .map(HttpMethod.GET, "/assets/gen/styles.min.css", this::getCombinedCSS)
            .map(HttpMethod.GET, "/assets/webfonts/:file", this::getAsset);
    }

    private @NotNull Promisable<HttpResponse> getAsset(@NotNull HttpRequest httpRequest) {
        try {
            return assetEndpointController.getAsset(httpRequest);
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
        }
        return HttpResponse.ofCode(500);
    }

    private @NotNull Promisable<HttpResponse> getCombinedCSS(@NotNull HttpRequest httpRequest) {
        try {
            return assetEndpointController.getCombinedCSS();
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
        }
        return HttpResponse.ofCode(500);
    }
}
