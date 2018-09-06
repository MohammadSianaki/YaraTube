package com.yaratech.yaratube.ui.login.loginmethod;

public class LoginMethodPresenter implements LoginMethodContract.Presenter {

    private LoginMethodContract.View mView;


    @Override
    public void getMobilePhoneNumber() {
        mView.showEnterMobileNumberDialog();
    }

    @Override
    public void attachView(LoginMethodContract.View view) {
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
        //no-op
    }
}
