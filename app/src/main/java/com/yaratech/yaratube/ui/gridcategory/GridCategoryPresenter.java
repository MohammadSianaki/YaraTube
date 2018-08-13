package com.yaratech.yaratube.ui.gridcategory;

import android.content.Context;
import android.util.Log;

import com.yaratech.yaratube.data.model.Product;
import com.yaratech.yaratube.data.source.DataSource;
import com.yaratech.yaratube.data.source.Repository;
import com.yaratech.yaratube.data.source.remote.RemoteDataSource;

import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class GridCategoryPresenter implements GridCategoryContract.Presenter {


    private GridCategoryContract.View mView;
    private Repository repository;
    private Context context;

    @Override
    public void attachView(GridCategoryContract.View view) {
        mView = view;
        context = ((GridCategoryFragment) mView).getContext();
        this.repository = Repository.getINSTANCE(new RemoteDataSource((context)));
    }

    @Override
    public void detachView(GridCategoryContract.View view) {
        context = null;
        mView = null;
    }

    @Override
    public void cancelProductApiRequest() {
        if (isAttached()) {
            repository.cancelProductsByCategoryIdApiRequest();
        }
    }

    @Override
    public void fetchProducts(int id) {
        if (isAttached()) {
            mView.showProgressBarLoading();
            repository.fetchProductsByCategoryId(new DataSource.ApiResultCallback() {
                @Override
                public void onDataLoaded(Object response) {
                    List<Product> productList = null;
                    try {
                        productList = (List<Product>) response;
                        Log.i(TAG, "onDataLoaded: ");
                    } catch (ClassCastException e) {
                        Log.i(TAG, "onDataLoaded: ClassCastException");
                        e.printStackTrace();
                    }
                    if (mView != null) {
                        mView.finishProgressBarLoading();
                        mView.showLoadedData(productList);
                    }
                }

                @Override
                public void onDataNotAvailable() {
                    if (mView != null) {
                        mView.finishProgressBarLoading();
                        mView.showDataNotAvailableToast();
                    }
                }

                @Override
                public void onNetworkNotAvailable() {
                    if (mView != null) {
                        mView.finishProgressBarLoading();
                        mView.showNetworkNotAvailableToast();
                    }
                }
            }, id);
        }
    }

    @Override
    public boolean isAttached() {
        return mView != null;
    }
}
