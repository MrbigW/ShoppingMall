package com.wrk.myshoppingmall.activity;

import android.widget.ImageButton;

import com.wrk.myshoppingmall.R;
import com.wrk.myshoppingmall.common.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class MessageCenterActivity extends BaseActivity {


    @BindView(R.id.ib_login_back)
    ImageButton ibLoginBack;

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message_center;
    }


    @OnClick(R.id.ib_login_back)
    public void onClick() {
        removeCurrentActivity();
    }
}
