package com.yaratech.yaratube.data.source.remote;

import android.content.Context;
import android.util.Log;

import com.yaratech.yaratube.data.model.Category;
import com.yaratech.yaratube.data.model.HomeResponse;
import com.yaratech.yaratube.data.model.Product;
import com.yaratech.yaratube.data.source.DataSource;
import com.yaratech.yaratube.utils.NetworkUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoteDataSource implements DataSource {

    private static final String TAG = "RemoteDataSource";
    private Context context;
    private ApiService apiService;
    private Call<HomeResponse> homeResponseCall;
    private Call<List<Category>> categoryCall;
    private Call<List<Product>> productsByCategoryCall;
    private Call<Product> productDetailsByProductIdCall;


    public RemoteDataSource(Context context) {
        this.context = context;
        apiService = ApiClient.getClient().create(ApiService.class);
    }

    @Override
    public void fetchAllCategories(final ApiResultCallback callback) {
        if (NetworkUtils.isNetworkAvailable(context)) {
            Log.i(TAG, "fetchAllCategories: network available");
            categoryCall = apiService.fetchAllCategories();
            categoryCall.enqueue(new Callback<List<Category>>() {
                @Override
                public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                    Log.i(TAG, "onResponse: ");
                    if (response.isSuccessful()) {
                        Log.i(TAG, "onResponse: Successful");
                        callback.onDataLoaded(response.body());
                    } else {
                        Log.i(TAG, "onResponse: not successful");
                        callback.onDataNotAvailable();
                    }
                }

                @Override
                public void onFailure(Call<List<Category>> call, Throwable t) {
                    Log.i(TAG, "onFailure: " + t.getMessage());
                    callback.onDataNotAvailable();
                }
            });

        } else {
            Log.i(TAG, "fetchAllCategories: network not available");
            callback.onNetworkNotAvailable();
        }
    }

    @Override
    public void fetchProductsByCategoryId(ApiResultCallback callback, int categoryId) {
        if (NetworkUtils.isNetworkAvailable(context)) {
            productsByCategoryCall = apiService.fetchProductsByCategoryId(categoryId);
            productsByCategoryCall.enqueue(new Callback<List<Product>>() {
                @Override
                public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                    if (response.isSuccessful()) {
                        Log.i(TAG, "onResponse: successful");
                        callback.onDataLoaded(response.body());
                    } else {
                        Log.i(TAG, "onResponse: not successful");
                        callback.onDataNotAvailable();
                    }
                }

                @Override
                public void onFailure(Call<List<Product>> call, Throwable t) {
                    Log.i(TAG, "onFailure: " + t.getMessage());
                    callback.onDataNotAvailable();
                }
            });
        } else {
            callback.onNetworkNotAvailable();
            Log.i(TAG, "fetchProductsByCategoryId: network not available");
        }
    }

    @Override
    public void fetchProductDetailsByProductId(ApiResultCallback callback, int productId) {
        if (NetworkUtils.isNetworkAvailable(context)) {
            Log.i(TAG, "fetchProductDetailsByProductId: network available");

            productDetailsByProductIdCall = apiService.fetchProductDetailsByProductId(productId);
            productDetailsByProductIdCall.enqueue(new Callback<Product>() {
                @Override
                public void onResponse(Call<Product> call, Response<Product> response) {
                    if (response.isSuccessful()) {
                        callback.onDataLoaded(response.body());
                        Log.i(TAG, "onResponse: successful");
                    } else {
                        callback.onDataNotAvailable();
                        Log.i(TAG, "onResponse: not successful");
                    }
                }

                @Override
                public void onFailure(Call<Product> call, Throwable t) {
                    callback.onDataNotAvailable();
                    Log.i(TAG, "onFailure: " + t.getMessage());
                }
            });
        } else {
            Log.i(TAG, "fetchProductDetailsByProductId: network not available");
            callback.onNetworkNotAvailable();
        }
    }

    @Override
    public void cancelProductsByCategoryIdApiRequest() {
        if (productsByCategoryCall != null) {
            productsByCategoryCall.cancel();
        }
    }

    @Override
    public void cancelProductDetailsByProductIdApiRequest() {
        if (productDetailsByProductIdCall != null) {
            productDetailsByProductIdCall.cancel();
        }
    }

    @Override
    public void fetchStoreItems(ApiResultCallback callback) {
        if (NetworkUtils.isNetworkAvailable(context)) {
            Log.i(TAG, "fetchStoreItems: network available");
            homeResponseCall = apiService.fetchStoreItems();
            homeResponseCall.enqueue(new Callback<HomeResponse>() {
                @Override
                public void onResponse(Call<HomeResponse> call, Response<HomeResponse> response) {
                    Log.i(TAG, "onResponse: ");
                    if (response.isSuccessful()) {
                        Log.i(TAG, "onResponse: successful");
                        callback.onDataLoaded(response.body());
                    } else {
                        callback.onDataNotAvailable();
                        Log.i(TAG, "onResponse: not successful");
                    }
                }

                @Override
                public void onFailure(Call<HomeResponse> call, Throwable t) {
                    Log.i(TAG, "onFailure: " + t.getMessage());
                    callback.onDataNotAvailable();
                }
            });


        } else {
            Log.i(TAG, "fetchAllCategories: network not available");
            callback.onNetworkNotAvailable();
        }
    }

    @Override
    public void cancelCategoryApiRequest() {
        if (homeResponseCall != null) {
            homeResponseCall.cancel();
        }
    }

    @Override
    public void cancelStoreApiRequest() {
        if (homeResponseCall != null) {
            homeResponseCall.cancel();
        }
    }
}
