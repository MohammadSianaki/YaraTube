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

    void fetchProductDetailsByProductId(ApiResultCallback callback, int productId);

    void fetchCommentsOfProductByProductId(ApiResultCallback callback, int productId);

    void cancelCommentApiRequest();

    void cancelStoreApiRequest();

    void cancelCategoryApiRequest();

    void cancelProductsByCategoryIdApiRequest();

    void cancelProductDetailsByProductIdApiRequest();

}
