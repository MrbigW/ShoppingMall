package com.wrk.myshoppingmall.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

/**
 * Created by MrbigW on 2016/11/18.
 * weChat:1024057635
 * GitHub:MrbigW
 * Usage: -.-
 * -------------------=.=------------------------
 */

public class BitmapUtils {

    /**
     * 将图片转换为圆形图片
     *
     * @param source
     * @return
     */
    public static Bitmap circleBitmap(Bitmap source) {
        // 获取bitmap对象的宽度
        int width = source.getWidth();
        // 返回一个正方形的Bitmap
        Bitmap bitmap = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888);
        // 提供指定宽高的canvas
        Canvas canvas = new Canvas(bitmap);
        // 提供画笔
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        // 在画布上绘制一个圆
        canvas.drawCircle(width / 2, width / 2, width / 2, paint);

        //设置图片相交情况下的处理方式
        //setXfermode：设置当绘制的图像出现相交情况时候的处理方式的,它包含的常用模式有哪几种
        //PorterDuff.Mode.SRC_IN 取两层图像交集部门,只显示上层图像,注意这里是指取相交叉的部分,然后显示上层图像
        //PorterDuff.Mode.DST_IN 取两层图像交集部门,只显示下层图像
        // 显示重叠部分的上部分
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        // 在画布上绘制一个bitmap
        canvas.drawBitmap(source, 0, 0, paint);

        return bitmap;
    }

    /**
     * 压缩图片
     *
     * @param source
     * @param width
     * @param height
     * @return
     */
    public static Bitmap zoom(Bitmap source, float width, float height) {

        Matrix matrix = new Matrix();

        float scaleW = width / source.getWidth();
        float scaleH = height / source.getHeight();

        matrix.postScale(scaleW, scaleH);

        Bitmap bm = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);

        return bm;
    }


}
