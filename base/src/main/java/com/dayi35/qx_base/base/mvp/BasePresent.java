package com.dayi35.qx_base.base.mvp;

import io.reactivex.disposables.Disposable;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2020/5/11 15:15
 * 描    述: 业务父类
 * 修订历史:
 * =========================================
 */
public interface BasePresent {
    //绑定数据
    void subscribe(Disposable subscription);
    //绑定数据
    void subscribe();
    //解除绑定
    void unSubscribe();
}
