package com.wmk.wb.model.entity.RetJson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by wmk on 2017/4/12.
 */

public class CommentsData {
    @SerializedName("comments")
    public List<Comments> comments;

    public List<Comments> getComments() {
        return comments;
    }

    public void setComments(List<Comments> comments) {
        this.comments = comments;
    }
}
