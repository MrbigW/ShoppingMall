package com.wrk.myshoppingmall.fragment;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wrk.myshoppingmall.R;
import com.wrk.myshoppingmall.adapter.HomeRecycleAdapter;
import com.wrk.myshoppingmall.bean.ResultBean;
import com.wrk.myshoppingmall.common.BaseFragment;
import com.wrk.myshoppingmall.utils.Constants;
import com.wrk.myshoppingmall.utils.ToastUtil;
import com.wrk.myshoppingmall.utils.zxing.activity.CaptureActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by MrbigW on 2016/11/21.
 * weChat:1024057635
 * GitHub:MrbigW
 * Usage: -.-
 * -------------------=.=------------------------
 */

public class HomeFragment extends BaseFragment {

    @BindView(R.id.ll_main_scan)
    LinearLayout llMainScan;
    @BindView(R.id.iv_main_search)
    ImageView ivMainSearch;
    @BindView(R.id.tv_main_search)
    TextView tvMainSearch;
    @BindView(R.id.iv_main_camera)
    ImageView ivMainCamera;
    @BindView(R.id.point_main_msg)
    TextView pointMainMsg;
    @BindView(R.id.ll_main_msg)
    LinearLayout llMainMsg;
    @BindView(R.id.rv_home)
    RecyclerView rvHome;
    @BindView(R.id.fab_main)
    FloatingActionButton fabMain;


    private HomeRecycleAdapter mRecycleAdapter;
    private ResultBean mResultBean;

    private float y;

    @Override
    protected String getUrl() {
        return Constants.HOME_URL;
    }

    @Override
    protected void initData(String content) {

        processData(content);

        mRecycleAdapter = new HomeRecycleAdapter(getActivity(), mResultBean);

        rvHome.setAdapter(mRecycleAdapter);

        final LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        rvHome.setLayoutManager(manager);

        rvHome.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItemPosition = manager.findLastVisibleItemPosition();
                if (lastVisibleItemPosition > 4) {
                    fabMain.setVisibility(View.VISIBLE);
                } else {
                    fabMain.setVisibility(View.GONE);
                }
            }
        });


    }

    private void processData(String content) {
        JSONObject jsonObject = JSON.parseObject(content);

        //得到状态码
        String code = jsonObject.getString("code");
        String msg = jsonObject.getString("msg");
        String result = jsonObject.getString("result");

        //得到resultBean的数据
        mResultBean = JSON.parseObject(result, ResultBean.class);

    }

    @Override
    protected void initTitle() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }


    @OnClick({R.id.ll_main_scan, R.id.iv_main_search, R.id.tv_main_search, R.id.iv_main_camera, R.id.point_main_msg, R.id.ll_main_msg, R.id.fab_main})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_main_scan:
                openScan();
                break;
            case R.id.iv_main_search:
                ToastUtil.showToast(getActivity(), "搜索");
                break;
            case R.id.tv_main_search:
                ToastUtil.showToast(getActivity(), "搜索文字");
                break;
            case R.id.iv_main_camera:
                ToastUtil.showToast(getActivity(), "相机");
                break;
            case R.id.ll_main_msg:
                ToastUtil.showToast(getActivity(), "消息");
                break;
            case R.id.fab_main:
                fabMain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rvHome.scrollToPosition(0);
                    }
                });
                break;
        }
    }

    /**
     * 打开二维码扫描
     */
    private void openScan() {
        config();
        startActivityForResult(new Intent(getActivity(),
                CaptureActivity.class), 0);
    }

    /**
     * 提高屏幕亮度
     */
    private void config() {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.screenBrightness = 1.0f;
        getActivity().getWindow().setAttributes(lp);
    }

}
