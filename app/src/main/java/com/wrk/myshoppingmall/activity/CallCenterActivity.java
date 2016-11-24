package com.wrk.myshoppingmall.activity;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.wrk.myshoppingmall.R;
import com.wrk.myshoppingmall.common.BaseActivity;
import com.wrk.myshoppingmall.utils.Constants;

import butterknife.BindView;

public class CallCenterActivity extends BaseActivity {


    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.pbLoad)
    ProgressBar pbLoad;

    private WebSettings mWebSettings;

    @Override
    protected void initData() {
        setWebView(Constants.CALL_CENTER);
    }

    private void setWebView(String url) {
        if (url != null) {
            mWebSettings = webview.getSettings();
            mWebSettings.setJavaScriptEnabled(true);

            webview.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    pbLoad.setVisibility(View.GONE);
                }
            });
            webview.loadUrl(url);

            //优先使用缓存
            webview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_call_center;
    }


}
