package com.dayi35.qx_utils.convert;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;

import com.dayi35.qx_utils.androidcodeutils.Utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2021/1/12 9:11
 * 描    述: 类 common_lang3字符处理的工具类
 * 1.从右开始截取字符串     2.从左开始截取字符串     3.从字符串的第几位开始截取，截取多少位
 * 4.截取字符串指定字符的前段      5.截取字符串指定字符的后段
 * 修订历史: java类
 * 1.新增 checkStr2Number 判断字符串是否可以转成int
 * =========================================
 */
public class ConvertStringUtil {

    /**
     * 从右开始截取字符串
     *
     * @param strData 需要截取的资源
     * @param num     截取多少位
     * @return
     */
    public static String rightNum(String strData, int num) {
        return TextUtils.isEmpty(strData) ? "" : StringUtils.right(strData, num);
    }

    /**
     * 从左开始截取字符串
     *
     * @param strData 需要截取的字符串资源
     * @param num     要截取的位数
     * @return
     */
    public static String leftNum(String strData, int num) {
        return TextUtils.isEmpty(strData) ? "" : StringUtils.left(strData, num);
    }

    /**
     * 从字符串的第几位开始截取，截取多少位
     *
     * @param strData 需要截取的字符串资源
     * @param frome   从第几位开始截取
     * @param num     截取多少位
     * @return
     */
    public static String midNum(String strData, int frome, int num) {
        return TextUtils.isEmpty(strData) ? "" : StringUtils.mid(strData, frome, num);
    }

    /**
     * 截取字符串指定字符的前段
     *
     * @param strData 需要截取的资源
     * @param sub     指定的字符串
     * @return
     */
    public static String subStringBefore(String strData, String sub) {
        return TextUtils.isEmpty(strData) ? "" : StringUtils.substringBefore(strData, sub);
    }

    /**
     * 截取字符串指定字符的后段
     *
     * @param strData
     * @param sub
     * @return
     */
    public static String subStringAfter(String strData, String sub) {
        return TextUtils.isEmpty(strData) ? "" : StringUtils.substringAfter(strData, sub);
    }

    /**
     * 截取字符串 从指定第from位开始截取 ， 截取num位， 并在末尾拼接sign
     *
     * @param strData 需要截取的资源
     * @param from    开始截取的位数
     * @param num     截取多少位
     * @param sign    要拼接的字符串
     * @return
     */
    public static String subNumSplitSign(String strData, int from, int num, String sign) {
        if (TextUtils.isEmpty(strData) || strData.length() <= num) {
            return strData;
        }
        return midNum(strData, from, num) + sign;
    }

    /**
     * 复制 文本
     *
     * @param value 需要复制的文本
     */
    public static boolean copyStr(String value) {
        try {
            ClipboardManager cm = (ClipboardManager) Utils.getApp().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData mClipData = ClipData.newPlainText("", value);
            cm.setPrimaryClip(mClipData);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 隐藏名字,返回如 **超
     *
     * @param name
     * @return
     */
    public static String hiddenName(String name) {
        return new StringBuilder("**").append(name.charAt(name.length() - 1)).toString();
    }

    /**
     * 隐藏身份证号码 返回如4****************9
     *
     * @param id
     * @return
     */
    public static String hiddenId(String id) {
        return new StringBuilder(String.valueOf(id.charAt(0)))
                .append("****************")
                .append(id.charAt(id.length() - 1)).toString();
    }

    /**
     * 判断是否可以转成number
     * @param str   代转字符串
     * @return
     */
    public static boolean checkStr2Int(String str){
        return NumberUtils.isDigits(str);
    }



}
