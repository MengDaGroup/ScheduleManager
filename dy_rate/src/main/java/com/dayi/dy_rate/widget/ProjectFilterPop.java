package com.dayi.dy_rate.widget;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dayi.dy_rate.R;
import com.dayi35.qx_base.beans.KeyValueEntity;
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
 * 创建日期: 2020/8/7 9:00
 * 描    述: 类 条件筛选弹窗
 * 修订历史:
 * =========================================
 */
public class ProjectFilterPop {

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
        public Builder create(View _anchor, int _popH) {
            //创建弹窗视图
            builder = new BasePop.Builder(contextWeakReference.get())
                    .create(_anchor)
                    .setView(R.layout.rate_pop_filter)
                    .setAnimation(BasePopView.ANIMATION.FOLD)
                    .setBackgroundDrawable(0x00000000)
                    .setOutsideTouchable(true)
                    .setWidthAndHeight(LinearLayout.LayoutParams.MATCH_PARENT, _popH);
            //背景弹窗
            bg_builder = new BasePop.Builder(contextWeakReference.get())
                    .create(_anchor)
                    .setView(new View(contextWeakReference.get()))
                    .setBackgroundDrawable(BasePop.bgColor)
                    .setWidthAndHeight(LinearLayout.LayoutParams.MATCH_PARENT,  -10000);
            return this;
        }

        public interface OnFilterItemClickedCallback{
            void onClick(String key, String value, int pos);
        }

        /**
         * 显示垂直列表弹窗
         * @param _vListData
         * @param _onVListClickListenner
         * @return
         */
        public Builder init(List<KeyValueEntity> _vListData,
                                    int counts,
                                    final OnFilterItemClickedCallback _onVListClickListenner) {

            // 获取弹窗视图
            View popView = builder.getView();
            RecyclerView vListRv = popView.findViewById(R.id.rate_rv_pop_filter);
            if (0 != counts){
                GridLayoutManager manager = new GridLayoutManager(contextWeakReference.get(), counts);
                vListRv.setLayoutManager(manager);
            }else {
                LinearLayoutManager manager = new LinearLayoutManager(contextWeakReference.get());
                vListRv.setLayoutManager(manager);
            }
            // 填充数据
            final List<KeyValueEntity> vListData = new ArrayList<>();
            vListData.addAll(_vListData);
            final BaseRecyclerAdapter<KeyValueEntity> mAdapter = new BaseRecyclerAdapter<KeyValueEntity>(vListData, R.layout.rate_item_pop_filter) {
                @Override
                protected void onBindViewHolder(SmartViewHolder holder, KeyValueEntity model, int position) {
                    holder.text(R.id.income_tv_userincome_popfilter_item, model.getKey());
                }
            };
            mAdapter.setOnItemClickListener((parent, view, position, id) -> {
                _onVListClickListenner.onClick(vListData.get(position).getKey(), vListData.get(position).getValue(), position);
                builder.dissmiss();
            });
            vListRv.setAdapter(mAdapter);
            builder.setOnDissmiss(() -> bg_builder.dissmiss());
            show();
            return this;
        }

        /**
         * 返回弹窗是否显示
         * @return
         */
        public boolean bIsShowing(){
            return builder.bIsShowing();
        }

        /**
         * 隐藏
         */
        public void dissmiss(){
            builder.dissmiss();
        }

        /**
         * 显示，在正下方显示
         */
        public void show(){
            if (null != builder && null != bg_builder){
                bg_builder.show(BasePopView.GRAVITY.LEFTTOP_TO_LEFTBOTTOM);
                builder.show(BasePopView.GRAVITY.LEFTTOP_TO_LEFTBOTTOM);
            }
        }
    }
}
