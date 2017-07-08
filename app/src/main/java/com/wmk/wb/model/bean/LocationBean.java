package com.wmk.wb.model.bean;

import com.wmk.wb.model.StaticData;

/**
 * Created by wmk on 2017/6/20.
 */

public class LocationBean {
    private String Lat;
    private String Long;
    private double dLat;
    private double dLong;
    private int Range;

    public double getdLat() {
        return dLat;
    }

    public void setdLat(double dLat) {
        this.dLat = dLat;
    }

    public double getdLong() {
        return dLong;
    }

    public void setdLong(double dLong) {
        this.dLong = dLong;
    }

    private static class SingletonHolder{
        private static final LocationBean INSTANCE = new LocationBean();
    }
    //获取单例
    public static LocationBean getInstance(){
        return LocationBean.SingletonHolder.INSTANCE;
    }
    public String getLat() {
        return Lat;
    }

    public void setLat(String lat) {
        Lat = lat;
    }

    public String getLong() {
        return Long;
    }

    public void setLong(String aLong) {
        Long = aLong;
    }

    public int getRange() {
        return Range;
    }

    public void setRange(int range) {
        Range = range;
    }
}
