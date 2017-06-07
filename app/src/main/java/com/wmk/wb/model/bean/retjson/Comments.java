package com.wmk.wb.model.bean.retjson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by wmk on 2017/4/12.
 */

public class Comments {

    @SerializedName("created_at")
    public String created_at;

    @SerializedName("text")
    public String text;

    @SerializedName("user")
    public User user;

    @SerializedName("id")
    public long id;

    @SerializedName("source")
    public String source;

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
