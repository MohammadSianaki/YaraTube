package com.yaratech.yaratube.data.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostProfileResponse {

    @SerializedName("data")
    @Expose
    private ProfileResponseOneData data;
    @SerializedName("error")
    @Expose
    private boolean error;
    @SerializedName("message")
    @Expose
    private String message;

    /**
     * No args constructor for use in serialization
     */
    public PostProfileResponse() {
    }

    /**
     * @param message
     * @param error
     * @param data
     */
    public PostProfileResponse(ProfileResponseOneData data, boolean error, String message) {
        super();
        this.data = data;
        this.error = error;
        this.message = message;
    }

    public ProfileResponseOneData getData() {
        return data;
    }

    public void setData(ProfileResponseOneData data) {
        this.data = data;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
