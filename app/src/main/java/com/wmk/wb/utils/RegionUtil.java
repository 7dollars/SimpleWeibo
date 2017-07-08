package com.wmk.wb.utils;

import com.wmk.wb.model.bean.RegionInfo;

import java.util.Random;

/**
 * Created by wmk on 2017/7/4.
 */

public class RegionUtil {
   private static double LatPublic[][]={//纬度
            {39.65,40.30},//北京
            {30.90,31.44},//上海
            {23.07,23.45},//广州
            {22.50,23.00},//深圳
            {30.11,30.44},//杭州
            {30.41,30.90},//成都
            {36.05,36.35},//青岛
            {38.80,39.30},//天津
            {31.85,32.15},//南京
            {30.30,30.80} //武汉
    };
    private static double LongPublic[][]={
            {116.00,116.85},
            {121.10,121.74},
            {113.10,113.50},
            {113.90,114.30},
            {120.00,120.36},
            {103.54,104.53},
            {120.28,120.63},
            {116.80,117.50},
            {118.50,119.00},
            {113.90,114.65}
    };
    public static RegionInfo getRegion(RegionEnum r)
    {
        RegionInfo ri=new RegionInfo();
        int temp=-1;
        switch(r)
        {
            case random:{
                temp=-1;
                break;
            }
            case beijing: {
                temp=0;
                break;
            }
            case shanghai:{
                temp=1;
                break;
            }
            case guangzhou:{
                temp=2;
                break;
            }
            case shenzhen:{
                temp=3;
                break;
            }
            case hangzhou:{
                temp=4;
                break;
            }
            case chengdu:{
                temp=5;
                break;
            }
            case qingdao:{
                temp=6;
                break;
            }
            case tianjin:{
                temp=7;
                break;
            }
            case nanjing:{
                temp=8;
                break;
            }
            case wuhan:{
                temp=9;
                break;
            }
        }
        if(temp==-1)
        {
            Random random = new Random();
            temp=random.nextInt(9)%(9+1);
        }
        ri.setLat(calcRandom(temp,0));
        ri.setLong(calcRandom(temp,1));
        return ri;
    }
    private static double calcRandom(int i,int j)//i为城市下标,j为经/纬，0为纬度，1为经度
    {
        int temp1,temp2;
        double sum;
        Random random = new Random();
        if(j==0)
        {
            temp1=(int)(LatPublic[i][0]*1000000);
            temp2=(int)(LatPublic[i][1]*1000000);
            sum=(double)random.nextInt(temp2)%(temp2-temp1+1)+temp1;
        }
        else
        {
            temp1=(int)(LongPublic[i][0]*1000000);
            temp2=(int)(LongPublic[i][1]*1000000);
            sum=(double)random.nextInt(temp2)%(temp2-temp1+1)+temp1;
        }
        return sum/1000000;
    }
}
