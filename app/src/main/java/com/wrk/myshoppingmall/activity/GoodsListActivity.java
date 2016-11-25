package com.wrk.myshoppingmall.activity;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.wrk.myshoppingmall.R;
import com.wrk.myshoppingmall.adapter.GoodsListAdapter;
import com.wrk.myshoppingmall.adapter.SelectExpandableListViewAdapter;
import com.wrk.myshoppingmall.bean.GoodsBean;
import com.wrk.myshoppingmall.bean.TypeListBean;
import com.wrk.myshoppingmall.common.BaseActivity;
import com.wrk.myshoppingmall.utils.Constants;
import com.wrk.myshoppingmall.utils.DownLoaderUtils;
import com.wrk.myshoppingmall.utils.SpaceItemDecoration;
import com.wrk.myshoppingmall.utils.ToastUtil;
import com.wrk.myshoppingmall.utils.UIUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.wrk.myshoppingmall.utils.Constants.GOODS_BEAN;

public class GoodsListActivity extends BaseActivity {

    private String[] urls = new String[]{
            Constants.CLOSE_STORE,
            Constants.GAME_STORE,
            Constants.COMIC_STORE,
            Constants.COSPLAY_STORE,
            Constants.GUFENG_STORE,
            Constants.STICK_STORE,
            Constants.WENJU_STORE,
            Constants.FOOD_STORE,
            Constants.SHOUSHI_STORE,
    };

    @BindView(R.id.ib_good_info_back)
    ImageButton ibGoodInfoBack;
    @BindView(R.id.tv_detail_title)
    TextView tvDetailTitle;
    @BindView(R.id.ib_good_info_more)
    ImageButton ibGoodInfoMore;
    @BindView(R.id.tv_goods_list_sort)
    TextView tvGoodsListSort;
    @BindView(R.id.tv_goods_list_price)
    TextView tvGoodsListPrice;
    @BindView(R.id.iv_goods_list_arrow)
    ImageView ivGoodsListArrow;
    @BindView(R.id.ll_goods_list_price)
    LinearLayout llGoodsListPrice;
    @BindView(R.id.tv_goods_list_select)
    TextView tvGoodsListSelect;
    @BindView(R.id.rcv_list)
    RecyclerView rcvList;
    @BindView(R.id.ibn_back)
    ImageButton ibnBack;
    @BindView(R.id.tv_ib_drawer_layout_title)
    TextView tvIbDrawerLayoutTitle;
    @BindView(R.id.ib_drawer_layout_confirm)
    TextView ibDrawerLayoutConfirm;
    @BindView(R.id.expandlv_filtrate)
    ExpandableListView expandlvFiltrate;
    @BindView(R.id.btn_select_filtrate)
    Button btnSelectFiltrate;
    @BindView(R.id.drawer_right)
    DrawerLayout drawerRight;

    // 得到点击的位置
    private int position;

