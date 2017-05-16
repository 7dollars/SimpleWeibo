package com.wmk.wb.presenter.adapter;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.special.ResideMenu.ResideMenu;
import com.wmk.wb.R;
import com.wmk.wb.model.entity.DetialsInfo;
import com.wmk.wb.model.entity.FinalViewData;
import com.wmk.wb.model.entity.holder.MainViewHolder3;
import com.wmk.wb.model.entity.StaticData;


import rx.Subscriber;

/**
 * Created by wmk on 2017/4/3.
 */

public class MainListAdapter extends RecyclerView.Adapter  {
    private Context mContext;
    private SwipeRefreshLayout mswipe;
    private  Subscriber<DetialsInfo> mSubscriber;
    private  Subscriber<String> mSubscriber2;
    private  ResideMenu resideMenu;

    public MainListAdapter(Context context, SwipeRefreshLayout swipe, Subscriber<DetialsInfo> s, Subscriber<String> s2, ResideMenu resideMenu) {
        super();
        mContext = context;
        mswipe = swipe;
        mSubscriber=s;
        mSubscriber2=s2;
        this.resideMenu=resideMenu;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        if (StaticData.getInstance().data != null)
            return StaticData.getInstance().data.size();
        else
            return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch(viewType)
        {
            case 0:
            {
                MainViewHolder3 mainviewholder = new MainViewHolder3(LayoutInflater.from(
                        mContext).inflate(R.layout.wb_text_normal_pic, parent,
                        false),mSubscriber,resideMenu);
                return mainviewholder;
            }
            case 1:
            {
                MainViewHolder3 mainviewholder = new MainViewHolder3(LayoutInflater.from(
                        mContext).inflate(R.layout.wb_text_convey_pic, parent,
                        false),mSubscriber,resideMenu);
                return mainviewholder;
            }
        }
        return null;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        LinearLayoutManager manager;
        FinalViewData data=StaticData.getInstance().getData().get(position);
        switch(getItemViewType(position))
        {
            case 0:
            {
                MainViewHolder3 holder3=(MainViewHolder3)holder;
                holder3.content.setText(data.getText());
                holder3.author.setText(data.getName());
                holder3.time.setText(data.getTime());
                holder3.reposts_comments_count.setText(data.getReposts_count()+"转发 | "+data.getComments_count()+"回复");

                Glide.with(mContext).load(data.getHeadurl()).into(holder3.head);
                manager = new LinearLayoutManager(holder3.pic_view.getContext());
                manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                holder3.pic_view.setLayoutManager(manager);
                holder3.pic_view.setAdapter(new PicListAdapter(mContext,data.getPicurls(),position,mSubscriber2));
                break;
            }
            case 1:
            {
                int i=position;
                MainViewHolder3 holder3=(MainViewHolder3)holder;
                holder3.content.setText(data.getText());
                holder3.author.setText(data.getName());
                holder3.time.setText(data.getTime());
                holder3.ret_content.setText("@"+data.getRet_name()+":"+data.getRet_text());
                holder3.reposts_comments_count.setText(data.getReposts_count()+"转发 | "+data.getComments_count()+"回复");
                holder3.reposts_comments_ret.setText(data.getReposts_count_ret()+"转发 | "+data.getComments_count_ret()+"回复");

                Glide.with(mContext).load(data.getHeadurl()).into(holder3.head);


                manager = new LinearLayoutManager(holder3.pic_view.getContext());
                manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                holder3.pic_view.setLayoutManager(manager);
                holder3.pic_view.setAdapter(new PicListAdapter(mContext, data.getRet_picurls(),i,mSubscriber2));
                break;
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(StaticData.getInstance().getData().get(position).getRet_text() == null)
        {
            return 0;
        }
        else
        {
            return 1;
        }
    }
}