package com.wmk.wb.debug;

import android.app.Application;

import com.github.moduth.blockcanary.BlockCanary;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by wmk on 2017/6/13.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        BlockDetectByPrinter.start();
     //   BlockCanary.install(this,  new AppBlockCanaryContext()).start();
    //    LeakCanary.install(this);
        // Normal app init code...
    }
}
