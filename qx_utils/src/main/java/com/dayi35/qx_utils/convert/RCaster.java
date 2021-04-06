package com.dayi35.qx_utils.convert;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2020/11/23 15:57
 * 描    述: 类    R2反射工具类
 * 修订历史:
 * =========================================
 */
public class RCaster {
    //id取得属性名字
    private HashMap<Long, String> r1Map = new HashMap<>();
    //属性名字回取id
    private HashMap<String, Long> r2Map = new HashMap<>();
    private Class R;
    private Class R2;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public RCaster(Class r, Class r2) {
        R = r;
        R2 = r2;
        initMap1();
        initMap2();
    }

    /**
     * R1 id cast to R2
     *
     * @param rid
     * @return
     */
    public int cast(int rid) {
        Long key = Long.valueOf(rid);
        String name = r1Map.get(key);
        if(name == null ) return 1;
        Long id2 = r2Map.get(name);
        return id2 == null ? 1 : id2.intValue();
    }

    /**
     * 初始化r1Map
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressWarnings("Duplicates")
    private void initMap1() {
        Class[] classes = R.getClasses();

        for (Class aClass : classes) {
            if (aClass.getSimpleName().equals("id")) {
                Field[] fields = aClass.getFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    try {
                        Object obj = field.get(aClass);
                        if(obj == null) continue;
                        String value = obj.toString();
                        Long x = Long.valueOf(value);
                        if(!r1Map.containsKey(x))
                            r1Map.put(x, field.getName());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    /**
     * 初始化r2Map
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressWarnings("Duplicates")
    private void initMap2() {
        Class[] classes = R2.getClasses();

        for (Class aClass : classes) {
            if (aClass.getSimpleName().equals("id")) {
                Field[] fields = aClass.getFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    try {
                        Object obj = field.get(aClass);
                        if(obj == null) continue;
                        String value = obj.toString();
                        Long x = Long.valueOf(value);
//                        r2Map.put(x, field.getName());
                        if(!r2Map.containsKey(x))
                            r2Map.put(field.getName(),x);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }
}
