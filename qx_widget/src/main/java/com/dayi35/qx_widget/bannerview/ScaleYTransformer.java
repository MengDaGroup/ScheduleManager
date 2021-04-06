package com.dayi35.qx_widget.bannerview;

import android.os.Build;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

/**
 * created by yao on 2020/12/17
 * Describe:切换效果，y轴变小
 **/
public class ScaleYTransformer implements ViewPager.PageTransformer {

    private static final float MAX_SCALE = 1.05f;
    private static final float MIN_SCALE = 0.9f;
    private ViewPager mViewPager;

    public ScaleYTransformer() {
    }

    public ScaleYTransformer(ViewPager viewPager) {
        this.mViewPager = viewPager;
    }


    @Override
    public void transformPage(@NonNull View page, float position) {
        if (position < -1) {
            position = -1;
        } else if (position > 1) {
            position = 1;
        }

        float tempScale = position < 0 ? 1 + position : 1 - position;
        float slope = (MAX_SCALE - MIN_SCALE) / 1;
        float scaleValue = MIN_SCALE + tempScale * slope;
        page.setScaleY(scaleValue);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            page.getParent().requestLayout();
        }
    }


}
