package com.dayi.dy_rate.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.dayi.dy_rate.R;
import com.dayi.dy_rate.R2;
import com.dayi.dy_rate.contract.UserContract;
import com.dayi.dy_rate.entity.ComponentDetialEntity;
import com.dayi.dy_rate.entity.LogUpdateEntity;
import com.dayi.dy_rate.entity.ProjectEntity;
import com.dayi.dy_rate.entity.RateBaserPagerEntity;
import com.dayi.dy_rate.presenter.ComponentDetailPresenter;
import com.dayi.dy_rate.ui.adapter.LogUpdateAdapter;
import com.dayi35.qx_base.arouter.ARouterHelper;
import com.dayi35.qx_base.arouter.ARouterPath;
import com.dayi35.qx_base.base.state.BaseStateActivity;
import com.dayi35.qx_base.beans.EventBusEntity;
import com.dayi35.qx_base.constant.Constant;
import com.dayi35.qx_base.utils.EventBusHelper;
import com.dayi35.qx_utils.common.DateUtil;
import com.dayi35.qx_utils.common.StatusBarUtil;
import com.dayi35.qx_utils.common.ToastUtils;
import com.dayi35.qx_utils.convert.FloatUtils;
import com.dayi35.qx_utils.convert.RCaster;
import com.dayi35.qx_widget.labelview.LabelTextView;
import com.dayi35.qx_widget.progress.DonutProgress;
import com.dayi35.qx_widget.titlebar.TitleBar;
import com.dayi35.recycle.adapter.BaseRecyclerAdapter;
import com.dayi35.recycle.adapter.SmartViewHolder;
import com.dayi35.recycle.inter.OnLoadMoreListener;
import com.dayi35.recycle.view.DYRefreshView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
@Route(path = ARouterPath.Rate.ComponentDetail.PATH)
public class ComponentDetailActivity extends BaseStateActivity<ComponentDetailPresenter> implements UserContract.ComponentDetailContract.View {
    @BindView(R2.id.rate_tb_project_preview) TitleBar mTbTitle;                 //title
    @BindView(R2.id.rate_dp_progress) DonutProgress mDpProgress;                //进度
    @BindView(R2.id.rate_tv_project_name) TextView mTvProjectName;              //项目名称
    @BindView(R2.id.rate_tv_preview_os) TextView mTvOs;                         //端
    @BindView(R2.id.rate_tv_belong) TextView mTvBelong;                         //归属
    @BindView(R2.id.rate_tv_time_length) TextView mTvTimeLength;                //时长
    @BindView(R2.id.rate_tv_update_time) TextView mTvUpdateTime;                //最后更新时间
    @BindView(R2.id.rate_ltv_preview_state) LabelTextView mLtvState;            //状态
    @BindView(R2.id.rate_rv_notes) DYRefreshView mRvNotes;                       //更新日志
    @BindView(R2.id.rate_tv_remark) TextView mTvRemark;                         //备注

    @Autowired(name = ARouterPath.Rate.ComponentDetail.Params.COMPONENTID)
    String mComponentId;                        //功能ＩＤ

    private ComponentDetialEntity componentDetialEntity;
    private int pageNo = 1;                     //页码
    private LogUpdateAdapter mLogAdapter;       //日志适配器

    @Override
    protected int getMainLayoutId() {
        return R.layout.rate_activity_compont_detail;
    }

    @Override
    protected void initStateLayout() {
        getStateManager();
    }

