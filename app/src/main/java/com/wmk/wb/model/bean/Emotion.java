package com.wmk.wb.model.bean;

import com.wmk.wb.R;
import com.wmk.wb.utils.MyHashMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wmk on 2017/6/8.
 */

public class Emotion {
    public static MyHashMap<String, Integer> emojiMap;
    public static ArrayList<String> emojiArray;

    static {
        emojiMap = new MyHashMap<String, Integer>(80);
        emojiArray = new ArrayList<>();
        emojiMap.put("[呵呵]", R.mipmap.d_hehe);
        emojiArray.add("[呵呵]");
        emojiMap.put("[嘻嘻]", R.mipmap.d_xixi);
        emojiArray.add("[嘻嘻]");
        emojiMap.put("[哈哈]", R.mipmap.d_haha);
        emojiArray.add("[哈哈]" );
        emojiMap.put("[爱你]", R.mipmap.d_aini);
        emojiArray.add("[爱你]" );
        emojiMap.put("[挖鼻屎]", R.mipmap.d_wabishi);
        emojiArray.add("[挖鼻屎]" );
        emojiMap.put("[吃惊]", R.mipmap.d_chijing);
        emojiArray.add( "[吃惊]");
        emojiMap.put("[晕]", R.mipmap.d_yun);
        emojiArray.add( "[晕]");
        emojiMap.put("[泪]", R.mipmap.d_lei);
        emojiArray.add("[泪]" );
        emojiMap.put("[馋嘴]", R.mipmap.d_chanzui);
        emojiArray.add("[馋嘴]" );
        emojiMap.put("[抓狂]", R.mipmap.d_zhuakuang);
        emojiArray.add("[抓狂]" );
        emojiMap.put("[哼]", R.mipmap.d_heng);
        emojiArray.add("[哼]" );
        emojiMap.put("[可爱]", R.mipmap.d_keai);
        emojiArray.add("[可爱]" );
        emojiMap.put("[怒]", R.mipmap.d_nu);
        emojiArray.add("[怒]" );
        emojiMap.put("[汗]", R.mipmap.d_han);
        emojiArray.add("[汗]" );
        emojiMap.put("[害羞]", R.mipmap.d_haixiu);
        emojiArray.add( "[害羞]");
        emojiMap.put("[睡觉]", R.mipmap.d_shuijiao);
        emojiArray.add("[睡觉]" );
        emojiMap.put("[钱]", R.mipmap.d_qian);
        emojiArray.add("[钱]" );
        emojiMap.put("[偷笑]", R.mipmap.d_touxiao);
        emojiArray.add("[偷笑]" );
        emojiMap.put("[笑cry]", R.mipmap.d_xiaoku);
        emojiArray.add("[笑cry]" );
        emojiMap.put("[doge]", R.mipmap.d_doge);
        emojiArray.add("[doge]" );
        emojiMap.put("[喵喵]", R.mipmap.d_miao);
        emojiArray.add("[喵喵]" );
        emojiMap.put("[酷]", R.mipmap.d_ku);
        emojiArray.add("[酷]" );
        emojiMap.put("[衰]", R.mipmap.d_shuai);
        emojiArray.add("[衰]" );
        emojiMap.put("[闭嘴]", R.mipmap.d_bizui);
        emojiArray.add("[闭嘴]" );
        emojiMap.put("[鄙视]", R.mipmap.d_bishi);
        emojiArray.add( "[鄙视]");
        emojiMap.put("[花心]", R.mipmap.d_huaxin);
        emojiArray.add("[花心]" );
        emojiMap.put("[鼓掌]", R.mipmap.d_guzhang);
        emojiArray.add("[鼓掌]" );
        emojiMap.put("[悲伤]", R.mipmap.d_beishang);
        emojiArray.add( "[悲伤]");
        emojiMap.put("[思考]", R.mipmap.d_sikao);
        emojiArray.add("[思考]" );
        emojiMap.put("[生病]", R.mipmap.d_shengbing);
        emojiArray.add("[生病]" );
        emojiMap.put("[亲亲]", R.mipmap.d_qinqin);
        emojiArray.add("[亲亲]" );
        emojiMap.put("[怒骂]", R.mipmap.d_numa);
        emojiArray.add("[怒骂]" );
        emojiMap.put("[太开心]", R.mipmap.d_taikaixin);
        emojiArray.add("[太开心]" );
        emojiMap.put("[懒得理你]", R.mipmap.d_landelini);
        emojiArray.add("[懒得理你]" );
        emojiMap.put("[右哼哼]", R.mipmap.d_youhengheng);
        emojiArray.add("[右哼哼]" );
        emojiMap.put("[左哼哼]", R.mipmap.d_zuohengheng);
        emojiArray.add("[左哼哼]" );
        emojiMap.put("[嘘]", R.mipmap.d_xu);
        emojiArray.add("[嘘]" );
        emojiMap.put("[委屈]", R.mipmap.d_weiqu);
        emojiArray.add( "[委屈]");
        emojiMap.put("[吐]", R.mipmap.d_tu);
        emojiArray.add("[吐]" );
        emojiMap.put("[可怜]", R.mipmap.d_kelian);
        emojiArray.add("[可怜]" );
        emojiMap.put("[打哈气]", R.mipmap.d_dahaqi);
        emojiArray.add("[打哈气]" );
        emojiMap.put("[挤眼]", R.mipmap.d_jiyan);
        emojiArray.add("[挤眼]" );
        emojiMap.put("[失望]", R.mipmap.d_shiwang);
        emojiArray.add( "[失望]");
        emojiMap.put("[顶]", R.mipmap.d_ding);
        emojiArray.add("[顶]" );
        emojiMap.put("[疑问]", R.mipmap.d_yiwen);
        emojiArray.add( "[疑问]");
        emojiMap.put("[困]", R.mipmap.d_kun);
        emojiArray.add("[困]" );
        emojiMap.put("[感冒]", R.mipmap.d_ganmao);
        emojiArray.add("[感冒]" );
        emojiMap.put("[拜拜]", R.mipmap.d_baibai);
        emojiArray.add("[拜拜]" );
        emojiMap.put("[黑线]", R.mipmap.d_heixian);
        emojiArray.add("[黑线]" );
        emojiMap.put("[阴险]", R.mipmap.d_yinxian);
        emojiArray.add("[阴险]" );
        emojiMap.put("[打脸]", R.mipmap.d_dalian);
        emojiArray.add("[打脸]" );
        emojiMap.put("[傻眼]", R.mipmap.d_shayan);
        emojiArray.add( "[傻眼]");
        emojiMap.put("[猪头]", R.mipmap.d_zhutou);
        emojiArray.add("[猪头]" );
        emojiMap.put("[熊猫]", R.mipmap.d_xiongmao);
        emojiArray.add( "[熊猫]");
        emojiMap.put("[兔子]", R.mipmap.d_tuzi);
        emojiArray.add("[兔子]" );
    }

    public static int getImgByName(String imgName) {
        Integer integer = emojiMap.get(imgName);
        return integer == null ? -1 : integer;
    }
}
