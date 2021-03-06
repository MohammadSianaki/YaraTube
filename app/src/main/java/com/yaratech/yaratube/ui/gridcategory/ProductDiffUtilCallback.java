package com.yaratech.yaratube.ui.gridcategory;

import android.support.v7.util.DiffUtil;

import com.yaratech.yaratube.data.model.other.Product;

import java.util.List;

public class ProductDiffUtilCallback extends DiffUtil.Callback {
    private List<Product> oldList;
    private List<Product> newList;

    public ProductDiffUtilCallback(List<Product> oldList, List<Product> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getId() == newList.get(newItemPosition).getId();

    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return true;

    }
}
