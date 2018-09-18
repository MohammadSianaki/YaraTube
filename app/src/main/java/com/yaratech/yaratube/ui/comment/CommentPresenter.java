package com.yaratech.yaratube.ui.comment;

import android.util.Log;

import com.yaratech.yaratube.data.AppDataManager;
import com.yaratech.yaratube.data.DataManager;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class CommentPresenter implements CommentContract.Presenter {
    private static final String TAG = "CommentPresenter";
    private CommentContract.View mView;
    private AppDataManager appDataManager;
    private CompositeDisposable compositeDisposable;

    public CommentPresenter(AppDataManager appDataManager) {
        this.appDataManager = appDataManager;
        this.compositeDisposable = new CompositeDisposable();
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
    public void unSubscribe() {
        if (isAttached()) {
            compositeDisposable.clear();
        }
    }

    @Override
    public void submitCommentToProduct(int productId, int score, String title, String textContent, String token) {
        Disposable disposable = appDataManager.submitCommentToProduct(productId, score, title, textContent, token, new DataManager.CommentApiResultCallback() {
            @Override
            public void onSuccess(String message, int responseCode) {
                mView.showToast("دیدگاه شما با موفقیت ثبت گردید");
                mView.closeDialog();
            }

            @Override
            public void onError(String message, int responseCode) {
                if (responseCode == -1) {
                    mView.showToast(message);
                }
                Log.d(TAG, "onErrorMessage: " + message + "   response code [" + responseCode + "]");
            }

            @Override
            public void onFailure(String message) {
                Log.d(TAG, "onFailure() called with: message = [" + message + "]");
            }
        });
        compositeDisposable.add(disposable);
    }

}
