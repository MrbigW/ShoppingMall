package com.wrk.myshoppingmall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wrk.myshoppingmall.R;
import com.wrk.myshoppingmall.bean.GoodsBean;
import com.wrk.myshoppingmall.ui.NumAddSubView;
import com.wrk.myshoppingmall.utils.CartProvider;
import com.wrk.myshoppingmall.utils.Constants;

import java.util.Iterator;
import java.util.List;

/**
 * Created by MrbigW on 2016/11/27.
 * weChat:1024057635
 * GitHub:MrbigW
 * Usage: -.-
 * -------------------=.=------------------------
 */
public class ShoppingCartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private TextView tv_total_price;
    private CheckBox checkbox_all;
    private Context mContext;
    private List<GoodsBean> mListBeen;

    private CartProvider mCartProvider;

    public ShoppingCartAdapter(Context context, List<GoodsBean> goodsBeen, CheckBox checkboxAll, TextView tvTotalPrice1) {
        this.mContext = context;
        this.mListBeen = goodsBeen;
        this.checkbox_all = checkboxAll;
        this.tv_total_price = tvTotalPrice1;
        this.mCartProvider = new CartProvider(context);

        ShowTotalPrice();

        // 设置Item的点击事件
        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                // 1.状态的变化
                GoodsBean cart = mListBeen.get(position);
                cart.setIsChildSelected(!cart.isChildSelected()); // 状态取反
                notifyItemChanged(position); // 刷新状态
                // 2.保存状态
                mCartProvider.updateData(cart);
                // 3.设置全选和反选
                checkAll_none();

                // 4.设置总价格
                ShowTotalPrice();
            }
        });
        // 校验是否全选
        checkAll_none();
        // 设置Checkbox_all的点击事件
        checkbox_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 得到checkBox的状态
                // 设置全选和非全选
                checkAll_none(checkbox_all.isChecked());

                ShowTotalPrice();
            }
        });


    }

    public void checkAll_none() {
        if (mListBeen != null && mListBeen.size() > 0) {

            int num = 0;

            for (int i = 0; i < mListBeen.size(); i++) {
                GoodsBean cart = mListBeen.get(i);
                if (!cart.isChildSelected()) {
                    checkbox_all.setChecked(false);
                } else {
                    num++;
                }
            }

            if (num == mListBeen.size()) {
                checkbox_all.setChecked(true);
            }
        }
    }

    public void checkAll_none(boolean isChecked) {
        if (mListBeen != null && mListBeen.size() > 0) {


            for (int i = 0; i < mListBeen.size(); i++) {
                GoodsBean cart = mListBeen.get(i);
                cart.setIsChildSelected(isChecked);
                notifyItemChanged(i); // 刷新适配器
            }


        }
    }

    /**
     * 显示总价格
     */
    public void ShowTotalPrice() {
        tv_total_price.setText(getTotalPrice() + "");
    }

    public float getTotalPrice() {
        float result = 0;

        if (mListBeen != null && mListBeen.size() > 0) {
            for (int i = 0; i < mListBeen.size(); i++) {
                GoodsBean cart = mListBeen.get(i);
                // 是否被勾选
                if (cart.isChildSelected()) {
                    result += Double.parseDouble(cart.getCover_price()) * cart.getNumber();
                }

            }
        }

        return result;
    }


    public String getProductNames() {
        String result = "";
        if (mListBeen != null && mListBeen.size() > 0) {
            for (int i = 0; i < mListBeen.size(); i++) {
                GoodsBean cart = mListBeen.get(i);
                // 是否被勾选
                if (cart.isChildSelected()) {
                    result += cart.getName() + "----";
                }

            }
        }
        return result;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(mContext, R.layout.item_shopping_cart, null);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        // 1.根据位置得到数据
        final GoodsBean cart = mListBeen.get(position);
        // 2.绑定数据
        Picasso.with(mContext)
                .load(Constants.Base_URl_IMAGE + cart.getFigure())
                .placeholder(R.drawable.tupian_bg_tmall)
                .into(viewHolder.iv_icon);

        viewHolder.tv_name.setText(cart.getName());

        viewHolder.tv_price.setText("￥" + cart.getCover_price());

        viewHolder.num_add_sub_view.setValue(cart.getNumber());

        // 是否选中
        viewHolder.checkbox.setChecked(cart.isChildSelected());

        viewHolder.num_add_sub_view.setButtonOnClickListener(new NumAddSubView.onButtonOnClickListener() {
            @Override
            public void onButtonSubOnClick(View view, int value) {
                updatePrice(value, cart);
            }

            @Override
            public void onButtonAddOnClick(View view, int value) {
                // 设置该值
                updatePrice(value, cart);
            }
        });

    }

    private void updatePrice(int value, GoodsBean cart) {
        // 设置该值
        cart.setNumber(value);
        // 保存到内存与本地中
        mCartProvider.updateData(cart);
        // 显示总价格
        ShowTotalPrice();
    }

    @Override
    public int getItemCount() {
        return mListBeen.size();
    }

    /**
     * 清楚数据
     */
    public void clearData() {
        mListBeen.clear();
        notifyItemRangeRemoved(0, mListBeen.size());
    }

    /**
     * 添加数据
     *
     * @param count
     * @param beanList
     */
    public void addData(int count, List<GoodsBean> beanList) {
        mListBeen.addAll(count, beanList);
        notifyItemRangeChanged(count, mListBeen.size());
    }

    public int getCount() {
        return mListBeen.size();
    }

    public void addData(List<GoodsBean> beanList) {
        addData(0, beanList);
    }

    public void deleteData() {
        if (mListBeen != null && mListBeen.size() > 0) {

            for (Iterator iterator = mListBeen.iterator(); iterator.hasNext(); ) {
                GoodsBean cart = (GoodsBean) iterator.next();
                if (cart.isChildSelected()) {
                    int i = mListBeen.indexOf(cart);
                    iterator.remove();
                    mCartProvider.deleteData(cart);
                    notifyItemRemoved(i);
                }
            }
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private CheckBox checkbox;
        private ImageView iv_icon;
        private TextView tv_name;
        private TextView tv_price;
        private NumAddSubView num_add_sub_view;


        public ViewHolder(final View itemView) {
            super(itemView);

            num_add_sub_view = (NumAddSubView) itemView.findViewById(R.id.num_add_sub_view);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            iv_icon = (ImageView) itemView.findViewById(R.id.iv_icon);
            checkbox = (CheckBox) itemView.findViewById(R.id.checkbox);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.OnItemClick(v, getLayoutPosition());
                    }
                }
            });

        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void OnItemClick(View view, int position);
    }

}
































