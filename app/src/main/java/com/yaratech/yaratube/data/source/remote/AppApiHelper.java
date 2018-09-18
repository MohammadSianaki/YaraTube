package com.yaratech.yaratube.data.source.remote;

import android.content.Context;
import android.util.Log;

import com.yaratech.yaratube.data.DataManager;
import com.yaratech.yaratube.data.model.api.CommentResponse;
import com.yaratech.yaratube.data.model.api.GetProfileResponse;
import com.yaratech.yaratube.data.model.api.GoogleLoginResponse;
import com.yaratech.yaratube.data.model.api.MobileLoginStepOneResponse;
import com.yaratech.yaratube.data.model.api.MobileLoginStepTwoResponse;
import com.yaratech.yaratube.data.model.api.PostProfileResponse;
import com.yaratech.yaratube.data.model.api.StoreResponse;
import com.yaratech.yaratube.data.model.other.Category;
import com.yaratech.yaratube.data.model.other.Comment;
import com.yaratech.yaratube.data.model.other.Product;
import com.yaratech.yaratube.utils.DeviceUtils;

import java.io.IOException;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import retrofit2.Response;

public class AppApiHelper implements ApiHelper {
    //-------------------------------------------------------------------------------------
    private static final String TAG = "AppApiHelper";
    private ApiService apiService;
    private Context context;

    public AppApiHelper(Context context) {
        this.context = context;
        apiService = ApiClient.getClient(context.getCacheDir()).create(ApiService.class);
    }


    //-------------------------------------------------------------------------------------

