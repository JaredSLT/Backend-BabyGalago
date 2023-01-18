package tech.tresearchgroup.babygalago.controller.modules;


import com.meilisearch.sdk.Client;
import com.meilisearch.sdk.Config;
import io.activej.inject.annotation.Provides;
import io.activej.inject.module.AbstractModule;
import tech.tresearchgroup.babygalago.controller.SettingsController;

public class SearchModule extends AbstractModule {
    @Provides
    Client client(SettingsController settingsController) {
        return new Client(new Config(settingsController.getSearchHost(), settingsController.getSearchKey()));
    }
}
