package com.yaratech.yaratube.data.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GoogleLoginResponse {

    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("success")
    @Expose
    private boolean success;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
