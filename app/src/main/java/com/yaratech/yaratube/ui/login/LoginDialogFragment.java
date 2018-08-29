package com.yaratech.yaratube.ui.login;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yaratech.yaratube.R;
import com.yaratech.yaratube.data.model.Event;
import com.yaratech.yaratube.data.source.UserRepository;
import com.yaratech.yaratube.ui.login.loginmethod.LoginMethodFragment;
import com.yaratech.yaratube.ui.login.phonenumberlogin.PhoneNumberLoginFragment;
import com.yaratech.yaratube.ui.login.verification.VerificationCodeFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginDialogFragment extends DialogFragment implements LoginContract.View {
    //---------------------------------------------------------------------------------------
    private static final String TAG = "LoginDialogFragment";
    private LoginContract.Presenter mPresenter;
    private Unbinder mUnBinder;
    private UserRepository userRepository;
    private CompositeDisposable compositeDisposable;

    //---------------------------------------------------------------------------------------

    public LoginDialogFragment() {
        // Required empty public constructor
    }

    public static LoginDialogFragment newInstance() {

        Bundle args = new Bundle();

        LoginDialogFragment fragment = new LoginDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "<<<<    lifecycle   >>>>    onCreate    LoginDialogFragment: ");
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            initDependencies();
        }
    }

    private void initDependencies() {

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d(TAG, "onCreateDialog() called with: savedInstanceState = [" + savedInstanceState + "]");
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView() called with: inflater = [" + inflater + "], container = ["
                + container + "], savedInstanceState = [" + savedInstanceState + "]");
        // Inflate the layout for this fragment
        Log.d(TAG, "<<<<    lifecycle   >>>>    onCreateView: LoginDialogFragment");
        EventBus.getDefault().register(this);
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "<<<<    lifecycle   >>>>    onViewCreated: LoginDialogFragment");
        super.onViewCreated(view, savedInstanceState);
        mUnBinder = ButterKnife.bind(this, view);
        mPresenter = new LoginPresenter(userRepository);
        mPresenter.attachView(this);
        mPresenter.checkUserStepLogin();
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
        Log.d(TAG, "<<<<    lifecycle   >>>>    onDestroyView() called");
        compositeDisposable.clear();
        mUnBinder.unbind();
        mPresenter.detachView();
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "<<<<    lifecycle   >>>>    LoginDialogFragment: ");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "<<<<    lifecycle   >>>>    onDetach() called");
        super.onDetach();
    }

    private void addFragment(Fragment fragment, boolean addToBackStack, String tag) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        if (fragment.isAdded()) {
            if (tag.equals(PhoneNumberLoginFragment.class.getSimpleName())) {
                Log.d("Correct Button", "addFragment: PhoneNumberLoginFragment");
            }
            for (Fragment fr : getChildFragmentManager().getFragments()) {
                transaction
                        .hide(fr);

            }
            transaction
                    .show(fragment)
                    .commit();
        } else {
            transaction
                    .add(R.id.fl_login_fragment_content, fragment, tag)
                    .commit();
        }
    }

    @Subscribe
    public void getMessageFromChildFragment(Event.ChildParentMessage event) {
        if (event.getMessage().equals(Event.MOBILE_PHONE_NUMBER_METHOD_BUTTON_CLICK_MESSAGE)) {
            Log.d(TAG, "getMessageFromChildFragment: LoginMethod :  MobilePhoneNumber Button Clicked  ");
            mPresenter.saveLoginStep(event.getLoginStep());
            showLoginPhoneNumberDialog();
        }
        if (event.getMessage().equals(Event.MOBILE_PHONE_NUMBER_SUBMIT_BUTTON_CLICK_MESSAGE)) {
            Log.d(TAG, "getMessageFromChildFragment: <<<< PhoneNumberLoginDialog >>>> submit button clicked");
            mPresenter.saveLoginStep(event.getLoginStep());
            showVerificationDialog();
        }
        if (event.getMessage().equals(Event.MOBILE_PHONE_NUMBER_VERIFY_BUTTON_CLICK_MESSAGE)) {
            mPresenter.saveLoginStep(event.getLoginStep());
            closeDialog();
        }
        if (event.getMessage().equals(Event.MOBILE_PHONE_NUMBER_CORRECT_BUTTON_CLICK_MESSAGE)) {
            Log.d("Correct Button", "getMessageFromChildFragment: Correct Button Clicked");
            mPresenter.saveLoginStep(event.getLoginStep());
            showLoginPhoneNumberDialog();
        }
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void setCompositeDisposable(CompositeDisposable compositeDisposable) {
        this.compositeDisposable = compositeDisposable;
    }

    @Override
    public void closeDialog() {
        dismiss();
    }

    @Override
    public void showUserHasBeenLoginToast() {
        closeDialog();
        Toast.makeText(getContext(), "You Are Still Login!!!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoginMethodDialog() {
        LoginMethodFragment loginMethodFragment = (LoginMethodFragment) getChildFragmentManager().findFragmentByTag(LoginMethodFragment.class.getSimpleName());
        if (loginMethodFragment == null) {
            loginMethodFragment = LoginMethodFragment.newInstance();
        }
        addFragment(loginMethodFragment,
                false,
                LoginMethodFragment.class.getSimpleName());
    }

    @Override
    public void showLoginPhoneNumberDialog() {
        Log.d(TAG, "showLoginPhoneNumberDialog: ");
        PhoneNumberLoginFragment phoneNumberLoginFragment = (PhoneNumberLoginFragment) getChildFragmentManager().findFragmentByTag(PhoneNumberLoginFragment.class.getSimpleName());
        if (phoneNumberLoginFragment == null) {
            phoneNumberLoginFragment = PhoneNumberLoginFragment.newInstance();
        }
        phoneNumberLoginFragment.setUserRepository(userRepository);
        phoneNumberLoginFragment.setCompositeDisposable(compositeDisposable);
        addFragment(phoneNumberLoginFragment,
                false,
                PhoneNumberLoginFragment.class.getSimpleName());
    }

    @Override
    public void showVerificationDialog() {
        VerificationCodeFragment verificationCodeFragment = (VerificationCodeFragment) getChildFragmentManager().findFragmentByTag(VerificationCodeFragment.class.getSimpleName());
        if (verificationCodeFragment == null) {
            verificationCodeFragment = VerificationCodeFragment.newInstance();
        }
        verificationCodeFragment.setUserRepository(userRepository);
        verificationCodeFragment.setCompositeDisposable(compositeDisposable);
        addFragment(verificationCodeFragment,
                false,
                VerificationCodeFragment.class.getSimpleName());
    }
}
