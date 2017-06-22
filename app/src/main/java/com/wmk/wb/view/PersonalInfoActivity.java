package com.wmk.wb.view;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.stylingandroid.prism.Prism;
import com.wmk.wb.R;
import com.wmk.wb.model.StaticData;
import com.wmk.wb.model.bean.DetialsInfo;
import com.wmk.wb.model.bean.Pic_List_Info;
import com.wmk.wb.model.bean.retjson.User;
import com.wmk.wb.presenter.MainAC;
import com.wmk.wb.presenter.PersonalAC;
import com.wmk.wb.presenter.adapter.MainListAdapter;
import com.wmk.wb.presenter.adapter.PersonalListAdapter;
import com.wmk.wb.utils.EndlessRecyclerOnScrollListener;
import com.wmk.wb.view.Interface.IMain;
import com.wmk.wb.view.Interface.IPersonal;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
/*
与MainActivity共用一个presenter
*/

public class PersonalInfoActivity extends AppCompatActivity implements IPersonal{
    @Override
    protected void onDestroy() {
        StaticData.getInstance().setPersonalflag(false);
        super.onDestroy();
    }

    @BindView(R.id.tool_bar)
    Toolbar mToolbar;

    @BindView(R.id.main_list)
    RecyclerView main_list;

    private PersonalListAdapter ListAdapter;
    private Prism prism;
    public String name;

    private PersonalAC instance;
    public PersonalInfoActivity() {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        ButterKnife.bind(this);

        Intent intent=getIntent();
        name=intent.getStringExtra("name");
        instance=new PersonalAC(this);

        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.mipmap.ic_arrow_back_white_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);

        prism = Prism.Builder.newInstance()
                .background(mToolbar)
                .background(getWindow())
                .build();

        setTitle(name+"的微博");

        Subscriber<DetialsInfo> mSubscriber=instance.getDetialsSubscriber();
        Subscriber<Pic_List_Info> mSubscriber2=instance.getPicSubscriber();

        ListAdapter=new PersonalListAdapter(this,null,mSubscriber,mSubscriber2);

        LinearLayoutManager manager = new LinearLayoutManager(main_list.getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        main_list.setLayoutManager(manager);
        main_list.setAdapter(ListAdapter);

        instance.getWbData(0,intent.getStringExtra("name"));

        EndlessRecyclerOnScrollListener end=new EndlessRecyclerOnScrollListener(manager) {
            @Override
            public void onLoadMore(int currentPage) {
                if(StaticData.getInstance().getData().size()>0)
                {
                    instance.getWbData(StaticData.getInstance().getPersonaldata().get(StaticData.getInstance().getPersonaldata().size()-1).getId(),name);//-1代表获取个人信息
                }
            }
        };//上拉加载
        main_list.addOnScrollListener(end);
    }

    @Override
    protected void onResume() {

        prism.setColor(getResources().getColor(instance.getThemeColor()));
        instance.setPersonalFlag(true);
        instance.setData();
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {

            case android.R.id.home:
            {
                onBackPressed();
                break;
            }
        }
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

}