    @Override
    protected ComponentDetailPresenter createPresent() {
        return new ComponentDetailPresenter(this);
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
        mRvNotes.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void initData() {
        initAdapter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getP().componentGetDetail(mComponentId);
    }

    /**
     * 初始化notes适配器
     */
    private void initAdapter() {
        mLogAdapter = new LogUpdateAdapter(this);
        mRvNotes.setAdapter(mLogAdapter);
        mLogAdapter.setMore(R.layout.rv_footer, () -> load());
        mRvNotes.setRefreshListener(() -> refresh());
    }

    /**
     * 根据数据实体attach界面数据
     * @param mProjectEntity
     */
    private void updateView(ComponentDetialEntity mProjectEntity) {
        mTbTitle.setCenterText(mProjectEntity.getName());
        mTvOs.setText(mProjectEntity.getModuleName());
        mTvRemark.setText(TextUtils.isEmpty(mProjectEntity.getRemark()) ? "暂无描述" : mProjectEntity.getRemark());
        //项目名称
        mTvProjectName.setText(mProjectEntity.getName());
        //时长
        mTvTimeLength.setText("期限：" + DateUtil.getTimestamp(mProjectEntity.getStartTime(), DateUtil.mFormat_12, DateUtil.mFormat_date_24) + "至" + DateUtil.getTimestamp(mProjectEntity.getEndTime(), DateUtil.mFormat_12, DateUtil.mFormat_date_24));
        //更新时间
        mTvUpdateTime.setText("于" + mProjectEntity.getUpdatedAt() + "有更新");
        //设置进度
        mDpProgress.setProgress(FloatUtils.float2Point(mProjectEntity.getProgress()));
//        mDpProgress.setDonut_progress(FloatUtils.str2point(mProjectEntity.getProgress()));
        //计算时间差
        long times = DateUtil.dateToLong(mProjectEntity.getEndTime(), DateUtil.mFormat_date_24) - System.currentTimeMillis();
        //先判断进度是否为100  100为项目已完成
        if ("100".equals(mProjectEntity.getProgress())){
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
        if (null != componentDetialEntity) {
            Bundle bundle = new Bundle();
            bundle.putString(ARouterPath.Rate.ComponentUpdate.Params.PROJECTID, componentDetialEntity.getProjectId());
            bundle.putString(ARouterPath.Rate.ComponentUpdate.Params.MODULEID, componentDetialEntity.getModuleId());
            bundle.putString(ARouterPath.Rate.ComponentUpdate.Params.COMPONENTID, componentDetialEntity.getId());
            bundle.putFloat(ARouterPath.Rate.ComponentUpdate.Params.COMPONENTPROGRESS, componentDetialEntity.getProgress());
            bundle.putString(ARouterPath.Rate.ComponentUpdate.Params.COMPONENTNAME, componentDetialEntity.getName());
            bundle.putString(ARouterPath.Rate.ComponentUpdate.Params.STARTTIME, componentDetialEntity.getStartTime());
            bundle.putString(ARouterPath.Rate.ComponentUpdate.Params.ENDTIME, componentDetialEntity.getEndTime());
            ARouterHelper.navigation(ARouterPath.Rate.ComponentUpdate.PATH, bundle);
        }else {
            ToastUtils.showShort("获取信息失败, 请重新获取");
        }
    }

    /**
     * @param entity    功能详情实体类 ---> 获取到功能详情
     */
    @Override
    public void onGetComponentDetail(ComponentDetialEntity entity) {
        componentDetialEntity = entity;
        updateView(entity);
        refresh();
    }

    /**
     *
     * @param entity    日志列表实体      --->    获取到功能更新日志
     */
    @Override
    public void onGetLogList(RateBaserPagerEntity<LogUpdateEntity> entity) {
        if (pageNo == 1){//刷新
            mRvNotes.setRefreshing(false);
            mLogAdapter.clear();
        }
        if (pageNo == entity.getPager().getTotalPage()){
            mLogAdapter.setNoMore(R.layout.rv_layout_common_nomore);
            mLogAdapter.stopMore();
        }
        pageNo = pageNo + 1;
        mLogAdapter.addAll(entity.getList());
    }

    /**
     * 刷新
     */
    private void refresh(){
        pageNo = 1;
        load();
    }

    /**
     * 加载
     */
    private void load() {
        getP().logGetList(pageNo,mComponentId);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventSubcriber(EventBusEntity entity) {
        switch (entity.getEventCode()){
            case Constant.CodeEvent.RATE_PROGRESSUPDATE:
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
