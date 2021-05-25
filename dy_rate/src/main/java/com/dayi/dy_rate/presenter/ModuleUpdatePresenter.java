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
 * 创建日期: 2021/5/14 16:38
 * 描    述: 类    组件（模块）更新/创建
 * 修订历史:
 * =========================================
 */
public class ModuleUpdatePresenter extends BasePresentHttpImp<UserContract.ModuleUpdateContract.View> implements UserContract.ModuleUpdateContract.Presenter {
    private RateModel model;
    public ModuleUpdatePresenter(UserContract.ModuleUpdateContract.View mView) {
        super(mView);
        model = RateModel.getInstance();
    }

    /**
     *
     * @param id            组件ID            --->ID 为空 ？ 创建/更新组件
     * @param name          项目名
     * @param belongId      归属者
     * @param startTime     开始时间
     * @param endTime       结束时间
     * @param projectId     项目ID
     * @param remark        组件说明
     */
    @Override
    public void moduleCreate(String id, String name, String belongId, String startTime, String endTime, String projectId, String remark) {
        mView.showLoading();
        conver(model.moduleCreate(id, name, belongId, startTime, endTime, projectId, remark), new RequestCallback<RateBaseEntity<String>>() {
            @Override
            public void onSuccess(RateBaseEntity<String> entity) {
                mView.hideLoading();
                mView.onDoSuccess(TextUtils.isEmpty(id) ? 1 : 2, entity.getMessage());
            }

            @Override
            public void onError(String err) {
                mView.hideLoading();
                ToastUtils.showShort(err);
            }
        });
    }
}
