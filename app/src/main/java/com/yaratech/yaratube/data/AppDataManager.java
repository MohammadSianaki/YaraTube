package com.yaratech.yaratube.data;

import com.yaratech.yaratube.data.model.db.User;
import com.yaratech.yaratube.data.source.local.db.DbHelper;
import com.yaratech.yaratube.data.source.local.prefs.PreferencesHelper;
import com.yaratech.yaratube.data.source.remote.ApiHelper;

import io.reactivex.disposables.Disposable;
import okhttp3.MultipartBody;


public class AppDataManager implements DataManager {
    //----------------------------------------------------------------------------------------------
    private static AppDataManager INSTANCE = null;
    private PreferencesHelper preferencesHelper;
    private DbHelper dbHelper;
    private ApiHelper apiHelper;

    public AppDataManager(PreferencesHelper preferencesHelper,
                          DbHelper dbHelper,
                          ApiHelper apiHelper) {
        this.preferencesHelper = preferencesHelper;
        this.dbHelper = dbHelper;
        this.apiHelper = apiHelper;
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

    @Override
    public Disposable registerUserWithThisGoogleApiToken(String googleToken, LoginApiResultCallback callback) {
        return apiHelper.registerUserWithThisGoogleApiToken(googleToken, callback);
    }

    @Override
    public Disposable uploadUserProfileImageAvatar(MultipartBody.Part image, String token, DashboardApiResultCallback callback) {
        return apiHelper.uploadUserProfileImageAvatar(image, token, callback);
    }

    @Override
    public Disposable uploadUserProfileInformation(String nickName, String dateOfBirth, String gender, String token, DashboardApiResultCallback callback) {
        return apiHelper.uploadUserProfileInformation(nickName, dateOfBirth, gender, token, callback);
    }

    @Override
    public Disposable loadUserProfileInformation(String token, DashboardApiResultCallback callback) {
        return apiHelper.loadUserProfileInformation(token, callback);
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
    public String getUserTokenApi() {
        return preferencesHelper.getUserTokenApi();
    }

    @Override
    public void setUserTokenApi(String token) {
        preferencesHelper.setUserTokenApi(token);
    }

    @Override
    public void clearPreferences() {
        preferencesHelper.clearPreferences();
    }

    @Override
    public void onStopActivity() {
        apiHelper.onStopActivity();
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
    public Disposable saveUserToDb(User user, SaveUserDatabaseResultCallback callback) {
        return dbHelper.saveUserToDb(user, callback);
    }

    @Override
    public Disposable clearDatabase() {
        return dbHelper.clearDatabase();
    }

    //endregion
}
