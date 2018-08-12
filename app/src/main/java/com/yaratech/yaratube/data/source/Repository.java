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
    public void fetchAllCategories(CategoryApiResultCallback callback) {
        remoteDataSource.fetchAllCategories(callback);
    }


    @Override
    public void fetchStoreItems(StoreApiResultCallback callback) {
        remoteDataSource.fetchStoreItems(callback);
    }
}
