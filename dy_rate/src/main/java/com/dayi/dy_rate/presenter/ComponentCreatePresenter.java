package com.dayi.dy_rate.presenter;

import android.text.TextUtils;

import com.dayi.dy_rate.contract.UserContract;
import com.dayi.dy_rate.entity.RateBaseEntity;
import com.dayi.dy_rate.model.RateModel;
import com.dayi35.qx_base.http.RequestCallback;
import com.dayi35.qx_utils.common.ToastUtils;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2021/5/17 14:15
 * 描    述: 类    功能创建
 * 修订历史:
 * =========================================
 */
public class ComponentCreatePresenter extends BasePresentHttpImp<UserContract.ComponentUpdateContract.View> implements UserContract.ComponentUpdateContract.Presenter {
    private RateModel model;
    public ComponentCreatePresenter(UserContract.ComponentUpdateContract.View mView) {
        super(mView);
        model = RateModel.getInstance();
    }

    /**
     * @param id            功能ＩＤ        --->    新建/更新   功能
     * @param name          项目名
     * @param projectId     项目ID
     * @param startTime     开始时间
     * @param endTime       结束时间
     * @param moduleId      组件ID
     * @param remark        功能说明
     */
    @Override
    public void componentCreate(String id, String name, String projectId, String startTime, String endTime, String moduleId, String remark) {
        mView.showLoading();
        conver(model.componentCreate(id, name, projectId, startTime, endTime, moduleId, remark), new RequestCallback<RateBaseEntity<String>>() {
            @Override
            public void onSuccess(RateBaseEntity<String> entity) {
                mView.hideLoading();
                mView.onComponentCreated(TextUtils.isEmpty(id) ? 1 : 2, entity.getMessage());
            }

            @Override
            public void onError(String err) {
                mView.hideLoading();
                ToastUtils.showShort(err);
            }
        });
    }

    /**
     * @param id        功能ID    --->    更新功能进度
     * @param progress  进度
     * @param remark    更新说明
     */
    @Override
    public void componentUpdateProgress(String id, int progress, String remark) {
        mView.showLoading();
        conver(model.componentUpdateProgress(id, progress, remark), new RequestCallback<RateBaseEntity<String>>() {
            @Override
            public void onSuccess(RateBaseEntity<String> entity) {
                mView.hideLoading();
                mView.onUpdateProgress(entity.getMessage());
            }

            @Override
            public void onError(String err) {
                mView.hideLoading();
                ToastUtils.showShort(err);
            }
        });
    }
}
