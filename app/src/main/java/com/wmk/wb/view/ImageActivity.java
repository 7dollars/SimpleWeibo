package com.wmk.wb.view;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.wmk.wb.R;
import com.wmk.wb.presenter.adapter.PicPagerAdapter;
import com.wmk.wb.view.Interface.IImage;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;

public class ImageActivity extends AppCompatActivity implements IImage {

    @BindView(R.id.viewpager)
    ViewPager pager;

    private ArrayList<String> pic_list;
    private int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        ButterKnife.bind(this);

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
    public void loadPic(int position, PhotoView img) {
        Glide.with(ImageActivity.this)
                .load(pic_list.get(position))
                .crossFade()
                .into(img);
        if(pic_list.size()>position+1) {
            Glide.with(ImageActivity.this)
                    .load(pic_list.get(position+1))
                    .crossFade();
        }
    }
}
