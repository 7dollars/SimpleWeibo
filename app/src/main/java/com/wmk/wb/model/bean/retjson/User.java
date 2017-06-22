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

    @SerializedName("description")
    public String description;

    @SerializedName("profile_image_url")
    public String profile_image_url;

    public String getAvatar_large() {
        if(avatar_large!=null)
            return avatar_large;
        else
            return null;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProfile_image_url() {
        return profile_image_url;
    }

    public void setProfile_image_url(String profile_image_url) {
        this.profile_image_url = profile_image_url;
    }
}
