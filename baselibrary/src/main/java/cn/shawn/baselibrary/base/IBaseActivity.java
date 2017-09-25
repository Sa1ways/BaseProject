package cn.shawn.baselibrary.base;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import cn.shawn.baselibrary.utils.InputMethodUtils;
import cn.shawn.baselibrary.utils.ToastUtil;
import cn.shawn.baselibrary.utils.ViewUtil;

/**
 * Created by daopeng on 2017/9/25.
 */
//
public abstract class IBaseActivity<ViewType extends IBaseView, PresenterType extends BasePresenter<ViewType>> extends AppCompatActivity {

    protected String TAG = this.getClass().getSimpleName();

    protected PresenterType mPresenterType;

    protected boolean hadModifiedTopPadding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        //状态栏改色
        initStatusBar();
        initTitleBar();
        initMvp();
        initView();
        initData();
        //重置 资源
        initResources();
        //注册与application中的exit方法绑定
        registerReceiver(mExitReceiver,new IntentFilter(BaseApplication.ACTION_FINISH));
    }

    protected final void initStatusBar(){
        if(!needTranslucentStatusBar()) return;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
    }

    protected void initMvp(){
        mPresenterType = initPresenter();
        if(this != null && mPresenterType != null){
            mPresenterType.onAttachToView((ViewType) this);
        }
    }

    protected abstract int getContentView();

    protected abstract void initTitleBar();

    protected abstract PresenterType initPresenter();

    protected abstract void initView();

    protected abstract void initData();

    protected boolean needTranslucentStatusBar(){
        return false;
    }

    protected boolean needCloseInputWhenTouchSpace(){
        return false;
    }

    private void initResources() {
        //配置App默认的资源 防止修改系统字体大小导致的适配问题
        Resources res = getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);//保证Intent的及时性
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mExitReceiver);
        mPresenterType.detachView();
    }

    private BroadcastReceiver mExitReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(BaseApplication.ACTION_FINISH.equals(intent.getAction())){
                IBaseActivity.this.finish();
            }
        }
    };

    //--------------------------- 动态权限的封装 --------------------------
    protected void requestSdCardPermission( ){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED){
                onGotSdCardPermissionSucceed();
            }else{
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        PermissionCode.REQUEST_SDCARD_PERMISSION);
            }
        }else{
            onGotSdCardPermissionSucceed();
        }
    }

    protected void requestCameraPremission(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED){
                onGotCameraPermissionSucceed();
            }else{
                requestPermissions(new String[]{Manifest.permission.CAMERA},
                        PermissionCode.REQUEST_CAMERA_PERMISSION);
            }
        }else{
            onGotCameraPermissionSucceed();
        }
    }

    protected void requestFinePremission(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED){
                onGotFinePermissionSucceed();
            }else{
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        PermissionCode.REQUEST_FINE_PERMISSION);
            }
        }else{
            onGotFinePermissionSucceed();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length == 0) return;
        switch (requestCode){
            case PermissionCode.REQUEST_CAMERA_PERMISSION:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED) onGotCameraPermissionSucceed();
                else onGotCameraPermissionFailed();
                break;
            case PermissionCode.REQUEST_FINE_PERMISSION:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED) onGotFinePermissionSucceed();
                else onGotFinePermissionFailed();
                break;
            case PermissionCode.REQUEST_SDCARD_PERMISSION:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED) onGotSdCardPermissionSucceed();
                else onGotSdCardPermissionFailed();
                break;
        }
    }

    protected void onGotCameraPermissionFailed() {}

    protected  void onGotCameraPermissionSucceed(){}

    protected void onGotFinePermissionFailed() {}

    protected  void onGotFinePermissionSucceed(){}

    protected void onGotSdCardPermissionFailed() {}

    protected  void onGotSdCardPermissionSucceed(){}

    //点击输入框外部弹回软键盘

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (needCloseInputWhenTouchSpace() && ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (InputMethodUtils.isShouldHideInput(v, ev)) {
                InputMethodUtils.forceHideInputMethod(v, this);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    //--------------------------- functional function----------------------
    //动态调整titleBar的topPadding使状态栏显示正常
    protected final void appendTitleViewTopPaddingByStatusBar(View titleBarView, View... views) {
        ViewGroup.LayoutParams lp = titleBarView.getLayoutParams();
        lp.height = lp.height + ViewUtil.getStatusBarHeight(this);
        if (views != null && views.length > 0) {
            for (View view : views) {
                if (view != null) {
                    view.setPadding(view.getPaddingLeft(), view.getPaddingTop() + ViewUtil.getStatusBarHeight(this), view.getPaddingRight(),view.getPaddingBottom());
                }
            }
        }
    }

    protected void showToast(CharSequence content){
        ToastUtil.showToast(content);
    }

    protected void showToast(@IntegerRes int res){
        ToastUtil.showToast(res);
    }

}
