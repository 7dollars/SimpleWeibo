package com.wmk.wb.presenter;

import com.wmk.wb.model.DataManager;
import com.wmk.wb.model.StaticData;
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

    public void getComments(final long max_id)
    {
        Subscriber<CommentsData> mSubscribe;

        mSubscribe=new Subscriber<CommentsData>() {
            @Override
            public void onCompleted() {
                instance.notifyListChange();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(CommentsData commentsdata) {
                FinalCommentsData fdata;
                List<FinalCommentsData> data=new ArrayList<>();
                if(max_id==0)
                {
                    StaticData.getInstance().cdata.clear();
                }
                for (int i = 0; i < commentsdata.comments.size(); i++) {
                    if(i==0&&max_id!=0)//防止刷新时候有一条重复
                        i=1;
                    fdata=new FinalCommentsData();
                    fdata.setHeadurl(commentsdata.comments.get(i).user.getAvatar_large())
                            .setName(commentsdata.comments.get(i).user.getName())
                            .setText(commentsdata.comments.get(i).text)
                            .setTime(ConvertDate.calcDate(commentsdata.comments.get(i).created_at))
                            .setId(commentsdata.comments.get(i).id)
                            .setSource(commentsdata.comments.get(i).source)
                            .setWbId(id);
                    data.add(fdata);
                }
                StaticData.getInstance().cdata.addAll(data);
            }
        };
        DataManager.getInstance().getComments(mSubscribe,id,max_id,null);
    }
}
