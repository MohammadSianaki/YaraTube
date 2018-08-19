package com.yaratech.yaratube.ui.productdetails;

import android.content.Context;

import com.yaratech.yaratube.data.model.Comment;
import com.yaratech.yaratube.data.model.ProductDetails;
import com.yaratech.yaratube.data.source.DataSource;
import com.yaratech.yaratube.data.source.Repository;
import com.yaratech.yaratube.data.source.remote.RemoteDataSource;

import java.lang.ref.WeakReference;
import java.util.List;

public class DetailsPresenter implements DetailsContract.Presenter {

    //---------------------------------------------------------------------------------------------
    private Repository repository;
    private WeakReference<DetailsContract.View> mWeakReference;
    //---------------------------------------------------------------------------------------------


    public DetailsPresenter(Context context) {
        this.repository = Repository.getINSTANCE(new RemoteDataSource((context)));
    }

    @Override
    public void attachView(DetailsContract.View view) {
        mWeakReference = new WeakReference<>(view);
    }

    @Override
    public void detachView() {
        mWeakReference = null;
    }

    @Override
    public boolean isAttached() {
        return mWeakReference != null;
    }

    @Override
    public void fetchProductDetails(int productId) {
        if (isAttached()) {
            mWeakReference.get().showProgressBarLoading();
            repository.fetchProductDetailsByProductId(new DataSource.ApiResultCallback() {
                @Override
                public void onDataLoaded(Object response) {
                    ProductDetails product = (ProductDetails) response;
                    if (mWeakReference != null) {
                        mWeakReference.get().finishProgressBarLoading();
                        mWeakReference.get().showLoadedData(product);
                    }
                }

                @Override
                public void onDataNotAvailable() {
                    if (mWeakReference != null) {
                        mWeakReference.get().finishProgressBarLoading();
                        mWeakReference.get().showDataNotAvailableToast();
                    }
                }

                @Override
                public void onNetworkNotAvailable() {
                    if (mWeakReference != null) {
                        mWeakReference.get().finishProgressBarLoading();
                        mWeakReference.get().showNetworkNotAvailableToast();
                    }
                }
            }, productId);
        }
    }

    @Override
    public void fetchProductComments(int productId) {
        if (isAttached()) {
            mWeakReference.get().showCommentLoading();
            repository.fetchCommentsOfProductByProductId(new DataSource.ApiResultCallback() {
                @Override
                public void onDataLoaded(Object response) {
                    List<Comment> commentList = (List<Comment>) response;
                    if (mWeakReference != null) {
                        mWeakReference.get().hideCommentLoading();
                        mWeakReference.get().showLoadedComments(commentList);
                    }
                }

                @Override
                public void onDataNotAvailable() {
                    if (mWeakReference != null) {
                        mWeakReference.get().hideCommentLoading();
                        mWeakReference.get().showDataNotAvailableToast();
                    }
                }

                @Override
                public void onNetworkNotAvailable() {
                    if (mWeakReference != null) {
                        mWeakReference.get().hideCommentLoading();
                        mWeakReference.get().showNetworkNotAvailableToast();
                    }
                }
            }, productId);
        }
    }

    @Override
    public void cancelProductCommentApiRequest() {

    }

    @Override
    public void cancelProductDetailsApiRequest() {
        repository.cancelProductDetailsByProductIdApiRequest();
    }
}
