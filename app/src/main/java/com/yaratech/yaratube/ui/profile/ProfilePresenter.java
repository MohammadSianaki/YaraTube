package com.yaratech.yaratube.ui.profile;

import com.yaratech.yaratube.data.AppDataManager;

import io.reactivex.disposables.CompositeDisposable;

public class ProfilePresenter implements ProfileContract.Presenter {

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
}
