package com.wmk.wb.view;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.stylingandroid.prism.Prism;
import com.wmk.wb.R;
import com.wmk.wb.model.StaticData;
import com.wmk.wb.model.bean.DetialsInfo;
import com.wmk.wb.model.bean.LoadingBus;
import com.wmk.wb.model.bean.PersonalACInfo;
import com.wmk.wb.model.bean.Pic_List_Info;
import com.wmk.wb.model.bean.retjson.User;
import com.wmk.wb.presenter.MainAC;
import com.wmk.wb.presenter.PersonalAC;
import com.wmk.wb.presenter.adapter.MainListAdapter;
import com.wmk.wb.presenter.adapter.PersonalListAdapter;
import com.wmk.wb.utils.ColorThemeUtils;
import com.wmk.wb.utils.EndlessRecyclerOnScrollListener;
import com.wmk.wb.utils.WrapContentLinearLayoutManager;
import com.wmk.wb.view.Interface.IMain;
import com.wmk.wb.view.Interface.IPersonal;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import rx.Subscriber;
/*
与MainActivity共用一个presenter
*/

public class PersonalInfoActivity extends AppCompatActivity implements IPersonal{
    @Override
    protected void onPause() {
        super.onPause();
        isActive=false;
    }

    @Override
    protected void onDestroy() {
        StaticData.getInstance().setPersonalflag(false);
        super.onDestroy();
    }

    @BindView(R.id.tool_bar)
    Toolbar mToolbar;

    @BindView(R.id.main_list)
    RecyclerView main_list;

    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.headImageView)
    CircleImageView head;
    @BindView(R.id.miaoshu)
    TextView description;
    @BindView(R.id.fensi)
    TextView follower;
    @BindView(R.id.guanzhu)
    TextView friends;
    @BindView(R.id.weibo)
    TextView weibo;
    @BindView(R.id.frame)
    FrameLayout frame;
    @BindView(R.id.coll)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;

    @BindView(R.id.back)
    FrameLayout back;

    @BindView(R.id.pname)
    TextView pname;

    private PersonalListAdapter ListAdapter;
    private Prism prism;
    private CollapsingToolbarLayoutState state;
    private boolean isActive;//是否在前台
    private boolean LoadMoreFlag;//是否正在加载，防止多次加载请求
    private TextView loadingtxt;


    private enum CollapsingToolbarLayoutState {
        EXPANDED,
        COLLAPSED,
        INTERNEDIATE //中间
    }
    private PersonalAC instance;
    public PersonalInfoActivity() {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        Intent intent=getIntent();
//        name=intent.getStringExtra("name");
        instance=new PersonalAC(this);

        setSupportActionBar(mToolbar);


        prism = Prism.Builder.newInstance()
                .background(getWindow())
                .background(frame)
                .build();

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                if (i == 0) {
                    if (state != CollapsingToolbarLayoutState.EXPANDED) {
                        state = CollapsingToolbarLayoutState.EXPANDED;//修改状态标记为展开
                        back.setVisibility(View.INVISIBLE);
                        pname.setVisibility(View.INVISIBLE);
                    }
                } else if (Math.abs(i) >= appBarLayout.getTotalScrollRange()) {
                    if (state != CollapsingToolbarLayoutState.COLLAPSED) {
                        mToolbar.setElevation(0);
                        state = CollapsingToolbarLayoutState.COLLAPSED;//修改状态标记为折叠
                        back.setVisibility(View.VISIBLE);
                        pname.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (state != CollapsingToolbarLayoutState.INTERNEDIATE) {
                        state = CollapsingToolbarLayoutState.INTERNEDIATE;//修改状态标记为中间
                        back.setVisibility(View.INVISIBLE);
                        pname.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });
        setTitle("");

        Subscriber<DetialsInfo> mSubscriber=instance.getDetialsSubscriber();
        Subscriber<Pic_List_Info> mSubscriber2=instance.getPicSubscriber();

        ListAdapter=new PersonalListAdapter(this,null,mSubscriber,mSubscriber2);

        LinearLayoutManager manager = new WrapContentLinearLayoutManager(main_list.getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        main_list.setLayoutManager(manager);
        main_list.setAdapter(ListAdapter);

        instance.getWbData(0,intent.getStringExtra("name"));

        EndlessRecyclerOnScrollListener end=new EndlessRecyclerOnScrollListener(manager) {
            @Override
            public void onLoadMore(int currentPage) {
                if(StaticData.getInstance().getData().size()>0&&LoadMoreFlag==false)
                {
                    LoadMoreFlag=true;
                    instance.getWbData(StaticData.getInstance().getPersonaldata().get(StaticData.getInstance().getPersonaldata().size()-1).getId(),name.getText().toString());//-1代表获取个人信息
                }
            }
        };//上拉加载
        main_list.addOnScrollListener(end);
    }

    @OnClick(R.id.back)
    public void backClick(View v)
    {
        this.onBackPressed();
    }
    @OnClick(R.id.frame)
    public void frameClick(View v)
    {
        main_list.scrollToPosition(0);
        appBarLayout.setExpanded(true);
    }
    @Override
    protected void onResume() {
        prism.setColor(getResources().getColor(ColorThemeUtils.getColor(instance.getThemeColor())));
        instance.setData();
 //       ListAdapter.notifyDataSetChanged();
        super.onResume();
        isActive=true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setRefresh(boolean refresh, boolean isScrollToTop) {

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
        intent.setClass(this,DetialActivity.class);
        startActivity(intent);
    }

    @Override
    public void toActivity(Pic_List_Info pic_list_info) {
        Intent intent=new Intent();
        intent.putStringArrayListExtra("Largeurl",pic_list_info.getLarg_url());
        intent.putExtra("position",pic_list_info.getPosition());
        intent.setClass(this,ImageActivity.class);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this,pic_list_info.getView(),"image").toBundle());
       // startActivity(intent);
    }

    @Override
    public void toActivity(User user) {

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
    public void onEventMainThread(PersonalACInfo event) {
        if(!name.getText().equals(event.getName())&&isActive)
        {
            pname.setText(event.getName());
            name.setText(event.getName());
            if(!event.getDescription().equals(""))
                description.setText(event.getDescription());
            else
                description.setText("暂无简介");
            follower.setText("粉丝数："+event.getFollowers_count());
            friends.setText("关注数："+event.getFriends_count());
            weibo.setText("微博数："+event.getStatuses_count());
            if(event.getAvatar_large()!=null)
                Glide.with(this).load(event.getAvatar_large()).into(head);

        }
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
            instance.getWbData(StaticData.getInstance().getPersonaldata().get(StaticData.getInstance().getPersonaldata().size()-1).getId(),name.getText().toString());
        }

    }
}
