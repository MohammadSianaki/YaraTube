package com.yaratech.yaratube.ui.gridcategory;

import android.content.Context;

import com.yaratech.yaratube.data.model.Product;
import com.yaratech.yaratube.data.source.StoreDataSource;
import com.yaratech.yaratube.data.source.StoreRepository;
import com.yaratech.yaratube.data.source.remote.StoreRemoteDataSource;

import java.util.List;

public class GridCategoryPresenter implements GridCategoryContract.Presenter {


    private StoreRepository repository;
    private GridCategoryContract.View mView;

    public GridCategoryPresenter(Context context) {
        this.repository = StoreRepository.getINSTANCE(new StoreRemoteDataSource((context)));
    }

    @Override
    public void attachView(GridCategoryContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
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
            repository.fetchProductsByCategoryId(new StoreDataSource.ApiResultCallback() {
                @Override
                public void onDataLoaded(Object response) {
                    List<Product> productList = null;
                    try {
                        productList = (List<Product>) response;
                    } catch (ClassCastException e) {
                        throw new ClassCastException("response is not instance of List<Product>");
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
