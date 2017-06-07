package com.wmk.wb.model;

import android.content.Context;

import com.wmk.wb.model.bean.FinalCommentsData;
import com.wmk.wb.model.bean.FinalViewData;
import com.wmk.wb.model.bean.retjson.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wmk on 2017/4/4.
 */
//一些全局的数据，如ListView用的数据包，和Context
public class StaticData {

    public List<FinalViewData> data;
    public List<FinalCommentsData> cdata;
    public Context mContext;

    public Date system;
    public User localUser;
    public int WbFlag;

    public int getWbFlag() {
        return WbFlag;
    }

    public void setWbFlag(int wbFlag) {
        WbFlag = wbFlag;
    }


    public StaticData() {
        WbFlag=0;
        data=new ArrayList<>();
        cdata=new ArrayList<>();
        system    =   new    Date(System.currentTimeMillis());//获取当前时间
        localUser=new User();
    }

    public List<FinalViewData> getData() {
        return data;
    }

    public void setData(List<FinalViewData> data) {
        this.data = data;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public Date getSystem() {
        return system;
    }

    private static class SingletonHolder{
        private static final StaticData INSTANCE = new StaticData();
    }
    //获取单例
    public static StaticData getInstance(){
        return StaticData.SingletonHolder.INSTANCE;
    }
}
