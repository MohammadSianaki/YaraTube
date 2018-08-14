package com.yaratech.yaratube.ui.gridcategory;

import android.content.Context;

import com.yaratech.yaratube.data.model.Product;
import com.yaratech.yaratube.data.source.DataSource;
import com.yaratech.yaratube.data.source.Repository;
import com.yaratech.yaratube.data.source.remote.RemoteDataSource;

import java.lang.ref.WeakReference;
import java.util.List;

public class GridCategoryPresenter implements GridCategoryContract.Presenter {


    private Repository repository;
    private WeakReference<GridCategoryContract.View> mWeakReference;

    public GridCategoryPresenter(Context context) {
        this.repository = Repository.getINSTANCE(new RemoteDataSource((context)));
    }

    @Override
    public void attachView(GridCategoryContract.View view) {
        mWeakReference = new WeakReference<>(view);
    }

    @Override
    public void detachView() {
        mWeakReference = null;
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
            mWeakReference.get().showProgressBarLoading();
            repository.fetchProductsByCategoryId(new DataSource.ApiResultCallback() {
                @Override
                public void onDataLoaded(Object response) {
                    List<Product> productList = null;
                    try {
                        productList = (List<Product>) response;
                    } catch (ClassCastException e) {
                        throw new ClassCastException("response is not instance of List<Product>");
                    }
                    if (mWeakReference != null) {
                        mWeakReference.get().finishProgressBarLoading();
                        mWeakReference.get().showLoadedData(productList);
                    }
                }

                @Override
                public void onDataNotAvailable() {
                    if (mWeakReference != null) {
                        mWeakReference.get().finishProgressBarLoading();
                        mWeakReference.get().showDataNotAvailableToast();
                    }
                }

                @Override
                public void onNetworkNotAvailable() {
                    if (mWeakReference != null) {
                        mWeakReference.get().finishProgressBarLoading();
                        mWeakReference.get().showNetworkNotAvailableToast();
                    }
                }
            }, id);
        }
    }

    @Override
    public boolean isAttached() {
        return mWeakReference != null;
    }
}
