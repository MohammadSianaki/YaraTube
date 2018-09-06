package com.yaratech.yaratube.ui.productdetails;

import com.yaratech.yaratube.data.model.other.Comment;
import com.yaratech.yaratube.data.model.other.Product;
import com.yaratech.yaratube.ui.base.BasePresenter;
import com.yaratech.yaratube.ui.base.BaseView;

import java.util.List;

public interface DetailsContract {

    interface View extends BaseView {
        void showLoadedData(Product productDetails);

        void showLoadedComments(List<Comment> comments);

        void showCommentLoading();

        void hideCommentLoading();

        void showDataNotAvailableToast();

        void showNetworkNotAvailableToast();

        void showProgressBarLoading();

        void finishProgressBarLoading();

        void showCommentDialog(String token);

        void showLoginDialog();

        void goToPlayerActivity();
    }

    interface Presenter extends BasePresenter<View> {
        void fetchProductDetails(int productId);

        void fetchProductComments(int productId, int offset, int limit);

        void isUserLogin();

        void isUserLoginToPlay();
    }

}
