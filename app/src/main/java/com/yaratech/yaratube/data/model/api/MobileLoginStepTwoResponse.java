package com.yaratech.yaratube.data.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MobileLoginStepTwoResponse {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("error")
    @Expose
    private int error;
    @SerializedName("user_id")
    @Expose
    private int userId;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("files_added")
    @Expose
    private Object filesAdded;
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

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Object getFilesAdded() {
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
