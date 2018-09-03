package com.yaratech.yaratube.ui.gridcategory;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yaratech.yaratube.R;
import com.yaratech.yaratube.data.model.other.Product;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GridCategoryAdapter extends RecyclerView.Adapter<GridCategoryAdapter.ViewHolder> {

    List<Product> productList;

    private static final String TAG = "GridCategoryAdapter";
    private OnCategoryGridClickListener mListener;

    public GridCategoryAdapter(OnCategoryGridClickListener mListener) {
        this.mListener = mListener;
        productList = new ArrayList<>();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setProductList(List<Product> newProductList) {
        ArrayList newProducts = new ArrayList();
        newProducts.addAll(productList);
        newProducts.addAll(newProductList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new ProductDiffUtilCallback(productList, newProducts), true);
        this.productList = newProducts;
        diffResult.dispatchUpdatesTo(this);
//        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.product_item, parent, false);
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
            return 0;
        }
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.iv_product_item)
        ImageView imageView;


        @BindView(R.id.tv_product_item_title)
        TextView title;

        @BindView(R.id.tv_product_item_description)
        TextView description;


        public ViewHolder(View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> mListener.onClickedItem(productList.get(getAdapterPosition())));
        }


        public void onBind(Product product) {
            Glide.with((GridCategoryFragment) mListener).load(product.getFeatureAvatar().getXxxDpiUrl()).into(imageView);
            title.setText(product.getName());
            description.setText(product.getShortDescription());
        }
    }

    public interface OnCategoryGridClickListener {
        void onClickedItem(Product item);
    }
}

