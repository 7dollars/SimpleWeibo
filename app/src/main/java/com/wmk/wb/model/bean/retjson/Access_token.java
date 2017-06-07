package com.wmk.wb.model.bean.retjson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by wmk on 2017/4/3.
 */

public class Access_token {
    @SerializedName("access_token")
    public String access_token;

    @SerializedName("uid")
    public String uid;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
