package com.wmk.wb.view;


import android.app.Instrumentation;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.stylingandroid.prism.Prism;
import com.wmk.wb.R;

import com.wmk.wb.presenter.DetialAC;
import com.wmk.wb.utils.ColorThemeUtils;
import com.wmk.wb.view.Interface.IDetial;
import com.wmk.wb.view.fragment.CommentsFragment;
import com.wmk.wb.view.fragment.DetialFragment;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DetialActivity extends AppCompatActivity implements DetialFragment.OnFragmentInteractionListener,IDetial {

    @BindView(R.id.tool_bar)
    Toolbar mToolbar;

    private long id;
    private DetialAC instance;
    private Prism prism;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detial);

        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.mipmap.ic_arrow_back_white_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);
        instance=new DetialAC();

        setTitle("微博正文");

        prism = Prism.Builder.newInstance()
                .background(mToolbar)
                .background(getWindow())
                .build();

        Intent intent = getIntent();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.detial_frag1, DetialFragment.newInstance(intent.getIntExtra("position", 0), intent.getBooleanExtra("isRet", false), intent.getBooleanExtra("hasChild", false)))
                .commit();
        if (intent.getBooleanExtra("isRet", false)) {
            id=instance.getData(intent.getIntExtra("position", 0)).getRet_id();
        }
        else {
            id=instance.getData(intent.getIntExtra("position", 0)).getId();
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.detial_list1, CommentsFragment.newInstance(0, id))
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detial_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case android.R.id.home:
            {
                this.onBackPressed();
                break;
            }
            case R.id.copy:
            {
                Intent intent=getIntent();
                ClipboardManager cm=(ClipboardManager) getSystemService(this.CLIPBOARD_SERVICE);
                if(intent.getBooleanExtra("isRet", false)) {
                    cm.setPrimaryClip(ClipData.newPlainText("wb",instance.getData(intent.getIntExtra("position", 0)).getRet_text()));
                }
                else {
                    cm.setPrimaryClip(ClipData.newPlainText("wb",instance.getData(intent.getIntExtra("position", 0)).getText()));
                }
                Toast.makeText(this,"复制完成",Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.send:
            {
                Intent intent=getIntent();
                Intent intent1=new Intent();
                intent1.putExtra("position",intent.getIntExtra("position", 0));
                intent1.putExtra("isRet",intent.getBooleanExtra("isRet", false));
                intent1.putExtra("id",id);
                intent1.putExtra("sendflag",0);//0为转发
                intent1.setClass(this,SendCommentActivity.class);
                startActivity(intent1);
                break;
            }
            case R.id.comment:
            {
                Intent intent=getIntent();
                Intent intent1=new Intent();
                intent1.putExtra("id",id);
                intent1.putExtra("isRet",intent.getBooleanExtra("isRet", false));
                intent1.putExtra("sendflag",1);//0为转发
                intent1.setClass(this,SendCommentActivity.class);
                startActivity(intent1);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    @Override
    protected void onDestroy() {
        instance=null;
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        prism.setColor(getResources().getColor(ColorThemeUtils.getColor(instance.getThemeColor())));
        super.onResume();
    }

    @Override
    public void showToast(String text) {

    }
}
