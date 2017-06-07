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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;
import com.wangjie.androidbucket.utils.ABTextUtil;
import com.wangjie.androidbucket.utils.imageprocess.ABShape;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList;
import com.wmk.wb.R;
import com.wmk.wb.model.bean.Pic_List_Info;
import com.wmk.wb.model.bean.DetialsInfo;
import com.wmk.wb.model.StaticData;
import com.wmk.wb.utils.EndlessRecyclerOnScrollListener;
import com.wmk.wb.presenter.MainAC;
import com.wmk.wb.presenter.adapter.MainListAdapter;

import com.wmk.wb.utils.SpUtil;
import com.wmk.wb.view.Interface.IMain;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import rx.Subscriber;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener ,ResideMenu.OnMenuListener,IMain {

    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;

    @BindView(R.id.main_list)
    RecyclerView main_list;

    @BindView(R.id.tool_bar)
    Toolbar mToolbar;

    MainListAdapter ListAdapter;

    @BindView(R.id.activity_main_rfal)
    RapidFloatingActionLayout rfaLayout;
    @BindView(R.id.activity_main_rfab)
    RapidFloatingActionButton rfaBtn;

    private RapidFloatingActionHelper rfabHelper;
    private ResideMenu resideMenu;
    private long exitTime = 0;

    MainAC instance;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==1)
        {
            instance.getToken(data.getExtras().getString("CODE"),MainActivity.this);
            Log.e(data.getExtras().getString("CODE"),"123123");
        }

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN)
        {
            if(resideMenu.isOpened())
            {
                resideMenu.closeMenu();
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
            if(resideMenu.isOpened())
                resideMenu.closeMenu();
            else
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
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

        setTitle("全部内容");
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu_white_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);

        instance=new MainAC(this);

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
        resideMenu.setMenuListener(this);
        resideMenu.attachToActivity(this);
        resideMenu.setShadowVisible(false);
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
        int icon1[] = { R.mipmap.ic_person_white_24dp};
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

        Subscriber<DetialsInfo> mSubscriber=instance.getDetialsSubscriber();
        Subscriber<Pic_List_Info> mSubscriber2=instance.getPicSubscriber();
        ListAdapter=new MainListAdapter(MainActivity.this,swipe,mSubscriber,mSubscriber2,resideMenu);

        StaticData.getInstance().setmContext(MainActivity.this);

        LinearLayoutManager manager = new LinearLayoutManager(main_list.getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        main_list.setLayoutManager(manager);
        main_list.setAdapter(ListAdapter);

        EndlessRecyclerOnScrollListener end=new EndlessRecyclerOnScrollListener(manager) {
            @Override
            public void onLoadMore(int currentPage) {
                if(StaticData.getInstance().getData().size()>0)
                {
                    instance.getWbData(StaticData.getInstance().getData().get(StaticData.getInstance().getData().size()-1).getId());
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
                    instance.getWbData(0);
                }
            }
        });

        swipe.setRefreshing(true);
        instance.getWbData(0);

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
    @OnClick(R.id.more) //给 button1 设置一个点击事件
    public void more()
    {
        resideMenu.openMenu(ResideMenu.DIRECTION_RIGHT);
    }

///////////////////////////////////////////////
    //悬浮按钮
///////////////////////////////////////////////
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
///////////////////////////////////////////////
    //侧边菜单
///////////////////////////////////////////////
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
        instance.getWbData(0);
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
    @Override
    public void openMenu() {
        resideMenu.setShadowVisible(true);
        resideMenu.setBackground(R.color.colorPrimary);
    }
    @Override
    public void closeMenu() {
        resideMenu.setShadowVisible(false);
        resideMenu.setBackground(0);
    }

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
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,detialsInfo.getRe(),"transition").toBundle());
    }
    @Override
    public void toActivity(Pic_List_Info pic_list_info) {
        Intent intent=new Intent();
        intent.putStringArrayListExtra("Largeurl",pic_list_info.getLarg_url());
        intent.putExtra("position",pic_list_info.getPosition());
        intent.setClass(MainActivity.this,ImageActivity.class);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pic_list_info.getView(),"image").toBundle());
    }

}
