package com.dayi.dy_rate.utils;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.dayi.dy_rate.R;
import com.dayi35.qx_utils.common.ToastUtils;
import com.dayi35.qx_widget.pickers.common.LineConfig;
import com.dayi35.qx_widget.pickers.picker.DatePicker;
import com.dayi35.qx_widget.pickers.picker.DateTimePicker;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2021/5/21 15:13
 * 描    述: 类    时间选择器业务化工具类
 * 修订历史:
 * =========================================
 */
public class DatePickerHelper {
    private int startYear;
    private int startMonth;
    private int startDay;
    private int endYear;
    private int endMonth;
    private int endDay;
    private int type;       //1.开始  2.结束
    private String title;
    private OnPickerSelected callback;

    public DatePickerHelper startYear(int startYear){
        this.startYear = startYear;
        return this;
    }

    public DatePickerHelper startMonth(int startMonth){
        this.startMonth = startMonth;
        return this;
    }

    public DatePickerHelper startDay(int startDay){
        this.startDay = startDay;
        return this;
    }

    public DatePickerHelper endYear(int endYear){
        this.endYear = endYear;
        return this;
    }

    public DatePickerHelper endMonth(int endMonth){
        this.endMonth = endMonth;
        return this;
    }

    public DatePickerHelper endDay(int endDay){
        this.endDay = endDay;
        return this;
    }

    public DatePickerHelper type(int type){
        this.type = type;
        return this;
    }

    public DatePickerHelper title(String title){
        this.title = title;
        return this;
    }

    public DatePickerHelper callback(OnPickerSelected callback){
        this.callback = callback;
        return this;
    }

    public DatePicker init(Activity activity){
        if (allDataSet()) {
            DatePicker picker = new DatePicker(activity);
            picker.setActionButtonTop(true);
            picker.setRangeStart(startYear, startMonth, startDay);
            picker.setRangeEnd(endYear, endMonth, endDay);
            picker.setCanLinkage(true);
            picker.setTitleText(title);
            picker.setWeightEnable(true);
            picker.setWheelModeEnable(true);
            picker.setOffset(1);
            picker.setSelectedTextColor(activity.getResources().getColor(R.color.widget_color_a5dc86));
            LineConfig config = new LineConfig();
            config.setAlpha(120);//线透明度
            config.setVisible(false);//线不显示 默认显示
            picker.setLineConfig(config);
            picker.setLabel("年", "月", "日");
            picker.setOnDateTimePickListener((DateTimePicker.OnYearMonthDayTimePickListener) (year, month, day, hour, minute) -> callback.onSelected(type, year + "-" + month + "-" + day));
            return picker;
        }
        return null;
    }

    /**
     * 判断数据是否都设置完成，并弹出对应的提示
     * @return
     */
    private boolean allDataSet(){
        if (0 == startYear){
            ToastUtils.showShort("未设置开始年");
            return false;
        }else if (0 == startMonth){
            ToastUtils.showShort("未设置开始月");
            return false;
        }else if (0 == startDay){
            ToastUtils.showShort("未设置开始日");
            return false;
        }else if (0 == endYear){
            ToastUtils.showShort("未设置结束年");
            return false;
        }else if (0 == endMonth){
            ToastUtils.showShort("未设置结束月");
            return false;
        }else if (0 == endDay){
            ToastUtils.showShort("未设置结束日");
            return false;
        }else if (0 == type){
            ToastUtils.showShort("未设置选择器类型");
            return false;
        }else if (TextUtils.isEmpty(title)){
            ToastUtils.showShort("未设置弹窗标题");
            return false;
        }else if (null == callback){
            ToastUtils.showShort("回调未设置");
            return false;
        }
        return true;
    }

    public interface OnPickerSelected{
        void onSelected(int type, String date);
    }
}
