package com.wmk.wb.view;

import android.app.ActivityOptions;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import com.wmk.wb.model.bean.LoadingBus;
import com.wmk.wb.model.bean.LocationBean;
import com.wmk.wb.model.bean.Pic_List_Info;
import com.wmk.wb.presenter.ExploreAC;
import com.wmk.wb.presenter.adapter.ExplorerAdapter;
import com.wmk.wb.presenter.adapter.MainListAdapter;
import com.wmk.wb.utils.ColorThemeUtils;
import com.wmk.wb.utils.DensityUtil;
import com.wmk.wb.utils.EndlessRecyclerOnScrollListener;
import com.wmk.wb.utils.Myfab;
import com.wmk.wb.utils.RegionEnum;
import com.wmk.wb.utils.RegionUtil;
import com.wmk.wb.view.Interface.IExplore;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;

public class ExploreActivity extends AppCompatActivity implements IExplore ,View.OnClickListener ,Myfab.MenuListener {
    @Override
    protected void onPause() {
        isActive=false;
        super.onPause();
    }
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

    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout ctl;

    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;

    @BindView(R.id.fabtn)
    Myfab fabtn;
    // @BindView(R.id.nestedScrollView)
    // NestedScrollView nestedScrollView;

    private ExplorerAdapter explorerAdapter;
    private ExploreAC instance;
    private int oldflag = 1;
    private Prism prism;
    private int refreshflag = 0;
    private AlertDialog alertDialog;
    private RegionEnum regionEnum;
    private boolean isActive;
    private TextView loadingtxt;
    private boolean LoadMoreFlag;//是否正在加载，防止多次加载请求


