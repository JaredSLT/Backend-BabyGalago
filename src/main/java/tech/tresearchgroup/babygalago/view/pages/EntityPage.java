package tech.tresearchgroup.babygalago.view.pages;

import j2html.tags.DomContent;
import tech.tresearchgroup.babygalago.controller.controllers.QueueEntityController;
import tech.tresearchgroup.babygalago.view.components.HeadComponent;
import tech.tresearchgroup.babygalago.view.components.SideBarComponent;
import tech.tresearchgroup.babygalago.view.components.TopBarComponent;
import tech.tresearchgroup.cao.controller.GenericCAO;
import tech.tresearchgroup.palila.model.enums.PermissionGroupEnum;
import tech.tresearchgroup.palila.view.RenderablePage;

import java.util.LinkedList;
import java.util.List;

import static j2html.TagCreator.*;

public class EntityPage implements RenderablePage {
    /**
     * Renders the page
     *
     * @param contentList           The list of dom content
     * @param loggedIn              whether the user is logged in
     * @param editable              whether the page is editable (input or label)
     * @param theClass              the class of the entity being displayed
     * @param id                    the id of the entity being displayed
     * @param unreadCount           the number of unread notifications
     * @param permissionGroupEnum   the permission group which the user belongs to
     * @param serverName            the name of the server
     * @param isEnableUpload        if file upload is enabled
     * @param isMovieLibraryEnable  if the movie library is enabled
     * @param isTvShowLibraryEnable if the tv show library is enabled
     * @param isGameLibraryEnable   if the game library is enabled
     * @param isMusicLibraryEnable  if the music library is enabled
     * @param isBookLibraryEnable   if the book library is enabled
     * @return the page as a string
     */
    public String render(List<DomContent> contentList,
                         boolean loggedIn,
                         boolean editable,
                         Class theClass,
                         long id,
                         long unreadCount,
                         PermissionGroupEnum permissionGroupEnum,
                         String serverName,
                         boolean isEnableUpload,
                         boolean isMovieLibraryEnable,
                         boolean isTvShowLibraryEnable,
                         boolean isGameLibraryEnable,
                         boolean isMusicLibraryEnable,
                         boolean isBookLibraryEnable,
                         GenericCAO genericCAO) {
        return document(
            html(
                HeadComponent.render(serverName, genericCAO),
                TopBarComponent.render(unreadCount, QueueEntityController.getQueueSize(), loggedIn, permissionGroupEnum, isEnableUpload),
                SideBarComponent.render(
                    loggedIn,
                    isMovieLibraryEnable,
                    isTvShowLibraryEnable,
                    isGameLibraryEnable,
                    isMusicLibraryEnable,
                    isBookLibraryEnable,
                    genericCAO
                ),
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

    /**
     * Renders out the page with dummy data
     *
     * @return the page as a string
     */
    @Override
    public List<String> render() {
        List<String> pages = new LinkedList<>();
        /*
        String[] entityPackages = new String[]{"tech.tresearchgroup.schemas.galago.entities"};
        List<String> classNames = ReflectionMethods.getClassNames(entityPackages);
        for (String className : classNames) {
            try {
                Class<?> theClass = Class.forName(className);
                Object object = ReflectionMethods.getNewInstance(theClass);
                pages.add(
                    render(
                        ReflectionMethods.toFormObjects(false, object, theClass),
                        true,
                        false,
                        theClass,
                        0L,
                        0L,
                        PermissionGroupEnum.ADMINISTRATOR,
                        "Baby galago server",
                        true,
                        true,
                        true,
                        true,
                        true,
                        true
                    )
                );
                pages.add(
                    render(
                        ReflectionMethods.toFormObjects(true, object, theClass),
                        true,
                        true,
                        theClass,
                        0L,
                        0L,
                        PermissionGroupEnum.ADMINISTRATOR,
                        "Baby galago server",
                        true,
                        true,
                        true,
                        true,
                        true,
                        true
                    )
                );
            } catch (ClassNotFoundException | InvocationTargetException | InstantiationException |
                     IllegalAccessException | ExecutionControl.NotImplementedException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }*/
        return pages;
    }
}
