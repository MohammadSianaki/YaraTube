
package com.yaratech.yaratube.data.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HomeResponse {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("tabStrip")
    @Expose
    private List<Object> tabStrip = null;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("category")
    @Expose
    private List<Category> category = null;
    @SerializedName("parent_categories")
    @Expose
    private List<ParentCategory> parentCategories = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Object> getTabStrip() {
        return tabStrip;
    }

    public void setTabStrip(List<Object> tabStrip) {
        this.tabStrip = tabStrip;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

    public List<ParentCategory> getParentCategories() {
        return parentCategories;
    }

    public void setParentCategories(List<ParentCategory> parentCategories) {
        this.parentCategories = parentCategories;
    }

}
