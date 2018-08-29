
package com.yaratech.yaratube.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Product implements Parcelable {

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
    @SerializedName("producer")
    @Expose
    private Producer producer;
    @SerializedName("producer_name")
    @Expose
    private String producerName;
    @SerializedName("payment_type")
    @Expose
    private List<Integer> paymentType = null;
    @SerializedName("category")
    @Expose
    private List<Integer> category = null;
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
    private double rank;
    @SerializedName("is_enable")
    @Expose
    private boolean isEnable;
    @SerializedName("totalInstalled")
    @Expose
    private int totalInstalled;
    @SerializedName("short_description")
    @Expose
    private String shortDescription;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("promotionalContainers")
    @Expose
    private List<PromotionalContainer> promotionalContainers = null;
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
    @SerializedName("files")
    @Expose
    private List<File> files;
    @SerializedName("tags")
    @Expose
    private List<Object> tags;
    @SerializedName("category_model")
    @Expose
    private List<Object> categoryModel;
    @SerializedName("comments_summery")
    @Expose
    private List<CommentsSummery> commentsSummery;
    @SerializedName("price_unit")
    @Expose
    private String priceUnit;
    @SerializedName("total_view")
    @Expose
    private int totalView;
    @SerializedName("custom_json")
    @Expose
    private Object customJson;
    @SerializedName("polls")
    @Expose
    private List<Object> polls;
    @SerializedName("date_added")
    @Expose
    private String dateAdded;
    @SerializedName("invest_goal")
    @Expose
    private Object investGoal;
    @SerializedName("product_staff")
    @Expose
    private List<Object> productStaff;
    @SerializedName("support")
    @Expose
    private Support support;
    @SerializedName("is_special")
    @Expose
    private boolean isSpecial;
    @SerializedName("additional_attributes")
    @Expose
    private List<Object> additionalAttributes;
    @SerializedName("date_published")
    @Expose
    private String datePublished;
    @SerializedName("customjson")
    @Expose
    private Object customjson;
    @SerializedName("last_checked_file")
    @Expose
    private Object lastCheckedFile;

    protected Product(Parcel in) {
        id = in.readInt();
        name = in.readString();
        nameEnglish = in.readString();
        productType = in.readInt();
        producerName = in.readString();
        price = in.readInt();
        rank = in.readDouble();
        isEnable = in.readByte() != 0;
        totalInstalled = in.readInt();
        shortDescription = in.readString();
        description = in.readString();
        isPurchased = in.readByte() != 0;
        comments = in.readInt();
        isBookmarked = in.readByte() != 0;
        sku = in.readString();
        priceUnit = in.readString();
        totalView = in.readInt();
        dateAdded = in.readString();
        isSpecial = in.readByte() != 0;
        datePublished = in.readString();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

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

    public Producer getProducer() {
        return producer;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    public String getProducerName() {
        return producerName;
    }

    public void setProducerName(String producerName) {
        this.producerName = producerName;
    }

    public List<Integer> getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(List<Integer> paymentType) {
        this.paymentType = paymentType;
    }

    public List<Integer> getCategory() {
        return category;
    }

    public void setCategory(List<Integer> category) {
        this.category = category;
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

    public double getRank() {
        return rank;
    }

    public void setRank(double rank) {
        this.rank = rank;
    }

    public boolean isIsEnable() {
        return isEnable;
    }

    public void setIsEnable(boolean isEnable) {
        this.isEnable = isEnable;
    }

    public int getTotalInstalled() {
        return totalInstalled;
    }

    public void setTotalInstalled(int totalInstalled) {
        this.totalInstalled = totalInstalled;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<PromotionalContainer> getPromotionalContainers() {
        return promotionalContainers;
    }

    public void setPromotionalContainers(List<PromotionalContainer> promotionalContainers) {
        this.promotionalContainers = promotionalContainers;
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

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public List<Object> getTags() {
        return tags;
    }

    public void setTags(List<Object> tags) {
        this.tags = tags;
    }

    public List<Object> getCategoryModel() {
        return categoryModel;
    }

    public void setCategoryModel(List<Object> categoryModel) {
        this.categoryModel = categoryModel;
    }

    public List<CommentsSummery> getCommentsSummery() {
        return commentsSummery;
    }

    public void setCommentsSummery(List<CommentsSummery> commentsSummery) {
        this.commentsSummery = commentsSummery;
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

    public Object getCustomJson() {
        return customJson;
    }

    public void setCustomJson(Object customJson) {
        this.customJson = customJson;
    }

    public List<Object> getPolls() {
        return polls;
    }

    public void setPolls(List<Object> polls) {
        this.polls = polls;
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

    public Object getLastCheckedFile() {
        return lastCheckedFile;
    }

    public void setLastCheckedFile(Object lastCheckedFile) {
        this.lastCheckedFile = lastCheckedFile;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(nameEnglish);
        dest.writeInt(productType);
        dest.writeString(producerName);
        dest.writeInt(price);
        dest.writeDouble(rank);
        dest.writeByte((byte) (isEnable ? 1 : 0));
        dest.writeInt(totalInstalled);
        dest.writeString(shortDescription);
        dest.writeString(description);
        dest.writeByte((byte) (isPurchased ? 1 : 0));
        dest.writeInt(comments);
        dest.writeByte((byte) (isBookmarked ? 1 : 0));
        dest.writeString(sku);
        dest.writeString(priceUnit);
        dest.writeInt(totalView);
        dest.writeString(dateAdded);
        dest.writeByte((byte) (isSpecial ? 1 : 0));
        dest.writeString(datePublished);
    }
}
