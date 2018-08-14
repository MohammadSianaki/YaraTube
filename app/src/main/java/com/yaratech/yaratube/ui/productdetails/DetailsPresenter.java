package com.yaratech.yaratube.ui.productdetails;

import android.content.Context;

import com.yaratech.yaratube.data.model.Product;
import com.yaratech.yaratube.data.source.DataSource;
import com.yaratech.yaratube.data.source.Repository;
import com.yaratech.yaratube.data.source.remote.RemoteDataSource;

import java.lang.ref.WeakReference;

public class DetailsPresenter implements DetailsContract.Presenter {

    //---------------------------------------------------------------------------------------------
    private Repository repository;
    private WeakReference<DetailsContract.View> mWeakReference;
    //---------------------------------------------------------------------------------------------


    public DetailsPresenter(Context context) {
        this.repository = Repository.getINSTANCE(new RemoteDataSource((context)));
    }

    @Override
    public void attachView(DetailsContract.View view) {
        mWeakReference = new WeakReference<>(view);
    }

    @Override
    public void detachView() {
        mWeakReference = null;
    }

    @Override
    public boolean isAttached() {
        return mWeakReference != null;
    }

    @Override
    public void fetchProductDetails(int productId) {
        if (isAttached()) {
            mWeakReference.get().showProgressBarLoading();
            repository.fetchProductDetailsByProductId(new DataSource.ApiResultCallback() {
                @Override
                public void onDataLoaded(Object response) {
                    Product product = (Product) response;
                    if (mWeakReference != null) {
                        mWeakReference.get().finishProgressBarLoading();
                        mWeakReference.get().showLoadedData(product);
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
            }, productId);
        }
    }

    @Override
    public void cancelProductDetailsApiRequest() {
        repository.cancelProductDetailsByProductIdApiRequest();
    }
}
