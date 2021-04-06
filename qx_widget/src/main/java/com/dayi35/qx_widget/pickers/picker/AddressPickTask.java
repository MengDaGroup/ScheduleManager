package com.dayi35.qx_widget.pickers.picker;

import android.app.Activity;
import android.os.AsyncTask;

import com.alibaba.fastjson.JSON;
import com.dayi35.qx_utils.convert.DensityUtil;
import com.dayi35.qx_widget.R;
import com.dayi35.qx_widget.pickers.entity.City;
import com.dayi35.qx_widget.pickers.entity.County;
import com.dayi35.qx_widget.pickers.entity.Province;
import com.dayi35.qx_widget.pickers.listeners.OnLinkageListener;
import com.dayi35.qx_widget.pickers.util.ConvertUtils;

import java.util.ArrayList;


/**
 * 获取地址数据并显示地址选择器
 *
 * @author matt
 *         blog: addapp.cn
 */
public class AddressPickTask extends AsyncTask<String, Void, ArrayList<Province>> {
    private Activity activity;
    private Callback callback;
    private String selectedProvince = "", selectedCity = "", selectedCounty = "";
    private boolean hideProvince = false;
    private boolean hideCounty = false;
    private AddressPicker mPicker;

    public AddressPickTask(Activity activity) {
        this.activity = activity;
    }

    public void setHideProvince(boolean hideProvince) {
        this.hideProvince = hideProvince;
    }

    public void setHideCounty(boolean hideCounty) {
        this.hideCounty = hideCounty;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected ArrayList<Province> doInBackground(String... params) {
        if (params != null) {
            switch (params.length) {
                case 1:
                    selectedProvince = params[0];
                    break;
                case 2:
                    selectedProvince = params[0];
                    selectedCity = params[1];
                    break;
                case 3:
                    selectedProvince = params[0];
                    selectedCity = params[1];
                    selectedCounty = params[2];
                    break;
                default:
                    break;
            }
        }
        ArrayList<Province> data = new ArrayList<>();
        try {
            /**
             * 一个独立的三级联动json文件
             */
            String json = ConvertUtils.toString(activity.getAssets().open("BRCity.json"));
            data.addAll(JSON.parseArray(json, Province.class));

            /**
             * 三级分类
             */
//            String provinces = ConvertUtils.toString(activity.getAssets().open("provinces.json"));
//            String cities = ConvertUtils.toString(activity.getAssets().open("cityMap.json"));
//            String areas = ConvertUtils.toString(activity.getAssets().open("areaMap.json"));
//
//            List<County> countyList =  JSON.parseArray(areas, County.class);
//
//            List<City> cityList = JSON.parseArray(cities , City.class);
//
//            List<Province> provinceList = JSON.parseArray(provinces , Province.class);
//
//            for (Province p:provinceList){
//                String pid = p.getCode();
//                List<City> pcList = p.getCityList();
//                if(null==pcList) pcList = new ArrayList<>();   //省下面的市
//
//                for (City c: cityList){
//                    String cityId = c.getCode();
//                    List<County> cityCountyList = c.getAreaList();
//                    if(null==cityCountyList) cityCountyList = new ArrayList<>();  //市下面的县
//
//                    for (County county:countyList){
//                        if(county.getCode().startsWith(cityId)){
//                            cityCountyList.add(county);
//                        }
//                    }
//
//                    if(null==pcList) pcList = new ArrayList<>();
//                    if(cityId.startsWith(pid)){
//                        pcList.add(c);
//                    }
//                }
//            }
//            data.addAll(provinceList);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    protected void onPostExecute(ArrayList<Province> result) {
        if (result.size() > 0) {
            mPicker = new AddressPicker(activity, result);
            mPicker.setAnimationStyle(R.style.widget_translate_pop_animation_from_bottom_out_bottom);
            mPicker.setBackGroundResource(R.drawable.widget_shape_solid_ffffff_r8);
            mPicker.setCanLoop(false);
            mPicker.setSubmitTextColor(activity.getResources().getColor(R.color.widget_color_dbb76c));
            mPicker.setCancelTextColor(activity.getResources().getColor(R.color.widget_color_888888));
            mPicker.setHideProvince(hideProvince);
            mPicker.setTextSize((int) DensityUtil.px2sp(activity,activity.getResources().getDimension(R.dimen.widget_sp_14)));
            mPicker.setSelectedTextColor(activity.getResources().getColor(R.color.widget_color_dbb76c));
            mPicker.setLineColor(activity.getResources().getColor(R.color.widget_color_f0f0f0));
            mPicker.setHideCounty(hideCounty);
            mPicker.setWheelModeEnable(false);
            mPicker.setSubmitTextSize((int)DensityUtil.px2sp(activity,activity.getResources().getDimension(R.dimen.widget_sp_15)));
            mPicker.setCancelTextSize((int)DensityUtil.px2sp(activity,activity.getResources().getDimension(R.dimen.widget_sp_15)));
            if (hideCounty) {
                mPicker.setColumnWeight(1 / 3.0f, 2 / 3.0f);//将屏幕分为3份，省级和地级的比例为1:2
            } else {
                mPicker.setColumnWeight(5 / 8.0f, 5 / 8.0f, 5 / 8.0f);//省级、地级和县级的比例为2:3:3
            }
            mPicker.setSelectedItem(selectedProvince, selectedCity, selectedCounty);
            mPicker.setOnLinkageListener(callback);
            callback.onAddressInitSuccess(mPicker.getSelectedProvince(), mPicker.getSelectedCity(), mPicker.getSelectedCounty());
        } else {
            callback.onAddressInitFailed();
        }
    }

    public void show(){
        mPicker.show();
    }

    public interface Callback extends OnLinkageListener {

        void onAddressInitFailed();

        void onAddressInitSuccess(Province province, City city, County county);

    }

}
