package tech.tresearchgroup.babygalago.controller.endpoints.ui;

import io.activej.http.HttpRequest;
import io.activej.http.HttpResponse;
import io.activej.promise.Promisable;
import j2html.tags.DomContent;
import jdk.jshell.spi.ExecutionControl;
import lombok.AllArgsConstructor;
import tech.tresearchgroup.babygalago.controller.CardConverter;
import tech.tresearchgroup.babygalago.controller.SettingsController;
import tech.tresearchgroup.babygalago.controller.controllers.NotificationEntityController;
import tech.tresearchgroup.babygalago.controller.controllers.QueueEntityController;
import tech.tresearchgroup.babygalago.controller.controllers.UserEntityController;
import tech.tresearchgroup.babygalago.view.components.HeadComponent;
import tech.tresearchgroup.babygalago.view.components.SideBarComponent;
import tech.tresearchgroup.babygalago.view.components.TopBarComponent;
import tech.tresearchgroup.babygalago.view.pages.ViewPage;
import tech.tresearchgroup.palila.controller.BasicController;
import tech.tresearchgroup.palila.controller.CompressionController;
import tech.tresearchgroup.palila.controller.GenericController;
import tech.tresearchgroup.palila.model.Card;
import tech.tresearchgroup.schemas.galago.entities.BookEntity;
import tech.tresearchgroup.schemas.galago.entities.ExtendedUserEntity;
import tech.tresearchgroup.schemas.galago.entities.UserSettingsEntity;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static j2html.TagCreator.*;

@AllArgsConstructor
public class CRUDEndpointsController extends BasicController {
    private final Map<String, GenericController> controllers;

    private final UserEntityController userEntityController;
    private final SettingsController settingsController;
    private final NotificationEntityController notificationEntityController;

    private final ViewPage viewPage;

