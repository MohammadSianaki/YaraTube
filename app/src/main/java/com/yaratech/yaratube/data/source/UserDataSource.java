package com.yaratech.yaratube.data.source;

import com.yaratech.yaratube.data.source.local.UserLoginInfo;

import io.reactivex.disposables.Disposable;

public interface UserDataSource {


    interface InsertIntoDatabaseCallback {

        void onUserLoginInserted();

        void onAddedToCompositeDisposable(Disposable disposable);

        void onFailureMessage(String message);

    }

    interface ReadFromDatabaseCallback {
        void onUserLoginInfoLoaded(UserLoginInfo userLoginInfo);

        void onAddedToCompositeDisposable(Disposable disposable);

        void onFailureMessage(String message);
    }

    interface ApiResultCallback<T> {
        void onSuccessMessage(String message, int responseCode, T response);

        void onErrorMessage(String message, int responseCode);

        void onFailureMessage(String message, int responseCode);
    }

    void registerUserWithThisPhoneNumber(ApiResultCallback callback, String phoneNumber);

    void verifyUserWithThisCode(ApiResultCallback callback, String code, String phoneNumber);

    void checkIfUserIsAuthorized(ReadFromDatabaseCallback callback);

    void insertUserLoginInfo(InsertIntoDatabaseCallback callback, UserLoginInfo userLoginInfo);
}
