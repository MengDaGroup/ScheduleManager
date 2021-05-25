package com.dayi.dy_rate.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.dayi.dy_rate.R;
import com.dayi.dy_rate.R2;
import com.dayi.dy_rate.contract.UserContract;
import com.dayi.dy_rate.entity.ModuleEntity;
import com.dayi.dy_rate.entity.ProjectDetailEntity;
import com.dayi.dy_rate.entity.RateBaserPagerEntity;
import com.dayi.dy_rate.presenter.ProjectDetailPresenter;
import com.dayi.dy_rate.ui.adapter.ModuleListAdapter;
import com.dayi35.qx_base.arouter.ARouterHelper;
import com.dayi35.qx_base.arouter.ARouterPath;
import com.dayi35.qx_base.base.state.BaseStateActivity;
import com.dayi35.qx_base.beans.EventBusEntity;
import com.dayi35.qx_base.constant.Constant;
import com.dayi35.qx_base.utils.EventBusHelper;
import com.dayi35.qx_base.utils.GsonHelper;
import com.dayi35.qx_utils.common.DateUtil;
import com.dayi35.qx_utils.common.StatusBarUtil;
import com.dayi35.qx_utils.common.ToastUtils;
import com.dayi35.qx_utils.convert.FloatUtils;
import com.dayi35.qx_utils.convert.RCaster;
import com.dayi35.qx_widget.labelview.LabelTextView;
import com.dayi35.qx_widget.progress.DonutProgress;
import com.dayi35.qx_widget.titlebar.TitleBar;
import com.dayi35.recycle.inter.OnLoadMoreListener;
import com.dayi35.recycle.view.DYRefreshView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2021/4/8 11:06
 * 描    述: 类 项目详情--组件列表页（模块列表）
 * 修订历史:
 * =========================================
 */
@Route(path = ARouterPath.Rate.ProjectDetail.PATH)
public class ProjectDetailActivity extends BaseStateActivity<ProjectDetailPresenter> implements UserContract.ProjectDetailContract.View {
    @BindView(R2.id.rate_tb_projectteam_edit) TitleBar mTbTitle;                          //title
    @BindView(R2.id.rate_dp_progress) DonutProgress mDpProgress;                //进度
    @BindView(R2.id.rate_tv_project_name) TextView mTvProjectName;              //项目名称
    @BindView(R2.id.rate_tv_preview_os) TextView mTvOs;                         //端
    @BindView(R2.id.rate_tv_belong) TextView mTvBelong;                         //归属
    @BindView(R2.id.rate_tv_time_length) TextView mTvTimeLength;                //时长
    @BindView(R2.id.rate_tv_update_time) TextView mTvUpdateTime;                //最后更新时间
    @BindView(R2.id.rate_ltv_preview_state) LabelTextView mLtvState;            //状态
    @BindView(R2.id.rate_rv_project) DYRefreshView mRvProject;                   //列表
    @BindView(R2.id.rate_tv_remark) TextView mTvRemark;                         //备注

    @Autowired(name = ARouterPath.Rate.ProjectDetail.Params.PROJECTID)
    String mProjectId;

    private ModuleListAdapter mAdapter;            //适配器
    private List<ModuleEntity> mListMoudle;     //组件数据
    private ProjectDetailEntity mProjectEntity; //项目实体数据

    private int pageNo = 1;

    @Override
    protected int getMainLayoutId() {
        return R.layout.rate_activity_projectdetail;
    }

    @Override
    protected void initStateLayout() {
        getStateManager();
    }

    @Override
    protected ProjectDetailPresenter createPresent() {
        return new ProjectDetailPresenter(this);
    }

    @Override
    protected RCaster createCaster() {
        return new RCaster(R.class, R2.class);
    }

