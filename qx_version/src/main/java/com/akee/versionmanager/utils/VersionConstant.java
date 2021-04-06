package com.akee.versionmanager.utils;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2020/5/25 17:03
 * 描    述: 类 常量类
 * 修订历史:
 * =========================================
 */

public final class VersionConstant {
    /**
     * 网络连接超时时间
     */
    public static final int HTTP_TIME_OUT = 30_000;
    /**
     * Logcat日志输出Tag
     */
    public static final String TAG = "AppUpdate.";
    /**
     * 渠道通知id
     */
    public static final String DEFAULT_CHANNEL_ID = "appUpdate";
    /**
     * 渠道通知名称
     */
    public static final String DEFAULT_CHANNEL_NAME = "AppUpdate";
    /**
     * 新版本下载线程名称
     */
    public static final String THREAD_NAME = "app update thread";
    /**
     * apk文件后缀
     */
    public static final String APK_SUFFIX = ".apk";
    /**
     * 兼容Android N Uri 授权
     */
    public static String AUTHORITIES;
}
