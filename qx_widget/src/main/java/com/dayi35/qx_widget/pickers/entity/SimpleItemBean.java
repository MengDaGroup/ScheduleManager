package com.dayi35.qx_widget.pickers.entity;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2021/5/25 14:11
 * 描    述: 类
 * 修订历史:
 * =========================================
 */
public class SimpleItemBean {
    String label;
    String value;

    public SimpleItemBean() {
    }

    public SimpleItemBean(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
