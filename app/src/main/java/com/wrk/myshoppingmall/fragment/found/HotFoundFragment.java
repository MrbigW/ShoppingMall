package com.wrk.myshoppingmall.fragment.found;

import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.wrk.myshoppingmall.R;
import com.wrk.myshoppingmall.adapter.found.HotPostListViewAdapter;
import com.wrk.myshoppingmall.bean.HotPostBean;
import com.wrk.myshoppingmall.common.BaseFragment;
import com.wrk.myshoppingmall.utils.Constants;

import java.util.List;

import butterknife.BindView;

/**
 * Created by MrbigW on 2016/11/27.
 * weChat:1024057635
 * GitHub:MrbigW
 * Usage: -.-
 * -------------------=.=------------------------
 */

public class HotFoundFragment extends BaseFragment {
    @BindView(R.id.lv_hot_post)
    ListView lvHotPost;

    private List<HotPostBean.ResultBean> result;

    @Override
    protected String getUrl() {
        return Constants.HOT_POST_URL;
    }

    @Override
    protected void initData(String content) {
        processData(content);
        HotPostListViewAdapter adapter = new HotPostListViewAdapter(getActivity(), result);
        lvHotPost.setAdapter(adapter);
    }

    private void processData(String content) {
        result = JSON.parseObject(content, HotPostBean.class).getResult();
    }

    @Override
    protected void initTitle() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_found_hot;
    }


}
