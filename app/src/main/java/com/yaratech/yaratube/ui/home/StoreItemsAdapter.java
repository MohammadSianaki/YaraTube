package com.yaratech.yaratube.ui.home;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yaratech.yaratube.R;
import com.yaratech.yaratube.data.model.HeaderItem;
import com.yaratech.yaratube.data.model.HomeItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StoreItemsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<HeaderItem> headerItems;
    private List<HomeItem> homeItems;
    private HomeItemsAdapter.OnHomeItemsClickListener mListener;


    private static final int HEADER_VIEW_TYPE = 1;
    private static final int HOME_VIEW_TYPE = 2;

    public StoreItemsAdapter(HomeItemsAdapter.OnHomeItemsClickListener mListener) {
        this.mListener = mListener;
    }


    public void setHeaderItems(List<HeaderItem> headerItems) {
        this.headerItems = headerItems;
        notifyDataSetChanged();
    }

    public void setHomeItems(List<HomeItem> homeItems) {
        this.homeItems = homeItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder holder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case HEADER_VIEW_TYPE:
                view = inflater.inflate(R.layout.header_layout, parent, false);
                holder = new HeaderViewHolder(view);
                break;
            case HOME_VIEW_TYPE:
                view = inflater.inflate(R.layout.home_layout, parent, false);
                holder = new HomeViewHolder(view);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder viewHolder = (HeaderViewHolder) holder;
            viewHolder.onBindHeaderView();
        } else if (holder instanceof HomeViewHolder) {
            HomeViewHolder viewHolder = (HomeViewHolder) holder;
            viewHolder.onBindHomeView(homeItems.get(position - 1));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADER_VIEW_TYPE;
        } else {
            return HOME_VIEW_TYPE;
        }
    }

    @Override
    public int getItemCount() {
        if (homeItems == null) {
            return 0;
        }
        return homeItems.size() + 1;
    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rv_header_items)
        RecyclerView headerRecyclerView;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        public void onBindHeaderView() {
            HeaderItemsAdapter headerItemsAdapter = new HeaderItemsAdapter();
            headerRecyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false));
            headerRecyclerView.setAdapter(headerItemsAdapter);
            headerItemsAdapter.setHeaderItems(headerItems);
        }
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.rv_home_items)
        RecyclerView homeRecyclerView;

        @BindView(R.id.tv_home_items_name)
        TextView homeItemsName;

        public HomeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        public void onBindHomeView(HomeItem homeItem) {
            HomeItemsAdapter homeItemsAdapter = new HomeItemsAdapter(mListener);
            homeRecyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false));
            homeRecyclerView.setAdapter(homeItemsAdapter);
            homeItemsAdapter.setProducts(homeItem.getProducts());
            homeItemsName.setText(homeItem.getTitle());

        }
    }


}

