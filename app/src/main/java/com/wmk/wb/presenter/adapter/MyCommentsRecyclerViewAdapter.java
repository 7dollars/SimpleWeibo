package com.wmk.wb.presenter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wmk.wb.R;
import com.wmk.wb.model.entity.FinalCommentsData;
import com.wmk.wb.model.entity.StaticData;



import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyCommentsRecyclerViewAdapter extends RecyclerView.Adapter<MyCommentsRecyclerViewAdapter.ViewHolder> {

    private List<FinalCommentsData> data;

    private Context mContext;

    public MyCommentsRecyclerViewAdapter(Context mContext) {

        this.data= StaticData.getInstance().cdata;
        this.mContext=mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_comments, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.author.setText(data.get(position).getName());
        holder.text.setText(data.get(position).getText());
        holder.time.setText(data.get(position).getTime());
        holder.source.setText(Html.fromHtml(data.get(position).getSource()));

        String head=StaticData.getInstance().cdata.get(position).getHeadurl();
        if(head!=null) {
        /*    if (CacheUtil.getBitmapFromMemCache(head) != null) {
                holder.head.setImageBitmap(CacheUtil.getBitmapFromMemCache(head));
            } else {
                DataManager.getInstance().getPic(loadPic(head,this), head);
            }*/
            Glide.with(mContext).load(head).into(holder.head);
        }
    }

    @Override
    public int getItemCount() {
        return StaticData.getInstance().cdata.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView author;
        public final TextView text;
        public final TextView time;
        public final CircleImageView head;
        public final TextView source;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            author = (TextView) view.findViewById(R.id.txt_author);
            text = (TextView) view.findViewById(R.id.txt_content);
            time = (TextView) view.findViewById(R.id.txt_time);
            head=(CircleImageView)view.findViewById(R.id.imageView);
            source=(TextView)view.findViewById(R.id.count);
        }


    }

}
