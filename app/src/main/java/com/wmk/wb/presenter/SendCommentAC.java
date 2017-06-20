package com.wmk.wb.presenter;

import com.wmk.wb.model.DataManager;
import com.wmk.wb.model.StaticData;
import com.wmk.wb.view.Interface.ISendComment;

import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * Created by wmk on 2017/6/7.
 */

public class SendCommentAC  extends BasePresenter{
    private ISendComment instance;
    public SendCommentAC(ISendComment instance) {
        this.instance=instance;
    }

    public void send(int flag, long id, long commentid, String text,int position,boolean isRet)
    {
        if(text!=null)
        {
            final int temp=flag;
            Subscriber<ResponseBody> mSubscriber=new Subscriber<ResponseBody>() {
                @Override
                public void onCompleted() {
                 //   ISendComment iSendComment=new SendCommentActivity();
                    switch(temp)
                    {
                        case 0:
                        {
                            instance.showToast("转发成功",true);
                            break;
                        }
                        case 1:
                        {
                            instance.showToast(" 评论成功",true);
                            break;
                        }
                        case 2:
                        {
                            instance.showToast(" 评论成功",true);
                            break;
                        }
                    }

                }

                @Override
                public void onError(Throwable e) {
                 //   ISendComment iSendComment=new SendCommentActivity();
                    switch(temp)
                    {
                        case 0:
                        {
                            instance.showToast("转发失败，请稍后再试",false);
                            break;
                        }
                        case 1:
                        {
                            instance.showToast("评论失败，请稍后再试",false);
                            break;
                        }
                        case 2:
                        {
                            instance.showToast("评论失败，请稍后再试",false);
                            break;
                        }
                    }
                }

                @Override
                public void onNext(ResponseBody s) {
                }
            };

            switch(flag) {
                case 0: {
                    if(!isRet) {
                        if (StaticData.getInstance().getData().get(position).getText() != null && StaticData.getInstance().getData().get(position).getRet_text() != null)
                            DataManager.getInstance().relay(mSubscriber, id, text.toString() + "//@" + StaticData.getInstance().getData().get(position).getName() +
                                    ":" + StaticData.getInstance().getData().get(position).getText());
                    }
                    else
                        DataManager.getInstance().relay(mSubscriber, id, text.toString());
                    break;
                }
                case 1:{
                    DataManager.getInstance().setComments(mSubscriber,id,0,text.toString());
                    break;
                }
                case 2:{
                    DataManager.getInstance().setComments(mSubscriber,id,commentid,text.toString());
                    break;
                }
            }
        }
    }
}
