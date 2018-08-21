
package com.yaratech.yaratube.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.yaratech.yaratube.utils.AppConstants;

public class FeatureAvatar {

    @SerializedName("xhdpi")
    @Expose
    private String xhdpi;
    @SerializedName("xxhdpi")
    @Expose
    private String xxhdpi;
    @SerializedName("xxxdpi")
    @Expose
    private String xxxdpi;
    @SerializedName("mdpi")
    @Expose
    private String mdpi;
    @SerializedName("hdpi")
    @Expose
    private String hdpi;

    public String getXhdpi() {
        return xhdpi;
    }

    public void setXhdpi(String xhdpi) {
        this.xhdpi = xhdpi;
    }

    public String getXxhdpi() {
        return xxhdpi;
    }

    public void setXxhdpi(String xxhdpi) {
        this.xxhdpi = xxhdpi;
    }

    public String getXxxdpi() {
        return xxxdpi;
    }

    public void setXxxdpi(String xxxdpi) {
        this.xxxdpi = xxxdpi;
    }

    public String getMdpi() {
        return mdpi;
    }

    public void setMdpi(String mdpi) {
        this.mdpi = mdpi;
    }

    public String getHdpi() {
        return hdpi;
    }

    public void setHdpi(String hdpi) {
        this.hdpi = hdpi;
    }

    public String getXxxDpiUrl() {
        return AppConstants.BASE_URL + getXxxdpi();
    }
}
