package tech.tresearchgroup.babygalago.controller.endpoints;

import com.google.gson.Gson;
import io.activej.http.HttpRequest;
import io.activej.http.HttpResponse;
import io.activej.promise.Promisable;
import tech.tresearchgroup.babygalago.controller.SettingsController;
import tech.tresearchgroup.babygalago.controller.controllers.ExtendedUserEntityController;
import tech.tresearchgroup.babygalago.controller.controllers.NotificationEntityController;
import tech.tresearchgroup.babygalago.view.pages.EmptySearchPage;
import tech.tresearchgroup.babygalago.view.pages.SearchPage;
import tech.tresearchgroup.cao.controller.GenericCAO;
import tech.tresearchgroup.palila.controller.BasicController;
import tech.tresearchgroup.palila.controller.GenericController;
import tech.tresearchgroup.palila.controller.ReflectionMethods;
import tech.tresearchgroup.palila.model.Card;
import tech.tresearchgroup.palila.model.enums.PermissionGroupEnum;
import tech.tresearchgroup.palila.model.enums.ReturnType;
import tech.tresearchgroup.sao.model.GlobalSearchResultEntity;
import tech.tresearchgroup.sao.model.ResultEntity;
import tech.tresearchgroup.schemas.galago.entities.ExtendedUserEntity;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SearchEndpointsController extends BasicController {
    private final ExtendedUserEntityController extendedUserEntityController;
    private final SettingsController settingsController;
    private final Map<String, GenericController> controllers;
    private final Gson gson;
    private final SearchPage searchPage;
    private final EmptySearchPage emptySearchPage;
    private final NotificationEntityController notificationEntityController;
    private final GenericCAO genericCAO;

    public SearchEndpointsController(ExtendedUserEntityController extendedUserEntityController,
                                     SettingsController settingsController,
                                     Map<String, GenericController> controllers,
                                     Gson gson,
                                     SearchPage searchPage,
                                     EmptySearchPage emptySearchPage,
                                     NotificationEntityController notificationEntityController,
                                     GenericCAO genericCAO) {
        this.extendedUserEntityController = extendedUserEntityController;
        this.settingsController = settingsController;
        this.controllers = controllers;
        this.gson = gson;
        this.searchPage = searchPage;
        this.emptySearchPage = emptySearchPage;
        this.notificationEntityController = notificationEntityController;
        this.genericCAO = genericCAO;
    }

    /**
     * Searches all the media entities
     *
     * @param query       the query
     * @param httpRequest the request
     * @return the global search result entity
     * @throws SQLException              if it fails
     * @throws InvocationTargetException if it fails
     * @throws NoSuchMethodException     if it fails
     * @throws IllegalAccessException    if it fails
     * @throws InstantiationException    if it fails
     * @throws ClassNotFoundException    if it fails
     * @throws IOException               if it fails
     */
    private GlobalSearchResultEntity search(String query, HttpRequest httpRequest) throws SQLException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException, ClassNotFoundException, IOException {
        httpRequest.loadBody();
        List<String> classNames = ReflectionMethods.getClassNames(settingsController.getEntityPackages(), genericCAO);
        List<ResultEntity> searchResultEntities = new LinkedList<>();
        for (String className : classNames) {
            String[] splitClass = className.split("\\.");
            Class theClass = ReflectionMethods.findClass(splitClass[splitClass.length - 1].toLowerCase(), settingsController.getEntityPackages(), genericCAO);
            if (theClass != null) {
                GenericController genericController = getController(theClass, controllers);
                if (genericController != null) {
                    List<Object> objectList = (List<Object>) genericController.search(query, "*", ReturnType.OBJECT, httpRequest);
                    if (objectList != null) {
                        if (objectList.size() > 0) {
                            ResultEntity ResultEntity = new ResultEntity(theClass.getSimpleName(), objectList);
                            searchResultEntities.add(ResultEntity);
                        }
                    }
                }
            }
        }
        if (searchResultEntities.size() > 0) {
            return new GlobalSearchResultEntity(searchResultEntities);
        }
        return null;
    }

    /**
     * Performs a global search and returns JSON
     *
     * @param httpRequest the request
     * @return the search result
     * @throws Exception if it fails
     */
    public Promisable<HttpResponse> searchAPIResponse(HttpRequest httpRequest) throws Exception {
        String query = httpRequest.getQueryParameter("query");
        GlobalSearchResultEntity ResultEntity = search(query, httpRequest);
        if (ResultEntity != null) {
            return ok(gson.toJson(ResultEntity).getBytes());
        }
        return notFound();
    }

    /**
     * Performs a search and renders it in the search page
     *
     * @param httpRequest the request
     * @return the page
     * @throws Exception if it fails
     */
    public Promisable<HttpResponse> searchUIResponse(HttpRequest httpRequest) throws Exception {
        long start = System.currentTimeMillis();
        String query = httpRequest.getPostParameter("query");
        if (query != null) {
            if (query.length() > 0) {
                GlobalSearchResultEntity resultEntity = search(query, httpRequest);
                boolean loggedIn = verifyApiKey(httpRequest);
                ExtendedUserEntity extendedUserEntity = (ExtendedUserEntity) getUser(httpRequest, extendedUserEntityController);
                if (resultEntity != null) {
                    for (ResultEntity searchResult : resultEntity.getSearchResultList()) {
                        List<Card> cards = new LinkedList<>();
                        for (Object object : searchResult.getList()) {
                            GenericController genericController = getController(object.getClass(), controllers);
                            cards.add(genericController.toCard(object, "view"));
                        }
                        searchResult.setList(cards);
                    }
                    if (extendedUserEntity != null) {
                        long timeTaken = (System.currentTimeMillis() - start);
                        return ok(
                            searchPage.render(
                                loggedIn,
                                resultEntity,
                                timeTaken,
                                settingsController.getCardWidth(extendedUserEntity.getUserSettings()),
                                notificationEntityController.getNumberOfUnread(extendedUserEntity),
                                extendedUserEntity.getPermissionGroup(),
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
                } else {
                    return redirect("/search");
                }
            } else {
                return redirect("/search?errorType=length");
            }
        }
        return redirect("/error");
    }

    /**
     * Renders an empty search page (if a search yielded no results)
     *
     * @param httpRequest the request
     * @return the page
     * @throws Exception if it fails
     */
    public Promisable<HttpResponse> searchNotFound(HttpRequest httpRequest) throws Exception {
        long start = System.currentTimeMillis();
        boolean loggedIn = verifyApiKey(httpRequest);
        ExtendedUserEntity extendedUserEntity = (ExtendedUserEntity) getUser(httpRequest, extendedUserEntityController);
        long timeTaken = (System.currentTimeMillis() - start);
        return ok(
            emptySearchPage.render(
                loggedIn,
                timeTaken,
                notificationEntityController.getNumberOfUnread(extendedUserEntity),
                extendedUserEntity.getPermissionGroup(),
                settingsController.getServerName(),
                settingsController.isEnableUpload(),
                settingsController.isMovieLibraryEnable(),
                settingsController.isTvShowLibraryEnable(),
                settingsController.isGameLibraryEnable(),
                settingsController.isMusicLibraryEnable(),
                settingsController.isBookLibraryEnable(),
                genericCAO
            ).getBytes()
        );
    }

    /**
     * Performs a search for a specific media type
     *
     * @param httpRequest the request
     * @return the JSON or 500
     * @throws Exception if it fails
     */
    public Promisable<HttpResponse> searchByType(HttpRequest httpRequest) throws Exception {
        String query = httpRequest.getQueryParameter("query");
        String mediaType = httpRequest.getPathParameter("mediaType");
        if (canAccess(httpRequest, PermissionGroupEnum.USER, extendedUserEntityController)) {
            Class theClass = ReflectionMethods.findClass(mediaType, settingsController.getEntityPackages(), genericCAO);
            if (theClass != null) {
                GenericController genericController = getController(theClass, controllers);
                List data = (List) genericController.search(query, "*", ReturnType.OBJECT, httpRequest);
                if (data != null) {
                    if (data.size() > 0) {
                        return ok(gson.toJson(data).getBytes());
                    }
                }
            }
            return error();
        }
        return unauthorized();
    }
}
