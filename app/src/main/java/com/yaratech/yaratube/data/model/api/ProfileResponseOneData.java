package com.yaratech.yaratube.data.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileResponseOneData {

    @SerializedName("nickname")
    @Expose
    private String nickname;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("mobile")
    @Expose
    private Object mobile;
    @SerializedName("avatar")
    @Expose
    private Object avatar;
    @SerializedName("gender")
    @Expose
    private Object gender;
    @SerializedName("date_of_birth")
    @Expose
    private Object dateOfBirth;
    @SerializedName("is_official")
    @Expose
    private boolean isOfficial;

    /**
     * No args constructor for use in serialization
     */
    public ProfileResponseOneData() {
    }

    /**
     * @param dateOfBirth
     * @param email
     * @param nickname
     * @param isOfficial
     * @param gender
     * @param avatar
     * @param mobile
     */
    public ProfileResponseOneData(String nickname, String email, Object mobile, Object avatar, Object gender, Object dateOfBirth, boolean isOfficial) {
        super();
        this.nickname = nickname;
        this.email = email;
        this.mobile = mobile;
        this.avatar = avatar;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.isOfficial = isOfficial;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Object getMobile() {
        return mobile;
    }

    public void setMobile(Object mobile) {
        this.mobile = mobile;
    }

    public Object getAvatar() {
        return avatar;
    }

    public void setAvatar(Object avatar) {
        this.avatar = avatar;
    }

    public Object getGender() {
        return gender;
    }

    public void setGender(Object gender) {
        this.gender = gender;
    }

    public Object getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Object dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public boolean isIsOfficial() {
        return isOfficial;
    }

    public void setIsOfficial(boolean isOfficial) {
        this.isOfficial = isOfficial;
    }

}