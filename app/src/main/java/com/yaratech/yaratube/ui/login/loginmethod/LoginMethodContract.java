package com.yaratech.yaratube.ui.login.loginmethod;

import com.yaratech.yaratube.data.model.db.User;
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
        void saveUserLoginInfoIntoDatabase(User user);

        void saveUserTokenToSharedPref(String token);

        void getMobilePhoneNumber();

        void performGoogleSignIn(String token, String givenName, String email, String photoUrl);
    }
}
