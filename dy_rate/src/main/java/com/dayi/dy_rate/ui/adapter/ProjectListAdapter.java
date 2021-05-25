package com.dayi.dy_rate.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dayi.dy_rate.R;
import com.dayi.dy_rate.entity.ProjectEntity;
import com.dayi35.qx_utils.convert.FloatUtils;
import com.dayi35.qx_widget.labelview.LabelTextView;
import com.dayi35.qx_widget.progress.DonutProgress;
import com.dayi35.recycle.adapter.RecyclerArrayAdapter;
import com.dayi35.recycle.holder.BaseViewHolder;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2021/4/8 11:03
 * 描    述: 类        项目适配
 * 修订历史:
 * =========================================
 */
public class ProjectListAdapter extends RecyclerArrayAdapter<ProjectEntity> {

    public ProjectListAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ProjectHolder(parent, R.layout.rate_item_project);
    }

    private class ProjectHolder extends BaseViewHolder<ProjectEntity>{
        TextView mTvProjectName;            //项目名称
        DonutProgress mDpProgress;          //进度
        TextView mTvUpdater;                //更新者
        TextView mTvUpdateTime;             //更新时间
        TextView mTvBelong;                 //归属者
        LabelTextView mLtvState;            //状态
        TextView mTvOS;                     //端
        TextView mTvProject;                //项目
        public ProjectHolder(ViewGroup parent, int res) {
            super(parent, res);
            mTvProjectName = getView(R.id.rate_tv_project_name);
            mDpProgress = getView(R.id.rate_dp_progress);
            mTvUpdater = getView(R.id.rate_tv_updater);
            mTvUpdateTime = getView(R.id.rate_tv_update_time);
            mLtvState = getView(R.id.rate_ltv_item_state);
            mTvBelong = getView(R.id.rate_tv_belong);
            mTvOS = getView(R.id.rate_tv_os);
            mTvProject = getView(R.id.rate_tv_project);
        }

        @Override
        public void setData(ProjectEntity data) {
            super.setData(data);
            mTvProjectName.setText(data.getName());
            mTvUpdateTime.setText("更新于: " + data.getUpdatedAt());
            mTvProject.setText("项目");

            if (3 == data.getStatus()){
                //标签
                mLtvState.setLabelText("已结束");
                mLtvState.setLabelBackgroundColor(getContext().getResources().getColor(R.color.widget_color_cd5b55));
                //进度样式
                mDpProgress.setFinishedStrokeColor(getContext().getResources().getColor(R.color.widget_color_cd5b55));
                mDpProgress.setTextColor(getContext().getResources().getColor(R.color.widget_color_cd5b55));
            }else if (2 == data.getStatus()){
                //状态标签
                mLtvState.setLabelText("进行中");
                mLtvState.setLabelBackgroundColor(getContext().getResources().getColor(R.color.widget_color_a5dc86));
                //进度样式
                mDpProgress.setFinishedStrokeColor(getContext().getResources().getColor(R.color.widget_color_a5dc86));
                mDpProgress.setTextColor(getContext().getResources().getColor(R.color.widget_color_a5dc86));
            }else if (4 == data.getStatus()){
                //标签
                mLtvState.setLabelText("已逾期");
                mLtvState.setLabelBackgroundColor(getContext().getResources().getColor(R.color.widget_color_f8bb86));
                //进度样式
                mDpProgress.setFinishedStrokeColor(getContext().getResources().getColor(R.color.widget_color_f8bb86));
                mDpProgress.setTextColor(getContext().getResources().getColor(R.color.widget_color_f8bb86));
            }else if (1 == data.getStatus()){
                //标签
                mLtvState.setLabelText("未开始");
                mLtvState.setLabelBackgroundColor(getContext().getResources().getColor(R.color.widget_color_a5dc86));
                //进度样式
                mDpProgress.setFinishedStrokeColor(getContext().getResources().getColor(R.color.widget_color_a5dc86));
                mDpProgress.setTextColor(getContext().getResources().getColor(R.color.widget_color_a5dc86));
            }

            mTvOS.setText(data.getOs());
            mTvOS.setTextColor(getContext().getResources().getColor(R.color.widget_color_cd5b55));
            //设置进度
            mDpProgress.setProgress(FloatUtils.float2Point(data.getProgress()));
//            mDpProgress.setDonut_progress(FloatUtils.str2point(data.getProgress()));
        }
    }

}
