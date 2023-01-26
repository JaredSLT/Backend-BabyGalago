package tech.tresearchgroup.babygalago.model.postman;

import com.google.gson.annotations.SerializedName;

public class Auth {

    @SerializedName("type")
    private String type;

    public String getType() {
        return type;
    }
}