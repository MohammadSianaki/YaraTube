package com.yaratech.yaratube.data.source.remote;

import com.yaratech.yaratube.data.DataManager;

import io.reactivex.disposables.Disposable;
import okhttp3.MultipartBody;

public interface ApiHelper {

    //region Store Remote Api Access Methods

    Disposable fetchListOfCategories(DataManager.DashboardApiResultCallback callback);


    Disposable fetchStoreItems(DataManager.DashboardApiResultCallback callback);


    Disposable fetchProductsByCategoryId(int categoryId, int offset, int limit, DataManager.DashboardApiResultCallback callback);


    Disposable fetchProductDetailsByProductId(int productId, String deviceOs, DataManager.DashboardApiResultCallback callback);


    Disposable fetchCommentListOfProductByProductId(int productId, int offset, int limit, DataManager.DashboardApiResultCallback callback);


    Disposable submitCommentToProduct(int productId, int score, String title, String commentText, String token, DataManager.CommentApiResultCallback callback);


    //endregion

    //region User Remote Api Access Methods

    Disposable registerUserWithThisPhoneNumber(String phoneNumber, DataManager.LoginApiResultCallback callback);

    Disposable verifyUserWithThisCode(String phoneNumber, String verificationCode, DataManager.LoginApiResultCallback callback);

    Disposable registerUserWithThisGoogleApiToken(String googleToken, DataManager.LoginApiResultCallback callback);

    //endregions

    //region Profile Remote Api Access Methods


    Disposable uploadUserProfileImageAvatar(MultipartBody.Part image,
                                            String token,
                                            DataManager.DashboardApiResultCallback callback);

    Disposable uploadUserProfileInformation(String nickName,
                                            String dateOfBirth,
                                            String gender, String token, DataManager.DashboardApiResultCallback callback);

    Disposable loadUserProfileInformation(String token, DataManager.DashboardApiResultCallback callback);
    // endregion

    void onStopActivity();

}
