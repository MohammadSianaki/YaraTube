package com.yaratech.yaratube.ui.home;

import android.content.Context;
import android.util.Log;

import com.yaratech.yaratube.data.model.HomeResponse;
import com.yaratech.yaratube.data.source.DataSource;
import com.yaratech.yaratube.data.source.Repository;
import com.yaratech.yaratube.data.source.remote.RemoteDataSource;

public class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View mView;
    private Repository repository;
    private static final String TAG = "HomePresenter";


    @Override
    public void attachView(HomeContract.View view) {
        mView = view;
        Context context = ((HomeFragment) mView).getContext();
        this.repository = Repository.getINSTANCE(new RemoteDataSource((context)));
    }

    @Override
    public void detachView(HomeContract.View view) {
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
            repository.fetchStoreItems(new DataSource.StoreApiResultCallback() {

                @Override
                public void onDataLoaded(HomeResponse response) {
                    Log.i(TAG, "onDataLoaded: <<<< header item size is : >>>>" + response.getHeaderItems().size());
                    Log.i(TAG, "onDataLoaded: <<<< home item size is : >>>>" + response.getHomeItems().size());
                    if (mView != null) {
                        mView.finishProgressBarLoading();
                        mView.showLoadedData(response);
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
