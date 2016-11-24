package com.wrk.myshoppingmall.utils;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

/**
 * Created by MrbigW on 2016/11/16.
 * weChat:1024057635
 * GitHub:MrbigW
 * Usage: -.-
 * -------------------=.=------------------------
 */

public class DrawUtils {

    /**
     * 提供一个指定颜色和圆角半径的drawable对象
     *
     * @param rgb
     * @param radius
     * @param strokenColor
     * @return
     */
    public static GradientDrawable getDrawable(int rgb, float radius, int strokenColor) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(rgb);
        gradientDrawable.setGradientType(GradientDrawable.RECTANGLE);
        gradientDrawable.setCornerRadius(radius);
        gradientDrawable.setStroke(UIUtils.dp2px(1), strokenColor);
        return gradientDrawable;
    }

    /**
     * 带选择器的图片
     * @param normalDrawable
     * @param pressDrawable
     * @return
     */
    public static StateListDrawable getSelector(Drawable normalDrawable, Drawable pressDrawable) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        //给当前的颜色选择器添加选中图片指向状态，未选中图片指向状态
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, pressDrawable);
//        stateListDrawable.addState(new int[]{android.R.attr.state_enabled}, normalDrawable);
        //设置默认状态
        stateListDrawable.addState(new int[]{}, normalDrawable);
        return stateListDrawable;
    }


}

























