package com.wmk.wb.model;

import android.util.Log;

import com.wmk.wb.model.bean.FinalCommentsData;
import com.wmk.wb.model.bean.FinalViewData;
import com.wmk.wb.model.bean.WbCommentsStackBean;
import com.wmk.wb.model.bean.WbStackBean;
import com.wmk.wb.model.bean.retjson.WbData;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by wmk on 2017/8/16.
 * 微博数据栈
 */

public class WbDataStack {
    private List<WbStackBean> DataStack=new LinkedList<>();
    private List<WbCommentsStackBean> CommentsStack=new LinkedList<>();

    /**
     * 取顶部元素
     * @return
     */
    public WbStackBean getTop() {
        return DataStack.get(0);
    }

    /**
     * 压入空栈顶
     */
    public void pushNew() {
        DataStack.add(0,new WbStackBean());
        Log.v("stack","加入，剩余数量："+DataStack.size());
    }
    /**
     * 弹出栈顶
     */
    public void popTop() {
        DataStack.remove(0);
        Log.v("stack","移除，剩余数量："+DataStack.size());
    }

    public WbCommentsStackBean getCommentsTop() {
        return CommentsStack.get(0);
    }

    public void pushNewComments() {
        CommentsStack.add(0,new WbCommentsStackBean());
        Log.v("stack","加入，剩余数量："+DataStack.size());
    }
    public void popCommentsTop() {
        CommentsStack.remove(0);
        Log.v("stack","移除，剩余数量："+DataStack.size());
    }
    public List<WbStackBean> getDataStack() {
        return DataStack;
    }
    private static class SingletonHolder{
        public static final WbDataStack instance=new WbDataStack();
    }
    public static WbDataStack getInstance()
    {return SingletonHolder.instance;}
}
