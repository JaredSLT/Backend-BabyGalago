package tech.tresearchgroup.babygalago.controller.endpoints.api;

import io.activej.http.HttpHeaderValue;
import io.activej.http.HttpHeaders;
import io.activej.http.HttpRequest;
import io.activej.http.HttpResponse;
import io.activej.promise.Promisable;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.tresearchgroup.babygalago.controller.SettingsController;
import tech.tresearchgroup.babygalago.controller.controllers.ExtendedUserEntityController;
import tech.tresearchgroup.cao.controller.GenericCAO;
import tech.tresearchgroup.palila.controller.BasicController;
import tech.tresearchgroup.palila.controller.GenericController;
import tech.tresearchgroup.palila.controller.ReflectionMethods;
import tech.tresearchgroup.palila.model.entities.*;
import tech.tresearchgroup.palila.model.enums.ReturnType;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.Map;
import java.util.Objects;

@AllArgsConstructor
public class MediaTypeEndpointsController extends BasicController {
    private static final Logger logger = LoggerFactory.getLogger(MediaTypeEndpointsController.class);
    private final Map<String, GenericController> controllers;
    private final SettingsController settingsController;
    private final ExtendedUserEntityController extendedUserEntityController;
    private final GenericCAO genericCAO;

    /**
     * Gets the ratings for the media type
     *
     * @param httpRequest the request
     * @return the response page
     */
    public Promisable<HttpResponse> getMediaRatings(HttpRequest httpRequest) {
        return notImplemented();
    }

    /**
     * Deletes a media entity by its id
     *
     * @param httpRequest the request
     * @return the response page
     * @throws Exception if it fails
     */
    public @NotNull Promisable<HttpResponse> deleteMediaById(HttpRequest httpRequest) throws Exception {
        long mediaId = Long.parseLong(httpRequest.getPathParameter("mediaId"));
        String mediaType = httpRequest.getPathParameter("mediaType");
        Class className = ReflectionMethods.findClass(mediaType, settingsController.getEntityPackages(), genericCAO);
        if (className == null) {
            logger.info("Class null");
            return error();
        }
        GenericController genericController = getController(className, controllers);
        if (genericController == null) {
            logger.info("Controller null");
            return error();
        }
        if (genericController.delete(mediaId, httpRequest)) {
            return ok();
        }
        return error();
    }

    /**
     * Patches a media entity by its id
     *
     * @param httpRequest the request
     * @return the response page
     * @throws Exception if it fails
     */
    public @NotNull Promisable<HttpResponse> patchMediaById(HttpRequest httpRequest) throws Exception {
        String data = httpRequest.loadBody().getResult().asString(Charset.defaultCharset());
        long id = Long.parseLong(httpRequest.getPathParameter("mediaId"));
        String mediaType = httpRequest.getPathParameter("mediaType");
        Class className = ReflectionMethods.findClass(mediaType, settingsController.getEntityPackages(), genericCAO);
        GenericController genericController = getController(className, controllers);
        if (genericController != null) {
            return ok(genericController.update(id, data, httpRequest));
        }
        return error();
    }

