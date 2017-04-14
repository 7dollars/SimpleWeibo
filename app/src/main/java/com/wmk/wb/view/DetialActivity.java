package com.wmk.wb.view;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.wmk.wb.R;

import com.wmk.wb.model.entity.StaticData;
import com.wmk.wb.view.fragment.CommentsFragment;
import com.wmk.wb.view.fragment.DetialFragment;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DetialActivity extends AppCompatActivity implements DetialFragment.OnFragmentInteractionListener {

    @BindView(R.id.tool_bar)
    Toolbar mToolbar;

    private long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detial);

        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.mipmap.ic_arrow_back_white_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);

        setTitle("");

        Intent intent = getIntent();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.detial_frag1, DetialFragment.newInstance(intent.getIntExtra("position", 0), intent.getBooleanExtra("isRet", false), intent.getBooleanExtra("hasChild", false)))
                .commit();
        if (intent.getBooleanExtra("isRet", false)) {
            id=StaticData.getInstance().data.get(intent.getIntExtra("position", 0)).getRet_id();
        }
        else {
            id=StaticData.getInstance().data.get(intent.getIntExtra("position", 0)).getId();
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
            case R.id.copy:
            {
                Intent intent=getIntent();
                ClipboardManager cm=(ClipboardManager) getSystemService(this.CLIPBOARD_SERVICE);
                if(intent.getBooleanExtra("isRet", false)) {
                    cm.setPrimaryClip(ClipData.newPlainText("wb",StaticData.getInstance().getData().get(intent.getIntExtra("position", 0)).getRet_text()));
                }
                else {
                    cm.setPrimaryClip(ClipData.newPlainText("wb",StaticData.getInstance().getData().get(intent.getIntExtra("position", 0)).getText()));
                }
                Toast.makeText(this,"复制完成",Toast.LENGTH_SHORT).show();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
