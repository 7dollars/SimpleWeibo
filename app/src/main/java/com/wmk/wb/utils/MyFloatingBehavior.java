package com.wmk.wb.utils;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by wmk on 2017/7/22.
 * 实现floatingbutton的隐藏与显示
 */

public class MyFloatingBehavior extends CoordinatorLayout.Behavior {

    private boolean isOuting = false;
    public MyFloatingBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;//需要监听的方向
    }

    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target) {
        super.onStopNestedScroll(coordinatorLayout, child, target);
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        if (((dyConsumed < 0 && dyUnconsumed == 0) || (dyConsumed == 0 && dyUnconsumed < 0))
                && child.getVisibility() != View.VISIBLE) {// 显示
            AnimationUtils.scaleShow(child, null);
        }
        else if (((dyConsumed > 0 && dyUnconsumed == 0) || (dyConsumed == 0 && dyUnconsumed > 0))
                && child.getVisibility() != View.INVISIBLE&&!isOuting ) {
            AnimationUtils.scaleHide(child, new ViewPropertyAnimatorListener() {
                @Override
                public void onAnimationStart(View view) {
                    isOuting=true;
                }

                @Override
                public void onAnimationEnd(View view) {
                    isOuting=false;
                    view.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onAnimationCancel(View view) {
                    isOuting=false;
                }
            });
        }
    }
}
