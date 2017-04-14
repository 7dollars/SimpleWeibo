package com.wmk.wb.model.entity.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.wmk.wb.R;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by wmk on 2017/4/8.
 */

public class PicViewHolder extends RecyclerView.ViewHolder {
    public ImageView image;
    public String Largeurl;//大图的地址
    public PicViewHolder(View itemView,final Subscriber<String> mSubscriber) {
        super(itemView);
        image=(ImageView) itemView.findViewById(R.id.picview);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Largeurl!=null)
                {
                    Observable<String> observable=Observable.create(new Observable.OnSubscribe<String>() {
                        @Override
                        public void call(Subscriber<? super String> subscriber) {
                            subscriber.onNext(Largeurl);
                        }
                    });
                    observable.subscribe(mSubscriber);
                }
            }
        });

    }

    public String getLargeurl() {
        return Largeurl;
    }

    public void setLargeurl(String largeurl) {
        Largeurl = largeurl;
    }
}
