package com.wrk.myshoppingmall.activity;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;
import com.wrk.myshoppingmall.R;
import com.wrk.myshoppingmall.common.BaseActivity;
import com.wrk.myshoppingmall.utils.UIUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import uk.co.senab.photoview.PhotoView;

public class ProductPhotosActivity extends BaseActivity {


    @BindView(R.id.ibn_back)
    ImageButton ibnBack;
    @BindView(R.id.vp_photo)
    ViewPager vpPhoto;
    @BindView(R.id.ll_photo_point)
    LinearLayout llPhotoPoint;
    private ArrayList<String> mPhoto_url;
    private int mPos;

    @Override
    protected void initData() {
        // 获取传过来的url集合
        mPhoto_url = getIntent().getStringArrayListExtra("photo_url");
        mPos = getIntent().getIntExtra("position", 0);
        vpPhoto.setAdapter(new PhotoPagerAdapter());
        vpPhoto.setCurrentItem(mPos-1);
        vpPhoto.addOnPageChangeListener(new PhotoOnPageChangeListener());
        llPhotoPoint.removeAllViews();
        for (int i = 0; i < mPhoto_url.size(); i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(R.drawable.point_selector);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(UIUtils.dp2px(10), UIUtils.dp2px(10));

            if (i == 0) {
                imageView.setEnabled(true);
            } else {
                imageView.setEnabled(false);
                params.leftMargin = UIUtils.dp2px(8);
            }
            imageView.setLayoutParams(params);
            llPhotoPoint.addView(imageView);
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_product_photos;
    }


    @OnClick(R.id.ibn_back)
    public void onClick() {
        removeCurrentActivity();
    }


    private class PhotoPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return mPhoto_url.size();
        }

        @Override
        public Object instantiateItem(final ViewGroup container, int position) {

            final PhotoView photoView = new PhotoView(container.getContext());

            Picasso.with(container.getContext())
                    .load(mPhoto_url.get(position))
                    .into(photoView);

            container.addView(photoView);

            return photoView;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
            container.removeView((View) object);
        }
    }

    // 之前高亮的点
    private int prePosintion;

    private class PhotoOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            // 将之前高亮的点设置为默认
            llPhotoPoint.getChildAt(prePosintion).setEnabled(false);
            // 把现在的点设置为高亮
            llPhotoPoint.getChildAt(position).setEnabled(true);
            // 注意
            prePosintion = position;
        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}

















