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

    private static LocalDataSource INSTANCE = null;
    private static final String TAG = "LocalDataSource";
    private AppDataBase appDataBase;
    private UserDao userDao;

    private LocalDataSource(Context context) {
        this.appDataBase = AppDataBase.getINSTANCE(context);
        this.userDao = appDataBase.userDao();
    }

    public static LocalDataSource getINSTANCE(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new LocalDataSource(context);
        }
        return INSTANCE;
    }


    @Override
    public void checkIfUserIsAuthorized(ReadFromDatabaseCallback callback) {


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
                        callback.onUserLoginInfoLoaded(userLoginInfo);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "<<<<    LocalDataSource     >>>>        onError: ", e);
                        callback.onFailureMessage(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete() called");
                        callback.onNotFoundUserInDatabase();
                    }
                });


    }

    @Override
    public void insertUserLoginInfo(InsertIntoDatabaseCallback callback, UserLoginInfo userLoginInfo) {

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

    // TODO: 8/25/2018  Below Methods Must Be  Removed

    @Override
    public void setUserMobilePhoneNumber(String mobilePhoneNumber) {

    }

    @Override
    public String getUserMobilePhoneNumber() {
        return null;
    }

    @Override
    public void setUserLoginStep(int loginStep) {

    }

    @Override
    public int getUserLoginStep() {
        return 0;
    }

    @Override
    public void submitCommentToProduct(int productId, int score, String title, String textContent, String token, ApiResultCallback callback) {

    }

    @Override
    public void cancelPostCommentRequest() {

    }

    @Override
    public void registerUserWithThisPhoneNumber(ApiResultCallback callback, String phoneNumber) {

    }

    @Override
    public void verifyUserWithThisCode(ApiResultCallback callback, String code, String phoneNumber) {

    }

}
