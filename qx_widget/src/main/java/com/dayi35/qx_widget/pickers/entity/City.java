package com.dayi35.qx_widget.pickers.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 地市
 * <br/>
 * Author:matt : addapp.cn
 * DateTime:2016-10-15 19:07
 *
 */
public class City extends ItemBean {
    private String provinceId;
    private List<County> areaList = new ArrayList<County>();

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public List<County> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<County> areaList) {
        this.areaList = areaList;
    }

    /************************BRCity*******************************/

//    private List<County>areaList = new ArrayList<>();
//
//    public List<County> getAreaList() {
//        return areaList;
//    }
//
//    public void setAreaList(List<County> areaList) {
//        this.areaList = areaList;
//    }
}