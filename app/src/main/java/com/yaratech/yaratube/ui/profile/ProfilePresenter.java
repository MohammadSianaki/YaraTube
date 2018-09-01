package com.yaratech.yaratube.ui.profile;

import com.yaratech.yaratube.data.source.UserRepository;

public class ProfilePresenter implements ProfileContract.Presenter {

    private ProfileContract.View mView;
    private UserRepository userRepository;

    public ProfilePresenter(UserRepository userRepository) {
        this.userRepository = userRepository;
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
    public String getUserProfileImageAvatarPath() {
        return userRepository.loadUserProfileImageAvatarPath();
    }

    @Override
    public void saveUserProfileImageAvatarPath(String imagePath) {
        userRepository.saveUserProfileImageAvatarPath(imagePath);
    }

    @Override
    public void uploadUserProfileInfo() {

    }
}
