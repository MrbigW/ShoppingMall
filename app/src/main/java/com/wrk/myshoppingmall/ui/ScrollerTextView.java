package com.wrk.myshoppingmall.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by MrbigW on 2016/11/21.
 * weChat:1024057635
 * GitHub:MrbigW
 * Usage: -.-
 * -------------------=.=------------------------
 */

public class ScrollerTextView extends TextView {
    public ScrollerTextView(Context context) {
        this(context, null);
    }

    public ScrollerTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollerTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean isFocused() {
        return true;
    }
}



