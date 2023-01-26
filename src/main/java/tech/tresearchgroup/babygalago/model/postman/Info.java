package tech.tresearchgroup.babygalago.model.postman;

import com.google.gson.annotations.SerializedName;

public class Info {

    @SerializedName("schema")
    private String schema;

    @SerializedName("name")
    private String name;

    @SerializedName("_postman_id")
    private String postmanId;

    public String getSchema() {
        return schema;
    }

    public String getName() {
        return name;
    }

    public String getPostmanId() {
        return postmanId;
    }
}