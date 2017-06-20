package com.wmk.wb.model.bean.retjson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by wmk on 2017/4/3.
 */
//总的返回数据
public class WbData {
    @SerializedName("statuses")
    public List<Statuses> statuses;

    @SerializedName("comments")
    public List<Statuses> comments;//接受评论时使用

    @SerializedName("favorites")
    public List<Status> favorites;//接受评论时使用

    public List<Status> getFavorites() {
        return favorites;
    }

    public List<Statuses> getStatuses(int flag) {
        if(flag==1)
            return statuses;
        else
            return  comments;
    }
}
