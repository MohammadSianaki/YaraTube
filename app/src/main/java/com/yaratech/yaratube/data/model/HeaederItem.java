
package com.yaratech.yaratube.data.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HeaederItem {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("name_english")
    @Expose
    private String nameEnglish;
    @SerializedName("product_type")
    @Expose
    private int productType;
    @SerializedName("producer_name")
    @Expose
    private String producerName;
    @SerializedName("payment_type")
    @Expose
    private List<Object> paymentType = null;
    @SerializedName("price")
    @Expose
    private int price;
    @SerializedName("avatar")
    @Expose
    private Avatar avatar;
    @SerializedName("feature_avatar")
    @Expose
    private FeatureAvatar featureAvatar;
    @SerializedName("rank")
    @Expose
    private int rank;
    @SerializedName("short_description")
    @Expose
    private String shortDescription;
    @SerializedName("is_purchased")
    @Expose
    private boolean isPurchased;
    @SerializedName("comments")
    @Expose
    private int comments;
    @SerializedName("is_bookmarked")
    @Expose
    private boolean isBookmarked;
    @SerializedName("sku")
    @Expose
    private String sku;
    @SerializedName("price_unit")
    @Expose
    private String priceUnit;
    @SerializedName("total_view")
    @Expose
    private int totalView;
    @SerializedName("date_added")
    @Expose
    private String dateAdded;
    @SerializedName("invest_goal")
    @Expose
    private Object investGoal;
    @SerializedName("product_staff")
    @Expose
    private List<Object> productStaff = null;
    @SerializedName("support")
    @Expose
    private Support support;
    @SerializedName("is_special")
    @Expose
    private boolean isSpecial;
    @SerializedName("additional_attributes")
    @Expose
    private List<Object> additionalAttributes = null;
    @SerializedName("date_published")
    @Expose
    private String datePublished;
    @SerializedName("customjson")
    @Expose
    private Object customjson;

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

    public String getNameEnglish() {
        return nameEnglish;
    }

    public void setNameEnglish(String nameEnglish) {
        this.nameEnglish = nameEnglish;
    }

    public int getProductType() {
        return productType;
    }

    public void setProductType(int productType) {
        this.productType = productType;
    }

    public String getProducerName() {
        return producerName;
    }

    public void setProducerName(String producerName) {
        this.producerName = producerName;
    }

    public List<Object> getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(List<Object> paymentType) {
        this.paymentType = paymentType;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public FeatureAvatar getFeatureAvatar() {
        return featureAvatar;
    }

    public void setFeatureAvatar(FeatureAvatar featureAvatar) {
        this.featureAvatar = featureAvatar;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public boolean isIsPurchased() {
        return isPurchased;
    }

    public void setIsPurchased(boolean isPurchased) {
        this.isPurchased = isPurchased;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public boolean isIsBookmarked() {
        return isBookmarked;
    }

    public void setIsBookmarked(boolean isBookmarked) {
        this.isBookmarked = isBookmarked;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(String priceUnit) {
        this.priceUnit = priceUnit;
    }

    public int getTotalView() {
        return totalView;
    }

    public void setTotalView(int totalView) {
        this.totalView = totalView;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Object getInvestGoal() {
        return investGoal;
    }

    public void setInvestGoal(Object investGoal) {
        this.investGoal = investGoal;
    }

    public List<Object> getProductStaff() {
        return productStaff;
    }

    public void setProductStaff(List<Object> productStaff) {
        this.productStaff = productStaff;
    }

    public Support getSupport() {
        return support;
    }

    public void setSupport(Support support) {
        this.support = support;
    }

    public boolean isIsSpecial() {
        return isSpecial;
    }

    public void setIsSpecial(boolean isSpecial) {
        this.isSpecial = isSpecial;
    }

    public List<Object> getAdditionalAttributes() {
        return additionalAttributes;
    }

    public void setAdditionalAttributes(List<Object> additionalAttributes) {
        this.additionalAttributes = additionalAttributes;
    }

    public String getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(String datePublished) {
        this.datePublished = datePublished;
    }

    public Object getCustomjson() {
        return customjson;
    }

    public void setCustomjson(Object customjson) {
        this.customjson = customjson;
    }

}
