package tech.tresearchgroup.babygalago.controller.endpoints.ui;

import io.activej.http.HttpRequest;
import io.activej.http.HttpResponse;
import io.activej.promise.Promisable;
import jdk.jshell.spi.ExecutionControl;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import tech.tresearchgroup.babygalago.controller.SettingsController;
import tech.tresearchgroup.babygalago.controller.controllers.ExtendedUserEntityController;
import tech.tresearchgroup.babygalago.controller.controllers.NotificationEntityController;
import tech.tresearchgroup.babygalago.view.pages.EntityPage;
import tech.tresearchgroup.babygalago.view.pages.ViewPage;
import tech.tresearchgroup.palila.controller.BasicController;
import tech.tresearchgroup.palila.controller.CompressionController;
import tech.tresearchgroup.palila.controller.GenericController;
import tech.tresearchgroup.palila.controller.ReflectionMethods;
import tech.tresearchgroup.palila.model.Card;
import tech.tresearchgroup.palila.model.enums.PermissionGroupEnum;
import tech.tresearchgroup.palila.model.enums.ReturnType;
import tech.tresearchgroup.schemas.galago.entities.BookEntity;
import tech.tresearchgroup.schemas.galago.entities.ExtendedUserEntity;
import tech.tresearchgroup.schemas.galago.entities.UserSettingsEntity;
import tech.tresearchgroup.schemas.galago.enums.DisplayModeEnum;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@AllArgsConstructor
public class CRUDEndpointsController extends BasicController {
    private final Map<String, GenericController> controllers;

    private final ExtendedUserEntityController extendedUserEntityController;
    private final SettingsController settingsController;

    private final ViewPage viewPage;
    private final EntityPage entityPage;
    private final NotificationEntityController notificationEntityController;

