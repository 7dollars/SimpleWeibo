package com.wmk.wb.presenter;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.wmk.wb.model.StaticData;
import com.wmk.wb.model.WbDataStack;
import com.wmk.wb.model.bean.FinalCommentsData;
import com.wmk.wb.model.bean.WbCommentsStackBean;
import com.wmk.wb.model.bean.WbStackBean;
import com.wmk.wb.model.bean.retjson.WbData;
import com.wmk.wb.utils.SpUtil;

/**
 * Created by wmk on 2017/6/15.
 */

public class BasePresenter {
    public int getThemeColor()
    {
        return StaticData.getInstance().getThemecolor();
    }
    public void setDataType(int type){WbDataStack.getInstance().getTop().setDataType(type);}

    public void initDataStack() {
        WbDataStack.getInstance().pushNew();
    }
    public void cleanData(){
        WbDataStack.getInstance().popTop();
    }
    public WbStackBean getTop(){ return WbDataStack.getInstance().getTop();}




    public void initCommentsStack(){WbDataStack.getInstance().pushNewComments();}
    public WbCommentsStackBean getCommentsTop(){return WbDataStack.getInstance().getCommentsTop();}
    public void cleanCommentsData(){WbDataStack.getInstance().popCommentsTop();}
}
