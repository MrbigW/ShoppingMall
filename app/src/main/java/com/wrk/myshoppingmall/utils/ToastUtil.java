package com.wrk.myshoppingmall.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by MrbigW on 2016/10/27.
 * weChat:1024057635
 * Github:MrbigW
 * Usage: toast类
 * -------------------=.=------------------------
 */

public class ToastUtil {
    private static Toast toast;

    public static void showToast(Context context, String content) {
        if (toast == null) {
            toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }
}
