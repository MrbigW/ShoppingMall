package com.wrk.myshoppingmall.activity;

import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wrk.myshoppingmall.R;
import com.wrk.myshoppingmall.common.BaseActivity;
import com.wrk.myshoppingmall.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class ScanResultActivity extends BaseActivity {

    @BindView(R.id.scan_webview)
    WebView scanWebview;
    @BindView(R.id.pb_loading)
    ProgressBar pbLoading;
    @BindView(R.id.tv_isloading)
    TextView tvIsloading;
    @BindView(R.id.iv_error)
    ImageView ivError;
    @BindView(R.id.tv_error)
    TextView tvError;
    @BindView(R.id.ibn_back)
    ImageButton ibnBack;

    private WebSettings mSetting;

    @Override
    protected void initData() {

        String url = this.getIntent().getBundleExtra("data").getString("result");
        ToastUtil.showToast(this, url);
        if (url == null && TextUtils.isEmpty(url) && !url.startsWith("http://")) {
            tvIsloading.setVisibility(View.GONE);
            ivError.setVisibility(View.VISIBLE);
            pbLoading.setVisibility(View.GONE);
            tvError.setVisibility(View.VISIBLE);
            return;
        } else {
            mSetting = scanWebview.getSettings();
            mSetting.setJavaScriptEnabled(true);
            mSetting.setBuiltInZoomControls(true);

            scanWebview.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    tvIsloading.setVisibility(View.GONE);
                    ivError.setVisibility(View.GONE);
                    pbLoading.setVisibility(View.GONE);
                    tvError.setVisibility(View.GONE);
                    scanWebview.setVisibility(View.VISIBLE);
                }
            });

            scanWebview.loadUrl(url);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sacn_result;
    }


    @OnClick(R.id.ibn_back)
    public void onClick() {
        finish();
        scanWebview.removeAllViews();
    }
}
