package com.wmk.wb.model.bean;

import android.text.StaticLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wmk on 2017/8/16.
 */

public class WbStackBean {
    private List<FinalViewData> data;
    private int PageCount = 1;
    private int DataType = 0;//全部/原创/相互关注等等

    public int getPageCount() {
        return PageCount;
    }

    public void setPageCount(int pageCount) {
        PageCount = pageCount;
    }
    public void incPageCount() { this.PageCount++; }

    public WbStackBean() {
        this.data = new ArrayList<>();
    }

    public List<FinalViewData> getData() {
        return data;
    }

    public void setData(List<FinalViewData> data) {
        this.data = data;
    }
    public int getDataType() {
        return DataType;
    }

    public void setDataType(int dataType) {
        DataType = dataType;
    }
}
