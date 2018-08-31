package com.yaratech.yaratube.ui.profile;

public class ProfilePresenter implements ProfileContract.Presenter {

    private ProfileContract.View mView;


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
}
