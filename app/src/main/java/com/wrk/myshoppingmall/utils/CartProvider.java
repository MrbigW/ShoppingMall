package com.wrk.myshoppingmall.utils;


import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wrk.myshoppingmall.bean.GoodsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MrbigW on 2016/10/27.
 * weChat:1024057635
 * Github:MrbigW
 * Usage: 购物车存储器类，存取购物车的内容，取内容
 * -------------------=.=------------------------
 */

public class CartProvider {

    public static final String JSON_CART = "myshopping_cart";
    private Context mContext;

    // 千量级性能优于haspMap;
    private SparseArray<GoodsBean> mSparseArray;

    public CartProvider(Context context) {
        this.mContext = context;
        mSparseArray = new SparseArray<>(10);
        listToSparse();
    }

    /**
     * 将列表中的数据转换成SparseArray();
     */
    private void listToSparse() {
        List<GoodsBean> shoppingCarts = getAllData();
        if (shoppingCarts != null && shoppingCarts.size() > 0) {
            Log.e("111", shoppingCarts.size() + "");
            for (int i = 0; i < shoppingCarts.size(); i++) {
                GoodsBean shoppingCart = shoppingCarts.get(i);
                mSparseArray.put(Integer.parseInt(shoppingCart.getProduct_id()), shoppingCart);
            }
        }

    }

    /**
     * 得到所有数据
     *
     * @return
     */
    public List<GoodsBean> getAllData() {
        return getLocalData();
    }

    /**
     * 得到缓存的数据
     *
     * @return
     */
    private List<GoodsBean> getLocalData() {
        List<GoodsBean> shoppingCarts = new ArrayList<>();
        String saveJson = CacheUtils.getString(mContext, JSON_CART); // json字符串
        if (!TextUtils.isEmpty(saveJson)) {
            // 把json数据解析成集合
            shoppingCarts = new Gson().fromJson(saveJson, new TypeToken<List<GoodsBean>>() {
            }.getType());
        }
        return shoppingCarts;
    }

    /**
     * 添加购物车数据
     *
     * @param goodsBean
     */
    public void addData(GoodsBean goodsBean) {
        GoodsBean tempCart = mSparseArray.get(Integer.parseInt(goodsBean.getProduct_id())); // 判断是否在列表存在
        if (tempCart != null) {
            // 列表中已存在
            tempCart.setNumber(tempCart.getNumber() + 1);
        } else {
            tempCart = goodsBean;
            tempCart.setNumber(1);
        }

        // 保存到sparseArray
        mSparseArray.put(Integer.parseInt(tempCart.getProduct_id()), tempCart);

        // 保存
        commit();
    }

    /**
     * 删除购物车数据
     *
     * @param shoppingCart
     */
    public void deleteData(GoodsBean shoppingCart) {
        mSparseArray.delete(Integer.parseInt(shoppingCart.getProduct_id()));

        commit();
    }

    /**
     * 修改购物车数据
     *
     * @param goodsBean
     */
    public void updateData(GoodsBean goodsBean) {

        mSparseArray.put(Integer.parseInt(goodsBean.getProduct_id()), goodsBean);

        commit();
    }

    /**
     * 提交数据的更改
     */
    private void commit() {
        List<GoodsBean> goodsBeen = sparseToList();
        String json = new Gson().toJson(goodsBeen);
        CacheUtils.putString(mContext, JSON_CART, json);
    }

    /**
     * 把List列表数据->json文本数据-->保存到本地
     *
     * @return
     */
    private List<GoodsBean> sparseToList() {

        List<GoodsBean> shoppingCarts = new ArrayList<>();

        for (int i = 0; i < mSparseArray.size(); i++) {
            GoodsBean shoppingCart = mSparseArray.valueAt(i);
            shoppingCarts.add(shoppingCart);
        }

        return shoppingCarts;
    }


}































