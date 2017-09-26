package cn.shawn.baselibrary.http;

import android.content.Context;

import java.io.IOException;
import cn.shawn.baselibrary.http.utils.NetUtil;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by daopeng on 2017/9/26.
 */

public class CacheControlInterceptor implements Interceptor {
    private Context mContext;

    public CacheControlInterceptor(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!NetUtil.isNetworkConnected(mContext)) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        }

        Response response = chain.proceed(request);

        if (NetUtil.isNetworkConnected(mContext)) {
            int maxAge = 60 * 60; // read from cache for 1 minute
            response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .build();
        } else {
            int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
            response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }
        return response;
    }
}
