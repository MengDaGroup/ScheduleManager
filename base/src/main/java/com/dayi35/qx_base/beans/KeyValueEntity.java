package com.dayi35.qx_base.beans;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2021/5/11 14:13
 * 描    述: 类 key-value实体
 * 修订历史:
 * =========================================
 */
public class KeyValueEntity {
    private String label;
    private String value;

    public KeyValueEntity() {

    }

    public KeyValueEntity(String key, String value) {
        this.label = key;
        this.value = value;
    }

    public String getKey() {
        return label;
    }

    public void setKey(String key) {
        this.label = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
