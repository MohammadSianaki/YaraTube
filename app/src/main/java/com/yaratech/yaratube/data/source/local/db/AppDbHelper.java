package com.yaratech.yaratube.data.source.local.db;

import android.content.Context;
import android.util.Log;

import com.yaratech.yaratube.data.DataManager;
import com.yaratech.yaratube.data.model.db.User;

import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class AppDbHelper implements DbHelper {
    //------------------------------------------------------------------------------------------------------

    private static final String TAG = "AppDbHelper";
    private final AppDatabase mAppDatabase;

    public AppDbHelper(Context context) {
        mAppDatabase = AppDatabase.getINSTANCE(context);
    }


    //------------------------------------------------------------------------------------------------------
    @Override
    public Disposable isUserAuthorized(DataManager.LoginDatabaseResultCallback callback) {
        return Single
                .fromCallable(new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        User user = mAppDatabase.userDao().getUserFromDb();
                        if (user != null && user.getToken() != null) {
                            Log.d(TAG, "call() called with user = [" + user.toString() + "]");
                            return user.getToken();
                        }

                        return "";
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<String>() {
                    @Override
                    public void onSuccess(String token) {
                        callback.onSuccess(token);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError <<<<    isUserAuthorized   >>>>: ", e);
                        callback.onFailure(e.getMessage());
                    }
                });
    }

    @Override
    public Disposable saveUserToDb(User user, DataManager.SaveUserDatabaseResultCallback callback) {
        return Single
                .fromCallable(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        mAppDatabase.userDao().saveUserToDb(user);
                        return true;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Boolean>() {
                    @Override
                    public void onSuccess(Boolean flag) {
                        Log.d(TAG, "onSuccess() called with: flag = [" + flag + "]");
                        callback.onSuccess(flag);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError <<<<    saveuser   >>>>: ", e);
                        callback.onFailure(e.getMessage());
                    }
                });
    }

    @Override
    public Disposable clearDatabase() {
        return Completable
                .fromCallable(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        User user = mAppDatabase.userDao().getUserFromDb();
                        Log.d(TAG, "<<< logout >>> with :  " + user.toString());
                        mAppDatabase.userDao().deleteUserFromDb(user);
                        return true;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: <<<< logout : delete user from database successful >>>>");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: <<< logout : delete user from database >>>>", e);
                    }
                });
    }

}
