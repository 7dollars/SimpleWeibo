package com.wmk.wb.presenter.adapter;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.wmk.wb.R;
import com.wmk.wb.model.StaticData;
import com.wmk.wb.model.bean.DetialsInfo;
import com.wmk.wb.model.bean.FinalViewData;
import com.wmk.wb.model.bean.Pic_List_Info;
import com.wmk.wb.utils.TextUtils;
import com.wmk.wb.view.holder.MainViewHolder3;

import rx.Subscriber;

/**
 * Created by wmk on 2017/6/13.
 */

public class PersonalListAdapter extends MainListAdapter {

    public PersonalListAdapter(Context context, SwipeRefreshLayout swipe, Subscriber<DetialsInfo> s, Subscriber<Pic_List_Info> s2) {
        super(context, swipe, s, s2);
    }
}
