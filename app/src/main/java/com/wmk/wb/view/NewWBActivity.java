package com.wmk.wb.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.stylingandroid.prism.Prism;
import com.wmk.wb.R;
import com.wmk.wb.model.StaticData;
import com.wmk.wb.model.bean.EmojiBus;
import com.wmk.wb.model.bean.Emotion;
import com.wmk.wb.model.bean.LoadingBus;
import com.wmk.wb.model.bean.LocationBean;
import com.wmk.wb.presenter.NewwbAC;
import com.wmk.wb.utils.ColorThemeUtils;
import com.wmk.wb.utils.TextUtils;
import com.wmk.wb.view.Interface.INewWB;
import com.wmk.wb.view.fragment.EmojiFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewWBActivity extends AppCompatActivity implements INewWB, EmojiFragment.OnFragmentInteractionListener{

    @BindView(R.id.tool_bar)
    Toolbar mToolbar;

    @BindView(R.id.send)
    Button send;

    @BindView(R.id.emojibtn)
    ImageView emojibtn;

    @BindView(R.id.editText)
    EditText editText;

    @BindView(R.id.bottom_toolbar)
    LinearLayout btb;

    private boolean isEmojiShow=false;
    private  EmojiFragment ef;
    private Prism prism;
    private NewwbAC instance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_wb);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        setTitle("新微博");
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.mipmap.ic_arrow_back_white_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);

        ef=new EmojiFragment();
        instance=new NewwbAC(this);
        prism = Prism.Builder.newInstance()
                .background(mToolbar)
                .background(btb)
                .background(getWindow())
                .build();

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
    }

    @Override
    protected void onDestroy() {
        instance=null;
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

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

    @OnClick(R.id.send )
    public void send()
    {
        if(editText.getText()!=null)
        {
            instance.send(editText.getText().toString());
        }
    }

    @Override
    public void showToast(String text, boolean isExit) {
        Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT).show();
        if(isExit)
            finish();
    }
    @Override
    protected void onResume() {
        prism.setColor(getResources().getColor(ColorThemeUtils.getColor(instance.getThemeColor())));
        super.onResume();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void setKeyboardState(boolean isShow ,View v) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if(isShow)
            imm.showSoftInput(v,InputMethodManager.SHOW_FORCED);
        else
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
    @Subscribe
    public void onEventMainThread(EmojiBus event) {
        Log.e("123",String.valueOf(event.getEmoji()));
        TextUtils.appendImage(this,event.getEmojiName(),event.getEmoji(),editText);
    }

}
