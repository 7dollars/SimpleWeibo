package com.wmk.wb.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wmk.wb.R;
import com.wmk.wb.presenter.adapter.PicPagerAdapter;
import com.wmk.wb.view.Interface.IImage;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.xiaopan.sketch.Sketch;
import me.xiaopan.sketch.SketchImageView;
import me.xiaopan.sketch.drawable.ImageAttrs;
import me.xiaopan.sketch.request.CancelCause;
import me.xiaopan.sketch.request.DisplayListener;
import me.xiaopan.sketch.request.DisplayOptions;
import me.xiaopan.sketch.request.DownloadProgressListener;
import me.xiaopan.sketch.request.ErrorCause;
import me.xiaopan.sketch.request.ImageFrom;
import me.xiaopan.sketch.request.MaxSize;
import uk.co.senab.photoview.PhotoView;

public class ImageActivity extends AppCompatActivity implements IImage {

    @BindView(R.id.viewpager)
    ViewPager pager;

    private ArrayList<String> pic_list;
    private int position;
    private DisplayOptions displayOptions = new DisplayOptions();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        ButterKnife.bind(this);

        //loading.setVisibility(View.VISIBLE);
        Intent intent=getIntent();
        pic_list=intent.getStringArrayListExtra("Largeurl");
        position=intent.getIntExtra("position",0);

        pager.setAdapter(new PicPagerAdapter(pic_list.size(),this,this));
        pager.setCurrentItem(position,false);

      //  Glide.with(this).load(getIntent().getStringExtra("Largeurl")).into(img);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void loadPic(int position, SketchImageView img,final TextView loading) {
     /*   Glide.with(ImageActivity.this)
                .load(pic_list.get(position))
                .crossFade()
                .into(img);*/
     img.setDisplayListener(new DisplayListener() {
         @Override
         public void onCompleted(Drawable drawable, ImageFrom imageFrom, ImageAttrs imageAttrs) {
             loading.setVisibility(View.INVISIBLE);
         }

         @Override
         public void onStarted() {
             loading.setVisibility(View.VISIBLE);
         }

         @Override
         public void onError(ErrorCause errorCause) {
         }

         @Override
         public void onCanceled(CancelCause cancelCause) {
             loading.setVisibility(View.INVISIBLE);
         }
     });
     img.setDownloadProgressListener(new DownloadProgressListener() {
         @Override
         public void onUpdateDownloadProgress(int i, int i1) {
             loading.setText(String.valueOf((i1*100)/i)+"%");
         }
     });
        //img.getOptions().setDecodeGifImage(true);
        displayOptions.setMaxSize(new MaxSize(9999999,999999999));
        displayOptions.setDecodeGifImage(true);
        Sketch.with(ImageActivity.this)
                .display(pic_list.get(position), img)
                .options(displayOptions)
                .commit();
        }
    }

