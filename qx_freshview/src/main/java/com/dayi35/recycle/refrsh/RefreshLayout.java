package com.dayi35.recycle.refrsh;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by yao on 2019/12/26.
 */


public class RefreshLayout extends RefreshInterceptLauyout {

    // 事件监听接口
    private OnRefreshListener mListener;
    // Layout状态
    private RefreshStatus mStatus = RefreshStatus.DEFAULT;
    //阻尼系数
    private float mDamp = 0.5f;
    //恢复动画的执行时间
    public int SCROLL_TIME = 300;
    //是否刷新完成
    private boolean mIsRefreshSuccess = false;
    //是否加载完成
    private boolean mIsLoadSuccess = false;
    //正在加载中
    public boolean mIsLoading = false;
    //正在刷新中
    public boolean mIsRefreshing = false;
    //是否自动下拉刷新
    private boolean mIsAutoRefresh = false;

    //操作状态  -1是默认的状态   0刷新   1加载
    private int mActionStatus = -1;

    public RefreshLayout(Context context) {
        super(context);
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 设置是否支持下拉刷新
     *
     * @param isCanRefresh
     */
    public void setCanRefresh(boolean isCanRefresh) {
        this.mIsCanRefresh = isCanRefresh;
    }

    /**
     * 设置是否支持加载更多
     *
     * @param isCanLoad
     */
    public void setCanLoad(boolean isCanLoad) {
        this.mIsCanLoad = isCanLoad;
    }

    /**
     * 设置是否支持自动刷新
     *
     * @param isAutoRefresh
     */
    public void setAutoRefresh(boolean isAutoRefresh) {
        this.mIsAutoRefresh = isAutoRefresh;
        autoRefresh();
    }


    /**
     * 自动刷新
     */
    public void autoRefresh() {
        if (!mIsAutoRefresh) return;
        mIsRefreshing = true;
        measureView(mHeader);
        int end = mHeaderContent.getMeasuredHeight();
        performAnim(0, -end, new AnimListener() {
            @Override
            public void onGoing() {
                updateStatus(mStatus.REFRESH_READY);
            }

            @Override
            public void onEnd() {
                updateStatus(mStatus.REFRESH_DOING);
            }
        });

    }

    /**
     * 测量view
     *
     * @param v
     */
    public void measureView(View v) {
        if (v == null) {
            return;
        }
        int w = MeasureSpec.makeMeasureSpec(0,
                MeasureSpec.UNSPECIFIED);
        int h = MeasureSpec.makeMeasureSpec(0,
                MeasureSpec.UNSPECIFIED);
        v.measure(w, h);
    }

    /**
     * 设置接口回调
     *
     * @param listener
     */
    public void setOnRefreshListener(OnRefreshListener listener) {
        this.mListener = listener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                // 计算本次滑动的Y轴增量(距离)
                int dy = y - mLastYMove;
                // 如果getScrollY<0，即下拉操作

                if (getScrollY() < 0) { // 进行Y轴上的滑动
                    if (mHeader != null && !mIsLoading && !mIsRefreshing) {
                        goToRefresh(dy);
                    }
                } else if (getScrollY() > 0) {  // 如果getScrollY>=0，即上拉操作
                    if (mFooter != null && !mIsRefreshing && !mIsLoading) {
                        goToLoad(dy);
                    }
                } else {
                    if (dy >= 0) {
                        if (mActionStatus == -1) mActionStatus = 0;
                        goToRefresh(dy);
                    } else {
                        if (mActionStatus == -1) mActionStatus = 1;
                        goToLoad(dy);
                    }
                }
                // 记录y坐标
                mLastYMove = y;
                break;
            case MotionEvent.ACTION_UP:
                // 判断本次触摸系列事件结束时,Layout的状态
                switch (mStatus) {
                    //下拉刷新
                    case REFRESH_BEFORE:
                        scrolltoDefaultStatus(mStatus.REFRESH_CANCEL);
                        break;
                    case REFRESH_AFTER:
                        scrolltoRefreshStatus();
                        break;
                    //上拉加载更多
                    case LOAD_BEFORE:
                        scrolltoDefaultStatus(mStatus.LOAD_CANCEL);
                        break;
                    case LOAD_AFTER:
                        scrolltoLoadStatus();
                        break;
                    default:
                        mActionStatus = -1;
                        break;
                }
            case MotionEvent.ACTION_CANCEL:
                mActionStatus = -1;
                break;
        }
        mLastYIntercept = 0;
        postInvalidate();
        return true;
    }


    /**
     * 去刷新
     *
     * @param dy
     */
    private void goToRefresh(int dy) {
        if (mActionStatus == 0 && mHeaderContent != null) {
            performScroll(dy);
            if (Math.abs(getScrollY()) > mHeaderContent.getMeasuredHeight()) {
                updateStatus(mStatus.REFRESH_AFTER);
            } else {
                updateStatus(mStatus.REFRESH_BEFORE);
            }
        }
    }

    /**
     * 去加载
     *
     * @param dy
     */
    private void goToLoad(int dy) {
        if (mActionStatus == 1) {
            // 进行Y轴上的滑动
            performScroll(dy);
            if (mFooter != null && getScrollY() >= mBottomScroll + mFooter.getMeasuredHeight()) {
                updateStatus(mStatus.LOAD_AFTER);
            } else {
                updateStatus(mStatus.LOAD_BEFORE);
            }
        }
    }


