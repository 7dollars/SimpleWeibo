package com.wmk.wb.view;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.stylingandroid.prism.Prism;
import com.wmk.wb.R;
import com.wmk.wb.model.StaticData;
import com.wmk.wb.model.bean.DetialsInfo;
import com.wmk.wb.model.bean.LocationBean;
import com.wmk.wb.model.bean.Pic_List_Info;
import com.wmk.wb.presenter.ExploreAC;
import com.wmk.wb.presenter.adapter.ExplorerAdapter;
import com.wmk.wb.presenter.adapter.MainListAdapter;
import com.wmk.wb.utils.EndlessRecyclerOnScrollListener;
import com.wmk.wb.view.Interface.IExplore;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;

public class ExploreActivity extends AppCompatActivity implements IExplore ,View.OnClickListener {

    @BindView(R.id.zb)
    LinearLayout zb;

    @BindView(R.id.sb)
    LinearLayout sb;

    @BindView(R.id.address)
    TextView address;

    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;

    @BindView(R.id.content_list)
    RecyclerView content_list;

    @BindView(R.id.tool_bar)
    Toolbar mToolbar;

    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;

    private ExplorerAdapter explorerAdapter;
    private ExploreAC instance;
    private int oldflag=1;
    private Prism prism;
    private int refreshflag=0;


    public ExploreActivity() {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);
        ButterKnife.bind(this);
        instance=new ExploreAC(this);
        oldflag=StaticData.getInstance().getWbFlag();
        content_list.setNestedScrollingEnabled(false);
        prism = Prism.Builder.newInstance()
                .background(mToolbar)
                .background(getWindow())
                .build();
        prism.setColor(getResources().getColor(instance.getThemeColor()));

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if(StaticData.getInstance().Expdata.size()!=0)
                    instance.RefreshOnly(0);           //仅刷新不获取位置
                else {
                    if(refreshflag==0) {
                        instance.initLocation(getApplicationContext());
                        instance.getWbData(0);         //刷新周围微博
                    }
                    else
                    {
                        instance.initGeocode(getApplicationContext());
                        instance.getRandomData(0);     //刷新随便看看
                    }
                }
            }
        });

        Subscriber<DetialsInfo> mSubscriber=instance.getDetialsSubscriber();
        Subscriber<Pic_List_Info> mSubscriber2=instance.getPicSubscriber();
        explorerAdapter=new ExplorerAdapter(this,null,mSubscriber,mSubscriber2);

        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.mipmap.ic_arrow_back_white_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setTitle("探索");

        LinearLayoutManager manager = new LinearLayoutManager(content_list.getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        content_list.setLayoutManager(manager);
        content_list.setAdapter(explorerAdapter);
        zb.setOnClickListener(this);
        sb.setOnClickListener(this);
        mToolbar.setOnClickListener(this);

    }

    @Override
    protected void onDestroy() {
        StaticData.getInstance().setWbFlag(oldflag);
        StaticData.getInstance().setExpflag(false);
        instance.destroyLocation();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        StaticData.getInstance().setExpflag(true);
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id)
        {
            case R.id.zb://周边内容
            {
                instance.initLocation(getApplicationContext());
                instance.getWbData(0);
                break;
            }
            case R.id.sb://随便走走
            {
                instance.initGeocode(getApplicationContext());
                instance.getRandomData(0);
                break;
            }
            case R.id.tool_bar:
            {
                nestedScrollView.scrollTo(0,0);
                break;
            }
        }
    }

    @Override
    public void setRefresh(boolean refresh, boolean isScrollToTop) {
        swipe.setRefreshing(refresh);
        if(isScrollToTop)
        {
            content_list.scrollToPosition(0);
            nestedScrollView.scrollTo(0,0);
        }
    }

    @Override
    public void setAddress(String addr) {
        if(addr==""||addr==null)
            address.setText("不知道定位到了哪里");
        else
            address.setText(addr);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {

            case android.R.id.home:
            {
                finish();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void notifyListChange() {
        explorerAdapter.notifyDataSetChanged();
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void toActivity(DetialsInfo detialsInfo) {
        Intent intent=new Intent();
        intent.putExtra("isRet",detialsInfo.isRet());
        intent.putExtra("position",detialsInfo.getPosition());
        intent.putExtra("hasChild",detialsInfo.isHasChild());
        intent.setClass(this,DetialActivity.class);
        startActivity(intent);
    }
    @Override
    public void toActivity(Pic_List_Info pic_list_info) {
        Intent intent=new Intent();
        intent.putStringArrayListExtra("Largeurl",pic_list_info.getLarg_url());
        intent.putExtra("position",pic_list_info.getPosition());
        intent.setClass(this,ImageActivity.class);
        startActivity(intent);
        //  startActivity(intent);
        //, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pic_list_info.getView(),"image").toBundle()
    }

}
