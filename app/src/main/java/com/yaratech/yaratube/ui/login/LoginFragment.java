package com.yaratech.yaratube.ui.login;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yaratech.yaratube.R;
import com.yaratech.yaratube.data.model.Event;
import com.yaratech.yaratube.data.source.GlobalBus;
import com.yaratech.yaratube.data.source.UserRepository;
import com.yaratech.yaratube.ui.login.loginmethod.LoginMethodFragment;
import com.yaratech.yaratube.ui.login.phonenumberlogin.PhoneNumberLoginFragment;
import com.yaratech.yaratube.ui.login.verification.VerificationDialogFragment;

import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends DialogFragment implements LoginContract.View {
    //---------------------------------------------------------------------------------------
    private static final String TAG = "LoginFragment";
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
            verificationDialogFragment = VerificationDialogFragment.newInstance();
            initDependencies();
        }
    }

    private void initDependencies() {
        phoneNumberLoginFragment.setUserRepository(userRepository);
        phoneNumberLoginFragment.setCompositeDisposable(compositeDisposable);
        verificationDialogFragment.setUserRepository(userRepository);
        verificationDialogFragment.setCompositeDisposable(compositeDisposable);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        GlobalBus.getINSTANCE().register(this);
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnBinder = ButterKnife.bind(this, view);
        mPresenter = new LoginPresenter(userRepository);
        mPresenter.attachView(this);
        addFragment(loginMethodFragment, false, LoginMethodFragment.class.getSimpleName());
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
        compositeDisposable.clear();
        mUnBinder.unbind();
        mPresenter.detachView();
        super.onDestroyView();
        GlobalBus.getINSTANCE().unregister(this);
    }

    @Override
    public void onDestroy() {
        Log.d("onDestroy()", "LoginFragment: ");
        super.onDestroy();
    }

    private void addFragment(Fragment fragment, boolean addToBackStack, String tag) {
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_login_fragment_content, fragment, tag)
                .commit();
    }

    @Subscribe
    public void getMessageFromChildFragment(Event.ChildParentMessage event) {
        if (event.getMessage().equals(Event.MOBILE_PHONE_NUMBER_METHOD_BUTTON_CLICK_MESSAGE)) {
            Log.d(TAG, "getMessageFromChildFragment: LoginMethod :  MobilePhoneNumber Button Clicked  ");
            mPresenter.saveLoginStep(event.getLoginStep());
            addFragment(phoneNumberLoginFragment, false, PhoneNumberLoginFragment.class.getSimpleName());
        }
        if (event.getMessage().equals(Event.MOBILE_PHONE_NUMBER_SUBMIT_BUTTON_CLICK_MESSAGE)) {
            mPresenter.saveLoginStep(event.getLoginStep());

            Log.d(TAG, "getMessageFromChildFragment: <<<< PhoneNumberLoginDialog >>>> submit button clicked");
            addFragment(verificationDialogFragment, false, VerificationDialogFragment.class.getSimpleName());
        }
        if (event.getMessage().equals(Event.MOBILE_PHONE_NUMBER_VERIFY_BUTTON_CLICK_MESSAGE)) {
            mPresenter.saveLoginStep(event.getLoginStep());
            dismiss();
        }
        if (event.getMessage().equals(Event.MOBILE_PHONE_NUMBER_CORRECT_BUTTON_CLICK_MESSAGE)) {
            mPresenter.saveLoginStep(event.getLoginStep());
            addFragment(phoneNumberLoginFragment, false, PhoneNumberLoginFragment.class.getSimpleName());
        }
    }

    public void sendMessageToChildFragment(Event.ParentChildMessage event) {

    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void setCompositeDisposable(CompositeDisposable compositeDisposable) {
        this.compositeDisposable = compositeDisposable;
    }
}
