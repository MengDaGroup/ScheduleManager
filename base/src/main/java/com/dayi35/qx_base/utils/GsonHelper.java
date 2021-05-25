package com.dayi35.qx_base.utils;


import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.List;

/**
 * <json公共解析库>
 *
 * @author Akeecai
 * @version [版本号, 2016/6/6]
 * @see [相关类/方法]    Type type = new TypeToken<T>(){}.getType();
 *             T entity = GsonHelper.JSONToObj(json, type);
 * @since [V1]
 */
public class GsonHelper {

    private static String TAG = GsonHelper.class.getName();

    private static Gson gson = new Gson();

    /**
     * 把json string 转化成类对象
     *
     * @param str
     * @param t
     * @return
     */
    public static <T> T toType(String str, Class<T> t) {
        try {
            if (str != null && !"".equals(str.trim())) {
                T res = gson.fromJson(str.trim(), t);
                return res;
            }
        } catch (Exception e) {
            Log.e(TAG, "exception:" + e.getMessage());
        }
        return null;
    }

    /**
     * 把类对象转化成json string
     *
     * @param t
     * @return
     */
    public static <T> String toJson(T t) {
        return gson.toJson(t);
    }

    /**
     * json转实体类
     */
    public static <T> T JSONToObj(String jsonStr, Type type) {
        T t = null;
        try {
            Gson gson = new Gson();
            t = gson.fromJson(jsonStr, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * listObject 转Jsonarray
     * @param list
     * @return
     */
    public static JSONArray ProLogList2Json(List<?> list){
        JSONArray json = new JSONArray();
        for(Object pLog : list){
            json.put(new Gson().toJson(pLog));
        }
        return json;
    }

}
