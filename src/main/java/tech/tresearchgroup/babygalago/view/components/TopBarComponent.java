package tech.tresearchgroup.babygalago.view.components;

import j2html.tags.DomContent;
import org.jetbrains.annotations.NotNull;
import tech.tresearchgroup.palila.model.enums.PermissionGroupEnum;

import static j2html.TagCreator.*;

public class TopBarComponent {
    public static @NotNull DomContent render(Long notificationsAlert, long queueItems, boolean loggedIn, PermissionGroupEnum permissionGroupEnum, boolean uploadEnabled) {
        return header(
            section().withClass("navbar-section"),
            section(
                iff(loggedIn,
                    iffElse(uploadEnabled,
                        div(
                            a().withClass("btn fa fa-cloud-upload-alt").withHref("/upload").withText("Upload file")
                        ).withClass("dropdown"),
                        div(
                            a().withClass("btn fa fa-cloud-upload-alt").withHref("/disabled").withText("Upload file")
                        ).withClass("dropdown")
                    )
                ),
                div(
                    a(
                        iffElse(loggedIn,
                            figure(
                                img().withAlt("blah").withSrc("/assets/placeholder.webp"),
                                i().withClass("avatar-presence online")
                            ).withClass("btn btn-link nav-btn avatar avatar-xl"),
                            figure(
                                img().withAlt("blah").withSrc("/assets/placeholder.webp"),
                                i().withClass("avatar-presence offline")
                            ).withClass("btn btn-link nav-btn avatar")
                        ),
                        i().withClass("icon icon-caret")
                    ).withClass("dropdown-toggle").withHref("#").withTabindex(0),
                    iffElse(loggedIn,
                        ul(
                            li(
                                a(
                                    i().withClass("fa fa-folder").withText(" Summary")
                                ).withHref("/profile")
                            ).withClass("menu-item"),
                            li(
                                a(
                                    i().withClass("fa fa-list").withText(" Queue")
                                ).withHref("/queue"),
                                div(
                                    label().withClass("label label-primary").withText(String.valueOf(queueItems))
                                ).withClass("menu-badge")
                            ).withClass("menu-item"),
                            li(
                                a(
                                    i().withClass("fa fa-cog").withText(" User Settings")
                                ).withHref("/user/settings")
                            ).withClass("menu-item"),
                            iff(permissionGroupEnum.equals(PermissionGroupEnum.OPERATOR),
                                li(
                                    a(
                                        i().withClass("fa fa-cog").withText(" Server Settings")
                                    ).withHref("/settings")
                                ).withClass("menu-item")
                            ),
                            li(
                                a(
                                    i().withClass("fa fa-sign-out-alt").withText(" Logout")
                                ).withHref("/logout")
                            ).withClass("menu-item")
                        ).withClass("menu"),
                        ul(
                            li(
                                a(
                                    i().withClass("fas fa-sign-in-alt").withText(" Login")
                                ).withHref("/login")
                            ).withClass("menu-item")
                        ).withClass("menu")
                    )
                ).withClass("dropdown"),
                iff(loggedIn,
                    div(
                        a().withClass("btn badge fa fa-bell").withHref("/notifications").withText("Notifications").attr("data-badge", notificationsAlert)
                    ).withClass("dropdown")
                )
            ).withClass("navbar-center"),
            section(
                form(
                    div(
                        input().withClass("form-input").withPlaceholder("Search").withType("text").withName("query"),
                        button().withClass("btn input-group-btn fa fa-search")
                    ).withClass("input-group input-inline")
                ).withAction("/search").withMethod("POST")
            ).withClass("navbar-section")
        ).withClass("navbar");
    }
}
