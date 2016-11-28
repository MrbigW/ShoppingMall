package com.wrk.myshoppingmall.fragment.found;

import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.wrk.myshoppingmall.R;
import com.wrk.myshoppingmall.adapter.found.NewPostListViewAdapter;
import com.wrk.myshoppingmall.bean.NewPostBean;
import com.wrk.myshoppingmall.common.BaseFragment;
import com.wrk.myshoppingmall.utils.Constants;

import java.util.List;

import butterknife.BindView;

/**
 * Created by MrbigW on 2016/11/26.
 * weChat:1024057635
 * GitHub:MrbigW
 * Usage: -.-
 * -------------------=.=------------------------
 */

public class NewFoundFragment extends BaseFragment {
    @BindView(R.id.lv_new_post)
    ListView lvNewPost;
    private List<NewPostBean.ResultBean> mResult;

    @Override
    protected String getUrl() {
        return Constants.NEW_POST_URL;
    }

    @Override
    protected void initData(String content) {
        processData(content);
        NewPostListViewAdapter adapter = new NewPostListViewAdapter(getActivity(), mResult);
        lvNewPost.setAdapter(adapter);
    }

    private void processData(String content) {
        mResult = JSON.parseObject(content, NewPostBean.class).getResult();
    }

    @Override
    protected void initTitle() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_found_new;
    }


}


















