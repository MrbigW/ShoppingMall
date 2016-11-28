package com.wrk.myshoppingmall.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.wrk.myshoppingmall.R;
import com.wrk.myshoppingmall.common.BaseFragment;
import com.wrk.myshoppingmall.fragment.WeiTao.FlowListFragment;
import com.wrk.myshoppingmall.fragment.WeiTao.RandomListFragment;
import com.wrk.myshoppingmall.fragment.WeiTao.TagFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by MrbigW on 2016/11/21.
 * weChat:1024057635
 * GitHub:MrbigW
 * Usage: -.-
 * -------------------=.=------------------------
 */

public class WeiTaoFragment extends BaseFragment {
    @BindView(R.id.tl_1)
    SegmentTabLayout tl1;
    @BindView(R.id.fl_type)
    FrameLayout flType;

    private List<BaseFragment> mFragments;
    private Fragment mFragment;

    public FlowListFragment mFlowListFragment;
    public TagFragment tagFragment;
    public RandomListFragment mRandomListFragment;

    private int currentTab;

    @Override
    protected String getUrl() {
        return null;
    }

    @Override
    protected void initData(String content) {
        initFragment();
    }

    @Override
    protected void initTitle() {
        String[] titles = {"普通分类", "流式标签", "随机标签"};
        tl1.setTabData(titles);

        tl1.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                switchFragment(mFragment, mFragments.get(position));
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

    }

    public void switchFragment(Fragment fromFragment, BaseFragment nextFragment) {
        if (tl1 != null) {
            currentTab = tl1.getCurrentTab();
        }
        if (mFragment != nextFragment) {
            mFragment = nextFragment;
            if (nextFragment != null) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                //判断nextFragment是否添加
                if (!nextFragment.isAdded()) {
                    //隐藏当前Fragment
                    if (fromFragment != null) {
                        transaction.hide(fromFragment);
                    }
                    transaction.add(R.id.fl_type, nextFragment, "tagFragment").commit();
                } else {
                    //隐藏当前Fragment
                    if (fromFragment != null) {
                        transaction.hide(fromFragment);
                    }
                    transaction.show(nextFragment).commit();
                }
            }
        }
    }

    private void initFragment() {
        mFragments = new ArrayList<>();
        mFlowListFragment = new FlowListFragment();
        tagFragment = new TagFragment();
        mRandomListFragment = new RandomListFragment();

        mFragments.add(tagFragment);
        mFragments.add(mFlowListFragment);
        mFragments.add(mRandomListFragment);

        switchFragment(mFragment, mFragments.get(0));
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_weitao;
    }


}
