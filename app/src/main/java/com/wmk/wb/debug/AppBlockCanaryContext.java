package com.wmk.wb.debug;

import com.github.moduth.blockcanary.BlockCanaryContext;

/**
 * Created by wmk on 2017/8/1.
 */

public class AppBlockCanaryContext extends BlockCanaryContext {
    @Override
    public int provideBlockThreshold() {
        return 16;
    }
}