    public HttpResponse browse(Class theClass, HttpRequest httpRequest) throws IOException, SQLException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        if (settingsController.isLibraryDisabled(theClass)) {
            return redirect("/disabled");
        }
        ExtendedUserEntity userEntity = (ExtendedUserEntity) getUser(httpRequest, userEntityController);
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
        List objects = controller.readOrderByPaginated(maxResults, page, sortBy, ascending, false, httpRequest);
        List<Card> cards = new LinkedList<>();
        if (objects != null) {
            cards = CardConverter.convert(objects, "browse", theClass);
        }
        boolean loggedIn = verifyApiKey(httpRequest);
        byte[] data = viewPage.render(loggedIn, theClass.getSimpleName(), theClass.getSimpleName().toLowerCase(), "browse", cards, settingsController.getCardWidth(userSettingsEntity), page, maxPage, userEntity, theClass, ascending, sortBy, true);
        return ok(data);
    }

    public HttpResponse view(Class theClass, HttpRequest httpRequest) throws IOException, SQLException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException, ExecutionControl.NotImplementedException {
        if (settingsController.isLibraryDisabled(theClass)) {
            return redirect("/disabled");
        }
        long id = Long.parseLong(httpRequest.getPathParameter("id"));
        boolean loggedIn = verifyApiKey(httpRequest);
        ExtendedUserEntity userEntity = (ExtendedUserEntity) getUser(httpRequest, userEntityController);
        GenericController controller = getController(theClass, controllers);
        Object object = controller.readSecureResponse(id, httpRequest);
        return ok(toForm(controller.toFormObjects(false, object), loggedIn, false, userEntity, theClass, id).getBytes());
    }

    public HttpResponse delete(Class theClass, HttpRequest httpRequest) throws Exception {
        if (settingsController.isLibraryDisabled(theClass)) {
            return redirect("/disabled");
        }
        long id = Long.parseLong(httpRequest.getPathParameter("id"));
        GenericController controller = getController(theClass, controllers);
        if (controller.delete(id, httpRequest)) {
            return redirect("/browse/" + theClass.getSimpleName().toLowerCase());
        }
        return error();
    }

    public HttpResponse edit(Class theClass, HttpRequest httpRequest) throws IOException, SQLException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException, ExecutionControl.NotImplementedException {
        if (settingsController.isLibraryDisabled(theClass)) {
            return redirect("/disabled");
        }
        ExtendedUserEntity userEntity = (ExtendedUserEntity) getUser(httpRequest, userEntityController);
        long id = Long.parseLong(httpRequest.getPathParameter("id"));
        GenericController controller = getController(theClass, controllers);
        Object object = controller.readSecureResponse(id, httpRequest);
        boolean loggedIn = verifyApiKey(httpRequest);
        return ok(toForm(controller.toFormObjects(true, object), loggedIn, true, userEntity, theClass, id).getBytes());
    }

    public HttpResponse postEntity(Class theClass, HttpRequest httpRequest) {
        if (settingsController.isLibraryDisabled(theClass)) {
            return redirect("/disabled");
        }
        int id = Integer.parseInt(httpRequest.getPathParameter("id"));
        /*
        MovieEntity movieEntity = MovieForm.getFromForm(httpRequest);
        if (movieController.update(movieEntity.getId(), movieEntity, httpRequest)) {
            return redirect("/view/movie/" + id);
        }*/
        return error();
    }

    public Promisable<HttpResponse> add(Class theClass, HttpRequest httpRequest) throws IOException, SQLException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException, ExecutionControl.NotImplementedException {
        if (settingsController.isLibraryDisabled(theClass)) {
            return redirect("/disabled");
        }
        ExtendedUserEntity userEntity = (ExtendedUserEntity) getUser(httpRequest, userEntityController);
        boolean loggedIn = verifyApiKey(httpRequest);
        GenericController controller = getController(theClass, controllers);
        byte[] data = toForm(controller.toFormObjects(true, null), loggedIn, true, userEntity, theClass, null).getBytes();
        byte[] compressed = CompressionController.compress(data);
        return okResponseCompressed(compressed);
    }

    public HttpResponse newEntities(Class theClass, HttpRequest httpRequest) throws IOException, SQLException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        if (settingsController.isLibraryDisabled(theClass)) {
            return redirect("/disabled");
        }
        ExtendedUserEntity userEntity = (ExtendedUserEntity) getUser(httpRequest, userEntityController);
        UserSettingsEntity userSettingsEntity = userEntity.getUserSettings();
        int page = httpRequest.getQueryParameter("page") != null ? Integer.parseInt(Objects.requireNonNull(httpRequest.getQueryParameter("page"))) : 0;
        int maxResults = settingsController.getMaxBrowseResults(userSettingsEntity);
        GenericController controller = getController(theClass, controllers);
        long maxPage = controller.getTotalPages(settingsController.getMaxBrowseResults(userSettingsEntity), httpRequest);
        List objects = controller.readOrderByPaginated(maxResults, page, "id", false, false, httpRequest);
        LinkedList<Card> cards = new LinkedList<>();
        if (objects != null) {
            cards.addAll(CardConverter.convert(objects, "browse", theClass));
        }
        boolean loggedIn = verifyApiKey(httpRequest);
        String sortBy = httpRequest.getQueryParameter("sortBy");
        String lowerCaseClassName = theClass.getSimpleName().toLowerCase();
        byte[] data = viewPage.render(loggedIn, "New " + lowerCaseClassName + ":", lowerCaseClassName, "new", cards, settingsController.getCardWidth(userSettingsEntity), page, maxPage, userEntity, theClass, false, sortBy, false);
        byte[] compressed = CompressionController.compress(data);
        return okResponseCompressed(compressed);
    }

    public HttpResponse popularEntities(Class theClass, HttpRequest httpRequest) throws IOException, SQLException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        if (settingsController.isLibraryDisabled(theClass)) {
            return redirect("/disabled");
        }
        ExtendedUserEntity userEntity = (ExtendedUserEntity) getUser(httpRequest, userEntityController);
        UserSettingsEntity userSettingsEntity = userEntity.getUserSettings();
        int page = httpRequest.getQueryParameter("page") != null ? Integer.parseInt(Objects.requireNonNull(httpRequest.getQueryParameter("page"))) : 0;
        int maxResults = settingsController.getMaxBrowseResults(userSettingsEntity);
        GenericController controller = getController(theClass, controllers);
        long maxPage = controller.getTotalPages(settingsController.getMaxBrowseResults(userSettingsEntity), httpRequest);
        List objects = controller.readOrderByPaginated(maxResults, page, "views", false, false, httpRequest);
        LinkedList<Card> cards = new LinkedList<>();
        if (objects != null) {
            cards.addAll(CardConverter.convert(objects, "browse", theClass));
        }
        boolean loggedIn = verifyApiKey(httpRequest);
        String sortBy = httpRequest.getQueryParameter("sortBy");
        String lowerCaseClassName = theClass.getSimpleName().toLowerCase();
        byte[] data = viewPage.render(loggedIn, "Popular " + lowerCaseClassName + ":", lowerCaseClassName, "popular", cards, settingsController.getCardWidth(userSettingsEntity), page, maxPage, userEntity, BookEntity.class, false, sortBy, false);
        byte[] compressed = CompressionController.compress(data);
        return okResponseCompressed(compressed);
    }

    public String toForm(List<DomContent> contentList,
                         boolean loggedIn,
                         boolean editable,
                         ExtendedUserEntity userEntity,
                         Class theClass,
                         Long id) throws SQLException {
        return document(
            html(
                HeadComponent.render(settingsController.getServerName()),
                TopBarComponent.render(notificationEntityController.getNumberOfUnread(userEntity), QueueEntityController.getQueueSize(), loggedIn, userEntity.getPermissionGroup(), settingsController.isEnableUpload()),
                SideBarComponent.render(loggedIn,
                    settingsController.isMovieLibraryEnable(),
                    settingsController.isTvShowLibraryEnable(),
                    settingsController.isGameLibraryEnable(),
                    settingsController.isMusicLibraryEnable(),
                    settingsController.isBookLibraryEnable()),
                script().withSrc("/assets/autocompletetextbox.js"),
                body(
                    div(
                        form(
                            iff(!editable,
                                a(" Edit").withClass("floatRight btn fas fa-edit").withHref("/edit/" + theClass.getSimpleName().toLowerCase() + "/" + id)
                            ),
                            br(),
                            each(contentList, content -> content),
                            br(),
                            br(),
                            iff(editable,
                                button("Save").withClass("floatRight")
                            )
                        )
                            .withAction("#")
                            .withMethod("POST")
                    ).withClass("body")
                )
            )
        );
    }
}
