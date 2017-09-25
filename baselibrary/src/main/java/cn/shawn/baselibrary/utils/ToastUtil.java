package cn.shawn.baselibrary.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by daopeng on 2017/9/25.
 */

public class ToastUtil {

    private static Context mContext;

    private static Toast toast;

    private ToastUtil(){}

    public static void init(Context context){
        mContext =context;
    }

    public static void showToast(CharSequence content){
        if(toast == null){
            toast= Toast.makeText(mContext,content,Toast.LENGTH_SHORT);
        }else{
            toast.setText(content);
            toast.setDuration(content.length() > 8?Toast.LENGTH_LONG:Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM,0,200);
        }
        toast.show();
    }

    public static void showToast(int res){
        String content = mContext.getResources().getString(res);
        showToast(content);
    }

}
