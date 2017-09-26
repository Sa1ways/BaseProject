package cn.shawn.baseproject.http;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;


/**
 * Created by daopeng on 2017/9/26.
 */

public interface GitHubService {
    @GET("users/{user}/repos")
    Observable<List<Repo>> listRepos(@Path("user") String user);
}
