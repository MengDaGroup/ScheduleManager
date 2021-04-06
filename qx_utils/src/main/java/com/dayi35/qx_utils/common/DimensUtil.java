package com.dayi35.qx_utils.common;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.dayi35.qx_utils.androidcodeutils.Utils;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2020/11/30 9:26
 * 描    述: 类 屏幕尺寸工具类
 * 修订历史:
 * =========================================
 */
public class DimensUtil {

    /**
     * 获取屏幕的宽
     * @return
     */
    public static int getScreenWidth(){
        int width = 1080;
        WindowManager wm = (WindowManager) Utils.getApp().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getRealMetrics(outMetrics);//2340  note7   包括导航栏
        width = outMetrics.widthPixels;
        return width;
    }

    /**
     * 获取屏幕的高
     * @return
     */
    public static int getScreenHeight(){
        int height = 1920;
        WindowManager wm = (WindowManager) Utils.getApp().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getRealMetrics(outMetrics);//2340  note7   包括导航栏
        height = outMetrics.heightPixels;
        return height;
    }
}
