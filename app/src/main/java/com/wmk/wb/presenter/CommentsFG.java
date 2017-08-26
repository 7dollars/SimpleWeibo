package com.wmk.wb.presenter;

import android.content.Context;

import com.wmk.wb.model.DataManager;
import com.wmk.wb.model.StaticData;
import com.wmk.wb.model.WbDataStack;
import com.wmk.wb.model.bean.FinalCommentsData;
import com.wmk.wb.model.bean.retjson.CommentsData;
import com.wmk.wb.utils.ConvertDate;
import com.wmk.wb.view.Interface.ICommentsFG;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * Created by wmk on 2017/6/7.
 */

public class CommentsFG {
    private ICommentsFG instance;
    long id;
    public CommentsFG(ICommentsFG instance,long id) {
        this.instance=instance;
        this.id=id;
    }

    public void getComments(Context context,final long max_id)
    {
        Subscriber<FinalCommentsData> mSubscribe;

        mSubscribe=new Subscriber<FinalCommentsData>() {
            List<FinalCommentsData> data = new ArrayList<>();
            @Override
            public void onCompleted() {
                WbDataStack.getInstance().getCommentsTop().setData(data);
                instance.notifyListChange();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(FinalCommentsData commentsdata) {
                data.add(commentsdata);
            }
        };
        DataManager.getInstance().getComments(context,mSubscribe,id,max_id);
    }
}
