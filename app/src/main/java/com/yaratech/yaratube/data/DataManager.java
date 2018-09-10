package com.yaratech.yaratube.data;

import com.yaratech.yaratube.data.source.local.db.DbHelper;
import com.yaratech.yaratube.data.source.local.prefs.PreferencesHelper;
import com.yaratech.yaratube.data.source.remote.ApiHelper;

import io.reactivex.disposables.Disposable;

public interface DataManager extends PreferencesHelper, DbHelper, ApiHelper {

    interface DashboardApiResultCallback<T> {

        void onDataLoaded(T response);

        void onDataNotAvailable();

        void onNetworkNotAvailable();
    }

    interface CommentApiResultCallback<T> {

        void onSuccess(String message, int responseCode);

        void onError(String message, int responseCode);

        void onFailure(String message);

    }

    interface LoginApiResultCallback<T> {

        void onSuccess(String message, int responseCode, T data);

        void onError(String message, int responseCode);

        void onFailure(String message);

    }

    interface LoginDatabaseResultCallback {

        void onSuccess(String token);

        void onFailure(String message);

    }

    interface SaveUserDatabaseResultCallback {
        void onSuccess(boolean aBoolean);

        void onFailure(String message);

    }

    //region Store Remote Api Access Methods


    Disposable fetchListOfCategories(DashboardApiResultCallback callback);


    Disposable fetchStoreItems(DashboardApiResultCallback callback);


    Disposable fetchProductsByCategoryId(int categoryId, int offset, int limit, DashboardApiResultCallback callback);


    Disposable fetchProductDetailsByProductId(int productId, String deviceOs, DashboardApiResultCallback callback);


    Disposable fetchCommentListOfProductByProductId(int productId, int offset, int limit, DashboardApiResultCallback callback);


    Disposable submitCommentToProduct(int productId, int score, String title, String commentText, String token, CommentApiResultCallback callback);


    //endregion

    //region User Remote Api Access Methods

    Disposable registerUserWithThisPhoneNumber(String phoneNumber, LoginApiResultCallback callback);

    Disposable verifyUserWithThisCode(String phoneNumber, String verificationCode, LoginApiResultCallback callback);

    //endregions

    //region Local Shared Preferences Access Methods

    void setUserMobilePhoneNumber(String mobilePhoneNumber);

    void setUserLoginStep(int loginStep);

    void setUserProfileImageAvatarPath(String imagePath);

    int getUserLoginStep();

    String getUserProfileImageAvatarPath();

    String getUserMobilePhoneNumber();

    //endregion
}
