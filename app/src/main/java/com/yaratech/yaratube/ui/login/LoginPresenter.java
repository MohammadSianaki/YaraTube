package com.yaratech.yaratube.ui.login;

import com.yaratech.yaratube.data.source.UserRepository;

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View mView;
    private UserRepository userRepository;

    public LoginPresenter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void attachView(LoginContract.View view) {
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
    public void saveLoginStep(int loginStep) {
        userRepository.setUserLoginStep(loginStep);
    }

    @Override
    public void saveUserMobilePhoneNumber(String phoneNumber) {
        userRepository.setUserMobilePhoneNumber(phoneNumber);
    }
}
