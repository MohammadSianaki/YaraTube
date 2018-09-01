package com.yaratech.yaratube.ui.productdetails;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yaratech.yaratube.R;
import com.yaratech.yaratube.data.model.Comment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private List<Comment> commentList;

    public CommentAdapter() {
        this.commentList = new ArrayList<>();
    }

    public void setCommentList(List<Comment> commentList) {
        ArrayList<Comment> newCommentList = new ArrayList<>();
        newCommentList.addAll(this.commentList);
        newCommentList.addAll(commentList);
        DiffUtil.DiffResult diffResult = DiffUtil.
                calculateDiff(new CommentDiffUtilCallback(this.commentList, newCommentList));
        this.commentList = newCommentList;
        diffResult.dispatchUpdatesTo(this);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comment comment = commentList.get(position);
        holder.onBind(comment);
    }

    @Override
    public int getItemCount() {
        if (commentList == null) {
            return 0;
        }
        return commentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_product_detail_comment)
        TextView commentTextView;

        public ViewHolder(View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void onBind(Comment comment) {
            commentTextView.setText(comment.getCommentText());
        }

    }

}
