package com.wmk.wb.view.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.wmk.wb.R;
import com.wmk.wb.model.bean.Pic_List_Info;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by wmk on 2017/4/8.
 */

public class PicViewHolder extends RecyclerView.ViewHolder {
    public ImageView image;
    public String Largeurl;//大图的地址
    public int position;
    public ArrayList<String>large_url;
    public PicViewHolder(final View itemView, final Subscriber<Pic_List_Info> mSubscriber) {
        super(itemView);
        image=(ImageView) itemView.findViewById(R.id.picview);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(large_url!=null) {
                    final Pic_List_Info pli = new Pic_List_Info();
                    pli.setPosition(position);
                    pli.setLarg_url(large_url);
                    pli.setView(itemView);

                    Observable<Pic_List_Info> observable = Observable.create(new Observable.OnSubscribe<Pic_List_Info>() {
                        @Override
                        public void call(Subscriber<? super Pic_List_Info> subscriber) {
                            subscriber.onNext(pli);
                        }
                    });
                    observable.subscribe(mSubscriber);
                }
            }
        });

    }

    public void setLargeurl(String largeurl) {
        Largeurl = largeurl;
    }

    public void setLarge_url(ArrayList<String> large_url) {
        this.large_url = large_url;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
