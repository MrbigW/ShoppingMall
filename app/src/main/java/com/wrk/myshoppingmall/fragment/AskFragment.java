package com.wrk.myshoppingmall.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.flyco.tablayout.listener.CustomTabEntity;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.wrk.myshoppingmall.R;
import com.wrk.myshoppingmall.activity.MessageCenterActivity;
import com.wrk.myshoppingmall.activity.SearchActivity;
import com.wrk.myshoppingmall.bean.TabEntity;
import com.wrk.myshoppingmall.common.BaseFragment;
import com.wrk.myshoppingmall.fragment.found.HotFoundFragment;
import com.wrk.myshoppingmall.fragment.found.NewFoundFragment;
import com.wrk.myshoppingmall.ui.CustomSlidingTablyout.SlidingTabLayout;
import com.wrk.myshoppingmall.utils.BitmapUtils;
import com.wrk.myshoppingmall.utils.CacheUtils;
import com.wrk.myshoppingmall.utils.Constants;
import com.wrk.myshoppingmall.utils.UIUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by MrbigW on 2016/11/21.
 * weChat:1024057635
 * GitHub:MrbigW
 * Usage: -.-
 * -------------------=.=------------------------
 */

public class AskFragment extends BaseFragment {

    private final String[] mTitles = {
            "热门", "穿搭", "海淘"
            , "育儿", "装修", "美妆", "数码", "健身"
    };
    private final int[] imgIds = {
            R.drawable.hot, R.drawable.looks, R.drawable.seabuy,
            R.drawable.son, R.drawable.zx, R.drawable.mz,
            R.drawable.sm, R.drawable.js
    };

    @BindView(R.id.ib_found_icon)
    ImageButton ibFoundIcon;
    @BindView(R.id.tv_found_search)
    TextView tvFoundSearch;
    @BindView(R.id.ib_found_msg)
    ImageButton ibFoundMsg;
    @BindView(R.id.vp_found)
    ViewPager vpFound;
    @BindView(R.id.tl_10)
    SlidingTabLayout tl10;

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private LocalBroadcastManager mBroadcastManager;


    @Override
    protected String getUrl() {
        return null;
    }

    @Override
    protected void initData(String content) {

        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], imgIds[i], imgIds[i]));
        }
        vpFound.setAdapter(new FoundPagerAdapter(getActivity().getSupportFragmentManager()));

        tl10.setViewPager(vpFound);

        tl10.setTabData(mTabEntities);

        vpFound.setCurrentItem(0);

    }

    @Override
    protected void initTitle() {
        if (CacheUtils.getBoolean(getActivity(), Constants.IS_LOGIN)) {
            String profile_image_url = CacheUtils.getString(getActivity(), "profile_image_url");
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
            }).into(ibFoundIcon);
        } else {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.aliuser_place_holder);
            Bitmap zoom = BitmapUtils.zoom(bitmap, UIUtils.dp2px(40), UIUtils.dp2px(40));
            Bitmap circleBitmap = BitmapUtils.circleBitmap(zoom);
            ibFoundIcon.setImageBitmap(circleBitmap);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_ask;
    }


    @OnClick({R.id.ib_found_icon, R.id.tv_found_search, R.id.ib_found_msg})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_found_icon:
                mBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
                mBroadcastManager.sendBroadcast(new Intent(Constants.SWITCH2ME));
                break;
            case R.id.tv_found_search:
                startActivity(new Intent(getActivity(), SearchActivity.class));
                break;
            case R.id.ib_found_msg:
                startActivity(new Intent(getActivity(), MessageCenterActivity.class));
                break;
        }
    }


    public class FoundPagerAdapter extends FragmentPagerAdapter {

        public FoundPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new NewFoundFragment();
                case 1:
                    return new HotFoundFragment();
                case 2:
                    return new NewFoundFragment();
                case 3:
                    return new HotFoundFragment();
                case 4:
                    return new NewFoundFragment();
                case 5:
                    return new HotFoundFragment();
                case 6:
                    return new NewFoundFragment();
                case 7:
                    return new HotFoundFragment();
            }

            return null;
        }

        @Override
        public int getCount() {
            return mTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // 注释掉就取消了预加载
//            super.destroyItem(container, position, object);
        }
    }

}
