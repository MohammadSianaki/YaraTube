package com.yaratech.yaratube.ui.login;

import com.yaratech.yaratube.data.AppDataManager;
import com.yaratech.yaratube.data.model.other.Event;

import io.reactivex.disposables.CompositeDisposable;

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View mView;
    private AppDataManager appDataManager;
    private CompositeDisposable compositeDisposable;

    public LoginPresenter(AppDataManager appDataManager) {
        this.appDataManager = appDataManager;
        this.compositeDisposable = new CompositeDisposable();
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
    public void unSubscribe() {
        if (isAttached()) {
            compositeDisposable.clear();
        }
    }

    @Override
    public void saveLoginStep(int loginStep) {
        appDataManager.setUserLoginStep(loginStep);
    }

    @Override
    public void checkUserStepLogin() {
        int step = appDataManager.getUserLoginStep();
        if (step == Event.LOGIN_STEP_FINISH) {
            mView.showUserHasBeenLoginToast();
        } else if (appDataManager.getUserLoginStep() == Event.LOGIN_STEP_THREE) {
            mView.showVerificationDialog();
        } else {
            mView.showLoginMethodDialog();
        }
    }

    @Override
    public void saveUserMobilePhoneNumber(String phoneNumber) {
        appDataManager.setUserMobilePhoneNumber(phoneNumber);
    }
}
