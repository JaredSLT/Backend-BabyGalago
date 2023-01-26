package tech.tresearchgroup.babygalago.model.postman;

import com.google.gson.annotations.SerializedName;

public class Body {

    @SerializedName("mode")
    private String mode;

    @SerializedName("options")
    private Options options;

    @SerializedName("raw")
    private String raw;

    public String getMode() {
        return mode;
    }

    public Options getOptions() {
        return options;
    }

    public String getRaw() {
        return raw;
    }
}