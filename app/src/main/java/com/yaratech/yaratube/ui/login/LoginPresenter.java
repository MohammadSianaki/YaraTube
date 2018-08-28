package com.yaratech.yaratube.ui.login;

import com.yaratech.yaratube.data.model.Event;
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
    public void checkUserStepLogin() {
        int step = userRepository.getUserLoginStep();
        if (step == Event.LOGIN_STEP_FINISH) {
            mView.showUserHasBeenLoginToast();
        } else if (userRepository.getUserLoginStep() == Event.LOGIN_STEP_THREE) {
            mView.showVerificationDialog();
        } else {
            mView.showLoginMethodDialog();
        }
    }

    @Override
    public void saveUserMobilePhoneNumber(String phoneNumber) {
        userRepository.setUserMobilePhoneNumber(phoneNumber);
    }
}
