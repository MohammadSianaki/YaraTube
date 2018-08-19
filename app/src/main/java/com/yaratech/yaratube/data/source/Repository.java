package com.yaratech.yaratube.data.source;

import com.yaratech.yaratube.data.source.remote.RemoteDataSource;

public class Repository implements DataSource {

    private static Repository INSTANCE = null;
    private RemoteDataSource remoteDataSource;

    private Repository(DataSource remoteDataSource) {
        //no instance
        if (remoteDataSource instanceof RemoteDataSource) {
            this.remoteDataSource = (RemoteDataSource) remoteDataSource;
        } else {
            throw new ClassCastException("IS NOT INSTANCE OF RemoteDataSource");
        }

    }

    public static Repository getINSTANCE(DataSource remoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new Repository(remoteDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void fetchAllCategories(ApiResultCallback callback) {
        remoteDataSource.fetchAllCategories(callback);
    }


    @Override
    public void fetchProductsByCategoryId(ApiResultCallback callback, int categoryId) {
        remoteDataSource.fetchProductsByCategoryId(callback, categoryId);
    }

    @Override
    public void fetchProductDetailsByProductId(ApiResultCallback callback, int productId) {
        remoteDataSource.fetchProductDetailsByProductId(callback, productId);
    }

    @Override
    public void fetchCommentsOfProductByProductId(ApiResultCallback callback, int productId) {
        remoteDataSource.fetchCommentsOfProductByProductId(callback, productId);
    }

    @Override
    public void cancelCommentApiRequest() {
        remoteDataSource.cancelCommentApiRequest();
    }


    @Override
    public void fetchStoreItems(ApiResultCallback callback) {
        remoteDataSource.fetchStoreItems(callback);
    }

    @Override
    public void cancelProductsByCategoryIdApiRequest() {
        remoteDataSource.cancelProductsByCategoryIdApiRequest();
    }

    @Override
    public void cancelProductDetailsByProductIdApiRequest() {
        remoteDataSource.cancelProductDetailsByProductIdApiRequest();
    }

    @Override
    public void cancelCategoryApiRequest() {
        remoteDataSource.cancelCategoryApiRequest();
    }

    @Override
    public void cancelStoreApiRequest() {
        remoteDataSource.cancelStoreApiRequest();
    }
}
