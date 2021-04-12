package com.dayi.dy_rate.ui.activity;

import android.content.Intent;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dayi.dy_rate.R;
import com.dayi.dy_rate.R2;
import com.dayi.dy_rate.contract.UserContract;
import com.dayi.dy_rate.entity.ProjectEntity;
import com.dayi.dy_rate.entity.ProjectTeamEntity;
import com.dayi.dy_rate.presenter.ProjectPresenter;
import com.dayi.dy_rate.presenter.ProjectTeamPresenter;
import com.dayi.dy_rate.ui.adapter.ProjectAdapter;
import com.dayi.dy_rate.ui.adapter.ProjectTeamAdapter;
import com.dayi35.qx_base.arouter.ARouterPath;
import com.dayi35.qx_base.base.state.BaseStateActivity;
import com.dayi35.qx_base.beans.EventBusEntity;
import com.dayi35.qx_base.constant.Constant;
import com.dayi35.qx_base.utils.EventBusHelper;
import com.dayi35.qx_base.utils.GsonHelper;
import com.dayi35.qx_utils.common.StatusBarUtil;
import com.dayi35.qx_utils.convert.RCaster;
import com.dayi35.qx_widget.titlebar.TitleBar;
import com.dayi35.recycle.view.DYRefreshView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    @BindView(R2.id.rate_rv_project)
    DYRefreshView mRvProject;                   //列表
    @BindView(R2.id.rate_btn_add)
    Button mBtnAdd;                             //添加按钮

    private ProjectTeamAdapter mAdapter;            //适配器

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
        mAdapter = new ProjectTeamAdapter(this);
        mRvProject.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(position -> {
            Intent intent = new Intent(ProjectTeamListActivity.this, ProjectListActivity.class);
            intent.putExtra("project", GsonHelper.toJson(mAdapter.getAllData().get(position)));
            intent.putExtra("project_team_id", mAdapter.getAllData().get(position).getObjectId());
            intent.putExtra("title", mAdapter.getAllData().get(position).getProjectName());
            startActivity(intent);
        });

        mBtnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(ProjectTeamListActivity.this, EditProjectTeamActivity.class);
            startActivity(intent);
        });

        refresh();
    }

    @Override
    protected TitleBar titleBar() {
        return null;
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
        }
    }

    /**
     * 适配数据
     * @param entity
     */
    private void adapterEntity(List<ProjectTeamEntity> entity) {
        mAdapter.addAll(entity);
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
     * 刷新
     */
    private void refresh(){
        mAdapter.clear();
        getP().search(new ProjectTeamEntity());
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
