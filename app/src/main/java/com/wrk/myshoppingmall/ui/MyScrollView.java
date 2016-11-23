package com.wrk.myshoppingmall.ui;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

import com.wrk.myshoppingmall.utils.UIUtils;


/**
 * Created by MrbigW on 2016/11/14.
 * weChat:1024057635
 * GitHub:MrbigW
 * Usage: -.-
 * -------------------=.=------------------------
 */

public class MyScrollView extends ScrollView {

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //获取子视图
    private View childView;
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if(getChildCount() > 0){
            childView = getChildAt(0);
        }

    }

    private int lastY;//记录上一次y坐标的位置
    private Rect normal = new Rect();//用于记录临界状态时的left,top,right,bottom的坐标
    private boolean isFinishAnimation = true;//判断是否结束了动画

    private int lastX;//记录上一次x坐标的位置
    private int downX,downY;//记录down事件时，x轴和y轴的坐标位置


    /**
     * @param ev
     * @return 如果返回值为true，表示拦截子视图的处理。如果返回false，表示不拦截
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean isIntercept = false;
        int eventX = (int) ev.getX();
        int eventY = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN :
                downX = lastX = eventX;
                downY = lastY = eventY;
                break;
            case MotionEvent.ACTION_MOVE:
                //记录水平方向和垂直方向移动的距离
                int totalX = Math.abs(eventX - downX);
                int totalY = Math.abs(eventY - downY);

                if(totalX < totalY && totalY > UIUtils.dp2px(10)){
                    isIntercept = true;
                }

                lastX = eventX;
                lastY = eventY;
                break;
        }

        return isIntercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(childView == null || !isFinishAnimation){
            return super.onTouchEvent(ev);
        }

        //获取当前y轴方向的坐标(相较于当前视图)
        int eventY = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN :
                lastY = eventY;
                break;
            case MotionEvent.ACTION_MOVE:

                //获取移动量
                int dy = eventY - lastY;

                if(isNeedMove()){
                    if(normal.isEmpty()){//如果没有记录过left,top,right,bottom，返回true.
                        //记录临界状态的left,top,right,bottom
                        normal.set(childView.getLeft(),childView.getTop(),childView.getRight(),childView.getBottom());
                    }
                    //给视图重新布局
                    childView.layout(childView.getLeft(),childView.getTop() + dy / 2,childView.getRight(),childView.getBottom() + dy / 2);
                }

                //重新赋值
                lastY = eventY;
                break;
            case MotionEvent.ACTION_UP:
                if(isNeedAnimation()){

                    TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, 0, normal.bottom - childView.getBottom());
                    translateAnimation.setDuration(200);
//                    translateAnimation.setFillAfter(true);
                    childView.startAnimation(translateAnimation);

                    //设置动画的监听
                    translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            isFinishAnimation = false;
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            isFinishAnimation = true;
                            childView.clearAnimation();//清除动画
                            childView.layout(normal.left, normal.top, normal.right,normal.bottom);//重新布局
                            normal.setEmpty();//清空normal中left、top、right、bottom数据
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                }

                break;
        }




        return super.onTouchEvent(ev);
    }

    /**
     * 是否需要使用动画
     * @return
     */
    private boolean isNeedAnimation() {
        return !normal.isEmpty();
    }

    /**
     * 判断是否需要按照我们自定义的方式重新布局
     * @return
     */
    private boolean isNeedMove() {
        int measuredHeight = childView.getMeasuredHeight();//获取子视图测量的高度
        int height = this.getHeight();//得到屏幕的高度
        Log.e("TAG", "measuredHeight = " + measuredHeight + ",height = " + height);

        int dy = measuredHeight - height;//获取二者的距离差

        int scrollY = this.getScrollY();//获取当前视图在y轴方向上移动的位移 (最初：0.上移：+，下移：-)
//        Log.e("TAG", "scrollY = " + scrollY);
        if(scrollY <= 0 || scrollY >= dy){
            return true;
        }
        return false;
    }
}


















































