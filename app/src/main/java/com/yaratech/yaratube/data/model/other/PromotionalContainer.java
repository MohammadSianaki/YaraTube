
package com.yaratech.yaratube.data.model.other;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PromotionalContainer {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("video")
    @Expose
    private Object video;
    @SerializedName("external_video")
    @Expose
    private String externalVideo;
    @SerializedName("external_frame")
    @Expose
    private Object externalFrame;
    @SerializedName("img")
    @Expose
    private String img;
    @SerializedName("length")
    @Expose
    private Object length;
    @SerializedName("video_path")
    @Expose
    private Object videoPath;
    @SerializedName("banner")
    @Expose
    private boolean banner;
    @SerializedName("is_confirmed")
    @Expose
    private boolean isConfirmed;
    @SerializedName("priority")
    @Expose
    private int priority;
    @SerializedName("product")
    @Expose
    private int product;
    @SerializedName("aparat_key")
    @Expose
    private Object aparatKey;
    @SerializedName("video_redirect")
    @Expose
    private String videoRedirect;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Object getVideo() {
        return video;
    }

    public void setVideo(Object video) {
        this.video = video;
    }

    public String getExternalVideo() {
        return externalVideo;
    }

    public void setExternalVideo(String externalVideo) {
        this.externalVideo = externalVideo;
    }

    public Object getExternalFrame() {
        return externalFrame;
    }

    public void setExternalFrame(Object externalFrame) {
        this.externalFrame = externalFrame;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Object getLength() {
        return length;
    }

    public void setLength(Object length) {
        this.length = length;
    }

    public Object getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(Object videoPath) {
        this.videoPath = videoPath;
    }

    public boolean isBanner() {
        return banner;
    }

    public void setBanner(boolean banner) {
        this.banner = banner;
    }

    public boolean isIsConfirmed() {
        return isConfirmed;
    }

    public void setIsConfirmed(boolean isConfirmed) {
        this.isConfirmed = isConfirmed;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getProduct() {
        return product;
    }

    public void setProduct(int product) {
        this.product = product;
    }

    public Object getAparatKey() {
        return aparatKey;
    }

    public void setAparatKey(Object aparatKey) {
        this.aparatKey = aparatKey;
    }

    public String getVideoRedirect() {
        return videoRedirect;
    }

    public void setVideoRedirect(String videoRedirect) {
        this.videoRedirect = videoRedirect;
    }

}
