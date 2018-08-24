package com.yaratech.yaratube.data.source.local;

import android.content.Context;
import android.util.Log;

import com.yaratech.yaratube.data.source.UserDataSource;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.MaybeObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class LocalDataSource implements UserDataSource {

    private static final String TAG = "LocalDataSource";
    private AppDataBase appDataBase;
    private UserDao userDao;

    public LocalDataSource(Context context) {
        this.appDataBase = AppDataBase.getINSTANCE(context);
        this.userDao = appDataBase.userDao();
    }

    @Override
    public void registerUserWithThisPhoneNumber(ApiResultCallback callback, String phoneNumber) {

    }

    @Override
    public void checkIfUserIsAuthorized(UserDataSource.DatabaseResultCallback callback) {


        userDao.getUserLoginInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MaybeObserver<UserLoginInfo>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {
                        callback.onAddedToCompositeDisposable(disposable);
                    }

                    @Override
                    public void onSuccess(UserLoginInfo userLoginInfo) {
                        if (userLoginInfo.getIsAuthorized() == 1) {
                            callback.onUserIsAuthorized(true);
                        } else {
                            callback.onUserIsAuthorized(false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "<<<<    LocalDataSource     >>>>        onError: ", e);
                        callback.onFailureMessage(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete() called");
                    }
                });


    }

    @Override
    public void insertUserLoginInfo(UserDataSource.DatabaseResultCallback callback, UserLoginInfo userLoginInfo) {

        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                userDao.insertUserLoginInfo(userLoginInfo);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable disposable) {
                        callback.onAddedToCompositeDisposable(disposable);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "<<<<    insertUserLoginInfo     >>>>    onComplete() called");
                        callback.onUserLoginInserted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "<<<<    insertUserLoginInfo     >>>>    onError: ", e);
                        callback.onFailureMessage(e.getMessage());
                    }
                });
    }
}
