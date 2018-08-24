package com.yaratech.yaratube.ui.login.verification;

import android.util.Log;

import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;
import com.yaratech.yaratube.data.source.UserDataSource;
import com.yaratech.yaratube.data.source.UserRepository;
import com.yaratech.yaratube.utils.TextUtils;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class VerificationPresenter implements VerificationContract.Presenter {
    private static final String TAG = "VerificationPresenter";
    private final UserRepository repository;
    private final CompositeDisposable compositeDisposable;
    private VerificationContract.View mView;

    public VerificationPresenter(UserRepository repository) {
        this.repository = repository;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void attachView(VerificationContract.View view) {
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
                        return TextUtils.isAllDigits(s);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Disposable disposable = (Disposable) o.subscribeWith(new DisposableObserver<String>() {
            @Override
            public void onNext(String code) {
                repository.verifyUserWithThisCode(new UserDataSource.ApiResultCallback() {

                    @Override
                    public void onSuccessMessage(String message, int responseCode, Object response) {
                        Log.d(TAG, "onSuccessMessage() called with: message = [" + message + "], responseCode = [" + responseCode + "], response = [" + response + "]");
                    }

                    @Override
                    public void onErrorMessage(String message, int responseCode) {
                        Log.d(TAG, "onErrorMessage() called with: message = [" + message + "], responseCode = [" + responseCode + "]");
                    }

                    @Override
                    public void onFailureMessage(String message, int responseCode) {
                        Log.d(TAG, "onFailureMessage() called with: message = [" + message + "], responseCode = [" + responseCode + "]");
                    }
                }, code, phoneNumber);
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
}