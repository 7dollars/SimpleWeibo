package com.wmk.wb.presenter;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.wmk.wb.model.StaticData;
import com.wmk.wb.utils.SpUtil;

/**
 * Created by wmk on 2017/6/15.
 */

public class BasePresenter {
    public int getThemeColor()
    {
        return StaticData.getInstance().getThemecolor();
    }
}
