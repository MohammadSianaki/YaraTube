package com.yaratech.yaratube.ui.login.loginmethod;

import android.util.Log;

import com.yaratech.yaratube.data.AppDataManager;
import com.yaratech.yaratube.data.DataManager;
import com.yaratech.yaratube.data.model.other.Event;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class LoginMethodPresenter implements LoginMethodContract.Presenter {
    private static final String TAG = "LoginMethodPresenter";
    private LoginMethodContract.View mView;
    private AppDataManager appDataManager;
    private CompositeDisposable compositeDisposable;

    public LoginMethodPresenter(AppDataManager appDataManager) {
        this.appDataManager = appDataManager;
        this.compositeDisposable = new CompositeDisposable();
    }


    @Override
    public void getMobilePhoneNumber() {
        mView.showEnterMobileNumberDialog();
    }

    @Override
    public void performGoogleSignIn(String token) {
        Disposable disposable = appDataManager.registerUserWithThisGoogleApiToken(token, new DataManager.LoginApiResultCallback() {
            @Override
            public void onSuccess(String message, int responseCode, Object data) {
                Log.d(TAG, "onSuccess() called with: message = [" + message + "], responseCode = [" + responseCode);
                mView.sendMessageToParentFragment
                        (new Event.ChildParentMessage(Event.GOOGLE_SIGN_IN_SUCCESSFUL_MESSAGE, Event.LOGIN_STEP_FINISH));
            }

            @Override
            public void onError(String message, int responseCode) {
                Log.d(TAG, "onError() called with: message = [" + message + "], responseCode = [" + responseCode + "]");
            }

            @Override
            public void onFailure(String message) {
                Log.d(TAG, "onFailure() called with: message = [" + message + "]");
            }
        });
        compositeDisposable.add(disposable);
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
        if (isAttached()) {
            compositeDisposable.clear();
        }
    }
}
