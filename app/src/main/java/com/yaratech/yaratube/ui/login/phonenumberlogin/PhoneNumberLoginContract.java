package com.yaratech.yaratube.ui.login.phonenumberlogin;

import com.yaratech.yaratube.ui.base.BasePresenter;
import com.yaratech.yaratube.ui.base.BaseView;

import io.reactivex.Observable;

public class PhoneNumberLoginContract {

    interface View extends BaseView {
        void showVerificationCodeDialog();

        void showToastError(String message);

        void submitPhoneNumber(String phoneNumber);

    }


    interface Presenter extends BasePresenter<View> {

        void observePhoneNumberInput(Observable textViewObservable);

        void observeSubmitButton(Observable buttonObservable, String phoneNumber);
    }


}
