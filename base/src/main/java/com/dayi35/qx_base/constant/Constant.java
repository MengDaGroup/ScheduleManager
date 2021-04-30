package com.dayi35.qx_base.constant;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2021/4/7 9:18
 * 描    述: 类 常量
 * 修订历史:
 * =========================================
 */
public class Constant {

    /**
     * KEY类
     */
    public interface KeyConstants{
        //九鼎
        String bombAppKey       =       "8281752079635ffb5d990087c5955837";
        //大易进度
//        String bombAppKey       =       "fbd00de1492cf29a59acd6342378017d";
    }

    /**
     * EventBus Code
     * 1-500        rate大易项目进度管理
     */
    public interface CodeEvent{
        //更新项目组列表
        int RATE_PROJECTTEAMUPDATE = 1;
    }

    /**
     * SPUtils 的key
     */
    public interface KeySPUtils{
        //缓存项目实体
        String RATE_PROJECTRATE = "RATE_PROJECTRATE";
    }


}
