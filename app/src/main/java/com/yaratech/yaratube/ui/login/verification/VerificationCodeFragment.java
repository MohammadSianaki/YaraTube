package com.yaratech.yaratube.ui.login.verification;

import android.Manifest;
import android.content.IntentFilter;
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
import com.yaratech.yaratube.ui.login.LoginDialogFragment;
import com.yaratech.yaratube.utils.PermissionUtils;
import com.yaratech.yaratube.utils.TextUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class VerificationCodeFragment extends Fragment implements VerificationContract.View {
    //------------------------------------------------------------------------------------------
    private static final String TAG = "VerificationDialogFragm";
    private static final String KEY_MOBILE_PHONE_NUMBER = "KEY_MOBILE_PHONE_NUMBER";
    private static final int SMS_PERMISSION_REQUEST_CODE = 1234;
    private static final String[] SMS_PERMISSION = new String[]{Manifest.permission.READ_SMS};
    public static final String KEY_ONE_TIME_PASSWORD = "KEY_ONE_TIME_PASSWORD";


    @BindView(R.id.et_code_number_verification_code_dialog_input)
    EditText verificationCodeEditText;

    @BindView(R.id.btn_verification_code_dialog_submit_code)
    Button verificationCodeSubmitButton;

    @BindView(R.id.btn_verification_code_dialog_corrent_phone_number)
    Button verificationCodeCorrectButton;

    private VerificationContract.Presenter mPresenter;
    private Unbinder mUnBinder;
    private AppDataManager appDataManager;
    private SmsBroadcastReceiver smsReceiver;
    private SmsBroadcastReceiver.Listener smsListener;
    //------------------------------------------------------------------------------------------

    public VerificationCodeFragment() {
        //no-arg constructor
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
        if (getArguments() != null) {
            getArguments().putString(KEY_MOBILE_PHONE_NUMBER, mPresenter.getUserMobilePhoneNumber());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SMS_PERMISSION_REQUEST_CODE
                && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // permission was granted
            Log.d(TAG, "onRequestPermissionsResult: permission was granted");
            getMessageFromBroadcast();
        } else {
            // permission denied
            mPresenter.observeVerificationCodeInput(RxTextView.textChangeEvents(verificationCodeEditText), getArguments().getString(KEY_MOBILE_PHONE_NUMBER));
            Log.d(TAG, "onRequestPermissionsResult: permission denied");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (PermissionUtils.checkMarshmallowPermission()) {
            // check runtime permissions for api 23 and higher
            if (!PermissionUtils.checkSMSPermissions(getActivity())) {
                Log.d(TAG, "onActivityCreated: request runtime permission");
                requestPermissions(SMS_PERMISSION, SMS_PERMISSION_REQUEST_CODE);
            } else {
                Log.d(TAG, "onActivityCreated: permission has already been granted");
                getMessageFromBroadcast();
            }
        } else {
            // api 22 and lower don't need runtime permissions
            getMessageFromBroadcast();
        }


    }

    private void getMessageFromBroadcast() {
        Log.d(TAG, "getMessageFromBroadcast() called");
        smsReceiver = new SmsBroadcastReceiver();
        smsReceiver.setListener(smsListener);
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.provider.Telephony.SMS_RECEIVED");
        getContext().registerReceiver(smsReceiver, filter);
    }

    @Override
    public void showLoginStepTwoDialog() {
        sendMessageToParentFragment(new Event.ChildParentMessage(Event.MOBILE_PHONE_NUMBER_CORRECT_BUTTON_CLICK_MESSAGE, Event.LOGIN_STEP_TWO));
    }


    @Override
    public void verifyButtonClickHandler(String verificationCode) {
        mPresenter.observerSubmitButtonClicks(RxView.clicks(verificationCodeSubmitButton), getArguments().getString(KEY_MOBILE_PHONE_NUMBER),
                verificationCode);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.observeVerificationCodeInput(RxTextView.textChangeEvents(verificationCodeEditText), getArguments().getString(KEY_MOBILE_PHONE_NUMBER));
        mPresenter.observerCorrectButtonClicks(RxView.clicks(verificationCodeCorrectButton));
    }

    @Override
    public void onDestroyView() {
        mPresenter.unSubscribe();
        mUnBinder.unbind();
        mPresenter.detachView();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        if (smsReceiver != null) {
            getContext().unregisterReceiver(smsReceiver);
        }
        super.onDestroy();
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

    public SmsBroadcastReceiver.Listener getSmsListener() {
        return new SmsBroadcastReceiver.Listener() {
            @Override
            public void onTextReceived(String text) {
                String OTP = TextUtils.removeNonDigits(text);
                Log.d(TAG, "onReceivedMessage() called with: message = [" + OTP + "]");
                mPresenter.verifyUserWithPhoneNumberAndVerificationCode(getArguments().getString(KEY_MOBILE_PHONE_NUMBER), OTP);

            }
        };
    }

    @Override
    public void closeDialog() {
        if (getParentFragment() != null) {
            ((LoginDialogFragment) getParentFragment()).closeDialog();
        }
    }
}
