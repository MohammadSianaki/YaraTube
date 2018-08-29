package com.yaratech.yaratube.ui.player;

public class PlayerPresenter implements PlayerContract.Presenter {


    PlayerContract.View mView;

    @Override
    public void attachView(PlayerContract.View view) {
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
