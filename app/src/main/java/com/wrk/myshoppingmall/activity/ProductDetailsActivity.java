package com.wrk.myshoppingmall.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.squareup.picasso.Picasso;
import com.wrk.myshoppingmall.R;
import com.wrk.myshoppingmall.bean.GoodsBean;
import com.wrk.myshoppingmall.bean.ResultBean;
import com.wrk.myshoppingmall.common.BaseActivity;
import com.wrk.myshoppingmall.pay.MyAliPay;
import com.wrk.myshoppingmall.ui.BuyPopUpWindow;
import com.wrk.myshoppingmall.ui.FlowLayout;
import com.wrk.myshoppingmall.ui.MorePopUpWindow;
import com.wrk.myshoppingmall.ui.NumAddSubView;
import com.wrk.myshoppingmall.ui.ScrollViewContainer;
import com.wrk.myshoppingmall.utils.Constants;
import com.wrk.myshoppingmall.utils.DownLoaderUtils;
import com.wrk.myshoppingmall.utils.DrawUtils;
import com.wrk.myshoppingmall.utils.ToastUtil;
import com.wrk.myshoppingmall.utils.UIUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ProductDetailsActivity extends BaseActivity {

    private String[] colors = new String[]{"Black", "Yellow", "Red", "Blue", "HelloWorld", "C++-C-ObjectC-java", "Android vs ios", "算法与数据结构", "JNI与NDK", "team working"};

    private String[] sizes = new String[]{"165/S", "170/M", "175/L", "180/XL", "185/XXL", "XXXL"};


    @BindView(R.id.vp_goodinfo_detail)
    Banner vpGoodinfoDetail;
    @BindView(R.id.ibn_detail_back)
    ImageButton ibnDetailBack;
    @BindView(R.id.ibn_detail_cart)
    ImageButton ibnDetailCart;
    @BindView(R.id.ibn_detail_more)
    ImageButton ibnDetailMore;
    @BindView(R.id.tv_good_info_name)
    TextView tvGoodInfoName;
    @BindView(R.id.tv_good_info_desc)
    TextView tvGoodInfoDesc;
    @BindView(R.id.tv_good_info_price)
    TextView tvGoodInfoPrice;
    @BindView(R.id.tv_good_info_store)
    TextView tvGoodInfoStore;
    @BindView(R.id.tv_good_info_style)
    TextView tvGoodInfoStyle;
    @BindView(R.id.wb_good_info_more)
    WebView wbGoodInfoMore;
    @BindView(R.id.tv_good_info_callcenter)
    TextView tvGoodInfoCallcenter;
    @BindView(R.id.tv_good_info_shop)
    TextView tvGoodInfoCart;
    @BindView(R.id.tv_good_info_collection)
    TextView tvGoodInfoCollection;
    @BindView(R.id.btn_good_info_addcart)
    Button btnGoodInfoAddcart;
    @BindView(R.id.btn_good_info_buynow)
    Button btnGoodInfoBuynow;
    @BindView(R.id.ll_goods_root)
    LinearLayout llGoodsRoot;
    @BindView(R.id.svc_detail)
    ScrollViewContainer svcDetail;

    private MorePopUpWindow mMorePopUpWindow;
    private BuyPopUpWindow mShoppingPopUpWindow;

    private ResultBean mResultBean;


    private LocalBroadcastManager mBroadcastManager;
    private String mName;
    private String mCover_price;
    private String mFigure;
    private FlowLayout mFlow_color;
    private FlowLayout mFlow_size;
    private String mProduct_id;
    private GoodsBean mGoodsBean;

    private String shareUrl;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_product_details;
    }


    @Override
    protected void initData() {
        mBroadcastManager = LocalBroadcastManager.getInstance(this);

        // 获取传递过来的数据
        mGoodsBean = (GoodsBean) getIntent().getSerializableExtra(Constants.GOODS_BEAN);
        shareUrl = getIntent().getStringExtra(Constants.GOODS_SHARE);

        if (mGoodsBean != null && !TextUtils.isEmpty(mGoodsBean.toString())) {
            setDataToView(mGoodsBean);
        }

        if (!TextUtils.isEmpty(shareUrl)) {
            mGoodsBean = new GoodsBean();
            mGoodsBean.setFigure(shareUrl);
            new DownLoaderUtils().getJsonResult(Constants.HOME_URL)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<String>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            ToastUtil.showToast(ProductDetailsActivity.this, "服务器异常,请重试");
                            removeCurrentActivity();
                        }

                        @Override
                        public void onNext(String s) {
                            if (!TextUtils.isEmpty(s)) {
                                processData(s);
                                findSharedProduct();
                                setDataToView(mGoodsBean);
                            } else {
                                ToastUtil.showToast(ProductDetailsActivity.this, "服务器异常,请重试");
                            }
                        }
                    });
        }



    }

    private void findSharedProduct() {
        List<ResultBean.BannerInfoBean> act_info = mResultBean.getBanner_info();
        for (int i = 0; i < act_info.size(); i++) {
            if (act_info.get(i).getImage().equals(shareUrl)) {
                Log.e("222", act_info.get(i).getImage() + "---" + shareUrl);
                if (i == 0) {
                    mGoodsBean.setName("尚硅谷在线课堂");
                    mGoodsBean.setCover_price("320.00");
                    mGoodsBean.setProduct_id("627");
                } else if (i == 1) {
                    mGoodsBean.setName("尚硅谷抢座");
                    mGoodsBean.setCover_price("800.00");
                    mGoodsBean.setProduct_id("21");
                } else if (i == 2) {
                    mGoodsBean.setName("尚硅谷讲座");
                    mGoodsBean.setCover_price("150.00");
                    mGoodsBean.setProduct_id("1341");
                }
            }
        }
        List<ResultBean.HotInfoBean> hot_info = mResultBean.getHot_info();
        for (int i = 0; i < hot_info.size(); i++) {
            if (hot_info.get(i).getFigure().equals(shareUrl)) {
                mGoodsBean.setName(hot_info.get(i).getName());
                mGoodsBean.setCover_price(hot_info.get(i).getCover_price());
                mGoodsBean.setProduct_id(hot_info.get(i).getProduct_id());
            }
        }
        List<ResultBean.RecommendInfoBean> recommend_info = mResultBean.getRecommend_info();
        for (int i = 0; i < recommend_info.size(); i++) {
            if (recommend_info.get(i).getFigure().equals(shareUrl)) {
                mGoodsBean.setName(recommend_info.get(i).getName());
                mGoodsBean.setCover_price(recommend_info.get(i).getCover_price());
                mGoodsBean.setProduct_id(recommend_info.get(i).getProduct_id());
            }
        }
        ResultBean.SeckillInfoBean seckill_info = mResultBean.getSeckill_info();
        List<ResultBean.SeckillInfoBean.ListBean> seckill_infoList = seckill_info.getList();
        for (int i = 0; i < seckill_infoList.size(); i++) {
            if (seckill_infoList.get(i).getFigure().equals(shareUrl)) {
                mGoodsBean.setName(seckill_infoList.get(i).getName());
                mGoodsBean.setCover_price(seckill_infoList.get(i).getCover_price());
                mGoodsBean.setProduct_id(seckill_infoList.get(i).getProduct_id());
            }
        }
    }

    private void setDataToView(GoodsBean goodsBean) {
        mName = goodsBean.getName();
        mCover_price = goodsBean.getCover_price();
        mFigure = goodsBean.getFigure();
        mProduct_id = goodsBean.getProduct_id();

        if (mName != null) {
            tvGoodInfoName.setText(mName);
        }
        if (mCover_price != null) {
            tvGoodInfoPrice.setText("￥" + mCover_price);
        }

        setBanner(mFigure);
        setWebView(mProduct_id);
    }

    private void setBanner(String figure) {
        vpGoodinfoDetail.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        // 设置图片加载器
        vpGoodinfoDetail.setImageLoader(new PicassoImageLoader());
        // 设置图片集合
        final List<String> imageUris = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            imageUris.add(Constants.Base_URl_IMAGE + figure);
        }
        vpGoodinfoDetail.setImages(imageUris);
        // 设置banner动画效果
        vpGoodinfoDetail.setBannerAnimation(Transformer.DepthPage);
        //设置自动轮播，默认为true
        vpGoodinfoDetail.isAutoPlay(false);
        //设置轮播时间
        vpGoodinfoDetail.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        vpGoodinfoDetail.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        vpGoodinfoDetail.start();
        //banner的点击事件
        vpGoodinfoDetail.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void OnBannerClick(int position) {
                Intent intent = new Intent(ProductDetailsActivity.this, ProductPhotosActivity.class);
                intent.putStringArrayListExtra("photo_url", (ArrayList<String>) imageUris);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
    }


    private void setWebView(String product_id) {

        if (product_id != null) {
            //http://192.168.51.104:8080/atguigu/json/GOODSINFO_URL.json2691
//            wbGoodInfoMore.loadUrl(Constants.GOODSINFO_URL + product_id);
            wbGoodInfoMore.loadUrl("http://www.tianmao.com");
            //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
            wbGoodInfoMore.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                    view.loadUrl(url);
                    return true;
                }
            });
            //启用支持javascript
            WebSettings settings = wbGoodInfoMore.getSettings();
            settings.setJavaScriptEnabled(true);

            //优先使用缓存
            wbGoodInfoMore.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
    }

    private Timer mTimer;
    private TimerTask mTimerTask;
    private float alpha = 1.0f;

    @OnClick({R.id.ibn_detail_back, R.id.ibn_detail_cart, R.id.ibn_detail_more, R.id.tv_good_info_callcenter, R.id.tv_good_info_shop, R.id.tv_good_info_collection, R.id.btn_good_info_addcart, R.id.btn_good_info_buynow})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibn_detail_back:
                this.removeCurrentActivity();
                break;
            case R.id.ibn_detail_cart:
                mBroadcastManager.sendBroadcast(new Intent(Constants.SWITCH2CART));
                this.removeCurrentActivity();
                break;
            case R.id.ibn_detail_more:
                showMorePopWindow();
                break;
            case R.id.tv_good_info_callcenter:
                // 跳转至客服
                startActivity(new Intent(this, CallCenterActivity.class));
                break;
            case R.id.tv_good_info_shop:
                ToastUtil.showToast(this, "店铺");
                break;
            case R.id.tv_good_info_collection:
                ToastUtil.showToast(this, "收藏");
                break;
            case R.id.btn_good_info_addcart:
                showShoppingPopWindow(0);
                break;
            case R.id.btn_good_info_buynow:
                showShoppingPopWindow(1);
                break;
        }
    }

    private int pos;

    private void showShoppingPopWindow(final int type) {

        mShoppingPopUpWindow = new BuyPopUpWindow(this);

        TextView tv_shopping_price = (TextView) mShoppingPopUpWindow.getContentView().findViewById(R.id.tv_shopping_price);
        ImageButton ibn_shopping_back = (ImageButton) mShoppingPopUpWindow.getContentView().findViewById(R.id.ibn_shopping_back);
        ImageView shopping_img = (ImageView) mShoppingPopUpWindow.getContentView().findViewById(R.id.shopping_img);
        final NumAddSubView nas_add_cart = (NumAddSubView) mShoppingPopUpWindow.getContentView().findViewById(R.id.nas_add_cart);
        mFlow_color = (FlowLayout) mShoppingPopUpWindow.getContentView().findViewById(R.id.flow_color);
        mFlow_size = (FlowLayout) mShoppingPopUpWindow.getContentView().findViewById(R.id.flow_size);
        final Button btn_shopping_confirm = (Button) mShoppingPopUpWindow.getContentView().findViewById(R.id.btn_shopping_confirm);

        // 设置价格
        tv_shopping_price.setText("特惠抢购￥" + mCover_price);
        // 显示图片
        Picasso.with(this).load(Constants.Base_URl_IMAGE + mFigure).fit().into(shopping_img);
        // 为FlowLayout添加数据
        initFlowLayout();
        // 为FlowLayout设置每个子View的点击事件
        flowLayoutOnClick();
        // 返回键的点击事件
        ibn_shopping_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShoppingPopUpWindow.dismiss();
            }
        });
        // 确定键的点击事件
        btn_shopping_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ColorsTags = null;
                String SizeTags = null;
                for (int i = 0; i < mFlow_color.getChildCount(); i++) {
                    TextView textView = (TextView) mFlow_color.getChildAt(i);
                    if (textView.getTag() != null) {
                        ColorsTags = textView.getTag().toString();
                    }
                }
                for (int i = 0; i < mFlow_size.getChildCount(); i++) {
                    TextView textView = (TextView) mFlow_size.getChildAt(i);
                    if (textView.getTag() != null) {
                        SizeTags = textView.getTag().toString();
                    }
                }

                if (TextUtils.isEmpty(ColorsTags) && TextUtils.isEmpty(SizeTags)) {
                    ToastUtil.showToast(ProductDetailsActivity.this, "请选择颜色和尺寸");
                    return;
                }
                if (TextUtils.isEmpty(ColorsTags)) {
                    ToastUtil.showToast(ProductDetailsActivity.this, "请选择颜色");
                    return;
                }
                if (TextUtils.isEmpty(SizeTags)) {
                    ToastUtil.showToast(ProductDetailsActivity.this, "请选择尺寸");
                    return;
                }

                // 得到商品总数
                int current = nas_add_cart.getValue();
                String des = mName + " 尺寸:" + SizeTags + " 颜色:" + ColorsTags + " 数量:" + current;
                if (type == 0) { // 加入购物车
                    ToastUtil.showToast(ProductDetailsActivity.this, des + " 加入成功");
                    mShoppingPopUpWindow.dismiss();
                } else { // 直接购买
                    double totalMoneny = Double.parseDouble(mCover_price) * current;

                    ToastUtil.showToast(ProductDetailsActivity.this, des);
                    new MyAliPay(ProductDetailsActivity.this).pay(btn_shopping_confirm, mName, des, String.valueOf(totalMoneny));
                    mShoppingPopUpWindow.dismiss();
                }
            }
        });


        // 显示popUpWindow
        mShoppingPopUpWindow.showAtLocation(ibnDetailBack, Gravity.BOTTOM, 0, 0);

        // 屏幕渐变亮度
        mTimer = new Timer();
        mTimerTask = new TimerTask() {
            @Override
            public void run() {

                alpha -= 0.15f;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lightChange(alpha);
                    }
                });
                if (alpha <= 0.2f) {
                    mTimer.cancel();
                    mTimerTask.cancel();
                }
            }
        };
        mTimer.schedule(mTimerTask, 0, 50);

        // popupWindow消失屏幕渐变亮度
        mShoppingPopUpWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mTimer = new Timer();
                mTimerTask = new TimerTask() {
                    @Override
                    public void run() {

                        alpha += 0.15f;

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                lightChange(alpha);
                            }
                        });
                        if (alpha >= 1.0f) {
                            mTimer.cancel();
                            mTimerTask.cancel();
                        }
                    }
                };
                mTimer.schedule(mTimerTask, 0, 50);
            }
        });

    }

    private void flowLayoutOnClick() {
        for (int i = 0; i < mFlow_color.getChildCount(); i++) {
            pos = i;
            final TextView textView = (TextView) mFlow_color.getChildAt(i);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int j = 0; j < mFlow_color.getChildCount(); j++) {
                        TextView other = (TextView) mFlow_color.getChildAt(j);
                        other.setBackground(DrawUtils.getDrawable(Color.rgb(245, 245, 245), UIUtils.dp2px(10), Color.TRANSPARENT));
                        other.setTag(null);
                    }
                    textView.setTag(colors[pos]);
                    textView.setBackground(DrawUtils.getDrawable(Color.rgb(255, 80, 0), UIUtils.dp2px(10), Color.TRANSPARENT));
                }
            });
        }

        for (int i = 0; i < mFlow_size.getChildCount(); i++) {
            pos = i;
            final TextView textView = (TextView) mFlow_size.getChildAt(i);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int j = 0; j < mFlow_size.getChildCount(); j++) {
                        TextView other = (TextView) mFlow_size.getChildAt(j);
                        other.setBackground(DrawUtils.getDrawable(Color.rgb(245, 245, 245), UIUtils.dp2px(10), Color.TRANSPARENT));
                        other.setTag(null);
                    }
                    textView.setTag(colors[pos]);
                    textView.setBackground(DrawUtils.getDrawable(Color.rgb(255, 80, 0), UIUtils.dp2px(10), Color.TRANSPARENT));
                }
            });
        }
    }

    private void initFlowLayout() {

        for (int i = 0; i < colors.length; i++) {

            final TextView textView = new TextView(this);

            textView.setText(colors[i]);
            textView.setTextSize(UIUtils.dp2px(5));
            textView.setTextColor(Color.BLACK);

            ViewGroup.MarginLayoutParams mp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            mp.leftMargin = UIUtils.dp2px(8);
            mp.topMargin = UIUtils.dp2px(8);
            mp.rightMargin = UIUtils.dp2px(8);
            mp.bottomMargin = UIUtils.dp2px(8);

            textView.setLayoutParams(mp);
            // 设置背景
            textView.setBackground(DrawUtils.getDrawable(Color.rgb(245, 245, 245), UIUtils.dp2px(10), Color.TRANSPARENT));
//          
            // 设置内边距
            int padding = UIUtils.dp2px(10);
            textView.setPadding(padding, padding, padding, padding);

            mFlow_color.addView(textView);

        }
        for (int i = 0; i < sizes.length; i++) {
            final TextView textView = new TextView(this);

            textView.setText(sizes[i]);
            textView.setTextSize(UIUtils.dp2px(5));
            textView.setTextColor(Color.BLACK);

            ViewGroup.MarginLayoutParams mp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            mp.leftMargin = UIUtils.dp2px(8);
            mp.topMargin = UIUtils.dp2px(8);
            mp.rightMargin = UIUtils.dp2px(8);
            mp.bottomMargin = UIUtils.dp2px(8);

            textView.setLayoutParams(mp);


            // 设置背景
            textView.setBackground(DrawUtils.getDrawable(Color.rgb(245, 245, 245), UIUtils.dp2px(10), Color.TRANSPARENT));

            // 设置内边距
            int padding = UIUtils.dp2px(10);
            textView.setPadding(padding, padding, padding, padding);

            mFlow_size.addView(textView);

        }

    }


    private void showMorePopWindow() {
        mMorePopUpWindow = new MorePopUpWindow(this);

        LinearLayout more_message = (LinearLayout) mMorePopUpWindow.getContentView().findViewById(R.id.more_message);
        LinearLayout more_home = (LinearLayout) mMorePopUpWindow.getContentView().findViewById(R.id.more_home);
        LinearLayout more_help = (LinearLayout) mMorePopUpWindow.getContentView().findViewById(R.id.more_help);
        LinearLayout more_share = (LinearLayout) mMorePopUpWindow.getContentView().findViewById(R.id.more_share);

        more_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToast(ProductDetailsActivity.this, "消息");
                mMorePopUpWindow.dismiss();
            }
        });
        more_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMorePopUpWindow.dismiss();
                removeCurrentActivity();
                mBroadcastManager.sendBroadcast(new Intent(Constants.SWITCH2HOME));
            }
        });
        more_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToast(ProductDetailsActivity.this, "帮助");
                mMorePopUpWindow.dismiss();
            }
        });
        more_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // 分享
                Intent intent = new Intent(ProductDetailsActivity.this, SharedActivity.class);
                intent.putExtra(Constants.GOODS_SHARE, mGoodsBean.getFigure());
                startActivity(intent);
                mMorePopUpWindow.dismiss();
            }
        });

        mMorePopUpWindow.showAtLocation(ibnDetailMore, Gravity.TOP, UIUtils.dp2px(110), UIUtils.dp2px(75));

    }

    /**
     * banner的图片加载器
     */
    public class PicassoImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {

            //Picasso 加载图片简单用法
            Picasso.with(context).load((String) path).into(imageView);

        }
    }

    /**
     * 显示时屏幕变暗
     */
    private void lightChange(float a) {

        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();

        layoutParams.alpha = a;

        getWindow().setAttributes(layoutParams);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMorePopUpWindow != null && mMorePopUpWindow.isShowing()) {
            mMorePopUpWindow.dismiss();
            mMorePopUpWindow = null;
        }
        if (mShoppingPopUpWindow != null && mShoppingPopUpWindow.isShowing()) {
            mShoppingPopUpWindow.dismiss();
            mShoppingPopUpWindow = null;
        }
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
}
