package com.yaratech.yaratube.data.source.remote;

import android.content.Context;
import android.util.Log;

import com.yaratech.yaratube.data.model.CommentResponse;
import com.yaratech.yaratube.data.model.MobileLoginStepOneResponse;
import com.yaratech.yaratube.data.model.MobileLoginStepTwoResponse;
import com.yaratech.yaratube.data.source.UserDataSource;
import com.yaratech.yaratube.data.source.local.UserLoginInfo;
import com.yaratech.yaratube.utils.DeviceUtils;
import com.yaratech.yaratube.utils.NetworkUtils;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class UserRemoteDataSource implements UserDataSource {

    private static final String TAG = "UserRemoteDataSource";
    private ApiService apiService;
    private Context context;
    private Single<Response<MobileLoginStepOneResponse>> mobileLoginStepOneResponseSingle;
    private Single<Response<MobileLoginStepTwoResponse>> mobileLoginStepTwoResponseSingle;
    private Call<CommentResponse> postComment;

    public UserRemoteDataSource(Context context) {
        this.context = context;
        apiService = ApiClient.getClient().create(ApiService.class);
    }

    @Override
    public void cancelPostCommentRequest() {
        if (postComment != null) {
            postComment.cancel();
        }
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

    // TODO: 8/25/2018  Below Methods Must Be  Removed

    @Override
    public void insertUserLoginInfo(InsertIntoDatabaseCallback callback, UserLoginInfo userLoginInfo) {

    }

    @Override
    public void setUserMobilePhoneNumber(String mobilePhoneNumber) {

    }

    @Override
    public String getUserMobilePhoneNumber() {
        return null;
    }

    @Override
    public void setUserLoginStep(int loginStep) {

    }

    @Override
    public int getUserLoginStep() {
        return 0;
    }


    @Override
    public void checkIfUserIsAuthorized(ReadFromDatabaseCallback callback) {

    }

    @Override
    public void submitCommentToProduct(int productId, int score, String title, String textContent, String token, ApiResultCallback callback) {
        postComment = apiService.postComment(
                title,
                score,
                textContent,
                productId,
                token);
        if (NetworkUtils.isNetworkAvailable(context)) {
            postComment.enqueue(new Callback<CommentResponse>() {
                @Override
                public void onResponse(Call<CommentResponse> call, Response<CommentResponse> response) {
                    Log.d(TAG, "onResponse  : post comment");
                    if (response.isSuccessful()) {
                        Log.d(TAG, "onResponse  : post comment is successful");
                        callback.onSuccessMessage(response.message(), response.code(), response.body());
                    } else {
                        Log.d(TAG, "onResponse  : post comment is not successful");
                        callback.onErrorMessage(response.message(), response.code());
                    }
                }

                @Override
                public void onFailure(Call<CommentResponse> call, Throwable t) {
                    Log.e(TAG, "onFailure: post comment", t);
                    callback.onFailureMessage(t.getMessage(), ((HttpException) t).code());
                }
            });
        } else {
            callback.onErrorMessage("Check Your Internet Connection...", -1);
        }
    }
}
