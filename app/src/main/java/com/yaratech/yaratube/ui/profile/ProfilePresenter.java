package com.yaratech.yaratube.ui.profile;

import android.util.Log;

import com.yaratech.yaratube.data.AppDataManager;
import com.yaratech.yaratube.data.DataManager;
import com.yaratech.yaratube.data.model.api.PostProfileResponse;

import java.io.File;

import io.reactivex.disposables.CompositeDisposable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ProfilePresenter implements ProfileContract.Presenter {
    private static final String TAG = "ProfilePresenter";
    private ProfileContract.View mView;
    private AppDataManager appDataManager;
    private CompositeDisposable compositeDisposable;

    public ProfilePresenter(AppDataManager appDataManager) {
        this.appDataManager = appDataManager;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void attachView(ProfileContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public boolean isAttached() {
        return mView != null;
    }

    @Override
    public void unSubscribe() {
        if (isAttached()) {
            compositeDisposable.clear();
        }
    }

    @Override
    public String getUserProfileImageAvatarPath() {
        return appDataManager.getUserProfileImageAvatarPath();
    }

    @Override
    public void saveUserProfileImageAvatarPath(String imagePath) {
        appDataManager.setUserProfileImageAvatarPath(imagePath);
    }

    @Override
    public void uploadUserProfileInfo() {

    }

    @Override
    public void uploadUserProfileImageAvatar(String filePath) {
        Log.d(TAG, "uploadUserProfileImageAvatar() called with: filePath = [" + filePath + "]");
        File file = new File(filePath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("upload", file.getName(), requestBody);
        String token = appDataManager.getUserTokenApi();
        appDataManager.uploadUserProfileImageAvatar(body, token, new DataManager.DashboardApiResultCallback() {
            @Override
            public void onDataLoaded(Object response) {
                PostProfileResponse profileResponse = (PostProfileResponse) response;
                Log.d(TAG, "onDataLoaded() called with: response = [" + profileResponse.getMessage() + "]");
            }

            @Override
            public void onDataNotAvailable() {
                // no-op
            }

            @Override
            public void onNetworkNotAvailable() {
                //no-op
            }
        });
    }
}