    private List<TypeListBean.ResultBean.PageDataBean> page_data;
    private GoodsListAdapter mAdapter;
    private SelectExpandableListViewAdapter mExpandableAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_goods_list;
    }

    @Override
    protected void initData() {
        position = getIntent().getIntExtra("position", -1);
        getDataFromNet();
        initDrawLayout();
    }

    private void initDrawLayout() {
        mExpandableAdapter = new SelectExpandableListViewAdapter(GoodsListActivity.this);
        expandlvFiltrate.setAdapter(mExpandableAdapter);

        // ExpandableListView的父项的点击事件
        expandlvFiltrate.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return false;
            }
        });
        // ExpandableListView的子项的点击事件
        expandlvFiltrate.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                return true;
            }
        });

    }


    private void getDataFromNet() {
        new DownLoaderUtils().getJsonResult(urls[position])
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        setData();
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.showToast(GoodsListActivity.this, "联网失败：" + e.getMessage());
                    }

                    @Override
                    public void onNext(String s) {
                        if (!TextUtils.isEmpty(s)) {
                            processData(s);
                        }
                    }
                });
    }

    private void setData() {
        // RecyclerView的GridLayoutManger
        GridLayoutManager manager = new GridLayoutManager(GoodsListActivity.this, 2);
        rcvList.setLayoutManager(manager);
        // 适配器
        mAdapter = new GoodsListAdapter(GoodsListActivity.this, page_data);
        // 设置间距
        rcvList.addItemDecoration(new SpaceItemDecoration(10));
        // 设置适配器
        rcvList.setAdapter(mAdapter);
        // 设置每项的单击事件
        mAdapter.setOnItemClickListener(new GoodsListAdapter.OnItemClickListener() {
            @Override
            public void setOnItemClickListener(TypeListBean.ResultBean.PageDataBean data) {
                String name = data.getName();
                String cover_price = data.getCover_price();
                String figure = data.getFigure();
                String product_id = data.getProduct_id();

                GoodsBean goodsBean = new GoodsBean(name, cover_price, figure, product_id);
                Intent intent = new Intent(GoodsListActivity.this, ProductDetailsActivity.class);
                intent.putExtra(GOODS_BEAN, goodsBean);
                startActivity(intent);
            }
        });
    }

    private void processData(String content) {
        TypeListBean typeListBean = JSON.parseObject(content, TypeListBean.class);
        page_data = typeListBean.getResult().getPage_data();
    }


    @OnClick({R.id.ib_good_info_back, R.id.ib_good_info_more, R.id.tv_goods_list_sort, R.id.ll_goods_list_price, R.id.tv_goods_list_select, R.id.ibn_back, R.id.ib_drawer_layout_confirm, R.id.btn_select_filtrate})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_good_info_back: // 返回
                removeCurrentActivity();
                break;
            case R.id.ib_good_info_more: // 返回主页面
                removeCurrentActivity();
                break;
            case R.id.tv_goods_list_sort: // 综合排序
                sort_nothing();
                break;
            case R.id.ll_goods_list_price: // 价格排序
                sort_price();
                break;
            case R.id.tv_goods_list_select: // 打开筛选
                openDrawer();
                break;
            case R.id.ibn_back: // 离开筛选
                drawerRight.closeDrawer(Gravity.RIGHT);
                break;
            case R.id.ib_drawer_layout_confirm: // 确认筛选
                confirm_select();
                break;
            case R.id.btn_select_filtrate:// 重置过滤
                filtrate_select();
                break;
        }
    }

    private void openDrawer() {
        tvGoodsListPrice.setTextColor(UIUtils.getColor(R.color.color_tv));
        tvGoodsListSort.setTextColor(UIUtils.getColor(R.color.color_tv));
        ivGoodsListArrow.setVisibility(View.GONE);
        drawerRight.openDrawer(Gravity.RIGHT);
    }

    private void filtrate_select() {

    }

    private void confirm_select() {

    }

    private boolean isUp = false;

    private void sort_price() {
        ivGoodsListArrow.setVisibility(View.VISIBLE);
        tvGoodsListSort.setTextColor(UIUtils.getColor(R.color.color_tv));
        tvGoodsListPrice.setTextColor(UIUtils.getColor(R.color.color_style));
        // 默认降序排
        if (isUp) {
            ivGoodsListArrow.setBackgroundResource(R.drawable.new_price_sort_asc);
            mAdapter.sortWithPrice(isUp);
            isUp = false;
        } else {
            ivGoodsListArrow.setBackgroundResource(R.drawable.new_price_sort_desc);
            mAdapter.sortWithPrice(isUp);
            isUp = true;
        }

    }

    private void sort_nothing() {
        // 将价格排序恢复原状态
        tvGoodsListPrice.setTextColor(UIUtils.getColor(R.color.color_tv));
        ivGoodsListArrow.setVisibility(View.GONE);
        isUp = false;
        // 改变综合排序的状态7
        tvGoodsListSort.setTextColor(UIUtils.getColor(R.color.color_style));
        mAdapter.sortWithNothing();
    }
}
