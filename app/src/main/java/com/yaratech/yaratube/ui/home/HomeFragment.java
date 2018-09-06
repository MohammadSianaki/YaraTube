package com.yaratech.yaratube.ui.home;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.yaratech.yaratube.R;
import com.yaratech.yaratube.data.AppDataManager;
import com.yaratech.yaratube.data.model.api.StoreResponse;
import com.yaratech.yaratube.data.model.other.Product;
import com.yaratech.yaratube.ui.BaseActivity;
import com.yaratech.yaratube.ui.OnRequestedProductItemClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements
        HomeContract.View,
        HomeItemsAdapter.OnHomeItemsClickListener {

    //----------------------------------------------------------------------------------------
    private static final String TAG = "HomeFragment";
    private HomeContract.Presenter mPresenter;
    private StoreItemsAdapter mStoreItemsAdapter;
    private OnRequestedProductItemClickListener mListener;
    private Unbinder mUnBinder;
    private AppDataManager appDataManager;


    @BindView(R.id.pb_store_items_loading)
    ProgressBar progressBar;

    @BindView(R.id.rv_store_items)
    RecyclerView recyclerViewStoreItems;

    //----------------------------------------------------------------------------------------

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onAttach(Context context) {
        Log.i(TAG, "onAttach: HomeFragment");
        if (context instanceof BaseActivity) {
            mListener = (OnRequestedProductItemClickListener) context;
        }
        super.onAttach(context);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: HomeFragment");
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: HomeFragment");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onViewCreated: HomeFragment");
        super.onViewCreated(view, savedInstanceState);
        mUnBinder = ButterKnife.bind(this, view);
        mStoreItemsAdapter = new StoreItemsAdapter(this, getFragmentManager());
        mPresenter = new HomePresenter(appDataManager);
        mPresenter.attachView(this);
        setupRecyclerView();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onActivityCreated: HomeFragment");
        super.onActivityCreated(savedInstanceState);
        mPresenter.fetchStoreItems();
    }

    @Override
    public void onStart() {
        Log.i(TAG, "onStart: HomeFragment");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.i(TAG, "onResume: HomeFragment");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.i(TAG, "onPause: HomeFragment");
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        Log.i(TAG, "onSaveInstanceState: HomeFragment");
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStop() {
        Log.i(TAG, "onStop: HomeFragment");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.i(TAG, "onDestroyView: HomeFragment");
        mUnBinder.unbind();
        mPresenter.unSubscribe();
        mPresenter.detachView();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy: HomeFragment");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.i(TAG, "onDetach: HomeFragment");
        mListener = null;
        super.onDetach();
    }


    private void setupRecyclerView() {
        recyclerViewStoreItems.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerViewStoreItems.setLayoutAnimation(AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_animation_slide_right));
        recyclerViewStoreItems.setAdapter(mStoreItemsAdapter);
    }

    @Override
    public void showLoadedData(StoreResponse storeResponse) {
        mStoreItemsAdapter.setHeaderItems(storeResponse.getHeaderItems());
        mStoreItemsAdapter.setHomeItems(storeResponse.getHomeItems());
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
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onProductItemClicked(Product item) {
        mListener.showProductDetailsOfRequestedProductItem(item);
    }

    public void setAppDataManager(AppDataManager appDataManager) {
        this.appDataManager = appDataManager;
    }

    public void runLayoutAnimation() {
        final Context context = recyclerViewStoreItems.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_slide_right);

        recyclerViewStoreItems.setLayoutAnimation(controller);
        recyclerViewStoreItems.getAdapter().notifyDataSetChanged();
        recyclerViewStoreItems.scheduleLayoutAnimation();
    }
}