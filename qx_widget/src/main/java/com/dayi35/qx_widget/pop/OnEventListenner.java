package com.dayi35.qx_widget.pop;

import android.view.View;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2020/8/7 9:00
 * 描    述: 类 pop的点击回调事件
 * 修订历史:
 * =========================================
 */
public class OnEventListenner {
    /**
     * 自定义点击事件回调
     */
    public interface OnBaseClickListenner{
        void onClick(View view);
    }
    /**
     * 弹窗消失回调
     */
    public interface OnBaseListenner{
        void onDissmiss();
    }
    /**
     * 垂直列表弹窗条目点击事件
     */
    public interface OnVListClickListenner{
        void onClick(String value, int pos);
    }

    /**
     * 商品管理中的点击回调
     */
    public interface OnShopProductSelectCallback{
        /**
         *
         * @param type  类型（String）
         */
        void onTypeClicked(String type);
    }

    /**
     * 分享弹窗里的点击事件
     */
    public interface OnShareCommonWayCallback{
        void wayCopy();
        void wayWechat();
        void wayPoster();
    }
}
