package com.yaratech.yaratube.ui.gridcategory;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

public class GridCategoryAdapter extends RecyclerView.Adapter<GridCategoryAdapter.ViewHolder> {

    List<Product> productList;

    private static final String TAG = "GridCategoryAdapter";
    private OnCategoryGridClickListener mListener;

    public GridCategoryAdapter(OnCategoryGridClickListener mListener) {
        this.mListener = mListener;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.category_prodcut_item, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product currentProduct = productList.get(position);
        holder.onBind(currentProduct);
    }

    @Override
    public int getItemCount() {
        if (productList == null) {
            Log.i(TAG, "getItemCount: <<<< 0 >>>>");
            return 0;
        }
        if (productList != null) {
            Log.i(TAG, "getItemCount: <<<< " + productList.size() + " >>>>");
        }
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.iv_category_card_product)
        ImageView imageView;


        @BindView(R.id.tv_category_card_title)
        TextView title;

        @BindView(R.id.tv_category_card_description)
        TextView description;


        public ViewHolder(View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> mListener.onClickedItem(productList.get(getAdapterPosition())));
        }


        public void onBind(Product product) {
            String url = AppConstants.BASE_URL + product.getAvatar().getXxxdpi();
            Glide.with(itemView.getContext()).load(url).into(imageView);
            title.setText(product.getName());
            description.setText(product.getShortDescription());
        }
    }

    public interface OnCategoryGridClickListener {
        public void onClickedItem(Product item);
    }
}

