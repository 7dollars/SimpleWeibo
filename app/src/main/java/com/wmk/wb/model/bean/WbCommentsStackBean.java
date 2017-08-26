package com.wmk.wb.model.bean;

import android.text.StaticLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wmk on 2017/8/16.
 */

public class WbCommentsStackBean {
    private List<FinalCommentsData> data;

    public WbCommentsStackBean() {
        this.data = new ArrayList<>();

    }

    public List<FinalCommentsData> getData() {
        return data;
    }

    public void setData(List<FinalCommentsData> data) {
        this.data = data;
    }


}
