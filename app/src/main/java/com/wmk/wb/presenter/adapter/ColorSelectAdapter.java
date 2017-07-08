package com.wmk.wb.presenter.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

import com.wmk.wb.R;
import com.wmk.wb.utils.ColorThemeUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by wmk on 2017/6/15.
 */

public class ColorSelectAdapter extends BaseAdapter{

    public ColorSelectAdapter() {
        super();
    }

    @Override
    public int getCount() {
        return ColorThemeUtils.getColorLength();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh1 = null;
        if(convertView==null)
        {
            vh1=new ViewHolder();
            convertView=View.inflate(parent.getContext(),R.layout.color_layout,null);
            vh1.imageView=(ImageView)convertView.findViewById(R.id.imageView2);
            convertView.setTag(vh1);
        }
        else
        {
            vh1=(ViewHolder) convertView.getTag();
        }
        vh1.imageView.setBackgroundColor(parent.getContext().getResources().getColor(ColorThemeUtils.getColor(position)));
        return convertView;
    }
    public static class ViewHolder {
        public ImageView imageView;
    }
}
