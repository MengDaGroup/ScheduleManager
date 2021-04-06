package com.dayi35.qx_base.http;

import io.reactivex.disposables.Disposable;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2020/7/2 16:59
 * 描    述: 类 数据回调类
 * 修订历史:
 * =========================================
 */
public abstract class RequestCallback<T> {
    public void onSubscribe(Disposable d){}
    public abstract void onSuccess(T entity);
    public abstract void onError(String err);
    public void onComplete(){}
}
