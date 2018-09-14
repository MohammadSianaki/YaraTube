package com.yaratech.yaratube.ui.productdetails;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.yaratech.yaratube.R;
import com.yaratech.yaratube.data.AppDataManager;
import com.yaratech.yaratube.data.model.other.Comment;
import com.yaratech.yaratube.data.model.other.Product;
import com.yaratech.yaratube.ui.EndlessRecyclerViewScrollListener;
import com.yaratech.yaratube.ui.player.PlayerActivity;
import com.yaratech.yaratube.utils.SnackbarUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDetailsFragment extends Fragment implements DetailsContract.View, SwipeRefreshLayout.OnRefreshListener {
    private final static String KEY_ID = "KEY_ID";
    private static final String TAG = "ProductDetailsFragment";
    private static final String KEY_PRODUCT = "KEY_PRODUCT";
    private static final String KEY_PRODUCT_FILE = "KEY_PRODUCT_FILE";
    private static final int LIMIT = 3;
    private static final int BASE_OFFSET = 0;
    //------------------------------------------------------------------------------------------------------
    private LinearLayoutManager linearLayoutManager;
    private DetailsContract.Presenter mPresenter;
    private Unbinder mUnBinder;
    private CommentAdapter commentAdapter;
    private AppDataManager appDataManager;
    private OnProductDetailsInteraction mListener;

    @BindView(R.id.iv_product_details_media)
    ImageView productDetailsMedia;

    @BindView(R.id.tv_product_details_title)
    TextView productDetailsTitle;

    @BindView(R.id.pb_product_detail)
    ProgressBar progressBar;

    @BindView(R.id.pb_comment_loading)
    ProgressBar commentProgressBar;

    @BindView(R.id.tv_product_details_description)
    TextView productDetailsDescription;

    @BindView(R.id.rv_comments_of_products)
    RecyclerView listOfComments;

    @BindView(R.id.btn_submit_comment)
    Button submitComment;

    @BindView(R.id.product_details_fragment_play_image_button)
    ImageButton playVideo;

    @BindView(R.id.details_toolbar)
    Toolbar toolbar;

    @BindView(R.id.product_details_fragment_coordinator)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.product_details_fragment_swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    //------------------------------------------------------------------------------------------------------

    public ProductDetailsFragment() {
        // Required empty public constructor
    }

    public static ProductDetailsFragment newInstance(Product product) {
        Bundle args = new Bundle();
        args.putInt(KEY_ID, product.getId());
        args.putParcelable(KEY_PRODUCT, product);
        ProductDetailsFragment fragment = new ProductDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnProductDetailsInteraction) {
            mListener = (OnProductDetailsInteraction) context;
        } else {
            throw new ClassCastException("mListener not instance of OnProductDetailsInteraction");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: ProductDetailsFragment");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_details, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnBinder = ButterKnife.bind(this, view);
        setupToolbar();
        Log.i(TAG, "onViewCreated: ProductDetailsFragment");
        mPresenter = new ProductDetailsPresenter(appDataManager);
        mPresenter.attachView(this);
        commentAdapter = new CommentAdapter();
        setRecyclerView();
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    private void setupToolbar() {
        toolbar.setTitle(R.string.details_fragment_toolbar_title);
    }

    private void setRecyclerView() {
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        listOfComments.setLayoutManager(linearLayoutManager);
        listOfComments.setAdapter(commentAdapter);
        listOfComments.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadNextDataFromApi(view.getAdapter().getItemCount());
            }
        });
    }

    private void loadNextDataFromApi(int offset) {
        mPresenter.fetchProductComments(getArguments().getInt(KEY_ID), offset, LIMIT);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "onActivityCreated: ProductDetailsFragment");
        mPresenter.fetchProductDetails(getArguments().getInt(KEY_ID));
        mPresenter.fetchProductComments(getArguments().getInt(KEY_ID), BASE_OFFSET, LIMIT);
        playVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.isUserLoginToPlay();
            }
        });

        productDetailsMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.isUserLoginToPlay();
            }
        });
    }


    @Override
    public void showLoadedData(Product product) {
        swipeRefreshLayout.setRefreshing(false);
        getArguments().putString(KEY_PRODUCT_FILE, product.getFiles().get(0).getFile());
        Glide.with(this).load(product.getFeatureAvatar().getXxxDpiUrl()).into(productDetailsMedia);
        productDetailsTitle.setText(product.getName());
        productDetailsDescription.setText(product.getDescription());
    }

    @Override
    public void showLoadedComments(List<Comment> comments) {
        commentAdapter.setCommentList(comments);
    }

    @Override
    public void showCommentLoading() {
        if (!swipeRefreshLayout.isRefreshing()) {
            commentProgressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideCommentLoading() {
        commentProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showDataNotAvailableToast() {
        SnackbarUtils
                .showServerConnectionFailureSnackbar(coordinatorLayout, new SnackbarUtils.SnackbarCallback() {
                    @Override
                    public void onRetryAgainPressed() {
                        mPresenter.fetchProductDetails(getArguments().getInt(KEY_ID));
                        mPresenter.fetchProductComments(getArguments().getInt(KEY_ID), BASE_OFFSET, LIMIT);
                    }
                });
    }

    @Override
    public void showNetworkNotAvailableToast() {
        Toast.makeText(getContext(), "Check you network connection...", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void showProgressBarLoading() {
        if (!swipeRefreshLayout.isRefreshing())
            progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void finishProgressBarLoading() {
        progressBar.setVisibility(View.GONE);
    }


    @Override
    public void onDestroyView() {
        mPresenter.unSubscribe();
        mUnBinder.unbind();
        mPresenter.detachView();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void setAppDataManager(AppDataManager appDataManager) {
        this.appDataManager = appDataManager;
    }

    @Override
    public void showCommentDialog(String token) {
        mListener.showCommentDialog(token, getArguments().getInt(KEY_ID));
    }

    @Override
    public void showLoginDialog() {
        mListener.showLoginDialogToInsertComment();
    }

    @OnClick(R.id.btn_submit_comment)
    void onSubmitCommentClicked() {
        mPresenter.isUserLogin();
    }

    @Override
    public void goToPlayerActivity() {
        Intent intent = new Intent(getActivity(), PlayerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(KEY_PRODUCT_FILE, getArguments().getString(KEY_PRODUCT_FILE));
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        //check if previous loading is finished
        if (progressBar.getVisibility() != View.VISIBLE && commentProgressBar.getVisibility() != View.VISIBLE) {
            swipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    swipeRefreshLayout.setRefreshing(true);
                    mPresenter.fetchProductDetails(getArguments().getInt(KEY_ID));
                    mPresenter.fetchProductComments(getArguments().getInt(KEY_ID), BASE_OFFSET, LIMIT);
                }
            });
        }
    }

    public interface OnProductDetailsInteraction {
        void showLoginDialogToInsertComment();

        void showCommentDialog(String token, int productId);
    }

}
