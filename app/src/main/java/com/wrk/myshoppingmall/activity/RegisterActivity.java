package com.wrk.myshoppingmall.activity;

import android.content.Intent;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.alibaba.fastjson.JSON;
import com.wrk.myshoppingmall.R;
import com.wrk.myshoppingmall.bean.RegisterBean;
import com.wrk.myshoppingmall.common.BaseActivity;
import com.wrk.myshoppingmall.utils.Constants;
import com.wrk.myshoppingmall.utils.DownLoaderUtils;
import com.wrk.myshoppingmall.utils.EditTextShakeHelper;
import com.wrk.myshoppingmall.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RegisterActivity extends BaseActivity {


    @BindView(R.id.ib_login_back)
    ImageButton ibLoginBack;
    @BindView(R.id.et_login_phone)
    EditText etLoginPhone;
    @BindView(R.id.et_login_pwd)
    EditText etLoginPwd;
    @BindView(R.id.ib_login_visible)
    ImageButton ibLoginVisible;
    @BindView(R.id.et_login_again_pwd)
    EditText etLoginAgainPwd;
    @BindView(R.id.btn_login)
    Button btnLogin;

    private EditTextShakeHelper mShakeHelper;

    private int count;

    private RegisterBean mRegisterBean;

    @Override
    protected void initData() {
        mShakeHelper = new EditTextShakeHelper(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }


    @OnClick({R.id.ib_login_back, R.id.ib_login_visible, R.id.btn_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_login_back:
                removeCurrentActivity();
                break;
            case R.id.ib_login_visible:
                count++;
                if (count % 2 == 0) {
                    ibLoginVisible.setBackgroundResource(R.drawable.new_password_drawable_invisible);
                    etLoginPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    etLoginAgainPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else {
                    ibLoginVisible.setBackgroundResource(R.drawable.new_password_drawable_visible);
                    etLoginPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    etLoginAgainPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
                break;
            case R.id.btn_login:
                String pwd = etLoginPwd.getText().toString().trim();
                String pwd_again = etLoginAgainPwd.getText().toString().trim();
                String phone = etLoginPhone.getText().toString().trim();

                if (TextUtils.isEmpty(pwd) || TextUtils.isEmpty(pwd_again) || TextUtils.isEmpty(phone)) {
                    ToastUtil.showToast(RegisterActivity.this, "用户名或密码不能为空");
                    mShakeHelper.shake(etLoginAgainPwd, etLoginPhone, etLoginPwd);
                    return;
                }

                if (!pwd.equals(pwd_again)) {
                    ToastUtil.showToast(RegisterActivity.this, "两次输入密码不相同");
                    return;
                }

                sendFromToServie(phone, pwd);

                break;
        }
    }


    private void sendFromToServie(final String phone, String pwd) {
        // 登录  http://182.92.5.3:8081/android/user/login?username=s&password=34
        // 注册  http://182.92.5.3:8081/android/user/reg?username=s&password=34
        Map<String, String> params = new HashMap<>();
        params.put("username", phone);
        params.put("password", pwd);
        new DownLoaderUtils().sendComplexFrom(Constants.REGISTER_URL, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.showToast(RegisterActivity.this, "注册错误：" + e.getMessage());
                    }

                    @Override
                    public void onNext(String s) {
                        if (TextUtils.isEmpty(s)) {
                            ToastUtil.showToast(RegisterActivity.this, "服务器无响应!");
                        } else {
                            processData(s);
                            if (mRegisterBean.getStatus() == 200) {
                                ToastUtil.showToast(RegisterActivity.this, mRegisterBean.getMessage());
                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                intent.putExtra("screen_name", phone);
                                intent.putExtra("profile_image_url", "http://192.168.1.8:8080/atguigu/img/img/hehe.JPG");
                                RegisterActivity.this.setResult(9, intent);
                                removeCurrentActivity();
                            } else {
                                ToastUtil.showToast(RegisterActivity.this, mRegisterBean.getMessage());
                            }
                        }
                    }
                });
    }

    private void processData(String s) {
        mRegisterBean = JSON.parseObject(s, RegisterBean.class);
    }
}
