package com.yaratech.yaratube.ui.category;

import com.yaratech.yaratube.data.source.StoreDataSource;
import com.yaratech.yaratube.data.source.StoreRepository;

import java.util.List;

public class CategoryPresenter implements CategoryContract.Presenter {

    private static final String TAG = "CategoryPresenter";
    private StoreRepository repository;
    private CategoryContract.View mView;

    public CategoryPresenter(StoreRepository storeRepository) {
        this.repository = storeRepository;
    }

    @Override
    public void attachView(CategoryContract.View view) {
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
    public void fetchCategoriesFromRemoteDataSource() {
        // check if view is attached to the presenter
        if (isAttached()) {
            // show progress bar
            mView.showProgressBarLoading();
            repository.fetchAllCategories(new StoreDataSource.ApiResultCallback() {

                @Override
                public void onDataLoaded(Object response) {
                    List list = (List) response;
                    if (mView != null) {
                        mView.finishProgressBarLoading();
                        mView.showLoadedData(list);
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
    public void cancelCategoryApiRequest() {
        repository.cancelCategoryApiRequest();
    }
}