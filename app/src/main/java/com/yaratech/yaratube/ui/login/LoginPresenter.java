package com.yaratech.yaratube.ui.login;

import com.yaratech.yaratube.data.source.Repository;

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View mView;
    private Repository repository;




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
    public boolean checkIfUserAuthorized() {
        return false;
    }
}
