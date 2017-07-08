package com.wmk.wb.utils;

import com.wmk.wb.R;

/**
 * Created by wmk on 2017/6/29.
 */

public class ColorThemeUtils {
    private static int [] color={R.color.colorPrimary,R.color.primaryColor,R.color.colorAccent,R.color.md_grey_600,
            R.color.md_green_800};
    public static int getColor(int i)
    {
        return i>color.length?color[0]:color[i];
    }
    public static int getColorLength(){return color.length;}
}
