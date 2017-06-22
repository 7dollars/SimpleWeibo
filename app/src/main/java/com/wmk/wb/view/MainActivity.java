package com.wmk.wb.view;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import com.squareup.leakcanary.LeakCanary;
import com.stylingandroid.prism.Prism;
import com.wmk.wb.R;
import com.wmk.wb.model.bean.LocationBean;
import com.wmk.wb.model.bean.NameEvent;
import com.wmk.wb.model.bean.Pic_List_Info;
import com.wmk.wb.model.bean.DetialsInfo;
import com.wmk.wb.model.StaticData;
import com.wmk.wb.model.bean.TagEvent;
import com.wmk.wb.model.bean.retjson.User;
import com.wmk.wb.utils.EndlessRecyclerOnScrollListener;
import com.wmk.wb.presenter.MainAC;
import com.wmk.wb.presenter.adapter.MainListAdapter;

import com.wmk.wb.utils.Myfab;
import com.wmk.wb.utils.SpUtil;
import com.wmk.wb.view.Interface.IMain;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import de.hdodenhof.circleimageview.CircleImageView;
import rx.Subscriber;
import uk.co.senab.photoview.PhotoView;

public class MainActivity extends AppCompatActivity implements IMain,Myfab.MenuListener,View.OnClickListener{

    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;

    @BindView(R.id.main_list)
    RecyclerView main_list;

    @BindView(R.id.tool_bar)
    Toolbar mToolbar;

    MainListAdapter ListAdapter;

    private View header;

    private RelativeLayout RlHeader;

    @BindView(R.id.fabtn)
    Myfab fabtn;

    private long exitTime = 0;

    private int commentflag=1;

    private MainAC instance;
    private Drawer dw;

    private CircleImageView head;