    /**
     * loads the data for and renders the browse page
     *
     * @param httpRequest the request
     * @return the page
     * @throws SQLException              if it fails
     * @throws InvocationTargetException if it fails
     * @throws NoSuchMethodException     if it fails
     * @throws IllegalAccessException    if it fails
     * @throws InstantiationException    if it fails
     */
    public Promisable<HttpResponse> browse(HttpRequest httpRequest) throws SQLException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        try {
            String mediaType = httpRequest.getPathParameter("mediaType");
            Class theClass = ReflectionMethods.findClass(mediaType, settingsController.getEntityPackages());
            if (theClass != null) {
                if (settingsController.isLibraryDisabled(theClass)) {
                    return redirect("/disabled");
                }
                ExtendedUserEntity userEntity = (ExtendedUserEntity) getUser(httpRequest, extendedUserEntityController);
                UserSettingsEntity userSettingsEntity = null;
                if (userEntity != null) {
                    userSettingsEntity = userEntity.getUserSettings();
                }
                boolean ascending = Objects.equals(httpRequest.getQueryParameter("ascending"), "on");
                int page = httpRequest.getQueryParameter("page") != null ? Integer.parseInt(Objects.requireNonNull(httpRequest.getQueryParameter("page"))) : 0;
                int maxResults = settingsController.getMaxBrowseResults(userSettingsEntity);
                GenericController controller = getController(theClass, controllers);
                long maxPage = controller.getTotalPages(maxResults, httpRequest);
                String sortBy = httpRequest.getQueryParameter("sortBy");
                List objects = (List) controller.readOrderByPaginated(maxResults, page, sortBy, ascending, false, ReturnType.OBJECT, httpRequest);
                List<Card> cards = new LinkedList<>();
                if (objects != null) {
                    for (Object object : objects) {
                        cards.add(controller.toCard(object, "browse"));
                    }
                }
                boolean loggedIn = verifyApiKey(httpRequest);
                PermissionGroupEnum permission = PermissionGroupEnum.ALL;
                if(userEntity != null) {
                    permission = userEntity.getPermissionGroup();
                }
                byte[] data = viewPage.render(
                    loggedIn,
                    theClass.getSimpleName(),
                    theClass.getSimpleName().toLowerCase(),
                    "browse",
                    cards,
                    settingsController.getCardWidth(userSettingsEntity),
                    page,
                    maxPage,
                    theClass,
                    ascending,
                    settingsController.getDisplayMode(userSettingsEntity).equals(DisplayModeEnum.POSTER),
                    sortBy,
                    true,
                    notificationEntityController.getNumberOfUnread(userEntity),
                    permission,
                    settingsController.getServerName(),
                    settingsController.isEnableUpload(),
                    settingsController.isMovieLibraryEnable(),
                    settingsController.isTvShowLibraryEnable(),
                    settingsController.isGameLibraryEnable(),
                    settingsController.isMusicLibraryEnable(),
                    settingsController.isBookLibraryEnable()
                ).getBytes();
                return ok(data);
            }
            return redirect("/notfound");
        } catch (IOException | ClassNotFoundException e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
            return redirect("/error");
        }
    }

    /**
     * loads the data for an entity and displays it in the page
     *
     * @param httpRequest the request
     * @return the page
     */
    public Promisable<HttpResponse> view(HttpRequest httpRequest) {
        try {
            String mediaType = httpRequest.getPathParameter("mediaType");
            Class theClass = ReflectionMethods.findClass(mediaType, settingsController.getEntityPackages());
            if (theClass != null) {
                if (settingsController.isLibraryDisabled(theClass)) {
                    return redirect("/disabled");
                }
                long id = Long.parseLong(httpRequest.getPathParameter("id"));
                boolean loggedIn = verifyApiKey(httpRequest);
                ExtendedUserEntity userEntity = (ExtendedUserEntity) getUser(httpRequest, extendedUserEntityController);
                GenericController controller = getController(theClass, controllers);
                Object object = controller.readSecureResponse(id, ReturnType.OBJECT, httpRequest);
                return ok(
                    entityPage.render(
                        ReflectionMethods.toFormObjects(false, object, theClass),
                        loggedIn,
                        false,
                        theClass,
                        id,
                        notificationEntityController.getNumberOfUnread(userEntity),
                        userEntity.getPermissionGroup(),
                        settingsController.getServerName(),
                        settingsController.isEnableUpload(),
                        settingsController.isMovieLibraryEnable(),
                        settingsController.isTvShowLibraryEnable(),
                        settingsController.isGameLibraryEnable(),
                        settingsController.isMusicLibraryEnable(),
                        settingsController.isBookLibraryEnable()
                    ).getBytes());
            }
            return redirect("/notfound");
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
            return redirect("/error");
        }
    }

    /**
     * Deletes an entity and redirects to the browse page for that entity type
     *
     * @param httpRequest the request
     * @return the redirect
     */
    public Promisable<HttpResponse> delete(HttpRequest httpRequest) {
        try {
            String mediaType = httpRequest.getPathParameter("mediaType");
            Class theClass = ReflectionMethods.findClass(mediaType, settingsController.getEntityPackages());
            if (theClass != null) {
                if (settingsController.isLibraryDisabled(theClass)) {
                    return redirect("/disabled");
                }
                long id = Long.parseLong(httpRequest.getPathParameter("id"));
                GenericController controller = getController(theClass, controllers);
                if (controller.delete(id, httpRequest)) {
                    return redirect("/browse/" + theClass.getSimpleName().toLowerCase());
                }
                return redirect("/error");
            }
            return redirect("/notfound");
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
            return redirect("/error");
        }
    }

    /**
     * Edits an entity and displays the entity with the changes
     *
     * @param httpRequest the request
     * @return the page
     */
    public Promisable<HttpResponse> edit(HttpRequest httpRequest) {
        try {
            String mediaType = httpRequest.getPathParameter("mediaType");
            Class theClass = ReflectionMethods.findClass(mediaType, settingsController.getEntityPackages());
            if (theClass != null) {
                if (settingsController.isLibraryDisabled(theClass)) {
                    return redirect("/disabled");
                }
                ExtendedUserEntity userEntity = (ExtendedUserEntity) getUser(httpRequest, extendedUserEntityController);
                long id = Long.parseLong(httpRequest.getPathParameter("id"));
                GenericController controller = getController(theClass, controllers);
                Object object = controller.readSecureResponse(id, ReturnType.OBJECT, httpRequest);
                boolean loggedIn = verifyApiKey(httpRequest);
                return ok(
                    entityPage.render(
                        ReflectionMethods.toFormObjects(true, object, theClass),
                        loggedIn,
                        true,
                        theClass,
                        id,
                        notificationEntityController.getNumberOfUnread(userEntity),
                        userEntity.getPermissionGroup(),
                        settingsController.getServerName(),
                        settingsController.isEnableUpload(),
                        settingsController.isMovieLibraryEnable(),
                        settingsController.isTvShowLibraryEnable(),
                        settingsController.isGameLibraryEnable(),
                        settingsController.isMusicLibraryEnable(),
                        settingsController.isBookLibraryEnable()
                    ).getBytes()
                );
            }
            return notFound();
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
            return error();
        }
    }

    /**
     * Creates a new entity
     *
     * @param theClass    the entity type
     * @param httpRequest the request
     * @return redirects to the view page for the entity
     */
    public Promisable<HttpResponse> postEntity(Class theClass, HttpRequest httpRequest) {
        if (settingsController.isLibraryDisabled(theClass)) {
            return redirect("/disabled");
        }
        int id = Integer.parseInt(httpRequest.getPathParameter("id"));
        /*
        MovieEntity movieEntity = MovieForm.getFromForm(httpRequest);
        if (movieController.update(movieEntity.getId(), movieEntity, httpRequest)) {
            return redirect("/view/movie/" + id);
        }*/
        return redirect("/error");
    }

    /**
     * Adds a new entity and displays it
     *
     * @param httpRequest the request
     * @return the entity page
     */
    public Promisable<HttpResponse> add(HttpRequest httpRequest) {
        try {
            String mediaType = httpRequest.getPathParameter("mediaType");
            Class theClass = ReflectionMethods.findClass(mediaType, settingsController.getEntityPackages());
            if (theClass != null) {
                if (settingsController.isLibraryDisabled(theClass)) {
                    return redirect("/disabled");
                }
                ExtendedUserEntity userEntity = (ExtendedUserEntity) getUser(httpRequest, extendedUserEntityController);
                boolean loggedIn = verifyApiKey(httpRequest);
                byte[] data = entityPage.render(
                    ReflectionMethods.toFormObjects(true, null, theClass),
                    loggedIn,
                    true,
                    theClass,
                    0L,
                    notificationEntityController.getNumberOfUnread(userEntity),
                    userEntity.getPermissionGroup(),
                    settingsController.getServerName(),
                    settingsController.isEnableUpload(),
                    settingsController.isMovieLibraryEnable(),
                    settingsController.isTvShowLibraryEnable(),
                    settingsController.isGameLibraryEnable(),
                    settingsController.isMusicLibraryEnable(),
                    settingsController.isBookLibraryEnable()
                ).getBytes();
                byte[] compressed = CompressionController.compress(data);
                return okResponseCompressed(compressed);
            }
            return redirect("/notfound");
        } catch (IOException | SQLException | InstantiationException | IllegalAccessException |
                 InvocationTargetException | ClassNotFoundException e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
            return redirect("/error");
        } catch (ExecutionControl.NotImplementedException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Displays a list of newly created entities for the specified entity type
     *
     * @param httpRequest the request
     * @return the page
     */
    public Promisable<HttpResponse> newEntities(HttpRequest httpRequest) {
        try {
            String mediaType = httpRequest.getPathParameter("mediaType");
            Class theClass = ReflectionMethods.findClass(mediaType, settingsController.getEntityPackages());
            if (theClass != null) {
                if (settingsController.isLibraryDisabled(theClass)) {
                    return redirect("/disabled");
                }
                ExtendedUserEntity userEntity = (ExtendedUserEntity) getUser(httpRequest, extendedUserEntityController);
                UserSettingsEntity userSettingsEntity = userEntity.getUserSettings();
                int page = httpRequest.getQueryParameter("page") != null ? Integer.parseInt(Objects.requireNonNull(httpRequest.getQueryParameter("page"))) : 0;
                int maxResults = settingsController.getMaxBrowseResults(userSettingsEntity);
                GenericController controller = getController(theClass, controllers);
                long maxPage = controller.getTotalPages(settingsController.getMaxBrowseResults(userSettingsEntity), httpRequest);
                List objects = (List) controller.readOrderByPaginated(maxResults, page, "id", false, false, ReturnType.OBJECT, httpRequest);
                LinkedList<Card> cards = new LinkedList<>();
                if (objects != null) {
                    for (Object object : objects) {
                        cards.add(controller.toCard(object, "browse"));
                    }
                }
                boolean loggedIn = verifyApiKey(httpRequest);
                String sortBy = httpRequest.getQueryParameter("sortBy");
                String lowerCaseClassName = theClass.getSimpleName().toLowerCase();
                byte[] data = viewPage.render(
                    loggedIn,
                    "New " + lowerCaseClassName + ":",
                    lowerCaseClassName,
                    "new",
                    cards,
                    settingsController.getCardWidth(userSettingsEntity),
                    page,
                    maxPage,
                    theClass,
                    false,
                    settingsController.getDisplayMode(userSettingsEntity).equals(DisplayModeEnum.POSTER),
                    sortBy,
                    false,
                    notificationEntityController.getNumberOfUnread(userEntity),
                    userEntity.getPermissionGroup(),
                    settingsController.getServerName(),
                    settingsController.isEnableUpload(),
                    settingsController.isMovieLibraryEnable(),
                    settingsController.isTvShowLibraryEnable(),
                    settingsController.isGameLibraryEnable(),
                    settingsController.isMusicLibraryEnable(),
                    settingsController.isBookLibraryEnable()
                ).getBytes();
                byte[] compressed = CompressionController.compress(data);
                return okResponseCompressed(compressed);
            }
            return redirect("/notfound");
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
            return redirect("/error");
        }
    }

    /**
     * Creates a list of popular entities
     *
     * @param httpRequest the request
     * @return the page
     */
    public Promisable<HttpResponse> popularEntities(HttpRequest httpRequest) {
        try {
            String mediaType = httpRequest.getPathParameter("mediaType");
            Class theClass = ReflectionMethods.findClass(mediaType, settingsController.getEntityPackages());
            if (theClass != null) {
                if (settingsController.isLibraryDisabled(theClass)) {
                    return redirect("/disabled");
                }
                ExtendedUserEntity userEntity = (ExtendedUserEntity) getUser(httpRequest, extendedUserEntityController);
                UserSettingsEntity userSettingsEntity = userEntity.getUserSettings();
                int page =
                    httpRequest.getQueryParameter("page") != null ?
                        Integer.parseInt(Objects.requireNonNull(httpRequest.getQueryParameter("page"))) :
                        0;
                int maxResults = settingsController.getMaxBrowseResults(userSettingsEntity);
                GenericController controller = getController(theClass, controllers);
                long maxPage = controller.getTotalPages(
                    settingsController.getMaxBrowseResults(userSettingsEntity),
                    httpRequest
                );
                List objects = (List) controller.readOrderByPaginated(
                    maxResults,
                    page,
                    "views",
                    false,
                    false,
                    ReturnType.OBJECT,
                    httpRequest
                );
                LinkedList<Card> cards = new LinkedList<>();
                if (objects != null) {
                    for (Object object : objects) {
                        cards.add(controller.toCard(object, "browse"));
                    }
                }
                boolean loggedIn = verifyApiKey(httpRequest);
                String sortBy = httpRequest.getQueryParameter("sortBy");
                String lowerCaseClassName = theClass.getSimpleName().toLowerCase();
                byte[] data = viewPage.render(
                    loggedIn,
                    "Popular " + lowerCaseClassName + ":",
                    lowerCaseClassName,
                    "popular",
                    cards,
                    settingsController.getCardWidth(userSettingsEntity),
                    page,
                    maxPage,
                    BookEntity.class,
                    false,
                    settingsController.getDisplayMode(userSettingsEntity).equals(DisplayModeEnum.POSTER),
                    sortBy,
                    false,
                    notificationEntityController.getNumberOfUnread(userEntity),
                    userEntity.getPermissionGroup(),
                    settingsController.getServerName(),
                    settingsController.isEnableUpload(),
                    settingsController.isMovieLibraryEnable(),
                    settingsController.isTvShowLibraryEnable(),
                    settingsController.isGameLibraryEnable(),
                    settingsController.isMusicLibraryEnable(),
                    settingsController.isBookLibraryEnable()
                ).getBytes();
                byte[] compressed = CompressionController.compress(data);
                return okResponseCompressed(compressed);
            }
            return redirect("/notfound");
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
            return redirect("/error");
        }
    }

    /**
     * Loads the object from the form and creates it
     *
     * @param httpRequest the request
     * @return a redirect to browsing that entity type
     * @throws Exception if it fails
     */
    public Promisable<HttpResponse> createObjectFromForm(HttpRequest httpRequest) throws Exception {
        String className = httpRequest.getPostParameter("mediaClassName");
        if (className != null) {
            Class theClass = ReflectionMethods.findClass(className, settingsController.getEntityPackages());
            Object object = ReflectionMethods.getObjectFromForm(theClass, httpRequest);
            GenericController controller = getController(theClass, controllers);
            if (controller.createSecureResponse(object, ReturnType.OBJECT, httpRequest) != null) {
                return redirect("/browse/" + className);
            }
            return redirect("/error");
        }
        return redirect("/notfound");
    }

    /**
     * Updates an entity from the data within the form
     *
     * @param httpRequest the request
     * @return a redirect to the browse page for that entity type
     */
    public Promisable<HttpResponse> updateObjectFromForm(HttpRequest httpRequest) {
        try {
            String className = httpRequest.getPostParameter("mediaClassName");
            if (className != null) {
                Class theClass = ReflectionMethods.findClass(className, settingsController.getEntityPackages());
                if (theClass != null) {
                    String parameter = httpRequest.getPostParameter("id");
                    if (parameter != null) {
                        long id = Long.parseLong(parameter);
                        Object object = ReflectionMethods.getObjectFromForm(theClass, httpRequest);
                        GenericController controller = getController(theClass, controllers);
                        if (controller.update(id, object, httpRequest)) {
                            return redirect("/browse/" + className);
                        } else {
                            return redirect("/error");
                        }
                    }
                }
            }
            return redirect("/notfound");
        } catch (Exception e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
            return redirect("/error");
        }
    }

    /**
     * Delets an entity using the POST method
     *
     * @param httpRequest the request
     * @return 200 if ok, 404 if entity not found, 500 if it fails
     */
    public @NotNull Promisable<HttpResponse> postDelete(HttpRequest httpRequest) {
        return redirect("/underconstruction");
    }

    /**
     * Uploads a file using the POST method
     *
     * @param httpRequest the request
     * @return the upload page
     * @throws ClassNotFoundException if the media type doesn't exist
     */
    public @NotNull Promisable<HttpResponse> postUpload(HttpRequest httpRequest) throws ClassNotFoundException {
        String varName = httpRequest.getPathParameter("varName");
        String mediaType = httpRequest.getPathParameter("mediaType");
        return handleUpload(
            mediaType,
            varName,
            settingsController.getEntityPackages(),
            settingsController.getBaseLibraryPath(),
            controllers,
            httpRequest
        );
    }
}
