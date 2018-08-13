package com.yaratech.yaratube.data.source;

public interface DataSource {

    interface ApiResultCallback<T> {

        void onDataLoaded(T response);

        void onDataNotAvailable();

        void onNetworkNotAvailable();
    }


    void fetchAllCategories(ApiResultCallback callback);

    void fetchStoreItems(ApiResultCallback callback);

    void fetchProductsByCategoryId(ApiResultCallback callback, int categoryId);

    void cancelStoreApiRequest();

    void cancelCategoryApiRequest();

    void cancelProductApiRequest();


}
