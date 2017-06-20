package com.wmk.wb.utils;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.Toast;

import com.wmk.wb.R;
import com.wmk.wb.model.bean.Emotion;
import com.wmk.wb.model.bean.NameEvent;
import com.wmk.wb.model.bean.TagEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wmk on 2017/6/8.
 */

public class TextUtils {
    public static SpannableString getWeiBoText(Context context, String text) {
        Resources res = context.getResources();

        Pattern EMOJI_PATTER = Pattern.compile("\\[([\u4e00-\u9fa5\\w])+\\]");
        Pattern AT_PATTERN = Pattern.compile("@[\\u4e00-\\u9fa5\\w\\-]+");
        Pattern Url_PATTERN = Pattern.compile("((http|https|ftp|ftps):\\/\\/)?([a-zA-Z0-9-]+\\.){1,5}(com|cn|net|org|hk|tw)((\\/(\\w|-)+(\\.([a-zA-Z]+))?)+)?(\\/)?(\\??([\\.%:a-zA-Z0-9_-]+=[#\\.%:a-zA-Z0-9_-]+(&amp;)?)+)?");
        Pattern TAG_PATTERN = Pattern.compile("#([^\\#|.]+)#");

        SpannableString spannable = new SpannableString(text);
        Matcher emoji = EMOJI_PATTER.matcher(spannable);
        while (emoji.find()) {
            String key = emoji.group(); // 获取匹配到的具体字符
            int start = emoji.start(); // 匹配字符串的开始位置
            Integer imgRes = Emotion.getImgByName(key);
            System.out.println("@@@"+imgRes);
            if (imgRes != -1) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeResource(res, imgRes, options);

                int scale = options.outWidth / 32;
                options.inJustDecodeBounds = false;
                options.inSampleSize = scale;
                Bitmap bitmap = BitmapFactory.decodeResource(res, imgRes, options);

                ImageSpan span = new ImageSpan(context, bitmap);
                spannable.setSpan(span, start, start + key.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }

        Matcher url = Url_PATTERN.matcher(spannable);
        while (url.find()) {
            String urlString = url.group();
            int start = url.start();
            spannable.setSpan(new MyURLSpan(context, urlString), start, start + urlString.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        }

        Matcher at = AT_PATTERN.matcher(spannable);
        while (at.find()) {
            String atUserName = at.group();
            int start = at.start();
            spannable.setSpan(new MyAtSpan(context, atUserName), start, start + atUserName.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        }

        Matcher tag=TAG_PATTERN.matcher((spannable));
        while (tag.find())
        {
            String topic=tag.group();
            int start = tag.start();
            spannable.setSpan(new MyTagSpan(context,topic),start,start+topic.length(),Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        return spannable;
    }
    private static class MyAtSpan extends ClickableSpan {
        private String mName;
        private Context context;

        MyAtSpan(Context ctx, String name) {
            context = ctx;
            mName = name;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(Color.parseColor("#3F51B5"));
            ds.setUnderlineText(false); //去掉下划线
        }

        @Override
        public void onClick(View widget) {
            if(mName.length()!=0) {
                mName = mName.substring(1);
                EventBus.getDefault().post(new NameEvent(mName));
            }
        }
    }

    private static class MyURLSpan extends ClickableSpan {
        private String mUrl;
        private Context context;

        MyURLSpan(Context ctx, String url) {
            context = ctx;
            mUrl = url;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(Color.parseColor("#3F51B5"));
        }

        @Override
        public void onClick(View widget) {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse(mUrl);
            intent.setData(content_url);
            context.startActivity(intent);
        }
    }
    private static class MyTagSpan extends ClickableSpan {
        private String mTag;
        private Context context;

        MyTagSpan(Context ctx, String tag) {
            context = ctx;
            mTag = tag;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(Color.parseColor("#3F51B5"));
            ds.setUnderlineText(false); //去掉下划线
        }

        @Override
        public void onClick(View widget) {
        //    String tag= mTag.substring(1,mTag.length()-1);
        //    EventBus.getDefault().post(new TagEvent(mTag));
        }
    }
}
