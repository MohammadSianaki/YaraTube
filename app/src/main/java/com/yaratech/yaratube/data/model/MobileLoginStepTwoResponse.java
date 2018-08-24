package com.yaratech.yaratube.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MobileLoginStepTwoResponse {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("files_added")
    @Expose
    private String filesAdded;
    @SerializedName("nickname")
    @Expose
    private String nickName;
    @SerializedName("Fino_token")
    @Expose
    private String finoToken;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getFilesAdded() {
        return filesAdded;
    }

    public void setFilesAdded(String filesAdded) {
        this.filesAdded = filesAdded;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getFinoToken() {
        return finoToken;
    }

    public void setFinoToken(String finoToken) {
        this.finoToken = finoToken;
    }
}
