package com.dayi.dy_rate.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dayi.dy_rate.R;
import com.dayi.dy_rate.R2;
import com.dayi.dy_rate.contract.UserContract;
import com.dayi.dy_rate.entity.ProjectEntity;
import com.dayi.dy_rate.entity.ProjectRateEntity;
import com.dayi.dy_rate.entity.ProjectTeamEntity;
import com.dayi.dy_rate.presenter.ProjectPresenter;
import com.dayi.dy_rate.presenter.ProjectTeamPresenter;
import com.dayi.dy_rate.ui.adapter.ProjectAdapter;
import com.dayi.dy_rate.ui.adapter.ProjectTeamAdapter;
import com.dayi.dy_rate.widget.ProjectFilterPop;
import com.dayi35.qx_base.arouter.ARouterPath;
import com.dayi35.qx_base.base.state.BaseStateActivity;
import com.dayi35.qx_base.beans.EventBusEntity;
import com.dayi35.qx_base.constant.Constant;
import com.dayi35.qx_base.utils.EventBusHelper;
import com.dayi35.qx_base.utils.GsonHelper;
import com.dayi35.qx_utils.androidcodeutils.SPUtils;
import com.dayi35.qx_utils.common.StatusBarUtil;
import com.dayi35.qx_utils.common.ToastUtils;
import com.dayi35.qx_utils.convert.RCaster;
import com.dayi35.qx_widget.pop.OnEventListenner;
import com.dayi35.qx_widget.titlebar.TitleBar;
import com.dayi35.recycle.view.DYRefreshView;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2021/4/8 11:06
 * 描    述: 类 项目列表
 * 修订历史:
 * =========================================
 */
public class ProjectTeamListActivity extends BaseStateActivity<ProjectTeamPresenter> implements UserContract.ProjectTeamContract.View {
    @BindView(R2.id.rate_tb_main)
    TitleBar mTbTitle;                          //title
    @BindView(R2.id.rate_rv_project)
    DYRefreshView mRvProject;                   //列表
    @BindView(R2.id.rate_btn_add)
    Button mBtnAdd;                             //添加按钮
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

    private ProjectTeamAdapter mAdapter;            //适配器
    private ProjectFilterPop.Builder mPopName;      //name筛选
    private ProjectFilterPop.Builder mPopOs;        //端筛选
    private ProjectFilterPop.Builder mPopState;     //状态筛选
    private List<String> mListOs = new ArrayList<>();           //os
    private List<String> mListState = new ArrayList<>();        //state
    private List<String> mListName = new ArrayList<>();         //name
    private List<ProjectRateEntity> mListRate = new ArrayList<>();  //项目实体集合
    private List<ProjectTeamEntity> mListTeam = new ArrayList<>();  //项目组实体集合

    private String mObjectId;                   //项目唯一标识（name筛选）
    private int mOs;                            //端     0.不过滤   1.Android   2.IOS
    private int mState;                         //状态  0.不过滤该字段， 1.进行中   2.已结束   3.已逾期


    @Override
    protected int getMainLayoutId() {
        return R.layout.rate_activity_projectteam_list;
    }

    @Override
    protected void initStateLayout() {
        getStateManager();
    }

    @Override
    protected ProjectTeamPresenter createPresent() {
        return new ProjectTeamPresenter(this);
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
        //获取服务器已有项目
        refreshRate();
        mAdapter = new ProjectTeamAdapter(this);
        mRvProject.setAdapter(mAdapter);
        mListName.add("全部");
        //添加OS数据
        mListOs.add("OS");
        mListOs.add("Android");
        mListOs.add("IOS");
        //添加状态数据
        mListState.add("状态");
        mListState.add("进行中");
        mListState.add("已结束");
        mListState.add("已逾期");

        initListener();
        refresh();
    }

