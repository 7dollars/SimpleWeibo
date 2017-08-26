package com.wmk.wb.presenter.adapter;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wmk.wb.model.StaticData;
import com.wmk.wb.model.WbDataStack;
import com.wmk.wb.model.bean.DetialsInfo;
import com.wmk.wb.model.bean.Pic_List_Info;
import com.wmk.wb.view.holder.MainViewHolder3;

import rx.Subscriber;

/**
 * Created by wmk on 2017/8/4.
 */

public class TopicListAdapter extends MainListAdapter  {
    public TopicListAdapter(Context context, SwipeRefreshLayout swipe, Subscriber<DetialsInfo> s, Subscriber<Pic_List_Info> s2) {
        super(context, swipe, s, s2);
    }
}
