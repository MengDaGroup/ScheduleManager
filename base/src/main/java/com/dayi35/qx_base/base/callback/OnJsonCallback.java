package com.dayi35.qx_base.base.callback;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2021/3/31 9:37
 * 描    述: 类    json回调
 * 修订历史:
 * =========================================
 */
public interface OnJsonCallback {
    void onSuccess(String json);
    void onFailed();
}
