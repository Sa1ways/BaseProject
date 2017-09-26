package cn.shawn.baseproject;

import cn.shawn.baselibrary.base.BaseApplication;
import cn.shawn.baselibrary.http.HttpHelper;

/**
 * Created by daopeng on 2017/9/25.
 */

public class App extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        HttpHelper.init(this);
    }
}
