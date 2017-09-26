package cn.shawn.http;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by daopeng on 2017/9/26.
 */

public class HttpHelper {

    public static final String TAG = HttpHelper.class.getSimpleName();

    public static final int DEFAULT_TIMEOUT = 30;

    private Map<String,Object> mServiceStore = new HashMap<>();

    private static HttpHelper mInstance;

    private static Context mContext;

    private HttpHelper(){}

    public static void init(Context context){
        mContext = context;
    }

    public static HttpHelper getInstance(){
        if(mInstance == null){
            synchronized (HttpHelper.class){
                if(mInstance == null){
                    mInstance = new HttpHelper();
                }
            }
        }
        return mInstance;
    }

    public <ServiceType> ServiceType getRetrofitService(String baseUrl,Class<ServiceType> serviceTypeClass){
        String serviceName = serviceTypeClass.getName();
        if(mServiceStore.containsKey(serviceName)){
            return (ServiceType) mServiceStore.get(serviceName);
        }else{
            ServiceType service = getRetrofit(baseUrl).create(serviceTypeClass);
            mServiceStore.put(serviceName,service);
            return service;
        }
    }

    public Retrofit getRetrofit(String baseUrl){
        return getRetrofit(baseUrl, null);
    }

    public Retrofit getRetrofit(String baseUrl, OkHttpClient client){
        if(client == null){
            client = generateDefaultClient();
        }
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory( RxJava2CallAdapterFactory.create())
                .build();
    }

    private OkHttpClient generateDefaultClient() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(new LogInterceptor())
                .addInterceptor(new CacheControlInterceptor(mContext))
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT,TimeUnit.SECONDS)
                .connectTimeout(DEFAULT_TIMEOUT,TimeUnit.SECONDS)
                .build();
        return client;
    }

}
