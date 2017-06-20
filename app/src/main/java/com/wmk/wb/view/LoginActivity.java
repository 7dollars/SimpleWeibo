package com.wmk.wb.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wmk.wb.R;
import com.wmk.wb.presenter.LoginAC;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {


    @BindView(R.id.login_view) public WebView login_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        WebSettings webSettings = login_view.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setAppCacheEnabled(false);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(false);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        String myurl="https://api.weibo.com/oauth2/authorize?client_id=3741569039&response_type=code&redirect_uri=https://api.weibo.com/oauth2/default.html";
        String weico="https://open.weibo.cn/oauth2/authorize?client_id=211160679&redirect_uri=http://oauth.weico.cc&scope=email&Cdirect_messages_read&direct_messages_write&friendships_groups_read&friendships_groups_write&statuses_to_me_read&follow_app_official_microblog&invitation_write&response_type=code&display=mobile&packagename=com.eico.weico&key_hash=1e6e33db08f9192306c4afa0a61ad56c";
        login_view.loadUrl(weico);

        login_view.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onPageFinished(WebView view, String url) {
                String code,Token,uid;
                Intent intent=new Intent();
                LoginAC lgac=new LoginAC();
                code=lgac.getCodeFromURL(url);
                Token=lgac.getTokenFromURL(url);
                uid=lgac.getUidFromURL(url);
                if(code!=null)
                {
                    intent.putExtra("CODE",code);
                    setResult(1,intent);
                    finish();
                }
                if(Token!=null)
                {
                    intent.putExtra("Token",Token);
                    intent.putExtra("Uid",uid);
                    Log.e("123123",Token);
                    setResult(2,intent);
                    finish();
                }

                super.onPageFinished(view, url);
            }
        });
    }
}
