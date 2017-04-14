package com.wmk.wb.presenter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.wmk.wb.R;
import com.wmk.wb.model.entity.holder.PicViewHolder;
import java.util.List;

import rx.Subscriber;

/**
 * Created by wmk on 2017/4/8.
 */

public class PicListAdapter extends RecyclerView.Adapter<PicViewHolder> {

    private Context mContext;
    private  List<String> url_list;
    private  int i;
    Subscriber<String> ms;
    public PicListAdapter(Context context,List<String> url,int position,Subscriber<String> ms) {
        super();
        mContext=context;
        url_list=url;
        i=position;
        this.ms=ms;
    }

    @Override
    public PicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PicViewHolder holder=new PicViewHolder(LayoutInflater.from(
                mContext).inflate(R.layout.recview_pic, parent,
                false),ms);
        return holder;
    }

    @Override
    public void onBindViewHolder(PicViewHolder holder, int position) {

        Glide.with(mContext).load(url_list.get(position)).into(holder.image);
        String s = url_list.get(position);
        String result = "http://wx4.sinaimg.cn/large/"+s.substring(s.lastIndexOf('/')+1);
        holder.setLargeurl(result);

    }
    @Override
    public int getItemCount() {
        return url_list.size();
    }

}
