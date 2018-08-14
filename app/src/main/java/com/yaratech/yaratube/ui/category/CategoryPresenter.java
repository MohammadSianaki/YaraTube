package com.yaratech.yaratube.ui.category;

import android.content.Context;

import com.yaratech.yaratube.data.source.DataSource;
import com.yaratech.yaratube.data.source.Repository;
import com.yaratech.yaratube.data.source.remote.RemoteDataSource;

import java.lang.ref.WeakReference;
import java.util.List;

public class CategoryPresenter implements CategoryContract.Presenter {

    private static final String TAG = "CategoryPresenter";
    private Repository repository;
    private WeakReference<CategoryContract.View> mWeakReference;

    public CategoryPresenter(Context context) {
        this.repository = Repository.getINSTANCE(new RemoteDataSource((context)));
    }

    @Override
    public void attachView(CategoryContract.View view) {
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
    public void fetchCategoriesFromRemoteDataSource() {
        // check if view is attached to the presenter
        if (isAttached()) {
            // show progress bar
            mWeakReference.get().showProgressBarLoading();
            repository.fetchAllCategories(new DataSource.ApiResultCallback() {

                @Override
                public void onDataLoaded(Object response) {
                    List list = (List) response;
                    if (mWeakReference != null) {
                        mWeakReference.get().finishProgressBarLoading();
                        mWeakReference.get().showLoadedData(list);
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
    public void cancelCategoryApiRequest() {
        repository.cancelCategoryApiRequest();
    }
}