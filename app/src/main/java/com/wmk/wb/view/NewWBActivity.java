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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import rx.Subscriber;

public class NewWBActivity extends AppCompatActivity {

    @BindView(R.id.tool_bar)
    Toolbar mToolbar;

    @BindView(R.id.send)
    Button send;

    @BindView(R.id.editText)
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_wb);
        ButterKnife.bind(this);

        setTitle("新微博");
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.mipmap.ic_arrow_back_white_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);
       // Intent intent=getIntent();

    }

    @OnClick(R.id.send )
    public void send()
    {
        if(send.getText()!=null)
        {
            Subscriber<ResponseBody> mSubscriber=new Subscriber<ResponseBody>() {
                @Override
                public void onCompleted() {
                    Toast.makeText(NewWBActivity.this,"发布成功",Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onError(Throwable e) {
                    Toast.makeText(NewWBActivity.this,"发布失败，请稍后重试",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNext(ResponseBody s) {
                }
            };
            DataManager.getInstance().createNew(mSubscriber, editText.getText().toString());
            //DataManager.getInstance().relay(mSubscriber, id, 0, URLEncoder.encode(editText.getText().toString(), "GBK"));


        }
    }
}
