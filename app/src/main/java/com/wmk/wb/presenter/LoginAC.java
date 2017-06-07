package com.wmk.wb.presenter;

import android.net.Uri;

/**
 * Created by wmk on 2017/4/2.
 */

public class LoginAC{
    public String getTokenFromURL(String url) {
        Uri uri=Uri.parse(url);
        String code= uri.getQueryParameter("code");

        return code;
    }
}
