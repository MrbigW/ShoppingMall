package com.wrk.myshoppingmall.ui;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.wrk.myshoppingmall.R;
import com.wrk.myshoppingmall.utils.UIUtils;


/**
 * Created by MrbigW on 2016/10/24.
 * weChat:1024057635
 * Github:MrbigW
 * Usage: 自定义PopUpWindow
 * -------------------=.=------------------------
 */

public class MorePopUpWindow extends PopupWindow {

    private Context mContext;

    private LayoutInflater mInflater;

    private View mContentView;


    public MorePopUpWindow(Context context) {
        super(context);

        this.mContext = context;

        // 渲染布局
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContentView = mInflater.inflate(R.layout.more_popup_layout, null);

        // 设置View
        setContentView(mContentView);

        // 设置宽高
        setWidth(UIUtils.dp2px(180));
        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);


        /**
         * 设置这个背景才能点击外边和back消失
         */
        setBackgroundDrawable(new ColorDrawable());

        // 设置获取焦点
        setFocusable(true);

        // 设置点击外边可以消失
        setOutsideTouchable(true);

        // 设置可触摸
        setTouchable(true);

        // 设置点击外部消失
        setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                // 判断是否点击了外部
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    return true;
                }

                return false;
            }
        });


    }

}






























