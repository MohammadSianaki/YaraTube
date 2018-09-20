package com.yaratech.yaratube.ui.more;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yaratech.yaratube.R;
import com.yaratech.yaratube.data.AppDataManager;
import com.yaratech.yaratube.ui.aboutus.AboutFragment;
import com.yaratech.yaratube.ui.login.LoginDialogFragment;
import com.yaratech.yaratube.ui.profile.ProfileFragment;
import com.yaratech.yaratube.utils.ActivityUtils;
import com.yaratech.yaratube.utils.SnackbarUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoreFragment extends Fragment implements
        MoreContract.View,
        MoreAdapter.OnMorePageItemClickListener {
    //--------------------------------------------------------------------------------------------------------
    private static final String TAG = "MoreFragment";


    @BindView(R.id.more_fragment_list_item)
    RecyclerView recyclerView;

    private MoreContract.Presenter mPresenter;
    private Unbinder mUnBinder;
    private ProfileFragment profileFragment;
    private AppDataManager appDataManager;
    private MoreAdapter moreAdapter;
    //--------------------------------------------------------------------------------------------------------

    public MoreFragment() {
        // Required empty public constructor
    }

    public static MoreFragment newInstance() {

        Bundle args = new Bundle();

        MoreFragment fragment = new MoreFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            profileFragment = ProfileFragment.newInstance();
            initProfileFragmentDependency();
        }

    }

    private void initProfileFragmentDependency() {
        profileFragment.setAppDataManager(appDataManager);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_more, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnBinder = ButterKnife.bind(this, view);
        mPresenter = new MorePresenter(appDataManager, new CompositeDisposable());
        mPresenter.attachView(this);
        setupRecyclerView();

    }

    private void setupRecyclerView() {
        moreAdapter = new MoreAdapter(this);
        recyclerView.setAdapter(moreAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void showAboutFragment() {
        AboutFragment aboutFragment = AboutFragment.newInstance();
        ActivityUtils
                .addFragmentToActivity(
                        getFragmentManager(),
                        aboutFragment,
                        R.id.fl_base_activity_content,
                        true,
                        AboutFragment.class.getSimpleName());
    }

    @Override
    public void showProfileFragment() {
        ProfileFragment profileFragment = ProfileFragment.newInstance();
        profileFragment.setAppDataManager(appDataManager);
        ActivityUtils
                .addFragmentToActivity(
                        getFragmentManager(),
                        profileFragment,
                        R.id.fl_base_activity_content,
                        true,
                        ProfileFragment.class.getSimpleName());
    }

    @Override
    public void showLoginDialog() {
        LoginDialogFragment loginFragment = LoginDialogFragment.newInstance();
        loginFragment.setAppDataManager(appDataManager);
        loginFragment.show(getFragmentManager(), LoginDialogFragment.class.getSimpleName());
    }

    @Override
    public void showSuccessfulLogoutMessage() {
        SnackbarUtils
                .showSuccessfulMessage(getView(), getString(R.string.successful_logout_message));
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView() called <<<<Test>>>");
        mUnBinder.unbind();
        mPresenter.unSubscribe();
        mPresenter.detachView();
        super.onDestroyView();
    }

    public void setAppDataManager(AppDataManager appDataManager) {
        this.appDataManager = appDataManager;
    }

    @Override
    public void onClick(String title) {
        switch (title) {
            case "پروفایل":
                mPresenter.isUserAuthorized();
                break;
            case "درباره ما":
                showAboutFragment();
                break;
            case "تماس با ما":
                break;
            case "خروج از حساب کاربری":
                mPresenter.logout();
                break;
        }
    }
}
