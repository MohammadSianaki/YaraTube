package com.yaratech.yaratube.ui.login.phonenumberlogin;

import android.util.Log;

import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;
import com.yaratech.yaratube.data.source.UserDataSource;
import com.yaratech.yaratube.data.source.UserRepository;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class PhoneNumberLoginPresenter implements PhoneNumberLoginContract.Presenter {
    private static final String TAG = "PhoneNumberLoginPresent";
    private PhoneNumberLoginContract.View mView;
    private UserRepository repository;
    private CompositeDisposable compositeDisposable;

    public PhoneNumberLoginPresenter(UserRepository repository) {
        this.repository = repository;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void observePhoneNumberInput(Observable observable) {
        Observable o = observable
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

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        Disposable disposable = (Disposable) o.subscribeWith(new DisposableObserver<String>() {


            @Override
            public void onNext(String phoneNumber) {
                Log.d(TAG, "onNext() called with: phoneNumber = [" + phoneNumber + "]");
                repository.registerUserWithThisPhoneNumber(new UserDataSource.ApiResultCallback() {

                    @Override
                    public void onSuccessMessage(String message, int responseCode, Object response) {
                        Log.d(TAG, "onSuccessMessage() called with: message = [" + message + "], responseCode = [" + responseCode + "]");
                        mView.showVerificationCodeDialog(phoneNumber);
                    }

                    @Override
                    public void onErrorMessage(String message, int responseCode) {
                        Log.d(TAG, "onErrorMessage() called with: message = [" + message + "], responseCode = [" + responseCode + "]");
                        mView.showToastError(message);
                    }

                    @Override
                    public void onFailureMessage(String message, int responseCode) {
                        Log.d(TAG, "onFailureMessage() called with: message = [" + message + "], responseCode = [" + responseCode + "]");
                    }
                }, phoneNumber);
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

    @Override
    public void attachView(PhoneNumberLoginContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        compositeDisposable.clear();
        mView = null;
    }

    @Override
    public boolean isAttached() {
        return mView != null;
    }
}