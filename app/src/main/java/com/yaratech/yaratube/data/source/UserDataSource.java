package com.yaratech.yaratube.data.source;

import com.yaratech.yaratube.data.source.local.UserLoginInfo;

import io.reactivex.disposables.Disposable;

public interface UserDataSource {

    void cancelPostCommentRequest();

    interface InsertIntoDatabaseCallback {


        void onUserLoginInserted();

        void onAddedToCompositeDisposable(Disposable disposable);

        void onFailureMessage(String message);


    }

    interface ReadFromDatabaseCallback {

        void onUserLoginInfoLoaded(UserLoginInfo userLoginInfo);

        void onAddedToCompositeDisposable(Disposable disposable);

        void onFailureMessage(String message);

        void onNotFoundUserInDatabase();

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

    void setUserMobilePhoneNumber(String mobilePhoneNumber);

    String getUserMobilePhoneNumber();

    void setUserLoginStep(int loginStep);

    int getUserLoginStep();

    void submitCommentToProduct(int productId, int score, String title, String textContent, String token, ApiResultCallback callback);

    void saveUserProfileImageAvatarPath(String imagePath);

    String loadUserProfileImageAvatarPath();
}
