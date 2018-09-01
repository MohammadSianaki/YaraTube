package com.yaratech.yaratube.data.source;

public interface StoreDataSource {

    interface ApiResultCallback<T> {

        void onDataLoaded(T response);

        void onDataNotAvailable();

        void onNetworkNotAvailable();
    }



    void fetchAllCategories(ApiResultCallback callback);

    void fetchStoreItems(ApiResultCallback callback);

    void fetchProductsByCategoryId(int categoryId, int offset, int limit, ApiResultCallback callback);

    void fetchProductDetailsByProductId(ApiResultCallback callback, int productId, String androidOs);

    void fetchCommentsOfProductByProductId(ApiResultCallback callback, int productId);

    void cancelCommentApiRequest();

    void cancelStoreApiRequest();

    void cancelCategoryApiRequest();

    void cancelProductsByCategoryIdApiRequest();

    void cancelProductDetailsByProductIdApiRequest();

    interface Local {

    }
}
