package com.icebroken.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.widget.Button;
import android.widget.TextView;

import com.mocuz.common.baseapp.BaseApplication;
import com.yuyh.library.imgsel.utils.FileUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.icebroken.R;

/**
 * Created by wwy on 2017/3/15.
 */

public class WwyUtil {


    //设置button样式1
    public static void setButtonStyle1(Button button) {
        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(12);
//        gd.setColor(Color.parseColor(BaseUtil.getEndColor()));
        button.setBackgroundDrawable(gd);
    }

    //设置button样式1 不能点击
    public static void setButtonStyleDisable(Button button) {
        GradientDrawable gd = new GradientDrawable();
//        gd.setCornerRadius(12);
        gd.setColor(ContextCompat.getColor(BaseApplication.getAppContext(), R.color.d3d3d3));
        button.setBackgroundDrawable(gd);
    }

    //设置button样式1 能点击
    public static void setButtonStyleEnable(Button button) {
        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(12);
        gd.setColor(Color.parseColor("#3676fc"));
        button.setBackgroundDrawable(gd);
    }

    //设置button样式1 能点击
    public static void setButtonStyleEnable(TextView button) {
        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(12);
        gd.setColor(Color.parseColor("#3676fc"));
        button.setBackgroundDrawable(gd);
    }

    //设置button样式2
    public static void setButtonStyle2(Button button) {
        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(12);
        gd.setColor(ContextCompat.getColor(BaseApplication.getAppContext(), R.color.white));
        gd.setShape(GradientDrawable.RECTANGLE); // 画框
//        gd.setStroke(1, Color.parseColor(BaseUtil.getEndColor()));
//        button.setTextColor(Color.parseColor(BaseUtil.getEndColor()));
        button.setBackgroundDrawable(gd);
    }

    public static boolean isEmpty(String s) {
        if (s == null) {
            return true;
        }
        if (s.equals("")) {
            return true;
        }
        return false;
    }

    public static File saveBitmapFile(Bitmap bitmap, Context context) {
        File file = new File(FileUtils.createRootPath(context) + "/" + System.currentTimeMillis() + ".jpg");//将要保存图片的路径
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
