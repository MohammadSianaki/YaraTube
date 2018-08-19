
package com.yaratech.yaratube.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommentsSummery {

    @SerializedName("score")
    @Expose
    private int score;
    @SerializedName("count")
    @Expose
    private int count;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
