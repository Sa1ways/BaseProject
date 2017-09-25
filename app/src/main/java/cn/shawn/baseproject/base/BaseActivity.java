package cn.shawn.baseproject.base;

import android.util.Log;
import android.view.View;

import cn.shawn.baselibrary.base.BasePresenter;
import cn.shawn.baselibrary.base.IBaseActivity;
import cn.shawn.baselibrary.base.IBaseView;
import cn.shawn.baseproject.R;

/**
 * Created by daopeng on 2017/9/25.
 * 抽取项目的TitleBar 和 网络错误的布局
 */

public abstract class BaseActivity<ViewType extends IBaseView,PresenterType extends BasePresenter<ViewType>>
        extends IBaseActivity<ViewType,PresenterType> {

    //title的根布局
    protected View flTitle;

    @Override
    protected void initTitleBar() {
        flTitle = findViewById(R.id.fl_title);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!hadModifiedTopPadding){
            hadModifiedTopPadding = true;
            Log.i(TAG, "onResume: "+flTitle);
            appendTitleViewTopPaddingByStatusBar(flTitle);
        }
    }

    @Override
    protected boolean needTranslucentStatusBar() {
        return true;
    }
}
