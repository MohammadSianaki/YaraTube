package com.yaratech.yaratube.data.source;

public interface DataSource {

    interface ApiResultCallback<T> {

        void onDataLoaded(T response);

        void onDataNotAvailable();

        void onNetworkNotAvailable();
    }


    void fetchAllCategories(ApiResultCallback callback);

    void fetchStoreItems(ApiResultCallback callback);

    void cancelStoreApiRequest();

    void cancelCategoryApiRequest();

}
