package com.yaratech.yaratube.data;

import com.yaratech.yaratube.data.model.db.UserLoginInfo;
import com.yaratech.yaratube.data.source.local.db.DbHelper;
import com.yaratech.yaratube.data.source.local.prefs.PreferencesHelper;
import com.yaratech.yaratube.data.source.remote.ApiHelper;

import java.util.Map;

import io.reactivex.disposables.Disposable;

public class AppDataManager implements DataManager {
    //----------------------------------------------------------------------------------------------
    private static AppDataManager INSTANCE = null;
    private PreferencesHelper preferencesHelper;
    private DbHelper dbHelper;
    private ApiHelper apiHelper;

    private AppDataManager(PreferencesHelper preferencesHelper,
                           DbHelper dbHelper,
                           ApiHelper apiHelper) {
        this.preferencesHelper = preferencesHelper;
        this.dbHelper = dbHelper;
        this.apiHelper = apiHelper;
    }

    public static AppDataManager getINSTANCE(PreferencesHelper preferencesHelper,
                                             DbHelper dbHelper,
                                             ApiHelper apiHelper) {
        if (INSTANCE == null) {
            INSTANCE = new AppDataManager(preferencesHelper, dbHelper, apiHelper);
        }
        return INSTANCE;
    }

//----------------------------------------------------------------------------------------------

    //region Store Remote Api Request Implementation

    @Override
    public Disposable fetchListOfCategories(DashboardApiResultCallback callback) {
        return apiHelper.fetchListOfCategories(callback);
    }

    @Override
    public Disposable fetchStoreItems(DashboardApiResultCallback callback) {
        return apiHelper.fetchStoreItems(callback);
    }

    @Override
    public Disposable fetchProductsByCategoryId(int categoryId, int offset, int limit, DashboardApiResultCallback callback) {
        return apiHelper.fetchProductsByCategoryId(categoryId, offset, limit, callback);
    }

    @Override
    public Disposable fetchProductDetailsByProductId(int productId, String deviceOs, DashboardApiResultCallback callback) {
        return apiHelper.fetchProductDetailsByProductId(productId, deviceOs, callback);
    }

    @Override
    public Disposable fetchCommentListOfProductByProductId(int productId, int offset, int limit, DashboardApiResultCallback callback) {
        return apiHelper.fetchCommentListOfProductByProductId(productId, offset, limit, callback);
    }

    @Override
    public Disposable submitCommentToProduct(int productId, int score, String title, String commentText, String token, CommentApiResultCallback callback) {
        return apiHelper.submitCommentToProduct(productId, score, title, commentText, token, callback);
    }

    //endregion

    //region User Remote Api Request Implementation

    @Override
    public Disposable registerUserWithThisPhoneNumber(String phoneNumber, LoginApiResultCallback callback) {
        return apiHelper.
                registerUserWithThisPhoneNumber(
                        phoneNumber,
                        callback);
    }

    @Override
    public Disposable verifyUserWithThisCode(String phoneNumber, String verificationCode, LoginApiResultCallback callback) {
        return apiHelper
                .verifyUserWithThisCode(
                        phoneNumber,
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
    public Disposable saveUserLoginInfo(UserLoginInfo userLoginInfo, SaveUserDatabaseResultCallback callback) {
        return dbHelper.saveUserLoginInfo(userLoginInfo, callback);
    }

    @Override
    public Disposable clearDatabase(LoginDatabaseResultCallback loginDatabaseResultCallback) {
        return dbHelper.clearDatabase(new LoginDatabaseResultCallback() {
            @Override
            public void onSuccess(Map<Boolean, String> map) {
            }

            @Override
            public void onFailure(String message) {
            }
        });
    }

    //endregion
}
