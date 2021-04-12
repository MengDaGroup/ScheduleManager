package com.dayi35.qx_base.beans;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2020/11/12 17:38
 * 描    述: 类 Eventbus实体
 * 修订历史:
 * =========================================
 */
public class EventBusEntity<T> {
    int eventCode;
    String eventMsg;
    T eventData;

    public EventBusEntity(int eventCode) {
        this.eventCode = eventCode;
    }

    public int getEventCode() {
        return eventCode;
    }

    public void setEventCode(int eventCode) {
        this.eventCode = eventCode;
    }

    public String getEventMsg() {
        return eventMsg;
    }

    public void setEventMsg(String eventMsg) {
        this.eventMsg = eventMsg;
    }

    public T getEventData() {
        return eventData;
    }

    public void setEventData(T eventData) {
        this.eventData = eventData;
    }

    @Override
    public String toString() {
        return "EventBusEntity{" +
                "eventCode='" + eventCode + '\'' +
                ", eventMsg='" + eventMsg + '\'' +
                ", eventData=" + eventData +
                '}';
    }
}
