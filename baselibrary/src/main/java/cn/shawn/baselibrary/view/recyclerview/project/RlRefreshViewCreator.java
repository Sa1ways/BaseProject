package cn.shawn.baselibrary.view.recyclerview.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.shawn.baselibrary.R;
import cn.shawn.baselibrary.view.recyclerview.iinterface.RefreshViewCreator;
import cn.shawn.baselibrary.view.recyclerview.view.RefreshLoadRecyclerView;
import cn.shawn.baselibrary.view.view.RefreshCircleView;

/**
 * Created by root on 17-6-24.
 */

public class RlRefreshViewCreator implements RefreshViewCreator {
    private TextView tvTips;
    private RefreshCircleView mCircleRefresh;

    @Override
    public View getRefreshView(Context context, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_refresh_recycler, parent, false);
        tvTips =  view.findViewById(R.id.tv_tips);
        mCircleRefresh = view.findViewById(R.id.refresh);
        return view;
    }

    @Override
    public void onPull(int pullHeight, int totalHeight, int state) {
        if (pullHeight <= 0)
            mCircleRefresh.setSwipeFactor((totalHeight - Math.abs(pullHeight)) / (float) totalHeight);
        if (state == RefreshLoadRecyclerView.REFRESH_STATE_NORMAL) {
            tvTips.setText("下拉刷新");
            mCircleRefresh.showArrow(true);
        } else if (state == RefreshLoadRecyclerView.REFRESH_STATE_LOOSE_TO_REFRESH) {
            tvTips.setText("松开刷新");
            mCircleRefresh.showArrow(false);
        } else if (state == RefreshLoadRecyclerView.REFRESH_STATE_PULL) {
            tvTips.setText("下拉刷新");
            mCircleRefresh.showArrow(true);
        }
    }

    @Override
    public void onRefreshing() {
        tvTips.setText("刷新中...");
        mCircleRefresh.setSwipeFactor(1);

    }

    @Override
    public void onRefreshFinish() {
        tvTips.setText("刷新完成~");
    }
}