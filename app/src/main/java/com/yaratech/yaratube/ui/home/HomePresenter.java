package com.yaratech.yaratube.ui.home;

import android.content.Context;

import com.yaratech.yaratube.data.model.HomeResponse;
import com.yaratech.yaratube.data.source.DataSource;
import com.yaratech.yaratube.data.source.Repository;
import com.yaratech.yaratube.data.source.remote.RemoteDataSource;

public class HomePresenter implements HomeContract.Presenter {
    //------------------------------------------------------------------------------------
    private static final String TAG = "HomePresenter";

    private Repository repository;
    private HomeContract.View mView;
    //------------------------------------------------------------------------------------

    public HomePresenter(Context context) {
        this.repository = Repository.getINSTANCE(new RemoteDataSource((context)));
    }

    @Override
    public void attachView(HomeContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public boolean isAttached() {
        return mView != null;
    }

    @Override
    public void fetchStoreItems() {
        if (isAttached()) {
            mView.showProgressBarLoading();
            repository.fetchStoreItems(new DataSource.ApiResultCallback() {

                @Override
                public void onDataLoaded(Object response) {
                    HomeResponse homeResponse = (HomeResponse) response;
                    if (mView != null) {
                        mView.finishProgressBarLoading();
                        mView.showLoadedData(homeResponse);
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
        }
    }

    @Override
    public void cancelStoreApiRequest() {
        repository.cancelStoreApiRequest();
    }
}
