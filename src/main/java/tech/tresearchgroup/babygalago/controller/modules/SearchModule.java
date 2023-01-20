package tech.tresearchgroup.babygalago.controller.modules;


import com.meilisearch.sdk.Client;
import com.meilisearch.sdk.Config;
import io.activej.inject.annotation.Provides;
import io.activej.inject.module.AbstractModule;
import tech.tresearchgroup.babygalago.controller.SettingsController;

/**
 * This class is used for dependency injection. It sets up the search client to be used elsewhere
 */
public class SearchModule extends AbstractModule {
    @Provides
    Client client(SettingsController settingsController) {
        return new Client(new Config(settingsController.getSearchHost(), settingsController.getSearchKey()));
    }
}
