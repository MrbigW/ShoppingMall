package com.wrk.myshoppingmall.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wrk.myshoppingmall.R;
import com.wrk.myshoppingmall.adapter.ShoppingCartAdapter;
import com.wrk.myshoppingmall.bean.GoodsBean;
import com.wrk.myshoppingmall.common.BaseFragment;
import com.wrk.myshoppingmall.utils.CartProvider;
import com.wrk.myshoppingmall.utils.Constants;
import com.wrk.myshoppingmall.utils.pay.MyAliPay;

import java.util.List;

import butterknife.BindView;

/**
 * Created by MrbigW on 2016/11/21.
 * weChat:1024057635
 * GitHub:MrbigW
 * Usage: -.-
 * -------------------=.=------------------------
 */

public class CartFragment extends BaseFragment {
    @BindView(R.id.tv_cart_edit)
    TextView tvCartEdit;
    @BindView(R.id.shoppingcart_recyclerView)
    RecyclerView shoppingcartRecyclerView;
    @BindView(R.id.btn_go)
    Button btnGo;
    @BindView(R.id.ll_nodata)
    LinearLayout llNodata;
    @BindView(R.id.checkbox_all)
    CheckBox checkboxAll;
    @BindView(R.id.tv_total_price1)
    TextView tvTotalPrice1;
    @BindView(R.id.btn_order1)
    Button btnOrder1;
    @BindView(R.id.btn_delete1)
    Button btnDelete1;

    private MyAliPay mAliPay;

    private CartProvider mCartProvider;

    private ShoppingCartAdapter mAdpater;

    // 编辑状态
    private static final String ACTION_EDIT = "1";
    // 完成状态
    private static final String ACTION_COMPLETE = "2";

    private LocalBroadcastManager mBroadcastManager;

    private BroadcastReceiver mCartRefreshRecevier = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            showData();
        }
    };

    @Override
    protected String getUrl() {
        return null;
    }

    @Override
    protected void initData(String content) {
        initOnClick();
        showData();
    }

    private void initOnClick() {
        // 编辑点击事件
        tvCartEdit.setTag(ACTION_EDIT);
        tvCartEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String state = (String) tvCartEdit.getTag();
                if (state.equals(ACTION_EDIT)) {
                    showDeleteButton();
                } else if (state.equals(ACTION_COMPLETE)) {
                    hideDeleteButton();
                }
            }

        });

        // 删除点击事件
        btnDelete1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdpater.deleteData();
                mAdpater.checkAll_none();
                mAdpater.ShowTotalPrice();

                if (mAdpater != null && mAdpater.getCount() > 0) {
                    llNodata.setVisibility(View.GONE);
                } else {
                    llNodata.setVisibility(View.VISIBLE);
                }
            }
        });

        // 结算点击事件
        btnOrder1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAliPay.pay(v, mAdpater.getProductNames(), "正在结算……", mAdpater.getTotalPrice() + "");
            }
        });

        // 跳转到首页
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnGoToHome != null) {
                    mOnGoToHome.goToHome();
                }
            }
        });
    }

    private void showData() {
        // 购物车所有数据
        List<GoodsBean> goodsBeen = mCartProvider.getAllData();
        llNodata.setVisibility(View.GONE);
        mAdpater = new ShoppingCartAdapter(getActivity(), goodsBeen, checkboxAll, tvTotalPrice1);
        if (goodsBeen != null && goodsBeen.size() > 0) {
            // 设置适配器
            shoppingcartRecyclerView.setAdapter(mAdpater);
            shoppingcartRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        } else {
            llNodata.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void initTitle() {
        mAliPay = new MyAliPay(getActivity());
        mCartProvider = new CartProvider(getActivity());
        mBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        mBroadcastManager.registerReceiver(mCartRefreshRecevier, new IntentFilter(Constants.CART_REFRESH));
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_cart;
    }


    private void showDeleteButton() {
        tvCartEdit.setTag(ACTION_COMPLETE);
        tvCartEdit.setText("完成");
        btnDelete1.setVisibility(View.VISIBLE);
        btnOrder1.setVisibility(View.GONE);
        mAdpater.checkAll_none(false);
        mAdpater.checkAll_none();
        mAdpater.ShowTotalPrice();
    }

    private void hideDeleteButton() {
        tvCartEdit.setTag(ACTION_EDIT);
        tvCartEdit.setText("编辑");
        btnDelete1.setVisibility(View.GONE);
        btnOrder1.setVisibility(View.VISIBLE);
        mAdpater.checkAll_none(true);
        mAdpater.checkAll_none();
        mAdpater.ShowTotalPrice();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBroadcastManager.unregisterReceiver(mCartRefreshRecevier);
    }


    public void setOnGoToHome(onGoToHome onGoToHome) {
        mOnGoToHome = onGoToHome;
    }

    public onGoToHome mOnGoToHome;


    public interface onGoToHome {
        void goToHome();
    }

}
