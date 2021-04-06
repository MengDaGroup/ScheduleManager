package com.dayi35.qx_utils.helper;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dayi35.qx_utils.R;
import com.dayi35.qx_utils.img.BitmapUtil;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2020/11/30 9:44
 * 描    述: 类 模糊界面显示工具
 * 修订历史:
 * =========================================
 */
public class BlurViewUtils {
    private Activity activity;
    private ImageView mBlurView;

    public BlurViewUtils(Activity activity) {
        this.activity = activity;
    }

    /**
     * 显示模糊背景
     */
    public void showBlurView(){
        Bitmap screenshot;
        screenshot = BitmapUtil.screenShot(activity);
        ViewGroup viewGroup = ((ViewGroup) activity.getWindow().getDecorView());
        mBlurView = viewGroup.findViewById(R.id.utils_iv_blur);
        if (mBlurView == null) {
            mBlurView = (ImageView) LayoutInflater.from(activity).inflate(R.layout.utils_include_blurview, viewGroup, false);
            viewGroup.addView(mBlurView);
        }
        mBlurView.setImageBitmap(BitmapUtil.blurBitmap(activity, screenshot, 15));
    }

    public void hideBlurView(){
        if (null != mBlurView){
            mBlurView.setImageBitmap(null);
        }
    }
}
