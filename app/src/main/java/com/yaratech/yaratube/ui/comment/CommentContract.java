package com.yaratech.yaratube.ui.comment;

import com.yaratech.yaratube.ui.base.BasePresenter;
import com.yaratech.yaratube.ui.base.BaseView;

public class CommentContract {
    interface View extends BaseView {

        void showToast(String s);

        void closeDialog();
    }

    interface Presenter extends BasePresenter<View> {

        void submitCommentToProduct(int productId, int score, String title, String textContent, String token);

        void cancelPostComment();
    }

}
