package com.yaratech.yaratube.ui.login.verification;

import android.util.Log;

import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;
import com.yaratech.yaratube.data.AppDataManager;
import com.yaratech.yaratube.data.DataManager;
import com.yaratech.yaratube.data.model.api.MobileLoginStepTwoResponse;
import com.yaratech.yaratube.data.model.db.User;
import com.yaratech.yaratube.data.model.db.UserLoginInfo;
import com.yaratech.yaratube.data.model.other.Event;
import com.yaratech.yaratube.utils.TextUtils;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;

public class VerificationPresenter implements VerificationContract.Presenter {
    private static final String TAG = "VerificationPresenter";
    private AppDataManager appDataManager;
    private final CompositeDisposable compositeDisposable;
    private VerificationContract.View mView;

    public VerificationPresenter(AppDataManager appDataManager) {
        this.appDataManager = appDataManager;
        this.compositeDisposable = new CompositeDisposable();
    }

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

    @Override
    public void unSubscribe() {
        if (isAttached()) {
            compositeDisposable.clear();
        }
    }

    @Override
    public void observeAutoReadVerificationCode(String phoneNumber, String verificationCode) {
        Disposable disposable = appDataManager.verifyUserWithThisCode(phoneNumber, verificationCode, new DataManager.LoginApiResultCallback() {
            @Override
            public void onSuccess(String message, int responseCode, Object response) {
                Log.d(TAG, "<<<<    AutoReadOTP     >>>>    onSuccessMessage() called with: message = [" + message + "], responseCode = [" + responseCode + "], response = [" + response + "]");
            }

            @Override
            public void onError(String message, int responseCode) {
                Log.d(TAG, "<<<<    AutoReadOTP     >>>>    onErrorMessage() called with: message = [" + message + "], responseCode = [" + responseCode + "]");
            }

            @Override
            public void onFailure(String message) {
                Log.d(TAG, "<<<<    AutoReadOTP     >>>>    onFailureMessage() called with: message = [" + message);
            }
        });
        compositeDisposable.add(disposable);
    }

    @Override
    public void observeVerificationCodeInput(Observable observable, String phoneNumber) {
        Observable o = observable
                .map(new Function<TextViewTextChangeEvent, String>() {

                    @Override
                    public String apply(TextViewTextChangeEvent textViewTextChangeEvent) throws Exception {
                        return textViewTextChangeEvent.text().toString();
                    }
                })
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Exception {
                        Log.d(TAG, "test() called with: s = [" + s + "]");
                        return !android.text.TextUtils.isEmpty(s) && TextUtils.isAllDigits(s) && s.length() >= 4;
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());

        Disposable disposable = (Disposable) o.subscribeWith(new DisposableObserver<String>() {
            @Override
            public void onNext(String verificationCode) {
                Disposable disposable1 = appDataManager.verifyUserWithThisCode(phoneNumber, verificationCode, new AppDataManager.LoginApiResultCallback() {

                    @Override
                    public void onSuccess(String message, int responseCode, Object response) {
                        ((VerificationCodeFragment) mView).sendMessageToParentFragment(new Event.ChildParentMessage(Event.MOBILE_PHONE_NUMBER_VERIFY_BUTTON_CLICK_MESSAGE, Event.LOGIN_STEP_FINISH));
                        MobileLoginStepTwoResponse mobileLoginStepTwoResponse = (MobileLoginStepTwoResponse) response;
                        UserLoginInfo userLoginInfo = new UserLoginInfo();
                        User user = new User();
                        user.setUserId(mobileLoginStepTwoResponse.getUserId());
                        user.setNickName(mobileLoginStepTwoResponse.getNickName());
                        user.setToken(mobileLoginStepTwoResponse.getToken());

                        userLoginInfo.setIsAuthorized(1);
                        userLoginInfo.setUser(user);

                        saveUserLoginInfoIntoDatabase(userLoginInfo);
                        Log.d(TAG, "onSuccessMessage() called with: message = [" + message + "], responseCode = [" + responseCode + "], response = [" + response + "]");
                        mView.closeDialog();
                    }

                    @Override
                    public void onError(String message, int responseCode) {
                        Log.d(TAG, "onErrorMessage() called with: message = [" + message + "], responseCode = [" + responseCode + "]");
                    }

                    @Override
                    public void onFailure(String message) {
                        Log.d(TAG, "onFailureMessage() called with: message = [" + message);
                    }
                });
                compositeDisposable.add(disposable1);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: ", e);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete() called");
            }
        });
        compositeDisposable.add(disposable);
    }

    private void saveUserLoginInfoIntoDatabase(UserLoginInfo userLoginInfo) {
        Disposable disposable = appDataManager.saveUserLoginInfo(userLoginInfo, new DataManager.LoginDatabaseResultCallback() {

            @Override
            public void onSuccess(Map<Boolean, String> map) {
                Log.d(TAG, "onSuccess() called with: aBoolean = [" + map.containsKey(true) + "]");
            }

            @Override
            public void onFailure(String message) {
                Log.d(TAG, "onFailure() called with: message = [" + message + "]");
            }
        });
    }


    @Override
    public String getUserMobilePhoneNumber() {
        return appDataManager.getUserMobilePhoneNumber();
    }
}
