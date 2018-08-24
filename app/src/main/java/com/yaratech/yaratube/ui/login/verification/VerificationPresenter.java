package com.yaratech.yaratube.ui.login.verification;

public class VerificationPresenter implements VerificationContract.Presenter {

    private VerificationContract.View mView;


    @Override
    public void attachView(VerificationContract.View view) {
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
