package com.wmk.wb.utils;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.bumptech.glide.Glide;

/**
 * Created by wmk on 2017/7/9.
 */

public class XRecyclerView extends RecyclerView {
    public XRecyclerView(Context context) {
        super(context);
    }

    public XRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
     //   addOnScrollListener(new Scrollrec());
    }

    public XRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
     //   addOnScrollListener(new Scrollrec());
    }
    private class Scrollrec extends OnScrollListener
    {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            switch (newState) {
                case SCROLL_STATE_IDLE: {
                    try {
                        Glide.with(getContext()).resumeRequests();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
                default: {
                    try {
                        Glide.with(getContext()).pauseRequests();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
