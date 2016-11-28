package com.wrk.myshoppingmall.adapter.weitao;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wrk.myshoppingmall.R;
import com.wrk.myshoppingmall.bean.TypeBean;
import com.wrk.myshoppingmall.utils.Constants;

import java.util.List;

/**
 * Created by MrbigW on 2016/11/26.
 * weChat:1024057635
 * GitHub:MrbigW
 * Usage: -.-
 * -------------------=.=------------------------
 */

public class TypeRightCommonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<TypeBean.ResultBean.ChildBean> mChildBeanList;

    public TypeRightCommonAdapter(Context context, List<TypeBean.ResultBean.ChildBean> data) {
        this.mContext = context;
        this.mChildBeanList = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(mContext, R.layout.item_type_hot, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.tv_hot_price.setText(mChildBeanList.get(position).getName());
        viewHolder.tv_hot_price.setTextColor(Color.BLACK);
        Picasso.with(mContext)
                .load(Constants.Base_URl_IMAGE + mChildBeanList.get(position).getPic())
                .fit()
                .placeholder(R.drawable.tupian_bg_tmall)
                .into(viewHolder.iv_hot_img);
    }

    @Override
    public int getItemCount() {
        return mChildBeanList == null ? 0 : mChildBeanList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_hot_price;
        private ImageView iv_hot_img;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_hot_price = (TextView) itemView.findViewById(R.id.tv_hot_price);
            iv_hot_img = (ImageView) itemView.findViewById(R.id.iv_hot_img);
        }


    }
}


















