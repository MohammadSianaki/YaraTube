package com.yaratech.yaratube.ui.comment;

import android.util.Log;

public class CommentPresenter implements CommentContract.Presenter {
    private static final String TAG = "CommentPresenter";
    private CommentContract.View mView;
    private UserRepository userRepository;

    public CommentPresenter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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

    @Override
    public void submitCommentToProduct(int productId, int score, String title, String textContent, String token) {
        userRepository.submitCommentToProduct(productId, score, title, textContent, token, new UserDataSource.UserApiResultCallback() {
            @Override
            public void onSuccessMessage(String message, int responseCode, Object response) {
                mView.showToast("دیدگاه شما با موفقیت ثبت گردید");
                mView.closeDialog();
            }

            @Override
            public void onErrorMessage(String message, int responseCode) {
                if (responseCode == -1) {
                    mView.showToast(message);
                }
                Log.d(TAG, "onErrorMessage: " + message + "   response code [" + responseCode + "]");
            }

            @Override
            public void onFailureMessage(String message, int responseCode) {
                Log.d(TAG, "onFailureMessage: " + message + "   response code [" + responseCode + "]");
            }
        });
    }

    @Override
    public void cancelPostComment() {
        userRepository.cancelPostCommentRequest();
    }
}
