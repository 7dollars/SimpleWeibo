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

    @SerializedName("gender")
    public String gender;

    @SerializedName("followers_count")
    public String followers_count;

    @SerializedName("friends_count")
    public String friends_count;

    @SerializedName("statuses_count")
    public String statuses_count;

    @SerializedName("following")
    public boolean following;

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

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public User setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getProfile_image_url() {
        return profile_image_url;
    }

    public void setProfile_image_url(String profile_image_url) {
        this.profile_image_url = profile_image_url;
    }

    public String getGender() {
        return gender;
    }

    public String getFollowers_count() {
        return followers_count;
    }

    public String getFriends_count() {
        return friends_count;
    }

    public String getStatuses_count() {
        return statuses_count;
    }

    public boolean isfollowing() {
        return following;
    }
}
