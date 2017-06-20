package com.wmk.wb.presenter.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wmk.wb.view.Interface.IImage;

import me.xiaopan.sketch.SketchImageView;
import uk.co.senab.photoview.PhotoView;

/**
 * Created by wmk on 2017/6/7.
 */

public class PicPagerAdapter extends PagerAdapter {
    private int size;
    private Context context;
    private IImage instance;
    public PicPagerAdapter(int size,Context context,IImage instance) {
        this.size=size;
        this.context=context;
        this.instance=instance;
    }

    @Override
    public int getCount() {
        return size;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        SketchImageView imageView = new SketchImageView(context);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        imageView .setLayoutParams(layoutParams);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageView.setZoomEnabled(true);
      //  imageView.setMaximumScale(10);
        container.addView(imageView);
        // imageView.setBackgroundColor(R.color.colorPrimaryDark);
        instance.loadPic(position,imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ImageView imageView = (ImageView) object;
        if (imageView == null)
            return;
        Glide.clear(imageView);     //核心，解决OOM
        ((ViewPager) container).removeView(imageView);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
}
