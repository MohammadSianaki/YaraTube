package com.yaratech.yaratube.ui.login.loginmethod;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.yaratech.yaratube.R;
import com.yaratech.yaratube.data.AppDataManager;
import com.yaratech.yaratube.data.model.other.Event;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class LoginMethodFragment extends Fragment implements
        LoginMethodContract.View,
        GoogleApiClient.OnConnectionFailedListener {
    //------------------------------------------------------------------------------------------------------
    private static final String TAG = "LoginMethodFragment";
    private static final int GOOGLE_SIGN_ING_REQUEST_CODE = 100;
    @BindView(R.id.btn_mobile_phone_number_sign_in)
    Button mobileLoginButton;

    @BindView(R.id.btn_google_sign_in)
    Button googleSignInButton;

    private Unbinder mUnBinder;
    private LoginMethodContract.Presenter mPresenter;
    private GoogleSignInAccount googleSignInAccount;
    private GoogleApiClient googleApiClient;
    private AppDataManager appDataManager;

    //------------------------------------------------------------------------------------------------------
    public LoginMethodFragment() {
        // no-arg constructor
    }


    public static LoginMethodFragment newInstance() {

        Bundle args = new Bundle();
        LoginMethodFragment fragment = new LoginMethodFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "<<<<    lifecycle   >>>>    onCreate: LoginMethodFragment");
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            initGoogleSingIn();
        }
    }

    private void initGoogleSingIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient
                .Builder(getContext())
                .enableAutoManage(getActivity(), this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "<<<<    lifecycle   >>>>    onCreateView: LoginMethodFragment");
        return inflater.inflate(R.layout.fragment_login_method, container, false);
    }

    @Override
    public void showEnterMobileNumberDialog() {
        sendMessageToParentFragment(new Event.ChildParentMessage(Event.MOBILE_PHONE_NUMBER_METHOD_BUTTON_CLICK_MESSAGE, Event.LOGIN_STEP_TWO));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "<<<<    lifecycle   >>>>    onViewCreated: LoginMethodFragment");
        super.onViewCreated(view, savedInstanceState);
        mUnBinder = ButterKnife.bind(this, view);
        mPresenter = new LoginMethodPresenter(appDataManager);
        mPresenter.attachView(this);
        mobileLoginButton.setOnClickListener(v -> {
            mPresenter.getMobilePhoneNumber();
        });

        googleSignInButton.setOnClickListener(v -> {
            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
            startActivityForResult(signInIntent, GOOGLE_SIGN_ING_REQUEST_CODE);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == GOOGLE_SIGN_ING_REQUEST_CODE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            googleSignInAccount = result.getSignInAccount();
            Log.d(TAG, "handleSignInResult() called with: result = [" + result.isSuccess() + "]");
            mPresenter.performGoogleSignIn(googleSignInAccount.getIdToken());
        } else {
            Log.d(TAG, "handleSignInResult() called with: result = [" + result.isSuccess() + "]");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        googleApiClient.stopAutoManage(getActivity());
        googleApiClient.disconnect();
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "<<<<    lifecycle   >>>>    onDestroyView: LoginMethodFragment");
        mPresenter.unSubscribe();
        mUnBinder.unbind();
        mPresenter.detachView();
        super.onDestroyView();
    }


    @Override
    public void onDestroy() {
        Log.d(TAG, "<<<<    lifecycle   >>>>    onDestroy: LoginMethodFragment");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "<<<<    lifecycle   >>>>    onDetach: LoginMethodFragment");
        super.onDetach();
    }

    @Override
    public void sendMessageToParentFragment(Event.ChildParentMessage event) {
        EventBus.getDefault().post(event);
    }

    @Override
    public void showSuccessfulGoogleLogin() {
        Toast.makeText(getContext(), "Welcome!!!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed: <<<< " + connectionResult.getErrorCode() + " >>>>");
        Log.d(TAG, "onConnectionFailed() called with: connectionResult = [" + connectionResult + "]");
    }

    public void setAppDataManager(AppDataManager appDataManager) {
        this.appDataManager = appDataManager;
    }
}
