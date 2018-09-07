package com.yaratech.yaratube.ui.login.loginmethod;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.yaratech.yaratube.R;
import com.yaratech.yaratube.data.model.other.Event;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class LoginMethodFragment extends Fragment implements LoginMethodContract.View {
    //------------------------------------------------------------------------------------------------------
    private static final String TAG = "LoginMethodFragment";
    @BindView(R.id.btn_mobile_phone_number_sign_in)
    Button loginButton;


    private Unbinder mUnBinder;
    private LoginMethodContract.Presenter mPresenter;

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
        mPresenter = new LoginMethodPresenter();
        mPresenter.attachView(this);
        loginButton.setOnClickListener(v -> {
            mPresenter.getMobilePhoneNumber();
        });
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
    public void onDestroyView() {
        Log.d(TAG, "<<<<    lifecycle   >>>>    onDestroyView: LoginMethodFragment");
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

    public void sendMessageToParentFragment(Event.ChildParentMessage event) {
        EventBus.getDefault().post(event);
    }
}
