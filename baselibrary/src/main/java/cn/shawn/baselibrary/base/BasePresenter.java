package cn.shawn.baselibrary.base;

import java.lang.ref.SoftReference;

/**
 * Created by daopeng on 2017/9/25.
 */

public class BasePresenter<V extends IBaseView> {

    private SoftReference<V> mViewReference;

    public void onAttachToView(V view){
        mViewReference = new SoftReference(view);
    }

    public V getRealView(){
        return mViewReference == null? null:mViewReference.get();
    }

    public void detachView(){
        if(mViewReference != null){
            mViewReference.clear();
        }
    }

    private boolean isDetached(){
        return getRealView() == null;
    }
}
