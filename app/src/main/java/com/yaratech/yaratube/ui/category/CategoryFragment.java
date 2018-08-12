package com.yaratech.yaratube.ui.category;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yaratech.yaratube.R;
import com.yaratech.yaratube.data.model.Category;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment implements CategoryAdapter.OnRecyclerViewInteractionListener, CategoryContract.View {

    //------------------------------------------------------------------------------------------------

    private static final String TAG = "CategoryFragment";

    @BindView(R.id.rv_categories)
    RecyclerView recyclerViewCategories;


    private CategoryAdapter categoryAdapter;
    private CategoryContract.Presenter mPresenter;
    private OnCategoryFragmentInteractionListener mListener;

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
        Log.i(TAG, "onAttach: ");
        if (context instanceof OnCategoryFragmentInteractionListener) {
            mListener = (OnCategoryFragmentInteractionListener) context;
        } else {
            throw new ClassCastException("Not Instance OF OnCategoryFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: ");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i(TAG, "onCreateView: ");
        return inflater.inflate(R.layout.fragment_category, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "onViewCreated: ");
        ButterKnife.bind(this, view);
        categoryAdapter = new CategoryAdapter(this);
        mPresenter = new CategoryPresenter();
        mPresenter.attachView(this);
        setupRecyclerView();

    }

    @Override
    public void showLoadedData(List list) {
        categoryAdapter.setCategoryList(list);
    }

    @Override
    public void showDataNotAvailableToast() {
        Toast.makeText(getContext(), "Data is not available now...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNetworkNotAvailableToast() {
        Toast.makeText(getContext(), "Check you network connection...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "onActivityCreated: ");
        mPresenter.fetchCategoriesFromRemoteDataSource();
    }

    private void setupRecyclerView() {
        recyclerViewCategories.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewCategories.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL));
        recyclerViewCategories.setAdapter(categoryAdapter);
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy: ");
        mPresenter.detachView(this);
        super.onDestroy();
    }

    @Override
    public void onItemClicked(Category category) {
        mListener.onClick(category);
    }


    public interface OnCategoryFragmentInteractionListener {
        void onClick(Category item);
    }

}
