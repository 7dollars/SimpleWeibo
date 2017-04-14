package com.wmk.wb.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.wmk.wb.R;
import com.wmk.wb.presenter.DataManager;
import com.wmk.wb.model.entity.DetialsInfo;
import com.wmk.wb.model.entity.RetJson.Access_token;
import com.wmk.wb.model.entity.FinalViewData;
import com.wmk.wb.model.entity.RetJson.User;
import com.wmk.wb.model.entity.StaticData;
import com.wmk.wb.model.entity.RetJson.WbData;
import com.wmk.wb.presenter.EndlessRecyclerOnScrollListener;
import com.wmk.wb.presenter.IMainAC;
import com.wmk.wb.presenter.MainAC;
import com.wmk.wb.presenter.adapter.MainListAdapter;

import com.wmk.wb.utils.ConvertDate;
import com.wmk.wb.utils.SpUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import rx.Subscriber;

public class MainActivity extends AppCompatActivity{

    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;

    @BindView(R.id.main_list)
    RecyclerView main_list;

    @BindView(R.id.tool_bar)
    Toolbar mToolbar;

    MainListAdapter ListAdapter;

    @BindView(R.id.navigation_view)
    NavigationView mNavigationView;

    @BindView(R.id.drawer_layout)
    DrawerLayout dwlayout;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==1)
        {
            getToken(data.getExtras().getString("CODE"));
            Log.e(data.getExtras().getString("CODE"),"123123");
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setTitle("全部内容");
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu_white_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);

        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main_list.scrollToPosition(0);
                main_list.smoothScrollToPosition(0);//第一个scroll有时候滚不到顶部，所以用第二个补一下。。。。

            }
        });

        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        StaticData.getInstance().setCacheSize(maxMemory/8);

        Subscriber<DetialsInfo> mSubscriber=setDetialsSubscriber();
        Subscriber<String> mSubscriber2=setPicSubscriber();
        ListAdapter=new MainListAdapter(MainActivity.this,swipe,mSubscriber,mSubscriber2);
        StaticData staticData=StaticData.getInstance();
        staticData.setmContext(MainActivity.this);
        getUserInfo();

        LinearLayoutManager manager = new LinearLayoutManager(main_list.getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        main_list.setLayoutManager(manager);
        main_list.setAdapter(ListAdapter);

        EndlessRecyclerOnScrollListener end=new EndlessRecyclerOnScrollListener(manager) {
            @Override
            public void onLoadMore(int currentPage) {
                if(StaticData.getInstance().getData().size()>0)
                {
                    getWbData(StaticData.getInstance().getData().get(StaticData.getInstance().getData().size()-1).getId());
                }
            }
        };//上拉加载
        main_list.addOnScrollListener(end);
        mNavigationView.setNavigationItemSelectedListener(naviListener);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(SpUtil.getString(MainActivity.this,"token",null)==null) {
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, LoginActivity.class);
                    startActivityForResult(intent, 0);
                }
                else {
                    getWbData(0);
                }
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        dwlayout.openDrawer(mNavigationView);
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.login:
            {
                Intent intent=new Intent();
                intent.setClass(MainActivity.this,LoginActivity.class);
                startActivityForResult(intent,0);
                break;
            }
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }
    private NavigationView.OnNavigationItemSelectedListener naviListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem menuItem) {
            //点击NavigationView中定义的menu item时触发反应
            switch (menuItem.getItemId()) {
                case R.id.allWB: {
                    StaticData.getInstance().setWbFlag(0);
                    swipe.setRefreshing(true);
                    getWbData(0);
                    setTitle("所有内容");
                    main_list.scrollToPosition(0);
                    main_list.smoothScrollToPosition(0);
                    break;
                }
                case R.id.biatrialWB: {
                    StaticData.getInstance().setWbFlag(1);
                    swipe.setRefreshing(true);
                    getWbData(0);
                    setTitle("相互关注");
                    main_list.scrollToPosition(0);
                    break;
                }
                case R.id.originalWB: {
                    StaticData.getInstance().setWbFlag(2);
                    swipe.setRefreshing(true);
                    getWbData(0);
                    setTitle("原创内容");
                    main_list.scrollToPosition(0);
                    main_list.smoothScrollToPosition(0);
                    break;
                }
                case R.id.mentionWB: {
                    StaticData.getInstance().setWbFlag(3);
                    swipe.setRefreshing(true);
                    getWbData(0);
                    setTitle("提到我的");
                    main_list.scrollToPosition(0);
                    break;
                }
            }
            //关闭DrawerLayout回到主界面选中的tab的fragment页
            dwlayout.closeDrawer(mNavigationView);
            return false;
        }
    };
    public void getToken(String code)
    {
        Subscriber<Access_token> mSubscribe;
        mSubscribe=new Subscriber<Access_token>() {
            @Override
            public void onCompleted() {
                Toast.makeText(MainActivity.this,"登陆成功",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(MainActivity.this,"登陆失败",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(Access_token access_token) {
                SpUtil.putString(MainActivity.this,"token",access_token.getAccess_token());
                SpUtil.putString(MainActivity.this,"uid",access_token.getUid());
            }
        };
        DataManager.getInstance().getAccess(mSubscribe,code);
    }
    public void getWbData(final long max_id)//获取微博数据，max_id:数据起始id，0为从最新开始获取
    {
        Subscriber<WbData> mSubscribe;
        final IMainAC mainAC=new MainAC();
        mSubscribe=new Subscriber<WbData>() {

            @Override
            public void onCompleted() {
                swipe.setRefreshing(false);
                //swipe.setLoading(false);
                ListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(WbData wbData) {
                FinalViewData fdata;
                List<FinalViewData> data=new ArrayList<>();
                for (int i = 0; i < wbData.getStatuses().size(); i++) {
                    if(i==0&&max_id!=0)
                        i=1;
                    fdata = new FinalViewData();
                    fdata.setText(wbData.getStatuses().get(i).getText());
                    fdata.setHeadurl(wbData.getStatuses().get(i).getUser().getAvatar_large());
                    fdata.setName(wbData.getStatuses().get(i).getUser().getName());
                    fdata.setId(wbData.getStatuses().get(i).getId());
                    fdata.setTime(ConvertDate.calcDate(wbData.getStatuses().get(i).getCreated_at()));
                    fdata.setReposts_count(wbData.getStatuses().get(i).getReposts_count());
                    fdata.setComments_count(wbData.getStatuses().get(i).getComments_count());
                    if(wbData.getStatuses().get(i).getRetweeted_statuses()!=null)
                    {
                        fdata.setRet_time(ConvertDate.calcDate(wbData.getStatuses().get(i).getRetweeted_statuses().getCreated_at()));
                        fdata.setRet_text(wbData.getStatuses().get(i).getRetweeted_statuses().getText());
                        fdata.setReposts_count_ret(wbData.getStatuses().get(i).getRetweeted_statuses().getReposts_count());
                        fdata.setComments_count_ret(wbData.getStatuses().get(i).getRetweeted_statuses().getComments_count());
                        fdata.setRet_name(wbData.getStatuses().get(i).getRetweeted_statuses().getUser().getName());
                        fdata.setRet_headurl(wbData.getStatuses().get(i).getRetweeted_statuses().getUser().getAvatar_large());
                        fdata.setRet_id(wbData.getStatuses().get(i).getRetweeted_statuses().getId());
                        if(wbData.getStatuses().get(i).getRetweeted_statuses().getPic_urls()!=null)
                        {
                                 fdata.setRet_picurls(wbData.getStatuses().get(i).getRetweeted_statuses().getPic_urls());
                        }
                        if(wbData.getStatuses().get(i).getRetweeted_statuses().getPic_ids()!=null)
                        {
                            List<String> array=new ArrayList<>();
                            for(String ids:wbData.getStatuses().get(i).getRetweeted_statuses().getPic_ids()){
                                ids="http://ww3.sinaimg.cn/thumbnail/"+ids+".jpg";
                                array.add(ids);
                            }
                            fdata.setRet_picurls(array);
                        }
                    }
                    if(wbData.getStatuses().get(i).getPic_urls()!=null)
                    {
                        fdata.setPicurls(wbData.getStatuses().get(i).getPic_urls());
                    }
                    if(wbData.getStatuses().get(i).getPic_ids()!=null)
                    {
                        List<String> array=new ArrayList<>();
                        for(String ids:wbData.getStatuses().get(i).getPic_ids()){
                            ids="http://ww3.sinaimg.cn/thumbnail/"+ids+".jpg";
                            array.add(ids);
                        }
                        fdata.setPicurls(array);
                    }
                    if(max_id!=0)
                        mainAC.addDataToMainList(fdata);
                    else
                        data.add(fdata);
                }
                if(max_id==0)
                    mainAC.refreshDataToMainList(data);

            }
        };
        mainAC.getDataDataManager().getWbData(mSubscribe, max_id);
    }
    public void getUserInfo()
    {
        Subscriber<User> mSubscribe;
        mSubscribe=new Subscriber<User>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(User user) {
                if(user.name!=null)
                {
                    View view=mNavigationView.inflateHeaderView(R.layout.nv_header);
                    CircleImageView head=(CircleImageView)view.findViewById(R.id.imageView2);

                    StaticData.getInstance().getLocalUser().setName(user.name);
                    TextView txt=(TextView)view.findViewById(R.id.nameView);
                    Glide.with(MainActivity.this).load(user.getAvatar_large()).into(head);
                    txt.setText(StaticData.getInstance().getLocalUser().getName());
                }
            }
        };
        DataManager.getInstance().getLocalUser(mSubscribe);
    }

    public Subscriber<DetialsInfo> setDetialsSubscriber()
    {
        final Subscriber<DetialsInfo> mSubscriber=new Subscriber<DetialsInfo>() {
            @Override
            public void onCompleted() {
                unsubscribe();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(DetialsInfo detialsInfo) {

                Intent intent=new Intent();
                intent.putExtra("isRet",detialsInfo.isRet());
                intent.putExtra("position",detialsInfo.getPosition());
                intent.putExtra("hasChild",detialsInfo.isHasChild());
                intent.setClass(MainActivity.this,DetialActivity.class);
                startActivity(intent);

            }
        };
        return mSubscriber;
    }
    public Subscriber<String> setPicSubscriber()
    {
        final Subscriber<String> mSubscriber=new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Intent intent=new Intent();
                intent.putExtra("Largeurl",s);
                intent.setClass(MainActivity.this,ImageActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        };
        return mSubscriber;
    }

}
