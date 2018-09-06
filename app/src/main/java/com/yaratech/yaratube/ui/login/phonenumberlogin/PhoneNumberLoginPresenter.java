package com.yaratech.yaratube.ui.login.phonenumberlogin;

import android.util.Log;

import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;
import com.yaratech.yaratube.data.AppDataManager;
import com.yaratech.yaratube.data.DataManager;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;

public class PhoneNumberLoginPresenter implements PhoneNumberLoginContract.Presenter {
    private static final String TAG = "PhoneNumberLoginPresent";
    private PhoneNumberLoginContract.View mView;
    private AppDataManager appDataManager;
    private CompositeDisposable compositeDisposable;

    public PhoneNumberLoginPresenter(AppDataManager appDataManager) {
        this.appDataManager = appDataManager;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void observePhoneNumberInput(Observable observable) {
        Observer observer = observable
                .map(new Function<TextViewTextChangeEvent, String>() {
                    @Override
                    public String apply(TextViewTextChangeEvent textViewTextChangeEvent) throws Exception {
                        return textViewTextChangeEvent.text().toString();
                    }
                })
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String input) throws Exception {
                        return input.startsWith("09") && input.length() == 11;
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<String>() {


                    @Override
                    public void onNext(String phoneNumber) {
                        Log.d(TAG, "onNext() called with: phoneNumber = [" + phoneNumber + "]");
                        Disposable disposable1 = appDataManager.registerUserWithThisPhoneNumber(phoneNumber, new DataManager.LoginApiResultCallback() {

                            @Override
                            public void onSuccess(String message, int responseCode, Object data) {
                                Log.d(TAG, "onSuccessMessage() called with: message = [" + message + "], responseCode = [" + responseCode + "]");
                                mView.showVerificationCodeDialog();
                                saveMobilePhoneNumber(phoneNumber);
                            }

                            @Override
                            public void onError(String message, int responseCode) {
                                Log.d(TAG, "onErrorMessage() called with: message = [" + message + "], responseCode = [" + responseCode + "]");
                                mView.showToastError(message);
                            }

                            @Override
                            public void onFailure(String message) {
                                Log.d(TAG, "onFailure() called with: message = [" + message + "]");
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
        compositeDisposable.add((Disposable) observer);
    }

    private void saveMobilePhoneNumber(String phoneNumber) {
        appDataManager.setUserMobilePhoneNumber(phoneNumber);
    }

    @Override
    public void attachView(PhoneNumberLoginContract.View view) {
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
