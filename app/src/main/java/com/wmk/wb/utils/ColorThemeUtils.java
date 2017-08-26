package com.wmk.wb.utils;

import com.wmk.wb.R;

/**
 * Created by wmk on 2017/6/29.
 */

public class ColorThemeUtils {
    private static int [] color={
            R.color.colorPrimary,R.color.md_grey_600,R.color.color1,R.color.color2,
            R.color.color3,R.color.color4,R.color.color5,R.color.color6,
            R.color.color7,R.color.color8,R.color.color9,R.color.color10,
            R.color.color11,R.color.color12,R.color.color13,R.color.color14,
            R.color.color15,R.color.color16,R.color.color17,R.color.color18,
            R.color.color19,R.color.color20,R.color.color21,R.color.color22,
            R.color.color23,R.color.color24,R.color.color25,R.color.color26,
            R.color.color27,R.color.color28,R.color.color29,R.color.color30
            };
    public static int getColor(int i)
    {
        return i>color.length?color[0]:color[i];
    }
    public static int getColorLength(){return color.length;}
}
