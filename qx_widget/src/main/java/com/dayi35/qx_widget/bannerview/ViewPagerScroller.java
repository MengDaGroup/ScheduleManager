package com.dayi35.qx_widget.bannerview;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * created by yao on 2020/12/17
 * Describe:滚动控制器
 **/
public class ViewPagerScroller extends Scroller {

    private int mDuration = 1000;// ViewPager默认的最大Duration 为250,我们默认稍微大一点。值越大越慢。
    private boolean mIsUseDefaultDuration = false;


    public ViewPagerScroller(Context context) {
        super(context);
    }

    public ViewPagerScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public ViewPagerScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    public boolean isUseDefaultDuration() {
        return mIsUseDefaultDuration;
    }

    public void setDuration(int duration) {
        mDuration = duration;
    }


    public int getScrollDuration() {
        return mDuration;
    }

    /**
     * 设置滚动时间
     *
     * @param startX
     * @param startY
     * @param dx
     * @param dy
     */
    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mIsUseDefaultDuration ? duration : mDuration);
    }

    /**
     * 是否用默认时间
     *
     * @param useDefaultDuration
     */
    public void setUseDefaultDuration(boolean useDefaultDuration) {
        mIsUseDefaultDuration = useDefaultDuration;
    }
}
