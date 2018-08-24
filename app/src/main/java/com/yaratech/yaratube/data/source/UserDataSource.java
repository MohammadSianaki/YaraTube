package com.yaratech.yaratube.data.source;

import com.yaratech.yaratube.data.source.local.UserLoginInfo;

import io.reactivex.disposables.Disposable;

public interface UserDataSource {


    interface DatabaseResultCallback<T> {
        void onUserLoginInfoLoaded(T data);

        void onUserLoginInserted();

        void onUserIsAuthorized(boolean isAuthorized);

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

    void checkIfUserIsAuthorized(UserDataSource.DatabaseResultCallback callback);

    void insertUserLoginInfo(UserDataSource.DatabaseResultCallback callback, UserLoginInfo userLoginInfo);
}
