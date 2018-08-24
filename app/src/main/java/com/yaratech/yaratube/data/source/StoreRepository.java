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
    public void fetchProductsByCategoryId(StoreDataSource.ApiResultCallback callback, int categoryId) {
        storeRemoteDataSource.fetchProductsByCategoryId(callback, categoryId);
    }

    @Override
    public void fetchProductDetailsByProductId(StoreDataSource.ApiResultCallback callback, int productId) {
        storeRemoteDataSource.fetchProductDetailsByProductId(callback, productId);
    }

    @Override
    public void fetchCommentsOfProductByProductId(StoreDataSource.ApiResultCallback callback, int productId) {
        storeRemoteDataSource.fetchCommentsOfProductByProductId(callback, productId);
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
