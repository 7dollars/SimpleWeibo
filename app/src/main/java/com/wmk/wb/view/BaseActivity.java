package com.wmk.wb.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.wmk.wb.presenter.BasePresenter;
import com.wmk.wb.presenter.adapter.MainListAdapter;

/**
 * Created by wmk on 2017/8/16.
 */

public abstract class BaseActivity extends AppCompatActivity {
    private BasePresenter instance;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance=new BasePresenter();

        instance.initDataStack();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        instance.cleanData();
    }
}
