package com.yaratech.yaratube.ui.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yaratech.yaratube.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class LoginFragment extends DialogFragment implements LoginContract.View {
    //------------------------------------------------------------------------------------------------------

    @BindView(R.id.btn_mobile_login)
    ButterKnife loginButton;


    private Unbinder mUnBinder;

    //------------------------------------------------------------------------------------------------------
    public LoginFragment() {
        // no-arg constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static LoginFragment newInstance() {

        Bundle args = new Bundle();
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    @Override
    public void onDestroyView() {
        mUnBinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void showDataNotAvailableToast() {

    }

    @Override
    public void showNetworkNotAvailableToast() {

    }

    @Override
    public void showProgressBarLoading() {

    }

    @Override
    public void finishProgressBarLoading() {

    }
}
