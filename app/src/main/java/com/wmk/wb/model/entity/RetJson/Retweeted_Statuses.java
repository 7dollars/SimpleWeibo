package com.wmk.wb.model.entity.RetJson;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wmk on 2017/4/6.
 */

public class Retweeted_Statuses {
    @SerializedName("text")
    public String text;

    @SerializedName("user")
    public User user;

    @SerializedName("id")
    public long id;

    @SerializedName("created_at")
    public String created_at;

    @SerializedName("pic_urls")
    public List<Pic_Urls> pic_urls;

    @SerializedName("pic_ids")
    public List<String> pic_ids;

    @SerializedName("reposts_count")//转发数
    public int reposts_count;

    @SerializedName("comments_count")//评论数
    public int comments_count;

    @SerializedName("source")
    public String source;

    public List<String> getPic_urls() {
        List<String> url=new ArrayList<>();
        for(int i=0;i<pic_urls.size();i++)
        {
            url.add(pic_urls.get(i).thumbnail_pic);
        }
        return url;
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

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setPic_urls(List<Pic_Urls> pic_urls) {
        this.pic_urls = pic_urls;
    }

    public List<String> getPic_ids() {
        return pic_ids;
    }

    public void setPic_ids(List<String> pic_ids) {
        this.pic_ids = pic_ids;
    }

    public int getReposts_count() {
        return reposts_count;
    }

    public void setReposts_count(int reposts_count) {
        this.reposts_count = reposts_count;
    }

    public int getComments_count() {
        return comments_count;
    }

    public void setComments_count(int comments_count) {
        this.comments_count = comments_count;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