    /**
     * Gets a media entity by its id
     *
     * @param httpRequest the request
     * @return the response page
     * @throws SQLException              if its fails
     * @throws IOException               if its fails
     * @throws InvocationTargetException if its fails
     * @throws NoSuchMethodException     if its fails
     * @throws InstantiationException    if its fails
     * @throws IllegalAccessException    if its fails
     * @throws ClassNotFoundException    if its fails
     */
    public @NotNull Promisable<HttpResponse> getMediaById(HttpRequest httpRequest) throws SQLException, IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        long mediaId = Long.parseLong(httpRequest.getPathParameter("mediaId"));
        String mediaType = httpRequest.getPathParameter("mediaType");
        Class className = ReflectionMethods.findClass(mediaType, settingsController.getEntityPackages(), genericCAO);
        if (className == null) {
            return notFound();
        }
        GenericController genericController = getController(className, controllers);
        if (genericController != null) {
            byte[] data = (byte[]) genericController.readSecureResponse(mediaId, ReturnType.JSON, httpRequest);
            if (data == null) {
                return notFound();
            }
            return okResponseCompressed(data);
        }
        return error();
    }

    /**
     * Makes the server reindex all entities with the search server for a media type
     *
     * @param httpRequest the request
     * @return the response page
     * @throws SQLException              if its fails
     * @throws InvocationTargetException if its fails
     * @throws NoSuchMethodException     if its fails
     * @throws IllegalAccessException    if its fails
     * @throws InstantiationException    if its fails
     * @throws ClassNotFoundException    if its fails
     * @throws IOException               if its fails
     */
    public @NotNull Promisable<HttpResponse> putReindex(HttpRequest httpRequest) throws SQLException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException, ClassNotFoundException, IOException {
        String mediaType = httpRequest.getPathParameter("mediaType");
        Class theClass = ReflectionMethods.findClass(mediaType, settingsController.getEntityPackages(), genericCAO);
        GenericController genericController = getController(theClass, controllers);
        if (genericController != null) {
            return ok(genericController.reindex(httpRequest));
        }
        return error();
    }

    /**
     * Deletes an index for a media type
     *
     * @param httpRequest the request
     * @return the page response
     * @throws Exception if its fails
     */
    public Promisable<HttpResponse> putDeleteIndex(HttpRequest httpRequest) throws Exception {
        String mediaType = httpRequest.getPathParameter("mediaType");
        Class theClass = ReflectionMethods.findClass(mediaType, settingsController.getEntityPackages(), genericCAO);
        GenericController genericController = getController(theClass, controllers);
        if (genericController != null) {
            return genericController.deleteAllIndexes(httpRequest);
        }
        return error();
    }

    /**
     * Gets a sample of the media entity
     *
     * @param httpRequest the request
     * @return the page response
     * @throws SQLException              if its fails
     * @throws InvocationTargetException if its fails
     * @throws NoSuchMethodException     if its fails
     * @throws IllegalAccessException    if its fails
     * @throws InstantiationException    if its fails
     * @throws IOException               if its fails
     * @throws ClassNotFoundException    if its fails
     */
    public Promisable<HttpResponse> getMediaSample(HttpRequest httpRequest) throws SQLException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException, IOException, ClassNotFoundException {
        String mediaType = httpRequest.getPathParameter("mediaType");
        Class theClass = ReflectionMethods.findClass(mediaType, settingsController.getEntityPackages(), genericCAO);
        if (theClass != null) {
            GenericController genericController = getController(theClass, controllers);
            if (genericController != null) {
                byte[] data = genericController.getSample(httpRequest);
                if (data != null) {
                    return okResponseCompressed(genericController.getSample(httpRequest));
                }
                return notFound();
            }
            if (mediaType.equals("extendeduserentity")) {
                return okResponseCompressed(extendedUserEntityController.getSample(httpRequest));
            }
        }
        return error();
    }

    /**
     * Creates a media entity
     *
     * @param httpRequest the request
     * @return the page response
     * @throws Exception if its fails
     */
    public Promisable<HttpResponse> putMedia(HttpRequest httpRequest) throws Exception {
        String data = httpRequest.loadBody().getResult().asString(Charset.defaultCharset());
        String mediaType = httpRequest.getPathParameter("mediaType");
        Class theClass = ReflectionMethods.findClass(mediaType, settingsController.getEntityPackages(), genericCAO);
        GenericController genericController = getController(theClass, controllers);
        if (genericController != null) {
            return ok(genericController.createSecureResponse(data, ReturnType.JSON, httpRequest) != null);
        }
        return error();
    }

    /**
     * Creates a media entity
     *
     * @param httpRequest the request
     * @return the page response
     * @throws Exception if its fails
     */
    public Promisable<HttpResponse> postMedia(HttpRequest httpRequest) throws Exception {
        String data = httpRequest.loadBody().getResult().asString(Charset.defaultCharset());
        String mediaType = httpRequest.getPathParameter("mediaType");
        Class theClass = ReflectionMethods.findClass(mediaType, settingsController.getEntityPackages(), genericCAO);
        GenericController genericController = getController(theClass, controllers);
        if (genericController != null) {
            return ok((byte[]) genericController.createSecureResponse(data, ReturnType.JSON, httpRequest));
        }
        return error();
    }

    /**
     * Gets a list of media entities using pagination
     *
     * @param httpRequest the request
     * @return the page response
     * @throws SQLException              if its fails
     * @throws IOException               if its fails
     * @throws InvocationTargetException if its fails
     * @throws NoSuchMethodException     if its fails
     * @throws IllegalAccessException    if its fails
     * @throws InstantiationException    if its fails
     * @throws ClassNotFoundException    if its fails
     */
    public Promisable<HttpResponse> getMedia(HttpRequest httpRequest) throws SQLException, IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        int page = httpRequest.getQueryParameter("page") != null ? Integer.parseInt(Objects.requireNonNull(httpRequest.getQueryParameter("page"))) : 0;
        int pageSize = httpRequest.getQueryParameter("pageSize") != null ? Integer.parseInt(Objects.requireNonNull(httpRequest.getQueryParameter("pageSize"))) : 0;
        String mediaType = httpRequest.getPathParameter("mediaType");
        Class theClass = ReflectionMethods.findClass(mediaType, settingsController.getEntityPackages(), genericCAO);
        if (theClass != null) {
            GenericController genericController = getController(theClass, controllers);
            byte[] data = (byte[]) genericController.readPaginatedResponse(page, pageSize, true, ReturnType.JSON, httpRequest);
            if (data != null) {
                return okResponseCompressed(data);
            }
            return notFound();
        }
        return notFound();
    }

    /**
     * Gets the sub entity of a media entity
     *
     * @param httpRequest the request
     * @return the page response
     * @throws ClassNotFoundException    if its fails
     * @throws SQLException              if its fails
     * @throws InvocationTargetException if its fails
     * @throws NoSuchMethodException     if its fails
     * @throws InstantiationException    if its fails
     * @throws IllegalAccessException    if its fails
     * @throws IOException               if its fails
     */
    public Promisable<HttpResponse> getMediaSubEntityById(@NotNull HttpRequest httpRequest) throws ClassNotFoundException, SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException {
        String mediaType = httpRequest.getPathParameter("mediaType");
        long mediaId = Long.parseLong(httpRequest.getPathParameter("mediaId"));
        Class theClass = ReflectionMethods.findClass(mediaType, settingsController.getEntityPackages(), genericCAO);
        if (theClass != null) {
            GenericController controller = getController(theClass, controllers);
            if (AudioFileEntity.class.equals(theClass)) {
                logger.info("AUDIO");
            } else if (BookFileEntity.class.equals(theClass)) {
                logger.info("BOOK");
            } else if (FileEntity.class.equals(theClass)) {
                logger.info("FILE");
            } else if (GameFileEntity.class.equals(theClass)) {
                logger.info("GAME");
            } else if (ImageFileEntity.class.equals(theClass)) {
                logger.info("IMAGE");
            } else if (VideoFileEntity.class.equals(theClass)) {
                VideoFileEntity videoFileEntity = (VideoFileEntity) controller.readSecureResponse(mediaId, ReturnType.OBJECT, httpRequest);
                logger.info(videoFileEntity.getPath());
                return handle206(Path.of(videoFileEntity.getPath()), settingsController.getChunk(), httpRequest);
            }
        }
        return error();
    }

    /**
     * Gets which methods are available for this endpoint
     *
     * @param httpRequest the request
     * @return the response page
     */
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

    /**
     * Gets which methods are available for this endpoint
     *
     * @param httpRequest the request
     * @return the response page
     */
    @NotNull
    public Promisable<HttpResponse> optionsMediaType(@NotNull HttpRequest httpRequest) {
        return HttpResponse.ok200().withHeader(HttpHeaders.ALLOW, HttpHeaderValue.of("GET, POST, PUT"));
    }

    /**
     * Gets which methods are available for this endpoint
     *
     * @param httpRequest the request
     * @return the response page
     */
    @NotNull
    public Promisable<HttpResponse> optionsDeleteIndex(@NotNull HttpRequest httpRequest) {
        return HttpResponse.ok200().withHeader(HttpHeaders.ALLOW, HttpHeaderValue.of("PUT"));
    }

    /**
     * Gets which methods are available for this endpoint
     *
     * @param httpRequest the request
     * @return the response page
     */
    @NotNull
    public Promisable<HttpResponse> optionsReIndex(@NotNull HttpRequest httpRequest) {
        return HttpResponse.ok200().withHeader(HttpHeaders.ALLOW, HttpHeaderValue.of("PUT"));
    }

    /**
     * Gets which methods are available for this endpoint
     *
     * @param httpRequest the request
     * @return the response page
     */
    @NotNull
    public Promisable<HttpResponse> optionsSearch(@NotNull HttpRequest httpRequest) {
        return HttpResponse.ok200().withHeader(HttpHeaders.ALLOW, HttpHeaderValue.of("GET"));
    }

    /**
     * Gets which methods are available for this endpoint
     *
     * @param httpRequest the request
     * @return the response page
     */
    @NotNull
    public Promisable<HttpResponse> optionsMediaTypeById(@NotNull HttpRequest httpRequest) {
        return HttpResponse.ok200().withHeader(HttpHeaders.ALLOW, HttpHeaderValue.of("GET, PATCH, DELETE"));
    }

    /**
     * Gets which methods are available for this endpoint
     *
     * @param httpRequest the request
     * @return the response page
     */
    @NotNull
    public Promisable<HttpResponse> optionsMediaTypeRatingsById(@NotNull HttpRequest httpRequest) {
        return HttpResponse.ok200().withHeader(HttpHeaders.ALLOW, HttpHeaderValue.of("GET"));
    }
}
