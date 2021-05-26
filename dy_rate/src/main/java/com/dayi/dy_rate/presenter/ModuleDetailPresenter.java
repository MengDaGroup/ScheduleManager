package com.dayi.dy_rate.presenter;

import com.dayi.dy_rate.contract.UserContract;
import com.dayi.dy_rate.entity.ComponentEntity;
import com.dayi.dy_rate.entity.ModuleDetailEntity;
import com.dayi.dy_rate.entity.RateBaseEntity;
import com.dayi.dy_rate.entity.RateBaserPagerEntity;
import com.dayi.dy_rate.model.RateModel;
import com.dayi35.qx_base.http.RequestCallback;
import com.dayi35.qx_utils.common.ToastUtils;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2021/5/14 10:40
 * 描    述: 类        组件详情界面（模块）
 * 修订历史:
 * =========================================
 */
public class ModuleDetailPresenter extends BasePresentHttpImp<UserContract.ModuleDetailContract.View> implements UserContract.ModuleDetailContract.Presenter {
    private RateModel model;
    public ModuleDetailPresenter(UserContract.ModuleDetailContract.View mView) {
        super(mView);
        model = RateModel.getInstance();
    }

    /**
     *
     * @param id    获取组件（模块详情） ---> 组件ID
     */
    @Override
    public void moduleGetDetail(String id) {
        mView.showLoading();
        conver(model.moduleGetDetail(id), new RequestCallback<RateBaseEntity<ModuleDetailEntity>>() {
            @Override
            public void onSuccess(RateBaseEntity<ModuleDetailEntity> entity) {
                mView.hideLoading();
                mView.onGetModuleDetail(entity.getData());
            }

            @Override
            public void onError(String err) {
                mView.hideLoading();
                ToastUtils.showShort(err);
            }
        });
    }

    /**
     *
     * @param name          模块名         ---> 获取功能列表
     * @param projectId     项目ID
     * @param moduleId      模块ID
     * @param page          页码
     */
    @Override
    public void componentGetList(String name, String projectId, String moduleId, int page) {
        mView.showLoading();
        conver(model.componentGetList(name, projectId, moduleId, page), new RequestCallback<RateBaseEntity<RateBaserPagerEntity<ComponentEntity>>>() {
            @Override
            public void onSuccess(RateBaseEntity<RateBaserPagerEntity<ComponentEntity>> entity) {
                mView.hideLoading();
                mView.onGetComponetList(entity.getData());
            }

            @Override
            public void onError(String err) {
                mView.hideLoading();
                ToastUtils.showShort(err);
            }
        });
    }

    /**
     * 删除功能
     * @param id
     */
    @Override
    public void componentDelete(String id) {
        mView.showLoading();
        conver(model.componentDelete(id), new RequestCallback<RateBaseEntity<String>>() {
            @Override
            public void onSuccess(RateBaseEntity<String> entity) {
                mView.hideLoading();
                mView.onComponentDelete(entity.getMessage());
            }

            @Override
            public void onError(String err) {
                mView.hideLoading();
                ToastUtils.showShort(err);
            }
        });
    }
}
