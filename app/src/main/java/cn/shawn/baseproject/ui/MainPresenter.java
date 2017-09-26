package cn.shawn.baseproject.ui;

import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cn.shawn.baselibrary.base.BasePresenter;
import cn.shawn.baselibrary.http.HttpSubscriber;
import cn.shawn.baselibrary.utils.ToastUtil;
import cn.shawn.baseproject.http.Repo;
import cn.shawn.baseproject.http.RequestManager;
import io.reactivex.annotations.NonNull;

import static cn.shawn.baseproject.http.RetrofitTest.TAG;

/**
 * Created by daopeng on 2017/9/26.
 */

public class MainPresenter extends BasePresenter<IMainView> {

    public void getGitInfo(){
        RequestManager.getInstance().getRepoData(new HttpSubscriber<List<Repo>>() {
            @Override
            public void onRequestSuccess(@NonNull List<Repo> repo) {
                Log.i(TAG, "onRequestSuccess: "+repo.size());
                if(isDetached()) return;
                getRealView().onGotInfo(repo);
            }

            @Override
            public void onRequestFailed(@NonNull Throwable e) {
                Log.i(TAG, "onRequestFailed: "+e);
                if(isDetached()) return;
                ToastUtil.showToast(e.getMessage().toString());
            }
        });
    }

    public void getMockData(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<Repo> data = new ArrayList<Repo>();
                for (int i = 0; i < 10; i++) {
                    data.add(null);
                }
                getRealView().onGotInfo(data);
            }
        },1000);
    }

    public void getMoreMockData(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<Repo> data = new ArrayList<Repo>();
                for (int i = 0; i < 10; i++) {
                    data.add(null);
                }
                getRealView().onGotMoreInfo(data);
            }
        },1000);
    }
}
