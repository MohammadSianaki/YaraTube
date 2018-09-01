package com.yaratech.yaratube.data.source;

import com.yaratech.yaratube.data.source.remote.StoreRemoteDataSource;

public class StoreRepository implements StoreDataSource {

    private static StoreRepository INSTANCE = null;
    private StoreRemoteDataSource storeRemoteDataSource;

    private StoreRepository(StoreDataSource remoteDataSource) {
        //no instance
        if (remoteDataSource instanceof StoreRemoteDataSource) {
            this.storeRemoteDataSource = (StoreRemoteDataSource) remoteDataSource;
        } else {
            throw new ClassCastException("IS NOT INSTANCE OF StoreRemoteDataSource");
        }

    }

    public static StoreRepository getINSTANCE(StoreDataSource remoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new StoreRepository(remoteDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void fetchAllCategories(StoreDataSource.ApiResultCallback callback) {
        storeRemoteDataSource.fetchAllCategories(callback);
    }


    @Override
    public void fetchProductsByCategoryId(int categoryId, int offset, int limit, ApiResultCallback callback) {
        storeRemoteDataSource.fetchProductsByCategoryId(categoryId, offset, limit, callback);
    }

    @Override
    public void fetchProductDetailsByProductId(ApiResultCallback callback, int productId, String deviceOs) {
        storeRemoteDataSource.fetchProductDetailsByProductId(callback, productId, deviceOs);
    }

    @Override
    public void fetchCommentsOfProductByProductId(int productId, int offset, int limit, ApiResultCallback callback) {
        storeRemoteDataSource.fetchCommentsOfProductByProductId(productId, offset, limit, callback);
    }

    @Override
    public void cancelCommentApiRequest() {
        storeRemoteDataSource.cancelCommentApiRequest();
    }


    @Override
    public void fetchStoreItems(StoreDataSource.ApiResultCallback callback) {
        storeRemoteDataSource.fetchStoreItems(callback);
    }

    @Override
    public void cancelProductsByCategoryIdApiRequest() {
        storeRemoteDataSource.cancelProductsByCategoryIdApiRequest();
    }

    @Override
    public void cancelProductDetailsByProductIdApiRequest() {
        storeRemoteDataSource.cancelProductDetailsByProductIdApiRequest();
    }

    @Override
    public void cancelCategoryApiRequest() {
        storeRemoteDataSource.cancelCategoryApiRequest();
    }

    @Override
    public void cancelStoreApiRequest() {
        storeRemoteDataSource.cancelStoreApiRequest();
    }

}
