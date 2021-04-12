package com.dayi35.qx_base.utils;

import com.dayi35.qx_base.beans.EventBusEntity;

import org.greenrobot.eventbus.EventBus;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2021/3/2 19:17
 * 描    述: 类        EventBus管理
 * 修订历史:
 * =========================================
 */
public class EventBusHelper {

    /**
     * 判断对象是否已经注册
     * @param obj   需要判断的对象
     * @return
     */
    public static boolean isObjectRegisted(Object obj){
        return EventBus.getDefault().isRegistered(obj);
    }

    /**
     * eventbus简单注册对象
     * @param obj
     */
    public static void register(Object obj){
        if (!isObjectRegisted(obj)) {
            EventBus.getDefault().register(obj);
        }
    }

    /**
     * eventbus释放对象
     * @param obj
     */
    public static void unRegister(Object obj){
        if (isObjectRegisted(obj)) {
            EventBus.getDefault().unregister(obj);
        }
    }

    /**
     * postCode（简单更新状态）
     * @param code
     */
    public static void onSendCode(int code){
        EventBus.getDefault().post(new EventBusEntity(code));
    }

    /**
     * postentity实体，data为string
     * @param entity
     */
    public static void onSendStringEntity(EventBusEntity<String> entity){
        EventBus.getDefault().post(entity);
    }

}