    @Override
    public Disposable fetchListOfCategories(DataManager.DashboardApiResultCallback callback) {
        return apiService
                .fetchAllCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Response<List<Category>>>() {
                    @Override
                    public void onSuccess(Response<List<Category>> response) {
                        Log.d(TAG, "onSuccess: <<<<    fetchListOfCategories    >>>> with :" +
                                " success code = [" + response + "]" + ", message = [" + response.message() + "]");
                        if (response.isSuccessful()) {
                            callback.onDataLoaded(response.body());
                        } else {
                            try {
                                Log.d(TAG, "onSuccess: <<<<    fetchListOfCategories    >>>> with : error body  [" + response.errorBody().string() + "]");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            callback.onDataNotAvailable();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError <<<<    fetchListOfCategories   >>>>: ", e);
                        callback.onDataNotAvailable();
                    }
                });
    }


    @Override
    public Disposable fetchStoreItems(DataManager.DashboardApiResultCallback callback) {
        return apiService
                .fetchStoreItems()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Response<StoreResponse>>() {
                    @Override
                    public void onSuccess(Response<StoreResponse> response) {
                        Log.d(TAG, "onSuccess:  <<<<    fetchStoreItems   >>>>");
                        if (response.isSuccessful()) {
                            Log.d(TAG, "onSuccess: <<<<    fetchStoreItems    >>>> with :" +
                                    " success code = [" + response + "]" + ", message = [" + response.message() + "]")
                            ;
                            callback.onDataLoaded(response.body());
                        } else {
                            try {
                                Log.d(TAG, "onSuccess: <<<<    fetchStoreItems    >>>> with : error body  [" + response.errorBody().string() + "]");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            callback.onDataNotAvailable();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError <<<<    fetchStoreItems   >>>>: ", e);
                        callback.onDataNotAvailable();
                    }
                });
    }


    @Override
    public Disposable fetchProductsByCategoryId(int categoryId, int offset, int limit, DataManager.DashboardApiResultCallback callback) {
        return apiService
                .fetchProductsByCategoryId(categoryId, offset, limit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Response<List<Product>>>() {
                    @Override
                    public void onSuccess(Response<List<Product>> response) {
                        Log.d(TAG, "onSuccess:  <<<<    fetchProductsByCategoryId   >>>>");
                        if (response.isSuccessful()) {
                            Log.d(TAG, "onSuccess: <<<<    fetchProductsByCategoryId    >>>> with :" +
                                    " success code = [" + response + "]" + ", message = [" + response.message() + "]")
                            ;
                            callback.onDataLoaded(response.body());
                        } else {
                            try {
                                Log.d(TAG, "onSuccess: <<<<    fetchProductsByCategoryId    >>>> with : error body  [" + response.errorBody().string() + "]");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            callback.onDataNotAvailable();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError <<<<    fetchProductsByCategoryId   >>>>: ", e);
                        callback.onDataNotAvailable();
                    }
                });
    }


    @Override
    public Disposable fetchProductDetailsByProductId(int productId, String deviceOs, DataManager.DashboardApiResultCallback callback) {
        return apiService
                .fetchProductDetailsByProductId(productId, deviceOs)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Response<Product>>() {
                    @Override
                    public void onSuccess(Response<Product> response) {
                        Log.d(TAG, "onSuccess:  <<<<    fetchProductDetailsByProductId   >>>>");
                        if (response.isSuccessful()) {
                            Log.d(TAG, "onSuccess: <<<<    fetchProductDetailsByProductId    >>>> with :" +
                                    " success code = [" + response + "]" + ", message = [" + response.message() + "]")
                            ;
                            callback.onDataLoaded(response.body());
                        } else {
                            try {
                                Log.d(TAG, "onSuccess: <<<<    fetchProductDetailsByProductId    >>>> with : error body  [" + response.errorBody().string() + "]");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            callback.onDataNotAvailable();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError <<<<    fetchProductDetailsByProductId   >>>>: ", e);
                        callback.onDataNotAvailable();
                    }
                });
    }


    @Override
    public Disposable fetchCommentListOfProductByProductId(int productId, int offset, int limit, DataManager.DashboardApiResultCallback callback) {
        return apiService
                .fetchCommentOfProductsByProductId(productId, offset, limit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Response<List<Comment>>>() {
                    @Override
                    public void onSuccess(Response<List<Comment>> response) {
                        Log.d(TAG, "onSuccess:  <<<<    fetchCommentListOfProductByProductId   >>>>");
                        if (response.isSuccessful()) {
                            Log.d(TAG, "onSuccess: <<<<    fetchCommentListOfProductByProductId    >>>> with :" +
                                    " success code = [" + response + "]" + ", message = [" + response.message() + "]")
                            ;
                            callback.onDataLoaded(response.body());
                        } else {
                            try {
                                Log.d(TAG, "onSuccess: <<<<    fetchCommentListOfProductByProductId    >>>> with : error body  [" + response.errorBody().string() + "]");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            callback.onDataNotAvailable();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError <<<<    fetchCommentListOfProductByProductId   >>>>: ", e);
                        callback.onDataNotAvailable();
                    }
                });
    }


    @Override
    public Disposable submitCommentToProduct(int productId, int score, String title, String commentText, String token, DataManager.CommentApiResultCallback callback) {
        return apiService
                .submitCommentToProduct(productId, token, title, commentText, score)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Response<CommentResponse>>() {
                    @Override
                    public void onSuccess(Response<CommentResponse> response) {
                        Log.d(TAG, "onSuccess:  <<<<    submitCommentToProduct   >>>>");
                        if (response.isSuccessful()) {
                            Log.d(TAG, "onSuccess: <<<<    submitCommentToProduct    >>>> with :" +
                                    " success code = [" + response + "]" + ", message = [" + response.message() + "]")
                            ;
                            callback.onSuccess(response.body().getMessage(), response.code());
                        } else {
                            try {
                                Log.d(TAG, "onSuccess: <<<<    submitCommentToProduct    >>>> with : error body  [" + response.errorBody().string() + "]");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            callback.onError(response.body().getMessage(), response.body().getError());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError <<<<    submitCommentToProduct   >>>>: ", e);
                        callback.onFailure(e.getMessage());
                    }
                });
    }


    @Override
    public Disposable registerUserWithThisPhoneNumber(String phoneNumber, DataManager.LoginApiResultCallback callback) {
        return apiService.mobileLoginStepOne(phoneNumber,
                DeviceUtils.getDeviceId(context),
                DeviceUtils.getDeviceModel(),
                DeviceUtils.getDeviceOS(),
                null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Response<MobileLoginStepOneResponse>>() {
                    @Override
                    public void onSuccess(Response<MobileLoginStepOneResponse> response) {
                        if (response.isSuccessful()) {
                            Log.d(TAG, "onSuccess: <<<<    registerUserWithThisPhoneNumber    >>>> with :" +
                                    " success code = [" + response + "]" + ", message = [" + response.message() + "]")
                            ;
                            callback.onSuccess(response.body().getMessage(), response.code(), response.body());
                        } else {
                            try {
                                Log.d(TAG, "onSuccess: <<<<    registerUserWithThisPhoneNumber    >>>> with : error body  [" + response.errorBody().string() + "]");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            callback.onError(response.body().getMessage(), response.body().getError());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError <<<<    registerUserWithThisPhoneNumber   >>>>: ", e);
                        callback.onFailure(e.getMessage());
                    }
                });

    }

    @Override
    public Disposable verifyUserWithThisCode(String phoneNumber, String verificationCode, DataManager.LoginApiResultCallback callback) {
        return apiService
                .mobileLoginStepTwo(
                        null,
                        phoneNumber,
                        verificationCode,
                        DeviceUtils.getDeviceId(context))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Response<MobileLoginStepTwoResponse>>() {
                    @Override
                    public void onSuccess(Response<MobileLoginStepTwoResponse> response) {
                        if (response.isSuccessful()) {
                            Log.d(TAG, "onSuccess: <<<<    verifyUserWithThisCode    >>>> with :" +
                                    " success code = [" + response + "]" + ", message = [" + response.message() + "]")
                            ;
                            callback.onSuccess(response.body().getMessage(), response.code(), response.body());
                        } else {
                            try {
                                Log.d(TAG, "onSuccess: <<<<    verifyUserWithThisCode    >>>> with : error body  [" + response.errorBody().string() + "]");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            callback.onError(response.body().getMessage(), response.body().getError());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError <<<<    verifyUserWithThisCode   >>>>: ", e);
                        callback.onFailure(e.getMessage());
                    }
                });
    }

    @Override
    public Disposable registerUserWithThisGoogleApiToken(String googleToken, DataManager.LoginApiResultCallback callback) {
        return apiService
                .registerUserWithThisGoogleApiToken(
                        googleToken,
                        DeviceUtils.getDeviceId(context),
                        DeviceUtils.getDeviceOS(),
                        DeviceUtils.getDeviceModel())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Response<GoogleLoginResponse>>() {
                    @Override
                    public void onSuccess(Response<GoogleLoginResponse> response) {
                        if (response.isSuccessful()) {
                            Log.d(TAG, "onSuccess: <<<<    registerUserWithThisGoogleApiToken    >>>> with :" +
                                    " success code = [" + response + "]" + ", message = [" + response.message() + "]");
                            callback.onSuccess(response.body().getMessage(), response.code(), response.body());
                        } else {
                            Log.d(TAG, "onSuccess: not successful");
                            try {
                                Log.d(TAG, "onSuccess: <<<<    registerUserWithThisGoogleApiToken    >>>> with : error body  [" + response.errorBody().string() + "]");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            callback.onError(response.message(), response.code());

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError <<<<    registerUserWithThisGoogleApiToken   >>>>: ", e);
                        callback.onFailure(e.getMessage());
                    }
                });
    }

    @Override
    public Disposable uploadUserProfileImageAvatar(MultipartBody.Part image, String token, DataManager.DashboardApiResultCallback callback) {
        Log.d(TAG, "uploadUserProfileImageAvatar() called with: image = [" + image + "], token = [" + token + "], callback = [" + callback + "]");
        return apiService
                .uploadUserProfileImageAvatar(image, "Token " + token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Response<PostProfileResponse>>() {
                    @Override
                    public void onSuccess(Response<PostProfileResponse> response) {
                        Log.d(TAG, "onSuccess() called with: response = [" + response.isSuccessful() + "]");
                        Log.d(TAG, "onSuccess() called with: response message = [" + response.message() + "]");
                        if (response.isSuccessful()) {
                            callback.onDataLoaded(response.body());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ", e);
                    }
                });
    }

    @Override
    public Disposable uploadUserProfileInformation(String nickName, String dateOfBirth, String gender, String token, DataManager.DashboardApiResultCallback callback) {
        return apiService
                .uploadUserProfileInformation(
                        nickName,
                        dateOfBirth,
                        gender,
                        "Token " + token,
                        DeviceUtils.getDeviceId(context),
                        DeviceUtils.getDeviceModel(),
                        DeviceUtils.getDeviceOS())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Response<PostProfileResponse>>() {
                    @Override
                    public void onSuccess(Response<PostProfileResponse> response) {
                        Log.d(TAG, "onSuccess() called with: response = [" + response.isSuccessful() + "]");
                        if (response.isSuccessful()) {
                            callback.onDataLoaded(response.body());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " + e.getMessage(), e);
                    }
                });
    }

    @Override
    public Disposable loadUserProfileInformation(String token, DataManager.DashboardApiResultCallback callback) {
        return apiService
                .loadUserProfileInfo("Token " + token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Response<GetProfileResponse>>() {
                    @Override
                    public void onSuccess(Response<GetProfileResponse> response) {
                        Log.d(TAG, "onSuccess() called with: response = [" + response.isSuccessful() + "]");
                        if (response.isSuccessful()) {
                            callback.onDataLoaded(response.body());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " + e.getMessage(), e);
                    }
                });
    }

    @Override
    public void onStopActivity() {
        context = null;
    }
}
