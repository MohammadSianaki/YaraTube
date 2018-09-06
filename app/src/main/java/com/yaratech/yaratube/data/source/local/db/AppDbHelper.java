package com.yaratech.yaratube.data.source.local.db;

import android.content.Context;
import android.util.Log;

import com.yaratech.yaratube.data.DataManager;
import com.yaratech.yaratube.data.model.db.UserLoginInfo;

import java.util.HashMap;
import java.util.Map;
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
    private static AppDbHelper INSTANCE = null;

    private AppDbHelper(Context context) {
        mAppDatabase = AppDatabase.getINSTANCE(context);
    }

    public static AppDbHelper getINSTANCE(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new AppDbHelper(context);
        }
        return INSTANCE;
    }

    //------------------------------------------------------------------------------------------------------
    @Override
    public Disposable isUserAuthorized(DataManager.LoginDatabaseResultCallback callback) {
        return Single
                .fromCallable(new Callable<Map<Boolean, String>>() {
                    @Override
                    public Map<Boolean, String> call() throws Exception {
                        UserLoginInfo userLoginInfo = mAppDatabase.userDao().getUserLoginInfo();
                        Map<Boolean, String> map = new HashMap<>();
                        if (userLoginInfo.getIsAuthorized() == 1) {
                            map.put(true, userLoginInfo.getUser().getToken());
                        } else {
                            map.put(false, null);
                        }

                        return map;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Map<Boolean, String>>() {
                    @Override
                    public void onSuccess(Map<Boolean, String> map) {
                        callback.onSuccess(map);
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
                .fromCallable(new Callable<Map<Boolean, String>>() {
                    @Override
                    public Map<Boolean, String> call() throws Exception {
                        UserLoginInfo userLoginInfo = mAppDatabase.userDao().getUserLoginInfo();
                        Map<Boolean, String> map = new HashMap<>();
                        if (userLoginInfo.getIsAuthorized() == 1) {
                            map.put(true, userLoginInfo.getUser().getToken());
                        } else {
                            map.put(false, null);
                        }

                        return map;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Map<Boolean, String>>() {
                    @Override
                    public void onSuccess(Map<Boolean, String> map) {
                        Log.d(TAG, "onSuccess() called with: token = [" + map.get(true) + "]");
                        callback.onSuccess(map);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError <<<<    saveUserLoginInfo   >>>>: ", e);
                        callback.onFailure(e.getMessage());
                    }
                });
    }

    @Override
    public Disposable clearDatabase(DataManager.LoginDatabaseResultCallback loginDatabaseResultCallback) {
        return null;
    }
}