    @Override
    protected void initView() {
        StatusBarUtil.transparent(this);
        StatusBarUtil.setDarkMode(this);
        EventBusHelper.register(this);
        mRvProject.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void initData() {

        mAdapter = new ModuleListAdapter(this);
        mRvProject.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(position -> {
            Bundle bundle = new Bundle();
            bundle.putString(ARouterPath.Rate.ModuleDetail.Params.MODULEID, mAdapter.getAllData().get(position).getId());
            bundle.putString(ARouterPath.Rate.ModuleDetail.Params.PROJECTID, mAdapter.getAllData().get(position).getProjectId());
            ARouterHelper.navigation(ARouterPath.Rate.ModuleDetail.PATH, bundle);
        });
        //刷新
        mRvProject.setRefreshListener(() -> refresh());
        //加载
        mAdapter.setMore(R.layout.rv_footer, () -> load());

    }

    @Override
    protected void onResume() {
        super.onResume();
        getP().projectConsult(mProjectId);
    }

    /**
     * 更新card数据
     * @param mProjectEntity
     */
    private void updateView(ProjectDetailEntity mProjectEntity) {
        //端
        if (1 == mProjectEntity.getOs()){
            mTvOs.setText("安卓");
        }else {
            mTvOs.setText("苹果");
        }
        //项目名称
        mTvProjectName.setText(mProjectEntity.getName());
        mTvRemark.setText(TextUtils.isEmpty(mProjectEntity.getRemark()) ? "暂无描述" : mProjectEntity.getRemark());
        //归属
//        mTvBelong.setText(mProjectEntity.getBelongId());
        //时长
        mTvTimeLength.setText("期限：" + mProjectEntity.getStartTime() + "至" + mProjectEntity.getEndTime());
        //更新时间
        mTvUpdateTime.setText("最近更新:" + mProjectEntity.getUpdatedAt());
        //设置进度
        mDpProgress.setProgress(FloatUtils.float2Point(mProjectEntity.getProgress()));
        mTbTitle.setCenterText(mProjectEntity.getName());
//        mDpProgress.setDonut_progress(FloatUtils.str2point(mProjectEntity.getProgress()));
        //计算时间差
        long times = DateUtil.dateToLong(mProjectEntity.getEndTime(), DateUtil.mFormat_date_24) - System.currentTimeMillis();
        //先判断进度是否为100  100为项目已完成
        if (3 == mProjectEntity.getStatus()){
            mLtvState.setLabelText("已结束");
            mLtvState.setLabelBackgroundColor(getResources().getColor(R.color.widget_color_cd5b55));
        }else if (2 == mProjectEntity.getStatus()){       //项目进度不为100 ，判断是否在期限内
            mLtvState.setLabelBackgroundColor(getResources().getColor(R.color.widget_color_a5dc86));
            int day = (int) (times / (24 * 60 * 60 * 1000));
            if (day == 0) {
                mLtvState.setLabelText("< 1 天");
            } else {
                mLtvState.setLabelText(day + "天");
            }
        }else if (1 == mProjectEntity.getStatus()){
            mLtvState.setLabelBackgroundColor(getResources().getColor(R.color.widget_color_a5dc86));
            mLtvState.setLabelText("未开始");
        }else {
            mLtvState.setLabelText("已逾期");
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
        if (null != mProjectEntity) {
            Bundle bundle = new Bundle();
            bundle.putString(ARouterPath.Rate.ModuleUpdate.Params.PROJECTID, mProjectEntity.getId());
            bundle.putString(ARouterPath.Rate.ModuleUpdate.Params.STARTTIME, mProjectEntity.getStartTime());
            bundle.putString(ARouterPath.Rate.ModuleUpdate.Params.ENDTIME, mProjectEntity.getEndTime());
            ARouterHelper.navigation(ARouterPath.Rate.ModuleUpdate.PATH, bundle);
        }else {
            ToastUtils.showShort("项目数据获取失败");
        }
    }

    /**
     * 获取到项目详情
     * @param entity
     */
    @Override
    public void onProjectConsult(ProjectDetailEntity entity) {
        //刷新列表数据
        refresh();
        if (null != entity) {
            mProjectEntity = entity;
            //更新界面数据
            updateView(entity);
        }
    }

    private void refresh(){
        pageNo = 1;
        load();
    }

    private void load(){
        getP().moudleGetList("", mProjectId, -1, pageNo);
    }

    /**
     * 组件列表
     * @param entity
     */
    @Override
    public void onGetMoudleList(RateBaserPagerEntity<ModuleEntity> entity) {
        if (pageNo == 1){//刷新
            mRvProject.setRefreshing(false);
            mAdapter.clear();
        }
        if (pageNo == entity.getPager().getTotalPage()){
            mAdapter.setNoMore(R.layout.rv_layout_common_nomore);
            mAdapter.stopMore();
        }
        pageNo = pageNo + 1;
        mAdapter.addAll(entity.getList());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventSubcriber(EventBusEntity entity) {
        switch (entity.getEventCode()){
            case Constant.CodeEvent.RATE_MODULEUPDATE:
//                refresh();
                break;
                default:
                    break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusHelper.unRegister(this);
    }
}
