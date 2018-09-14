
package com.yaratech.yaratube.data.model.api;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.yaratech.yaratube.data.model.other.Credit;
import com.yaratech.yaratube.data.model.other.MagicCredit;

public class GetProfileResponse {

    @SerializedName("friends")
    @Expose
    private List<Object> friends = null;
    @SerializedName("date_of_birth")
    @Expose
    private String dateOfBirth;
    @SerializedName("magic_credit")
    @Expose
    private MagicCredit magicCredit;
    @SerializedName("mobile")
    @Expose
    private Object mobile;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("error")
    @Expose
    private boolean error;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("nickname")
    @Expose
    private String nickname;
    @SerializedName("avatar")
    @Expose
    private Object avatar;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("credit")
    @Expose
    private Credit credit;

    /**
     * No args constructor for use in serialization
     * 
     */
    public GetProfileResponse() {
    }

    /**
     * 
     * @param id
     * @param dateOfBirth
     * @param magicCredit
     * @param friends
     * @param error
     * @param nickname
     * @param email
     * @param gender
     * @param credit
     * @param avatar
     * @param mobile
     */
    public GetProfileResponse(List<Object> friends, String dateOfBirth, MagicCredit magicCredit, Object mobile, String email, boolean error, int id, String nickname, Object avatar, String gender, Credit credit) {
        super();
        this.friends = friends;
        this.dateOfBirth = dateOfBirth;
        this.magicCredit = magicCredit;
        this.mobile = mobile;
        this.email = email;
        this.error = error;
        this.id = id;
        this.nickname = nickname;
        this.avatar = avatar;
        this.gender = gender;
        this.credit = credit;
    }

    public List<Object> getFriends() {
        return friends;
    }

    public void setFriends(List<Object> friends) {
        this.friends = friends;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public MagicCredit getMagicCredit() {
        return magicCredit;
    }

    public void setMagicCredit(MagicCredit magicCredit) {
        this.magicCredit = magicCredit;
    }

    public Object getMobile() {
        return mobile;
    }

    public void setMobile(Object mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Object getAvatar() {
        return avatar;
    }

    public void setAvatar(Object avatar) {
        this.avatar = avatar;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Credit getCredit() {
        return credit;
    }

    public void setCredit(Credit credit) {
        this.credit = credit;
    }

    @Override
    public String toString() {
        return "GetProfileResponse{" +
                "friends=" + friends +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", magicCredit=" + magicCredit +
                ", mobile=" + mobile +
                ", email='" + email + '\'' +
                ", error=" + error +
                ", id=" + id +
                ", nickname='" + nickname + '\'' +
                ", avatar=" + avatar +
                ", gender='" + gender + '\'' +
                ", credit=" + credit +
                '}';
    }
}
