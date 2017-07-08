package com.wmk.wb.view.Interface;

import android.widget.ImageView;
import android.widget.TextView;

import me.xiaopan.sketch.SketchImageView;
import uk.co.senab.photoview.PhotoView;

/**
 * Created by wmk on 2017/6/7.
 */

public interface IImage {
    void loadPic(int position, SketchImageView img, final TextView loading);
}
