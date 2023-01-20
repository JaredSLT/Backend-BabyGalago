package tech.tresearchgroup.babygalago.controller.modules;

import com.google.gson.Gson;
import io.activej.inject.annotation.Provides;
import io.activej.inject.module.AbstractModule;

/**
 * This class is used for dependency injection. It sets up a gson instance to be used elsewhere
 */
public class JsonSerializerModule extends AbstractModule {
    @Provides
    Gson gson() {
        return new Gson();
    }
}
