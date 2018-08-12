package com.yaratech.yaratube.data.source;

import java.util.List;

public interface DataSource {

    interface LoadDataCallback<T> {

        void onDataLoaded(List<T> list);

        void onDataNotAvailable();

        void onNetworkNotAvailable();
    }


    void fetchAllCategories(LoadDataCallback callback);


}
