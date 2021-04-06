package com.dayi.dayiintegration;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2021/3/25 15:00
 * 描    述: 类
 * 修订历史:
 * =========================================
 */
public class APP extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

    }

    /**
     * 初始化ARouter相关
     */
    private void initARouter(){
        if (true){
            ARouter.openLog();// Print log
            ARouter.openDebug();//open debug
        }
        ARouter.init(this);
    }
}
