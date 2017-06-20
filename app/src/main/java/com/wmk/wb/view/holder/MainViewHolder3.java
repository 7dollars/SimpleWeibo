package com.wmk.wb.view.holder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wmk.wb.R;
import com.wmk.wb.model.bean.DetialsInfo;
import com.wmk.wb.model.bean.NameEvent;
import com.wmk.wb.utils.ClickMovementMethod;

import org.greenrobot.eventbus.EventBus;

import de.hdodenhof.circleimageview.CircleImageView;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by wmk on 2017/4/8.
 */

public class MainViewHolder3 extends RecyclerView.ViewHolder{
    public TextView author;
    public TextView time;
    public TextView content;
    public CircleImageView head;
    public TextView ret_content;
    public RecyclerView pic_view;
    public TextView reposts_comments_count;
    public TextView reposts_comments_ret;

    public CardView cv;
    public CardView cv_ret;
    public MainViewHolder3(final View itemView, final Subscriber<DetialsInfo> mSubscriber) {
        super(itemView);
        author = (TextView) itemView.findViewById(R.id.txt_author);
        time = (TextView) itemView.findViewById(R.id.txt_time);
        content = (TextView) itemView.findViewById(R.id.txt_content);
        head = (CircleImageView) itemView.findViewById(R.id.imageView);
        pic_view=(RecyclerView)itemView.findViewById(R.id.list_pic);
        cv=(CardView)itemView.findViewById(R.id.main);
        cv_ret=(CardView)itemView.findViewById(R.id.comment);
        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new NameEvent(author.getText().toString()));
            }
        });
        content.setOnTouchListener(ClickMovementMethod.getInstance());

        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final DetialsInfo detialsInfo=new DetialsInfo();
                if(cv_ret!=null)
                    detialsInfo.setHasChild(true);
                detialsInfo.setPosition(getPosition());
                detialsInfo.setRet(false);
                detialsInfo.setRe(itemView);

                Observable<DetialsInfo> observable=Observable.create(new Observable.OnSubscribe<DetialsInfo>() {
                    @Override
                    public void call(Subscriber<? super DetialsInfo> subscriber) {
                        subscriber.onNext(detialsInfo);
                    }
                });
                observable.subscribe(mSubscriber);
            }
        });

        if(cv_ret!=null) {
            cv_ret.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final DetialsInfo detialsInfo=new DetialsInfo();
                    detialsInfo.setPosition(getPosition());
                    detialsInfo.setRet(true);
                    detialsInfo.setRe(itemView);

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
        if(itemView.findViewById(R.id.ret_content)!=null)
        {
            ret_content = (TextView) itemView.findViewById(R.id.ret_content);
            reposts_comments_ret=(TextView)itemView.findViewById(R.id.count_ret);

            ret_content.setOnTouchListener(ClickMovementMethod.getInstance());
        }
        reposts_comments_count=(TextView)itemView.findViewById(R.id.count);

    }

}
