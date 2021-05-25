package com.dayi.dy_rate.ui.activity;

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
import com.dayi.dy_rate.entity.ComponentEntity;
import com.dayi.dy_rate.entity.ModuleDetailEntity;
import com.dayi.dy_rate.entity.ProjectDetailEntity;
import com.dayi.dy_rate.entity.RateBaserPagerEntity;
import com.dayi.dy_rate.presenter.ModuleDetailPresenter;
import com.dayi.dy_rate.ui.adapter.ComponentListAdapter;
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
import com.dayi35.recycle.inter.OnItemClickListener;
import com.dayi35.recycle.inter.OnLoadMoreListener;
import com.dayi35.recycle.view.DYRefreshView;


import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2021/5/14 10:51
 * 描    述: 类 组件（模块）详情
 * 修订历史:
 * =========================================
 */
@Route(path = ARouterPath.Rate.ModuleDetail.PATH)
public class ModuleDetailActivity extends BaseStateActivity<ModuleDetailPresenter> implements UserContract.ModuleDetailContract.View {
    @BindView(R2.id.rate_tb_module_detail) TitleBar mTbTitle;                   //title
    @BindView(R2.id.rate_dp_progress) DonutProgress mDpProgress;                //进度
    @BindView(R2.id.rate_tv_project_name) TextView mTvProjectName;              //项目名称
    @BindView(R2.id.rate_tv_preview_os) TextView mTvOs;                         //端
    @BindView(R2.id.rate_tv_belong) TextView mTvBelong;                         //归属
    @BindView(R2.id.rate_tv_time_length) TextView mTvTimeLength;                //时长
    @BindView(R2.id.rate_tv_update_time) TextView mTvUpdateTime;                //最后更新时间
    @BindView(R2.id.rate_ltv_preview_state) LabelTextView mLtvState;            //状态
    @BindView(R2.id.rate_rv_project) DYRefreshView mRvcomponent;                //列表
    @BindView(R2.id.rate_btn_add) Button mBtnAdd;                               //添加按钮
    @BindView(R2.id.rate_tv_remark) TextView mTvRemark;                         //备注

    @Autowired(name = ARouterPath.Rate.ModuleDetail.Params.MODULEID)
    String mModuleId;                       //moduleID
    @Autowired(name = ARouterPath.Rate.ModuleDetail.Params.PROJECTID)
    String mProjectId;                      //projectID

    private int pageNo = 1;                 //页码
    private ComponentListAdapter mComponentAdapter; //功能适配器
    private ModuleDetailEntity mModuleEntity;   //组件数据

    @Override
    protected int getMainLayoutId() {
        return R.layout.rate_activity_moduledetail;
    }

    @Override
    protected void initStateLayout() {
        getStateManager();
    }

    @Override
    protected ModuleDetailPresenter createPresent() {
        return new ModuleDetailPresenter(this);
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
        mRvcomponent.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void initData() {
        mComponentAdapter = new ComponentListAdapter(this);
        mRvcomponent.setAdapter(mComponentAdapter);
        mComponentAdapter.setOnItemClickListener(position -> goComponentDetail(mComponentAdapter.getAllData().get(position).getId()));
        //刷新
        mRvcomponent.setRefreshListener(() -> refresh());
        //加载
        mComponentAdapter.setMore(R.layout.rv_footer, () -> load());
    }

    @Override
    protected void onResume() {
        super.onResume();
        getP().moduleGetDetail(mModuleId);
    }

    @Override
    protected TitleBar titleBar() {
        return mTbTitle;
    }

    @Override
    protected void onRightClick() {
        super.onRightClick();
        if (null != mModuleEntity) {
            Bundle bundle = new Bundle();
            bundle.putString(ARouterPath.Rate.ComponentUpdate.Params.PROJECTID, mModuleEntity.getProjectId());
            bundle.putString(ARouterPath.Rate.ComponentUpdate.Params.MODULEID, mModuleEntity.getId());
            bundle.putString(ARouterPath.Rate.ComponentUpdate.Params.STARTTIME, mModuleEntity.getStartTime());
            bundle.putString(ARouterPath.Rate.ComponentUpdate.Params.ENDTIME, mModuleEntity.getEndTime());
            ARouterHelper.navigation(ARouterPath.Rate.ComponentUpdate.PATH, bundle);
        }else {
            ToastUtils.showShort("组件数据获取失败，请重新获取");
        }
    }

    /**
     * 更新card数据
     * @param mProjectEntity
     */
    private void updateView(ModuleDetailEntity mProjectEntity) {

        //项目名称
        mTvProjectName.setText(mProjectEntity.getName());
        mTvRemark.setText(TextUtils.isEmpty(mProjectEntity.getRemark()) ? "暂无描述" : mProjectEntity.getRemark());
        //归属
//        mTvBelong.setText(mProjectEntity.getBelongId());
        //时长
        mTvTimeLength.setText("期限：" + DateUtil.getTimestamp(mProjectEntity.getStartTime(), DateUtil.mFormat_12, DateUtil.mFormat_date_24) + "至" + DateUtil.getTimestamp(mProjectEntity.getEndTime(), DateUtil.mFormat_12, DateUtil.mFormat_date_24));
        //更新时间
        mTvUpdateTime.setText("最近更新:" + mProjectEntity.getUpdatedAt());
        //设置进度
        mDpProgress.setProgress(FloatUtils.float2Point(mProjectEntity.getProgress()));
        mTbTitle.setCenterText(mProjectEntity.getName());
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

    /**
     *
     * @param entity    组件详情实体      ---> 获取到组件详情信息
     */
    @Override
    public void onGetModuleDetail(ModuleDetailEntity entity) {
        //获取功能列表
        refresh();
        mModuleEntity = entity;
        //title
        mTbTitle.setCenterText(entity.getName());
        updateView(entity);
    }

    /**
     *
     * @param entity 功能列表       ---> 获取到功能（成分）列表
     */
    @Override
    public void onGetComponetList(RateBaserPagerEntity<ComponentEntity> entity) {
        if (pageNo == 1){//刷新
            mRvcomponent.setRefreshing(false);
            mComponentAdapter.clear();
        }
        if (pageNo == entity.getPager().getTotalPage()){
            mComponentAdapter.setNoMore(R.layout.rv_layout_common_nomore);
            mComponentAdapter.stopMore();
        }
        pageNo = pageNo + 1;
        mComponentAdapter.addAll(entity.getList());
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
    private void load(){
        getP().componentGetList("", mProjectId, mModuleId, pageNo);
    }

    /**
     * 跳转功能详情
     * @param componentId
     */
    private void goComponentDetail(String componentId){
        Bundle bundle = new Bundle();
        bundle.putString(ARouterPath.Rate.ComponentDetail.Params.COMPONENTID, componentId);
        ARouterHelper.navigation(ARouterPath.Rate.ComponentDetail.PATH, bundle);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventSubcriber(EventBusEntity entity) {
        switch (entity.getEventCode()){
            case Constant.CodeEvent.RATE_COMPONENTUPDATE:
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
