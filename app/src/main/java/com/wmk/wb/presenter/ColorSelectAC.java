package com.wmk.wb.presenter;

import com.wmk.wb.model.StaticData;

/**
 * Created by wmk on 2017/6/15.
 */

public class ColorSelectAC extends BasePresenter {
    public void setTheme(int color)
    {
        StaticData.getInstance().setThemecolor(color);
    }
}