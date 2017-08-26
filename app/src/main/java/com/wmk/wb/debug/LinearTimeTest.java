package com.wmk.wb.debug;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TimingLogger;
import android.widget.LinearLayout;

/**
 * Created by wmk on 2017/8/16.
 */

public class LinearTimeTest extends LinearLayout {
    private String time;
    public LinearTimeTest(Context context) {
        super(context);
    }

    public LinearTimeTest(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.setWillNotDraw(false);
    }

    public LinearTimeTest(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setWillNotDraw(false);
    }

    public LinearTimeTest(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public void draw(Canvas canvas) {
        TimingLogger timing= new TimingLogger("timing","all");
        super.draw(canvas);
        timing.addSplit("end");
        timing.dumpToLog();
    }
}
