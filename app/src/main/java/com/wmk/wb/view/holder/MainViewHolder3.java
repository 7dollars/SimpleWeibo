package com.wmk.wb.view.holder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wmk.wb.R;
import com.wmk.wb.model.bean.DetialsInfo;
import com.wmk.wb.utils.NinePicLayout;
import com.wmk.wb.utils.StaticLayoutView;

import de.hdodenhof.circleimageview.CircleImageView;
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
    public TextView loading;
    public LinearLayout loadingLayout;
    public NinePicLayout nine;

    public CardView cv;
    public CardView cv_ret;
    public MainViewHolder3(final View itemView, final Subscriber<DetialsInfo> mSubscriber) {
        super(itemView);
        author = (TextView) itemView.findViewById(R.id.txt_author);
        time = (TextView) itemView.findViewById(R.id.txt_time);
        content = (TextView) itemView.findViewById(R.id.txt_content);
        head = (CircleImageView) itemView.findViewById(R.id.imageView);
     //   pic_view=(RecyclerView)itemView.findViewById(R.id.list_pic);
        cv=(CardView)itemView.findViewById(R.id.main);
        cv_ret=(CardView)itemView.findViewById(R.id.comment);
        loading=(TextView)itemView.findViewById(R.id.loading_item);
        nine=(NinePicLayout)itemView.findViewById(R.id.nineLayout);
        loadingLayout=(LinearLayout)itemView.findViewById(R.id.loading_layout);


    }

}
