package com.dayi.dy_rate.presenter;

import com.dayi.dy_rate.contract.UserContract;
import com.dayi.dy_rate.entity.ComponentDetialEntity;
import com.dayi.dy_rate.entity.LogUpdateEntity;
import com.dayi.dy_rate.entity.RateBaseEntity;
import com.dayi.dy_rate.entity.RateBaserPagerEntity;
import com.dayi.dy_rate.model.RateModel;
import com.dayi35.qx_base.http.RequestCallback;
import com.dayi35.qx_utils.common.ToastUtils;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2021/5/17 14:22
 * 描    述: 类        功能详情
 * 修订历史:
 * =========================================
 */
public class ComponentDetailPresenter extends BasePresentHttpImp<UserContract.ComponentDetailContract.View> implements UserContract.ComponentDetailContract.Presenter {
    private RateModel model;
    public ComponentDetailPresenter(UserContract.ComponentDetailContract.View mView) {
        super(mView);
        model = RateModel.getInstance();
    }

    /**
     * @param id    功能ID    ---> 根据功能ID查询功能详情
     */
    @Override
    public void componentGetDetail(String id) {
        mView.showLoading();
        conver(model.componentGetDetail(id), new RequestCallback<RateBaseEntity<ComponentDetialEntity>>() {
            @Override
            public void onSuccess(RateBaseEntity<ComponentDetialEntity> entity) {
                mView.hideLoading();
                mView.onGetComponentDetail(entity.getData());
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
     * @param page          页码      --->获取功能更新日志
     * @param id            功能ＩＤ
     */
    @Override
    public void logGetList(int page, String id) {
        mView.showLoading();
        conver(model.logGetList(page, id), new RequestCallback<RateBaseEntity<RateBaserPagerEntity<LogUpdateEntity>>>() {
            @Override
            public void onSuccess(RateBaseEntity<RateBaserPagerEntity<LogUpdateEntity>> entity) {
                mView.hideLoading();
                mView.onGetLogList(entity.getData());
            }

            @Override
            public void onError(String err) {
                mView.hideLoading();
                ToastUtils.showShort(err);
            }
        });
    }
}
