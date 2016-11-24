package com.wrk.myshoppingmall.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.wrk.myshoppingmall.R;
import com.wrk.myshoppingmall.utils.UIUtils;

public class WelcomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // 隐藏状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        UIUtils.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                finish();
                overridePendingTransition(R.anim.right_enter, R.anim.left_exit);
            }
        }, 2000);
    }

}
