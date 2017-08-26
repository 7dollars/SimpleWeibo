package com.wmk.wb.presenter.adapter;

import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.wmk.wb.R;
import com.wmk.wb.model.bean.EmojiBus;
import com.wmk.wb.model.bean.Emotion;

import java.util.ArrayList;
import java.util.HashMap;
import com.wmk.wb.utils.MyHashMap;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by wmk on 2017/7/29.
 */

public class EmojiPagerAdapter extends PagerAdapter {
    @Override
    public int getCount() {
        int size=Emotion.emojiMap.size();
        return size%18==0?size/18:size/18+1;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //View v=View.inflate(container.getContext(), R.layout.pic_pager,null);
        SimpleAdapter gridAdapter;
        GridView gv;
        ArrayList<HashMap<String,Object>> emojilist;

        View v=View.inflate(container.getContext(),R.layout.emoji_pager_child,null);
        gv=(GridView)v.findViewById(R.id.emojiGrid);
        emojilist=new ArrayList<>();

        for(int i=position*18;i<(position+1)*18;i++) {
            if(i>=Emotion.emojiArray.size())
                break;
            HashMap<String, Object> item = new HashMap<>();
            item.put("image",Emotion.emojiMap.getValueArray().get(i));
            emojilist.add(item);
        }
        gridAdapter=new SimpleAdapter(container.getContext(),emojilist,R.layout.emoji_grid,new String[] {"image"}, new int[] {R.id.emojiImage});
        gv.setAdapter(gridAdapter);
        final int posi=position;
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EmojiBus eb=new EmojiBus(Emotion.getImgByName(Emotion.emojiArray.get(posi*18+position)),Emotion.emojiArray.get(posi*18+position));
                EventBus.getDefault().post(eb);
            }
        });
        container.addView(v);

        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View v = (View) object;
        container.removeView(v);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
}
