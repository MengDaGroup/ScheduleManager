package com.dayi.dy_rate.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dayi.dy_rate.R;
import com.dayi.dy_rate.R2;
import com.dayi.dy_rate.contract.UserContract;
import com.dayi.dy_rate.entity.ProjectEntity;
import com.dayi.dy_rate.entity.ProjectRateEntity;
import com.dayi.dy_rate.entity.ProjectTeamEntity;
import com.dayi.dy_rate.presenter.ProjectPresenter;
import com.dayi.dy_rate.presenter.ProjectTeamPresenter;
import com.dayi.dy_rate.utils.UserRateHelper;
import com.dayi35.qx_base.base.state.BaseStateActivity;
import com.dayi35.qx_base.beans.BottomPopEntity;
import com.dayi35.qx_base.beans.EventBusEntity;
import com.dayi35.qx_base.constant.Constant;
import com.dayi35.qx_base.pop.BottomCommonPop;
import com.dayi35.qx_base.utils.EventBusHelper;
import com.dayi35.qx_base.utils.GsonHelper;
import com.dayi35.qx_utils.androidcodeutils.SPUtils;
import com.dayi35.qx_utils.common.DateUtil;
import com.dayi35.qx_utils.common.StatusBarUtil;
import com.dayi35.qx_utils.common.ToastUtils;
import com.dayi35.qx_utils.convert.RCaster;
import com.dayi35.qx_widget.titlebar.TitleBar;
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
 * 创建日期: 2021/4/9 10:24
 * 描    述: 类        项目组编辑 type 1.新增  2.修改
 * 修订历史:
 * =========================================
 */
public class EditProjectTeamActivity extends BaseStateActivity<ProjectTeamPresenter> implements UserContract.ProjectTeamContract.View {
    private final int TYPEADD = 1;              //添加
    private final int TYPEUPDATE = 2;           //更新

    @BindView(R2.id.rate_tv_project) TextView mTvProject;               //归属项目
    @BindView(R2.id.rate_tb_project_edit) TitleBar mTbTitle;            //title
    @BindView(R2.id.rate_btn_save) Button mBtnAdd;                      //添加/修改
    @BindView(R2.id.rate_et_name) EditText mEtName;                     //项目名称
    @BindView(R2.id.rate_et_belong) EditText mEtBelong;                 //项目归属
    @BindView(R2.id.rate_et_starttime) EditText mEtStartTime;           //项目开始时间
    @BindView(R2.id.rate_et_endtime) EditText mEtEndTime;               //项目结束时间
    @BindView(R2.id.rate_et_progress) EditText mEtProgress;             //项目进度
    @BindView(R2.id.rate_tv_noted) TextView mTvNoted;                   //项目已有备注

    private int type;                                                   //界面 1.新增  2.修改
    private String mProjectJson;                                        //projectJson
    private ProjectTeamEntity mProjectEntity;                           //组件实例
    private String projectId;                                           //所选项目的ID
    private String projectName;                                         //所选项目的名称
    private List<ProjectRateEntity> mRateEntities = new ArrayList<>();  //项目实例集合

    private BottomCommonPop.Builder projectPop;                         //项目选择弹窗


    @Override
    protected int getMainLayoutId() {
        return R.layout.rate_activity_projectteam_edit;
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
        StatusBarUtil.transparent(this);
        StatusBarUtil.setDarkMode(this);
        EventBusHelper.register(this);
        mBtnAdd.setOnClickListener(v -> {
            if (verifyInput()){
                if (TYPEADD == type){           //添加
                    getP().create(buildProjectEntity());
                }else if (TYPEUPDATE == type){  //更新
                    getP().update(buildProjectEntity());
                }
            }
        });
        initProjectData();

    }

    /**
     * 获取项目集的相关数据
     */
    private void initProjectData() {
        String json = SPUtils.getInstance().getString(Constant.KeySPUtils.RATE_PROJECTRATE);
        //是否有本地缓存，若无，获取服务器数据
        if (!TextUtils.isEmpty(json)){
            Type type = new TypeToken<List<ProjectRateEntity>>(){}.getType();
            List<ProjectRateEntity> entities = GsonHelper.JSONToObj(json, type);
            mRateEntities.addAll(entities);
        }
    }

