package com.yaratech.yaratube.ui.login.phonenumberlogin;

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
import android.widget.EditText;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.yaratech.yaratube.R;
import com.yaratech.yaratube.data.model.other.Event;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class PhoneNumberLoginFragment extends Fragment implements PhoneNumberLoginContract.View {
    //----------------------------------------------------------------------------------------------------------------------
    private static final String TAG = "PhoneLoginFragment";

    @BindView(R.id.btn_phone_number_login_dialog_submit)
    Button submitPhoneNumberButton;

    @BindView(R.id.et_phone_number_login_dialog_input)
    EditText phoneNumberEditText;


    private Unbinder mUnBinder;
    private PhoneNumberLoginContract.Presenter mPresenter;
    private UserRepository userRepository;
    private CompositeDisposable compositeDisposable;
    //----------------------------------------------------------------------------------------------------------------------


    public PhoneNumberLoginFragment() {
        // Required empty public constructor
    }


    public static PhoneNumberLoginFragment newInstance() {

        Bundle args = new Bundle();

        PhoneNumberLoginFragment fragment = new PhoneNumberLoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "<<<<    lifecycle   >>>>    onCreate: PhoneLoginFragment");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG, "<<<<    lifecycle   >>>>    onCreateView: PhoneLoginFragment");
        EventBus.getDefault().register(this);
        return inflater.inflate(R.layout.fragment_phone_number_login, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "<<<<    lifecycle   >>>>    onViewCreated: PhoneLoginFragment");

        super.onViewCreated(view, savedInstanceState);
        mUnBinder = ButterKnife.bind(this, view);
        mPresenter = new PhoneNumberLoginPresenter(userRepository, compositeDisposable);
        mPresenter.attachView(this);
        Observable observable = RxTextView.textChangeEvents(phoneNumberEditText);
        RxView.clicks(submitPhoneNumberButton)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        Log.d("Login", "accept() called with: Button Click ");
                        mPresenter.observePhoneNumberInput(observable);
                    }
                });
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "<<<<    lifecycle   >>>>    onDestroyView: PhoneLoginFragment");
        mUnBinder.unbind();
        mPresenter.detachView();
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "<<<<    lifecycle   >>>>    onDestroy: PhoneLoginFragment");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "<<<<    lifecycle   >>>>    onDetach: PhoneLoginFragment");
        super.onDetach();
    }


    @Override
    public void showVerificationCodeDialog() {
        sendMessageToParentFragment(new Event.ChildParentMessage(Event.MOBILE_PHONE_NUMBER_SUBMIT_BUTTON_CLICK_MESSAGE, Event.LOGIN_STEP_THREE));
    }

    @Override
    public void showToastError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Subscribe
    public void getMessageFromParentFragment(Event.ParentChildMessage event) {

    }

    public void sendMessageToParentFragment(Event.ChildParentMessage event) {
        EventBus.getDefault().post(event);
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void setCompositeDisposable(CompositeDisposable compositeDisposable) {
        this.compositeDisposable = compositeDisposable;
    }
}
