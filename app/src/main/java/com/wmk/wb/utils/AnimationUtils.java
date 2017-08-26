package com.wmk.wb.utils;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

/**
 * Created by wmk on 2017/7/22.
 */

public class AnimationUtils {
    public static void scaleShow(View view, ViewPropertyAnimatorListener viewPropertyAnimatorListener) {
        view.setVisibility(View.VISIBLE);
        ViewCompat.animate(view)
                .scaleX(1.0f)
                .scaleY(1.0f)
                .alpha(1.0f)
                .setDuration(300)
                .setListener(viewPropertyAnimatorListener)
                .setInterpolator(new FastOutSlowInInterpolator())
                .start();
    }
    public static void scaleHide(View view, ViewPropertyAnimatorListener viewPropertyAnimatorListener) {
        ViewCompat.animate(view)
                .scaleX(0.0f)
                .scaleY(0.0f)
                .alpha(0.0f)
                .setDuration(300)
                .setInterpolator(new LinearInterpolator())
                .setListener(viewPropertyAnimatorListener)
                .start();
    }
}
