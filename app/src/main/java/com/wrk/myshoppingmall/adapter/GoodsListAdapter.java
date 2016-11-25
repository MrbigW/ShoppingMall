package com.wrk.myshoppingmall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wrk.myshoppingmall.R;
import com.wrk.myshoppingmall.bean.TypeListBean;
import com.wrk.myshoppingmall.utils.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by MrbigW on 2016/11/25.
 * weChat:1024057635
 * GitHub:MrbigW
 * Usage: -.-
 * -------------------=.=------------------------
 */
public class GoodsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<TypeListBean.ResultBean.PageDataBean> mNoSortData;
    private List<TypeListBean.ResultBean.PageDataBean> mPageDataBean;


    public GoodsListAdapter(Context context, List<TypeListBean.ResultBean.PageDataBean> page_data) {
        this.mContext = context;
        this.mPageDataBean = page_data;
        mNoSortData = new ArrayList<>();
        mNoSortData.addAll(mPageDataBean);
    }


    public void sortWithNothing() {
        if (mNoSortData != null && mNoSortData.size() > 0) {
            // 替换数据
            mPageDataBean.clear();
            mPageDataBean.addAll(mNoSortData);
            // 刷新数据
            notifyItemRangeChanged(0, mPageDataBean.size());
        }
    }

    public void sortWithPrice(boolean up) {

        if (up) {
            Collections.sort(mPageDataBean, new Comparator<TypeListBean.ResultBean.PageDataBean>() {
                @Override
                public int compare(TypeListBean.ResultBean.PageDataBean o1, TypeListBean.ResultBean.PageDataBean o2) {
                    if (Double.parseDouble(o1.getCover_price()) > Double.parseDouble(o2.getCover_price())) {
                        return 1;
                    } else if (Double.parseDouble(o1.getCover_price()) == Double.parseDouble(o2.getCover_price())) {
                        return 0;
                    } else {
                        return -1;
                    }
                }
            });
        } else {
            Collections.sort(mPageDataBean, new Comparator<TypeListBean.ResultBean.PageDataBean>() {
                @Override
                public int compare(TypeListBean.ResultBean.PageDataBean o1, TypeListBean.ResultBean.PageDataBean o2) {
                    if (Double.parseDouble(o1.getCover_price()) > Double.parseDouble(o2.getCover_price())) {
                        return -1;
                    } else if (Double.parseDouble(o1.getCover_price()) == Double.parseDouble(o2.getCover_price())) {
                        return 0;
                    } else {
                        return 1;
                    }
                }
            });
        }
        notifyItemRangeChanged(0, mPageDataBean.size());
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(mContext, R.layout.item_goods_layout, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        ((ViewHolder) holder).setData(mPageDataBean.get(position));
    }

    @Override
    public int getItemCount() {
        return mPageDataBean == null ? 0 : mPageDataBean.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_hot;
        private TextView tv_name;
        private TextView tv_price;
        private TypeListBean.ResultBean.PageDataBean data;

        public ViewHolder(View itemView) {
            super(itemView);

            iv_hot = (ImageView) itemView.findViewById(R.id.iv_hot);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.setOnItemClickListener(data);
                    }
                }
            });

        }


        public void setData(TypeListBean.ResultBean.PageDataBean data) {
            this.data = data;
            tv_name.setText(data.getName());
            tv_price.setText("￥" + data.getCover_price());
            Picasso.with(mContext)
                    .load(Constants.Base_URl_IMAGE + data.getFigure())
                    .placeholder(R.drawable.tupian_bg_tmall)
                    .fit()
                    .into(iv_hot);
        }

    }

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void setOnItemClickListener(TypeListBean.ResultBean.PageDataBean data);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}





















