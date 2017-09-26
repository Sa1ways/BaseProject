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

    protected V getRealView(){
        return mViewReference == null? null:mViewReference.get();
    }

    protected void detachView(){
        if(mViewReference != null){
            mViewReference.clear();
        }
    }

    protected boolean isDetached(){
        return getRealView() == null;
    }
}
