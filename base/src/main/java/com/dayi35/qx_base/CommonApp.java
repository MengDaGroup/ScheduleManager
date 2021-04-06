package com.dayi35.qx_base;

import android.app.Application;
import android.content.Context;
import android.util.Config;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.launcher.ARouter;
import com.dayi35.qx_base.arouter.ARouterHelper;
import com.dayi35.qx_utils.androidcodeutils.Utils;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;

import cn.bmob.v3.Bmob;
import timber.log.Timber;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2020/11/12 17:38
 * 描    述: 类    application
 * 修订历史:
 * =========================================
 */
public class CommonApp extends Application {
    public static Application ins;
    private static CommonApp instance;

    public static CommonApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        ins = this;
        instance = this;
        initARouter();
        initTimber();
        initImagePicker();
        Bmob.initialize(this, "8281752079635ffb5d990087c5955837");

    }



    /**
     * 初始化ARouter相关
     */
    private void initARouter(){
        if (Config.DEBUG){
            ARouter.openLog();// Print log
            ARouter.openDebug();//open debug
        }
        ARouter.init(this);
    }

    /**
     * 初始化Timber日志
     */
    private void initTimber(){
        if (Config.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new ProductionTree());
        }
    }

    /**
     * 生产环境下的日志处理，可根据日志级别做相应的处理
     */
    private static class ProductionTree extends Timber.Tree {
        @Override
        protected void log(int priority, String tag, @NonNull String message, Throwable t) {
            //TODO 生产环境下日志的上传或其他操作
        }
    }

    /**
     * 初始化图片选择器
     */
    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
//        imagePicker.setImageLoader(new ImageLoaderUtil());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(false);                            //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setSelectLimit(8);              //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);                         //保存文件的高度。单位像素
    }
    @Override
    public void onTerminate() {
        super.onTerminate();
        ARouterHelper.destroy();
    }
}
