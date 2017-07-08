package com.wmk.wb.view;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.stylingandroid.prism.Prism;
import com.wmk.wb.R;
import com.wmk.wb.presenter.ColorSelectAC;
import com.wmk.wb.presenter.adapter.ColorSelectAdapter;
import com.wmk.wb.utils.ColorThemeUtils;
import com.wmk.wb.utils.SpUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ColorSelectActivity extends AppCompatActivity {

    private Prism prism;
    private ColorSelectAC instance;

    @BindView(R.id.grid)
    GridView grid;
    @BindView(R.id.tool_bar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_select);
        ButterKnife.bind(this);
        instance=new ColorSelectAC();

        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.mipmap.ic_arrow_back_white_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);

        setTitle("主题选择");

        prism = Prism.Builder.newInstance()
                .background(mToolbar)
                .background(getWindow())
                .build();
        prism.setColor(getResources().getColor(R.color.colorAccent));
     //   int color[]={R.color.colorAccent,R.color.primaryColor};
        ColorSelectAdapter csa=new ColorSelectAdapter();
        grid.setAdapter(csa);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SpUtil.putInt(ColorSelectActivity.this,"themecolor",position);
                instance.setTheme(position);
                prism.setColor(getResources().getColor(ColorThemeUtils.getColor(position)));
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        prism.setColor(getResources().getColor(ColorThemeUtils.getColor(instance.getThemeColor())));
        super.onResume();
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

}
