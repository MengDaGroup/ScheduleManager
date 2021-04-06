package com.dayi35.qx_widget.bannerview;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

/**
 * created by yao on 2020/12/17
 * Describe:
 **/
public interface VPViewHolder<T> {

    /**
     * 创建view
     */
    View createView(Context context);

    /**
     * 绑定数据
     *
     * @param context
     * @param position
     * @param data
     */
    void onBind(Context context, int position, T data);


}
