package com.yaratech.yaratube.ui.gridcategory;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.yaratech.yaratube.R;
import com.yaratech.yaratube.data.model.Product;
import com.yaratech.yaratube.data.source.StoreRepository;
import com.yaratech.yaratube.ui.BaseActivity;
import com.yaratech.yaratube.ui.OnRequestedProductItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class GridCategoryFragment extends Fragment implements GridCategoryContract.View, GridCategoryAdapter.OnCategoryGridClickListener {
    private static final String KEY_ID = "KEY_ID";
    private static final String TAG = "GridCategoryFragment";
    //-------------------------------------------------------------------------------------------

    private GridCategoryContract.Presenter mPresenter;
    private GridCategoryAdapter gridCategoryAdapter;
    private OnRequestedProductItemClickListener onRequestedProductItemClickListener;
    private Unbinder mUnBinder;
    private StoreRepository storeRepository;


    @BindView(R.id.rv_products_of_category)
    RecyclerView recyclerViewOfProducts;

    @BindView(R.id.pb_loading_products_of_category)
    ProgressBar progressBar;

    //-------------------------------------------------------------------------------------------
    public GridCategoryFragment() {
        // Required empty public constructor
    }

    public static GridCategoryFragment newInstance(int categoryId) {

        Bundle args = new Bundle();
        args.putInt(KEY_ID, categoryId);
        GridCategoryFragment fragment = new GridCategoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        Log.i(TAG, "onAttach: GridCategoryFragment");
        if (context instanceof BaseActivity) {
            onRequestedProductItemClickListener = (OnRequestedProductItemClickListener) context;
        }
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: GridCategoryFragment");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i(TAG, "onCreateView: GridCategoryFragment");
        return inflater.inflate(R.layout.fragment_grid_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnBinder = ButterKnife.bind(this, view);
        Log.i(TAG, "onViewCreated: GridCategoryFragment");
        gridCategoryAdapter = new GridCategoryAdapter(this);
        mPresenter = new GridCategoryPresenter(storeRepository);
        mPresenter.attachView(this);
        setupRecyclerView();

    }

    private void setupRecyclerView() {
        recyclerViewOfProducts.setLayoutManager(new GridLayoutManager(
                getContext(),
                2,
                LinearLayoutManager.VERTICAL,
                false));
        recyclerViewOfProducts.setAdapter(gridCategoryAdapter);
    }

    @Override
    public void showLoadedData(List list) {
        gridCategoryAdapter.setProductList(list);
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
    public void showProgressBarLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void finishProgressBarLoading() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.fetchProducts(getArguments().getInt(KEY_ID));
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView: ");
        mUnBinder.unbind();
        mPresenter.cancelProductApiRequest();
        mPresenter.detachView();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        onRequestedProductItemClickListener = null;
        super.onDetach();
    }

    @Override
    public void onClickedItem(Product item) {
        onRequestedProductItemClickListener.showProductDetailsOfRequestedProductItem(item);
    }

    public void setStoreRepository(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }
}
