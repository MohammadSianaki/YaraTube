package com.yaratech.yaratube.ui.login.verification;

import com.yaratech.yaratube.data.model.db.User;
import com.yaratech.yaratube.ui.base.BasePresenter;
import com.yaratech.yaratube.ui.base.BaseView;

import io.reactivex.Observable;

public interface VerificationContract {

    interface View extends BaseView {
        void closeDialog();

        void showLoginStepTwoDialog();

        void verifyButtonClickHandler(String verificationCode);
    }


    interface Presenter extends BasePresenter<View> {
        void saveUserLoginInfoIntoDatabase(User user);

        void saveUserTokenToSharedPref(String token);

        void verifyUserWithPhoneNumberAndVerificationCode(String phoneNumber, String verificationCode);

        String getUserMobilePhoneNumber();

        void observeVerificationCodeInput(
                Observable observable, String phoneNumber);

        void observerSubmitButtonClicks(Observable buttonClicks, String phoneNumber, String verificationCode);

        void observerCorrectButtonClicks(Observable buttonClicks);
    }

}
