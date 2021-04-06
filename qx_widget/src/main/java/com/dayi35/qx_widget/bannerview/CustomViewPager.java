package com.dayi35.qx_widget.bannerview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * created by yao on 2020/12/17
 * Describe:
 **/
public class CustomViewPager extends ViewPager {

    private List<Integer> mChildCenterXAbs = new ArrayList<>();
    private SparseArray<Integer> mChildIndex = new SparseArray<>();
    private boolean mIsSlide = true;  //是否可以左右滑动

    private int mLastX;
    private int mLastY;

    public CustomViewPager(@NonNull Context context) {
        super(context);
        init();
    }

    public CustomViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setClipToPadding(false);
        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    /**
     * 第n个位置的child的绘制索引
     */
    @Override
    protected int getChildDrawingOrder(int childCount, int n) {
        if (n == 0 || mChildIndex.size() != childCount) {
            mChildCenterXAbs.clear();
            mChildIndex.clear();
            int viewCenterX = getViewCenterX(this);
            for (int i = 0; i < childCount; i++) {
                int indexAbs = Math.abs(viewCenterX - getViewCenterX(getChildAt(i)));
                //两个距离相同，后来的 那个做自增，从而保持abs不同
                if (mChildIndex.get(indexAbs) != null) {
                    ++indexAbs;
                }
                mChildCenterXAbs.add(indexAbs);
                mChildIndex.append(indexAbs, i);
            }
            Collections.sort(mChildCenterXAbs);
        }
        //那个item距离中心点远一些，就先draw它。（最近的就是中间放大的item,最后draw）
        return mChildIndex.get(mChildCenterXAbs.get(childCount - 1 - n));
    }

    private int getViewCenterX(View view) {
        int[] array = new int[2];
        view.getLocationOnScreen(array);
        return array[0] + view.getWidth() / 2;
    }

    public void enableSlide(boolean isSlide) {
        mIsSlide = isSlide;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!mIsSlide) {
            return false;
        } else {
            return super.onInterceptTouchEvent(ev);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        boolean consumed = false;

        final int action = ev.getAction() & MotionEvent.ACTION_MASK;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                consumed = true;
                break;

            case MotionEvent.ACTION_MOVE:
//水平移动的 增量
                int deltaX = x - mLastX;
                //垂直移动的 增量
                int deltaY = y - mLastY;
                //当水平增量大于竖直增量时，表示水平滑动，此时需要父View去处理事件
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                    consumed = true;
                } else {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;

            case MotionEvent.ACTION_UP:

                break;
            default:
                break;

        }
        mLastY = y;
        mLastX = x;
        return super.dispatchTouchEvent(ev);
    }
}
