package com.wrk.myshoppingmall.common;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

/**
 * Created by MrbigW on 2016/11/20.
 * weChat:1024057635
 * GitHub:MrbigW
 * Usage: -.-
 * -------------------=.=------------------------
 */

public class MyApplication extends Application {

    public static Context mContext;
    public static Handler mHandler;
    public static Thread mainThread; // 获取主线程
    public static int mainThreadId;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this.getApplicationContext();
        mHandler = new Handler();
        mainThread = Thread.currentThread();
        mainThreadId = android.os.Process.myTid();// 获取当前主线程的ID


    }


}