    @Override
    protected void initData() {
        type = getIntent().getIntExtra("type", TYPEADD);
        //更新
        if (TYPEUPDATE == type) {
            //得到界面传值
            mProjectJson = getIntent().getStringExtra("project");
            if (!TextUtils.isEmpty(mProjectJson)) {
                //解析成实体
                mProjectEntity = GsonHelper.toType(mProjectJson, ProjectTeamEntity.class);
            }
            mEtName.setEnabled(false);
            mEtName.setText(mProjectEntity.getProjectName());
            mEtBelong.setEnabled(false);
            mEtBelong.setText(mProjectEntity.getProjectBelong());
            mEtStartTime.setEnabled(false);
            mEtStartTime.setText(mProjectEntity.getProjectStartTime());
            mEtEndTime.setEnabled(false);
            mEtEndTime.setText(mProjectEntity.getProjectEndTime());
            mTvProject.setText(mProjectEntity.getProjectRateName());
            projectId = mProjectEntity.getProjectRateId();
            projectName = mProjectEntity.getProjectRateName();
        }else {//添加
            mEtName.setEnabled(true);
            mEtBelong.setEnabled(true);
            mEtStartTime.setEnabled(true);
            mEtEndTime.setEnabled(true);
            mTvProject.setOnClickListener(v -> {
                if (null != mRateEntities && mRateEntities.size() > 0) {
                    if (null == projectPop) {
                        List<BottomPopEntity> bottomPopEntities = new ArrayList<>();
                        for (ProjectRateEntity entity : mRateEntities
                        ) {
                            BottomPopEntity bottomPopEntity = new BottomPopEntity(entity.getProjectName());
                            bottomPopEntities.add(bottomPopEntity);
                        }
                        projectPop = new BottomCommonPop.Builder(EditProjectTeamActivity.this)
                                .create(v)
                                .init(bottomPopEntities, (value, pos) -> {
                                    projectId = mRateEntities.get(pos).getObjectId();
                                    projectName = mRateEntities.get(pos).getProjectName();
                                    mTvProject.setText(projectName);
                                });
                    }
                    projectPop.show();
                }else {
                    ToastUtils.showShort("未获取到可选项目数据");
                }
            });
        }
    }

    @Override
    protected TitleBar titleBar() {
        return mTbTitle;
    }

    /**
     * 构建projectentity实体
     * @return
     */
    private ProjectTeamEntity buildProjectEntity(){
        if (TYPEADD == type){
            mProjectEntity = new ProjectTeamEntity();
            mProjectEntity.setCreateUser(UserRateHelper.get().getUserNickName());
            mProjectEntity.setCreateTime(DateUtil.formatByTimeStamp(System.currentTimeMillis(), DateUtil.mFormat_date_24));
            mProjectEntity.setProjectState(1);       //创建的时候设置状态为进行中
            mProjectEntity.setProjectOS("1");        //设置为Android端
            mProjectEntity.setProjectRateId(projectId);
            mProjectEntity.setProjectRateName(projectName);
        }
        mProjectEntity.setProjectName(mEtName.getText().toString().trim());
        mProjectEntity.setProjectBelong(mEtBelong.getText().toString().trim());
        mProjectEntity.setProjectStartTime(mEtStartTime.getText().toString().trim());
        mProjectEntity.setProjectEndTime(mEtEndTime.getText().toString().trim());
        mProjectEntity.setProjectProgress(mEtProgress.getText().toString().trim());
        mProjectEntity.setUpdateUser(UserRateHelper.get().getUserNickName());
        mProjectEntity.setUpdateTime(DateUtil.formatByTimeStamp(System.currentTimeMillis(), DateUtil.mFormat_date_24));
        long times = DateUtil.dateToLong(mProjectEntity.getProjectEndTime(), DateUtil.mFormat_date_24) - System.currentTimeMillis();
        int statu = 0;
        if (100 == Integer.valueOf(mProjectEntity.getProjectProgress())){
            statu = 2;
        }else if (times >= 0){
            statu =1;
        }else {
            statu = 3;
        }
        mProjectEntity.setProjectState(statu);
        return mProjectEntity;
    }

    /**
     * 验证输入
     * @return
     */
    private boolean verifyInput(){
        if (TextUtils.isEmpty(mEtName.getText().toString().trim())){
            ToastUtils.showShort("请输入项目名称");
            return false;
        }else if (TextUtils.isEmpty(mEtBelong.getText().toString().trim())){
            ToastUtils.showShort("请输入项目归属者");
            return false;
        }else if (TextUtils.isEmpty(mEtStartTime.getText().toString().trim())){
            ToastUtils.showShort("请输入项目开始时间");
            return false;
        }else if (TextUtils.isEmpty(mEtEndTime.getText().toString().trim())){
            ToastUtils.showShort("请输入项目结束时间");
            return false;
        }else if (TextUtils.isEmpty(mEtProgress.getText().toString().trim())){
            ToastUtils.showShort("请输入项目当前进度");
            return false;
        }else if (TextUtils.isEmpty(projectName) || TextUtils.isEmpty(projectId)){
            ToastUtils.showShort("未选择归属项目");
            return false;
        }
        return true;
    }

    /**
     *
     * @param type          1.新增成功      2.修改成功      3.查询成功
     * @param entity
     */
    @Override
    public void onDoSuccess(int type, List<ProjectTeamEntity> entity) {
        EventBusHelper.onSendCode(Constant.CodeEvent.RATE_PROJECTTEAMUPDATE);
        Intent intent = new Intent(this, ProjectTeamListActivity.class);
        startActivity(intent);
    }

    @Override
    public void onGetRateSuccess(List<ProjectRateEntity> entities) {

    }

    /**
     *
     * @param type  失败来源：1. 新增来源         2.修改来源      3.查询来源
     * @param msg
     */
    @Override
    public void onFailed(int type, String msg) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventSubcriber(EventBusEntity entity) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusHelper.unRegister(this);
    }
}
