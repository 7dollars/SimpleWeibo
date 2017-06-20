package com.wmk.wb.view;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.stylingandroid.prism.Prism;
import com.wmk.wb.R;
import com.wmk.wb.presenter.NewwbAC;
import com.wmk.wb.view.Interface.INewWB;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewWBActivity extends AppCompatActivity implements INewWB {

    @BindView(R.id.tool_bar)
    Toolbar mToolbar;

    @BindView(R.id.send)
    Button send;

    @BindView(R.id.editText)
    EditText editText;
    private Prism prism;
    private NewwbAC instance;
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

        instance=new NewwbAC(this);

        prism = Prism.Builder.newInstance()
                .background(mToolbar)
                .background(getWindow())
                .build();
    }

    @Override
    protected void onDestroy() {
        instance=null;
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
        prism.setColor(getResources().getColor(instance.getThemeColor()));
        super.onResume();
    }
}
