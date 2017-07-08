package com.wmk.wb.model.bean;

/**
 * Created by wmk on 2017/4/12.
 */

public class FinalCommentsData {
    public String name;
    public String text;
    public String headurl;
    public String time;
    public long id;
    public String source;
    public Long wbId;

    public String getName() {
        return name;
    }

    public FinalCommentsData setName(String name) {
        this.name = name;
        return this;
    }

    public String getText() {
        return text;
    }

    public FinalCommentsData setText(String text) {
        this.text = text;
        return this;
    }

    public String getHeadurl() {
        return headurl;
    }

    public FinalCommentsData setHeadurl(String headurl) {
        this.headurl = headurl;
        return this;
    }

    public String getTime() {
        return time;
    }

    public FinalCommentsData setTime(String time) {
        this.time = time;
        return this;
    }

    public long getId() {
        return id;
    }

    public FinalCommentsData setId(long id) {
        this.id = id;
        return this;
    }

    public String getSource() {
        return source;
    }

    public FinalCommentsData setSource(String source) {
        this.source = source;
        return this;
    }

    public Long getWbId() {
        return wbId;
    }

    public FinalCommentsData setWbId(Long wbId) {
        this.wbId = wbId;
        return this;
    }
}
