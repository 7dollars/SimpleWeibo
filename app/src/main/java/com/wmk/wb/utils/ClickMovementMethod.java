package com.wmk.wb.utils;

import android.text.Layout;
import android.text.Spannable;
import android.text.style.ClickableSpan;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * Created by wmk on 2017/6/14.
 */

public class ClickMovementMethod implements View.OnTouchListener  {
    private static ClickMovementMethod sInstance;

    public static ClickMovementMethod getInstance() {
        if (sInstance == null) {
            sInstance = new ClickMovementMethod();
        }
        return sInstance;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        boolean ret = false;
        TextView widget = (TextView) v;
        CharSequence text = widget.getText();
        Spannable spannable = Spannable.Factory.getInstance().newSpannable(text);
        int action = event.getAction();
        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_DOWN) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            x -= widget.getTotalPaddingLeft();
            y -= widget.getTotalPaddingTop();
            x += widget.getScrollX();
            y += widget.getScrollY();
            Layout layout = widget.getLayout();
            int line = layout.getLineForVertical(y);
            int off = layout.getOffsetForHorizontal(line, x);
            ClickableSpan[] link = spannable.getSpans(off, off, ClickableSpan.class);
            if (link.length != 0) {
                if (action == MotionEvent.ACTION_UP) {
                    link[0].onClick(widget);
                }
                ret = true;
            }
        }
        return ret;
    }
}
