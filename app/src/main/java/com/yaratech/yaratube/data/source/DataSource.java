package com.yaratech.yaratube.data.source;

import com.yaratech.yaratube.data.model.HomeResponse;

import java.util.List;

public interface DataSource {

    interface CategoryApiResultCallback<T> {

        void onDataLoaded(List<T> list);

        void onDataNotAvailable();

        void onNetworkNotAvailable();
    }


    interface StoreApiResultCallback {

        void onDataLoaded(HomeResponse response);

        void onDataNotAvailable();

        void onNetworkNotAvailable();
    }


    void fetchAllCategories(CategoryApiResultCallback callback);

    void fetchStoreItems(StoreApiResultCallback callback);

    void cancelCategoryApiRequest();

    void cancelStoreApiRequest();
}
