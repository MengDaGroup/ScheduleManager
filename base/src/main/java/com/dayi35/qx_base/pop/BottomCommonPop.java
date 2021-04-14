package com.dayi35.qx_base.pop;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dayi35.qx_base.R;
import com.dayi35.qx_base.beans.BottomPopEntity;
import com.dayi35.qx_widget.pop.BasePop;
import com.dayi35.qx_widget.pop.BasePopView;
import com.dayi35.qx_widget.pop.OnEventListenner;
import com.dayi35.recycle.adapter.BaseRecyclerAdapter;
import com.dayi35.recycle.adapter.SmartViewHolder;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2020/12/8 10:43
 * 描    述: 类 底部弹窗
 * 修订历史:
 * =========================================
 */
public class BottomCommonPop {

    public static class Builder {
        private WeakReference<Context> contextWeakReference;
        private BasePop.Builder builder = null;
        private BasePop.Builder bg_builder = null;

        public Builder(Context _context) {
            this.contextWeakReference = new WeakReference<>(_context);
        }

        /**
         * 垂直列表弹窗显示
         * @param _anchor
         * @return
         */
        public Builder create(View _anchor) {
            //创建弹窗视图
            builder = new BasePop.Builder(contextWeakReference.get())
                    .create(_anchor)
                    .setView(R.layout.base_pop_bottomcommon)
                    .setAnimation(BasePopView.ANIMATION.TRANSLATE)
                    .setBackgroundDrawable(0x00000000)
                    .setOutsideTouchable(true)
                    .setWidthAndHeight(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            //背景弹窗
            bg_builder = new BasePop.Builder(contextWeakReference.get())
                    .create(_anchor)
                    .setView(new View(contextWeakReference.get()))
                    .setBackgroundDrawable(0x80000000)
                    .setWidthAndHeight(LinearLayout.LayoutParams.MATCH_PARENT,  10000);
            return this;
        }

        /**
         * 初始化弹窗信息
         * @param entities
         * @param _listenner
         * @return
         */
        public Builder init(List<BottomPopEntity> entities, OnEventListenner.OnVListClickListenner _listenner){
            if (null == entities){
                entities = new ArrayList<>();
            }
            // 获取弹窗视图
            View popView = builder.getView();
            Button btnCancel = popView.findViewById(R.id.common_btn_bottomcommon_cancel);
            RecyclerView rvClumn = popView.findViewById(R.id.common_rv_bottomcommon_column);
            rvClumn.setLayoutManager(new LinearLayoutManager(contextWeakReference.get()));
            BaseRecyclerAdapter<BottomPopEntity> clumnAdapter = new BaseRecyclerAdapter<BottomPopEntity>(entities, R.layout.base_item_pop_bottomcommon, (parent, view, position, id) -> {
                if (null != _listenner){
                    builder.dissmiss();
                    _listenner.onClick("", position);
                }
            }) {
                @Override
                protected void onBindViewHolder(SmartViewHolder holder, BottomPopEntity model, int position) {
                    holder.text(R.id.common_tv_item_bottomcommon_columnname, model.getColumnName());
                }
            };
            rvClumn.setAdapter(clumnAdapter);

            /// 背景弹窗
            bg_builder.show(BasePopView.GRAVITY.RIGHTTOP_TO_RIGHTBOTTOM);
            builder.setOnDissmiss(() -> bg_builder.dissmiss());

            btnCancel.setOnClickListener(v -> builder.dissmiss());
            return this;
        }

        /**
         * 显示，从正下方显示
         */
        public void show(){
            if (null != builder && null != bg_builder){
                bg_builder.show(BasePopView.GRAVITY.RIGHTTOP_TO_RIGHTBOTTOM);
                builder.show(BasePopView.SIMPLE_GRAVITY.FROM_BOTTOM);
            }
        }

    }

}
