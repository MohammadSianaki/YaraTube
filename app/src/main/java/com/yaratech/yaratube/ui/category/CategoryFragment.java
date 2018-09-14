package com.yaratech.yaratube.ui.category;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.yaratech.yaratube.R;
import com.yaratech.yaratube.data.AppDataManager;
import com.yaratech.yaratube.data.model.other.Category;
import com.yaratech.yaratube.utils.SnackbarUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment implements CategoryAdapter.OnRecyclerViewInteractionListener, CategoryContract.View {

    //------------------------------------------------------------------------------------------------

    private static final String TAG = "CategoryFragment";

    @BindView(R.id.rv_categories)
    RecyclerView recyclerViewCategories;

    @BindView(R.id.pb_category_loading)
    ProgressBar progressBar;

    @BindView(R.id.category_fragment_coordinator)
    CoordinatorLayout coordinatorLayout;

    private CategoryAdapter categoryAdapter;
    private CategoryContract.Presenter mPresenter;
    private OnCategoryFragmentInteractionListener mListener;
    private Unbinder mUnBinder;
    private AppDataManager appDataManager;

    //------------------------------------------------------------------------------------------------
    public CategoryFragment() {
        // Required empty public constructor
    }

    public static CategoryFragment newInstance() {

        Bundle args = new Bundle();

        CategoryFragment fragment = new CategoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i(TAG, "onAttach: CategoryFragment");
        if (context instanceof OnCategoryFragmentInteractionListener) {
            mListener = (OnCategoryFragmentInteractionListener) context;
        } else {
            throw new ClassCastException("Not Instance OF OnCategoryFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: CategoryFragment");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i(TAG, "onCreateView: CategoryFragment");
        return inflater.inflate(R.layout.fragment_category, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "onViewCreated: CategoryFragment");
        mUnBinder = ButterKnife.bind(this, view);
        categoryAdapter = new CategoryAdapter(this);
        mPresenter = new CategoryPresenter(appDataManager);
        mPresenter.attachView(this);
        setupRecyclerView();

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "onActivityCreated: CategoryFragment");
        mPresenter.fetchCategoriesFromRemoteDataSource();
    }


    @Override
    public void onStart() {
        Log.i(TAG, "onStart: CategoryFragment");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.i(TAG, "onResume: CategoryFragment");
        super.onResume();
    }


    @Override
    public void onPause() {
        Log.i(TAG, "onPause: CategoryFragment");
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        Log.i(TAG, "onSaveInstanceState: CategoryFragment");
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStop() {
        Log.i(TAG, "onStop: CategoryFragment");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        mUnBinder.unbind();
        mPresenter.unSubscribe();
        mPresenter.detachView();
        super.onDestroyView();
        Log.i(TAG, "onDestroyView: CategoryFragment");
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy: CategoryFragment");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        mListener = null;
        Log.i(TAG, "onDetach: CategoryFragment");
        super.onDetach();
    }

    private void setupRecyclerView() {
        recyclerViewCategories.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerViewCategories.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerViewCategories.setAdapter(categoryAdapter);
    }

    @Override
    public void onItemClicked(Category category) {
        mListener.showProductsOfRequestedCategoryItem(category);
    }

    @Override
    public void showLoadedData(List list) {
        categoryAdapter.setCategoryList(list);
    }

    @Override
    public void showDataNotAvailableToast() {
        SnackbarUtils
                .showServerConnectionFailureSnackbar(coordinatorLayout, new SnackbarUtils.SnackbarCallback() {
                    @Override
                    public void onRetryAgainPressed() {
                        mPresenter.fetchCategoriesFromRemoteDataSource();
                    }
                });
    }

    @Override
    public void showNetworkNotAvailableToast() {
        Toast.makeText(getContext(), "Check you network connection...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressBarLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void finishProgressBarLoading() {
        progressBar.setVisibility(View.GONE);
    }

    public void setAppDataManager(AppDataManager appDataManager) {
        this.appDataManager = appDataManager;
    }

    public interface OnCategoryFragmentInteractionListener {
        void showProductsOfRequestedCategoryItem(Category item);
    }

}
