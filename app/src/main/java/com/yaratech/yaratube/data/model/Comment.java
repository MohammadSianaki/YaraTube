
package com.yaratech.yaratube.data.model;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Comment implements Parcelable
{

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("score")
    @Expose
    private int score;
    @SerializedName("comment_text")
    @Expose
    private String commentText;
    @SerializedName("likes")
    @Expose
    private int likes;
    @SerializedName("dislikes")
    @Expose
    private int dislikes;
    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("date_added")
    @Expose
    private String dateAdded;
    @SerializedName("comment_reply")
    @Expose
    private List<Object> commentReply = null;
    @SerializedName("user_avatar")
    @Expose
    private String userAvatar;
    @SerializedName("product")
    @Expose
    private int product;
    @SerializedName("is_read")
    @Expose
    private boolean isRead;
    @SerializedName("is_approved")
    @Expose
    private boolean isApproved;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("date_modify")
    @Expose
    private String dateModify;
    public final static Creator<Comment> CREATOR = new Creator<Comment>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        public Comment[] newArray(int size) {
            return (new Comment[size]);
        }

    }
    ;

    protected Comment(Parcel in) {
        this.id = ((int) in.readValue((int.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.score = ((int) in.readValue((int.class.getClassLoader())));
        this.commentText = ((String) in.readValue((String.class.getClassLoader())));
        this.likes = ((int) in.readValue((int.class.getClassLoader())));
        this.dislikes = ((int) in.readValue((int.class.getClassLoader())));
        this.user = ((String) in.readValue((String.class.getClassLoader())));
        this.dateAdded = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.commentReply, (Object.class.getClassLoader()));
        this.userAvatar = ((String) in.readValue((String.class.getClassLoader())));
        this.product = ((int) in.readValue((int.class.getClassLoader())));
        this.isRead = ((boolean) in.readValue((boolean.class.getClassLoader())));
        this.isApproved = ((boolean) in.readValue((boolean.class.getClassLoader())));
        this.productName = ((String) in.readValue((String.class.getClassLoader())));
        this.dateModify = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public Comment() {
    }

    /**
     * 
     * @param commentReply
     * @param score
     * @param dateAdded
     * @param userAvatar
     * @param id
     * @param product
     * @param commentText
     * @param title
     * @param dateModify
     * @param likes
     * @param isApproved
     * @param dislikes
     * @param productName
     * @param user
     * @param isRead
     */
    public Comment(int id, String title, int score, String commentText, int likes, int dislikes, String user, String dateAdded, List<Object> commentReply, String userAvatar, int product, boolean isRead, boolean isApproved, String productName, String dateModify) {
        super();
        this.id = id;
        this.title = title;
        this.score = score;
        this.commentText = commentText;
        this.likes = likes;
        this.dislikes = dislikes;
        this.user = user;
        this.dateAdded = dateAdded;
        this.commentReply = commentReply;
        this.userAvatar = userAvatar;
        this.product = product;
        this.isRead = isRead;
        this.isApproved = isApproved;
        this.productName = productName;
        this.dateModify = dateModify;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public List<Object> getCommentReply() {
        return commentReply;
    }

    public void setCommentReply(List<Object> commentReply) {
        this.commentReply = commentReply;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public int getProduct() {
        return product;
    }

    public void setProduct(int product) {
        this.product = product;
    }

    public boolean isIsRead() {
        return isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }

    public boolean isIsApproved() {
        return isApproved;
    }

    public void setIsApproved(boolean isApproved) {
        this.isApproved = isApproved;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDateModify() {
        return dateModify;
    }

    public void setDateModify(String dateModify) {
        this.dateModify = dateModify;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(title);
        dest.writeValue(score);
        dest.writeValue(commentText);
        dest.writeValue(likes);
        dest.writeValue(dislikes);
        dest.writeValue(user);
        dest.writeValue(dateAdded);
        dest.writeList(commentReply);
        dest.writeValue(userAvatar);
        dest.writeValue(product);
        dest.writeValue(isRead);
        dest.writeValue(isApproved);
        dest.writeValue(productName);
        dest.writeValue(dateModify);
    }

    public int describeContents() {
        return  0;
    }

}
