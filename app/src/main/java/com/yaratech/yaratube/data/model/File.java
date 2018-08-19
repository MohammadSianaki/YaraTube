
package com.yaratech.yaratube.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class File {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("file")
    @Expose
    private String file;
    @SerializedName("img")
    @Expose
    private Object img;
    @SerializedName("sku")
    @Expose
    private String sku;
    @SerializedName("sku_registered")
    @Expose
    private boolean skuRegistered;
    @SerializedName("sku_reg_date")
    @Expose
    private String skuRegDate;
    @SerializedName("price")
    @Expose
    private int price;
    @SerializedName("is_purchased")
    @Expose
    private boolean isPurchased;
    @SerializedName("length")
    @Expose
    private int length;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("sub_fa")
    @Expose
    private Object subFa;
    @SerializedName("sub_en")
    @Expose
    private Object subEn;
    @SerializedName("is_downloadable")
    @Expose
    private boolean isDownloadable;
    @SerializedName("price_unit")
    @Expose
    private String priceUnit;
    @SerializedName("is_enable")
    @Expose
    private boolean isEnable;
    @SerializedName("file_type")
    @Expose
    private int fileType;
    @SerializedName("file_redirect")
    @Expose
    private String fileRedirect;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Object getImg() {
        return img;
    }

    public void setImg(Object img) {
        this.img = img;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public boolean isSkuRegistered() {
        return skuRegistered;
    }

    public void setSkuRegistered(boolean skuRegistered) {
        this.skuRegistered = skuRegistered;
    }

    public String getSkuRegDate() {
        return skuRegDate;
    }

    public void setSkuRegDate(String skuRegDate) {
        this.skuRegDate = skuRegDate;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isIsPurchased() {
        return isPurchased;
    }

    public void setIsPurchased(boolean isPurchased) {
        this.isPurchased = isPurchased;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getSubFa() {
        return subFa;
    }

    public void setSubFa(Object subFa) {
        this.subFa = subFa;
    }

    public Object getSubEn() {
        return subEn;
    }

    public void setSubEn(Object subEn) {
        this.subEn = subEn;
    }

    public boolean isIsDownloadable() {
        return isDownloadable;
    }

    public void setIsDownloadable(boolean isDownloadable) {
        this.isDownloadable = isDownloadable;
    }

    public String getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(String priceUnit) {
        this.priceUnit = priceUnit;
    }

    public boolean isIsEnable() {
        return isEnable;
    }

    public void setIsEnable(boolean isEnable) {
        this.isEnable = isEnable;
    }

    public int getFileType() {
        return fileType;
    }

    public void setFileType(int fileType) {
        this.fileType = fileType;
    }

    public String getFileRedirect() {
        return fileRedirect;
    }

    public void setFileRedirect(String fileRedirect) {
        this.fileRedirect = fileRedirect;
    }

}
