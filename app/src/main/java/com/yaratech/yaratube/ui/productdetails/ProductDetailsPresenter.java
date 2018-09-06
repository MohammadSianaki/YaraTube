package com.yaratech.yaratube.ui.productdetails;

import android.util.Log;

import com.yaratech.yaratube.data.AppDataManager;
import com.yaratech.yaratube.data.DataManager;
import com.yaratech.yaratube.data.model.other.Comment;
import com.yaratech.yaratube.data.model.other.Product;
import com.yaratech.yaratube.utils.AppConstants;

import java.util.List;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class ProductDetailsPresenter implements DetailsContract.Presenter {

    //---------------------------------------------------------------------------------------------
    private static final String TAG = "ProductDetailsPresenter";
    private AppDataManager appDataManager;
    private CompositeDisposable compositeDisposable;
    private DetailsContract.View mView;
    //---------------------------------------------------------------------------------------------


    public ProductDetailsPresenter(AppDataManager appDataManager) {
        this.appDataManager = appDataManager;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void attachView(DetailsContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public boolean isAttached() {
        return mView != null;
    }

    @Override
    public void unSubscribe() {
        if (isAttached()) {
            compositeDisposable.clear();
        }
    }

    @Override
    public void fetchProductDetails(int productId) {
        if (isAttached()) {
            mView.showProgressBarLoading();
            Disposable disposable = appDataManager.fetchProductDetailsByProductId(productId, AppConstants.DEVICE_OS, new DataManager.DashboardApiResultCallback() {

                @Override
                public void onDataLoaded(Object response) {
                    Product product = (Product) response;
                    if (product.getFiles() == null) {
                        Log.d(TAG, "onDataLoaded: in presenter is null");
                    } else {
                        Log.d(TAG, "onDataLoaded: in presenter is not null");
                    }

                    if (mView != null) {
                        mView.finishProgressBarLoading();
                        mView.showLoadedData(product);
                    }
                }

                @Override
                public void onDataNotAvailable() {
                    if (mView != null) {
                        mView.finishProgressBarLoading();
                        mView.showDataNotAvailableToast();
                    }
                }

                @Override
                public void onNetworkNotAvailable() {
                    if (mView != null) {
                        mView.finishProgressBarLoading();
                        mView.showNetworkNotAvailableToast();
                    }
                }
            });
            compositeDisposable.add(disposable);
        }
    }

    @Override
    public void fetchProductComments(int productId, int offset, int limit) {
        if (isAttached()) {
            mView.showCommentLoading();
            appDataManager.fetchCommentListOfProductByProductId(productId, offset, limit, new DataManager.DashboardApiResultCallback() {
                @Override
                public void onDataLoaded(Object response) {
                    List<Comment> commentList = (List<Comment>) response;
                    if (mView != null) {
                        mView.hideCommentLoading();
                        mView.showLoadedComments(commentList);
                    }
                }

                @Override
                public void onDataNotAvailable() {
                    if (mView != null) {
                        mView.hideCommentLoading();
                        mView.showDataNotAvailableToast();
                    }
                }

                @Override
                public void onNetworkNotAvailable() {
                    if (mView != null) {
                        mView.hideCommentLoading();
                        mView.showNetworkNotAvailableToast();
                    }
                }
            });
        }
    }


    @Override
    public void isUserLogin() {
        Disposable disposable = appDataManager.isUserAuthorized(new DataManager.LoginDatabaseResultCallback() {
            @Override
            public void onSuccess(Map<Boolean, String> map) {
                if (map.containsKey(true)) {
                    mView.showCommentDialog(map.get(true));
                } else {
                    mView.showLoginDialog();
                }
            }

            @Override
            public void onFailure(String message) {
                Log.d(TAG, "onFailure() called with: message = [" + message + "]");
            }

        });
        compositeDisposable.add(disposable);
    }

    @Override
    public void isUserLoginToPlay() {
        Disposable disposable = appDataManager.isUserAuthorized(new DataManager.LoginDatabaseResultCallback() {
            @Override
            public void onSuccess(Map<Boolean, String> map) {
                if (map.containsKey(true)) {
                    mView.goToPlayerActivity();
                } else {
                    mView.showLoginDialog();
                }
            }

            @Override
            public void onFailure(String message) {
                Log.d(TAG, "onFailure() called with: message = [" + message + "]");
            }
        });
        compositeDisposable.add(disposable);
    }
}
