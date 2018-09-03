package com.yaratech.yaratube.data.source.remote;

import com.yaratech.yaratube.data.DataManager;

import io.reactivex.disposables.Disposable;

public interface ApiHelper {
    //region Store Remote Api Access Methods

    Disposable fetchListOfCategories(DataManager.StoreApiResultCallback callback);

    void cancelListOfCategoriesApiRequest();

    Disposable fetchStoreItems(DataManager.StoreApiResultCallback callback);

    void cancelStoreItemsApiRequest();

    Disposable fetchProductsByCategoryId(int categoryId, int offset, int limit, DataManager.StoreApiResultCallback callback);

    void cancelProductsByCategoryIdApiRequest();

    Disposable fetchProductDetailsByProductId(int productId, String deviceOs, DataManager.StoreApiResultCallback callback);

    void cancelProductDetailsByProductIdApiRequest();

    Disposable fetchCommentListOfProductByProductId(int productId, int offset, int limit, DataManager.StoreApiResultCallback callback);

    void cancelCommentListOfProductByProductIdApiRequest();

    Disposable submitCommentToProduct(int productId, int score, String title, String commentText, String token, DataManager.CommentApiResultCallback callback);

    void cancelSubmitCommentToProductApiRequest();

    //endregion

    //region User Remote Api Access Methods

    Disposable registerUserWithThisPhoneNumber(String phoneNumber, String deviceId, String deviceModel, String deviceOs, DataManager.LoginApiResultCallback callback);

    Disposable verifyUserWithThisCode(String phoneNumber, String deviceId, String verificationCode, DataManager.LoginApiResultCallback callback);

    //endregions

}