    public ExploreActivity() {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        instance = new ExploreAC(this);
        oldflag = StaticData.getInstance().getWbFlag();

        List<Integer> list = new ArrayList();
        list.add(R.mipmap.ic_autorenew_white_24dp);
        list.add(R.mipmap.ic_vertical_align_top_white_24dp);
        fabtn.setIcon(list);
        fabtn.setMenuListener(this);

        //   dialog.setOnClickListener(this);
        prism = Prism.Builder.newInstance()
                .background(appBarLayout)
                .background(mToolbar)
                .background(getWindow())
                .build();
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });

        Subscriber<DetialsInfo> mSubscriber = instance.getDetialsSubscriber();
        Subscriber<Pic_List_Info> mSubscriber2 = instance.getPicSubscriber();
        explorerAdapter = new ExplorerAdapter(this, null, mSubscriber, mSubscriber2);

        setSupportActionBar(mToolbar);
        //   ActionBar actionBar = getSupportActionBar();
        //   actionBar.setHomeAsUpIndicator(R.mipmap.ic_arrow_back_white_24dp);
        //   actionBar.setDisplayHomeAsUpEnabled(true);
        // setTitle("探索");


        LinearLayoutManager manager = new LinearLayoutManager(content_list.getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        content_list.setLayoutManager(manager);
        content_list.setAdapter(explorerAdapter);

        EndlessRecyclerOnScrollListener end = new EndlessRecyclerOnScrollListener(manager) {
            @Override
            public void onLoadMore(int currentPage) {
                if (StaticData.getInstance().getData().size() > 0&&LoadMoreFlag==false) {
                    LoadMoreFlag=true;
                    instance.getWbData(StaticData.getInstance().getData().get(StaticData.getInstance().getData().size() - 1).getId());
                }
            }
        };//上拉加载
        content_list.addOnScrollListener(end);
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

    public void createDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View dialog = inflater.inflate(R.layout.region_select_dialog, null);
        setDialogListener(dialog);
        alertDialog = new AlertDialog
                .Builder(this)
                .setTitle("想去哪儿？")
                .setView(dialog)
                .setNegativeButton("取消", null)
                .show();
    }

    @Override
    protected void onResume() {
        prism.setColor(getResources().getColor(ColorThemeUtils.getColor(instance.getThemeColor())));
        fabtn.setColor(ContextCompat.getColor(this, ColorThemeUtils.getColor(instance.getThemeColor())));
        StaticData.getInstance().setExpflag(true);
        isActive=true;
        super.onResume();
    }

    public void refresh()
    {
        if(regionEnum==null) {
            instance.initLocation(getApplicationContext());
            instance.getWbData(0);         //刷新周围微博
        }
        else
        {
            instance.initGeocode(getApplicationContext());
            instance.getRandomData(0, RegionUtil.getRegion(regionEnum));     //刷新随便看看
        }
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.zb://周边内容
            {
                regionEnum = null;
                instance.initLocation(getApplicationContext());
                instance.getWbData(0);
                break;
            }
            case R.id.sb://随便走走
            {
                createDialog();
                instance.initGeocode(getApplicationContext());
                // instance.getRandomData(0);
                break;
            }
            case R.id.tool_bar: {
                appBarLayout.setExpanded(true);
                // nestedScrollView.scrollTo(0,0);
                content_list.scrollToPosition(0);
                break;
            }
            case R.id.remen: {
                instance.getRandomData(0, RegionUtil.getRegion(RegionEnum.random));
                regionEnum = RegionEnum.beijing;
                exitDialog();
                break;
            }
            case R.id.beijing: {
                instance.getRandomData(0, RegionUtil.getRegion(RegionEnum.beijing));
                regionEnum = RegionEnum.beijing;
                exitDialog();
                break;
            }
            case R.id.shanghai: {
                instance.getRandomData(0, RegionUtil.getRegion(RegionEnum.shanghai));
                regionEnum = RegionEnum.shanghai;
                exitDialog();
                break;
            }
            case R.id.guangzhou: {
                instance.getRandomData(0, RegionUtil.getRegion(RegionEnum.guangzhou));
                regionEnum = RegionEnum.guangzhou;
                exitDialog();
                break;
            }
            case R.id.shenzhen: {
                instance.getRandomData(0, RegionUtil.getRegion(RegionEnum.shenzhen));
                regionEnum = RegionEnum.shenzhen;
                exitDialog();
                break;
            }
            case R.id.hangzhou: {
                instance.getRandomData(0, RegionUtil.getRegion(RegionEnum.hangzhou));
                regionEnum = RegionEnum.hangzhou;
                exitDialog();
                break;
            }
            case R.id.chengdu: {
                instance.getRandomData(0, RegionUtil.getRegion(RegionEnum.chengdu));
                regionEnum = RegionEnum.chengdu;
                exitDialog();
                break;
            }
            case R.id.qingdao: {
                instance.getRandomData(0, RegionUtil.getRegion(RegionEnum.qingdao));
                regionEnum = RegionEnum.qingdao;
                exitDialog();
                break;
            }
            case R.id.tianjin: {
                instance.getRandomData(0, RegionUtil.getRegion(RegionEnum.tianjin));
                regionEnum = RegionEnum.tianjin;
                exitDialog();
                break;
            }
            case R.id.nanjing: {
                instance.getRandomData(0, RegionUtil.getRegion(RegionEnum.nanjing));
                regionEnum = RegionEnum.nanjing;
                exitDialog();
                break;
            }
            case R.id.wuhan: {
                instance.getRandomData(0, RegionUtil.getRegion(RegionEnum.wuhan));
                regionEnum = RegionEnum.wuhan;
                exitDialog();
                break;
            }
        }
    }

    public void exitDialog() {
        if (alertDialog != null)
            alertDialog.dismiss();
    }

    public void setDialogListener(View v) {
        LinearLayout l = (LinearLayout) v.findViewById(R.id.beijing);
        l.setOnClickListener(this);
        l = (LinearLayout) v.findViewById(R.id.remen);
        l.setOnClickListener(this);
        l = (LinearLayout) v.findViewById(R.id.shanghai);
        l.setOnClickListener(this);
        l = (LinearLayout) v.findViewById(R.id.guangzhou);
        l.setOnClickListener(this);
        l = (LinearLayout) v.findViewById(R.id.shenzhen);
        l.setOnClickListener(this);
        l = (LinearLayout) v.findViewById(R.id.wuhan);
        l.setOnClickListener(this);
        l = (LinearLayout) v.findViewById(R.id.nanjing);
        l.setOnClickListener(this);
        l = (LinearLayout) v.findViewById(R.id.chengdu);
        l.setOnClickListener(this);
        l = (LinearLayout) v.findViewById(R.id.qingdao);
        l.setOnClickListener(this);
        l = (LinearLayout) v.findViewById(R.id.hangzhou);
        l.setOnClickListener(this);
        l = (LinearLayout) v.findViewById(R.id.tianjin);
        l.setOnClickListener(this);
    }

    @Override
    public void setRefresh(boolean refresh, boolean isScrollToTop) {
        swipe.setRefreshing(refresh);
        if (isScrollToTop) {
            content_list.scrollToPosition(0);
            // nestedScrollView.scrollTo(0,0);
        }
    }

    @Override
    public void setAddress(String addr) {
        if (addr == "" || addr == null)
            address.setText("不知道定位到了哪里");
        else
            address.setText(addr);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home: {
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
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void toActivity(DetialsInfo detialsInfo) {
        Intent intent = new Intent();
        intent.putExtra("isRet", detialsInfo.isRet());
        intent.putExtra("position", detialsInfo.getPosition());
        intent.putExtra("hasChild", detialsInfo.isHasChild());
        intent.setClass(this, DetialActivity.class);
        startActivity(intent);
    }

    @Override
    public void toActivity(Pic_List_Info pic_list_info) {
        Intent intent = new Intent();
        intent.putStringArrayListExtra("Largeurl", pic_list_info.getLarg_url());
        intent.putExtra("position", pic_list_info.getPosition());
        intent.setClass(this, ImageActivity.class);
        //  startActivity(intent);
        //  startActivity(intent);
        //, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pic_list_info.getView(),"image").toBundle()
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this, pic_list_info.getView(), "image").toBundle());
    }

    @Override
    public void setLoadingFaild() {
        if(loadingtxt!=null)
            loadingtxt.setText("加载失败，请点击重试");
    }

    @Override
    public void setLoadMore(boolean loadmore) {
        LoadMoreFlag=loadmore;
    }

    @Subscribe
    public void onEventMainThread(LoadingBus event) {
        if(!isActive)
            return;
        loadingtxt=event.getLoading();
        if(!event.isPress())
            return;
        if (StaticData.getInstance().getData().size() > 0&&LoadMoreFlag==false) {
            LoadMoreFlag=true;
            instance.getWbData(StaticData.getInstance().getData().get(StaticData.getInstance().getData().size() - 1).getId());
        }
        else {
            refresh();
        }
    }
    @Override
    public void click(int i) {
        switch (i) {
            case 1: {
                fabtn.startAnimation();
                content_list.scrollToPosition(0);
                appBarLayout.setExpanded(true);
                swipe.setRefreshing(true);
                refresh();
                break;

            }
            case 2: {
                fabtn.startAnimation();
                content_list.scrollToPosition(0);
                appBarLayout.setExpanded(true);
                break;
            }

        }
    }
}
