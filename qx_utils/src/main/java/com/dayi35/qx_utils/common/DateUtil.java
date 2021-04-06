package com.dayi35.qx_utils.common;

import android.text.TextUtils;
import android.widget.TextView;

import org.apache.commons.lang3.time.DateUtils;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ljc on 2017/4/27.
 */

public class DateUtil {
    private static String result = "";
    public static String mFormat_24 = "yyyy-MM-dd HH:mm:ss";  //24小时制
    public static String mFormat_24_minute = "yyyy-MM-dd HH:mm";  //24小时制
    public static String mFormat_12 = "yyyy-MM-dd hh:mm:ss";  //12小时制
    public static String mFormat_date_24 = "yyyy-MM-dd";  //
    public static String mFormat_Y_M_D = "yyyy/MM/dd";
    public static String mFormat_YY_MM_DD = "yyyy年MM月dd日";
    public static String mFormat_MM_DD_HH_MM = "MM月dd日 HH:mm";

    /**
     * 调此方法输入所要转换的时间、格式，返回时间戳
     */
    public static String getTimestamp(String time, String type, String type2) {
        SimpleDateFormat sdr = new SimpleDateFormat(type, Locale.CHINA);
        SimpleDateFormat sdr2 = new SimpleDateFormat(type2);
        Date date;
        String times2 = null;
        try {
            date = sdr.parse(time);
            times2 = sdr2.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return times2;
    }

    /**
     * 根据时间戳和时间格式，获取日期string
     *
     * @param timeStamp 时间戳
     * @param format    日期格式
     * @return
     */
    public static String formatByTimeStamp(long timeStamp, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        result = sdf.format(new Date(timeStamp));  //php获取到的时间戳是秒，java里面是毫秒，所以要乘以1000（如果时间戳是毫秒，则不需要）
        return result;
    }

    public static String formatByTimeStamp(Date data, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        result = sdf.format(data);
        return result;
    }


    /**
     * 动态的把秒设置为其他单位的数值
     *
     * @param second  秒
     * @param tvValue 转换为其他单位后的值
     * @param tvUnit  转换后的单位
     */
    public static void setDynamicTime(int second, TextView tvValue, TextView tvUnit) {
        if (second > 3600) {
            DecimalFormat df = new DecimalFormat("0.0");
            tvValue.setText(df.format((float) second / 3600));
            tvUnit.setText(" 小时 ");
        } else if (second >= 60) {
            int miniate = second / 60;
            tvValue.setText(miniate + "");
            tvUnit.setText(" 分钟 ");
        } else {
            tvValue.setText(second + "");
            tvUnit.setText(" 秒 ");
        }
    }


    /**
     * 计算剩余确认收货时间
     *
     * @param time
     * @return
     */
    public static String calcRemainConfirmTime(int time) {
        StringBuilder builder;
        int day = time / 24;
        int hour = time % 24;
        builder = new StringBuilder("还剩").append(day).append("天").append(hour).append("小时确认");

        return builder.toString();

    }

    /**
     * 调此方法输入所要转换的时间输入例如（"2014-06-14  16:09:00"）返回时间戳
     */
    public static long dateToLong(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        Date date;
        long l = 0;
        try {
            date = sdr.parse(time);
            l = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return l;
    }

    /**
     * 将yyyy-MM-dd HH:mm:ss字符串转换成日期<br/>
     *
     * @param dateStr yyyy-MM-dd HH:mm:ss字符串
     * @return Date 日期 ,转换异常时返回null。
     */
    public static Date parseDate(String dateStr) {
        return parseDate(dateStr, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 将yyyy-MM-dd HH:mm:ss字符串转换成日期<br/>
     *
     * @param dateStr    时间字符串
     * @param dataFormat 当前时间字符串的格式。
     * @return Date 日期 ,转换异常时返回null。
     */
    public static Date parseDate(String dateStr, String dataFormat) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(dataFormat, Locale.PRC);
            Date date = dateFormat.parse(dateStr);
            return new Date(date.getTime());
        } catch (ParseException e) {
            return null;
        }
    }


    public static String getDateDetail(String date) {
        //第一种方式获取今天日期
        String today1 = DateFormat.getDateInstance().format(new Date());
        System.out.println(today1);

        //第二种方式获取今天日期
        java.sql.Date today2 = new java.sql.Date(System.currentTimeMillis());
        System.out.println(today2);

        //第三种方式获取今天日期
        Format f = new SimpleDateFormat("yyyy-MM-dd");//注意：MM要大写
        Date today = new Date();
        if (date.contains(f.format(today))) {
            return new StringBuilder("今日 ").append(date.substring(11, 16)).toString();
        }

        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.DATE, 1);// 今天+1天,明天
        Date tomorrow = c.getTime();

        if (date.contains(f.format(tomorrow))) {
            return new StringBuilder("明日 ").append(date.substring(11, 16)).toString();
        }
        date = date.substring(5);
        date = date.replaceFirst("-", "月");
        date = date.replaceFirst(" ", "日 ");
        date = date.substring(0, 12);
        return date;
    }

    public static String getDateDetail2(String date) {
        //第一种方式获取今天日期
        String today1 = DateFormat.getDateInstance().format(new Date());
        System.out.println(today1);

        //第二种方式获取今天日期
        java.sql.Date today2 = new java.sql.Date(System.currentTimeMillis());
        System.out.println(today2);

        //第三种方式获取今天日期
        Format f = new SimpleDateFormat("yyyy-MM-dd");//注意：MM要大写
        Date today = new Date();
        if (date.contains(f.format(today))) {
            return new StringBuilder("今日 ").append(date.substring(11, 16)).toString();
        }

        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.DATE, 1);// 今天+1天,明天
        Date tomorrow = c.getTime();

        if (date.contains(f.format(tomorrow))) {
            return "明日预告";
        }
        date = date.substring(5, 10);
        date = date.replaceFirst("-", "月");
        date = date + "日";
//        date = date.replaceFirst(" ", "日 ");
        return date;
    }

    /**
     * 是否当天
     *
     * @return
     */
    public static boolean isToday(String date) {
        Format f = new SimpleDateFormat("yyyy-MM-dd");//注意：MM要大写
        Date today = new Date();
        if (date.contains(f.format(today))) {
            return true;
        }
        return false;
    }

    /**
     * 是否小于等于3天
     * 从日期里截取日，如2021-04-19 23:59:59截取  19
     */
    public static boolean isLess3Day(String dateValue) {
        if (TextUtils.isEmpty(dateValue) || dateValue.length() < 10) {
            return false;
        }

        Calendar nowCalendar = Calendar.getInstance();
        Date date = parseDate(dateValue);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        if (nowCalendar.get(Calendar.YEAR) != calendar.get(Calendar.YEAR)) {
            return false;
        }

        if (calendar.get(Calendar.DAY_OF_YEAR) - nowCalendar.get(Calendar.DAY_OF_YEAR) > 2) {
            return false;
        }
        return true;
    }
}
