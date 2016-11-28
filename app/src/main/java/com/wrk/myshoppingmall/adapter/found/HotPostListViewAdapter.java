package com.wrk.myshoppingmall.adapter.found;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.wrk.myshoppingmall.R;
import com.wrk.myshoppingmall.bean.HotPostBean;
import com.wrk.myshoppingmall.utils.BitmapUtils;
import com.wrk.myshoppingmall.utils.Constants;
import com.wrk.myshoppingmall.utils.UIUtils;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrbigW on 2016/11/27.
 * weChat:1024057635
 * GitHub:MrbigW
 * Usage: -.-
 * -------------------=.=------------------------
 */

public class HotPostListViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<HotPostBean.ResultBean> mResultBeen;


    public HotPostListViewAdapter(Context context, List<HotPostBean.ResultBean> result) {
        this.mContext = context;
        this.mResultBeen = result;
    }

    @Override
    public int getCount() {
        return mResultBeen == null ? 0 : mResultBeen.size();
    }

    @Override
    public Object getItem(int position) {
        return mResultBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_hotpost_listview, null);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }

        // 设置数据
        HotPostBean.ResultBean resultBean = mResultBeen.get(position);
        // 设置名字
        mHolder.tvHotUsername.setText(resultBean.getUsername());

        SimpleDateFormat myFmt = new SimpleDateFormat("MM-dd HH:mm");
        mHolder.tvHotAddtime.setText(myFmt.format(Integer.parseInt(resultBean.getAdd_time())));


        // 设置图片
        Picasso.with(mContext)
                .load(Constants.Base_URl_IMAGE + resultBean.getFigure())
                .into(mHolder.ivHotFigure);
        // 设置圆形头像
        Picasso.with(mContext)
                .load(Constants.Base_URl_IMAGE + resultBean.getAvatar())
                .transform(new Transformation() {
                    @Override
                    public Bitmap transform(Bitmap source) {
                        // 对图像先进行压缩出路
                        Bitmap zoom = BitmapUtils.zoom(source, UIUtils.dp2px(40), UIUtils.dp2px(40));
                        // 再将图片圆形处理
                        Bitmap circleBitmap = BitmapUtils.circleBitmap(zoom);
                        // 回收source
                        source.recycle();
                        return circleBitmap;
                    }

                    @Override
                    public String key() {
                        return "";
                    }
                }).into(mHolder.ibNewPostAvatar);

        //设置内容
        mHolder.tvHotSaying.setText(resultBean.getSaying());
        mHolder.tvHotLikes.setText(resultBean.getLikes());
        mHolder.tvHotComments.setText(resultBean.getComments());
        // 设置类型
        String is_top = resultBean.getIs_top();
        if ("1".equals(is_top)) {
            LinearLayout.LayoutParams textViewLp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            TextView textView = new TextView(mContext);
            textView.setText("置顶");
            textViewLp.setMargins(UIUtils.dp2px(10), 0, UIUtils.dp2px(5), 0);
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(Color.WHITE);
            textView.setBackgroundResource(R.drawable.is_top_shape);
            textView.setPadding(UIUtils.dp2px(5), UIUtils.dp2px(5), UIUtils.dp2px(5), UIUtils.dp2px(5));
            mHolder.llHotPost.removeAllViews();
            mHolder.llHotPost.addView(textView, textViewLp);
        }
        String is_hot = resultBean.getIs_hot();
        if ("1".equals(is_hot)) {
            LinearLayout.LayoutParams textViewLp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            TextView textView = new TextView(mContext);
            textViewLp.setMargins(0, 0, UIUtils.dp2px(5), 0);
            textView.setText("热门");
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(Color.WHITE);
            textView.setPadding(UIUtils.dp2px(5), UIUtils.dp2px(5), UIUtils.dp2px(5), UIUtils.dp2px(5));
            textView.setBackgroundResource(R.drawable.is_hot_shape);
            mHolder.llHotPost.addView(textView, textViewLp);
        }
        String is_essence = resultBean.getIs_essence();
        if ("1".equals(is_essence)) {
            LinearLayout.LayoutParams textViewLp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            textViewLp.setMargins(0, 0, UIUtils.dp2px(5), 0);
            TextView textView = new TextView(mContext);
            textView.setText("精华");
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(Color.WHITE);
            textView.setPadding(UIUtils.dp2px(5), UIUtils.dp2px(5), UIUtils.dp2px(5), UIUtils.dp2px(5));
            textView.setBackgroundResource(R.drawable.is_essence_shape);
            mHolder.llHotPost.addView(textView, textViewLp);
        }

        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.tv_hot_username)
        TextView tvHotUsername;
        @BindView(R.id.tv_hot_addtime)
        TextView tvHotAddtime;
        @BindView(R.id.rl)
        RelativeLayout rl;
        @BindView(R.id.iv_hot_figure)
        ImageView ivHotFigure;
        @BindView(R.id.ll_hot_post)
        LinearLayout llHotPost;
        @BindView(R.id.tv_hot_saying)
        TextView tvHotSaying;
        @BindView(R.id.tv_hot_likes)
        TextView tvHotLikes;
        @BindView(R.id.tv_hot_comments)
        TextView tvHotComments;
        @BindView(R.id.ib_new_post_avatar)
        ImageButton ibNewPostAvatar;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}


























