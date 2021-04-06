package com.dayi35.qx_utils.widget;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;

import com.dayi35.qx_utils.common.DimensUtil;


/**
 * Created by hyl on 2019/10/11.
 */

public class LayoutParamsManager {
    public static final float STANDARD_WIDTH = 750f;
    public static final float STANDARD_HEIGHT = 1624f;
    public static final float STANDARD_DENSITY = 1f;

    private LayoutParamsManager() {
    }

    private static final void scaleWidthAndHeightByRatio(
            LayoutParams lt) {

        int widthWeight = lt.width;
        int heightWeight = lt.height;

        if (widthWeight != LayoutParams.WRAP_CONTENT &&
                widthWeight != LayoutParams.MATCH_PARENT) {
            widthWeight = scaleWidthPixels(widthWeight);
        }
        if (heightWeight != LayoutParams.WRAP_CONTENT &&
                heightWeight != LayoutParams.MATCH_PARENT) {
            heightWeight = scaleHeightPixels(heightWeight);
        }

        lt.width = widthWeight;
        lt.height = heightWeight;
    }


    public static final void scalePaddingForView(View source) {
        if (source != null) {
            int left = source.getPaddingLeft();
            int top = source.getPaddingTop();
            int right = source.getPaddingRight();
            int bottom = source.getPaddingBottom();
            if (left > 0 && isScaleWidthPixels()) {
                left = scaleWidthPixels(left);
            }

            if (top > 0 && isScaleHeightPixels()) {
                top = scaleHeightPixels(top);
            }

            if (right > 0 && isScaleWidthPixels()) {
                right = scaleWidthPixels(right);
            }

            if (bottom > 0 && isScaleHeightPixels()) {
                bottom = scaleHeightPixels(bottom);
            }

            source.setPadding(left, top, right, bottom);
        }
    }


    private static final void scaleMargin(MarginLayoutParams params) {
        if (params == null)
            return;
        if (params.leftMargin != 0 && isScaleWidthPixels()) {
            params.leftMargin = scaleWidthPixels(params.leftMargin);
        }

        if (params.topMargin != 0 && isScaleHeightPixels()) {
            params.topMargin = scaleHeightPixels(params.topMargin);
        }

        if (params.rightMargin != 0 && isScaleWidthPixels()) {
            params.rightMargin = scaleWidthPixels(params.rightMargin);
        }

        if (params.bottomMargin != 0 && isScaleHeightPixels()) {
            params.bottomMargin = scaleHeightPixels(params.bottomMargin);
        }
    }

    public static final void scaleViewByRatio(View source) {
        if (source == null || !isScale()) {
            return;
        }

        LayoutParams lt = source.getLayoutParams();
        if (lt != null) {
            scaleWidthAndHeightByRatio(lt);

            if (lt instanceof MarginLayoutParams) {
                scaleMargin((MarginLayoutParams) lt);
            }

            scalePaddingForView(source);
        }
    }


    /**
     * 对所有子view的宽高，padding，margin做比例性放大缩小
     *
     * @param group view
     */
    public static final void scaleViewGroupByRatio(ViewGroup group) {
        if (group == null || !isScale()) {
            return;
        }
        final int childCount = group.getChildCount();

        for (int i = 0; i < childCount; i++) {
            View child = group.getChildAt(i);
            scaleViewByRatio(child);
        }
    }

    private static final boolean isScale() {
        return isScaleHeightPixels() || isScaleWidthPixels();
    }

    private static final boolean isScaleWidthPixels() {
        return (DimensUtil.getScreenWidth() / STANDARD_WIDTH) != STANDARD_DENSITY;
    }

    private static final boolean isScaleHeightPixels() {
        return (DimensUtil.getScreenHeight() / STANDARD_HEIGHT) != STANDARD_DENSITY;
    }


    public static final int scaleWidthPixels(int px) {
        if (px == 0) {
            return 0;
        }
        int result = Math.round(DimensUtil.getScreenWidth() / STANDARD_WIDTH * px);
        return result;
    }

    public static final float scaleWidthPixels(float px) {
        if (px == 0) {
            return 0;
        }
        float result = Math.round(DimensUtil.getScreenWidth() / STANDARD_WIDTH * px);
        return result;
    }

    public static final int scaleHeightPixels(int px) {
        if (px == 0) {
            return 0;
        }
        int result = Math.round(DimensUtil.getScreenHeight() / STANDARD_HEIGHT * px);
        return result;
    }

}
