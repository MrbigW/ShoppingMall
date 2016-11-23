package com.wrk.myshoppingmall.common;

import android.app.Activity;

import java.util.Stack;

/**
 * Created by MrbigW on 2016/11/12.
 * weChat:1024057635
 * GitHub:MrbigW
 * Usage: 统一应用程序中所有的Activity的栈管理（单例：饿汉式，懒汉式）
 * 涉及到activity的添加、删除指定、删除当前、删除所有、返回栈大小的方法
 * -------------------=.=------------------------
 */

public class ActivityManager {

    // 使用饿汉式实现单例模型
    private ActivityManager() {
    }

    private static ActivityManager mInstance = new ActivityManager();

    public static ActivityManager getInstance() {
        return mInstance;
    }

    // 提供操作Acitivity的容器:Stack
    private Stack<Activity> mActivityStack = new Stack<>();

    // Activity的添加
    public void add(Activity activity) {
        if (activity != null) {
            mActivityStack.add(activity);
        }
    }

    // 删除指定的Activity
    public void remove(Activity activity) {
//        for (int i = 0; i < mActivityStack.size(); i++) {
//            if (activity != null && activity.getClass().equals(mActivityStack.get(i).getClass())) {
//                // 销毁当前Activity对象
//                activity.finish();
//                // 将指定的activity对象从栈空间移除
//                mActivityStack.remove(i);
//            }
//        }

        for (int i = mActivityStack.size() - 1; i >= 0; i--) {
            if (activity != null && activity.getClass().equals(mActivityStack.get(i).getClass())) {
                // 销毁当前Activity对象
                mActivityStack.get(i).finish();
                // 将指定的activity对象从栈空间移除
                mActivityStack.remove(i);
            }
        }
    }

    // 删除当前的Acitivity
    public void removeCurrent() {
//        // 方式一：
//        Activity activity = mActivityStack.get(mActivityStack.size() - 1);
//        activity.finish();
//        mActivityStack.remove(mActivityStack.size() - 1);
//        // 方式二：
        mActivityStack.lastElement().finish();
        mActivityStack.remove(mActivityStack.lastElement());
//        // 方式三
//        mActivityStack.pop().finish();

    }

    // 删除所有的Activity
    public void removeAll() {
        for (int i = mActivityStack.size() - 1; i >= 0; i--) {
            // 销毁当前Activity对象
            mActivityStack.get(i).finish();
            // 将指定的activity对象从栈空间移除
            mActivityStack.remove(i);
        }
    }

    // 得到Activity数量
    public int getAcitivitySize() {
        return mActivityStack.size();
    }

}






























