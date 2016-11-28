package com.wrk.myshoppingmall.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.wrk.myshoppingmall.R;
import com.wrk.myshoppingmall.bean.LoginBean;
import com.wrk.myshoppingmall.common.BaseActivity;
import com.wrk.myshoppingmall.utils.BitmapUtils;
import com.wrk.myshoppingmall.utils.CacheUtils;
import com.wrk.myshoppingmall.utils.Constants;
import com.wrk.myshoppingmall.utils.DownLoaderUtils;
import com.wrk.myshoppingmall.utils.EditTextShakeHelper;
import com.wrk.myshoppingmall.utils.ToastUtil;
import com.wrk.myshoppingmall.utils.UIUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginActivity extends BaseActivity {


    @BindView(R.id.ib_login_back)
    ImageButton ibLoginBack;
    @BindView(R.id.et_login_phone)
    EditText etLoginPhone;
    @BindView(R.id.et_login_pwd)
    EditText etLoginPwd;
    @BindView(R.id.ib_login_visible)
    ImageButton ibLoginVisible;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_login_register)
    TextView tvLoginRegister;
    @BindView(R.id.tv_login_forget_pwd)
    TextView tvLoginForgetPwd;
    @BindView(R.id.ib_weibo)
    ImageButton ibWeibo;
    @BindView(R.id.ib_qq)
    ImageButton ibQq;
    @BindView(R.id.ib_wechat)
    ImageButton ibWechat;
    @BindView(R.id.ll_bg)
    LinearLayout llBg;
    @BindView(R.id.iv_icon)
    ImageView ivIcon;

    private int count;

    private LoginBean mLoginBean;

    private EditTextShakeHelper mShakeHelper;

    @Override
    protected void initData() {
        mShakeHelper = new EditTextShakeHelper(LoginActivity.this);

        String screen_name = getIntent().getStringExtra("screen_name");
        String profile_image_url = getIntent().getStringExtra("profile_image_url");

        if (!TextUtils.isEmpty(screen_name) && !TextUtils.isEmpty(profile_image_url)) {
            etLoginPhone.setText(screen_name);
            Picasso.with(LoginActivity.this).load(profile_image_url).transform(new Transformation() {
                @Override
                public Bitmap transform(Bitmap bitmap) {
                    //先对图片进行压缩
                    Bitmap zoom = BitmapUtils.zoom(bitmap, UIUtils.dp2px(50), UIUtils.dp2px(50));
                    //对请求回来的Bitmap进行圆形处理
                    Bitmap ciceBitMap = BitmapUtils.circleBitmap(zoom);
                    bitmap.recycle();//必须队更改之前的进行回收
                    return ciceBitMap;
                }

                @Override
                public String key() {
                    return "";
                }
            }).into(ivIcon);
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }


    @OnClick({R.id.ib_login_back, R.id.ib_login_visible, R.id.btn_login, R.id.tv_login_register, R.id.tv_login_forget_pwd, R.id.ib_weibo, R.id.ib_qq, R.id.ib_wechat})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_login_back:
                removeCurrentActivity();
                if (CacheUtils.getString(LoginActivity.this, "screen_name").equals(" ")) {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
                break;
            case R.id.ib_login_visible:
                count++;
                if (count % 2 == 0) {
                    ibLoginVisible.setBackgroundResource(R.drawable.new_password_drawable_invisible);
                    etLoginPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else {
                    ibLoginVisible.setBackgroundResource(R.drawable.new_password_drawable_visible);
                    etLoginPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
                break;
            case R.id.btn_login:

                String phone = etLoginPhone.getText().toString().trim();
                String pwd = etLoginPwd.getText().toString().trim();

                if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(pwd)) {
                    ToastUtil.showToast(LoginActivity.this, "用户名或密码为空！");
                    mShakeHelper.shake(etLoginPhone, etLoginPwd);
                    return;
                }

                sendFromToServie(phone, pwd);

                break;
            case R.id.tv_login_register:
                startActivityForResult(new Intent(LoginActivity.this, RegisterActivity.class), 9);
                break;
            case R.id.tv_login_forget_pwd:
                ToastUtil.showToast(LoginActivity.this, "忘了就重新申个号啊！");
                break;
            case R.id.ib_weibo:
                ToastUtil.showToast(LoginActivity.this, "该功能尚未上线！");
                break;
            case R.id.ib_qq:
                ToastUtil.showToast(LoginActivity.this, "该功能尚未上线！");
                break;
            case R.id.ib_wechat:
                ToastUtil.showToast(LoginActivity.this, "该功能尚未上线！");
                break;
        }
    }

    private void sendFromToServie(String phone, String pwd) {
        // 登录  http://182.92.5.3:8081/android/user/login?username=s&password=34
        // 注册  http://182.92.5.3:8081/android/user/reg?username=s&password=34
        Map<String, String> params = new HashMap<>();
        params.put("username", phone);
        params.put("password", pwd);
        new DownLoaderUtils().sendComplexFrom(Constants.LOGIN_URL, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.showToast(LoginActivity.this, "登录错误：" + e.getMessage());
                    }

                    @Override
                    public void onNext(String s) {
                        if (TextUtils.isEmpty(s)) {
                            ToastUtil.showToast(LoginActivity.this, "服务器无响应");
                        } else {
                            processData(s);
                            if (mLoginBean.getStatus() == 200) {
                                ToastUtil.showToast(LoginActivity.this, mLoginBean.getMessage());
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("screen_name", mLoginBean.getBody().getUser().getUsername());
                                intent.putExtra("profile_image_url", "http://192.168.1.8:8080/atguigu/img/img/hehe.JPG");
                                CacheUtils.putBoolean(LoginActivity.this, Constants.IS_LOGIN, true);
                                LoginActivity.this.setResult(0, intent);
                                removeCurrentActivity();
                            } else {
                                ToastUtil.showToast(LoginActivity.this, mLoginBean.getMessage());
                            }
                        }
                    }
                });
    }

    private void processData(String content) {
        mLoginBean = JSON.parseObject(content, LoginBean.class);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 9 && resultCode == 9 && data != null) {
            etLoginPhone.setText(data.getStringExtra("phone"));
            Picasso.with(LoginActivity.this).load(data.getStringExtra("profile_image_url")).transform(new Transformation() {
                @Override
                public Bitmap transform(Bitmap bitmap) {
                    //先对图片进行压缩
                    Bitmap zoom = BitmapUtils.zoom(bitmap, UIUtils.dp2px(50), UIUtils.dp2px(50));
                    //对请求回来的Bitmap进行圆形处理
                    Bitmap ciceBitMap = BitmapUtils.circleBitmap(zoom);
                    bitmap.recycle();//必须队更改之前的进行回收
                    return ciceBitMap;
                }

                @Override
                public String key() {
                    return "";
                }
            }).into(ivIcon);
        }
    }
}
