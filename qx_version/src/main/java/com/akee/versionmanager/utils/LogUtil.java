package com.akee.versionmanager.utils;

import android.util.Log;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2020/5/25 16:23
 * 描    述: 类 日志工具类
 * 修订历史:
 * =========================================
 */

public final class LogUtil {

    /**
     * 日志开关
     */
    private static boolean b = true;

    public static void enable(boolean enable) {
        b = enable;
    }

    /**
     * 输出Error信息
     *
     * @param tag tag
     * @param msg String
     */
    public static void e(String tag, String msg) {
        if (b) {
            Log.e(tag, msg);
        }
    }

    /**
     * 输出Error信息
     *
     * @param tag tag
     * @param msg int
     */
    public static void e(String tag, int msg) {
        if (b) {
            Log.e(tag, String.valueOf(msg));
        }
    }

    /**
     * 输出Error信息
     *
     * @param tag tag
     * @param msg float
     */
    public static void e(String tag, float msg) {
        if (b) {
            Log.e(tag, String.valueOf(msg));
        }
    }

    /**
     * 输出Error信息
     *
     * @param tag tag
     * @param msg Long
     */
    public static void e(String tag, Long msg) {
        if (b) {
            Log.e(tag, String.valueOf(msg));
        }
    }

    /**
     * 输出Error信息
     *
     * @param tag tag
     * @param msg double
     */
    public static void e(String tag, double msg) {
        if (b) {
            Log.e(tag, String.valueOf(msg));
        }
    }

    /**
     * 输出Error信息
     *
     * @param tag tag
     * @param msg boolean
     */
    public static void e(String tag, boolean msg) {
        if (b) {
            Log.e(tag, String.valueOf(msg));
        }
    }

    /**
     * 输出Debug信息
     *
     * @param tag tag
     * @param msg String
     */
    public static void d(String tag, String msg) {
        if (b) {
            Log.d(tag, msg);
        }
    }

    /**
     * 输出Info信息
     *
     * @param tag tag
     * @param msg 字符串
     */
    public static void i(String tag, String msg) {
        if (b) {
            Log.i(tag, msg);
        }
    }
}
