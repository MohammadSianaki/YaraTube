package com.yaratech.yaratube.data;

import com.yaratech.yaratube.data.source.local.db.DbHelper;
import com.yaratech.yaratube.data.source.local.prefs.PreferencesHelper;
import com.yaratech.yaratube.data.source.remote.ApiHelper;

import io.reactivex.disposables.Disposable;

public interface DataManager extends PreferencesHelper, DbHelper, ApiHelper {

    interface StoreApiResultCallback<T> {

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

        void onSuccess(boolean aBoolean);

        void onFailure(String message);

    }

    //region Store Remote Api Access Methods


    Disposable fetchListOfCategories(StoreApiResultCallback callback);

    void cancelListOfCategoriesApiRequest();

    Disposable fetchStoreItems(StoreApiResultCallback callback);

    void cancelStoreItemsApiRequest();

    Disposable fetchProductsByCategoryId(int categoryId, int offset, int limit, StoreApiResultCallback callback);

    void cancelProductsByCategoryIdApiRequest();

    Disposable fetchProductDetailsByProductId(int productId, String deviceOs, StoreApiResultCallback callback);

    void cancelProductDetailsByProductIdApiRequest();

    Disposable fetchCommentListOfProductByProductId(int productId, int offset, int limit, StoreApiResultCallback callback);

    void cancelCommentListOfProductByProductIdApiRequest();

    Disposable submitCommentToProduct(int productId, int score, String title, String commentText, String token, CommentApiResultCallback callback);

    void cancelSubmitCommentToProductApiRequest();

    //endregion

    //region User Remote Api Access Methods

    Disposable registerUserWithThisPhoneNumber(String phoneNumber, String deviceId, String deviceModel, String deviceOs, LoginApiResultCallback callback);

    Disposable verifyUserWithThisCode(String phoneNumber, String deviceId, String verificationCode, LoginApiResultCallback callback);

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
