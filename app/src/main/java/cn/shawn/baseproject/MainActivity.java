package cn.shawn.baseproject;

import android.widget.ImageView;
import cn.shawn.baselibrary.base.BasePresenter;
import cn.shawn.baselibrary.base.IBaseView;
import cn.shawn.baseproject.base.BaseActivity;

public class MainActivity extends BaseActivity<IBaseView,BasePresenter<IBaseView>> implements IBaseView {

    private ImageView ivBack;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected BasePresenter initPresenter() {
        return new BasePresenter();
    }

    @Override
    protected void initView() {
        ivBack = ((ImageView)findViewById(R.id.iv_back));
        //ivBack.setImageResource(R.mipmap.icon_cancel);
        //appendTitleViewTopPaddingByStatusBar(findViewById(R.id.fl_title));
    }


    @Override
    protected void initData() {

    }

    @Override
    protected boolean needCloseInputWhenTouchSpace() {
        return true;
    }
}
