package com.yaratech.yaratube.data.source.remote;

import android.content.Context;
import android.util.Log;

import com.yaratech.yaratube.data.model.MobileLoginStepOneResponse;
import com.yaratech.yaratube.data.model.MobileLoginStepTwoResponse;
import com.yaratech.yaratube.data.source.UserDataSource;
import com.yaratech.yaratube.data.source.local.UserLoginInfo;
import com.yaratech.yaratube.utils.DeviceUtils;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;
import retrofit2.Response;

public class UserRemoteDataSource implements UserDataSource {

    private static final String TAG = "UserRemoteDataSource";
    private ApiService apiService;
    private Context context;
    private Single<Response<MobileLoginStepOneResponse>> mobileLoginStepOneResponseSingle;
    private Single<Response<MobileLoginStepTwoResponse>> mobileLoginStepTwoResponseSingle;

    public UserRemoteDataSource(Context context) {
        this.context = context;
        apiService = ApiClient.getClient().create(ApiService.class);
    }

    @Override
    public void registerUserWithThisPhoneNumber(ApiResultCallback callback, String phoneNumber) {
        mobileLoginStepOneResponseSingle = apiService.mobileLoginStepOne(phoneNumber,
                DeviceUtils.getDeviceId(context),
                DeviceUtils.getDeviceModel(),
                DeviceUtils.getDeviceOS(),
                null);
        mobileLoginStepOneResponseSingle.
                subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<MobileLoginStepOneResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Response<MobileLoginStepOneResponse> response) {
                        if (response.isSuccessful()) {
                            Log.d(TAG, "onSuccess() called with: response.isSuccessful() ");
                            callback.onSuccessMessage(response.message(), response.code(), response.body());
                        } else {
                            Log.d(TAG, "onSuccess() called with: NOT response.isSuccessful() ");
                            callback.onErrorMessage(response.message(), response.code());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFailureMessage(e.getMessage(), ((HttpException) e).code());
                        Log.e(TAG, "onError: ", e);
                    }
                });
    }


    @Override
    public void insertUserLoginInfo(InsertIntoDatabaseCallback callback, UserLoginInfo userLoginInfo) {

    }

    @Override
    public void verifyUserWithThisCode(ApiResultCallback callback, String verificationCode, String phoneNumber) {
        Log.i(TAG, "<<<<    verifyUserWithThisCode      >>>>    :   [ phoneNumber " + phoneNumber + " ] , [ verificationCode" + verificationCode + " ]");
        mobileLoginStepTwoResponseSingle =
                apiService.mobileLoginStepTwo(
                        phoneNumber,
                        DeviceUtils.getDeviceId(context),
                        verificationCode,
                        null
                );
        mobileLoginStepTwoResponseSingle
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<MobileLoginStepTwoResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Response<MobileLoginStepTwoResponse> response) {
                        if (response.isSuccessful()) {
                            Log.d(TAG, "onSuccess() called with: response.isSuccessful() ");
                            callback.onSuccessMessage(response.message(), response.code(), response.body());
                        } else {
                            Log.d(TAG, "onSuccess() called with: NOT response.isSuccessful() ");
                            callback.onErrorMessage(response.message(), response.code());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFailureMessage(e.getMessage(), ((HttpException) e).code());
                        Log.e(TAG, "onError: ", e);
                    }
                });
    }

    @Override
    public void checkIfUserIsAuthorized(ReadFromDatabaseCallback callback) {

    }
}
