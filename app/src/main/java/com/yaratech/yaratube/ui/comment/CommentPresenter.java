package com.yaratech.yaratube.ui.comment;

public class CommentPresenter implements CommentContract.Presenter {

    private CommentContract.View mView;


    @Override
    public void attachView(CommentContract.View view) {
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
