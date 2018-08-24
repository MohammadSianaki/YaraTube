package com.yaratech.yaratube.ui.login.verification;

import com.yaratech.yaratube.ui.base.BasePresenter;
import com.yaratech.yaratube.ui.base.BaseView;

import io.reactivex.Observable;

public interface VerificationContract {

    interface View extends BaseView {

    }


    interface Presenter extends BasePresenter<View> {

        void observeVerificationCodeInput(Observable observable, String string);
    }

}
