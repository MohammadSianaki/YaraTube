package com.yaratech.yaratube.ui.login.loginmethod;

import android.util.Log;

import com.yaratech.yaratube.data.AppDataManager;
import com.yaratech.yaratube.data.DataManager;
import com.yaratech.yaratube.data.model.api.GoogleLoginResponse;
import com.yaratech.yaratube.data.model.db.User;
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
    public void saveUserLoginInfoIntoDatabase(User user) {
        appDataManager.saveUserToDb(user, new DataManager.SaveUserDatabaseResultCallback() {
            @Override
            public void onSuccess(boolean aBoolean) {
                Log.d(TAG, "onSuccess() called with: aBoolean = [" + aBoolean + "]");
                mView.sendMessageToParentFragment
                        (new Event.ChildParentMessage(Event.GOOGLE_SIGN_IN_SUCCESSFUL_MESSAGE, Event.LOGIN_STEP_FINISH));
            }

            @Override
            public void onFailure(String message) {
                Log.d(TAG, "onFailure() called with: message = [" + message + "]");
            }
        });
    }

    @Override
    public void getMobilePhoneNumber() {
        mView.showEnterMobileNumberDialog();
    }

    @Override
    public void performGoogleSignIn(String token, String givenName, String email, String photoUrl) {
        Disposable disposable = appDataManager.registerUserWithThisGoogleApiToken(token, new DataManager.LoginApiResultCallback() {
            @Override
            public void onSuccess(String message, int responseCode, Object data) {
                Log.d(TAG, "onSuccess() called with: message = [" + message + "], responseCode = [" + responseCode);
                GoogleLoginResponse googleLoginResponse = (GoogleLoginResponse) data;
                //  Creating User To Save in DB
                User user = new User();
                user.setEmail(email);
                user.setNickName(givenName);
                user.setPhotoUrl(photoUrl);
                user.setToken(googleLoginResponse.getToken());
                // Save User to Database
                saveUserLoginInfoIntoDatabase(user);
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
