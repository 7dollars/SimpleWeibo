package com.wmk.wb.model.entity;

import android.graphics.Bitmap;
import android.support.v7.widget.CardView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wmk on 2017/4/4.
 */
public class FinalViewData {
    public String text;
    public String headurl;
    public String ret_headurl;
    public String name;
    public String ret_name;
    public String ret_text;
    public List<String> picurls;
    public List<String> ret_picurls;
    public int reposts_count;
    public int comments_count;
    public int reposts_count_ret;
    public int comments_count_ret;
    public long id;
    public long ret_id;
    public String time;
    public String ret_time;

    public FinalViewData() {
        picurls=new ArrayList<>();
        ret_picurls=new ArrayList<>();
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public String getHeadurl() {
        return headurl;
    }

    public void setHeadurl(String headurl) {
        this.headurl = headurl;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRet_name() {
        return ret_name;
    }

    public void setRet_name(String ret_name) {
        this.ret_name = ret_name;
    }

    public String getRet_text() {
        return ret_text;
    }

    public void setRet_text(String ret_text) {
        this.ret_text = ret_text;
    }

    public List<String> getPicurls() {
        return picurls;
    }

    public void setPicurls(List<String> picurls) {
        this.picurls = picurls;
    }

    public List<String> getRet_picurls() {
        return ret_picurls;
    }

    public void setRet_picurls(List<String> ret_picurls) {
        this.ret_picurls = ret_picurls;
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

    public int getReposts_count_ret() {
        return reposts_count_ret;
    }

    public void setReposts_count_ret(int reposts_count_ret) {
        this.reposts_count_ret = reposts_count_ret;
    }

    public int getComments_count_ret() {
        return comments_count_ret;
    }

    public void setComments_count_ret(int comments_count_ret) {
        this.comments_count_ret = comments_count_ret;
    }

    public String getRet_time() {
        return ret_time;
    }

    public void setRet_time(String ret_time) {
        this.ret_time = ret_time;
    }

    public String getRet_headurl() {
        return ret_headurl;
    }

    public void setRet_headurl(String ret_headurl) {
        this.ret_headurl = ret_headurl;
    }

    public long getRet_id() {
        return ret_id;
    }

    public void setRet_id(long ret_id) {
        this.ret_id = ret_id;
    }
}
