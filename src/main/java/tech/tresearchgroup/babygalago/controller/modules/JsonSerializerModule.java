package tech.tresearchgroup.babygalago.controller.modules;

import com.google.gson.Gson;
import io.activej.inject.annotation.Provides;
import io.activej.inject.module.AbstractModule;

public class JsonSerializerModule extends AbstractModule {
    @Provides
    Gson gson() {
        return new Gson();
    }
}
