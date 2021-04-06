package com.dayi35.qx_utils.common;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by LiuJC on 2019/10/16
 *
 * 状态栏工具类
 *
 * Android版本19(4.4)以后，可以设置半透明的状态栏，21(5.0)以后，可以设置全透明的状态栏以及状态栏背景颜色
 * Android版本23(6.0)以后或者19(4.4)以后的MIUIV、Flyme系统，可以设置状态栏图标模式（白色和深色）
 **/
public class StatusBarUtil {

    /**
     * 全屏（隐藏状态栏）
     * @param activity
     */
    public static void fullScreen(Activity activity){
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
    /**
     * 透明状态栏
     * @param activity
     */
    public static void transparent(Activity activity){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }
    /**
     * 状态栏半透明效果
     */
    public static void halfTransparent(Activity activity){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.KITKAT){
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
    /**
     * 代码设置 fitsSystemWindows
     * @param fitsSystemWindows
     */
    public static void setRootViewFitsSystemWindows(Activity activity,boolean fitsSystemWindows){
        ((ViewGroup)activity.findViewById(android.R.id.content)).getChildAt(0).setFitsSystemWindows(fitsSystemWindows);
    }

    /**
     * 获取状态栏高度
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     *
     * @param activity
     * @param isDarkMode  是否把状态栏字体及图标设置成深色
     * @param color 状态栏背景颜色
     */
    public static void setStatusBarColor(Activity activity,boolean isDarkMode,int color){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){      //6.0
//                activity.getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                if(isDarkMode){
                    activity.getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                }else {
                    activity.getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_VISIBLE);
                }
                activity.getWindow().setStatusBarColor(color);
            }else if(FlymeSetStatusBarLightMode(activity.getWindow(),isDarkMode)){   //Flyme用户
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
                    activity.getWindow().setStatusBarColor(color);
                }
            }else if(MIUISetStatusBarLightMode(activity.getWindow(),isDarkMode)){   //MIUI6用户
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
                    activity.getWindow().setStatusBarColor(color);
                }
            }else {
                //非6.0、Flyme、IUIV6用户，无法设置状态栏模式
            }
        }
    }

    /**
     * 设置深色的图标和字体
     * @param activity
     */
    public static void setDarkMode(Activity activity){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){      //6.0
//                activity.getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                activity.getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                activity.getWindow().setStatusBarColor(0xffffffff);
            }else if(FlymeSetStatusBarLightMode(activity.getWindow(),true)){   //Flyme用户

            }else if(MIUISetStatusBarLightMode(activity.getWindow(),true)){   //MIUI6用户

            }else {
                //非6.0、Flyme、IUIV6用户，无法设置状态栏模式
            }
        }
    }

    /**
     * 设置白色的图标和字体
     * @param activity
     */
    public static void setLightMode(Activity activity){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){      //6.0
//                activity.getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                activity.getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_VISIBLE);
                activity.getWindow().setStatusBarColor(0x99000000);
            }else if(FlymeSetStatusBarLightMode(activity.getWindow(),false)){   //Flyme用户

            }else if(MIUISetStatusBarLightMode(activity.getWindow(),false)){   //MIUI6用户

            }else {
                //非6.0、Flyme、IUIV6用户，无法设置状态栏模式
            }
        }
    }

    /**
     * 设置状态栏图标为深色和魅族特定的文字风格
     * 可以用来判断是否为Flyme用户
     * @param window 需要设置的窗口
     * @param dark 是否把状态栏字体及图标颜色设置为深色
     * @return  boolean 成功执行返回true
     *
     */
    private static boolean FlymeSetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 设置状态栏字体图标为深色，需要MIUIV6以上
     * @param window 需要设置的窗口
     * @param dark 是否把状态栏字体及图标颜色设置为深色
     * @return  boolean 成功执行返回true
     *
     */
    private static boolean MIUISetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field  field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if(dark){
                    extraFlagField.invoke(window,darkModeFlag,darkModeFlag);//状态栏透明且黑色字体
                }else{
                    extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                }
                result=true;

            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return result;
    }

}
