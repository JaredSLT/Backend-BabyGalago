package tech.tresearchgroup.babygalago.controller.endpoints.api;

import io.activej.http.HttpHeaderValue;
import io.activej.http.HttpHeaders;
import io.activej.http.HttpRequest;
import io.activej.http.HttpResponse;
import io.activej.promise.Promisable;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import tech.tresearchgroup.babygalago.controller.SettingsController;
import tech.tresearchgroup.palila.controller.BasicController;
import tech.tresearchgroup.palila.controller.GenericController;
import tech.tresearchgroup.palila.controller.ReflectionMethods;
import tech.tresearchgroup.palila.model.entities.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.Map;
import java.util.Objects;

@AllArgsConstructor
public class MediaTypeEndpointsController extends BasicController {
    private final Map<String, GenericController> controllers;
    private final SettingsController settingsController;

    public Promisable<HttpResponse> getMediaRatings(HttpRequest httpRequest) {
        return notImplemented();
    }

    public @NotNull Promisable<HttpResponse> deleteMediaById(HttpRequest httpRequest) throws Exception {
        long mediaId = Long.parseLong(httpRequest.getPathParameter("mediaId"));
        String mediaType = httpRequest.getPathParameter("mediaType");
        Class className = ReflectionMethods.findClass(mediaType, settingsController.getEntityPackages());
        if (className == null) {
            System.out.println("Class null");
            return error();
        }
        GenericController genericController = getController(className, controllers);
        if (genericController == null) {
            System.out.println("Controller null");
            return error();
        }
        if (genericController.delete(mediaId, httpRequest)) {
            return ok();
        }
        return error();
    }

    public @NotNull Promisable<HttpResponse> patchMediaById(HttpRequest httpRequest) throws Exception {
        String data = httpRequest.loadBody().getResult().asString(Charset.defaultCharset());
        long id = Long.parseLong(httpRequest.getPathParameter("mediaId"));
        String mediaType = httpRequest.getPathParameter("mediaType");
        Class className = ReflectionMethods.findClass(mediaType, settingsController.getEntityPackages());
        GenericController genericController = getController(className, controllers);
        if (genericController != null) {
            return ok(genericController.update(id, data, httpRequest));
        }
        return error();
    }

