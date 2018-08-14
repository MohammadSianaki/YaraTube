package com.yaratech.yaratube.ui.productdetails;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.yaratech.yaratube.R;
import com.yaratech.yaratube.data.model.Product;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment implements DetailsContract.View {
    private final static String KEY_ID = "KEY_ID";
    private static final String TAG = "DetailsFragment";
    //------------------------------------------------------------------------------------------------------

    private DetailsContract.Presenter mPresenter;

    @BindView(R.id.iv_product_details_media)
    ImageView productDetailsMedia;

    @BindView(R.id.tv_product_details_title)
    TextView productDetailsTitle;

    @BindView(R.id.pb_product_detail)
    ProgressBar progressBar;

    @BindView(R.id.tv_product_details_description)
    TextView productDetailsDescription;

    //------------------------------------------------------------------------------------------------------

    public DetailsFragment() {
        // Required empty public constructor
    }


    public static DetailsFragment newInstance(int productId) {

        Bundle args = new Bundle();
        args.putInt(KEY_ID, productId);
        DetailsFragment fragment = new DetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: DetailsFragment");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false);
    }


    @Override

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        Log.i(TAG, "onViewCreated: DetailsFragment");
        mPresenter = new DetailsPresenter(getActivity().getApplicationContext());
        mPresenter.attachView(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "onActivityCreated: DetailsFragment");
        mPresenter.fetchProductDetails(getArguments().getInt(KEY_ID));

    }


    @Override
    public void showLoadedData(Product product) {
        Glide.with(this).load(product.getAvatar().getXxxDpiUrl()).into(productDetailsMedia);
        productDetailsTitle.setText(product.getName());
        productDetailsDescription.setText(product.getShortDescription());
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
    public void onDestroyView() {
        mPresenter.cancelProductDetailsApiRequest();
        mPresenter.detachView(this);
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
