package cn.shawn.baseproject.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.widget.ImageView;

import java.util.List;

import cn.shawn.baselibrary.view.recyclerview.iinterface.OnRefreshLoadListener;
import cn.shawn.baselibrary.view.recyclerview.project.RlRecyclerView;
import cn.shawn.baseproject.R;
import cn.shawn.baseproject.adapter.GitInfoAdapter;
import cn.shawn.baseproject.base.BaseActivity;
import cn.shawn.baseproject.http.Repo;

public class MainActivity extends BaseActivity<IMainView,MainPresenter> implements IMainView, OnRefreshLoadListener {

    private ImageView ivBack;
    private RlRecyclerView rvMain;
    private GitInfoAdapter mAdapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected MainPresenter initPresenter() {
        return new MainPresenter();
    }

    @Override
    protected void initView() {
        ivBack = ((ImageView)findViewById(R.id.iv_back));
        //ivBack.setImageResource(R.mipmap.icon_cancel);
        //appendTitleViewTopPaddingByStatusBar(findViewById(R.id.fl_title));
        rvMain = (RlRecyclerView) findViewById(R.id.rv_main);
        rvMain.setLayoutManager(new LinearLayoutManager(this));
        rvMain.setRefreshLoadListener(this);
        mAdapter = new GitInfoAdapter();
        rvMain.setAdapter(mAdapter);
    }


    @Override
    protected void initData() {
        mPresenterType.getMockData();
    }

    @Override
    protected boolean needCloseInputWhenTouchSpace() {
        return true;
    }

    @Override
    public void onGotInfo(List<Repo> data) {
        rvMain.stopRefresh();
        mAdapter.showData(data);
    }

    @Override
    public void onGotMoreInfo(List<Repo> data) {
        rvMain.stopLoadingMore();
        mAdapter.appendData(data);
    }

    @Override
    public void onRefreshing() {
        mPresenterType.getMockData();
    }

    @Override
    public void onLoadingMore() {
        mPresenterType.getMoreMockData();
    }
}
