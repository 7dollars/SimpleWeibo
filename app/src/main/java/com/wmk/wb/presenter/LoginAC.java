package com.wmk.wb.presenter;

import android.net.Uri;

/**
 * Created by wmk on 2017/4/2.
 */

public class LoginAC extends BasePresenter{
    public String getCodeFromURL(String url) {
        Uri uri=Uri.parse(url);
        String code= uri.getQueryParameter("code");

        return code;
    }
    public String getTokenFromURL(String url) {
        Uri uri=Uri.parse(url);
        String code= uri.getQueryParameter("access_token");
        return code;
    }
    public String getUidFromURL(String url) {
        Uri uri=Uri.parse(url);
        String code= uri.getQueryParameter("uid");
        return code;
    }
}
