package com.yaratech.yaratube.ui.login;

import com.yaratech.yaratube.ui.base.BasePresenter;
import com.yaratech.yaratube.ui.base.BaseView;

public interface LoginContract {

    interface View extends BaseView {
        void closeDialog();

        void showVerificationDialog();

        void showUserHasBeenLoginToast();

        void showLoginPhoneNumberDialog();

        void showLoginMethodDialog();
    }


    interface Presenter extends BasePresenter<View> {
        void saveLoginStep(int loginStep);

        void saveUserMobilePhoneNumber(String phoneNumber);

        void checkUserStepLogin();
    }


}
