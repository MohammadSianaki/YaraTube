
package com.yaratech.yaratube.data.model.other;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Producer {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("producer_slug")
    @Expose
    private String producerSlug;
    @SerializedName("avatar")
    @Expose
    private Object avatar;
    @SerializedName("description")
    @Expose
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProducerSlug() {
        return producerSlug;
    }

    public void setProducerSlug(String producerSlug) {
        this.producerSlug = producerSlug;
    }

    public Object getAvatar() {
        return avatar;
    }

    public void setAvatar(Object avatar) {
        this.avatar = avatar;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
