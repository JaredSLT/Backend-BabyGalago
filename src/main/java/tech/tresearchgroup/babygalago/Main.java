package tech.tresearchgroup.babygalago;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.tresearchgroup.babygalago.controller.HTMLProjectGenerator;
import tech.tresearchgroup.babygalago.controller.PostmanProjectGenerator;
import tech.tresearchgroup.babygalago.controller.SettingsController;
import tech.tresearchgroup.babygalago.controller.modules.*;
import tech.tresearchgroup.babygalago.view.endpoints.AssetEndpoint;
import tech.tresearchgroup.babygalago.view.endpoints.api.*;
import tech.tresearchgroup.babygalago.view.endpoints.ui.CRUDEndpoints;
import tech.tresearchgroup.babygalago.view.endpoints.ui.MainEndpoints;
import tech.tresearchgroup.babygalago.view.endpoints.ui.PlayEndpoints;
import tech.tresearchgroup.babygalago.view.endpoints.ui.UIUserEndpoints;
import tech.tresearchgroup.colobus.controller.modules.ForumControllersModule;

import java.util.Arrays;

public class Main extends MultiThreadedHttpsServerLauncher {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    public static final String VERSION = "V1.0.0";

    /**
     * This is used to combine all servlets together
     */
    public static final Multibinder<RoutingServlet> SERVLET_MULTIBINDER =
        Multibinders.ofBinaryOperator((servlet1, servlet2) -> servlet1.merge(servlet2));

    /**
     * This is the beginning of the program
     *
     * @param args an array of command line arguments
     * @throws Exception should anything crash
     */
    public static void main(String[] args) throws Exception {
        System.out.println("Starting with: " + Arrays.toString(args));
        if(args.length > 0) {
            if(args[0].equals("server")) {
                SettingsController.loadSettings();
                new Main().launch(args);
            }
        }
        Panel panel = new Panel();
        BasicWindow window = new BasicWindow();
        Terminal terminal = new DefaultTerminalFactory().createTerminal();
        Screen screen = new TerminalScreen(terminal);
        screen.startScreen();
        MultiWindowTextGUI gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLUE));
        panel.setLayoutManager(new BorderLayout());
        TerminalSize terminalSize = new TerminalSize(0, 0);

        panel.addComponent(
            new EmptySpace(
                terminalSize
            )
        );
        Panel actionsPanel = new Panel();
        ActionListBox actionListBox = new ActionListBox();
        actionListBox.addItem("Start server", () -> {
            try {
                SettingsController.loadSettings();
                terminal.close();
                new Main().launch(args);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        actionListBox.addItem("Generate HTML project", () -> {
            try {
                HTMLProjectGenerator.main(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        actionListBox.addItem("Generate Postman project", () -> {
            try {
                PostmanProjectGenerator.main(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        actionListBox.addTo(actionsPanel);
        actionsPanel.withBorder(Borders.singleLine("Actions"));
        panel.addComponent(actionsPanel);

        panel.addComponent(
            new EmptySpace(
                new TerminalSize(0, 0)
            )
        );
        window.setComponent(panel.withBorder(Borders.singleLine("Baby Galago Server " + VERSION)));
        gui.addWindowAndWait(window);
    }

    /**
     * Sets up the dependency injection and compiles the servlets together
     *
     * @return the server module
     */
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
                new JsonSerializerModule()
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

    /**
     * This is run when the program starts
     *
     * @throws Exception should something crash
     */
    @Override
    protected void onStart() throws Exception {
        super.onStart();
    }

    /**
     * This is run when the program stops
     *
     * @throws Exception should anything crash
     */
    @Override
    protected void onStop() throws Exception {
        logger.info("Finished.");
        super.onStop();
    }
}
