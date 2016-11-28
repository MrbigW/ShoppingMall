package com.wrk.myshoppingmall.fragment.WeiTao;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.wrk.myshoppingmall.R;
import com.wrk.myshoppingmall.bean.TagBean;
import com.wrk.myshoppingmall.common.BaseFragment;
import com.wrk.myshoppingmall.ui.FlowLayout;
import com.wrk.myshoppingmall.utils.Constants;
import com.wrk.myshoppingmall.utils.DrawUtils;
import com.wrk.myshoppingmall.utils.ToastUtil;
import com.wrk.myshoppingmall.utils.UIUtils;

import java.util.List;
import java.util.Random;

import butterknife.BindView;

/**
 * Created by MrbigW on 2016/11/26.
 * weChat:1024057635
 * GitHub:MrbigW
 * Usage: -.-
 * -------------------=.=------------------------
 */
public class FlowListFragment extends BaseFragment {
    @BindView(R.id.fl_type)
    FlowLayout flType;
    private List<TagBean.ResultBean> result;
    private Random mRandom;

    @Override
    protected String getUrl() {
        return Constants.TAG_URL;
    }

    @Override
    protected void initData(String content) {
        processData(content);
        initFlowLayout();
    }

    private void initFlowLayout() {
        for (int i = 0; i < result.size(); i++) {

            final TextView textView = new TextView(getActivity());

            textView.setText(result.get(i).getName());
            textView.setTextSize(UIUtils.dp2px(6));

            ViewGroup.MarginLayoutParams mp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            mp.leftMargin = UIUtils.dp2px(8);
            mp.topMargin = UIUtils.dp2px(8);
            mp.rightMargin = UIUtils.dp2px(8);
            mp.bottomMargin = UIUtils.dp2px(8);
            textView.setLayoutParams(mp);


            mRandom = new Random();
            final int red = mRandom.nextInt(210);
            final int green = mRandom.nextInt(210);
            final int blue = mRandom.nextInt(210);


            // 设置背景
            textView.setTextColor(Color.rgb(red, green, blue));
            textView.setBackgroundResource(R.drawable.tag_bg1);


            // 设置内边距
            int padding = UIUtils.dp2px(10);
            textView.setPadding(padding, padding, padding, padding);

            flType.addView(textView);

        }
    }

    @Override
    protected void initTitle() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_typelist;
    }

    private void processData(String json) {
        TagBean tagBean = JSON.parseObject(json, TagBean.class);
        result = tagBean.getResult();
    }
}