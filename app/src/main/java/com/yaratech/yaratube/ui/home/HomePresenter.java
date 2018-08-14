package com.yaratech.yaratube.ui.home;

import android.content.Context;

import com.yaratech.yaratube.data.model.HomeResponse;
import com.yaratech.yaratube.data.source.DataSource;
import com.yaratech.yaratube.data.source.Repository;
import com.yaratech.yaratube.data.source.remote.RemoteDataSource;

import java.lang.ref.WeakReference;

public class HomePresenter implements HomeContract.Presenter {
    //------------------------------------------------------------------------------------
    private static final String TAG = "HomePresenter";

    private Repository repository;
    private WeakReference<HomeContract.View> mWeakReference;
    //------------------------------------------------------------------------------------

    public HomePresenter(Context context) {
        this.repository = Repository.getINSTANCE(new RemoteDataSource((context)));
    }

    @Override
    public void attachView(HomeContract.View view) {
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
    public void fetchStoreItems() {
        if (isAttached()) {
            mWeakReference.get().showProgressBarLoading();
            repository.fetchStoreItems(new DataSource.ApiResultCallback() {

                @Override
                public void onDataLoaded(Object response) {
                    HomeResponse homeResponse = (HomeResponse) response;
                    if (mWeakReference != null) {
                        mWeakReference.get().finishProgressBarLoading();
                        mWeakReference.get().showLoadedData(homeResponse);
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
            });
        }
    }

    @Override
    public void cancelStoreApiRequest() {
        repository.cancelStoreApiRequest();
    }
}
