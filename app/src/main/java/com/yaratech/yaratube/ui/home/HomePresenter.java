package com.yaratech.yaratube.ui.home;

public class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View mView;

    @Override
    public void attachView(HomeContract.View view) {
        mView = view;
    }

    @Override
    public void detachView(HomeContract.View view) {
        mView = null;
    }

    @Override
    public boolean isAttached() {
        return mView != null;
    }
}
