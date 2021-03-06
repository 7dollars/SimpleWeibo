package com.wmk.wb.model.bean;

import android.text.SpannableString;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wmk on 2017/4/4.
 */
public class FinalViewData {
    public SpannableString text;
    public String headurl;
    public String ret_headurl;
    public String name;
    public String ret_name;
    public SpannableString ret_text;
    public SpannableString ret_text_with_name;

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
    public boolean isFollowing;

    public String description;
    public String gender;
    public String followers_count;
    public String friends_count;
    public String statuses_count;

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


    public SpannableString getText() {
        return text;
    }

    public FinalViewData setText(SpannableString text) {
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

    public SpannableString getRet_text_with_name() {
        return ret_text_with_name;
    }

    public FinalViewData setRet_text_with_name(SpannableString ret_text_with_name) {
        this.ret_text_with_name = ret_text_with_name;
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

    public SpannableString getRet_text() {
        return ret_text;
    }

    public FinalViewData setRet_text(SpannableString ret_text) {
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

    public String getDescription() {
        return description;
    }

    public FinalViewData setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getGender() {
        return gender;
    }

    public FinalViewData setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public String getFollowers_count() {
        return followers_count;
    }

    public FinalViewData setFollowers_count(String followers_count) {
        this.followers_count = followers_count;
        return this;
    }

    public String getFriends_count() {
        return friends_count;
    }

    public FinalViewData setFriends_count(String friends_count) {
        this.friends_count = friends_count;
        return this;
    }

    public String getStatuses_count() {
        return statuses_count;
    }

    public FinalViewData setStatuses_count(String statuses_count) {
        this.statuses_count = statuses_count;
        return this;
    }

    public boolean isFollowing() {
        return isFollowing;
    }

    public void setFollowing(boolean following) {
        isFollowing = following;
    }
}
