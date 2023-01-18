package tech.tresearchgroup.babygalago.view.components;

import j2html.tags.DomContent;
import org.jetbrains.annotations.NotNull;
import tech.tresearchgroup.palila.controller.cache.StaticDomContentCAO;

import static j2html.TagCreator.*;

public class SideBarComponent {
    public static @NotNull DomContent render(boolean loggedIn,
                                             boolean movieLibraryEnable,
                                             boolean tvShowLibraryEnable,
                                             boolean gameLibraryEnable,
                                             boolean musicLibraryEnable,
                                             boolean bookLibraryEnable) {
        DomContent cached = StaticDomContentCAO.read("sideBarComponent-" + loggedIn);
        if (cached != null) {
            return cached;
        }
        DomContent data = div(
            div(
                img().withAlt("Galago logo").withClass("sidebar-logo").withSrc("/assets/logo.webp")
            ).withClass("flex-centered"),
            iffElse(loggedIn,
                ul(
                    li(
                        a().withClass("btn btn-link nav-btn fa fa-home").withHref("/").withText(" Home")
                    ).withClass("nav-item"),
                    div().withClass("divider"),
                    iff(movieLibraryEnable,
                        li(
                            a().withClass("btn btn-link nav-btn fa fa-film").withHref("/browse/movieentity").withText(" Movies")
                        ).withClass("nav-item")
                    ),
                    iff(tvShowLibraryEnable,
                        li(
                            a().withClass("btn btn-link nav-btn fa fa-tv").withHref("/browse/tvshowentity").withText(" TV Shows")
                        ).withClass("nav-item")
                    ),
                    iff(gameLibraryEnable,
                        li(
                            a().withClass("btn btn-link nav-btn fa fa-gamepad").withHref("/browse/gameentity").withText(" Games")
                        ).withClass("nav-item")
                    ),
                    iff(musicLibraryEnable,
                        li(
                            a().withClass("btn btn-link nav-btn fa fa-music").withHref("/browse/songentity").withText(" Music")
                        ).withClass("nav-item")
                    ),
                    iff(bookLibraryEnable,
                        li(
                            a().withClass("btn btn-link nav-btn fa fa-book").withHref("/browse/bookentity").withText(" Books")
                        ).withClass("nav-item")
                    ),
                    div().withClass("divider"),
                    li(
                        a().withClass("btn btn-link nav-btn fa fa-user-circle").withHref("/forum/index").withText(" Forum")
                    ).withClass("nav-item"),
                    li(
                        a().withClass("btn btn-link nav-btn fa fa-newspaper").withHref("/news").withText(" News")
                    ).withClass("nav-item"),
                    li(
                        a().withClass("btn btn-link nav-btn fa fa-info-circle").withHref("/about").withText(" About")
                    ).withClass("nav-item")
                ).withClass("nav"),
                ul(
                    li(
                        a().withClass("btn btn-link nav-btn fas fa-sign-in-alt").withHref("/login").withText(" Login")
                    ).withClass("nav-item"),
                    div().withClass("divider"),
                    li(
                        a().withClass("btn btn-link nav-btn fa fa-user-circle").withHref("/forum/index").withText(" Forum")
                    ).withClass("nav-item"),
                    li(
                        a().withClass("btn btn-link nav-btn fa fa-info-circle").withHref("/about").withText(" About")
                    ).withClass("nav-item")
                ).withClass("nav")
            ),
            section(
                a(
                    i().withClass("fa fa-copyright fa-rotate-180")
                ).withClass("btn btn-link nav-btn").withHref("/licenses").withText(" 2022 T.R.G.")
            ).withClass("sidebar-footer")
        ).withClass("sidebar active");
        StaticDomContentCAO.create("sideBarComponent-" + loggedIn, data);
        return data;
    }
}
