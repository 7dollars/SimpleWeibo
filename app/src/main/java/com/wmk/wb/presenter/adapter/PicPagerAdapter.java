package com.wmk.wb.presenter.adapter;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wmk.wb.R;
import com.wmk.wb.view.Interface.IImage;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;

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
    private void SaveImageToSysAlbum(SketchImageView img) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ) {
            BitmapDrawable bmpDrawable = (BitmapDrawable)img.getDrawable();
            Bitmap bmp = bmpDrawable.getBitmap();
            if (bmp != null) {
                try {
                    String time=String.valueOf(System.currentTimeMillis());
                    File dir=new File(Environment.getExternalStorageDirectory()+"/Pictures/Wb/"),cachefile;
                    dir.mkdir();
                    cachefile=new File(dir,time+".png");
                    cachefile.createNewFile();
                    FileOutputStream fos=new FileOutputStream(cachefile);
                    bmp.compress(Bitmap.CompressFormat.PNG, 90, fos);
                    fos.flush();
                    fos.close();
                    Toast.makeText(context, "图片保存在"+cachefile.toString(), Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else {
                Toast.makeText(context, "图片获取失败", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(context, "内部存储获取失败", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public Object instantiateItem(final ViewGroup container, int position) {
        View v=View.inflate(context,R.layout.pic_pager,null);
        final SketchImageView imageView=(SketchImageView) v.findViewById(R.id.imageView7);
        TextView loading=(TextView)v.findViewById(R.id.textView2);
        imageView.setZoomEnabled(true);
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog
                        .Builder(container.getContext())
                        .setTitle("保存图片?")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("保存", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SaveImageToSysAlbum(imageView);
                            }
                        })
                        .show();
                return false;
            }
        });
      /*  SketchImageView imageView = new SketchImageView(context);
        TextView loading=new TextView(context);
        loading.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        imageView .setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
  //      loading.setGravity(Gravity.CENTER);
        loading.setText("0%");
        loading.setTextColor(ContextCompat.getColor(context,R.color.md_white_1000));
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageView.setZoomEnabled(true);
      //  imageView.setMaximumScale(10);
        container.addView(loading);
        loading.setVisibility(View.VISIBLE);
    //    container.addView(imageView);
        // imageView.setBackgroundColor(R.color.colorPrimaryDark);*/
        container.addView(v);
        instance.loadPic(position,imageView,loading);
        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View v = (View) object;
        SketchImageView imageView=(SketchImageView) v.findViewById(R.id.imageView7);
        if (imageView == null)
            return;
        Glide.clear(imageView);     //核心，解决OOM
        ((ViewPager) container).removeView(v);

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
}
