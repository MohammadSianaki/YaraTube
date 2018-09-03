package com.yaratech.yaratube.data.model.api;

import com.yaratech.yaratube.data.model.other.Category;

import java.util.List;

public class CategoryResponse {

    private List<Category> categoryList;

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }
}
