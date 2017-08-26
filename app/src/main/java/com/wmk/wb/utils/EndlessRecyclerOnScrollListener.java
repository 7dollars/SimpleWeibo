package com.wmk.wb.utils;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by wmk on 2017/4/11.
 */

public abstract class EndlessRecyclerOnScrollListener extends
        RecyclerView.OnScrollListener {

    private boolean loading = true;
    int firstVisibleItem, visibleItemCount, totalItemCount,oldCount;

    private LinearLayoutManager mLinearLayoutManager;

    public EndlessRecyclerOnScrollListener(
            LinearLayoutManager linearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        if(!recyclerView.canScrollVertically(1)) {
            if (!loading) {
                onLoadMore(0);
                loading = true;
            }
        }
        else {
            loading=false;
        }

    }
    public abstract void onLoadMore(int currentPage);
}