package com.wrk.myshoppingmall.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wrk.myshoppingmall.ui.LoadingPager;

import butterknife.ButterKnife;

/**
 * Created by MrbigW on 2016/11/14.
 * weChat:1024057635
 * GitHub:MrbigW
 * Usage: -.-
 * -------------------=.=------------------------
 */

public abstract class BaseFragment extends Fragment {

    public LoadingPager mLoadingPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // 绑定布局
        mLoadingPager = new LoadingPager(getActivity()) {
            @Override
            public int layoutId() {
                return getLayoutId();
            }

            @Override
            protected void onSuccess(ResultState resultState, View view_success) {
                ButterKnife.bind(BaseFragment.this, view_success); // 绑定布局
                initTitle();
                initData(resultState.getContent());
            }

            @Override
            protected String url() {
                return getUrl();
            }
        };

        return mLoadingPager;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        show();
    }

    public void show() {
        mLoadingPager.show();
    }

    protected abstract String getUrl();

    protected abstract void initData(String content);

    protected abstract void initTitle();

    public abstract int getLayoutId();

}






























