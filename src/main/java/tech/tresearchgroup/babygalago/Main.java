package tech.tresearchgroup.babygalago;

import io.activej.http.AsyncServlet;
import io.activej.http.RoutingServlet;
import io.activej.inject.Injector;
import io.activej.inject.Key;
import io.activej.inject.binding.Multibinder;
import io.activej.inject.binding.Multibinders;
import io.activej.inject.module.Module;
import io.activej.inject.module.ModuleBuilder;
import io.activej.inject.module.Modules;
import io.activej.worker.annotation.Worker;
import tech.tresearchgroup.babygalago.controller.SettingsController;
import tech.tresearchgroup.babygalago.controller.modules.*;
import tech.tresearchgroup.babygalago.view.endpoints.AssetEndpoint;
import tech.tresearchgroup.babygalago.view.endpoints.api.*;
import tech.tresearchgroup.babygalago.view.endpoints.ui.CRUDEndpoints;
import tech.tresearchgroup.babygalago.view.endpoints.ui.MainEndpoints;
import tech.tresearchgroup.babygalago.view.endpoints.ui.PlayEndpoints;
import tech.tresearchgroup.babygalago.view.endpoints.ui.UIUserEndpoints;
import tech.tresearchgroup.colobus.controller.modules.ForumControllersModule;

public class Main extends MultiThreadedHttpsServerLauncher {
    public static final String VERSION = "V1.0.0";

    public static final Multibinder<RoutingServlet> SERVLET_MULTIBINDER =
        Multibinders.ofBinaryOperator((servlet1, servlet2) -> servlet1.merge(servlet2));

    public static void main(String[] args) throws Exception {
        SettingsController.loadSettings();
        new Main().launch(args);
    }

    @Override
    @Worker
    public Module getBusinessLogicModule() {
        try {
            Injector.useSpecializer();
            Injector injector = Injector.of(
                new SettingsModule(),
                new PagesAndFormsModule(),
                new DatabaseModule(),
                new SearchModule(),
                new RestModule(),
                new ControllerModule(),
                new ForumControllersModule(),
                new JsonSerializerModule(),
                new BinarySerializerModule()
            );
            return Modules.combine(
                //API endpoints
                injector.getInstance(RatingEndpoints.class),
                injector.getInstance(GeneralEndpoints.class),
                injector.getInstance(MediaTypeEndpoints.class),
                injector.getInstance(NewsEndpoints.class),
                injector.getInstance(NotificationsEndpoints.class),
                injector.getInstance(QueueEndpoints.class),
                injector.getInstance(SettingsEndpoints.class),
                injector.getInstance(UserEndpoints.class),
                injector.getInstance(LoginEndpoints.class),

                //UI endpoints
                injector.getInstance(CRUDEndpoints.class),
                injector.getInstance(MainEndpoints.class),
                injector.getInstance(PlayEndpoints.class),
                injector.getInstance(UIUserEndpoints.class),

                //Asset loader
                injector.getInstance(AssetEndpoint.class),

                //Scheduled tasks
                injector.getInstance(TaskEndpoints.class),

                ModuleBuilder.create()
                    .bind(AsyncServlet.class)
                    .to(RoutingServlet.class)
                    .multibind(Key.of(RoutingServlet.class), SERVLET_MULTIBINDER)
                    .build()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onStart() throws Exception {
        super.onStart();
    }

    @Override
    protected void onStop() throws Exception {
        System.out.println("Finished.");
        super.onStop();
    }
}
