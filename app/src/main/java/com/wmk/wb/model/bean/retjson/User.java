package com.wmk.wb.model.bean.retjson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by wmk on 2017/4/5.
 */

public class User {
    @SerializedName("avatar_large")
    public String avatar_large;

    @SerializedName("name")
    public String name;

    public String getAvatar_large() {
        return avatar_large;
    }


    public String getName()
    {
        if(name!=null)
            return name;
        else
            return "未获取";
    }

    public void setName(String name) {
        this.name = name;
    }
}
