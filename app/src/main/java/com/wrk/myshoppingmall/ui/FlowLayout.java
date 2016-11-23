package com.wrk.myshoppingmall.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MrbigW on 2016/11/16.
 * weChat:1024057635
 * GitHub:MrbigW
 * Usage: 流式布局
 * -------------------=.=------------------------
 */

public class FlowLayout extends ViewGroup {


    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    // 测量
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec,heightMeasureSpec);

        // 获取该ViewGroup在布局文件中的宽高及各自的设置模式（精确模式，或者至多模式）
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        // 当前视图的宽高，如果是至多模式，需要计算出此两个变量值的值
        int width = 0;
        int height = 0;

        // 声明每行的宽度和高度
        int lineWidth = 0;
        int lineHeighet = 0;

        int childCount = getChildCount(); // 获取子视图的个数

        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i); // 获取子视图
            // 为了保证能获取子视图测量的宽高，必须调用如下的方法(通知父布局去测量子View)
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
            // 获取子视图的宽高
            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();
            // 获取子视图测量的margin值
            MarginLayoutParams mp = (MarginLayoutParams) childView.getLayoutParams();

            // 判断当前行的宽度加上要放入的childView的宽度及其左右margin值与至多模式的宽度值
            if (lineWidth + childWidth + mp.leftMargin + mp.rightMargin <= widthSize) { // 不换行
                // 更新行宽
                lineWidth += childWidth + mp.leftMargin + mp.rightMargin;
                // 更新行高
                lineHeighet = Math.max(lineHeighet, childHeight + mp.topMargin + mp.bottomMargin);
            } else { // 换行

                // 换行就要更新总的宽度和高度
                // 更新总宽度
                width = Math.max(width, lineWidth);
                // 更行总高度
                height += lineHeighet;

                // 更新新行宽
                lineWidth = childWidth + mp.leftMargin + mp.rightMargin;
                // 更新新行高
                lineHeighet = childHeight + mp.topMargin + mp.bottomMargin;

            }

            // 最后一行！！！
            if (i == childCount - 1) {
                // 更新总宽度
                width = Math.max(width, lineWidth);
                // 更行总高度
                height += lineHeighet;
            }

        }

        // 设置当前布局的宽高
        setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? widthSize : width,
                heightMode == MeasureSpec.EXACTLY ? heightSize : height);
    }


    private List<Integer> allHeights = new ArrayList<>(); // 集合里是每一行高度
    private List<List<View>> allViews = new ArrayList<>(); // 集合里是每一行的childView的集合

    // 布局:给每一个子View布局:childView.layout(l,t,r,b);
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int width = getWidth(); // 得到父视图的宽度
        int lineWidth = 0; // 每一行的宽
        int lineHeight = 0; // 每一行的高

        // 1.给集合元素赋值
        int childCount = getChildCount();
        List<View> lineList = new ArrayList<>(); // 保存这一行的childView
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            // 子视图的宽高
            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();
            // 子视图的边距
            MarginLayoutParams mp = (MarginLayoutParams) childView.getLayoutParams();

            if (lineWidth + childWidth + mp.leftMargin + mp.rightMargin <= width) { // 不换行
                lineWidth += childWidth + mp.leftMargin + mp.rightMargin;
                lineHeight = Math.max(lineHeight, childHeight + mp.bottomMargin + mp.topMargin);

                lineList.add(childView); // 添加子视图到集合中

            } else { // 换行
                // 将上面满childView的一行的childView集合加入总view集合
                allViews.add(lineList);
                // 将上面满childView的一行的高度加入总高度集合
                allHeights.add(lineHeight);

                // 置空此行的childView的集合
                lineList = new ArrayList<>();
                // 将换行后的新的childView加入集合
                lineList.add(childView);

                // 重新对新的一行的宽高赋值
                lineWidth = childWidth + mp.leftMargin + mp.rightMargin;
                lineHeight = childHeight + mp.topMargin + mp.bottomMargin;
            }

            // 最后一行
            if (i == childCount - 1) {
                allHeights.add(lineHeight);
                allViews.add(lineList);
            }

        }

        // 2.遍历集合元素，调用元素的layout()方法

        int x = 0;
        int y = 0;

        for (int i = 0; i < allViews.size(); i++) {
            List<View> lineViews = allViews.get(i);
            for (int j = 0; j < lineViews.size(); j++) {
                View childView = lineViews.get(j);
                MarginLayoutParams mp = (MarginLayoutParams) childView.getLayoutParams();
                // 计算得到left,right,top,bottom
                int left = mp.leftMargin + x;
                int top = mp.topMargin + y;
                int right = left + childView.getMeasuredWidth();
                int bottom = top + childView.getMeasuredHeight();

                childView.layout(left, top, right, bottom);
                // 对x重新赋值
                x += childView.getMeasuredWidth() + mp.leftMargin + mp.rightMargin;
            }

            // 换行以后
            x = 0;
            y += allHeights.get(i);
        }

    }

    //FlowLayout中重写如下的方法，在onMeasure()中可通过childView就可以getLayoutParams()
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        MarginLayoutParams mp = new MarginLayoutParams(getContext(), attrs);
        return mp;
    }

}










