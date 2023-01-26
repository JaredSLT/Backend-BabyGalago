package tech.tresearchgroup.babygalago.model.postman;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Request {

    @SerializedName("method")
    private String method;

    @SerializedName("header")
    private List<Object> header;

    @SerializedName("url")
    private Url url;

    @SerializedName("body")
    private Body body;

    @SerializedName("auth")
    private Auth auth;

    public String getMethod() {
        return method;
    }

    public List<Object> getHeader() {
        return header;
    }

    public Url getUrl() {
        return url;
    }

    public Body getBody() {
        return body;
    }

    public Auth getAuth() {
        return auth;
    }
}