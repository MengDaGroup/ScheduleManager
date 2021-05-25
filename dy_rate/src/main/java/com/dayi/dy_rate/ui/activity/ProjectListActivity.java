package com.dayi.dy_rate.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.dayi.dy_rate.R;
import com.dayi.dy_rate.R2;
import com.dayi.dy_rate.contract.UserContract;
import com.dayi.dy_rate.entity.ConditionsEntity;
import com.dayi.dy_rate.entity.ProjectEntity;
import com.dayi.dy_rate.entity.RateBaserPagerEntity;
import com.dayi.dy_rate.entity.TeamUserEntity;
import com.dayi.dy_rate.presenter.ProjectListPresenter;
import com.dayi.dy_rate.ui.adapter.ProjectListAdapter;
import com.dayi.dy_rate.widget.ProjectFilterPop;
import com.dayi35.qx_base.arouter.ARouterHelper;
import com.dayi35.qx_base.arouter.ARouterPath;
import com.dayi35.qx_base.base.state.BaseStateActivity;
import com.dayi35.qx_base.beans.EventBusEntity;
import com.dayi35.qx_base.beans.KeyValueEntity;
import com.dayi35.qx_base.constant.Constant;
import com.dayi35.qx_base.utils.EventBusHelper;
import com.dayi35.qx_base.utils.GsonHelper;
import com.dayi35.qx_utils.androidcodeutils.SPUtils;
import com.dayi35.qx_utils.common.StatusBarUtil;
import com.dayi35.qx_utils.common.ToastUtils;
import com.dayi35.qx_utils.convert.RCaster;
import com.dayi35.qx_widget.titlebar.TitleBar;
import com.dayi35.recycle.view.DYRefreshView;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2021/4/8 11:06
 * 描    述: 类 项目列表(所有的项目的合集)
 * 修订历史:
 * =========================================
 */
public class ProjectListActivity extends BaseStateActivity<ProjectListPresenter> implements UserContract.ProjectListContract.View {
    @BindView(R2.id.rate_tb_main)
    TitleBar mTbTitle;                          //title
    @BindView(R2.id.rate_rv_project)
    DYRefreshView mRvProject;                   //列表
    @BindView(R2.id.rate_rgl_filter)
    RadioGroup mRgLayout;                       //RadioGroup
    @BindView(R2.id.rate_tv_project_des)
    TextView mTvProjectDes;                     //项目概述
    @BindView(R2.id.rate_rb_name)
    RadioButton mRbName;                        //项目名筛选条件
    @BindView(R2.id.rate_rb_os)
    RadioButton mRbOs;                          //端筛选条件
    @BindView(R2.id.rate_rb_state)
    RadioButton mRbState;                       //状态筛选条件
    @BindView(R2.id.rate_rb_group)
    RadioButton mRbTeam;                       //团队筛选条件

    private ProjectListAdapter mAdapter;            //适配器

    private ProjectFilterPop.Builder mPopName;      //name筛选
    private ProjectFilterPop.Builder mPopOs;        //端筛选
    private ProjectFilterPop.Builder mPopState;     //状态筛选
    private ProjectFilterPop.Builder mPopTeam;     //团队筛选

    private List<KeyValueEntity> mListOs = new ArrayList<>();           //os
    private List<KeyValueEntity> mListState = new ArrayList<>();        //state
    private List<KeyValueEntity> mListName = new ArrayList<>();         //name
    private List<KeyValueEntity> mListTeam = new ArrayList<>();         //group

    private String mName = "";                           //项目名
    private String mOs = "-1";                            //端     -1.不过滤   1.Android   2.IOS
    private String mState = "-1";                         //状态  -1.不过滤该字段， 1.进行中   2.已结束   3.已逾期
    private String mTeam = "";                           //团队

    private int pageNo = 1;


    @Override
    protected int getMainLayoutId() {
        return R.layout.rate_activity_projectlist;
    }

    @Override
    protected void initStateLayout() {
        getStateManager();
    }

    @Override
    protected ProjectListPresenter createPresent() {
        return new ProjectListPresenter(this);
    }

    @Override
    protected RCaster createCaster() {
        return new RCaster(R.class, R2.class);
    }

