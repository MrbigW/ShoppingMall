package com.wrk.myshoppingmall.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wrk.myshoppingmall.R;


/**
 * Created by MrbigW on 2016/10/27.
 * weChat:1024057635
 * Github:MrbigW
 * Usage: 自定义增加删除控件
 * -------------------=.=------------------------
 */

public class NumAddSubView extends LinearLayout implements View.OnClickListener {

    private Context mContext;

    private Button btn_sub;
    private TextView tv_value;
    private Button btn_add;

    // 默认值
    private int value = 1;
    // 最小值
    private int minValue = 1;
    // 最大值
    private int maxValue = 10;

    public NumAddSubView(Context context) {
        this(context, null);
    }

    public NumAddSubView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumAddSubView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;

        // 把布局R.layout.add_sub_num_view添加到NumAddSubView.this中
        View.inflate(context, R.layout.add_sub_num_view, NumAddSubView.this);

        btn_sub = (Button) findViewById(R.id.btn_sub);
        tv_value = (TextView) findViewById(R.id.tv_value);
        btn_add = (Button) findViewById(R.id.btn_add);


        btn_sub.setOnClickListener(this);
        btn_add.setOnClickListener(this);

        if (attrs != null) {
            TintTypedArray tintTypedArray = TintTypedArray.obtainStyledAttributes(context, attrs, R.styleable.NumAddSubView);

            int value = tintTypedArray.getInt(R.styleable.NumAddSubView_value, 0);
            if (value > 0) {
                setValue(value);
            }
            int minValue = tintTypedArray.getInt(R.styleable.NumAddSubView_minValue, 0);
            if (minValue > 0) {
                setMinValue(minValue);
            }
            int maxValue = tintTypedArray.getInt(R.styleable.NumAddSubView_maxValue, 0);
            if (maxValue > 0) {
                setMaxValue(maxValue);
            }
            Drawable num_bg = tintTypedArray.getDrawable(R.styleable.NumAddSubView_num_bg);
            if (num_bg != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    this.setBackground(num_bg);
                }
            }
            Drawable sub_bg = tintTypedArray.getDrawable(R.styleable.NumAddSubView_sub_bg);
            if (sub_bg != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    btn_sub.setBackground(sub_bg);
                }
            }
            Drawable add_bg = tintTypedArray.getDrawable(R.styleable.NumAddSubView_add_bg);
            if (add_bg != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    btn_add.setBackground(add_bg);
                }
            }

        }

    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        tv_value.setText(value + "");
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sub:

                subNumber();
                if (mOnClickListener != null) {
                    mOnClickListener.onButtonSubOnClick(v, value);
                }

                break;

            case R.id.btn_add:

                addNumber();
                if (mOnClickListener != null) {
                    mOnClickListener.onButtonAddOnClick(v, value);
                }

                break;

        }
    }

    /**
     * 减
     */
    private void subNumber() {
        if (value > minValue) {
            value -= 1;
        }
        setValue(value);
    }

    /**
     * 加
     */
    private void addNumber() {
        if (value < maxValue) {
            value += 1;
        }
        setValue(value);
    }

    private onButtonOnClickListener mOnClickListener;

    public void setButtonOnClickListener(onButtonOnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    public interface onButtonOnClickListener {
        /**
         * 当减的按钮被点击时回调
         *
         * @param view
         * @param value
         */
        void onButtonSubOnClick(View view, int value);

        /**
         * 当加的按钮被点击时回调
         *
         * @param view
         * @param value
         */
        void onButtonAddOnClick(View view, int value);

    }

}























