    private Prism prism;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==1)
        {
            instance.getToken(data.getExtras().getString("CODE"),MainActivity.this);
            Log.e(data.getExtras().getString("CODE"),"123123");
            instance.getSlideMenu();
        }
        else if(resultCode==2)
        {
            SpUtil.putString(this,"token",data.getStringExtra("Token"));
            SpUtil.putString(this,"uid",data.getStringExtra("Uid"));
            instance.getWbData(0,commentflag,"",null);
            instance.getSlideMenu();
        }

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN)
        {
            if(dw.isDrawerOpen())
            {
                dw.closeDrawer();
                return  true;
            }
            if((System.currentTimeMillis()-exitTime) > 2000)  //System.currentTimeMillis()无论何时调用，肯定大于2000
            {
                Toast.makeText(getApplicationContext(), "再按一次退出程序",Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            }
            else
            {
                finish();
                System.exit(0);
            }

            return true;
        }
        else if(keyCode==KeyEvent.KEYCODE_MENU&& event.getAction() == KeyEvent.ACTION_DOWN)
        {
            if(dw.isDrawerOpen())
                dw.closeDrawer();
            else
                dw.openDrawer();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
///////////////////////////////////////////////
    //toolbar菜单
///////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        StaticData.getInstance().setmContext(MainActivity.this);//!!!!!!!!!!!

        setTitle("全部内容");
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu_white_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);

        instance=new MainAC(this);
        setStaticThemColor();
        List<Integer> list=new ArrayList();
        list.add(R.mipmap.ic_menu_white_24dp);
        list.add(R.mipmap.ic_create_white_24dp);
        list.add(R.mipmap.ic_autorenew_white_24dp);
        fabtn.setIcon(list);
        fabtn.setMenuListener(this);
       // fabtn.setColor(0xff3f51b5);
        header=View.inflate(this,R.layout.nv_header,null);
        RlHeader=(RelativeLayout) header.findViewById(R.id.rl);

        LinearLayout linearLayout=(LinearLayout) header.findViewById(R.id.sc);
        linearLayout.setOnClickListener(this);
        linearLayout=(LinearLayout) header.findViewById(R.id.zb);
        linearLayout.setOnClickListener(this);
        linearLayout=(LinearLayout) header.findViewById(R.id.ts);
        linearLayout.setOnClickListener(this);

        EventBus.getDefault().register(this);

        head=(CircleImageView) header.findViewById(R.id.headerView);
        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new NameEvent(""));
            }
        });
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName("全部微博").withIcon(R.mipmap.ic_home_grey600_24dp);
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withIdentifier(2).withName("相互关注").withIcon(R.mipmap.ic_group_grey600_24dp);
        PrimaryDrawerItem item3 = new PrimaryDrawerItem().withIdentifier(3).withName("原创内容").withIcon(R.mipmap.ic_create_grey600_24dp);
        PrimaryDrawerItem item4 = new PrimaryDrawerItem().withIdentifier(4).withName("提到我的").withIcon(R.mipmap.ic_messenger_grey600_24dp);
        PrimaryDrawerItem item5 = new PrimaryDrawerItem().withIdentifier(5).withName("收到回复").withIcon(R.mipmap.ic_forum_grey600_24dp);
        PrimaryDrawerItem item6 = new PrimaryDrawerItem().withIdentifier(6).withName("提到我的回复").withIcon(R.mipmap.ic_forum_grey600_24dp);
        PrimaryDrawerItem item7 = new PrimaryDrawerItem().withIdentifier(7).withName("登录").withSelectable(false).withIcon(R.mipmap.ic_person_grey600_24dp);
        PrimaryDrawerItem item8 = new PrimaryDrawerItem().withIdentifier(8).withName("主题").withSelectable(false).withIcon(R.mipmap.ic_settings_grey600_24dp);
       // SecondaryDrawerItem item2 = new SecondaryDrawerItem().withIdentifier(2).withName("setting");

        prism = Prism.Builder.newInstance()
                .background(mToolbar)
                .background(getWindow())
                .build();


        dw=new DrawerBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(false)
                .withActionBarDrawerToggle(false)
                .addDrawerItems(
                item1,
                item2,
                item3,
                new DividerDrawerItem(),
                item4,
                new DividerDrawerItem(),
                item5,
                item6,
                new DividerDrawerItem(),
                item7,
                item8
        )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        switch(position)
                        {
                            case 1:
                            {
                                StaticData.getInstance().setWbFlag(0);
                                setTitle("所有内容");
                                commentflag=1;
                                break;
                            }
                            case 2:
                            {
                                StaticData.getInstance().setWbFlag(1);
                                setTitle("相互关注");
                                commentflag=1;
                                break;
                            }
                            case 3:
                            {
                                StaticData.getInstance().setWbFlag(2);
                                setTitle("原创内容");
                                commentflag=1;
                                break;
                            }
                            case 5:
                            {
                                StaticData.getInstance().setWbFlag(3);
                                setTitle("提到我的");
                                commentflag=1;
                                break;
                            }
                            case 7:
                            {
                                StaticData.getInstance().setWbFlag(4);
                                commentflag=2;
                                setTitle("收到回复");
                                break;
                            }
                            case 8:
                            {
                                StaticData.getInstance().setWbFlag(5);
                                commentflag=2;
                                setTitle("提到我的回复");
                                break;
                            }
                            case 10:
                            {
                                Intent intent=new Intent();
                                intent.setClass(MainActivity.this,LoginActivity.class);
                                startActivityForResult(intent,0);
                                dw.closeDrawer();
                                return true;
                            }
                            case 11:
                            {
                                Intent intent=new Intent();
                                intent.setClass(MainActivity.this,ColorSelectActivity.class);
                                startActivity(intent);
                                return true;
                            }
                        }
                        dw.closeDrawer();
                        swipe.setRefreshing(true);
                        instance.getWbData(0,commentflag,"",null);
                        main_list.scrollToPosition(0);
                        main_list.smoothScrollToPosition(0);
                        return true;
                        // do something with the clicked item :D
                    }
                })
                .withHeader(header)
                .build();

        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main_list.scrollToPosition(0);
                main_list.smoothScrollToPosition(0);//第一个scroll有时候滚不到顶部，所以用第二个补一下。。。。

            }
        });

        Subscriber<DetialsInfo> mSubscriber=instance.getDetialsSubscriber();
        Subscriber<Pic_List_Info> mSubscriber2=instance.getPicSubscriber();
        instance.getSlideMenu();

        ListAdapter=new MainListAdapter(MainActivity.this,swipe,mSubscriber,mSubscriber2);

        LinearLayoutManager manager = new LinearLayoutManager(main_list.getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        main_list.setLayoutManager(manager);
        main_list.setAdapter(ListAdapter);

        EndlessRecyclerOnScrollListener end=new EndlessRecyclerOnScrollListener(manager) {
            @Override
            public void onLoadMore(int currentPage) {
                if (StaticData.getInstance().getData().size() > 0)
                {
                    instance.getWbData(StaticData.getInstance().getData().get(StaticData.getInstance().getData().size()-1).getId(),commentflag,"",LocationBean.getInstance());
                }
            }
        };//上拉加载
        main_list.addOnScrollListener(end);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(SpUtil.getString(MainActivity.this,"token",null)==null) {
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, LoginActivity.class);
                    startActivityForResult(intent, 0);
                }
                else {
                    instance.getWbData(0,commentflag,"",null);
                }
            }
        });

        swipe.setRefreshing(true);
        instance.getWbData(0,commentflag,"",null);

    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        RlHeader.setBackgroundColor(getResources().getColor(instance.getThemeColor()));
        fabtn.setColor(getResources().getColor(instance.getThemeColor()));
        prism.setColor(getResources().getColor(instance.getThemeColor()));
        instance.setPersonalFlag(false);
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {

            case android.R.id.home:
            {
                dw.openDrawer();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

///////////////////////////////////////////////
    //悬浮按钮
///////////////////////////////////////////////

@Override
public void click(int i) {
    switch (i)
    {
        case 1:
        {
            dw.openDrawer();
            fabtn.startAnimation();
            break;
        }
        case 2:
        {
            Intent intent=new Intent();
            intent.setClass(this,NewWBActivity.class);
            startActivity(intent);
            fabtn.collapse();
            break;
        }
        case 3:
        {
            fabtn.startAnimation();
            swipe.setRefreshing(true);
            instance.getWbData(0,commentflag,"",null);
            main_list.scrollToPosition(0);
            main_list.smoothScrollToPosition(0);
            break;
        }

    }

}
///////////////////////////////////////////////
    //侧边菜单
///////////////////////////////////////////////


///////////////////////////////////////////////
    //接口实现
///////////////////////////////////////////////
    @Override
    public void setRefresh(boolean refresh,boolean isScrollToTop) {
        swipe.setRefreshing(refresh);
        if(isScrollToTop)
        {
            main_list.scrollToPosition(0);
            main_list.smoothScrollToPosition(0);
        }
    }
    @Override
    public void notifyListChange() {
        ListAdapter.notifyDataSetChanged();
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
        intent.setClass(MainActivity.this,DetialActivity.class);
        startActivity(intent);
    }
    @Override
    public void toActivity(Pic_List_Info pic_list_info) {
        Intent intent=new Intent();
        intent.putStringArrayListExtra("Largeurl",pic_list_info.getLarg_url());
        intent.putExtra("position",pic_list_info.getPosition());
        intent.setClass(MainActivity.this,ImageActivity.class);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pic_list_info.getView(),"image").toBundle());
      //  startActivity(intent);
        //, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pic_list_info.getView(),"image").toBundle()
    }

    @Override
    public void toActivity(User user) {
        CircleImageView pv=(CircleImageView)header.findViewById(R.id.headerView);
        TextView name=(TextView)header.findViewById(R.id.nameView);

        name.setText(user.getName());
        name=(TextView)header.findViewById(R.id.contentView);
        name.setText(user.getDescription());

        Log.e("123123",user.getProfile_image_url());
        Glide.with(MainActivity.this)
                .load(user.getAvatar_large())
                .into(pv);

    }

    @Override
    public void MysetTitle(String title) {
        setTitle(title);
    }

    @Subscribe
    public void onEventMainThread(NameEvent event) {
        Intent intent=new Intent();
        intent.putExtra("name",event.getName().toString());
        intent.setClass(MainActivity.this,PersonalInfoActivity.class);
        instance.clearPersonalData();
        instance.setPersonalFlag(true);
        startActivity(intent);
    }
    @Subscribe
    public void onEventMainThread(TagEvent event) {
        Intent intent=new Intent();
        intent.putExtra("name",event.getName().toString());
        intent.setClass(MainActivity.this,PersonalInfoActivity.class);
        instance.clearPersonalData();
        instance.setTopicFlag(true);
        startActivity(intent);
    }
    public void setStaticThemColor()
    {
        int color=SpUtil.getInt(this,"themecolor",0);
        if(color==0) {
            SpUtil.putInt(this, "themecolor", R.color.colorPrimary);
            instance.setStaticColor(R.color.colorPrimary);
        }
        else
            instance.setStaticColor(color);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id)
        {
            case R.id.sc:
            {
                StaticData.getInstance().setWbFlag(6);
                setTitle("收藏");
                commentflag=3;
                dw.closeDrawer();
                swipe.setRefreshing(true);
                instance.getWbData(0,commentflag,"",null);
                main_list.scrollToPosition(0);
                main_list.smoothScrollToPosition(0);
                break;
            }
            case R.id.zb:
            {
              /*  LocationBean bean=LocationBean.getInstance();
                StaticData.getInstance().setWbFlag(7);
                setTitle("周边");
                instance.initLocation(getApplicationContext());
                commentflag=1;
                dw.closeDrawer();
                swipe.setRefreshing(true);
                instance.getWbData(0,commentflag,"",bean);
                main_list.scrollToPosition(0);
                main_list.smoothScrollToPosition(0);*/
              Intent intent=new Intent();
              intent.setClass(MainActivity.this,ExploreActivity.class);
              startActivity(intent);
                break;
            }
            case R.id.ts:
            {
                StaticData.getInstance().setWbFlag(8);
                setTitle("热门微博");
                commentflag=1;
                dw.closeDrawer();
                swipe.setRefreshing(true);
                instance.getWbData(0,commentflag,"",null);
                main_list.scrollToPosition(0);
                main_list.smoothScrollToPosition(0);
                break;
            }
        }
    }
}
