package com.wmk.wb.presenter.adapter;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;

import com.wmk.wb.model.bean.DetialsInfo;
import com.wmk.wb.model.bean.Pic_List_Info;

import rx.Subscriber;

/**
 * Created by wmk on 2017/6/21.
 */

public class ExplorerAdapter extends MainListAdapter {
    public ExplorerAdapter(Context context, SwipeRefreshLayout swipe, Subscriber<DetialsInfo> s, Subscriber<Pic_List_Info> s2) {
        super(context, swipe, s, s2);
    }
}
