package tech.tresearchgroup.babygalago.controller.modules;

import io.activej.inject.annotation.Provides;
import io.activej.inject.module.AbstractModule;
import tech.tresearchgroup.babygalago.controller.SettingsController;

/**
 * This class is used for dependency injection. It sets up the settings controller to be used elsewhere
 */
public class SettingsModule extends AbstractModule {
    @Provides
    SettingsController settingsController() {
        return new SettingsController();
    }
}
