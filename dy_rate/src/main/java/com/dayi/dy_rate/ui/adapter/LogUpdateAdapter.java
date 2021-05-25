package com.dayi.dy_rate.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dayi.dy_rate.R;
import com.dayi.dy_rate.entity.LogUpdateEntity;
import com.dayi35.recycle.adapter.RecyclerArrayAdapter;
import com.dayi35.recycle.holder.BaseViewHolder;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2021/5/19 10:26
 * 描    述: 类    更新日志适配器
 * 修订历史:
 * =========================================
 */
public class LogUpdateAdapter extends RecyclerArrayAdapter<LogUpdateEntity> {
    public LogUpdateAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new LogHolder(parent, R.layout.rate_item_update_notes);
    }

    private class LogHolder extends BaseViewHolder<LogUpdateEntity>{
        private TextView mTvNote;               //日志详情
        private TextView mTvTime;               //更新时间
        private TextView mTvUpdater;            //更新者
        public LogHolder(ViewGroup parent, int res) {
            super(parent, res);
            mTvNote = getView(R.id.rate_tv_update_notes);
            mTvTime = getView(R.id.rate_tv_update_time);
            mTvUpdater = getView(R.id.rate_tv_update_user);
        }

        @Override
        public void setData(LogUpdateEntity data) {
            super.setData(data);
            mTvNote.setText(data.getContent());
            mTvTime.setText(data.getCreatedAt());
            mTvUpdater.setText(data.getUser());
        }
    }
}
