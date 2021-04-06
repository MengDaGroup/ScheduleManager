package com.dayi35.recycle.refrsh;

/**
 * * 检查是否可以加载更多接口
 * * 当使用过程中需要自己控制是否需要加载时可以实现这个回调接口
 * Created by yao on 2019/12/26.
 */

public interface OnCheckCanLoadMoreListener {
    boolean checkCanDoLoadMore();
}
