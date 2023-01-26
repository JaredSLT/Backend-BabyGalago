package tech.tresearchgroup.babygalago.model.postman;

import com.google.gson.annotations.SerializedName;

public class QueryItem {

    @SerializedName("value")
    private String value;

    @SerializedName("key")
    private String key;

    public String getValue() {
        return value;
    }

    public String getKey() {
        return key;
    }
}