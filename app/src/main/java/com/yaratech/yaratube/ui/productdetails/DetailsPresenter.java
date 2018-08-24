package com.yaratech.yaratube.ui.productdetails;

import android.content.Context;

import com.yaratech.yaratube.data.model.Comment;
import com.yaratech.yaratube.data.model.ProductDetails;
import com.yaratech.yaratube.data.source.StoreDataSource;
import com.yaratech.yaratube.data.source.StoreRepository;
import com.yaratech.yaratube.data.source.remote.StoreRemoteDataSource;

import java.util.List;

public class DetailsPresenter implements DetailsContract.Presenter {

    //---------------------------------------------------------------------------------------------
    private StoreRepository repository;
    private DetailsContract.View mView;
    //---------------------------------------------------------------------------------------------


    public DetailsPresenter(Context context) {
        this.repository = StoreRepository.getINSTANCE(new StoreRemoteDataSource((context)));
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
    public void fetchProductDetails(int productId) {
        if (isAttached()) {
            mView.showProgressBarLoading();
            repository.fetchProductDetailsByProductId(new StoreDataSource.ApiResultCallback() {
                @Override
                public void onDataLoaded(Object response) {
                    ProductDetails product = (ProductDetails) response;
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
            }, productId);
        }
    }

    @Override
    public void fetchProductComments(int productId) {
        if (isAttached()) {
            mView.showCommentLoading();
            repository.fetchCommentsOfProductByProductId(new StoreDataSource.ApiResultCallback() {
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
