package com.yaratech.yaratube.ui.home;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yaratech.yaratube.R;
import com.yaratech.yaratube.data.model.Product;
import com.yaratech.yaratube.utils.AppConstants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeItemsAdapter extends RecyclerView.Adapter<HomeItemsAdapter.HomeViewHolder> {


    private List<Product> products;
    private OnHomeItemsClickListener mListener;

    public HomeItemsAdapter(OnHomeItemsClickListener mListener) {
        this.mListener = mListener;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.
                from(parent.getContext()).inflate(R.layout.home_item, parent, false);
        return new HomeViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        Product product = products.get(position);
        holder.onBind(product);
    }


    @Override
    public int getItemCount() {
        if (products == null) {
            return 0;
        }
        return products.size();
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_category_card_product)
        ImageView imageView;

        @BindView(R.id.tv_category_card_title)
        TextView title;

        @BindView(R.id.tv_category_card_description)
        TextView description;

        public HomeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(
                    v -> {
                        mListener.onProductItemClicked(products.get(getAdapterPosition()));
                    }
            );
        }

        public void onBind(Product product) {
            title.setText(product.getName());
            description.setText(product.getShortDescription());
            String url = AppConstants.BASE_URL + product.getAvatar().getXxxdpi();
            Glide.with(itemView.getContext()).load(url).into(imageView);
        }
    }

    public interface OnHomeItemsClickListener {
        public void onProductItemClicked(Product item);
    }
}
