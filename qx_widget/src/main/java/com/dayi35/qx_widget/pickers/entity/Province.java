package com.dayi35.qx_widget.pickers.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 省份
 * <br/>
 * Author:matt : addapp.cn
 * DateTime:2016-10-15 19:06
 *
 */
public class Province extends ItemBean {
    private List<City> cityList = new ArrayList<City>();

    public List<City> getCityList() {
        return cityList;
    }

    public void setCityList(List<City> cityList) {
        this.cityList = cityList;
    }


    /************************BRCity*******************************/

//    private List<City> cityList = new ArrayList<City>();
//
//    public List<City> getCityList() {
//        return cityList;
//    }
//
//    public void setCityList(List<City> cityList) {
//        this.cityList = cityList;
//    }
}