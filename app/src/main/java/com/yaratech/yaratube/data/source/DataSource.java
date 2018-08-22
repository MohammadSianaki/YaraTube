package com.yaratech.yaratube.data.source;

import com.yaratech.yaratube.data.source.local.UserLoginInfo;

import io.reactivex.disposables.Disposable;

public interface DataSource {

    interface ApiResultCallback<T> {

        void onDataLoaded(T response);

        void onDataNotAvailable();

        void onNetworkNotAvailable();
    }

    interface DatabaseResultCallback<T> {
        void onUserLoginInfoLoaded(T data);

        void onUserLoginInserted();

        void onUserIsAuthorized(boolean isAuthorized);

        void onAddedToCompositeDisposable(Disposable disposable);

        void onFailureMessage(String message);

    }

    interface Remote {
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


    interface Local {
        void checkIfUserIsAuthorized(DatabaseResultCallback callback);

        void insertUserLoginInfo(DatabaseResultCallback callback, UserLoginInfo userLoginInfo);
    }
}
