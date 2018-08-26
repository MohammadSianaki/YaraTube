package com.yaratech.yaratube.ui.login.loginmethod;

import com.yaratech.yaratube.ui.base.BasePresenter;
import com.yaratech.yaratube.ui.base.BaseView;

public interface LoginMethodContract {


    interface View extends BaseView {

        void showEnterMobileNumberDialog();
    }

    interface Presenter extends BasePresenter<View> {

        void getMobilePhoneNumber();

    }
}
