package com.yaratech.yaratube.ui.category;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yaratech.yaratube.R;
import com.yaratech.yaratube.data.model.Category;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {


    private List<Category> categoryList;
    private OnRecyclerViewInteractionListener listener;

    public CategoryAdapter(OnRecyclerViewInteractionListener listener) {
        this.listener = listener;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);

        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final Category currentCategory = categoryList.get(position);
        holder.onBind(currentCategory);

    }

    @Override
    public int getItemCount() {
        if (categoryList == null) {
            return 0;
        }
        return categoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_category_item)
        ImageView imageView;

        @BindView(R.id.tv_category_item)
        TextView textView;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void onBind(Category category) {
            textView.setText(category.getTitle());

            if (category.getAvatar() != null) {

                Glide
                        .with(itemView.getContext())
                        .load(category.getAvatarUrl())
                        .into(imageView);
            }

            itemView.setOnClickListener(view -> {
                listener.onItemClicked(category);
            });
        }
    }


    public interface OnRecyclerViewInteractionListener {

        void onItemClicked(Category category);
    }
}


