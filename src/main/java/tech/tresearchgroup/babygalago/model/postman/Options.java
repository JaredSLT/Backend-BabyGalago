package tech.tresearchgroup.babygalago.model.postman;

import com.google.gson.annotations.SerializedName;

public class Options {

    @SerializedName("raw")
    private Raw raw;

    public Raw getRaw() {
        return raw;
    }
}