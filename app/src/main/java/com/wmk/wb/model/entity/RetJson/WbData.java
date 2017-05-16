package com.wmk.wb.model.entity.retjson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by wmk on 2017/4/3.
 */
//总的返回数据
public class WbData {
    @SerializedName("statuses")
    public List<Statuses> statuses;

    public List<Statuses> getStatuses() {
        return statuses;
    }
}
