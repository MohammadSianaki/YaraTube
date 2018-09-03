package com.yaratech.yaratube.ui.productdetails;

import android.support.v7.util.DiffUtil;

import com.yaratech.yaratube.data.model.other.Comment;

import java.util.List;

public class CommentDiffUtilCallback extends DiffUtil.Callback {

    private List<Comment> oldComments;
    private List<Comment> newComments;

    public CommentDiffUtilCallback(List<Comment> oldComments, List<Comment> newComments) {
        this.oldComments = oldComments;
        this.newComments = newComments;
    }

    @Override
    public int getOldListSize() {
        return oldComments.size();
    }

    @Override
    public int getNewListSize() {
        return newComments.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldComments.get(oldItemPosition).getId() ==
                newComments.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldComments.get(oldItemPosition).equals(newComments.get(newItemPosition));
    }
}
