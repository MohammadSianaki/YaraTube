package com.yaratech.yaratube.ui.more;

import android.util.Log;

import com.yaratech.yaratube.data.AppDataManager;
import com.yaratech.yaratube.data.DataManager;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class MorePresenter implements MoreContract.Presenter {
    private static final String TAG = "MorePresenter";
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
        if (isAttached()) {
            compositeDisposable.clear();
        }
    }

    @Override
    public void isUserAuthorized() {
        Disposable disposable = appDataManager.isUserAuthorized(new DataManager.LoginDatabaseResultCallback() {
            @Override
            public void onSuccess(String token) {
                if (!token.equals("")) {
                    Log.d(TAG, "onSuccess: token is not null");
                    mView.showProfileFragment();
                } else {
                    Log.d(TAG, "onSuccess: token is null");
                    mView.showLoginDialog();
                }
            }

            @Override
            public void onFailure(String message) {
                Log.d(TAG, "onFailure() called with: message = [" + message + "]");
            }
        });
        compositeDisposable.add(disposable);
    }
}
