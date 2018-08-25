package com.yaratech.yaratube.ui.login;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yaratech.yaratube.R;
import com.yaratech.yaratube.data.source.UserRepository;
import com.yaratech.yaratube.ui.login.loginmethod.LoginMethodFragment;
import com.yaratech.yaratube.ui.login.phonenumberlogin.PhoneNumberLoginFragment;
import com.yaratech.yaratube.ui.login.verification.VerificationDialogFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends DialogFragment implements LoginContract.View {
    //---------------------------------------------------------------------------------------
    private LoginContract.Presenter mPresenter;
    private Unbinder mUnBinder;
    private LoginMethodFragment loginMethodFragment;
    private VerificationDialogFragment verificationDialogFragment;
    private PhoneNumberLoginFragment phoneNumberLoginFragment;

    private UserRepository userRepository;
    private CompositeDisposable compositeDisposable;

    //---------------------------------------------------------------------------------------

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {

        Bundle args = new Bundle();

        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            loginMethodFragment = LoginMethodFragment.newInstance();
            phoneNumberLoginFragment = PhoneNumberLoginFragment.newInstance();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnBinder = ButterKnife.bind(this, view);
        mPresenter = new LoginPresenter();
        mPresenter.attachView(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onDestroyView() {
        mUnBinder.unbind();
        mPresenter.detachView();
        super.onDestroyView();
    }

    private void addFragment(Fragment fragment) {

    }


    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void setCompositeDisposable(CompositeDisposable compositeDisposable) {
        this.compositeDisposable = compositeDisposable;
    }
}
