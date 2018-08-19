package com.yaratech.yaratube.ui.home.header;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yaratech.yaratube.R;
import com.yaratech.yaratube.data.model.HeaderItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HeaderItemsAdapter extends RecyclerView.Adapter<HeaderItemsAdapter.HeaderViewHolder> {

    private List<HeaderItem> headerItems;

    public void setHeaderItems(List<HeaderItem> headerItems) {
        this.headerItems = headerItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HeaderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.
                from(parent.getContext()).inflate(R.layout.header_item, parent, false);
        return new HeaderViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull HeaderViewHolder holder, int position) {
        HeaderItem headerItem = headerItems.get(position);
        holder.onBind(headerItem);
    }

    @Override
    public int getItemCount() {
        if (headerItems == null) {
            return 0;
        }
        return headerItems.size();
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.iv_header_card_product)
        ImageView imageView;


        public HeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void onBind(HeaderItem headerItem) {
            Glide.with(itemView.getContext()).load(headerItem.getFeatureAvatar().getXxxDpiUrl()).into(imageView);
        }
    }
}
