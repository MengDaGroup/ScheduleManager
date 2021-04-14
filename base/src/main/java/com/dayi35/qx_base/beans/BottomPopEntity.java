package com.dayi35.qx_base.beans;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2020/12/8 10:40
 * 描    述: 类 底部弹窗数据实体类
 * 修订历史:
 * =========================================
 */
public class BottomPopEntity<T> {
    String columnName;              //栏目名
    int columnType;                 //栏目类型
    T data;                         //数据


    public BottomPopEntity(String columnName) {
        this.columnName = columnName;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public int getColumnType() {
        return columnType;
    }

    public void setColumnType(int columnType) {
        this.columnType = columnType;
    }
}
