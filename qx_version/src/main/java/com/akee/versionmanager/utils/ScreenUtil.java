package com.akee.versionmanager.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2020/5/25 16:09
 * 描    述: 类 屏幕信息获取
 * 修订历史:
 * =========================================
 */

public final class ScreenUtil {

    /**
     * 获取屏幕宽度（像素）
     *
     * @param context 上下文
     * @return px
     */
    public static int getWith(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 获取屏幕高度（像素）
     *
     * @param context 上下文
     * @return px
     */
    public static int getHeight(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    /**
     * 包含底部导航栏高度
     *
     * @param context
     * @return
     */
    public static int getHeightContainNavBar(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
//        wm.getDefaultDisplay().getMetrics(outMetrics);//2131   note7
        wm.getDefaultDisplay().getRealMetrics(outMetrics);//2340  note7   包括导航栏
        return outMetrics.heightPixels;
    }
}
