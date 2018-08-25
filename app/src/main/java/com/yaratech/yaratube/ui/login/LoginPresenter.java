package com.yaratech.yaratube.ui.login;

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View mView;

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
}
