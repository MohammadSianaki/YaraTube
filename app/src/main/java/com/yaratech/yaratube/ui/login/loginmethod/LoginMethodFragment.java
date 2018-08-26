package com.yaratech.yaratube.ui.login.loginmethod;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.yaratech.yaratube.R;
import com.yaratech.yaratube.data.model.Event;
import com.yaratech.yaratube.data.source.GlobalBus;

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class LoginMethodFragment extends DialogFragment implements LoginMethodContract.View {
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


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        GlobalBus.getINSTANCE().register(this);
        return inflater.inflate(R.layout.fragment_login_method, container, false);
    }

    @Override
    public void showEnterMobileNumberDialog() {
        dismiss();
        sendMessageToParentFragment(new Event.ChildParentMessage(Event.MOBILE_PHONE_NUMBER_METHOD_BUTTON_CLICK_MESSAGE, Event.LOGIN_STEP_TWO));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
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
        mUnBinder.unbind();
        mPresenter.detachView();
        super.onDestroyView();
        GlobalBus.getINSTANCE().unregister(this);
    }


    @Override
    public void onDestroy() {
        Log.d("onDestroy()", "LoginMethodFragment: ");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Subscribe
    public void getMessageFromParentFragment(Event.ParentChildMessage event) {

    }

    public void sendMessageToParentFragment(Event.ChildParentMessage event) {
        GlobalBus.getINSTANCE().post(event);
    }
}
