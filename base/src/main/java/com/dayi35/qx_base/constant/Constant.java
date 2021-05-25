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

    public interface Host{
        //本地
        String HOST         =           "http://172.29.5.216:8081";
    }

    /**
     * KEY类
     */
    public interface KeyConstants{
        //九鼎
        String bombAppKey       =       "8281752079635ffb5d990087c5955837";
        //大易进度
//        String bombAppKey       =       "fbd00de1492cf29a59acd6342378017d";
    }

    public interface ValueConstants{
        //是否debug模式
        boolean isDebug = true;
        int PAGESIZE = 10;
        //header
        String COMMONHEADER = "Content-Type: application/x-www-form-urlencoded;charset=UTF-8";
        //网络缓存时间
        int TIME_CACHE  =  60*60;
    }

    /**
     * EventBus Code
     * 1-500        rate大易项目进度管理
     */
    public interface CodeEvent{
        //更新项目组列表
        int RATE_PROJECTTEAMUPDATE = 1;
        //更新组件列表
        int RATE_MODULEUPDATE = 2;
        //更新功能列表
        int RATE_COMPONENTUPDATE = 3;
        //更新进度
        int RATE_PROGRESSUPDATE = 4;
    }

    /**
     * SPUtils 的key
     */
    public interface KeySPUtils{
        //缓存项目实体
        String RATE_PROJECTRATE = "RATE_PROJECTRATE";
        //缓存OS数据
        String RATE_SP_OS = "rate_sp_os";
        //缓存团队数据
        String RATE_SP_TEAM = "rate_sp_team";
    }


}
