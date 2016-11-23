package com.wrk.myshoppingmall.common;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import butterknife.ButterKnife;

/**
 * Created by MrbigW on 2016/11/16.
 * weChat:1024057635
 * GitHub:MrbigW
 * Usage: Activity的基类
 * -------------------=.=------------------------
 */

public abstract class BaseActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutId());

        ButterKnife.bind(this);

        // 将MainActivity加入自定义栈
        ActivityManager.getInstance().add(this);

        initData();
    }


    /**
     * 初始化内容数据
     */
    protected abstract void initData();

    /**
     * 布局文件
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 销毁当前Activity
     */
    public void removeCurrentActivity() {
        ActivityManager.getInstance().removeCurrent();
    }

    /**
     * 启动新的Activity
     *
     * @param activity
     * @param bundle
     */
    public void goToActivity(Class activity, Bundle bundle) {
        Intent intent = new Intent(this, activity);
        if (bundle != null && bundle.size() != 0) {
            intent.putExtra("data", bundle);
        }
        startActivity(intent);
    }

    /**
     * 销毁所有的Activity
     */
    public void removeAll() {
        ActivityManager.getInstance().removeAll();
    }


}




















