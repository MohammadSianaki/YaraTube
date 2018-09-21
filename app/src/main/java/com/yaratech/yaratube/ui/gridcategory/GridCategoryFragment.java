package com.yaratech.yaratube.ui.gridcategory;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.yaratech.yaratube.R;
import com.yaratech.yaratube.data.AppDataManager;
import com.yaratech.yaratube.data.model.other.Category;
import com.yaratech.yaratube.data.model.other.Product;
import com.yaratech.yaratube.ui.BaseActivity;
import com.yaratech.yaratube.ui.EndlessRecyclerViewScrollListener;
import com.yaratech.yaratube.ui.OnRequestedProductItemClickListener;
import com.yaratech.yaratube.utils.SnackbarUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class GridCategoryFragment extends Fragment implements GridCategoryContract.View, GridCategoryAdapter.OnCategoryGridClickListener, SwipeRefreshLayout.OnRefreshListener {
    private static final String KEY_CATEGORY = "KEY_CATEGORY";
    private static final String TAG = "GridCategoryFragment";
    private static final int SPAN_COUNT = 2;
    private static final int LIMIT = 10;
    private static final int BASE_OFFSET = 0;
    public static final String KEY_ID = "KEY_ID";
    //-------------------------------------------------------------------------------------------

    private GridCategoryContract.Presenter mPresenter;
    private GridCategoryAdapter gridCategoryAdapter;
    private GridLayoutManager gridLayoutManager;
    private OnRequestedProductItemClickListener onRequestedProductItemClickListener;
    private Unbinder mUnBinder;
    private AppDataManager appDataManager;

    @BindView(R.id.grid_catgory_fragment_coordinator)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.rv_products_of_category)
    RecyclerView recyclerViewOfProducts;

    @BindView(R.id.pb_loading_products_of_category)
    ProgressBar progressBar;

    @BindView(R.id.grid_catgory_fragment_swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.grid_category_toolbar)
    Toolbar toolbar;

    //-------------------------------------------------------------------------------------------
    public GridCategoryFragment() {
        // Required empty public constructor
    }

    public static GridCategoryFragment newInstance(Category category) {

        Bundle args = new Bundle();
        args.putParcelable(KEY_CATEGORY, category);
        args.putInt(KEY_ID, category.getId());
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
        mPresenter = new GridCategoryPresenter(appDataManager);
        mPresenter.attachView(this);
        setupToolbar();
        setupRecyclerView();
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    private void setupToolbar() {
        if (getArguments() != null) {
            Category category = getArguments().getParcelable(KEY_CATEGORY);
            if (category != null) {
                toolbar.setTitle(category.getTitle());
            }
        }
    }

    private void setupRecyclerView() {
        Log.d(TAG, "setupRecyclerView() called");
        gridLayoutManager = new GridLayoutManager(
                getContext(),
                SPAN_COUNT,
                LinearLayoutManager.VERTICAL,
                false);
        recyclerViewOfProducts.setLayoutManager(gridLayoutManager);
        recyclerViewOfProducts.setAdapter(gridCategoryAdapter);
        recyclerViewOfProducts.addOnScrollListener(new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                Log.d(TAG, "onLoadMore: <<<< debug paginate >>>>");
                loadNextDataFromApi(view.getAdapter().getItemCount());
            }
        });
    }

    @Override
    public void showLoadedData(List list) {
        swipeRefreshLayout.setRefreshing(false);
        gridCategoryAdapter.setProductList(list);
    }

    private void loadNextDataFromApi(int offset) {
        Log.d(TAG, " <<<< debug paginate >>>> loadNextDataFromApi() called with: offset = [" + offset + "]");
        if (getArguments() != null) {
            mPresenter.fetchProducts(getArguments().getInt(KEY_ID), offset, LIMIT);
        }
    }

    @Override
    public void showDataNotAvailableToast() {
        SnackbarUtils
                .showServerConnectionFailureSnackbar(coordinatorLayout, new SnackbarUtils.SnackbarCallback() {
                    @Override
                    public void onRetryAgainPressed() {
                        if (getArguments() != null) {
                            mPresenter.fetchProducts(getArguments().getInt(KEY_ID), BASE_OFFSET, LIMIT);
                        }

                    }
                });
    }

    @Override
    public void showNetworkNotAvailableToast() {
        Toast.makeText(getContext(), "Check you network connection...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressBarLoading() {
        if (!swipeRefreshLayout.isRefreshing()) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void finishProgressBarLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            mPresenter.fetchProducts(getArguments().getInt(KEY_ID), BASE_OFFSET, LIMIT);
        }
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView: ");
        mUnBinder.unbind();
        mPresenter.unSubscribe();
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

    public void setAppDataManager(AppDataManager appDataManager) {
        this.appDataManager = appDataManager;
    }

    @Override
    public void onRefresh() {
        //check if previous loading is finished
        if (progressBar.getVisibility() != View.VISIBLE) {
            swipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    swipeRefreshLayout.setRefreshing(true);
                    if (getArguments() != null) {
                        mPresenter.fetchProducts(getArguments().getInt(KEY_ID), BASE_OFFSET, LIMIT);
                    }
                }
            });
        }
    }
}
