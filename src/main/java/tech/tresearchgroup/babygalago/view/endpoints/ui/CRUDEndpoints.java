package tech.tresearchgroup.babygalago.view.endpoints.ui;

import io.activej.http.HttpMethod;
import io.activej.http.HttpRequest;
import io.activej.http.HttpResponse;
import io.activej.http.RoutingServlet;
import io.activej.inject.annotation.Provides;
import io.activej.promise.Promisable;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import tech.tresearchgroup.babygalago.controller.SettingsController;
import tech.tresearchgroup.babygalago.controller.endpoints.ui.CRUDEndpointsController;
import tech.tresearchgroup.palila.controller.HttpResponses;
import tech.tresearchgroup.palila.controller.ReflectionMethods;

@AllArgsConstructor
public class CRUDEndpoints extends HttpResponses {
    private final CRUDEndpointsController CRUDEndpointsController;
    private final SettingsController settingsController;

    @Provides
    public RoutingServlet servlet() {
        return RoutingServlet.create()
            .map(HttpMethod.GET, "/add/:mediaType", this::add)
            .map(HttpMethod.POST, "/add/:mediaType", this::postAdd)
            .map(HttpMethod.GET, "/browse/:mediaType", this::browse)
            .map(HttpMethod.GET, "/edit/:mediaType/:id", this::edit)
            .map(HttpMethod.POST, "/edit/:mediaType/:id", this::postEdit)
            .map(HttpMethod.GET, "/new/:mediaType", this::newMediaType)
            .map(HttpMethod.GET, "/popular/:mediaType", this::popular)
            .map(HttpMethod.GET, "/view/:mediaType/:id", this::view)
            .map(HttpMethod.GET, "/delete/:mediaType/:id", this::delete)
            .map(HttpMethod.POST, "/delete/:mediaType/:id", this::postDelete);
    }

    private @NotNull Promisable<HttpResponse> add(@NotNull HttpRequest httpRequest) {
        try {
            String mediaType = httpRequest.getPathParameter("mediaType");
            Class className = ReflectionMethods.findClass(mediaType, settingsController.getEntityPackages());
            if (className != null) {
                return CRUDEndpointsController.add(className, httpRequest);
            }
            return notFound();
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
        }
        return error();
    }

    private Promisable<HttpResponse> postAdd(@NotNull HttpRequest httpRequest) {
        return notImplemented();
    }

    private @NotNull Promisable<HttpResponse> browse(@NotNull HttpRequest httpRequest) {
        try {
            String mediaType = httpRequest.getPathParameter("mediaType");
            Class className = ReflectionMethods.findClass(mediaType, settingsController.getEntityPackages());
            if (className != null) {
                return CRUDEndpointsController.browse(className, httpRequest);
            }
            return notFound();
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
        }
        return error();
    }

    private @NotNull Promisable<HttpResponse> edit(@NotNull HttpRequest httpRequest) {
        try {
            String mediaType = httpRequest.getPathParameter("mediaType");
            Class className = ReflectionMethods.findClass(mediaType, settingsController.getEntityPackages());
            if (className != null) {
                return CRUDEndpointsController.edit(className, httpRequest);
            }
            return notFound();
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
        }
        return error();
    }

    private Promisable<HttpResponse> postEdit(@NotNull HttpRequest httpRequest) {
        return notImplemented();
    }

    private @NotNull Promisable<HttpResponse> newMediaType(@NotNull HttpRequest httpRequest) {
        try {
            String mediaType = httpRequest.getPathParameter("mediaType");
            Class className = ReflectionMethods.findClass(mediaType, settingsController.getEntityPackages());
            if (className != null) {
                return CRUDEndpointsController.newEntities(className, httpRequest);
            }
            return notFound();
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
        }
        return error();
    }

    private @NotNull Promisable<HttpResponse> popular(@NotNull HttpRequest httpRequest) {
        try {
            String mediaType = httpRequest.getPathParameter("mediaType");
            Class className = ReflectionMethods.findClass(mediaType, settingsController.getEntityPackages());
            if (className != null) {
                return CRUDEndpointsController.popularEntities(className, httpRequest);
            }
            return notFound();
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
        }
        return error();
    }

    private @NotNull Promisable<HttpResponse> view(@NotNull HttpRequest httpRequest) {
        try {
            String mediaType = httpRequest.getPathParameter("mediaType");
            Class className = ReflectionMethods.findClass(mediaType, settingsController.getEntityPackages());
            if (className != null) {
                return CRUDEndpointsController.view(className, httpRequest);
            }
            return notFound();
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
        }
        return error();
    }

    private @NotNull Promisable<HttpResponse> delete(@NotNull HttpRequest httpRequest) {
        try {
            String mediaType = httpRequest.getPathParameter("mediaType");
            Class className = ReflectionMethods.findClass(mediaType, settingsController.getEntityPackages());
            if (className != null) {
                return CRUDEndpointsController.delete(className, httpRequest);
            }
            return notFound();
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
        }
        return error();
    }

    private Promisable<HttpResponse> postDelete(@NotNull HttpRequest httpRequest) {
        return notImplemented();
    }
}
