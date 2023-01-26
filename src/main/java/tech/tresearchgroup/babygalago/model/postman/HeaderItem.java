package tech.tresearchgroup.babygalago.model.postman;

import com.google.gson.annotations.SerializedName;

public class HeaderItem {

    @SerializedName("type")
    private String type;

    @SerializedName("value")
    private String value;

    @SerializedName("key")
    private String key;

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public String getKey() {
        return key;
    }
}