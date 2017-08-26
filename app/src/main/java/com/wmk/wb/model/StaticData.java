package com.wmk.wb.model;

import android.content.Context;

import com.wmk.wb.R;
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

    public Context mContext;
    public Date system;
    private int themecolor= R.color.colorPrimary;

    public int getThemecolor() {
        return themecolor;
    }

    public void setThemecolor(int themecolor) {
        this.themecolor = themecolor;
    }

    public StaticData() {
        system    =   new    Date(System.currentTimeMillis());//获取当前时间
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
