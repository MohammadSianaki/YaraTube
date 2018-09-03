package com.yaratech.yaratube.data;

import android.content.Context;

import com.yaratech.yaratube.data.model.db.UserLoginInfo;
import com.yaratech.yaratube.data.source.local.db.DbHelper;
import com.yaratech.yaratube.data.source.local.prefs.PreferencesHelper;
import com.yaratech.yaratube.data.source.remote.ApiHelper;

import io.reactivex.disposables.Disposable;

public class AppDataManager implements DataManager {
    //----------------------------------------------------------------------------------------------
    private static AppDataManager INSTANCE = null;
    private PreferencesHelper preferencesHelper;
    private DbHelper dbHelper;
    private ApiHelper apiHelper;
    private Context context;

    private AppDataManager(PreferencesHelper preferencesHelper,
                           DbHelper dbHelper,
                           ApiHelper apiHelper,
                           Context context) {
        this.preferencesHelper = preferencesHelper;
        this.dbHelper = dbHelper;
        this.apiHelper = apiHelper;
        this.context = context;
    }

    public static AppDataManager getINSTANCE(PreferencesHelper preferencesHelper,
                                             DbHelper dbHelper,
                                             ApiHelper apiHelper,
                                             Context context) {
        if (INSTANCE == null) {
            INSTANCE = new AppDataManager(preferencesHelper, dbHelper, apiHelper, context);
        }
        return INSTANCE;
    }

//----------------------------------------------------------------------------------------------

    //region Store Remote Api Request Implementation

    @Override
    public Disposable fetchListOfCategories(StoreApiResultCallback callback) {
        return apiHelper.fetchListOfCategories(callback);
    }

    @Override
    public void cancelListOfCategoriesApiRequest() {
        apiHelper.cancelListOfCategoriesApiRequest();
    }

    @Override
    public Disposable fetchStoreItems(StoreApiResultCallback callback) {
        return apiHelper.fetchStoreItems(callback);
    }

    @Override
    public void cancelStoreItemsApiRequest() {
        apiHelper.cancelStoreItemsApiRequest();
    }

    @Override
    public Disposable fetchProductsByCategoryId(int categoryId, int offset, int limit, StoreApiResultCallback callback) {
        return apiHelper.fetchProductsByCategoryId(categoryId, offset, limit, callback);
    }

    @Override
    public void cancelProductsByCategoryIdApiRequest() {
        apiHelper.cancelProductsByCategoryIdApiRequest();
    }

    @Override
    public Disposable fetchProductDetailsByProductId(int productId, String deviceOs, StoreApiResultCallback callback) {
        return apiHelper.fetchProductDetailsByProductId(productId, deviceOs, callback);
    }

    @Override
    public void cancelProductDetailsByProductIdApiRequest() {
        apiHelper.cancelProductDetailsByProductIdApiRequest();
    }

    @Override
    public Disposable fetchCommentListOfProductByProductId(int productId, int offset, int limit, StoreApiResultCallback callback) {
        return apiHelper.fetchCommentListOfProductByProductId(productId, offset, limit, callback);
    }

    @Override
    public void cancelCommentListOfProductByProductIdApiRequest() {
        apiHelper.cancelCommentListOfProductByProductIdApiRequest();
    }

    @Override
    public Disposable submitCommentToProduct(int productId, int score, String title, String commentText, String token, CommentApiResultCallback callback) {
        return apiHelper.submitCommentToProduct(productId, score, title, commentText, token, callback);
    }

    @Override
    public void cancelSubmitCommentToProductApiRequest() {
        apiHelper.cancelSubmitCommentToProductApiRequest();
    }

    //endregion

    //region User Remote Api Request Implementation

    @Override
    public Disposable registerUserWithThisPhoneNumber(String phoneNumber, String deviceId, String deviceModel, String deviceOs, LoginApiResultCallback callback) {
        return apiHelper.
                registerUserWithThisPhoneNumber(
                        phoneNumber,
                        deviceId,
                        deviceModel,
                        deviceOs,
                        callback);
    }

    @Override
    public Disposable verifyUserWithThisCode(String phoneNumber, String deviceId, String verificationCode, LoginApiResultCallback callback) {
        return apiHelper
                .verifyUserWithThisCode(
                        phoneNumber,
                        deviceId,
                        verificationCode,
                        callback);
    }

    //endregion

    //region Local SharedPreferences Implementation

    @Override
    public void setUserMobilePhoneNumber(String mobilePhoneNumber) {
        preferencesHelper.setUserMobilePhoneNumber(mobilePhoneNumber);
    }

    @Override
    public void setUserLoginStep(int loginStep) {
        preferencesHelper.setUserLoginStep(loginStep);
    }

    @Override
    public void setUserProfileImageAvatarPath(String imagePath) {
        preferencesHelper.setUserProfileImageAvatarPath(imagePath);
    }

    @Override
    public int getUserLoginStep() {
        return preferencesHelper.getUserLoginStep();
    }

    @Override
    public String getUserProfileImageAvatarPath() {
        return preferencesHelper.getUserProfileImageAvatarPath();
    }

    @Override
    public String getUserMobilePhoneNumber() {
        return preferencesHelper.getUserMobilePhoneNumber();
    }

    //endregion

    //region Local Database Implementation

    @Override
    public Disposable isUserAuthorized(LoginDatabaseResultCallback callback) {
        return dbHelper.isUserAuthorized(callback);
    }

    @Override
    public Disposable saveUserLoginInfo(UserLoginInfo userLoginInfo, LoginDatabaseResultCallback callback) {
        return dbHelper.saveUserLoginInfo(userLoginInfo, callback);
    }

    @Override
    public Disposable clearDatabase() {
        return dbHelper.clearDatabase();
    }

    //endregion
}
