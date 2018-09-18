package com.yaratech.yaratube.data.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MobileLoginStepOneResponse {

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("nickname")
    @Expose
    private String nickname;

    @SerializedName("error")
    @Expose
    private int error;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }
}
