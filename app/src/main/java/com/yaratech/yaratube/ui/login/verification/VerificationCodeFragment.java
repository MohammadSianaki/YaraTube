package com.yaratech.yaratube.ui.login.verification;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.yaratech.yaratube.R;
import com.yaratech.yaratube.data.AppDataManager;
import com.yaratech.yaratube.data.model.other.Event;
import com.yaratech.yaratube.ui.BaseActivity;
import com.yaratech.yaratube.ui.login.LoginDialogFragment;
import com.yaratech.yaratube.utils.TextUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class VerificationCodeFragment extends Fragment implements VerificationContract.View {
    //------------------------------------------------------------------------------------------
    private static final String TAG = "VerificationDialogFragm";
    private static final String KEY_MESSAGE = "KEY_MESSAGE";
    private static final String KEY_MOBILE_PHONE_NUMBER = "KEY_MOBILE_PHONE_NUMBER";


    @BindView(R.id.et_code_number_verification_code_dialog_input)
    EditText verificationCodeEditText;

    @BindView(R.id.btn_verification_code_dialog_submit_code)
    Button verificationCodeSubmitButton;

    @BindView(R.id.btn_verification_code_dialog_corrent_phone_number)
    Button verificationCodeCorrectButton;

    private VerificationContract.Presenter mPresenter;
    private boolean autoReadOtp = false;
    private Unbinder mUnBinder;
    private AppDataManager appDataManager;
    private SmsListener smsListener;
    private SmsReceiver smsReceiver;
    //------------------------------------------------------------------------------------------

    public VerificationCodeFragment() {
    }

    public static VerificationCodeFragment newInstance() {

        Bundle args = new Bundle();
        VerificationCodeFragment fragment = new VerificationCodeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        smsListener = getSmsListener();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_verification_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnBinder = ButterKnife.bind(this, view);
        mPresenter = new VerificationPresenter(appDataManager);
        mPresenter.attachView(this);
        smsReceiver = new SmsReceiver();
        if (getArguments() != null) {
            getArguments().putString(KEY_MOBILE_PHONE_NUMBER, mPresenter.getUserMobilePhoneNumber());
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void showLoginStepTwoDialog() {
        sendMessageToParentFragment(new Event.ChildParentMessage(Event.MOBILE_PHONE_NUMBER_CORRECT_BUTTON_CLICK_MESSAGE, Event.LOGIN_STEP_TWO));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == BaseActivity.PERMISSION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                autoReadOtp = true;
            } else {
                autoReadOtp = false;
            }
        }
    }


    @Override
    public void verifyButtonClickHandler(String verificationCode) {
        mPresenter.observerSubmitButtonClicks(RxView.clicks(verificationCodeSubmitButton), getArguments().getString(KEY_MOBILE_PHONE_NUMBER),
                verificationCode);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (autoReadOtp) {
            smsReceiver.bindListener(smsListener);
        } else {
            Log.d(TAG, "onActivityCreated() called : autoReadOtp is not allowed");
            mPresenter.observeVerificationCodeInput(RxTextView.textChangeEvents(verificationCodeEditText), getArguments().getString(KEY_MOBILE_PHONE_NUMBER));

            Disposable buttonDisposable = RxView
                    .clicks(verificationCodeSubmitButton)
                    .throttleFirst(3, TimeUnit.SECONDS)
                    .subscribe(new Consumer<Object>() {
                        @Override
                        public void accept(Object o) throws Exception {
                        }
                    });

            Disposable editTextDisposable = RxView
                    .clicks(verificationCodeCorrectButton)
                    .subscribe(new Consumer<Object>() {
                        @Override
                        public void accept(Object o) throws Exception {
                            Log.d("Correct Button", "accept: ");
                            showLoginStepTwoDialog();
                        }
                    });
        }
    }

    @Override
    public void onDestroyView() {
        mUnBinder.unbind();
        mPresenter.detachView();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        smsReceiver.unBindListener();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    public void sendMessageToParentFragment(Event.ChildParentMessage event) {
        EventBus.getDefault().post(event);
    }

    public void setAppDataManager(AppDataManager appDataManager) {
        this.appDataManager = appDataManager;
    }

    public SmsListener getSmsListener() {
        return new SmsListener() {
            @Override
            public void onReceivedMessage(String message) {
                if (getArguments() != null) {
                    getArguments().putString(KEY_MESSAGE, message);
                    String OTP = TextUtils.removeNonDigits(getArguments().getString(KEY_MESSAGE));
                    Log.d(TAG, "onReceivedMessage(): removeNonDigits Returned : " + OTP);
                }
            }
        };
    }

    @Override
    public void closeDialog() {
        ((LoginDialogFragment) getParentFragment()).closeDialog();
    }
}
