package com.wmk.wb.model.bean;

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

    public FinalViewData setTime(String time) {
        this.time = time;
        return this;
    }


    public String getText() {
        return text;
    }

    public FinalViewData setText(String text) {
        this.text = text;
        return  this;
    }


    public String getHeadurl() {
        return headurl;
    }

    public FinalViewData setHeadurl(String headurl) {
        if(headurl!=null)
            this.headurl = headurl;
        return  this;
    }


    public String getName() {
        return name;
    }

    public FinalViewData setName(String name) {
        this.name = name;
        return  this;
    }

    public long getId() {
        return id;
    }

    public FinalViewData setId(long id) {
        this.id = id;
        return  this;
    }

    public String getRet_name() {
        return ret_name;
    }

    public FinalViewData setRet_name(String ret_name) {
        this.ret_name = ret_name;
        return  this;
    }

    public String getRet_text() {
        return ret_text;
    }

    public FinalViewData setRet_text(String ret_text) {
        this.ret_text = ret_text;
        return  this;
    }

    public List<String> getPicurls() {
        return picurls;
    }

    public FinalViewData setPicurls(List<String> picurls) {
        this.picurls = picurls;
        return  this;
    }

    public List<String> getRet_picurls() {
        return ret_picurls;
    }

    public FinalViewData setRet_picurls(List<String> ret_picurls) {
        this.ret_picurls = ret_picurls;
        return  this;
    }

    public int getReposts_count() {
        return reposts_count;
    }

    public FinalViewData setReposts_count(int reposts_count) {
        this.reposts_count = reposts_count;
        return  this;
    }

    public int getComments_count() {
        return comments_count;
    }

    public FinalViewData setComments_count(int comments_count) {
        this.comments_count = comments_count;
        return  this;
    }

    public int getReposts_count_ret() {
        return reposts_count_ret;
    }

    public FinalViewData setReposts_count_ret(int reposts_count_ret) {
        this.reposts_count_ret = reposts_count_ret;
        return  this;
    }

    public int getComments_count_ret() {
        return comments_count_ret;
    }

    public FinalViewData setComments_count_ret(int comments_count_ret) {
        this.comments_count_ret = comments_count_ret;
        return  this;
    }

    public String getRet_time() {
        return ret_time;
    }

    public FinalViewData setRet_time(String ret_time) {
        this.ret_time = ret_time;
        return  this;
    }

    public String getRet_headurl() {
        return ret_headurl;
    }

    public FinalViewData setRet_headurl(String ret_headurl) {
        this.ret_headurl = ret_headurl;
        return  this;
    }

    public long getRet_id() {
        return ret_id;
    }

    public FinalViewData setRet_id(long ret_id) {
        this.ret_id = ret_id;
        return  this;
    }
}
