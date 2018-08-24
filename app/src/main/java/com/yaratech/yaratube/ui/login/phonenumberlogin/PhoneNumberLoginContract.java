package com.yaratech.yaratube.ui.login.phonenumberlogin;

import com.yaratech.yaratube.ui.base.BasePresenter;
import com.yaratech.yaratube.ui.base.BaseView;

import io.reactivex.Observable;

public class PhoneNumberLoginContract {

    interface View extends BaseView {
        void showVerificationCodeDialog(String phoneNumber);

        void showToastError(String message);
    }


    interface Presenter extends BasePresenter<View> {

        void observePhoneNumberInput(Observable observable);
    }


}
