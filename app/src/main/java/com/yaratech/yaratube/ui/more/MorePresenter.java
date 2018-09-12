package com.yaratech.yaratube.ui.more;

import com.yaratech.yaratube.data.AppDataManager;

import io.reactivex.disposables.CompositeDisposable;

public class MorePresenter implements MoreContract.Presenter {

    private MoreContract.View mView;
    private AppDataManager appDataManager;
    private CompositeDisposable compositeDisposable;

    public MorePresenter(AppDataManager appDataManager, CompositeDisposable compositeDisposable) {
        this.appDataManager = appDataManager;
        this.compositeDisposable = compositeDisposable;
    }


    @Override
    public void attachView(MoreContract.View view) {
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

    }
}
