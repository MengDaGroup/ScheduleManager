package com.dayi.dy_rate.presenter;

import com.dayi.dy_rate.contract.UserContract;
import com.dayi.dy_rate.entity.ModuleEntity;
import com.dayi.dy_rate.entity.ProjectDetailEntity;
import com.dayi.dy_rate.entity.RateBaseEntity;
import com.dayi.dy_rate.entity.RateBaserPagerEntity;
import com.dayi.dy_rate.model.RateModel;
import com.dayi35.qx_base.http.RequestCallback;
import com.dayi35.qx_utils.common.ToastUtils;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2021/5/13 11:21
 * 描    述: 类    项目详情--组件列表（模块）
 * 修订历史:
 * =========================================
 */
public class ProjectDetailPresenter extends BasePresentHttpImp<UserContract.ProjectDetailContract.View> implements UserContract.ProjectDetailContract.Presenter {
    private RateModel model;
    public ProjectDetailPresenter(UserContract.ProjectDetailContract.View mView) {
        super(mView);
        model = RateModel.getInstance();
    }

    /**
     * 查询项目详情
     * @param id
     */
    @Override
    public void projectConsult(String id) {
        mView.showLoading();
        conver(model.projectConsult(id), new RequestCallback<RateBaseEntity<ProjectDetailEntity>>() {
            @Override
            public void onSuccess(RateBaseEntity<ProjectDetailEntity> entity) {
                mView.hideLoading();
                mView.onProjectConsult(entity.getData());

            }

            @Override
            public void onError(String err) {
                mView.hideLoading();
                ToastUtils.showShort(err);
            }
        });
    }

    /**
     * 获取组件列表（模块）
     * @param name          项目名
     * @param projectId     项目ID
     * @param os            端
     */
    @Override
    public void moudleGetList(String name, String projectId, int os, int pageNo) {
        mView.showLoading();
        conver(model.moudleGetList(name, projectId, os, pageNo), new RequestCallback<RateBaseEntity<RateBaserPagerEntity<ModuleEntity>>>() {
            @Override
            public void onSuccess(RateBaseEntity<RateBaserPagerEntity<ModuleEntity>> entity) {
                mView.hideLoading();
                mView.onGetMoudleList(entity.getData());
            }

            @Override
            public void onError(String err) {
                mView.hideLoading();
                ToastUtils.showShort(err);
            }
        });
    }
}
