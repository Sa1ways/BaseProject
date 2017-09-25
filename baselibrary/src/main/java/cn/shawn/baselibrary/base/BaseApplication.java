package cn.shawn.baselibrary.base;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import cn.shawn.baselibrary.utils.ToastUtil;

/**
 * Created by daopeng on 2017/9/25.
 */

public class BaseApplication extends Application {

    public static final String ACTION_FINISH = "com.android.base.library.intent.action.FINISH";

    private static Context mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        ToastUtil.init(this);
    }

    public static Context getContext(){
        return mInstance;
    }

    protected void exitApp(){
        sendBroadcast(new Intent(ACTION_FINISH));
    }
}
