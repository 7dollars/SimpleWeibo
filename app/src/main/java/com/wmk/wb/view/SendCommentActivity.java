package com.wmk.wb.view;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wmk.wb.R;
import com.wmk.wb.presenter.DataManager;

import java.net.URLEncoder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import rx.Subscriber;

/////////////////////////////////////////
//这部分暂时没用。。。
//发送评论的时候，总返回HTTP400，说参数不全
//手动提交POST，也返回参数不全
//无解。。。就先把评论功能屏蔽吧
/////////////////////////////////////////
public class SendCommentActivity extends AppCompatActivity {

    @BindView(R.id.tool_bar)
    Toolbar mToolbar;

    @BindView(R.id.send)
    Button send;

    @BindView(R.id.editText)
    EditText editText;

    private boolean clickflag=false;
    private long id=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_comment);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu_white_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);
        Intent intent=getIntent();
        id=intent.getLongExtra("id",0);
    }
    @OnClick(R.id.send ) //给 button1 设置一个点击事件
    public void send()
    {
        if(send.getText()!=null)
        {
            Subscriber<ResponseBody> mSubscriber=new Subscriber<ResponseBody>() {
                @Override
                public void onCompleted() {
                    Toast.makeText(SendCommentActivity.this,"!!!",Toast.LENGTH_SHORT);
                }

                @Override
                public void onError(Throwable e) {
                    Toast.makeText(SendCommentActivity.this,"!!!",Toast.LENGTH_SHORT);
                }

                @Override
                public void onNext(ResponseBody s) {
                    Toast.makeText(SendCommentActivity.this,"!!!",Toast.LENGTH_SHORT);
                }
            };
            try {
                DataManager.getInstance().setComments(mSubscriber, id, 0, URLEncoder.encode(editText.getText().toString(), "GBK"));
            }
            catch (Exception ex)
            {
                Toast.makeText(SendCommentActivity.this,"!!!",Toast.LENGTH_SHORT);
            }
        }
    }

}
