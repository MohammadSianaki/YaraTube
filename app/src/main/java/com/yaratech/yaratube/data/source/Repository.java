package com.yaratech.yaratube.data.source;

import com.yaratech.yaratube.data.source.local.LocalDataSource;
import com.yaratech.yaratube.data.source.local.UserLoginInfo;
import com.yaratech.yaratube.data.source.remote.RemoteDataSource;

public class Repository implements DataSource.Remote, DataSource.Local {

    private static Repository INSTANCE = null;
    private RemoteDataSource remoteDataSource;
    private LocalDataSource localDataSource;

    private Repository(DataSource.Remote remoteDataSource, DataSource.Local localDataSource) {
        //no instance
        if (remoteDataSource instanceof RemoteDataSource) {
            this.remoteDataSource = (RemoteDataSource) remoteDataSource;
        } else {
            throw new ClassCastException("IS NOT INSTANCE OF RemoteDataSource");
        }

        if (localDataSource instanceof LocalDataSource) {
            this.localDataSource = (LocalDataSource) localDataSource;
        } else {
            throw new ClassCastException("IS NOT INSTANCE OF LocalDataSource");

        }

    }

    public static Repository getINSTANCE(DataSource.Remote remoteDataSource, DataSource.Local localDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new Repository(remoteDataSource, localDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void fetchAllCategories(DataSource.ApiResultCallback callback) {
        remoteDataSource.fetchAllCategories(callback);
    }


    @Override
    public void fetchProductsByCategoryId(DataSource.ApiResultCallback callback, int categoryId) {
        remoteDataSource.fetchProductsByCategoryId(callback, categoryId);
    }

    @Override
    public void fetchProductDetailsByProductId(DataSource.ApiResultCallback callback, int productId) {
        remoteDataSource.fetchProductDetailsByProductId(callback, productId);
    }

    @Override
    public void fetchCommentsOfProductByProductId(DataSource.ApiResultCallback callback, int productId) {
        remoteDataSource.fetchCommentsOfProductByProductId(callback, productId);
    }

    @Override
    public void cancelCommentApiRequest() {
        remoteDataSource.cancelCommentApiRequest();
    }


    @Override
    public void fetchStoreItems(DataSource.ApiResultCallback callback) {
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

    @Override
    public void insertUserLoginInfo(DataSource.DatabaseResultCallback callback, UserLoginInfo userLoginInfo) {
        localDataSource.insertUserLoginInfo(callback, userLoginInfo);
    }

    @Override
    public void checkIfUserIsAuthorized(DataSource.DatabaseResultCallback callback) {
        localDataSource.checkIfUserIsAuthorized(callback);
    }
}
