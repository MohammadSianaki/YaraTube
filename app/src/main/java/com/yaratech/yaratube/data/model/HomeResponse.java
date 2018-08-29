
package com.yaratech.yaratube.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomeResponse {


    @SerializedName("homeitem")
    @Expose
    private List<HomeItem> homeItems = null;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("headeritem")
    @Expose
    private List<Product> headerItems = null;
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

    public List<HomeItem> getHomeItems() {
        return homeItems;
    }

    public void setHomeItems(List<HomeItem> homeItems) {
        this.homeItems = homeItems;
    }

    public List<Product> getHeaderItems() {
        return headerItems;
    }

    public void setHeaderItems(List<Product> headerItems) {
        this.headerItems = headerItems;
    }
}
