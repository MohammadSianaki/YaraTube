package com.yaratech.yaratube.ui.login.loginmethod;

import com.yaratech.yaratube.data.model.other.Event;
import com.yaratech.yaratube.ui.base.BasePresenter;
import com.yaratech.yaratube.ui.base.BaseView;

public interface LoginMethodContract {


    interface View extends BaseView {

        void showEnterMobileNumberDialog();

        void showSuccessfulGoogleLogin();

        void sendMessageToParentFragment(Event.ChildParentMessage event);
    }

    interface Presenter extends BasePresenter<View> {

        void getMobilePhoneNumber();

        void performGoogleSignIn(String token);
    }
}
