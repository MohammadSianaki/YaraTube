package com.yaratech.yaratube.ui.profile;

import android.util.Log;

import com.yaratech.yaratube.data.AppDataManager;
import com.yaratech.yaratube.data.DataManager;
import com.yaratech.yaratube.data.model.api.GetProfileResponse;
import com.yaratech.yaratube.data.model.api.PostProfileResponse;

import java.io.File;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ProfilePresenter implements ProfileContract.Presenter {
    private static final String TAG = "ProfilePresenter";
    private ProfileContract.View mView;
    private AppDataManager appDataManager;
    private CompositeDisposable compositeDisposable;
    private String token;

    public ProfilePresenter(AppDataManager appDataManager, CompositeDisposable compositeDisposable) {
        this.appDataManager = appDataManager;
        this.compositeDisposable = compositeDisposable;
        this.token = appDataManager.getUserTokenApi();
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
    public void uploadUserProfileInfo(String name, String birthday, String gender) {
        Log.d(TAG, "uploadUserProfileInfo() called with: name = [" + name + "], gender = [" + gender + "], birthday = [" + birthday + "]");
        Disposable disposable = appDataManager
                .uploadUserProfileInformation(name,
                        validateBirthday(birthday),
                        validateGender(gender),
                        token,
                        new DataManager.DashboardApiResultCallback() {
                            @Override
                            public void onDataLoaded(Object response) {
                                mView.showSubmitSuccessfulMessage();
                            }

                            @Override
                            public void onDataNotAvailable() {
                                mView.showDataNotAvailableMessage();
                            }

                            @Override
                            public void onNetworkNotAvailable() {

                            }
                        });
        compositeDisposable.add(disposable);
    }

    @Override
    public void uploadUserProfileImageAvatar(String filePath) {
        Log.d(TAG, "uploadUserProfileImageAvatar() called with: filePath = [" + filePath + "]");
        File file = new File(filePath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("upload", file.getName(), requestBody);
        Disposable disposable = appDataManager.uploadUserProfileImageAvatar(body, token, new DataManager.DashboardApiResultCallback() {
            @Override
            public void onDataLoaded(Object response) {
                PostProfileResponse profileResponse = (PostProfileResponse) response;
                Log.d(TAG, "onDataLoaded() called with: response = [" + profileResponse.getMessage() + "]");
            }

            @Override
            public void onDataNotAvailable() {
                mView.showDataNotAvailableMessage();
            }

            @Override
            public void onNetworkNotAvailable() {
                //no-op
            }
        });
        compositeDisposable.add(disposable);
    }

    @Override
    public void loadUserProfileInfo() {
        Disposable disposable = appDataManager.loadUserProfileInformation(token, new DataManager.DashboardApiResultCallback() {
            @Override
            public void onDataLoaded(Object response) {
                GetProfileResponse getProfileResponse = (GetProfileResponse) response;
                mView.showLoadedUserProfileInformation(getProfileResponse);
            }

            @Override
            public void onDataNotAvailable() {
                mView.showDataNotAvailableMessage();
            }

            @Override
            public void onNetworkNotAvailable() {
                //no-op
            }
        });
        compositeDisposable.add(disposable);
    }

    private String validateBirthday(String birthday) {
        String[] items = birthday.split("/");
        for (String str : items
                ) {
            Log.d(TAG, "validateBirthday: item= " + str);
        }
        if (!items[1].startsWith("0")) {
            items[1] = "0" + items[1];
        }
        if (!items[2].startsWith("0")) {
            items[2] = "0" + items[2];
        }
        return items[0] + "-" + items[1] + "-" + items[2];
    }

    private String validateGender(String gender) {
        if (gender.equals("مرد")) {
            return "male";
        } else {
            return "female";
        }
    }
}
