package com.yaratech.yaratube.ui.productdetails;

import com.yaratech.yaratube.data.model.Comment;
import com.yaratech.yaratube.data.model.ProductDetails;
import com.yaratech.yaratube.ui.base.BasePresenter;
import com.yaratech.yaratube.ui.base.BaseView;

import java.util.List;

public interface DetailsContract {

    interface View extends BaseView {
        void showLoadedData(ProductDetails productDetails);

        void showLoadedComments(List<Comment> comments);

        void showCommentLoading();

        void hideCommentLoading();
    }

    interface Presenter extends BasePresenter<View> {
        void fetchProductDetails(int productId);

        void fetchProductComments(int productId);

        void cancelProductCommentApiRequest();

        void cancelProductDetailsApiRequest();

    }

}
