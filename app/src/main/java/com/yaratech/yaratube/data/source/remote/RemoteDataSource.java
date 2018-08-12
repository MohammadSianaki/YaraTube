package com.yaratech.yaratube.data.source.remote;

import android.content.Context;
import android.util.Log;

import com.yaratech.yaratube.data.model.home.Category;
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

    public RemoteDataSource(Context context) {
        this.context = context;
        apiService = ApiClient.getClient().create(ApiService.class);
    }

    @Override
    public void fetchAllCategories(final ApiResultCallback callback) {
        if (NetworkUtils.isNetworkAvailable(context)) {
            Log.i(TAG, "fetchAllCategories: network available");
            Call<List<Category>> call = apiService.fetchAllCategories();
            call.enqueue(new Callback<List<Category>>() {
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
}
