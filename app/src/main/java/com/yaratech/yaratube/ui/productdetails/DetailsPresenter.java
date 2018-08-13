package com.yaratech.yaratube.ui.productdetails;

import android.content.Context;

import com.yaratech.yaratube.data.model.Product;
import com.yaratech.yaratube.data.source.DataSource;
import com.yaratech.yaratube.data.source.Repository;
import com.yaratech.yaratube.data.source.remote.RemoteDataSource;
import com.yaratech.yaratube.ui.home.HomeFragment;

public class DetailsPresenter implements DetailsContract.Presenter {

    //---------------------------------------------------------------------------------------------
    private DetailsContract.View mView;
    private Repository repository;

    //---------------------------------------------------------------------------------------------
    @Override
    public void attachView(DetailsContract.View view) {
        mView = view;
        Context context = ((DetailsFragment) mView).getContext();
        this.repository = Repository.getINSTANCE(new RemoteDataSource((context)));
    }

    @Override
    public void detachView(DetailsContract.View view) {
        mView = null;
    }

    @Override
    public boolean isAttached() {
        return mView != null;
    }

    @Override
    public void fetchProductDetails(int productId) {
        if (isAttached()) {
            mView.showProgressBarLoading();
            repository.fetchProductDetailsByProductId(new DataSource.ApiResultCallback() {
                @Override
                public void onDataLoaded(Object response) {
                    Product product = (Product) response;
                    if (mView != null) {
                        mView.finishProgressBarLoading();
                        mView.showLoadedData(product);
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
            }, productId);
        }
    }

    @Override
    public void cancelProductDetailsApiRequest() {
        repository.cancelProductDetailsByProductIdApiRequest();
    }
}
