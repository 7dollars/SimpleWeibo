package com.wmk.wb.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by wmk on 2017/5/19.
 * 用于修复缩放图片崩溃问题
 */

public class ViewPagerFix extends android.support.v4.view.ViewPager  {
    public ViewPagerFix(Context context) {
        super(context);
    }

    public ViewPagerFix(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        try {
            return super.onTouchEvent(ev);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
