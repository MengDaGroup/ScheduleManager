package com.dayi35.qx_utils.convert;

import java.text.DecimalFormat;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2021/5/19 14:17
 * 描    述: 类    处理Float数据
 * 修订历史:
 * =========================================
 */
public class FloatUtils {

    /**
     * float 数据保留小数点后两位
     * @param data
     * @return
     */
    public static String str2point(float data){
        //构造方法的字符格式这里如果小数不足2位,会以0补足
        DecimalFormat decimalFormat = new DecimalFormat(".00");
        //format 返回的是字符串
        String p = decimalFormat.format(data);
        return p;
    }

    /**
     * floa 保留小数点后两位
     * @param data
     * @return
     */
    public static Float float2Point(float data){
        float result = (float)(Math.round(data*100))/100;
        return result;
    }


}
