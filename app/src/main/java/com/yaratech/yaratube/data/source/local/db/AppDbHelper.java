package com.yaratech.yaratube.data.source.local.db;

import android.util.Log;

import com.yaratech.yaratube.data.DataManager;
import com.yaratech.yaratube.data.model.db.UserLoginInfo;

import java.util.concurrent.Callable;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class AppDbHelper implements DbHelper {
    //------------------------------------------------------------------------------------------------------

    private static final String TAG = "AppDbHelper";
    private final AppDatabase mAppDatabase;

    private AppDbHelper(AppDatabase mAppDatabase) {
        this.mAppDatabase = mAppDatabase;
    }

    //------------------------------------------------------------------------------------------------------
    @Override
    public Disposable isUserAuthorized(DataManager.LoginDatabaseResultCallback callback) {
        return Single
                .fromCallable(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        return mAppDatabase.userDao().getUserLoginInfo().getIsAuthorized() == 1;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Boolean>() {
                    @Override
                    public void onSuccess(Boolean isLogin) {
                        Log.d(TAG, "onSuccess: <<<<    isUserAuthorized    >>>> with :" + " isLogin boolean = [" + isLogin + "]");
                        callback.onSuccess(isLogin);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError <<<<    isUserAuthorized   >>>>: ", e);
                        callback.onFailure(e.getMessage());
                    }
                });
    }

    @Override
    public Disposable saveUserLoginInfo(UserLoginInfo userLoginInfo, DataManager.LoginDatabaseResultCallback callback) {
        return Single
                .fromCallable(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        mAppDatabase.userDao().insertUserLoginInfo(userLoginInfo);
                        return true;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Boolean>() {
                    @Override
                    public void onSuccess(Boolean isUserSaved) {
                        Log.d(TAG, "onSuccess: <<<<    saveUserLoginInfo    >>>> with :" + " isLogin boolean = [" + isUserSaved + "]");
                        callback.onSuccess(isUserSaved);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError <<<<    saveUserLoginInfo   >>>>: ", e);
                        callback.onFailure(e.getMessage());
                    }
                });
    }

    @Override
    public Disposable clearDatabase() {
        return null;
    }
}
