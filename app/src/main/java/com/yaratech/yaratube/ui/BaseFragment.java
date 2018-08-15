package com.yaratech.yaratube.ui;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
    private static final String TAG = "BaseFragment";

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;

    private HomeFragment homeFragment;
    private CategoryFragment categoryFragment;
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
        if (savedInstanceState == null) {
            homeFragment = HomeFragment.newInstance();
            categoryFragment = CategoryFragment.newInstance();
        }
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
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        switch (item.getItemId()) {
            case R.id.bottom_nav_category_item:
                if (categoryFragment.isAdded()) {
                    Log.i(TAG, "chooseFragment: <<<< CategoryFragment is  added before >>>>");
                    fragmentManager
                            .beginTransaction()
                            .show(categoryFragment)
                            .commit();
                } else {
                    addFragment(categoryFragment, false, "CategoryFragment");
                    Log.i(TAG, "chooseFragment: <<<< CategoryFragment is NOT added before >>>>");
                }
                if (homeFragment.isAdded()) {
                    Log.i(TAG, "chooseFragment <<<< bottom_nav_category_item >>>> : <<<< HomeFragment is added before >>>>");
                    fragmentManager
                            .beginTransaction()
                            .hide(homeFragment)
                            .commit();
                }
                break;
            case R.id.bottom_nav_main_screen_item:
                if (homeFragment.isAdded()) {
                    Log.i(TAG, "chooseFragment: <<<< HomeFragment is added before >>>>");
                    fragmentManager
                            .beginTransaction()
                            .show(homeFragment)
                            .commit();
                } else {
                    addFragment(homeFragment, false, "HomeFragment");
                    Log.i(TAG, "chooseFragment: <<<< HomeFragment is NOT added before >>>>");
                }
                if (categoryFragment.isAdded()) {
                    Log.i(TAG, "chooseFragment <<<< bottom_nav_category_item >>>> : <<<< categoryFragment is added before >>>>");
                    fragmentManager
                            .beginTransaction()
                            .hide(categoryFragment)
                            .commit();
                }
                break;
        }
    }

    private void addFragment(Fragment fragment, boolean addToBackStack, String tag) {
        ActivityUtils
                .addFragmentToActivity(
                        getActivity().getSupportFragmentManager(),
                        fragment, R.id.fl_base_fragment_content,
                        addToBackStack, tag);
    }

}

