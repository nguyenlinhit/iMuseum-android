package vn.edu.tdmu.imuseum.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

/**
 * Created by nvulinh on 11/10/17.
 * Nguyen Vu Linh
 */

public class Response {
    @SerializedName("data")
    private Objects data;
    @SerializedName("responseMessage")
    private String message;
    @SerializedName("responseCode")
    private int statusCode;
    private String stringData;

    public Response(int statusCode) {
        this.statusCode = statusCode;
    }

    public Response(Objects data, String message, int statusCode) {
        this.data = data;
        this.message = message;
        this.statusCode = statusCode;
    }

    public Object getData() {
        return data;
    }

    public void setData(Objects data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStringData() {
        return stringData;
    }

    public void setStringData(String stringData) {
        this.stringData = stringData;
    }
}