    private void initListener() {
        //iteam的点击事件
        mAdapter.setOnItemClickListener(position -> {
            Intent intent = new Intent(ProjectTeamListActivity.this, ProjectListActivity.class);
            intent.putExtra("project", GsonHelper.toJson(mAdapter.getAllData().get(position)));
            intent.putExtra("project_team_id", mAdapter.getAllData().get(position).getObjectId());
            intent.putExtra("title", mAdapter.getAllData().get(position).getProjectName());
            startActivity(intent);
        });

        //添加按钮
        mBtnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(ProjectTeamListActivity.this, EditProjectTeamActivity.class);
            startActivity(intent);
        });

        //Name筛选按钮
        mRbName.setOnClickListener(v -> {
            if (null != mListName && mListName.size() > 0){
                mPopName = new ProjectFilterPop.Builder(ProjectTeamListActivity.this)
                        .create(mRgLayout, LinearLayout.LayoutParams.WRAP_CONTENT)
                        .init(mListName, 2, (value, pos) -> {
                            mTvProjectDes.setVisibility(View.GONE);
                            //第一位为补位字段---》全部
                            if (0 == pos){
                                mObjectId = null;
                            }else if (null != mListRate && mListRate.size() > 0) {
                                //第一位是添加的全部字段名
                                mObjectId = mListRate.get(pos - 1).getObjectId();
                            }
                            refreshAdapter();
                            mRbName.setText(value);
                            mRbName.setTextColor(getResources().getColor(R.color.widget_color_ff9912));
                            mRbOs.setTextColor(getResources().getColor(R.color.widget_color_2e2e2e));
                            mRbState.setTextColor(getResources().getColor(R.color.widget_color_2e2e2e));
                            //判断另外两个弹窗是否显示中，显示的话就隐藏掉
                            if (null != mPopOs && mPopOs.bIsShowing()){
                                mPopOs.dissmiss();
                            }
                            if (null != mPopState && mPopState.bIsShowing()){
                                mPopState.dissmiss();
                            }
                        });
            }else {
                ToastUtils.showShort("未获取到您的项目数据");
            }
        });

        //OS筛选按钮
       mRbOs.setOnClickListener(v -> mPopOs = new ProjectFilterPop.Builder(ProjectTeamListActivity.this)
               .create(mRgLayout, LinearLayout.LayoutParams.WRAP_CONTENT)
               .init(mListOs, 3, (value, pos) -> {
                   mTvProjectDes.setVisibility(View.GONE);
                   mOs = pos;
                   refreshAdapter();
                   mRbOs.setText(value);
                   mRbOs.setTextColor(getResources().getColor(R.color.widget_color_ff9912));
                   mRbName.setTextColor(getResources().getColor(R.color.widget_color_2e2e2e));
                   mRbState.setTextColor(getResources().getColor(R.color.widget_color_2e2e2e));
                   //判断另外两个弹窗是否显示中，显示的话就隐藏掉
                   if (null != mPopName && mPopName.bIsShowing()){
                       mPopName.dissmiss();
                   }
                   if (null != mPopState && mPopState.bIsShowing()){
                       mPopState.dissmiss();
                   }
               }));

        //State筛选按钮
        mRbState.setOnClickListener(v -> mPopState = new ProjectFilterPop.Builder(ProjectTeamListActivity.this)
                .create(mRgLayout, LinearLayout.LayoutParams.WRAP_CONTENT)
                .init(mListState, 4, (value, pos) -> {
                    mTvProjectDes.setVisibility(View.GONE);
                    mState = pos;
                    refreshAdapter();
                    mRbState.setText(value);
                    mRbState.setTextColor(getResources().getColor(R.color.widget_color_ff9912));
                    mRbName.setTextColor(getResources().getColor(R.color.widget_color_2e2e2e));
                    mRbOs.setTextColor(getResources().getColor(R.color.widget_color_2e2e2e));
                    //判断另外两个弹窗是否显示中，显示的话就隐藏掉
                    if (null != mPopName && mPopName.bIsShowing()){
                        mPopName.dissmiss();
                    }
                    if (null != mPopOs && mPopOs.bIsShowing()){
                        mPopOs.dissmiss();
                    }
                }));

    }

    @Override
    protected TitleBar titleBar() {
        return mTbTitle;
    }

    /**
     *
     * @param type          1.新增成功      2.修改成功      3.查询成功
     * @param entity
     */
    @Override
    public void onDoSuccess(int type, List<ProjectTeamEntity> entity) {
        //查询
        if (3 == type && null != entity){
            adapterEntity(entity);
            adapterDes();
        }
    }

    /**
     *
     * @param entities  获取到项目工程列表
     */
    @Override
    public void onGetRateSuccess(List<ProjectRateEntity> entities) {
        if (null != entities){
            mListRate.clear();
            mListName.clear();
            mListName.add("全部");
            mListRate.addAll(entities);
            SPUtils.getInstance().put(Constant.KeySPUtils.RATE_PROJECTRATE, GsonHelper.toJson(mListRate));
            for (ProjectRateEntity projectRateEntity:mListRate
                 ) {
                mListName.add(projectRateEntity.getProjectName());
            }
        }
    }

    /**
     * 适配数据
     * @param entity
     */
    private void adapterEntity(List<ProjectTeamEntity> entity) {
        mAdapter.addAll(entity);
        mListTeam.addAll(entity);
    }

    /**
     * 适配des信息
     */
    private void adapterDes(){
        mTvProjectDes.setVisibility(View.VISIBLE);
        if (TextUtils.isEmpty(mObjectId)) {
            mTvProjectDes.setText("共计" + (mListName.size()-1) + "个项目" + mAdapter.getAllData().size() + "个组件");
        }else {
            int totalProgress = 0;
            if (mAdapter.getAllData().size() > 0){
                for (ProjectTeamEntity proEntity: mAdapter.getAllData()
                ) {
                    totalProgress = totalProgress + Integer.valueOf(proEntity.getProjectProgress());
                }
            }
            mTvProjectDes.setText("当前平均进度约" + totalProgress/mAdapter.getAllData().size() + "%");
        }
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
     * 刷新adapter（未刷新数据源）
     */
    private void refreshAdapter(){
        mAdapter.clear();
        if (null != filterTeamEntity() && filterTeamEntity().size() > 0) {
            mAdapter.addAll(filterTeamEntity());
            adapterDes();
        }
    }

    /**
     * 刷新进度项目的数据（与本地缓存比较）
     */
    private void refreshRate(){
        String json = SPUtils.getInstance().getString(Constant.KeySPUtils.RATE_PROJECTRATE);
        //是否有本地缓存，若无，获取服务器数据
        if (!TextUtils.isEmpty(json)){
            Type type = new TypeToken<List<ProjectRateEntity>>(){}.getType();
            List<ProjectRateEntity> entities = GsonHelper.JSONToObj(json, type);
            for (ProjectRateEntity projectRateEntity:entities
            ) {
                mListName.add(projectRateEntity.getProjectName());
            }
        }else {
            getP().searchRate();
        }
    }

    /**
     * 过滤筛选条件下的数据
     * @return
     */
    private List<ProjectTeamEntity> filterTeamEntity(){
        List<ProjectTeamEntity> tempList = new ArrayList<>();
        for (ProjectTeamEntity entity: mListTeam
             ) {
            // mObjectId 为空（不限） 或者为同一id  且  mOs为0（不限）或为同一os  且 mState为0（不限）或为同一state
            if ((TextUtils.isEmpty(mObjectId) || mObjectId.equals(entity.getProjectRateId()))){
                if ((0 == mOs || Integer.valueOf(entity.getProjectOS()) == mOs)){
                    if ((0 == mState || entity.getProjectState() == mState)){
                        tempList.add(entity);
                    }
                }
            }
        }
        return tempList;
    }

    @Override
    protected void onRightClick() {
        super.onRightClick();
        mTvProjectDes.setVisibility(View.GONE);
        mAdapter.clear();
        mListTeam.clear();
        getP().searchRate();
        getP().search("", 0, 0);
    }

    /**
     * 刷新
     */
    private void refresh(){
        mAdapter.clear();
        mListTeam.clear();
        getP().search(mObjectId, mOs, mState);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventSubcriber(EventBusEntity entity) {
        switch (entity.getEventCode()){
            case Constant.CodeEvent.RATE_PROJECTTEAMUPDATE:
                refresh();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusHelper.unRegister(this);
    }
}
