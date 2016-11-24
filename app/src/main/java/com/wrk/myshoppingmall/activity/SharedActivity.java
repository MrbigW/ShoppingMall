package com.wrk.myshoppingmall.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.wrk.myshoppingmall.R;
import com.wrk.myshoppingmall.common.BaseActivity;
import com.wrk.myshoppingmall.utils.Constants;
import com.wrk.myshoppingmall.utils.UIUtils;
import com.wrk.myshoppingmall.utils.zxing.encoding.EncodingUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;

public class SharedActivity extends BaseActivity {

    @BindView(R.id.iv_share)
    ImageView ivShare;
    @BindView(R.id.ibn_back)
    ImageButton ibnBack;
    private String figure;

    @Override
    protected void initData() {
        // 得到数据
        figure = getIntent().getStringExtra(Constants.GOODS_SHARE);

        UIUtils.getHandler().post(new Runnable() {
            @Override
            public void run() {
                create();
            }
        });

    }

    /**
     * 创建二维码并将图片保存在本地
     */
    private void create() {
        int width = UIUtils.dp2px(200);
        Bitmap bitmap = EncodingUtils.createQRCode(figure,
                width, width, BitmapFactory.decodeResource(getResources(),
                        R.drawable.notify_small_icon));

        ivShare.setImageBitmap(bitmap);

        saveBitmap(bitmap);
    }

    /**
     * 将Bitmap保存在本地
     *
     * @param bitmap
     */
    public void saveBitmap(Bitmap bitmap) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(),
                "zxing_image");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = "zxing_image" + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(this.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 通知图库更新
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.parse("file://" + "/sdcard/namecard/")));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shared;
    }


    @OnClick(R.id.ibn_back)
    public void onClick() {
        removeCurrentActivity();
    }
}
