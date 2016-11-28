package com.wrk.myshoppingmall.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.wrk.myshoppingmall.R;
import com.wrk.myshoppingmall.common.BaseActivity;
import com.wrk.myshoppingmall.utils.CacheUtils;
import com.wrk.myshoppingmall.utils.Constants;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {


    @BindView(R.id.btn_login_out)
    Button btnLoginOut;

    @Override
    protected void initData() {
        if (TextUtils.isEmpty(CacheUtils.getString(SettingActivity.this, "screen_name"))||CacheUtils.getString(SettingActivity.this, "screen_name").equals(" ") ) {
            btnLoginOut.setVisibility(View.GONE);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }


    @OnClick(R.id.btn_login_out)
    public void onClick() {
        CacheUtils.putBoolean(SettingActivity.this, Constants.IS_LOGIN, false);
        Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
        intent.putExtra("screen_name", CacheUtils.getString(SettingActivity.this, "screen_name"));
        intent.putExtra("profile_image_url", CacheUtils.getString(SettingActivity.this, "profile_image_url"));
        startActivity(intent);
        CacheUtils.putString(SettingActivity.this, "screen_name", " ");
        CacheUtils.putString(SettingActivity.this, "profile_image_url", "");
        removeAll();
    }
}
