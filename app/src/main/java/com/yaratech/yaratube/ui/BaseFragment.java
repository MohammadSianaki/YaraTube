package com.yaratech.yaratube.ui;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.yaratech.yaratube.R;
import com.yaratech.yaratube.ui.category.CategoryFragment;
import com.yaratech.yaratube.ui.home.HomeFragment;
import com.yaratech.yaratube.utils.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener {

    //--------------------------------------------------------------------------------------------
    private static final String TAG = "lifecycle";

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;

    //--------------------------------------------------------------------------------------------

    public BaseFragment() {
        // Required empty public constructor
    }


    public static BaseFragment newInstance() {

        Bundle args = new Bundle();

        BaseFragment fragment = new BaseFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        Log.i(TAG, "onAttach: BaseFragment");
        super.onAttach(context);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: BaseFragment");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: BaseFragment");
        return inflater.inflate(R.layout.fragment_base, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "onViewCreated: BaseFragment");
        ButterKnife.bind(this, view);
        chooseFragment(bottomNavigationView.getMenu().getItem(0));
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onActivityCreated: BaseFragment");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onViewStateRestored: BaseFragment");
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onStart() {
        Log.i(TAG, "onStart: BaseFragment");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.i(TAG, "onResume: BaseFragment");
        super.onResume();
    }


    @Override
    public void onPause() {
        Log.i(TAG, "onPause: BaseFragment");
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        Log.i(TAG, "onSaveInstanceState: BaseFragment");
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStop() {
        Log.i(TAG, "onStop: BaseFragment");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.i(TAG, "onDestroyView: BaseFragment");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy: BaseFragment");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.i(TAG, "onDetach: BaseFragment");
        super.onDetach();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        chooseFragment(item);
        item.setChecked(true);
        return true;
    }


    private void chooseFragment(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.bottom_nav_category_item:
                addFragment(CategoryFragment.newInstance(), false);
                break;
            case R.id.bottom_nav_main_screen_item:
                addFragment(HomeFragment.newInstance(), false);
                break;
        }
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
        Log.i(TAG, "onAttachFragment: <<<<Child Fragment Added>>>>");
    }

    private void addFragment(Fragment fragment, boolean addToBackStack) {
        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .hide(getActivity().getSupportFragmentManager().findFragmentById(R.id.fl_base_fragment_content));
        ActivityUtils.replaceFragmentToActivity(getActivity().getSupportFragmentManager(), fragment, R.id.fl_base_fragment_content, addToBackStack);
    }

}

