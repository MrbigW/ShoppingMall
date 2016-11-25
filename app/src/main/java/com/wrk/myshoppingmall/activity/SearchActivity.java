package com.wrk.myshoppingmall.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wrk.myshoppingmall.R;
import com.wrk.myshoppingmall.bean.ResultBean;
import com.wrk.myshoppingmall.common.BaseActivity;
import com.wrk.myshoppingmall.ui.MySearchView;
import com.wrk.myshoppingmall.utils.Constants;
import com.wrk.myshoppingmall.utils.DownLoaderUtils;
import com.wrk.myshoppingmall.utils.SearchALG;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SearchActivity extends BaseActivity {

    @BindView(R.id.searchView)
    MySearchView mSearchView;

    private SearchALG mALG;


    private List<String> changedHintDatas;
    //热搜数据
    private List<String> hot_datas;
    //提示列表数据
    private List<String> hint_datas;

    private List<String> url_datas;

    ResultBean mResultBean;

    @Override
    protected void initData() {

        getData();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }


    /**
     * 设置searview的监听
     */
    class MyOnSearchListener implements MySearchView.OnSearchListener {

        /**
         * 搜索回调
         *
         * @param searchText 进行搜索的文本
         */
        @Override
        public void onSearch(String searchText) {
            if (!TextUtils.isEmpty(searchText)) {
                Intent intent = new Intent(SearchActivity.this, ProductDetailsActivity.class);

                for (int i = 0; i < url_datas.size(); i++) {
                    if (hint_datas.get(i).equals(searchText)) {
                        intent.putExtra(Constants.GOODS_SHARE, url_datas.get(i));
                        SearchActivity.this.startActivity(intent);
                        removeCurrentActivity();
                    }
                }
            } else {
                Toast.makeText(SearchActivity.this, "搜索内容不能为空！", Toast.LENGTH_SHORT).show();
            }
        }

        /**
         * 刷新提示列表
         *
         * @param changedText 改变后的文本
         */
        @Override
        public void onRefreshHintList(String changedText) {
            if (changedHintDatas == null) {
                changedHintDatas = new ArrayList<>();
            } else {
                changedHintDatas.clear();
            }
            if (TextUtils.isEmpty(changedText)) {
                return;
            }
            for (int i = 0; i < hint_datas.size(); i++) {
                String hint_data = hint_datas.get(i);
                boolean isAdd = mALG.isAddToHintList(hint_data, changedText);
                if (isAdd) {
                    changedHintDatas.add(hint_datas.get(i));
                }
            }

            /**
             * 根据搜索框文本的变化，动态的改变提示的listView
             */
            mSearchView.updateHintList(changedHintDatas);

        }
    }

    /**
     * 设置数据
     */
    private void getData() {
        hot_datas = new ArrayList<>();
        hint_datas = new ArrayList<>();
        url_datas = new ArrayList<>();

        mALG = new SearchALG(this);

        getResultBean();
    }

    private void getResultBean() {
        new DownLoaderUtils().getJsonResult(Constants.HOME_URL)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        mSearchView.setOnSearchListener(new MyOnSearchListener());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        if (!TextUtils.isEmpty(s)) {
                            processData(s);
                            setData();
                        }
                    }
                });
    }

    private void setData() {

        /**
         * 设置提示数据的集合 hint_datas
         */
        List<ResultBean.BannerInfoBean> act_info = mResultBean.getBanner_info();
        for (int i = 0; i < act_info.size(); i++) {
            url_datas.add(act_info.get(i).getImage());
        }
        hint_datas.add("尚硅谷在线课堂");
        hint_datas.add("尚硅谷抢座");
        hint_datas.add("尚硅谷讲座");
        List<ResultBean.HotInfoBean> hot_info = mResultBean.getHot_info();
        for (int i = 0; i < hot_info.size(); i++) {
            hint_datas.add(hot_info.get(i).getName().trim());
            url_datas.add(hot_info.get(i).getFigure());
        }
        List<ResultBean.RecommendInfoBean> recommend_info = mResultBean.getRecommend_info();
        for (int i = 0; i < recommend_info.size(); i++) {
            hint_datas.add(recommend_info.get(i).getName().trim());
            url_datas.add(recommend_info.get(i).getFigure());
        }
        ResultBean.SeckillInfoBean seckill_info = mResultBean.getSeckill_info();
        List<ResultBean.SeckillInfoBean.ListBean> seckill_infoList = seckill_info.getList();
        for (int i = 0; i < seckill_infoList.size(); i++) {
            hint_datas.add(seckill_infoList.get(i).getName().trim());
            url_datas.add(seckill_infoList.get(i).getFigure());
        }

        Log.e("333", hint_datas.toString() + "---" + url_datas.toString());
        //设置提示列表的最大显示列数
        mSearchView.setMaxHintLines(8);


        for (int i = 0; i < 8; i++) {
            hot_datas.add(hint_datas.get(new Random().nextInt(hint_datas.size())));
        }

        //设置热搜数据显示的列数
        mSearchView.setHotNumColumns(2);
        //设置热搜数据
        mSearchView.setHotSearchDatas(hot_datas);


        /**
         * 设置自动保存搜索记录
         */
        mSearchView.keepSearchHistory(true);

        //设置保存搜索记录的个数
        mSearchView.setMaxHistoryRecordCount(6);
    }

    private void processData(String content) {
        JSONObject jsonObject = JSON.parseObject(content);

        //得到状态码
        String code = jsonObject.getString("code");
        String msg = jsonObject.getString("msg");
        String result = jsonObject.getString("result");

        //得到resultBean的数据
        mResultBean = JSON.parseObject(result, ResultBean.class);
    }


}
