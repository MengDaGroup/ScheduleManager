package com.dayi.dy_rate.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dayi.dy_rate.R;
import com.dayi.dy_rate.entity.ComponentEntity;
import com.dayi35.qx_utils.common.DateUtil;
import com.dayi35.qx_utils.convert.FloatUtils;
import com.dayi35.qx_widget.labelview.LabelTextView;
import com.dayi35.qx_widget.progress.DonutProgress;
import com.dayi35.recycle.adapter.RecyclerArrayAdapter;
import com.dayi35.recycle.holder.BaseViewHolder;
import com.dayi35.recycle.swipe.OnSwipeMenuListener;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2021/5/17 17:19
 * 描    述: 类    功能适配器
 * 修订历史:
 * =========================================
 */
public class ComponentListAdapter extends RecyclerArrayAdapter<ComponentEntity> {
    public ComponentListAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ComponentHolder(parent, R.layout.rate_item_component);
    }

    private OnSwipeMenuListener listener;
    public void setOnSwipeMenuListener(OnSwipeMenuListener listener) {
        this.listener = listener;
    }

    private class ComponentHolder extends BaseViewHolder<ComponentEntity>{
        TextView mTvProjectName;            //项目名称
        DonutProgress mDpProgress;          //进度
        TextView mTvUpdater;                //更新者
        TextView mTvUpdateTime;             //更新时间
        TextView mTvBelong;                 //归属者
        LabelTextView mLtvState;            //状态
        TextView mTvOS;                     //端
        TextView mTvComponent;              //功能
        Button mBtnDelete;                  //删除
        RelativeLayout mRlContent;          //内容
        public ComponentHolder(ViewGroup parent, int res) {
            super(parent, res);
            mTvProjectName = getView(R.id.rate_tv_project_name);
            mDpProgress = getView(R.id.rate_dp_progress);
            mTvUpdater = getView(R.id.rate_tv_updater);
            mTvUpdateTime = getView(R.id.rate_tv_update_time);
            mLtvState = getView(R.id.rate_ltv_item_state);
            mTvBelong = getView(R.id.rate_tv_belong);
            mTvOS = getView(R.id.rate_tv_os);
            mTvComponent = getView(R.id.rate_tv_component);
            mBtnDelete = getView(R.id.rate_btn_delete);
            mRlContent = getView(R.id.rate_rl_item_content);
        }

        @Override
        public void setData(ComponentEntity data) {
            super.setData(data);
            mTvProjectName.setText(data.getName());
//            mTvUpdater.setText("更新者: " + data.getUpdateUser());
            mTvUpdateTime.setText("更新于: " + data.getUpdatedAt());
            mTvComponent.setText("功能");
//            mTvBelong.setText("归属于:" + data.getProjectBelong());
            //计算时间差
            long times = DateUtil.dateToLong(data.getEndTime(), DateUtil.mFormat_date_24) - System.currentTimeMillis();
            if (100 <= Float.valueOf(data.getProgress())){
                //标签
                mLtvState.setLabelText("已完成");
                mLtvState.setLabelBackgroundColor(getContext().getResources().getColor(R.color.widget_color_cd5b55));
                //进度样式
                mDpProgress.setFinishedStrokeColor(getContext().getResources().getColor(R.color.widget_color_cd5b55));
                mDpProgress.setTextColor(getContext().getResources().getColor(R.color.widget_color_cd5b55));
            }else if (times > 0){
                //状态标签
                mLtvState.setLabelText("进行中");
                mLtvState.setLabelBackgroundColor(getContext().getResources().getColor(R.color.widget_color_a5dc86));
                //进度样式
                mDpProgress.setFinishedStrokeColor(getContext().getResources().getColor(R.color.widget_color_a5dc86));
                mDpProgress.setTextColor(getContext().getResources().getColor(R.color.widget_color_a5dc86));
            }else {
                //标签
                mLtvState.setLabelText("已逾期");
                mLtvState.setLabelBackgroundColor(getContext().getResources().getColor(R.color.widget_color_f8bb86));
                //进度样式
                mDpProgress.setFinishedStrokeColor(getContext().getResources().getColor(R.color.widget_color_f8bb86));
                mDpProgress.setTextColor(getContext().getResources().getColor(R.color.widget_color_f8bb86));
            }
            //设置进度
            mDpProgress.setProgress(FloatUtils.float2Point(data.getProgress()));
//            mDpProgress.setDonut_progress(FloatUtils.str2point(data.getProgress()));
            mBtnDelete.setOnClickListener(v -> {
                if (null != listener){
                    listener.toDelete(getAdapterPosition());
                }
            });

            mRlContent.setOnClickListener(v -> {
                if (null != listener){
                    listener.toTop(getAdapterPosition());
                }
            });
        }
    }
}
