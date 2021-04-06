package com.dayi35.qx_utils.convert;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by LiuJC on 2020/4/2 double处理
 **/
public class DoubleUtil {

    /**
     * String 转成double
     * @param data
     * @return
     */
    public static Double toDouble(String data) {
        Double result = 0d;
        try {
            result = Double.parseDouble(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static int toInt(String data) {
        int result = -100;
        try {
            result = Integer.parseInt(data);
        } catch (Exception e) {
            e.printStackTrace();
            result = 0;
        }
        return result;
    }

    public static String doubleFormat(double sum) {
//        if (toDouble(sum.toString()) <= 0) {
//            return "0.00";
//        }
        if (sum < 1) {
            String s = sum+"";
            if (s.contains(".") && s.indexOf(".") + 3 <= s.length()) {
                return s.substring(0, s.indexOf(".") + 3);
            } else {
                return s + "0";
            }
        }
        DecimalFormat df = new DecimalFormat("#.00");
        String m = df.format(sum);
        return m;
    }

    /**
     * 转化成人民币格式，如¥16800.00
     */
    public static String doubleFormat5(double value) {
        if (value <= 0) {
            return "¥0.00";
        }
        if (value <= 1) {
//            return appendStr("¥", value + "");
            return appendStr("¥",
                    String.format("%.2f", value));
        }
        DecimalFormat df = new DecimalFormat("#.00");
        String m = appendStr("¥",
                df.format(value));
        return m;
    }

    public static String appendStr(String str1, String str2) {
        StringBuilder sb = new StringBuilder(str1);
        return sb.append(str2).toString();
    }

    /**
     * 加法运算
     * @param m1
     * @param m2
     * @return
     */
    public static double addDouble(double m1, double m2) {
        BigDecimal p1 = new BigDecimal(Double.toString(m1));
        BigDecimal p2 = new BigDecimal(Double.toString(m2));
        return p1.add(p2).doubleValue();
    }

    /**
     * 减法运算
     * @param m1
     * @param m2
     * @return
     */
    public static double subDouble(double m1, double m2) {
        BigDecimal p1 = new BigDecimal(Double.toString(m1));
        BigDecimal p2 = new BigDecimal(Double.toString(m2));
        return p1.subtract(p2).doubleValue();
    }

    /**
     * 乘法运算
     * @param m1
     * @param m2
     * @return
     */
    public static double mul(double m1, double m2) {
        BigDecimal p1 = new BigDecimal(Double.toString(m1));
        BigDecimal p2 = new BigDecimal(Double.toString(m2));
        return p1.multiply(p2).doubleValue();
    }


    /**
     *  除法运算
     *   @param   m1
     *   @param   m2
     *   @param   scale
     *   @return
     */
    public static double div(double m1, double m2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("Parameter error");
        }
        BigDecimal p1 = new BigDecimal(Double.toString(m1));
        BigDecimal p2 = new BigDecimal(Double.toString(m2));
        return p1.divide(p2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 保留两位，向上取整
     */
    public static double keep2Decimalup(double data) {
        String dataStr = data+"";
        if (dataStr.contains(".") && dataStr.indexOf(".") + 3 < dataStr.length()){
            //保留两位位小数向上截取（非四舍五入）
            double result = new BigDecimal(data).setScale(2, BigDecimal.ROUND_UP).doubleValue();
            return result;
        }else {
            return data;
        }
    }
}
