package com.yaratech.yaratube.ui.category;

import com.yaratech.yaratube.data.AppDataManager;
import com.yaratech.yaratube.data.DataManager;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class CategoryPresenter implements CategoryContract.Presenter {

    private static final String TAG = "CategoryPresenter";
    private AppDataManager appDataManager;
    private CategoryContract.View mView;
    private CompositeDisposable compositeDisposable;

    public CategoryPresenter(AppDataManager appDataManager) {
        this.appDataManager = appDataManager;
        this.compositeDisposable = new CompositeDisposable();
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
    public void unSubscribe() {
        if (isAttached()) {
            compositeDisposable.clear();
        }
    }

    @Override
    public void fetchCategoriesFromRemoteDataSource() {
        // check if view is attached to the presenter
        if (isAttached()) {
            // show progress bar
            mView.showProgressBarLoading();
            Disposable disposable = appDataManager.fetchListOfCategories(new DataManager.DashboardApiResultCallback() {

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
            compositeDisposable.add(disposable);
        }
    }
}