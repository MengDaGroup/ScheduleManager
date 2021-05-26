package com.dayi.dy_rate.presenter;


import com.dayi.dy_rate.contract.UserContract;
import com.dayi.dy_rate.entity.ConditionsEntity;
import com.dayi.dy_rate.entity.ProjectEntity;
import com.dayi.dy_rate.entity.RateBaseEntity;
import com.dayi.dy_rate.entity.RateBaserPagerEntity;
import com.dayi.dy_rate.entity.TeamUserEntity;
import com.dayi.dy_rate.model.RateModel;
import com.dayi35.qx_base.constant.Constant;
import com.dayi35.qx_base.http.RequestCallback;
import com.dayi35.qx_base.utils.GsonHelper;
import com.dayi35.qx_utils.androidcodeutils.SPUtils;
import com.dayi35.qx_utils.common.ToastUtils;

import java.util.List;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2021/4/8 9:58
 * 描    述: 类        项目组
 * 修订历史:
 * =========================================
 */
public class ProjectListPresenter extends BasePresentHttpImp<UserContract.ProjectListContract.View> implements UserContract.ProjectListContract.Presenter {
    private RateModel model;
    public ProjectListPresenter(UserContract.ProjectListContract.View mView) {
        super(mView);
        model = RateModel.getInstance();
    }

    /**
     * 获取项目列表
     * @param name      项目名
     * @param status    状态：1.未开始 2.进行中 3.已结束 4.逾期
     * @param teamId    团队id
     * @param os        客户端类型: 1 - 安卓 2 - IOS
     */
    @Override
    public void projectGetList(String name, String status, String teamId, String os, int pageNo) {
        mView.showLoading();
        conver(model.projectGetList(name, status, teamId, os, pageNo), new RequestCallback<RateBaseEntity<RateBaserPagerEntity<ProjectEntity>>>() {
            @Override
            public void onSuccess(RateBaseEntity<RateBaserPagerEntity<ProjectEntity>> entity) {
                mView.hideLoading();
                mView.onGetProjectList(entity.getData());
            }

            @Override
            public void onError(String err) {
                mView.hideLoading();
                ToastUtils.showShort(err);
            }
        });
    }

    /**
     * 获取项目过滤条件
     */
    @Override
    public void projectGetConditions() {
        mView.showLoading();
        conver(model.projectGetConditions(), new RequestCallback<RateBaseEntity<ConditionsEntity>>() {
            @Override
            public void onSuccess(RateBaseEntity<ConditionsEntity> entity) {
                mView.hideLoading();
                mView.onGetConditions(entity.getData());
            }

            @Override
            public void onError(String err) {
                mView.hideLoading();
                ToastUtils.showShort(err);
            }
        });
    }

    /**
     * 登出
     */
    @Override
    public void logout() {
        mView.showLoading();
        conver(model.userLogout(), new RequestCallback<RateBaseEntity<Object>>() {
            @Override
            public void onSuccess(RateBaseEntity<Object> entity) {
                mView.hideLoading();
                mView.onLogoutSuccess();
            }

            @Override
            public void onError(String err) {
                mView.hideLoading();
                ToastUtils.showShort(err);
            }
        });
    }

    /**
     * 获取成员数据
     */
    @Override
    public void memberList() {
        mView.showLoading();
        conver(model.memberList(), new RequestCallback<RateBaseEntity<List<TeamUserEntity>>>() {
            @Override
            public void onSuccess(RateBaseEntity<List<TeamUserEntity>> entity) {
                mView.hideLoading();
                mView.onGetMemberList(entity.getData());
                SPUtils.getInstance().put(Constant.KeySPUtils.RATE_SP_TEAM, GsonHelper.toJson(entity.getData()));
            }

            @Override
            public void onError(String err) {
                mView.hideLoading();
                ToastUtils.showShort(err);
            }
        });
    }
}
