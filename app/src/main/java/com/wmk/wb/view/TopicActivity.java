 package com.wmk.wb.view;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.stylingandroid.prism.Prism;
import com.wmk.wb.R;
import com.wmk.wb.model.StaticData;
import com.wmk.wb.model.WbDataStack;
import com.wmk.wb.model.bean.DetialsInfo;
import com.wmk.wb.model.bean.LoadingBus;
import com.wmk.wb.model.bean.Pic_List_Info;
import com.wmk.wb.model.bean.retjson.User;
import com.wmk.wb.model.bean.retjson.WbData;
import com.wmk.wb.presenter.TopicAC;
import com.wmk.wb.presenter.adapter.MainListAdapter;
import com.wmk.wb.presenter.adapter.PersonalListAdapter;
import com.wmk.wb.presenter.adapter.TopicListAdapter;
import com.wmk.wb.utils.ColorThemeUtils;
import com.wmk.wb.utils.EndlessRecyclerOnScrollListener;
import com.wmk.wb.utils.WrapContentLinearLayoutManager;
import com.wmk.wb.view.Interface.ITopic;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;

 public class TopicActivity extends BaseActivity implements ITopic{

     @BindView(R.id.tool_bar)
     Toolbar mToolbar;

     @BindView(R.id.topic_list)
     RecyclerView topic_list;

     @Override
     public void scrollToTop() {
         topic_list.scrollToPosition(0);
     }

     @BindView(R.id.progressBar)
     ProgressBar progress;

     private TopicAC instance;
     private Prism prism;
     private String topic;
     private boolean isActive;
     private TopicListAdapter ListAdapter;
     private boolean LoadMoreFlag=false;
     private TextView loadingtxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        instance=new TopicAC(this);

        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.mipmap.ic_arrow_back_white_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent=getIntent();
        topic=intent.getStringExtra("topic");
        setTitle(topic);

        prism = Prism.Builder.newInstance()
                .background(getWindow())
                .background(mToolbar)
                .build();
        Subscriber<DetialsInfo> mSubscriber=instance.getDetialsSubscriber();
        Subscriber<Pic_List_Info> mSubscriber2=instance.getPicSubscriber();

        instance.clearData();

        ListAdapter=new TopicListAdapter(this,null,mSubscriber,mSubscriber2);

        WrapContentLinearLayoutManager manager = new WrapContentLinearLayoutManager(topic_list.getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        topic_list.setLayoutManager(manager);
        topic_list.setAdapter(ListAdapter);

        topic=intent.getStringExtra("topic");
        instance.getWbData(this,topic,true);

        EndlessRecyclerOnScrollListener end=new EndlessRecyclerOnScrollListener(manager) {
            @Override
            public void onLoadMore(int currentPage) {
                if(WbDataStack.getInstance().getTop().getData().size()>0&&LoadMoreFlag==false)
                {
                    String t=topic;
                    setLoadMore(true);
                    instance.getWbData(TopicActivity.this,t,false);//
                }
            }
        };//上拉加载
        topic_list.addOnScrollListener(end);
    }

     @Override
     protected void onRestart() {
        super.onRestart();
     }

     @Override
     protected void onDestroy() {
         super.onDestroy();
     }

     @Override
     protected void onPause() {
        isActive=false;
         super.onPause();
     }

     @Override
     protected void onNewIntent(Intent intent) {
         super.onNewIntent(intent);
         if(intent.getStringExtra("topic").equals(topic))
             return;
         ListAdapter.notifyDataSetChanged();

         topic=intent.getStringExtra("topic");
         setTitle(topic);
         setLoadMore(true);
         instance.getWbData(this,topic,true);
     }

     @Override
     protected void onResume() {
        isActive=true;
         prism.setColor(getResources().getColor(ColorThemeUtils.getColor(instance.getThemeColor())));
         super.onResume();
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
     public void setLoadingFaild() {
         if(loadingtxt!=null)
             loadingtxt.setText("加载失败，请点击重试");
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
     public void setLoadMore(boolean isloading) {
        if(isloading)
            progress.setVisibility(View.VISIBLE);
        else
            progress.setVisibility(View.INVISIBLE);
         LoadMoreFlag=isloading;
     }

     @Subscribe
     public void onEventMainThread(LoadingBus event) {
         if(!isActive)
             return;
         loadingtxt=event.getLoading();
         if(!event.isPress())
             return;
         if (WbDataStack.getInstance().getTop().getData().size() > 0&&LoadMoreFlag==false) {
             setLoadMore(true);
             instance.getWbData(this,topic.toString(),false);
         }

     }

 }
