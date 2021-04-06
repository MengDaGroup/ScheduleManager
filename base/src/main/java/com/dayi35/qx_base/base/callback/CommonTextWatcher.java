package com.dayi35.qx_base.base.callback;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2020/6/29 15:10
 * 描    述: 类 自定义普通文字观察者
 * 修订历史:
 * =========================================
 */
public abstract class CommonTextWatcher implements TextWatcher {

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        afterChanged(s);
    }

    public abstract void afterChanged(Editable s);
}
