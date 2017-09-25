package cn.shawn.baselibrary.utils;

import android.content.Context;

import java.lang.reflect.Field;

/**
 * Created by daopeng on 2017/9/25.
 */

public class ViewUtil {

    public static int getStatusBarHeight(Context context) {
        Class<?> c;
        Object obj;
        Field field;
        int x, statusBarHeight = 0;

        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusBarHeight;
    }
}
