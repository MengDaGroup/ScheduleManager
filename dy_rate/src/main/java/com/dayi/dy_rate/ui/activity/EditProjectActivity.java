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
import com.dayi.dy_rate.presenter.ProjectPresenter;
import com.dayi.dy_rate.utils.UserRateHelper;
import com.dayi35.qx_base.base.state.BaseStateActivity;
import com.dayi35.qx_base.utils.GsonHelper;
import com.dayi35.qx_utils.common.DateUtil;
import com.dayi35.qx_utils.common.StatusBarUtil;
import com.dayi35.qx_utils.common.ToastUtils;
import com.dayi35.qx_utils.convert.RCaster;
import com.dayi35.qx_widget.titlebar.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2021/4/9 10:24
 * 描    述: 类        项目编辑 type 1.新增  2.修改
 * 修订历史:
 * =========================================
 */
public class EditProjectActivity extends BaseStateActivity<ProjectPresenter> implements UserContract.ProjectContract.View {
    private final int TYPEADD = 1;              //添加
    private final int TYPEUPDATE = 2;           //更新

    @BindView(R2.id.rate_tb_project_edit) TitleBar mTbTitle;            //title
    @BindView(R2.id.rate_btn_save) Button mBtnAdd;                      //添加/修改
    @BindView(R2.id.rate_et_name) EditText mEtName;                     //项目名称
    @BindView(R2.id.rate_et_belong) EditText mEtBelong;                 //项目归属
    @BindView(R2.id.rate_et_starttime) EditText mEtStartTime;           //项目开始时间
    @BindView(R2.id.rate_et_endtime) EditText mEtEndTime;               //项目结束时间
    @BindView(R2.id.rate_et_progress) EditText mEtProgress;             //项目进度
    @BindView(R2.id.rate_et_note) EditText mEtNote;                     //项目备注
    @BindView(R2.id.rate_tv_noted) TextView mTvNoted;                   //项目已有备注

    private int type;                                                   //界面 1.新增  2.修改
    private String mProjectJson;                                        //projectJson
    private String mTeamId;                                             //项目组ID
    private ProjectEntity mProjectEntity;                               //项目实例
    private List<String> listNote = new ArrayList<>();                  //备注列表

    @Override
    protected int getMainLayoutId() {
        return R.layout.rate_activity_project_edit;
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
        mBtnAdd.setOnClickListener(v -> {
            if (verifyInput()){
                if (TYPEADD == type){           //添加
                    getP().create(buildProjectEntity());
                }else if (TYPEUPDATE == type){  //更新
                    getP().update(buildProjectEntity());
                }
            }
        });
    }

    @Override
    protected void initData() {
        type = getIntent().getIntExtra("type", TYPEADD);
        mTeamId = getIntent().getStringExtra("project_team_id");
        //更新
        if (TYPEUPDATE == type) {
            //得到界面传值
            mProjectJson = getIntent().getStringExtra("project");
            if (!TextUtils.isEmpty(mProjectJson)) {
                //解析成实体
                mProjectEntity = GsonHelper.toType(mProjectJson, ProjectEntity.class);
            }
            mEtName.setEnabled(false);
            mEtName.setText(mProjectEntity.getProjectName());
            mEtBelong.setEnabled(false);
            mEtBelong.setText(mProjectEntity.getProjectBelong());
            mEtStartTime.setEnabled(false);
            mEtStartTime.setText(mProjectEntity.getProjectStartTime());
            mEtEndTime.setEnabled(false);
            mEtEndTime.setText(mProjectEntity.getProjectEndTime());
            listNote.addAll(mProjectEntity.getProjectNote());
        }else {//添加
            mEtName.setEnabled(true);
            mEtBelong.setEnabled(true);
            mEtStartTime.setEnabled(true);
            mEtEndTime.setEnabled(true);
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
    private ProjectEntity buildProjectEntity(){
        if (TYPEADD == type){
            mProjectEntity = new ProjectEntity();
            mProjectEntity.setCreateUser(UserRateHelper.get().getUserNickName());
            mProjectEntity.setCreateTime(DateUtil.formatByTimeStamp(System.currentTimeMillis(), DateUtil.mFormat_date_24));
            mProjectEntity.setProjectState(1);       //创建的时候设置状态为进行中
            mProjectEntity.setProjectOS("1");        //设置为Android端
            mProjectEntity.setProjectTeamId(mTeamId);
        }
        mProjectEntity.setProjectName(mEtName.getText().toString().trim());
        mProjectEntity.setProjectBelong(mEtBelong.getText().toString().trim());
        mProjectEntity.setProjectStartTime(mEtStartTime.getText().toString().trim());
        mProjectEntity.setProjectEndTime(mEtEndTime.getText().toString().trim());
        mProjectEntity.setProjectProgress(mEtProgress.getText().toString().trim());
        listNote.add(mEtNote.getText().toString().trim() + "\t\t\t\t- - -由" + UserRateHelper.get().getUserNickName() + "更新于:\t" + DateUtil.formatByTimeStamp(System.currentTimeMillis(), DateUtil.mFormat_24_minute));
        mProjectEntity.setProjectNote(listNote);
        mProjectEntity.setUpdateUser(UserRateHelper.get().getUserNickName());
        mProjectEntity.setUpdateTime(DateUtil.formatByTimeStamp(System.currentTimeMillis(), DateUtil.mFormat_date_24));
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
        }else if (TextUtils.isEmpty(mEtNote.getText().toString().trim())){
            ToastUtils.showShort("请输入提交说明");
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
    public void onDoSuccess(int type, List<ProjectEntity> entity) {
        Intent intent = new Intent(this, ProjectTeamListActivity.class);
        startActivity(intent);
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
