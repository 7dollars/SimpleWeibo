package com.wmk.wb.view;

import android.content.Intent;
import android.graphics.Color;
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
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;
import com.wangjie.androidbucket.utils.ABTextUtil;
import com.wangjie.androidbucket.utils.imageprocess.ABShape;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList;
import com.wangjie.rapidfloatingactionbutton.listener.OnRapidFloatingActionListener;
import com.wmk.wb.R;
import com.wmk.wb.presenter.DataManager;
import com.wmk.wb.model.entity.DetialsInfo;
import com.wmk.wb.model.entity.retjson.Access_token;
import com.wmk.wb.model.entity.FinalViewData;
import com.wmk.wb.model.entity.retjson.User;
import com.wmk.wb.model.entity.StaticData;
import com.wmk.wb.model.entity.retjson.WbData;
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
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import rx.Subscriber;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener {

    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;

    @BindView(R.id.main_list)
    RecyclerView main_list;

    @BindView(R.id.tool_bar)
    Toolbar mToolbar;

    MainListAdapter ListAdapter;

 //   @BindView(R.id.navigation_view)
 //   NavigationView mNavigationView;

 //   @BindView(R.id.drawer_layout)
  //  DrawerLayout dwlayout;


    @BindView(R.id.activity_main_rfal)
    RapidFloatingActionLayout rfaLayout;
    @BindView(R.id.activity_main_rfab)
    RapidFloatingActionButton rfaBtn;

    private RapidFloatingActionHelper rfabHelper;
    private ResideMenu resideMenu;
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

        RapidFloatingActionContentLabelList rfaContent = new RapidFloatingActionContentLabelList(this);
        rfaContent.setOnRapidFloatingActionContentLabelListListener(this);
        List<RFACLabelItem> items = new ArrayList<>();
        items.add(new RFACLabelItem<Integer>()
                .setResId(R.mipmap.ic_create_white_24dp)
                .setIconNormalColor(0xffd84315)
                .setIconPressedColor(0xffbf360c)
                .setWrapper(0)
        );
        items.add(new RFACLabelItem<Integer>()
                .setResId(R.mipmap.ic_more_vert_white_24dp)
                .setIconNormalColor(0xff4e342e)
                .setIconPressedColor(0xff3e2723)
                .setLabelColor(Color.WHITE)
                .setLabelSizeSp(14)
                .setLabelBackgroundDrawable(ABShape.generateCornerShapeDrawable(0xaa000000, ABTextUtil.dip2px(this, 4)))
                .setWrapper(1)
        );
        items.add(new RFACLabelItem<Integer>()

                .setResId(R.mipmap.ic_menu_white_24dp)
                .setIconNormalColor(0xff056f00)
                .setIconPressedColor(0xff0d5302)
                .setLabelColor(0xff056f00)
                .setWrapper(2)
        );

        rfaContent
                .setItems(items)
                .setIconShadowRadius(ABTextUtil.dip2px(this, 5))
                .setIconShadowColor(0xff888888)
                .setIconShadowDy(ABTextUtil.dip2px(this, 5))
        ;
        rfabHelper = new RapidFloatingActionHelper(
                this,
                rfaLayout,
                rfaBtn,
                rfaContent
        ).build();


        resideMenu = new ResideMenu(this);
        resideMenu.setBackground(R.color.colorPrimary);
        resideMenu.attachToActivity(this);
        resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);
        resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_LEFT);
        String titles[] = { "全部微博", "相互关注", "原创内容", "提到我的" };
        int icon[] = { R.mipmap.ic_home_white_24dp, R.mipmap.ic_autorenew_white_24dp, R.mipmap.ic_create_white_24dp, R.mipmap.ic_messenger_white_24dp };
        for (int i = 0; i < titles.length; i++){
            ResideMenuItem item = new ResideMenuItem(this, icon[i], titles[i]);
            item.setOnClickListener(this);
            resideMenu.addMenuItem(item,  ResideMenu.DIRECTION_LEFT); // or  ResideMenu.DIRECTION_RIGHT
        }
        String titles1[] = { "登录"};
        int icon1[] = { R.mipmap.ic_home_white_24dp};
        for (int i = 0; i < titles1.length; i++){
            ResideMenuItem item = new ResideMenuItem(this, icon1[i], titles1[i]);
            item.setOnClickListener(this);
            resideMenu.addMenuItem(item,  ResideMenu.DIRECTION_RIGHT); // or  ResideMenu.DIRECTION_RIGHT
        }

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
        ListAdapter=new MainListAdapter(MainActivity.this,swipe,mSubscriber,mSubscriber2,resideMenu);
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
      //  mNavigationView.setNavigationItemSelectedListener(naviListener);
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

        swipe.setRefreshing(true);
        getWbData(0);

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
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
                break;
            }
            default:{
                Intent intent=new Intent();
                intent.setClass(MainActivity.this,LoginActivity.class);
                startActivityForResult(intent,0);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

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
                if(max_id==0) {
                    main_list.scrollToPosition(0);
                    main_list.smoothScrollToPosition(0);
                }
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
            /*        View view=mNavigationView.inflateHeaderView(R.layout.nv_header);
                    CircleImageView head=(CircleImageView)view.findViewById(R.id.imageView2);

                    StaticData.getInstance().getLocalUser().setName(user.name);
                    TextView txt=(TextView)view.findViewById(R.id.nameView);
                    Glide.with(MainActivity.this).load(user.getAvatar_large()).into(head);
                    txt.setText(StaticData.getInstance().getLocalUser().getName());*/
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

    @Override
    public void onClick(View view) {

        Boolean flag=true;
        TextView txt=(TextView) view.findViewById(R.id.tv_title);
        if(txt.getText().equals("全部微博"))
        {
            StaticData.getInstance().setWbFlag(0);
            setTitle("所有内容");
            flag=false;
        }
        else if(txt.getText().equals("相互关注"))
        {
            StaticData.getInstance().setWbFlag(1);
            setTitle("相互关注");
            flag=false;
        }
        else if(txt.getText().equals("原创内容"))
        {
            StaticData.getInstance().setWbFlag(2);
            setTitle("原创内容");
            flag=false;
        }
        else if(txt.getText().equals("提到我的"))
        {
            StaticData.getInstance().setWbFlag(3);
            setTitle("提到我的");
            flag=false;
        }
        if(!flag) {
            swipe.setRefreshing(true);
            getWbData(0);
            main_list.scrollToPosition(0);
            main_list.smoothScrollToPosition(0);
            resideMenu.closeMenu();
            return;
        }
            if(txt.getText().equals("登录"))
        {
            Intent intent=new Intent();
            intent.setClass(MainActivity.this,LoginActivity.class);
            resideMenu.closeMenu();
            startActivityForResult(intent,0);
        }
    }
    @OnClick(R.id.more) //给 button1 设置一个点击事件
    public void more()
    {
        resideMenu.openMenu(ResideMenu.DIRECTION_RIGHT);
    }
    @Override
    public void onRFACItemLabelClick(int i, RFACLabelItem rfacLabelItem) {

    }

    @Override
    public void onRFACItemIconClick(int i, RFACLabelItem rfacLabelItem) {

        switch(i)
        {
            case 0:
            {
                Intent intent=new Intent();
                intent.setClass(this,NewWBActivity.class);
                startActivity(intent);
                rfaLayout.collapseContent();
                break;
            }
            case 1:
            {
                resideMenu.openMenu(ResideMenu.DIRECTION_RIGHT);
                rfaLayout.collapseContent();
                break;
            }
            case 2:
            {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
                rfaLayout.collapseContent();
                break;
            }
        }
    }
}
