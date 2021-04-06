package com.dayi35.qx_utils.common;

import android.graphics.Typeface;

import com.dayi35.qx_utils.androidcodeutils.Utils;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2020/11/10 10:50
 * 描    述: 类 字体工具
 * 修订历史:
 * =========================================
 */
public class TypefaceUtil {
    public static String TYPE_NUMBER                       =         "NumberRegular";
    public static String TYPE_NUMBERBLOAD                  =         "NumberMedium";

    /**
     * 字体样式获取
     * @param type
     * @return
     */
    public static Typeface getTypeface(String type){
        Typeface typeface = null;
        if (TYPE_NUMBER.equals(type)){
            typeface = Typeface.createFromAsset(Utils.getApp().getAssets(),"Barlow-Regular.ttf");
        }else if (TYPE_NUMBERBLOAD.equals(type)){
            typeface = Typeface.createFromAsset(Utils.getApp().getAssets(),"Barlow-Medium.ttf");
        }else {
            typeface = Typeface.createFromAsset(Utils.getApp().getAssets(),"Barlow-Regular.ttf");
        }

        return typeface;
    }


}
