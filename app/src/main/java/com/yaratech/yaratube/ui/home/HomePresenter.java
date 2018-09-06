package com.yaratech.yaratube.ui.home;

import com.yaratech.yaratube.data.AppDataManager;
import com.yaratech.yaratube.data.DataManager;
import com.yaratech.yaratube.data.model.api.StoreResponse;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class HomePresenter implements HomeContract.Presenter {
    //------------------------------------------------------------------------------------
    private static final String TAG = "HomePresenter";
    private CompositeDisposable compositeDisposable;
    private AppDataManager appDataManager;
    private HomeContract.View mView;
    //------------------------------------------------------------------------------------

    public HomePresenter(AppDataManager appDataManager) {
        this.appDataManager = appDataManager;
        this.compositeDisposable = new CompositeDisposable();
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
    public void unSubscribe() {
        if (isAttached()) {
            compositeDisposable.clear();
        }
    }

    @Override
    public void fetchStoreItems() {
        if (isAttached()) {
            mView.showProgressBarLoading();
            Disposable disposable = appDataManager.fetchStoreItems(new DataManager.DashboardApiResultCallback() {

                @Override
                public void onDataLoaded(Object response) {
                    StoreResponse storeResponse = (StoreResponse) response;
                    if (mView != null) {
                        mView.finishProgressBarLoading();
                        ((HomeFragment) mView).runLayoutAnimation();
                        mView.showLoadedData(storeResponse);
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
}
