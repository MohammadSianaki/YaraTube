
package com.yaratech.yaratube.data.model;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.yaratech.yaratube.utils.AppConstants;

import org.parceler.Parcel;

public class FeatureAvatar implements Parcelable {

    @SerializedName("mdpi")
    @Expose
    private String mdpi;
    @SerializedName("xxxdpi")
    @Expose
    private String xxxdpi;
    @SerializedName("hdpi")
    @Expose
    private String hdpi;
    @SerializedName("xxhdpi")
    @Expose
    private String xxhdpi;
    @SerializedName("xhdpi")
    @Expose
    private String xhdpi;

    protected FeatureAvatar(android.os.Parcel in) {
        mdpi = in.readString();
        xxxdpi = in.readString();
        hdpi = in.readString();
        xxhdpi = in.readString();
        xhdpi = in.readString();
    }

    public static final Creator<FeatureAvatar> CREATOR = new Creator<FeatureAvatar>() {
        @Override
        public FeatureAvatar createFromParcel(android.os.Parcel in) {
            return new FeatureAvatar(in);
        }

        @Override
        public FeatureAvatar[] newArray(int size) {
            return new FeatureAvatar[size];
        }
    };

    public String getMdpi() {
        return mdpi;
    }

    public void setMdpi(String mdpi) {
        this.mdpi = mdpi;
    }

    public String getXxxdpi() {
        return xxxdpi;
    }

    public void setXxxdpi(String xxxdpi) {
        this.xxxdpi = xxxdpi;
    }

    public String getHdpi() {
        return hdpi;
    }

    public void setHdpi(String hdpi) {
        this.hdpi = hdpi;
    }

    public String getXxhdpi() {
        return xxhdpi;
    }

    public void setXxhdpi(String xxhdpi) {
        this.xxhdpi = xxhdpi;
    }

    public String getXhdpi() {
        return xhdpi;
    }

    public void setXhdpi(String xhdpi) {
        this.xhdpi = xhdpi;
    }


    public String getXxxDpiUrl() {
        return AppConstants.BASE_URL + getXxxdpi();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeString(mdpi);
        dest.writeString(xxxdpi);
        dest.writeString(hdpi);
        dest.writeString(xxhdpi);
        dest.writeString(xhdpi);
    }
}
