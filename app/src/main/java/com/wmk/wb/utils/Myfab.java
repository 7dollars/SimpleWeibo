package com.wmk.wb.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.wmk.wb.R;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Created by wmk on 2017/6/9.
 */

public class Myfab extends View {

    private boolean isShow=false;
    private int height=0;
    private int rect=0;
    private Region ClickRegion=new Region();
    private List<Integer> icon=new ArrayList();
    private MenuListener menuListener;
    private boolean downflag=false;

    private int color;

    public void setColor(int color) {
        this.color = color;
    }

    int xx;
    int yy;

    public Myfab(Context context) {
        super(context);
        if (!isClickable()) {
            setClickable(true);
        }
    }

    public Myfab(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        if (!isClickable()) {
            setClickable(true);
        }
    }

    public Myfab(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void collapse()
    {
        rect=0;
        invalidate();
    }
    public void setIcon(List<Integer> list)
    {
        this.icon=list;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean result=false;
        switch (event.getAction())
        {
            case MotionEvent.ACTION_UP:
            {
                int x = (int) event.getX();
                int y = (int) event.getY();
                result=TouchMethod(x, y,false);
                downflag=true;
                break;
            }
            case MotionEvent.ACTION_DOWN:
            {
                int x = (int) event.getX();
                int y = (int) event.getY();
                result=TouchMethod(x, y,true);
                break;
            }
        }
        if(result)
            return super.onTouchEvent(event);
        else
            return false;
    }

    private boolean TouchMethod(int x,int y ,boolean isDown)
    {
        if(y>getMeasuredHeight()-getMeasuredWidth()&&y<getMeasuredHeight()) {
            if(!isDown)
                startAnimation();
            return true;
        }
        else if(y>0&&y<getMeasuredHeight()-getMeasuredWidth()&&isShow)
        {
            if(!isDown) {
                for (int i = icon.size(); i > 0; i--) {
                    if (y > (i - 1) * getMeasuredWidth() && y < i * getMeasuredWidth()) {
                        if (menuListener != null)
                            menuListener.click(icon.size() - i + 1);
                    }
                }
            }
            return true;
        }
        else
            return false;
    }
    public void addheight(int height)
    {
        this.height=height;
    }
    @Override
    protected void onDraw(Canvas canvas) {
       // super.onDraw(canvas);
        int px=getMeasuredWidth()/2;
        int py=getMeasuredWidth()/2;
        Paint mPaint=new Paint();
        mPaint.setColor(color);       //设置画笔颜色
        mPaint.setStyle(Paint.Style.FILL);  //设置画笔模式为填充
        mPaint.setStrokeWidth(10f);//设置画笔宽度为10px
        mPaint.setAntiAlias(true);

        Path path=new Path();
        path.setFillType(Path.FillType.EVEN_ODD);
        canvas.translate(px, getMeasuredHeight()-py);

        canvas.drawArc(-px,-px,px,px,0,180,true,mPaint);
        if(rect<yy-xx) {
            canvas.drawArc(-px, -px - rect, px, -rect + px, 180, 180, true, mPaint);
            canvas.drawRect(-px, -rect, px, 0, mPaint);
        }
        else {
            canvas.drawArc(-px, -getMeasuredHeight() + py, px, -getMeasuredHeight() + 3 * px, 180, 180, true, mPaint);
            canvas.drawRect(-px, -getMeasuredHeight() + 2*py, px, 0, mPaint);
        }

        Bitmap bitmap= BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.ic_add_white_24dp);
        canvas.drawBitmap(bitmap,-bitmap.getWidth()/2,-bitmap.getHeight()/2,mPaint);

        for(int i=0;i<icon.size();i++)
        {
            if(rect>=2*px*(i+1))//2*px=getMeasuredWidth(),i+1=当前的图表个数(-y)
            {
                Bitmap bitmap1= BitmapFactory.decodeResource(getContext().getResources(),icon.get(i).intValue());
                canvas.drawBitmap(bitmap1,-bitmap.getWidth()/2,-bitmap.getHeight()/2-(i+1)*2*px,mPaint);
            }
        }

        if(rect>=yy-xx) {
            ClickRegion.setEmpty();
            ClickRegion.set(getLeft(),getTop(),getRight(),getBottom());
            clearAnimation();
            isShow=true;
        }
        else if(rect==0) {
            ClickRegion.setEmpty();
            ClickRegion.set(getLeft(),getTop(),getRight(),getBottom());
            isShow=false;
            clearAnimation();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width=0;
        int height=0;
        if(MeasureSpec.getMode(widthMeasureSpec)==MeasureSpec.EXACTLY)
            width=MeasureSpec.getSize(widthMeasureSpec);
        if(MeasureSpec.getMode(heightMeasureSpec)==MeasureSpec.EXACTLY)
            height=MeasureSpec.getSize(heightMeasureSpec);

        width=width>height?height:width;//取较小的
        height=height>width?width:height;

        height+=icon.size()*width;

        xx=width;
        yy=height;
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(width,height);
    }

    public void setMenuListener(MenuListener menuListener) {
        this.menuListener = menuListener;
    }

    private class ami extends Animation
    {

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            if(rect<yy-xx)
            {
                rect+=50;
                invalidate();
            }
        }

    }
    private class ami2 extends Animation
    {

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            if(rect>50)
            {
                rect-=50;
            }
            else
                rect=0;
            invalidate();
        }

    }
    public void startAnimation() {
        if(!isShow) {
            ami move = new ami();
            move.setDuration(5000);
            move.setInterpolator(new AccelerateDecelerateInterpolator());
            //move.setRepeatCount(Animation.INFINITE);
            //move.setRepeatMode(Animation.REVERSE);
            startAnimation(move);
        }
        else
        {
            ami2 move = new ami2();
            move.setDuration(5000);
            move.setInterpolator(new AccelerateDecelerateInterpolator());
            //move.setRepeatCount(Animation.INFINITE);
            //move.setRepeatMode(Animation.REVERSE);
            startAnimation(move);
        }
    }
    public interface MenuListener
    {
        void click(int i);
    }
}
