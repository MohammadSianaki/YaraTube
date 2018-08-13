package com.yaratech.yaratube.ui.gridcategory;

import android.content.Context;

import com.yaratech.yaratube.data.source.Repository;
import com.yaratech.yaratube.data.source.remote.RemoteDataSource;

public class GridCategoryPresenter implements GridCategoryContract.Presenter {


    private GridCategoryContract.View mView;
    private Repository repository;


    @Override
    public void attachView(GridCategoryContract.View view) {
        mView = view;
        Context context = ((GridCategoryFragment) mView).getContext();
        this.repository = Repository.getINSTANCE(new RemoteDataSource((context)));
    }

    @Override
    public void detachView(GridCategoryContract.View view) {
        mView = null;
    }

    @Override
    public void fetchProducts() {
        if (isAttached()) {

        }
    }

    @Override
    public boolean isAttached() {
        return mView != null;
    }
}
