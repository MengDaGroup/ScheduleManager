package com.dayi35.qx_base.base.mvp;

import io.reactivex.disposables.Disposable;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2021/3/31 16:29
 * 描    述: 类    presenter实现基类
 * 修订历史:
 * =========================================
 */
public class BasePresentImpl<V extends BaseView> implements BasePresent {
    protected V mView;

    public BasePresentImpl(V mView) {
        this.mView = mView;
    }

    @Override
    public void subscribe(Disposable subscription) {

    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        mView = null;
    }
}
