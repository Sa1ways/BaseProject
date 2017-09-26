package cn.shawn.baseproject.http;

import java.util.List;

import cn.shawn.baselibrary.http.HttpHelper;
import cn.shawn.baseproject.config.NetConfig;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by daopeng on 2017/9/26.
 */

public class RequestManager {

    private static RequestManager mInstance;

    private RequestManager(){}

    public static RequestManager getInstance(){
        if(mInstance == null){
            synchronized (RequestManager.class){
                if(mInstance == null){
                    mInstance = new RequestManager();
                }
            }
        }
        return mInstance;
    }

    private Observable doSubscribe(Observable observable){
        return observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    private GitHubService getGitHubService(){
        return HttpHelper.getInstance().getRetrofitService(NetConfig.BASE_URL,GitHubService.class);
    }

    public void getRepoData(Observer<List<Repo>> next){
        doSubscribe(getGitHubService().listRepos("octocat"))
                .subscribe(next);
    }

}
