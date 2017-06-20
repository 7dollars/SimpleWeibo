package com.wmk.wb.model.bean.retjson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by wmk on 2017/6/20.
 */

public class Status {
    @SerializedName("status")
    public Statuses statuses;

    public Statuses getStatuses() {
        return statuses;
    }
}
