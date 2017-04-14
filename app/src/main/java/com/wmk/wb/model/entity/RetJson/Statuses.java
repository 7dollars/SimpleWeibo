package com.wmk.wb.model.entity.RetJson;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wmk on 2017/4/4.
 */
//返回数据分包
public class Statuses {
    @SerializedName("text")
    public String text;

    @SerializedName("user")
    public User user;

    @SerializedName("id")
    public long id;

    @SerializedName("pic_ids")
    public List<String> pic_ids;

    @SerializedName("created_at")
    public String created_at;

    @SerializedName("retweeted_status")
    public Retweeted_Statuses retweeted_statuses;

    @SerializedName("pic_urls")
    public List<Pic_Urls> pic_urls;

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


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getText() {
        return text;
    }

    public User getUser() {
        return user;
    }

    public List<String> getPic_ids() {
        return pic_ids;
    }

    public String getCreated_at() {
        return created_at;
    }

    public Retweeted_Statuses getRetweeted_statuses() {
        return retweeted_statuses;
    }

    public int getReposts_count() {
        return reposts_count;
    }

    public int getComments_count() {
        return comments_count;
    }

    public String getSource() {
        return source;
    }
}
