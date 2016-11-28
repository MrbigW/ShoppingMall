package com.wrk.myshoppingmall.adapter.found;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.opendanmaku.DanmakuItem;
import com.opendanmaku.IDanmakuItem;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.wrk.myshoppingmall.R;
import com.wrk.myshoppingmall.bean.NewPostBean;
import com.wrk.myshoppingmall.utils.BitmapUtils;
import com.wrk.myshoppingmall.utils.Constants;
import com.wrk.myshoppingmall.utils.UIUtils;

import java.util.ArrayList;
import java.util.Collections;
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

public class NewPostListViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<NewPostBean.ResultBean> mResultBeen;
    // 弹幕内容集合
    private List<String> comment_list;

    public NewPostListViewAdapter(Context context, List<NewPostBean.ResultBean> data) {
        this.mContext = context;
        this.mResultBeen = data;
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
            convertView = View.inflate(mContext, R.layout.item_listview_newpost, null);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }

        // 设置数据
        NewPostBean.ResultBean resultBean = mResultBeen.get(position);
        // 设置名字
        mHolder.tvCommunityUsername.setText(resultBean.getUsername());
        // 设置图片
        Picasso.with(mContext)
                .load(Constants.Base_URl_IMAGE + resultBean.getFigure())
                .into(mHolder.ivCommunityFigure);
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
        mHolder.tvCommunitySaying.setText(resultBean.getSaying());
        mHolder.tvCommunityLikes.setText(resultBean.getLikes());
        mHolder.tvCommunityComments.setText(resultBean.getComments());

        // 设置弹幕
        comment_list = (List<String>) resultBean.getComment_list();
        if (comment_list != null && comment_list.size() > 0) {
            mHolder.danmakuView.setVisibility(View.VISIBLE);

            List<IDanmakuItem> list = new ArrayList<>();
            for (int i = 0; i < comment_list.size(); i++) {
                IDanmakuItem item = new DanmakuItem(mContext, comment_list.get(i), mHolder.danmakuView.getWidth());
                list.add(item);
            }
            Collections.shuffle(comment_list);
            mHolder.danmakuView.addItem(list, true);
            mHolder.danmakuView.show();
        } else {
            mHolder.danmakuView.setVisibility(View.GONE);
        }

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_community_username)
        TextView tvCommunityUsername;
        @BindView(R.id.tv_community_addtime)
        TextView tvCommunityAddtime;
        @BindView(R.id.rl)
        RelativeLayout rl;
        @BindView(R.id.iv_community_figure)
        ImageView ivCommunityFigure;
        @BindView(R.id.danmakuView)
        com.opendanmaku.DanmakuView danmakuView;
        @BindView(R.id.tv_community_saying)
        TextView tvCommunitySaying;
        @BindView(R.id.tv_community_likes)
        TextView tvCommunityLikes;
        @BindView(R.id.tv_community_comments)
        TextView tvCommunityComments;
        @BindView(R.id.ib_new_post_avatar)
        ImageButton ibNewPostAvatar;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}


























