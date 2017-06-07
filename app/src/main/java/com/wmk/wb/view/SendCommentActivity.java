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
import com.wmk.wb.presenter.SendCommentAC;
import com.wmk.wb.view.Interface.ISendComment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SendCommentActivity extends AppCompatActivity implements ISendComment {

    @BindView(R.id.tool_bar)
    Toolbar mToolbar;

    @BindView(R.id.send)
    Button send;

    @BindView(R.id.editText)
    EditText editText;

    private boolean clickflag=false;
    private long id=0;

    private int sendflag=0;
    private long commentid=0;
    SendCommentAC instance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_comment);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.mipmap.ic_arrow_back_white_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);
        Intent intent=getIntent();
        id=intent.getLongExtra("id",0);
        sendflag=intent.getIntExtra("sendflag",0);
        commentid=intent.getLongExtra("commentid",0);
        instance=new SendCommentAC(this);

        if(sendflag==0)
            setTitle("转发");
        else
            setTitle("评论");
    }
    @OnClick(R.id.send ) //给 button1 设置一个点击事件
    public void send()
    {
        if(editText.getText()!=null)
        {
            instance.send(sendflag,id,commentid,editText.getText().toString());
        }
    }

    @Override
    public void showToast(String text, boolean isExit) {
        Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT).show();

        if(isExit)
            finish();
    }
}
