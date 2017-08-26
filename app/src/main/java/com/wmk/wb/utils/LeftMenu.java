package com.wmk.wb.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

/**
 * Created by wmk on 2017/8/7.
 */

public class LeftMenu extends HorizontalScrollView {

    private int mScreenWidth;

    private int mMenuRightPadding = 50;

    private int mMenuWidth;
    private int mHalfMenuWidth;

    private boolean once;
    private Context context;

    public LeftMenu(Context context) {
        super(context);
        this.context=context;
    }

    public LeftMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!once)
        {
            LinearLayout wrapper = (LinearLayout) getChildAt(0);
            ViewGroup menu = (ViewGroup) wrapper.getChildAt(0);
            ViewGroup content = (ViewGroup) wrapper.getChildAt(1);
            // dp to px
            mMenuRightPadding = DensityUtil.dip2px(context,mMenuRightPadding);

            mMenuWidth = mScreenWidth - mMenuRightPadding;
            mHalfMenuWidth = mMenuWidth / 2;
            menu.getLayoutParams().width = mMenuWidth;
            content.getLayoutParams().width = mScreenWidth;

        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action)
        {
            // Up时，进行判断，如果显示区域大于菜单宽度一半则完全显示，否则隐藏
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                if (scrollX > mHalfMenuWidth)
                    this.smoothScrollTo(mMenuWidth, 0);
                else
                    this.smoothScrollTo(0, 0);
                return true;
        }
        return super.onTouchEvent(ev);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed)
        {
            // 将菜单隐藏
            this.scrollTo(mMenuWidth, 0);
            once = true;
        }
    }
}
