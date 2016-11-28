package com.wrk.myshoppingmall.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.wrk.myshoppingmall.R;
import com.wrk.myshoppingmall.activity.LoginActivity;
import com.wrk.myshoppingmall.activity.MessageCenterActivity;
import com.wrk.myshoppingmall.activity.SettingActivity;
import com.wrk.myshoppingmall.common.BaseFragment;
import com.wrk.myshoppingmall.utils.BitmapUtils;
import com.wrk.myshoppingmall.utils.CacheUtils;
import com.wrk.myshoppingmall.utils.Constants;
import com.wrk.myshoppingmall.utils.UIUtils;

import butterknife.BindView;
import butterknife.OnClick;

import static com.wrk.myshoppingmall.common.MyApplication.mContext;

/**
 * Created by MrbigW on 2016/11/21.
 * weChat:1024057635
 * GitHub:MrbigW
 * Usage: -.-
 * -------------------=.=------------------------
 */

public class MeFragment extends BaseFragment {

    @BindView(R.id.ib_user_icon_avator)
    ImageButton ibUserIconAvator;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.tv_all_order)
    TextView tvAllOrder;
    @BindView(R.id.tv_user_pay)
    TextView tvUserPay;
    @BindView(R.id.tv_user_receive)
    TextView tvUserReceive;
    @BindView(R.id.tv_user_finish)
    TextView tvUserFinish;
    @BindView(R.id.tv_user_drawback)
    TextView tvUserDrawback;
    @BindView(R.id.tv_user_location)
    TextView tvUserLocation;
    @BindView(R.id.tv_user_collect)
    TextView tvUserCollect;
    @BindView(R.id.tv_user_coupon)
    TextView tvUserCoupon;
    @BindView(R.id.tv_user_score)
    TextView tvUserScore;
    @BindView(R.id.tv_user_prize)
    TextView tvUserPrize;
    @BindView(R.id.tv_user_ticket)
    TextView tvUserTicket;
    @BindView(R.id.tv_user_invitation)
    TextView tvUserInvitation;
    @BindView(R.id.tv_user_callcenter)
    TextView tvUserCallcenter;
    @BindView(R.id.tv_user_feedback)
    TextView tvUserFeedback;
    @BindView(R.id.scrollview)
    ScrollView scrollview;
    @BindView(R.id.tv_usercenter)
    TextView tvUsercenter;
    @BindView(R.id.ib_user_setting)
    ImageButton ibUserSetting;
    @BindView(R.id.ib_user_message)
    ImageButton ibUserMessage;

    @Override
    protected String getUrl() {
        return null;
    }

    @Override
    protected void initData(String content) {
        scrollview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int[] location = new int[2];
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_MOVE://下滑是正，上滑是负
                        ibUserIconAvator.getLocationOnScreen(location);//初始状态为125,即最大值是125，全部显示不透明是（40？）
                        float i = (location[1] - 40) / 85f;
                        tvUsercenter.setAlpha(1 - i);
                        break;
                }
                return false;
            }
        });
    }

    @Override
    protected void initTitle() {
        tvUsercenter.setAlpha(0);

        if (CacheUtils.getBoolean(getActivity(), Constants.IS_LOGIN)) {
            String screen_name = CacheUtils.getString(getActivity(), "screen_name");
            String profile_image_url = CacheUtils.getString(getActivity(), "profile_image_url");
            setData(screen_name, profile_image_url);
        } else {
            // 默认图片的圆形处理
            Bitmap defalutBm = BitmapFactory.decodeResource(getResources(), R.drawable.aliuser_place_holder);
            Bitmap zoom = BitmapUtils.zoom(defalutBm, UIUtils.dp2px(50), UIUtils.dp2px(50));
            Bitmap ciceBitMap = BitmapUtils.circleBitmap(zoom);
            ibUserIconAvator.setImageBitmap(ciceBitMap);
        }
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_me;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 登录成功
        if (requestCode == 0 && data != null && resultCode == 0) {
            String screen_name = data.getStringExtra("screen_name");
            String profile_image_url = data.getStringExtra("profile_image_url");

            CacheUtils.putString(getActivity(), "screen_name", screen_name);
            CacheUtils.putString(getActivity(), "profile_image_url", profile_image_url);

            if (TextUtils.isEmpty(screen_name) || TextUtils.isEmpty(profile_image_url)) {
                return;
            }
            setData(screen_name, profile_image_url);
        }

    }


    @OnClick({R.id.ib_user_icon_avator, R.id.ib_user_setting, R.id.ib_user_message})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_user_icon_avator:
                if (CacheUtils.getBoolean(getActivity(), Constants.IS_LOGIN)) {
                    // 设置
                    getActivity().startActivity(new Intent(getActivity(), SettingActivity.class));
                } else {
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    startActivityForResult(intent, 0);
                }
                break;
            case R.id.ib_user_setting:
                getActivity().startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
            case R.id.ib_user_message:
                startActivity(new Intent(getActivity(), MessageCenterActivity.class));
                break;
        }
    }

    // 设置头像和用户名
    private void setData(String screen_name, String profile_image_url) {
        Picasso.with(getActivity()).load(profile_image_url).transform(new Transformation() {
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
        }).into(ibUserIconAvator);
        tvUsername.setText(screen_name);
    }


}
