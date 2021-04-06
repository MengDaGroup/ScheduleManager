package com.dayi35.qx_base;

import android.content.Context;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2020/11/16 16:48
 * 描    述: 类 基础类配置管理
 * 修订历史:
 * =========================================
 */
public class BaseManager {
    private Context mContext;                       //上下文
    private int mTimeCache;                         //网络缓存保存时间

    private BaseManager(Builder builder){
        this.mContext = builder.mContext;
        this.mTimeCache = builder.mTimeCache;
        BaseConfig.instance().mContext(mContext);
        BaseConfig.instance().mTimeCahe(mTimeCache);
    }

    public static class Builder{
        private Context mContext;
        private int mTimeCache;

        public Builder mTimeCache(int mTimeCache){
            this.mTimeCache = mTimeCache;
            return this;
        }

        public Builder(Context mContext){
            this.mContext = mContext;
        }

        public BaseManager build(){
            return new BaseManager(this);
        }

    }

}
