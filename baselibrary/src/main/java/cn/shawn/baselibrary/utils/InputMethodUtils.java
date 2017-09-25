package cn.shawn.baselibrary.utils;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by daopeng on 2017/9/25.
 */

public class InputMethodUtils {

    /**
     * 强行隐藏软键盘
     */
    public static boolean forceHideInputMethod(View view, Context context) {
        if (view == null) {
            return false;
        }
        InputMethodManager manager = ((InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE));
        return manager != null &&
                manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 强行显示软键盘
     */
    public static boolean forceShowInputMethod(final View view, Context context) {
        final InputMethodManager manager = ((InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE));
        if (manager == null) {
            return false;
        }
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                manager.showSoftInput(view, InputMethodManager.SHOW_FORCED);
            }
        }, 500);
        return true;
    }

    /**
     * 如果有软键盘，那么隐藏它；反之，把它显示出来。
     */
    public static void toggleInputMethod(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     */
    public static boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return true;
    }
}