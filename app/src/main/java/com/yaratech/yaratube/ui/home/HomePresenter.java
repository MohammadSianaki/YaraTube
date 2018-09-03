package com.yaratech.yaratube.ui.home;

import com.yaratech.yaratube.data.model.api.StoreResponse;

public class HomePresenter implements HomeContract.Presenter {
    //------------------------------------------------------------------------------------
    private static final String TAG = "HomePresenter";

    private StoreRepository repository;
    private HomeContract.View mView;
    //------------------------------------------------------------------------------------

    public HomePresenter(StoreRepository storeRepository) {
        this.repository = storeRepository;

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
            repository.fetchStoreItems(new StoreDataSource.ApiResultCallback() {

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
        }
    }

    @Override
    public void cancelStoreApiRequest() {
        repository.cancelStoreApiRequest();
    }
}
