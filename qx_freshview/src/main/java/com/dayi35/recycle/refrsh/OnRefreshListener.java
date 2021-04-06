package com.dayi35.recycle.refrsh;

/**
 * 下拉刷新 加载更多接口
 * Created by yao on 2019/12/26.
 */

public interface OnRefreshListener {

    /**
     * 刷新回调
     */
    void onRefresh();

    /**
     * 加载更多回调
     */
    void onLoadMore();
}
