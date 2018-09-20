package com.yaratech.yaratube.ui.more;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yaratech.yaratube.R;
import com.yaratech.yaratube.data.AppDataManager;
import com.yaratech.yaratube.ui.aboutus.AboutFragment;
import com.yaratech.yaratube.ui.login.LoginDialogFragment;
import com.yaratech.yaratube.ui.profile.ProfileFragment;
import com.yaratech.yaratube.utils.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoreFragment extends Fragment implements MoreContract.View {
    //--------------------------------------------------------------------------------------------------------
    private static final String TAG = "MoreFragment";


    @BindView(R.id.fragment_more_profile_tv)
    TextView profileFragmentTextView;

    @BindView(R.id.fragment_more_about_us_tv)
    TextView aboutFragmentTextView;

    private MoreContract.Presenter mPresenter;
    private Unbinder mUnBinder;
    private ProfileFragment profileFragment;
    private AppDataManager appDataManager;
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

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        profileFragmentTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.isUserAuthorized();
            }
        });

        aboutFragmentTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAboutFragment();
            }
        });
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
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView() called <<<<Test>>>");
        mUnBinder.unbind();
        mPresenter.unSubscribe();
        mPresenter.detachView();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy() called  <<<<Test>>>");
        super.onDestroy();
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void setAppDataManager(AppDataManager appDataManager) {
        this.appDataManager = appDataManager;
    }
}
