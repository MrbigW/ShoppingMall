package com.wrk.myshoppingmall.fragment.WeiTao;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.wrk.myshoppingmall.R;
import com.wrk.myshoppingmall.activity.ProductDetailsActivity;
import com.wrk.myshoppingmall.adapter.weitao.TypeLeftAdapter;
import com.wrk.myshoppingmall.adapter.weitao.TypeRightCommonAdapter;
import com.wrk.myshoppingmall.adapter.weitao.TypeRightHotAdapter;
import com.wrk.myshoppingmall.bean.GoodsBean;
import com.wrk.myshoppingmall.bean.TypeBean;
import com.wrk.myshoppingmall.common.BaseFragment;
import com.wrk.myshoppingmall.utils.Constants;
import com.wrk.myshoppingmall.utils.DownLoaderUtils;
import com.wrk.myshoppingmall.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.wrk.myshoppingmall.R.id.lv_left;
import static com.wrk.myshoppingmall.utils.Constants.GOODS_BEAN;

/**
 * Created by MrbigW on 2016/11/26.
 * weChat:1024057635
 * GitHub:MrbigW
 * Usage: -.-
 * -------------------=.=------------------------
 */
public class TagFragment extends BaseFragment {

    private String[] urls = new String[]{Constants.SKIRT_URL, Constants.JACKET_URL, Constants.PANTS_URL, Constants.OVERCOAT_URL,
            Constants.ACCESSORY_URL, Constants.BAG_URL, Constants.DRESS_UP_URL, Constants.HOME_PRODUCTS_URL, Constants.STATIONERY_URL,
            Constants.DIGIT_URL, Constants.GAME_URL};

    @BindView(lv_left)
    ListView lvLeft;
    @BindView(R.id.rcv_hot)
    RecyclerView rcvHot;
    @BindView(R.id.rcv_type)
    RecyclerView rcvType;

    private TypeLeftAdapter leftAdapter;

    private List<TypeBean.ResultBean> result;

    @Override
    protected String getUrl() {
        return null;
    }

    @Override
    protected void initData(String content) {
        leftAdapter = new TypeLeftAdapter(getActivity());
        lvLeft.setAdapter(leftAdapter);
        // 为左边listview设置监听事件
        initListener(leftAdapter);
        lvLeft.setSelection(0);
        getDataFromNet(urls[0], 0);


    }


    @Override
    protected void initTitle() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_tag;
    }


    private void initListener(final TypeLeftAdapter adapter) {
        //点击监听
        lvLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                adapter.changeSelected(position);//刷新
                getDataFromNet(urls[position], position);
                leftAdapter.notifyDataSetChanged();
            }
        });

        //选中监听
        lvLeft.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                adapter.changeSelected(position);//刷新
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void getDataFromNet(String url, final int pos) {

        new DownLoaderUtils().getJsonResult(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.showToast(getActivity(), "服务器异常,请重试" + e.getMessage());
                    }

                    @Override
                    public void onNext(String s) {
                        processData(s);
                        TypeRightHotAdapter hotAdapter = new TypeRightHotAdapter(getActivity(), result.get(0).getHot_product_list());
                        hotAdapter.setOnItemClickListener(new TypeRightHotAdapter.OnItemClickListener() {
                            @Override
                            public void setOnItemClickListener(TypeBean.ResultBean.HotProductListBean data) {

                                String name = data.getName();
                                String cover_price = data.getCover_price();
                                String figure = data.getFigure();
                                String product_id = data.getProduct_id();

                                GoodsBean goodsBean = new GoodsBean(name, cover_price, figure, product_id);
                                Intent intent = new Intent(getActivity(), ProductDetailsActivity.class);
                                intent.putExtra(GOODS_BEAN, goodsBean);
                                getActivity().startActivity(intent);
                            }
                        });
                        rcvHot.setAdapter(hotAdapter);
                        rcvHot.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                        TypeRightCommonAdapter commonAdapter = new TypeRightCommonAdapter(getActivity(), result.get(0).getChild());
                        rcvType.setAdapter(commonAdapter);
                        rcvType.setLayoutManager(new GridLayoutManager(getActivity(), 3));
                    }
                });
    }

    private void processData(String content) {
        result = JSON.parseObject(content, TypeBean.class).getResult();
    }
}
