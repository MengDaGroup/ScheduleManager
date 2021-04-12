package com.dayi.dy_rate.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dayi.dy_rate.R;
import com.dayi.dy_rate.R2;
import com.dayi.dy_rate.contract.UserContract;
import com.dayi.dy_rate.entity.ProjectEntity;
import com.dayi.dy_rate.presenter.ProjectPresenter;
import com.dayi35.qx_base.base.state.BaseStateActivity;
import com.dayi35.qx_base.utils.GsonHelper;
import com.dayi35.qx_utils.common.DateUtil;
import com.dayi35.qx_utils.common.StatusBarUtil;
import com.dayi35.qx_utils.common.ToastUtils;
import com.dayi35.qx_utils.convert.RCaster;
import com.dayi35.qx_widget.labelview.LabelTextView;
import com.dayi35.qx_widget.progress.DonutProgress;
import com.dayi35.qx_widget.titlebar.TitleBar;
import com.dayi35.recycle.adapter.BaseRecyclerAdapter;
import com.dayi35.recycle.adapter.SmartViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2021/4/9 15:33
 * 描    述: 类
 * 修订历史:
 * =========================================
 */
public class ProjectPreviewActivity extends BaseStateActivity<ProjectPresenter> implements UserContract.ProjectContract.View {

    @BindView(R2.id.rate_tb_project_preview) TitleBar mTbTitle;                 //title
    @BindView(R2.id.rate_dp_progress) DonutProgress mDpProgress;                //进度
    @BindView(R2.id.rate_tv_project_name) TextView mTvProjectName;              //项目名称
    @BindView(R2.id.rate_tv_preview_os) TextView mTvOs;                         //端
    @BindView(R2.id.rate_tv_belong) TextView mTvBelong;                         //归属
    @BindView(R2.id.rate_tv_time_length) TextView mTvTimeLength;                //时长
    @BindView(R2.id.rate_tv_update_time) TextView mTvUpdateTime;                //最后更新时间
    @BindView(R2.id.rate_ltv_preview_state) LabelTextView mLtvState;            //状态
    @BindView(R2.id.rate_rv_notes) RecyclerView mRvNotes;                       //更新日志

    private String mProjectJson;                //项目实体json
    private ProjectEntity mProjectEntity;       //项目实体
    private List<String> mListNotes;            //更新日志
    private BaseRecyclerAdapter<String> mNotesAdapter;  //notes适配器

    @Override
    protected int getMainLayoutId() {
        return R.layout.rate_activity_project_preview;
    }

    @Override
    protected void initStateLayout() {
        getStateManager();
    }

    @Override
    protected ProjectPresenter createPresent() {
        return new ProjectPresenter(this);
    }

    @Override
    protected RCaster createCaster() {
        return new RCaster(R.class, R2.class);
    }

    @Override
    protected void initView() {
        StatusBarUtil.transparent(this);
        StatusBarUtil.setDarkMode(this);
        mRvNotes.setLayoutManager(new LinearLayoutManager(this));
        showLoading();
    }

    @Override
    protected void initData() {
        //得到界面传值
        mProjectJson = getIntent().getStringExtra("project");
        mListNotes = new ArrayList<>();
        if (!TextUtils.isEmpty(mProjectJson)){
            //解析成实体
            mProjectEntity = GsonHelper.toType(mProjectJson, ProjectEntity.class);
        }
        if (null != mProjectEntity){
            //attach界面数据
            updateView(mProjectEntity);
            //获取notes集合
            if (null != mProjectEntity.getProjectNote()) {  //已有更新
                mListNotes.addAll(mProjectEntity.getProjectNote());
            }
        }else {
            ToastUtils.showShort("数据加载失败");
        }
        hideLoading();
        initAdapter();
    }

    /**
     * 初始化notes适配器
     */
    private void initAdapter() {
        mNotesAdapter = new BaseRecyclerAdapter<String>(mListNotes, R.layout.rate_item_update_notes) {
            @Override
            protected void onBindViewHolder(SmartViewHolder holder, String model, int position) {
                holder.text(R.id.rate_tv_update_notes, model);
            }
        };
        mRvNotes.setAdapter(mNotesAdapter);
    }

    /**
     * 根据数据实体attach界面数据
     * @param mProjectEntity
     */
    private void updateView(ProjectEntity mProjectEntity) {
        mTbTitle.setCenterText(mProjectEntity.getProjectName());
        //端
        if ("1".equals(mProjectEntity.getProjectOS())){
            mTvOs.setText("Android");
        }else {
            mTvOs.setText("IOS");
        }
        //项目名称
        mTvProjectName.setText(mProjectEntity.getProjectName());
        //归属
        mTvBelong.setText(mProjectEntity.getProjectBelong());
        //时长
        mTvTimeLength.setText("期限：" + mProjectEntity.getProjectStartTime() + "至" + mProjectEntity.getProjectEndTime());
        //更新时间
        mTvUpdateTime.setText(mProjectEntity.getUpdateUser() + "于" + mProjectEntity.getUpdateTime() + "提交更新");
        //设置进度
        mDpProgress.setProgress(Integer.valueOf(mProjectEntity.getProjectProgress()));
        mDpProgress.setDonut_progress(mProjectEntity.getProjectProgress());
        //计算时间差
        long times = DateUtil.dateToLong(mProjectEntity.getProjectEndTime(), DateUtil.mFormat_date_24) - System.currentTimeMillis();
        //先判断进度是否为100  100为项目已完成
        if ("100".equals(mProjectEntity.getProjectProgress())){
            mLtvState.setLabelText("已结束");
            mLtvState.setLabelBackgroundColor(getResources().getColor(R.color.widget_color_cd5b55));
        }else if (times > 0){       //项目进度不为100 ，判断是否在期限内
            mLtvState.setLabelBackgroundColor(getResources().getColor(R.color.widget_color_a5dc86));
            int day = (int) (times/(24*60*60*1000));
            if (day == 0){
                mLtvState.setLabelText("< 1 天");
            }else {
                mLtvState.setLabelText(day + "天");
            }
        }else {
            mLtvState.setLabelText("已延期");
            mLtvState.setLabelBackgroundColor(getResources().getColor(R.color.widget_color_f8bb86));
        }
    }

    @Override
    protected TitleBar titleBar() {
        return mTbTitle;
    }

    @Override
    protected void onRightClick() {
        super.onRightClick();
        Intent intent = new Intent(ProjectPreviewActivity.this, EditProjectActivity.class);
        intent.putExtra("type", 2);
        intent.putExtra("project_team_id", mProjectEntity.getProjectTeamId());
        intent.putExtra("project", mProjectJson);
        startActivity(intent);
    }

    /**
     *
     * @param type          1.新增成功      2.修改成功      3.查询成功
     * @param entity
     */
    @Override
    public void onDoSuccess(int type, List<ProjectEntity> entity) {

    }

    /**
     *
     * @param type  失败来源：1. 新增来源         2.修改来源      3.查询来源
     * @param msg
     */
    @Override
    public void onFailed(int type, String msg) {

    }
}
