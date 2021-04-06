package com.dayi35.qx_widget.pickers.entity;

/**
 *
 * Author:matt : addapp.cn
 * DateTime:2016-10-15 19:06
 *
 */
public abstract class ItemBean extends JavaBean {
    private String code;
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "code=" + code + ",name=" + name;
    }


    /************************BRCity*******************************/

//    private String name;
//    private String code;
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getCode() {
//        return code;
//    }
//
//    public void setCode(String code) {
//        this.code = code;
//    }
}