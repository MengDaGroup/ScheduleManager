package com.akee.versionmanager.base;


import com.akee.versionmanager.listener.OnDownloadListener;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2020/5/25 17:00
 * 描    述: 类
 * 修订历史:
 * =========================================
 */

public abstract class BaseHttpDownloadManager {

    /**
     * 下载apk
     *
     * @param apkUrl   apk下载地址
     * @param apkName  apk名字
     * @param listener 回调
     */
    public abstract void download(String apkUrl, String apkName, OnDownloadListener listener);

    /**
     * 取消下载apk
     */
    public abstract void cancel();
}