    public @NotNull Promisable<HttpResponse> getMediaById(HttpRequest httpRequest) throws SQLException, IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        long mediaId = Long.parseLong(httpRequest.getPathParameter("mediaId"));
        String mediaType = httpRequest.getPathParameter("mediaType");
        Class className = ReflectionMethods.findClass(mediaType, settingsController.getEntityPackages());
        if (className == null) {
            return notFound();
        }
        GenericController genericController = getController(className, controllers);
        if (genericController != null) {
            byte[] data = genericController.readSecureAPIResponse(mediaId, httpRequest);
            if (data == null) {
                return notFound();
            }
            return okResponseCompressed(data);
        }
        return error();
    }

    public @NotNull Promisable<HttpResponse> search(HttpRequest httpRequest) throws SQLException, IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        String query = httpRequest.getQueryParameter("q");
        String mediaType = httpRequest.getPathParameter("mediaType");
        Class theClass = ReflectionMethods.findClass(mediaType, settingsController.getEntityPackages());
        GenericController genericController = getController(theClass, controllers);
        if (genericController != null) {
            return okResponseCompressed(genericController.searchAPIResponse(query, "*", httpRequest));
        }
        return error();
    }

    public @NotNull HttpResponse putReindex(HttpRequest httpRequest) throws SQLException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        String mediaType = httpRequest.getPathParameter("mediaType");
        Class theClass = ReflectionMethods.findClass(mediaType, settingsController.getEntityPackages());
        GenericController genericController = getController(theClass, controllers);
        if (genericController != null) {
            return ok(genericController.reindex(httpRequest));
        }
        return error();
    }

    public Promisable<HttpResponse> putDeleteIndex(HttpRequest httpRequest) throws Exception {
        String mediaType = httpRequest.getPathParameter("mediaType");
        Class theClass = ReflectionMethods.findClass(mediaType, settingsController.getEntityPackages());
        GenericController genericController = getController(theClass, controllers);
        if (genericController != null) {
            return genericController.deleteAllIndexes(httpRequest);
        }
        return error();
    }

    public Promisable<HttpResponse> getMediaSample(HttpRequest httpRequest) throws SQLException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException, IOException, ClassNotFoundException {
        String mediaType = httpRequest.getPathParameter("mediaType");
        Class theClass = ReflectionMethods.findClass(mediaType, settingsController.getEntityPackages());
        GenericController genericController = getController(theClass, controllers);
        if (genericController != null) {
            byte[] data = genericController.getSample(httpRequest);
            if (data != null) {
                return ok();
            }
            return notFound();
        }
        return error();
    }

    public Promisable<HttpResponse> putMedia(HttpRequest httpRequest) throws Exception {
        String data = httpRequest.loadBody().getResult().asString(Charset.defaultCharset());
        String mediaType = httpRequest.getPathParameter("mediaType");
        Class theClass = ReflectionMethods.findClass(mediaType, settingsController.getEntityPackages());
        GenericController genericController = getController(theClass, controllers);
        if (genericController != null) {
            return ok(genericController.createSecureResponse(data, httpRequest) != null);
        }
        return error();
    }

    public Promisable<HttpResponse> postMedia(HttpRequest httpRequest) throws Exception {
        String data = httpRequest.loadBody().getResult().asString(Charset.defaultCharset());
        String mediaType = httpRequest.getPathParameter("mediaType");
        Class theClass = ReflectionMethods.findClass(mediaType, settingsController.getEntityPackages());
        GenericController genericController = getController(theClass, controllers);
        if (genericController != null) {
            return ok(genericController.createSecureAPIResponse(data, httpRequest));
        }
        return error();
    }

    public Promisable<HttpResponse> getMedia(HttpRequest httpRequest) throws SQLException, IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        int page = httpRequest.getQueryParameter("page") != null ? Integer.parseInt(Objects.requireNonNull(httpRequest.getQueryParameter("page"))) : 0;
        int pageSize = httpRequest.getQueryParameter("pageSize") != null ? Integer.parseInt(Objects.requireNonNull(httpRequest.getQueryParameter("pageSize"))) : 0;
        String mediaType = httpRequest.getPathParameter("mediaType");
        Class theClass = ReflectionMethods.findClass(mediaType, settingsController.getEntityPackages());
        if (theClass != null) {
            GenericController genericController = getController(theClass, controllers);
            byte[] data = genericController.readPaginatedAPIResponse(page, pageSize, true, httpRequest);
            if (data != null) {
                return okResponseCompressed(data);
            }
            return notFound();
        }
        return notFound();
    }

    public Promisable<HttpResponse> getMediaSubEntityById(@NotNull HttpRequest httpRequest) throws ClassNotFoundException, SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException {
        String mediaType = httpRequest.getPathParameter("mediaType");
        long mediaId = Long.parseLong(httpRequest.getPathParameter("mediaId"));
        Class theClass = ReflectionMethods.findClass(mediaType, settingsController.getEntityPackages());
        if (theClass != null) {
            GenericController controller = getController(theClass, controllers);
            if (AudioFileEntity.class.equals(theClass)) {
                System.out.println("AUDIO");
            } else if (BookFileEntity.class.equals(theClass)) {
                System.out.println("BOOK");
            } else if (FileEntity.class.equals(theClass)) {
                System.out.println("FILE");
            } else if (GameFileEntity.class.equals(theClass)) {
                System.out.println("GAME");
            } else if (ImageFileEntity.class.equals(theClass)) {
                System.out.println("IMAGE");
            } else if (VideoFileEntity.class.equals(theClass)) {
                VideoFileEntity videoFileEntity = (VideoFileEntity) controller.readSecureResponse(mediaId, httpRequest);
                return handle206(Path.of(videoFileEntity.getPath()), httpRequest);
            }
        }
        return error();
    }

    public Promisable<HttpResponse> optionsMediaSubEntityById(@NotNull HttpRequest httpRequest) {
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
