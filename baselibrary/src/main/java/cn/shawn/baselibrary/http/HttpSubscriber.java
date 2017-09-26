package cn.shawn.baselibrary.http;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by daopeng on 2017/9/26.
 */

public abstract class HttpSubscriber<T> implements Observer<T> {

    public abstract void onRequestSuccess(@NonNull T t);

    public abstract void onRequestFailed(@NonNull Throwable e);

    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onNext(@NonNull T t) {
        onRequestSuccess(t);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        onRequestFailed(e);
    }

    @Override
    public void onComplete() {

    }


}
