package com.yaratech.yaratube.ui.home.header;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yaratech.yaratube.R;
import com.yaratech.yaratube.data.model.other.Product;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class HeaderItemsFragment extends Fragment {

    private final static String KEY_HEADER_ITEM = "KEY_HEADER_ITEM";
    private Product headerItem;
    private Unbinder unbinder;
    private OnHeaderItemsInteractionListener mListener;

    @BindView(R.id.iv_header_pager)
    ImageView imageView;

    public HeaderItemsFragment() {
        // Required empty public constructor
    }

    public static HeaderItemsFragment newInstance(Product headerItem) {

        Bundle args = new Bundle();
        args.putParcelable(KEY_HEADER_ITEM, headerItem);
        HeaderItemsFragment fragment = new HeaderItemsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnHeaderItemsInteractionListener) {
            mListener = (OnHeaderItemsInteractionListener) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            headerItem = getArguments().getParcelable(KEY_HEADER_ITEM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_header_items, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        view.setOnClickListener(v -> mListener.showRequestedHeaderItemDetails(headerItem));
        Glide.with(this).load(headerItem.getFeatureAvatar().getXxxDpiUrl()).into(imageView);
    }


    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    public interface OnHeaderItemsInteractionListener {
         void showRequestedHeaderItemDetails(Product item);
    }
}