    /**
     * 刷新状态
     *
     * @param status
     */
    private void updateStatus(RefreshStatus status) {
        this.mStatus = status;
        int scrollY = getScrollY();
        // 判断本次触摸系列事件结束时,Layout的状态
        switch (status) {
            //默认状态
            case DEFAULT:
                onDefault();
                break;
            //下拉刷新
            case REFRESH_BEFORE:
                if (mOnHeaderListener != null) {
                    mOnHeaderListener.onRefreshBefore(scrollY, mHeaderContent.getMeasuredHeight(), mHeader.getMeasuredHeight());
                }
                break;
            //松手刷新
            case REFRESH_AFTER:
                if (mOnHeaderListener != null) {
                    mOnHeaderListener.onRefreshAfter(scrollY, mHeaderContent.getMeasuredHeight(), mHeader.getMeasuredHeight());
                }
                break;
            //准备刷新
            case REFRESH_READY:
                if (mOnHeaderListener != null) {
                    mOnHeaderListener.onRefreshReady(scrollY, mHeaderContent.getMeasuredHeight(), mHeader.getMeasuredHeight());
                }
                break;
            //刷新中
            case REFRESH_DOING:
                if (mOnHeaderListener != null) {
                    mOnHeaderListener.onRefreshing(scrollY, mHeaderContent.getMeasuredHeight(), mHeader.getMeasuredHeight());
                }
                if (mListener != null)
                    mListener.onRefresh();
                break;
            //刷新完成
            case REFRESH_COMPLETE:
                if (mOnHeaderListener != null) {
                }
                mOnHeaderListener.onRefreshComplete(scrollY, mHeaderContent.getMeasuredHeight(), mHeader.getMeasuredHeight(), mIsRefreshSuccess);
                break;
            //取消刷新
            case REFRESH_CANCEL:
                if (mOnHeaderListener != null) {
                    mOnHeaderListener.onRefreshCancel(scrollY, mHeaderContent.getMeasuredHeight(), mHeader.getMeasuredHeight());
                }
                break;
            //上拉加载更多
            case LOAD_BEFORE:
                if (mOnFooterListener != null) {
                    mOnFooterListener.onLoadBefore(scrollY);
                }
                break;
            //松手加载
            case LOAD_AFTER:
                if (mOnFooterListener != null) {
                    mOnFooterListener.onLoadAfter(scrollY);
                }
                break;
            //准备加载
            case LOAD_READY:
                if (mOnFooterListener != null) {
                    mOnFooterListener.onLoadReady(scrollY);
                }
                break;
            //加载中
            case LOAD_DOING:
                if (mOnFooterListener != null) {
                    mOnFooterListener.onLoading(scrollY);
                }
                if (mListener != null)
                    mListener.onLoadMore();
                break;
            //加载完成
            case LOAD_COMPLETE:
                if (mOnFooterListener != null) {
                    mOnFooterListener.onLoadComplete(scrollY, mIsLoadSuccess);
                }
                break;
            //取消加载
            case LOAD_CANCEL:
                if (mOnFooterListener != null) {
                    mOnFooterListener.onLoadCancel(scrollY);
                }
                break;
        }
    }

    /**
     * 默认状态
     */
    private void onDefault() {
        mIsRefreshSuccess = false;
        mIsLoadSuccess = false;
    }

    /**
     * 滚动到加载状态
     */
    private void scrolltoLoadStatus() {
        mIsLoading = true;
        int start = getScrollY();
        int end = mFooter.getMeasuredHeight() + mBottomScroll;
        performAnim(start, end, new AnimListener() {
            @Override
            public void onGoing() {
                updateStatus(mStatus.LOAD_READY);
            }

            @Override
            public void onEnd() {
                updateStatus(mStatus.LOAD_DOING);
            }
        });
    }

    /**
     * 滚动到刷新状态
     */
    private void scrolltoRefreshStatus() {
        mIsRefreshing = true;
        int start = getScrollY();
        int end = -mHeaderContent.getMeasuredHeight();
        performAnim(start, end, new AnimListener() {
            @Override
            public void onGoing() {
                updateStatus(mStatus.REFRESH_READY);
            }

            @Override
            public void onEnd() {
                updateStatus(mStatus.REFRESH_DOING);
            }
        });
    }

    /**
     * 滚动到默认状态
     *
     * @param startStatus
     */
    private void scrolltoDefaultStatus(final RefreshStatus startStatus) {
        int start = getScrollY();
        int end = 0;
        performAnim(start, end, new AnimListener() {
            @Override
            public void onGoing() {
                updateStatus(startStatus);
            }

            @Override
            public void onEnd() {
                updateStatus(mStatus.DEFAULT);
            }
        });
    }

    /**
     * 停止刷新
     *
     * @param isSuccess
     */
    public void stopRefresh(boolean isSuccess) {
        if (!mIsRefreshing) {
            return;
        }
        mIsRefreshSuccess = isSuccess;
        mIsRefreshing = false;
        scrolltoDefaultStatus(RefreshStatus.REFRESH_COMPLETE);
    }

    /**
     * 停止加载更多
     *
     * @param isSuccess
     */
    public void stopLoadMore(boolean isSuccess) {
        mIsLoadSuccess = isSuccess;
        mIsLoading = false;
        scrolltoDefaultStatus(RefreshStatus.LOAD_COMPLETE);
    }

    /**
     * 执行滑动
     *
     * @param dy
     */
    public void performScroll(int dy) {
        scrollBy(0, (int) (-dy * mDamp));
    }

    /**
     * 执行动画
     *
     * @param start
     * @param end
     * @param listener
     */
    private void performAnim(int start, int end, final AnimListener listener) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.setDuration(SCROLL_TIME).start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                scrollTo(0, value);
                postInvalidate();
                listener.onGoing();
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                listener.onEnd();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    interface AnimListener {
        void onGoing();

        void onEnd();
    }

}
