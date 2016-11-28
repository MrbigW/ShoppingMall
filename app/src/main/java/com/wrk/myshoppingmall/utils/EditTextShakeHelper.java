package com.wrk.myshoppingmall.utils;

import android.app.Service;
import android.content.Context;
import android.os.Vibrator;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;

/**
 * Created by MrbigW on 2016/11/1.
 * weChat:1024057635
 * Github:MrbigW
 * Usage: 输入框的抖动
 * -------------------=.=------------------------
 */

public class EditTextShakeHelper {

    private Animation shakeAnimation;

    private CycleInterpolator cycleInterpolator;

    private Vibrator shakeVibrator;

    public EditTextShakeHelper(Context context) {
        shakeVibrator = (Vibrator) context
                .getSystemService(Service.VIBRATOR_SERVICE);
        shakeAnimation = new TranslateAnimation(0, 10, 0, 0);
        shakeAnimation.setDuration(300);
        cycleInterpolator = new CycleInterpolator(8);
        shakeAnimation.setInterpolator(cycleInterpolator);

    }

    public void shake(EditText... editTexts) {
        for (EditText editText : editTexts) {
            editText.startAnimation(shakeAnimation);
        }
        shakeVibrator.vibrate(new long[]{0, 500}, -1);
    }
}