    @Override
    protected void initView() {
        EventBusHelper.register(this);
        StatusBarUtil.transparent(this);
        StatusBarUtil.setDarkMode(this);
        mRvProject.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void initData() {
        mAdapter = new ProjectListAdapter(this);
        mRvProject.setAdapter(mAdapter);
        //添加OS数据
        mListOs.add(new KeyValueEntity("不限", "-1"));
        //添加状态数据
        mListState.add(new KeyValueEntity("不限","-1"));
        //团队的初始值
        mListTeam.add(new KeyValueEntity("不限", ""));
        //项目的初始值
        mListName.add(new KeyValueEntity("不限", ""));
        //过滤条件获取
        getP().projectGetConditions();
        initListener();
        //团队筛选条件
        getP().memberList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //项目数据
        refresh();
    }

    private void initListener() {
        //iteam的点击事件--跳转项目详情
        mAdapter.setOnItemClickListener(position -> {
            Bundle bundle = new Bundle();
            bundle.putString(ARouterPath.Rate.ProjectDetail.Params.PROJECTID, mAdapter.getAllData().get(position).getId());
            ARouterHelper.navigation(ARouterPath.Rate.ProjectDetail.PATH, bundle);
        });

        //Name筛选按钮
        mRbName.setOnClickListener(v -> {
            if (null != mListName && mListName.size() > 0){
                mPopName = new ProjectFilterPop.Builder(ProjectListActivity.this)
                        .create(mRgLayout, LinearLayout.LayoutParams.WRAP_CONTENT)
                        .init(mListName, 2, (key, value, pos) -> {
                            mTvProjectDes.setVisibility(View.GONE);
                            mName = value;
                            if (0 == pos){
                                mRbName.setText("项目");
                            }else {
                                mRbName.setText(key);
                            }
                            mRbName.setTextColor(getResources().getColor(R.color.widget_color_ff9912));
                            mRbOs.setTextColor(getResources().getColor(R.color.widget_color_2e2e2e));
                            mRbState.setTextColor(getResources().getColor(R.color.widget_color_2e2e2e));
                            mRbTeam.setTextColor(getResources().getColor(R.color.widget_color_2e2e2e));
                            //判断另外两个弹窗是否显示中，显示的话就隐藏掉
                            if (null != mPopOs && mPopOs.bIsShowing()){
                                mPopOs.dissmiss();
                            }
                            if (null != mPopState && mPopState.bIsShowing()){
                                mPopState.dissmiss();
                            }
                            if (null != mPopTeam && mPopTeam.bIsShowing()){
                                mPopTeam.dissmiss();
                            }
                            refresh();
                        });
            }else {
                ToastUtils.showShort("未获取到您的项目数据");
            }
        });

        //OS筛选按钮
       mRbOs.setOnClickListener(v -> mPopOs = new ProjectFilterPop.Builder(ProjectListActivity.this)
               .create(mRgLayout, LinearLayout.LayoutParams.WRAP_CONTENT)
               .init(mListOs, 3, (key, value, pos) -> {
                   mTvProjectDes.setVisibility(View.GONE);
                   mOs = value;
                   if (0 == pos){
                       mRbOs.setText("OS");
                   }else {
                       mRbOs.setText(key);
                   }
                   mRbOs.setTextColor(getResources().getColor(R.color.widget_color_ff9912));
                   mRbName.setTextColor(getResources().getColor(R.color.widget_color_2e2e2e));
                   mRbState.setTextColor(getResources().getColor(R.color.widget_color_2e2e2e));
                   mRbTeam.setTextColor(getResources().getColor(R.color.widget_color_2e2e2e));
                   //判断另外两个弹窗是否显示中，显示的话就隐藏掉
                   if (null != mPopName && mPopName.bIsShowing()){
                       mPopName.dissmiss();
                   }
                   if (null != mPopState && mPopState.bIsShowing()){
                       mPopState.dissmiss();
                   }
                   if (null != mPopTeam && mPopTeam.bIsShowing()){
                       mPopTeam.dissmiss();
                   }
                   refresh();
               }));

        //State筛选按钮
        mRbState.setOnClickListener(v -> mPopState = new ProjectFilterPop.Builder(ProjectListActivity.this)
                .create(mRgLayout, LinearLayout.LayoutParams.WRAP_CONTENT)
                .init(mListState, 4, (key, value, pos) -> {
                    mTvProjectDes.setVisibility(View.GONE);
                    mState = value;
                    if (0 == pos){
                        mRbState.setText("状态");
                    }else {
                        mRbState.setText(key);
                    }
                    mRbState.setTextColor(getResources().getColor(R.color.widget_color_ff9912));
                    mRbName.setTextColor(getResources().getColor(R.color.widget_color_2e2e2e));
                    mRbOs.setTextColor(getResources().getColor(R.color.widget_color_2e2e2e));
                    mRbTeam.setTextColor(getResources().getColor(R.color.widget_color_2e2e2e));
                    //判断另外两个弹窗是否显示中，显示的话就隐藏掉
                    if (null != mPopName && mPopName.bIsShowing()){
                        mPopName.dissmiss();
                    }
                    if (null != mPopOs && mPopOs.bIsShowing()){
                        mPopOs.dissmiss();
                    }
                    if (null != mPopTeam && mPopTeam.bIsShowing()){
                        mPopTeam.dissmiss();
                    }
                    refresh();
                }));

        mRbTeam.setOnClickListener(v -> mPopTeam = new ProjectFilterPop.Builder(ProjectListActivity.this)
                .create(mRgLayout, LinearLayout.LayoutParams.WRAP_CONTENT)
                .init(mListTeam, 2, (key, value, pos) -> {
                    mTvProjectDes.setVisibility(View.GONE);
                    mTeam = value;
                    if (0 == pos){
                        mRbTeam.setText("团队");
                    }else {
                        mRbTeam.setText(key);
                    }
                    mRbTeam.setTextColor(getResources().getColor(R.color.widget_color_ff9912));
                    mRbState.setTextColor(getResources().getColor(R.color.widget_color_2e2e2e));
                    mRbName.setTextColor(getResources().getColor(R.color.widget_color_2e2e2e));
                    mRbOs.setTextColor(getResources().getColor(R.color.widget_color_2e2e2e));
                    //判断另外两个弹窗是否显示中，显示的话就隐藏掉
                    if (null != mPopName && mPopName.bIsShowing()){
                        mPopName.dissmiss();
                    }
                    if (null != mPopOs && mPopOs.bIsShowing()){
                        mPopOs.dissmiss();
                    }
                    if (null != mPopState && mPopState.bIsShowing()){
                        mPopState.dissmiss();
                    }
                    refresh();
                }));

    }

    @Override
    protected TitleBar titleBar() {
        return mTbTitle;
    }


    /**
     * 获取到筛选条件
     * 说明：因为项目的筛选条件，需要在每次新建项目后重新拉取，所以相应的要更新这些过滤条件
     * @param entity
     */
    @Override
    public void onGetConditions(ConditionsEntity entity) {
        if ( null != entity){
            //组装项目筛选列表
            if (null != entity.getProjectList() && 0 < entity.getProjectList().size()){
                mListName.clear();
                //项目的初始值
                mListName.add(new KeyValueEntity("不限", ""));
                for (ConditionsEntity.ProjectListBean projectListBean: entity.getProjectList()
                     ) {
                    KeyValueEntity keyValueProject = new KeyValueEntity(projectListBean.getLabel(), projectListBean.getValue());
                    mListName.add(keyValueProject);
                }
            }
            //组装OS
            if (null != entity.getOs() && 0 < entity.getOs().size()){
                mListOs.clear();
                //添加OS数据
                mListOs.add(new KeyValueEntity("不限", "-1"));
                for (ConditionsEntity.OsBean osBean: entity.getOs()
                     ) {
                    KeyValueEntity keyValueOS = new KeyValueEntity(osBean.getLabel(), String.valueOf(osBean.getValue()));
                    mListOs.add(keyValueOS);
                }
                //将OS数据缓存
                SPUtils.getInstance().put(Constant.KeySPUtils.RATE_SP_OS, GsonHelper.toJson(mListOs));
            }
            //组装状态
            if (null != entity.getStatus() && 0 < entity.getStatus().size()){
                mListState.clear();
                //添加状态数据
                mListState.add(new KeyValueEntity("不限","-1"));
                for (ConditionsEntity.StatusBean stateBean: entity.getStatus()
                     ) {
                    KeyValueEntity keyValueState = new KeyValueEntity(stateBean.getLabel(), String.valueOf(stateBean.getValue()));
                    mListState.add(keyValueState);
                }
            }
        }
    }

    /**
     * @param entity  获取到项目列表
     */
    @Override
    public void onGetProjectList(RateBaserPagerEntity<ProjectEntity> entity) {
        mAdapter.clear();
        if (pageNo == 1){//刷新
            mRvProject.setRefreshing(false);
        }
        if (pageNo == entity.getPager().getTotalPage()){
            mAdapter.setNoMore(R.layout.rv_layout_common_nomore);
            mAdapter.stopMore();
        }
        pageNo = pageNo + 1;
        mAdapter.addAll(entity.getList());
    }

    /**
     *
     * @param type  失败来源：1. 新增来源         2.修改来源      3.查询来源
     * @param msg
     */
    @Override
    public void onFailed(int type, String msg) {

    }

    /**
     * 登出成功
     */
    @Override
    public void onLogoutSuccess() {

    }

    /**
     * 获取到成员列表
     * @param entities  成员列表    --->    获取到成员
     */
    @Override
    public void onGetMemberList(List<TeamUserEntity> entities) {
        if (null != entities && 0 < entities.size()){
            for (TeamUserEntity entity:entities
                 ) {
                KeyValueEntity keyValueEntity = new KeyValueEntity();
                keyValueEntity.setKey(entity.getLabel());
                keyValueEntity.setValue(entity.getValue());
                mListTeam.add(keyValueEntity);
            }
        }
    }

    @Override
    protected void onRightClick() {
        super.onRightClick();
        ARouterHelper.navigation(ARouterPath.Rate.ProjectUpdate.PARH);
    }

    /**
     * 刷新
     */
    private void refresh(){
        getP().projectGetList(mName, mState, mTeam, mOs);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventSubcriber(EventBusEntity entity) {
        switch (entity.getEventCode()){
            case Constant.CodeEvent.RATE_PROJECTTEAMUPDATE:
                refresh();
                getP().projectGetConditions();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusHelper.unRegister(this);
    }
}
