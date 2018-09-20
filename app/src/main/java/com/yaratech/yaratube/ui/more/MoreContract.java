package com.yaratech.yaratube.ui.more;

import com.yaratech.yaratube.ui.base.BasePresenter;
import com.yaratech.yaratube.ui.base.BaseView;

public class MoreContract {

    interface View extends BaseView {

        void showProfileFragment();

        void showLoginDialog();

        void showSuccessfulLogoutMessage();
    }


    interface Presenter extends BasePresenter<View> {
        void isUserAuthorized();

        void logout();
    }


}
