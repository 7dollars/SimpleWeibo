package com.wmk.wb.model.bean.retjson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by wmk on 2017/6/20.
 */

public class favorites {
    @SerializedName("status")
    public List<Status> status;

    public List<Status> getStatus() {
        return status;
    }

    public void setStatus(List<Status> status) {
        this.status = status;
    }
}
