package com.dayi35.qx_base.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by ljc on 2018/3/avatar.
 * multipart上传数据封装工具类
 */

public class MultiparBodyUtil {
    /**
     * 上传文件类型
     */
    public static final String TYPE_FILE = "multipart/form-data";
    /**
     * 上传文本类型
     */
    public static final String TYPE_TEXT = "text/plain";

    public static final String TYPE_IMG = "image/png";
    /**
     * 将文件路径封装成{@link List <MultipartBody.Part>}
     * @param key  接口中的key
     * @param filePaths 文件路径数组
     * @param mediaType 文件类型
     * @return
     */
    public static List<MultipartBody.Part> files2Part(String key, String[]filePaths, MediaType mediaType){
        List<MultipartBody.Part> parts = new ArrayList<>(filePaths.length);
        for (String filePath : filePaths) {
            File file = new File(filePath);
            //根据文件以及文件类型创建requestBody
            RequestBody requestBody = RequestBody.create(mediaType,file);
            //将requestBody封装成MultipartBody.Part类型
            MultipartBody.Part part = MultipartBody.Part.createFormData(key,file.getName(),requestBody);
            parts.add(part);
        }
        return parts;
    }

    /**
     * 其实也是将File封装成RequestBody，然后再封装成Part，
     * 不同的是使用MultipartBody.Builder来构建MultipartBody
     * @param key 接口中的key
     * @param filePaths 文件路径数组
     * @param mediaType 文件类型
     * @return
     */
    public static MultipartBody files2MultipartBody(String key, String[]filePaths, MediaType mediaType){
        MultipartBody.Builder builder = new MultipartBody.Builder();
        for (String filePath : filePaths) {
            File file = new File(filePath);
            RequestBody requestBody = RequestBody.create(mediaType,file);
            builder.addFormDataPart(key,file.getName(),requestBody);
        }
        builder.setType(MultipartBody.FORM);
        return builder.build();
    }

    public static MultipartBody files2MultipartBody(String key, String[]filePaths){
        MultipartBody.Builder builder = new MultipartBody.Builder();
        for (String filePath : filePaths) {
            File file = new File(filePath);
            RequestBody requestBody = RequestBody.create(MultipartBody.FORM,file);
            builder.addFormDataPart(key,file.getName(),requestBody);
        }
        builder.setType(MultipartBody.FORM);
        return builder.build();
    }

    public static MultipartBody.Part files2MultipartBody(String key, String filePaths){
        File file = new File(filePaths);
        //根据文件以及文件类型创建requestBody
        RequestBody requestBody = RequestBody.create(MultipartBody.FORM,file);
        //将requestBody封装成MultipartBody.Part类型
        MultipartBody.Part part = MultipartBody.Part.createFormData(key,file.getName(),requestBody);
        return part;
    }

    public static MultipartBody.Part image2MultipartBody(String key, String filePaths){
        File file = new File(filePaths);
        //根据文件以及文件类型创建requestBody
        RequestBody requestBody = RequestBody.create(MediaType.parse(TYPE_IMG),file);
        //将requestBody封装成MultipartBody.Part类型
        MultipartBody.Part part = MultipartBody.Part.createFormData(key,file.getName(),requestBody);
        return part;
    }


    /**
     * 直接添加文本类型的Part到的MultipartBody的Part集合中
     * @param parts Part集合
     * @param key 接口中的key
     * @param value 文本内容
     * @param posion 插入的位置
     */
    public static void addTextToPart(List<MultipartBody.Part> parts, String key, String value, int posion){
        RequestBody requestBody = RequestBody.create(MediaType.parse(TYPE_TEXT),value);
        MultipartBody.Part part = MultipartBody.Part.createFormData(key,null,requestBody);
        parts.add(posion,part);
    }

    /**
     * 添加文本类型的Part到的MultipartBody.Builder中
     * @param builder  用于构建MultipartBody的Builder
     * @param key 接口中的key
     * @param value 文本内容
     * @return
     */
    public static MultipartBody.Builder addTextPart(MultipartBody.Builder builder, String key, String value){
        RequestBody requestBody = RequestBody.create(MediaType.parse(TYPE_TEXT),value);
        // MultipartBody.Builder的addFormDataPart()有一个直接添加key value的重载，但坑的是这个方法
        // 不会设置编码类型，会出乱码，所以可以使用3个参数的，将中间的filename置为null就可以了
        // builder.addFormDataPart(key, value);
        // 还有一个坑就是，后台取数据的时候有可能是有顺序的，比如必须先取文本后取文件，
        // 否则就取不到（真弱啊...），所以还要注意add的顺序
        builder.addFormDataPart(key,null,requestBody);
        return builder;
    }
}
