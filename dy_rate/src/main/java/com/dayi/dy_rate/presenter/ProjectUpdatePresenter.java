package com.dayi.dy_rate.presenter;

import com.dayi.dy_rate.contract.UserContract;
import com.dayi.dy_rate.entity.RateBaseEntity;
import com.dayi.dy_rate.model.RateModel;
import com.dayi35.qx_base.http.RequestCallback;
import com.dayi35.qx_utils.common.ToastUtils;


/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2021/5/12 14:41
 * 描    述: 类        项目新增/编辑
 * 修订历史:
 * =========================================
 */
public class ProjectUpdatePresenter extends BasePresentHttpImp<UserContract.ProjectContract.CreateView> implements UserContract.ProjectContract.CreatePresenter {
    private RateModel model;

    public ProjectUpdatePresenter(UserContract.ProjectContract.CreateView mView) {
        super(mView);
        model = RateModel.getInstance();
    }

    /**
     * 新增/编辑    项目
     * @param type          1.新增项目  2.编辑项目
     * @param name          项目名
     * @param belongId      归属者
     * @param startTime     开始时间
     * @param endTime       结束时间
     * @param os            端
     * @param teamId        团队
     * @param remark        备注描述
     */
    @Override
    public void projectCreate(int type, String name, String belongId, String startTime, String endTime, int os, String teamId, String remark) {
        mView.showLoading();
        conver(model.projectCreate(name, belongId, startTime, endTime, os, teamId, remark), new RequestCallback<RateBaseEntity<String>>() {
            @Override
            public void onSuccess(RateBaseEntity<String> entity) {
                mView.hideLoading();
                mView.onCommonSuccess(type, entity.getMessage());
            }

            @Override
            public void onError(String err) {
                mView.hideLoading();
                ToastUtils.showShort(err);
            }
        });
    }
}
