package com.yaratech.yaratube.data.source.remote;

import android.util.Log;

import com.yaratech.yaratube.data.DataManager;
import com.yaratech.yaratube.data.model.api.CommentResponse;
import com.yaratech.yaratube.data.model.api.MobileLoginStepOneResponse;
import com.yaratech.yaratube.data.model.api.MobileLoginStepTwoResponse;
import com.yaratech.yaratube.data.model.api.StoreResponse;
import com.yaratech.yaratube.data.model.other.Category;
import com.yaratech.yaratube.data.model.other.Comment;
import com.yaratech.yaratube.data.model.other.Product;

import java.io.IOException;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class AppApiHelper implements ApiHelper {
    //-------------------------------------------------------------------------------------
    private static final String TAG = "AppApiHelper";
    private ApiService apiService;

    public AppApiHelper() {
        apiService = ApiClient.getClient().create(ApiService.class);
    }
    //-------------------------------------------------------------------------------------

    @Override
    public Disposable fetchListOfCategories(DataManager.StoreApiResultCallback callback) {
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
    public Disposable fetchStoreItems(DataManager.StoreApiResultCallback callback) {
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
    public Disposable fetchProductsByCategoryId(int categoryId, int offset, int limit, DataManager.StoreApiResultCallback callback) {
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
    public Disposable fetchProductDetailsByProductId(int productId, String deviceOs, DataManager.StoreApiResultCallback callback) {
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
    public Disposable fetchCommentListOfProductByProductId(int productId, int offset, int limit, DataManager.StoreApiResultCallback callback) {
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
    public Disposable registerUserWithThisPhoneNumber(String phoneNumber, String deviceId, String deviceModel, String deviceOs, DataManager.LoginApiResultCallback callback) {
        return apiService.mobileLoginStepOne(phoneNumber,
                deviceId,
                deviceModel,
                deviceOs,
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
    public Disposable verifyUserWithThisCode(String phoneNumber, String deviceId, String verificationCode, DataManager.LoginApiResultCallback callback) {
        return apiService
                .mobileLoginStepTwo(
                        null,
                        phoneNumber,
                        verificationCode,
                        deviceId)
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
}
