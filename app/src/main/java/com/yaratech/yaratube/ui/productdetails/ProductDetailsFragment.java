package com.yaratech.yaratube.ui.productdetails;


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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.yaratech.yaratube.R;
import com.yaratech.yaratube.data.model.Comment;
import com.yaratech.yaratube.data.model.ProductDetails;
import com.yaratech.yaratube.data.source.StoreRepository;
import com.yaratech.yaratube.data.source.UserRepository;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDetailsFragment extends Fragment implements DetailsContract.View {
    private final static String KEY_ID = "KEY_ID";
    private static final String TAG = "ProductDetailsFragment";
    //------------------------------------------------------------------------------------------------------

    private DetailsContract.Presenter mPresenter;
    private Unbinder mUnBinder;
    private CommentAdapter commentAdapter;
    private StoreRepository storeRepository;
    private UserRepository userRepository;
    private CompositeDisposable compositeDisposable;
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

    //------------------------------------------------------------------------------------------------------

    public ProductDetailsFragment() {
        // Required empty public constructor
    }

    public static ProductDetailsFragment newInstance(int productId) {

        Bundle args = new Bundle();
        args.putInt(KEY_ID, productId);
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
        Log.i(TAG, "onViewCreated: ProductDetailsFragment");
        mPresenter = new DetailsPresenter(storeRepository, userRepository, compositeDisposable);
        mPresenter.attachView(this);
        commentAdapter = new CommentAdapter();
        setRecyclerView();
    }

    private void setRecyclerView() {
        listOfComments.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        listOfComments.setAdapter(commentAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "onActivityCreated: ProductDetailsFragment");
        mPresenter.fetchProductDetails(getArguments().getInt(KEY_ID));
        mPresenter.fetchProductComments(getArguments().getInt(KEY_ID));
    }


    @Override
    public void showLoadedData(ProductDetails productDetails) {
        Glide.with(this).load(productDetails.getFeatureAvatar().getXxxDpiUrl()).into(productDetailsMedia);
        productDetailsTitle.setText(productDetails.getName());
        productDetailsDescription.setText(productDetails.getDescription());
    }

    @Override
    public void showLoadedComments(List<Comment> comments) {
        commentAdapter.setCommentList(comments);
    }

    @Override
    public void showCommentLoading() {
        commentProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideCommentLoading() {
        commentProgressBar.setVisibility(View.GONE);
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
    public void onDestroyView() {
        mUnBinder.unbind();
        mPresenter.cancelProductCommentApiRequest();
        mPresenter.cancelProductDetailsApiRequest();
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

    public void setStoreRepository(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void setCompositeDisposable(CompositeDisposable compositeDisposable) {
        this.compositeDisposable = compositeDisposable;
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

    public interface OnProductDetailsInteraction {
        void showLoginDialogToInsertComment();

        void showCommentDialog(String token, int productId);
    }

}
