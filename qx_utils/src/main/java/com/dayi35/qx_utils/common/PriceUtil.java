package com.dayi35.qx_utils.common;

import android.text.TextUtils;

import java.text.DecimalFormat;

/**
 * created by yao on 2020/12/18
 * Describe:价格转换
 **/
public class PriceUtil {

    /**
     * 字符串转double
     *
     * @param str
     * @return
     */
    public static double toDouble(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0.00;
        }
        if (str.startsWith(".")) {
            str = "0" + str;
        }
        return Double.parseDouble(str);
    }

    /**
     * 转化成人民币格式，如¥16800.00
     */
    public static String doubleFormat2(Object sum) {
        double value = toDouble(sum.toString());
        return doubleFormat5(value);
    }

    /**
     * @param value
     * @return 如果是1.0 ，返回¥1；如果是1.1，返回¥1.1，如果是1.23返回¥1.23
     */
    public static String doubleFormat3(double value) {
        if ((int) value == value) {
            return new StringBuilder("¥")
                    .append(String.valueOf((int) value))
                    .toString();

        } else {
            return new StringBuilder("¥")
                    .append(String.valueOf(value))
                    .toString();
        }
    }

    /**
     * 转化成人民币格式，如¥16800.00
     */
    public static String doubleFormat5(double value) {
        if (value <= 0) {
            return "¥0.00";
        }
        if (value <= 1) {
            return appendStr("¥",
                    String.format("%.2f", value));
        }
        DecimalFormat df = new DecimalFormat("#.00");
        String m = appendStr("¥",
                df.format(value));
        return m;
    }

    /**
     * 如果是整数，返回整数，如果有小数保留两个小数  如20   20.99
     */
    public static String doubleFormat6(double value) {
        if (value == (int) value) {
            return String.valueOf((int) value);
        } else {
            return doubleFormat7(value);
        }
    }

    /**
     * 2个小数位 如20.00  0.00   20.99  20.50
     */
    public static String doubleFormat7(double value) {
        if (value <= 1) {
            return String.format("%.2f", value);
        }
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(value);
    }

    /**
     * 如果是整数，返回整数，如果有小数保留两个小数  如20   20.99
     */
    public static String doubleFormat8(double value) {
        return "¥ " + doubleFormat6(value);
    }


    private static String appendStr(String str1, String str2) {
        StringBuilder sb = new StringBuilder(str1);
        return sb.append(str2).toString();
    }

}
