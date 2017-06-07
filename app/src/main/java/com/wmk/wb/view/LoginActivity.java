package com.wmk.wb.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        login_view.loadUrl("https://api.weibo.com/oauth2/authorize?client_id=3741569039&response_type=code&redirect_uri=https://api.weibo.com/oauth2/default.html");

        login_view.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onPageFinished(WebView view, String url) {
                String code;
                Intent intent=new Intent();
                LoginAC lgac=new LoginAC();
                code=lgac.getTokenFromURL(url);
                if(code!=null)
                {
                    intent.putExtra("CODE",code);
                    setResult(1,intent);
                    finish();
                }
                super.onPageFinished(view, url);
            }
        });
    }
}
