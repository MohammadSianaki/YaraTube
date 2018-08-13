package com.yaratech.yaratube.ui.gridcategory;

public class GridCategoryPresenter implements GridCategoryContract.Presenter {

    private GridCategoryContract.View mView;


    @Override
    public void attachView(GridCategoryContract.View view) {
        mView = view;
    }

    @Override
    public void detachView(GridCategoryContract.View view) {
        mView = null;
    }

    @Override
    public boolean isAttached() {
        return mView != null;
    }
}
