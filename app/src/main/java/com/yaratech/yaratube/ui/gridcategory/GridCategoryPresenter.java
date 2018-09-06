package com.yaratech.yaratube.ui.gridcategory;

import com.yaratech.yaratube.data.AppDataManager;
import com.yaratech.yaratube.data.DataManager;
import com.yaratech.yaratube.data.model.other.Product;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class GridCategoryPresenter implements GridCategoryContract.Presenter {


    private AppDataManager appDataManager;
    private GridCategoryContract.View mView;
    private CompositeDisposable compositeDisposable;

    public GridCategoryPresenter(AppDataManager appDataManager) {
        this.appDataManager = appDataManager;
        compositeDisposable = new CompositeDisposable();
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
    public void unSubscribe() {
        compositeDisposable.clear();
    }

    @Override
    public void fetchProducts(int categoryId, int offset, int limit) {
        if (isAttached()) {
            mView.showProgressBarLoading();
            Disposable disposable = appDataManager.fetchProductsByCategoryId(categoryId, offset, limit, new DataManager.DashboardApiResultCallback() {

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
            });
            compositeDisposable.add(disposable);
        }
    }

    @Override
    public boolean isAttached() {
        return mView != null;
    }
}
