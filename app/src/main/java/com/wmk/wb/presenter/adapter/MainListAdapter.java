package com.wmk.wb.presenter.adapter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wmk.wb.R;
import com.wmk.wb.model.WbDataStack;
import com.wmk.wb.model.bean.DetialsInfo;
import com.wmk.wb.model.bean.FinalViewData;
import com.wmk.wb.model.bean.LoadingBus;
import com.wmk.wb.model.bean.NameEvent;
import com.wmk.wb.model.bean.retjson.WbData;
import com.wmk.wb.utils.ClickMovementMethod;
import com.wmk.wb.utils.DensityUtil;
import com.wmk.wb.utils.NinePicLayout;
import com.wmk.wb.utils.StaticLayoutView;
import com.wmk.wb.view.holder.MainViewHolder3;
import com.wmk.wb.model.bean.Pic_List_Info;


import org.greenrobot.eventbus.EventBus;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by wmk on 2017/4/3.
 */

public class MainListAdapter extends RecyclerView.Adapter implements NinePicLayout.PicClickListener {
    protected Context mContext;
    private  Subscriber<DetialsInfo> mSubscriber;
    private  Subscriber<Pic_List_Info> mSubscriber2;
    protected FinalViewData data=null;
    private boolean cacheflag=false;
    private Canvas dummyCanvas;
    private TextPaint textPaint;


    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
        MainViewHolder3 holder3=(MainViewHolder3)holder;
        if(holder3.nine!=null)
            holder3.nine.clean();
    }

    public MainListAdapter(Context context, SwipeRefreshLayout swipe, Subscriber<DetialsInfo> s, Subscriber<Pic_List_Info> s2) {
        super();
        mContext = context;
        mSubscriber=s;
        mSubscriber2=s2;

    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        if (WbDataStack.getInstance().getTop().getData() != null&&WbDataStack.getInstance().getTop().getData().size()!=0)
            return WbDataStack.getInstance().getTop().getData().size() + 1;
        else
            return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MainViewHolder3 mainviewholder=null;
        View itemView=null;
        switch(viewType)
        {
            case 0:
            {
                itemView=LayoutInflater.from(mContext).inflate(R.layout.wb_text_normal_pic, parent, false);
                mainviewholder = new MainViewHolder3(itemView,mSubscriber);
                break;
            }
            case 1:
            {
                itemView=LayoutInflater.from(mContext).inflate(R.layout.wb_text_convey_pic, parent, false);
                mainviewholder = new MainViewHolder3(itemView,mSubscriber);
                break;
            }
            case 2:
            {
                itemView=LayoutInflater.from(mContext).inflate(R.layout.loading_item, parent, false);
                mainviewholder = new MainViewHolder3(itemView,mSubscriber);
                break;
            }
        }
        final MainViewHolder3 m=mainviewholder;
        final View item=itemView;
        if(mainviewholder.head!=null) {
            mainviewholder.head.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new NameEvent(m.author.getText().toString()));
                }
            });
            mainviewholder.content.setOnTouchListener(ClickMovementMethod.getInstance());
        }
        if(mainviewholder.cv!=null)
            mainviewholder.cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final DetialsInfo detialsInfo=new DetialsInfo();
                    if(m.cv_ret!=null)
                        detialsInfo.setHasChild(true);
                    detialsInfo.setPosition(m.getPosition());
                    detialsInfo.setRet(false);
                    detialsInfo.setRe(item);

                    Observable<DetialsInfo> observable=Observable.create(new Observable.OnSubscribe<DetialsInfo>() {
                        @Override
                        public void call(Subscriber<? super DetialsInfo> subscriber) {
                            subscriber.onNext(detialsInfo);
                        }
                    });
                    observable.subscribe(mSubscriber);
                }
            });

        if(mainviewholder.cv_ret!=null) {
            mainviewholder.cv_ret.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final DetialsInfo detialsInfo=new DetialsInfo();
                    detialsInfo.setPosition(m.getPosition());
                    detialsInfo.setRet(true);
                    detialsInfo.setRe(item);

                    Observable<DetialsInfo> observable=Observable.create(new Observable.OnSubscribe<DetialsInfo>() {
                        @Override
                        public void call(Subscriber<? super DetialsInfo> subscriber) {
                            subscriber.onNext(detialsInfo);
                        }
                    });
                    observable.subscribe(mSubscriber);
                }
            });
        }
        if(mainviewholder.loadingLayout!=null) {
            LoadingBus lb = new LoadingBus();
            lb.setLoading(mainviewholder.loading);
            lb.setPress(false);
            EventBus.getDefault().post(lb);
            mainviewholder.loadingLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!m.loading.getText().equals("加载中···")) {
                        LoadingBus lb = new LoadingBus();
                        lb.setLoading(m.loading);
                        lb.setPress(true);
                        m.loading.setText("加载中···");
                        EventBus.getDefault().post(lb);
                    }
                }
            });
        }
        if(itemView.findViewById(R.id.ret_content)!=null)
        {
           mainviewholder.ret_content = (TextView) itemView.findViewById(R.id.ret_content);
            mainviewholder. reposts_comments_ret=(TextView)itemView.findViewById(R.id.count_ret);

            mainviewholder.ret_content.setOnTouchListener(ClickMovementMethod.getInstance());
        }
        mainviewholder.reposts_comments_count=(TextView)itemView.findViewById(R.id.count);
        return mainviewholder;

    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(position!=getItemCount()-1)
            data= WbDataStack.getInstance().getTop().getData().get(position);
        MainViewHolder3 holder3=(MainViewHolder3)holder;
        switch(getItemViewType(position))
        {
            case 0:
            {
                holder3.content.setText(data.getText());
                holder3.author.setText(data.getName());
                holder3.time.setText(data.getTime());
                holder3.reposts_comments_count.setText(data.getReposts_count()+"转发 | "+data.getComments_count()+"回复");

                if(data.getHeadurl()!=null)
                    Glide.with(mContext).load(data.getHeadurl()).diskCacheStrategy(DiskCacheStrategy.RESULT).into(holder3.head);

                holder3.nine.setPicClickListener(this);
                holder3.nine.setImageUrlList(data.getPicurls());
                holder3.nine.loadPIC();
                break;
            }
            case 1:
            {
                holder3.content.setText(data.getText());
                holder3.author.setText(data.getName());
                holder3.time.setText(data.getTime());
                holder3.ret_content.setText(data.getRet_text_with_name());
                holder3.reposts_comments_count.setText(data.getReposts_count()+"转发 | "+data.getComments_count()+"回复");
               holder3.reposts_comments_ret.setText(data.getReposts_count_ret()+"转发 | "+data.getComments_count_ret()+"回复");

                if(data.getHeadurl()!=null)
                    Glide.with(mContext).load(data.getHeadurl()).diskCacheStrategy(DiskCacheStrategy.RESULT).into(holder3.head);

                holder3.nine.setPicClickListener(this);
                holder3.nine.setImageUrlList(data.getRet_picurls());
                holder3.nine.loadPIC();
                break;
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position==getItemCount()-1)
            return 2;
        if(WbDataStack.getInstance().getTop().getData().get(position).getRet_text() == null)
        {
            return 0;
        }
        else
        {
            return 1;
        }
    }

    @Override
    public void click(final Pic_List_Info i) {
        Observable<Pic_List_Info> observable = Observable.create(new Observable.OnSubscribe<Pic_List_Info>() {
                @Override
                public void call(Subscriber<? super Pic_List_Info> subscriber) {subscriber.onNext(i);}
            });
        observable.subscribe(mSubscriber2);
        }

}

