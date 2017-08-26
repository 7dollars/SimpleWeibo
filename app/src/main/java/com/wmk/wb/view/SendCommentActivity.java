package com.wmk.wb.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.stylingandroid.prism.Prism;
import com.wmk.wb.R;
import com.wmk.wb.model.bean.EmojiBus;
import com.wmk.wb.presenter.SendCommentAC;
import com.wmk.wb.utils.ColorThemeUtils;
import com.wmk.wb.utils.TextUtils;
import com.wmk.wb.view.Interface.ISendComment;
import com.wmk.wb.view.fragment.EmojiFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SendCommentActivity extends AppCompatActivity implements ISendComment,EmojiFragment.OnFragmentInteractionListener {

    @BindView(R.id.tool_bar)
    Toolbar mToolbar;

    @BindView(R.id.send)
    Button send;

    @BindView(R.id.editText)
    EditText editText;

    @BindView(R.id.emojibtn)
    ImageView emojibtn;

    @BindView(R.id.bottom_toolbar)
    LinearLayout btb;

    private boolean clickflag=false;
    private long id=0;
    private Prism prism;
    private int sendflag=0;
    private long commentid=0;
    SendCommentAC instance;
    private int position;
    private boolean isRet;
    private boolean isEmojiShow=false;
    private EmojiFragment ef;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home: {
                this.onBackPressed();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_comment);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.mipmap.ic_arrow_back_white_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);
        Intent intent=getIntent();
        id=intent.getLongExtra("id",0);
        sendflag=intent.getIntExtra("sendflag",0);
        commentid=intent.getLongExtra("commentid",0);
        position=intent.getIntExtra("position",0);
        isRet=intent.getBooleanExtra("isRet",false);
        instance=new SendCommentAC(this);
        ef=new EmojiFragment();

        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(isEmojiShow==true) {
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction tran = fm.beginTransaction();
                    tran.replace(R.id.rep_frag,new Fragment());
                    isEmojiShow=false;
                    tran.commit();
                }
                return false;
            }
        });
        emojibtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //     InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                //     imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction tran = fm.beginTransaction();
                if(isEmojiShow) {
                    tran.replace(R.id.rep_frag,new Fragment());
                    setKeyboardState(true,editText);
                    isEmojiShow=false;
                }
                else {
                    tran.replace(R.id.rep_frag,ef,"emoji");
                    setKeyboardState(false,editText);
                    isEmojiShow=true;
                }
                tran.commit();
            }
        });
        prism = Prism.Builder.newInstance()
                .background(mToolbar)
                .background(btb)
                .background(getWindow())
                .build();
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
            instance.send(sendflag,id,commentid,editText.getText().toString(),position,isRet);
        }
    }
    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        instance=null;
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        prism.setColor(getResources().getColor(ColorThemeUtils.getColor(instance.getThemeColor())));
        super.onResume();
    }

    @Override
    public void showToast(String text, boolean isExit) {
        Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT).show();

        if(isExit)
            finish();
    }
    private void setKeyboardState(boolean isShow ,View v) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if(isShow)
            imm.showSoftInput(v,InputMethodManager.SHOW_FORCED);
        else
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    @Subscribe
    public void onEventMainThread(EmojiBus event) {
        Log.e("123",String.valueOf(event.getEmoji()));
        TextUtils.appendImage(this,event.getEmojiName(),event.getEmoji(),editText);
    }
}
