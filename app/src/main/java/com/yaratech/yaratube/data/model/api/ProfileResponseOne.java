package com.yaratech.yaratube.data.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.yaratech.yaratube.data.model.other.ProfileResponseOneData;

public class ProfileResponseOne {

    @SerializedName("error")
    @Expose
    private int error;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private ProfileResponseOneData data;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ProfileResponseOneData getData() {
        return data;
    }

    public void setData(ProfileResponseOneData data) {
        this.data = data;
    }
}
