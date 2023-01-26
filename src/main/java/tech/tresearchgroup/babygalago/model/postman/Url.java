package tech.tresearchgroup.babygalago.model.postman;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Url {

    @SerializedName("path")
    private List<String> path;

    @SerializedName("protocol")
    private String protocol;

    @SerializedName("host")
    private List<String> host;

    @SerializedName("raw")
    private String raw;

    @SerializedName("port")
    private String port;

    @SerializedName("query")
    private List<QueryItem> query;

    public List<String> getPath() {
        return path;
    }

    public String getProtocol() {
        return protocol;
    }

    public List<String> getHost() {
        return host;
    }

    public String getRaw() {
        return raw;
    }

    public String getPort() {
        return port;
    }

    public List<QueryItem> getQuery() {
        return query;
    }
}