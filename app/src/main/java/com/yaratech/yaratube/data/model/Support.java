
package com.yaratech.yaratube.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Support implements Parcelable {

    @SerializedName("instagram")
    @Expose
    private String instagram;
    @SerializedName("linkedin")
    @Expose
    private String linkedin;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("google_plus")
    @Expose
    private String googlePlus;
    @SerializedName("telegram")
    @Expose
    private String telegram;
    @SerializedName("website")
    @Expose
    private String website;
    @SerializedName("tel")
    @Expose
    private String tel;

    protected Support(Parcel in) {
        instagram = in.readString();
        linkedin = in.readString();
        name = in.readString();
        email = in.readString();
        googlePlus = in.readString();
        telegram = in.readString();
        website = in.readString();
        tel = in.readString();
    }

    public static final Creator<Support> CREATOR = new Creator<Support>() {
        @Override
        public Support createFromParcel(Parcel in) {
            return new Support(in);
        }

        @Override
        public Support[] newArray(int size) {
            return new Support[size];
        }
    };

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGooglePlus() {
        return googlePlus;
    }

    public void setGooglePlus(String googlePlus) {
        this.googlePlus = googlePlus;
    }

    public String getTelegram() {
        return telegram;
    }

    public void setTelegram(String telegram) {
        this.telegram = telegram;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(instagram);
        dest.writeString(linkedin);
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(googlePlus);
        dest.writeString(telegram);
        dest.writeString(website);
        dest.writeString(tel);
    }
}
