package com.wmk.wb.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wmk.wb.R;
import com.wmk.wb.model.bean.Pic_List_Info;

import java.util.ArrayList;
import java.util.List;

import me.xiaopan.sketch.Sketch;
import me.xiaopan.sketch.SketchImageView;

/**
 * Created by wmk on 2017/7/8.
 */

public class NinePicLayout extends ViewGroup {
    private PicClickListener pli;
    private int TotalWidth,SingleWidth,OnceHeight,gap=5;
    private int row=0,column=0;
    private int l,r,t,b;
    private ArrayList<String> large_url_list;
    private List<String> url_list;
    private boolean loadflag=false;

    public NinePicLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void invalidate() {
        super.invalidate();
    }


    public NinePicLayout(Context context) {
        super(context);
    }

    public NinePicLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        this.l=l;this.r=r;this.t=t;this.b=b;
        layoutChildView();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
      //  super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        if(url_list==null) {
            setMeasuredDimension(sizeWidth,0);
            return;
        }
        int ChildCount=url_list.size();
        if(ChildCount==0) {
            setMeasuredDimension(sizeWidth,0);
            return;
        }
        TotalWidth=sizeWidth;
        SingleWidth=(TotalWidth-2*gap)/3;
        if(ChildCount==1) {
                      sizeHeight = (int) (0.62 * TotalWidth);
                      OnceHeight=sizeHeight;
                  }
                  else {
                      sizeHeight =  row * SingleWidth + (row - 1) * gap;
                  }
        setMeasuredDimension(sizeWidth,sizeHeight);

    }


    public void clean()
    {
        removeAllViews();
    }
    public void loadPIC()
    {

        int ChildCount=url_list.size();
        for(int i=0;i<ChildCount;i++) {
            SketchImageView siv = (SketchImageView) getChildAt(i);
           // Glide.with(getContext()).load(url_list.get(i)).into(siv);
            Sketch.with(getContext()).display(url_list.get(i), siv)
                       .commit();
        }
    }
    public void setImageUrlList(List<String> urlList) {
        large_url_list=new ArrayList<>();
        url_list=new ArrayList<>();
        if(urlList!=null)
        {
           // url_list=urlList;
            for(String x:urlList)
            {
                url_list.add( "http://wx4.sinaimg.cn/bmiddle/"+x.substring(x.lastIndexOf('/')+1));
            }
            for(String x:urlList)
            {
                large_url_list.add( "http://wx4.sinaimg.cn/large/"+x.substring(x.lastIndexOf('/')+1));
            }
            removeAllViews();
            for (int i = 0; i < large_url_list.size(); i++)
                addView(createImage(i), generateDefaultLayoutParams());
        }
        calcLayout();
    //    layoutChildView();
    }
    public void setPicClickListener(PicClickListener pcl) {
        this.pli=pcl;
    }
    private SketchImageView createImage(final int i) {
        SketchImageView si=new SketchImageView(getContext());
        si.setScaleType(ImageView.ScaleType.CENTER_CROP);
        si.setBackgroundColor(getResources().getColor(R.color.md_grey_300));
    //    Sketch.with(getContext()).display(large_url_list.get(i), si)
     //           .commit();
        si.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pli!=null&&large_url_list!=null) {
                    Pic_List_Info p = new Pic_List_Info();
                    p.setLarg_url(large_url_list);
                    p.setPosition(i);
                    p.setView(getChildAt(i));
                    pli.click(p);
                }
            }
        });
        return si;
    }
    private void layoutChildView()
    {
        int ChildCount;
        if(url_list!=null)
            ChildCount=url_list.size();
        else
            return;
        for(int i=0;i<ChildCount;i++)
        {
            int left,right,top,bottom;
            SketchImageView siv=(SketchImageView)getChildAt(i);
            if(siv==null)
                continue;
            if(ChildCount==1) {
                left=0;right=TotalWidth;top=0;bottom= OnceHeight;
            }
            else if(i<3)
            {
                left=(i)*(SingleWidth+gap);
                right=left+SingleWidth;
                top=(getRow(i))*(SingleWidth+gap);
                bottom=top+SingleWidth;
            }
            else if(i<6)
            {
                left=(i-3)*(SingleWidth+gap);
                right=left+SingleWidth;
                top=(getRow(i))*(SingleWidth+gap);
                bottom=top+SingleWidth;
            }
            else
            {
                left=(i-6)*(SingleWidth+gap);
                right=left+SingleWidth;
                top=(getRow(i))*(SingleWidth+gap);
                bottom=top+SingleWidth;
            }
            siv.layout(left,top,right,bottom);
        }
    }
    private int getRow(int i)
    {
        if(i<3)
            return 0;
        else if(i<6)
            return 1;
        else
            return 2;
    }

    public ArrayList<String> getLarge_url_list() {
        return large_url_list;
    }

    public interface PicClickListener {
        void click(Pic_List_Info pli);
    }

    private void calcLayout() {
        if(url_list!=null) {
            int length=url_list.size();
            if(length==0) {
                row=0;column=0;
            }
            else if (length <= 3) {
                row = 1;
                column = length;
            } else if (length <= 6) {
                row = 2;
                column = 3;
            } else {
                row = 3;
                column = 3;
            }
        }
    }
}
