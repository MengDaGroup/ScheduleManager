package com.dayi35.qx_base;

import android.content.Context;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2020/11/16 14:46
 * 描    述: 类 基础lib的配置类
 * 修订历史:
 * =========================================
 */
public class BaseConfig {
    private Context mContext;                                       //上下文
    private int mTimeCache;                                         //网络缓存保存时间

    public BaseConfig mContext(Context mContext){
        this.mContext = mContext;
        return this;
    }

    public BaseConfig mTimeCahe(int mTimeCache){
        this.mTimeCache = mTimeCache;
        return this;
    }

    public Context getmContext() {
        return mContext;
    }

    public int getmTimeCache() {
        return mTimeCache;
    }

    private static class InstanceHolder {

        private static final BaseConfig sInstance = new BaseConfig();

    }

    public static BaseConfig instance() {

        return BaseConfig.InstanceHolder.sInstance;
    }

}
