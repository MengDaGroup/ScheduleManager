package com.dayi.dy_rate.presenter;

import com.dayi.dy_rate.contract.UserContract;
import com.dayi.dy_rate.entity.DyUser;
import com.dayi.dy_rate.entity.UserEntity;
import com.dayi.dy_rate.utils.UserRateHelper;
import com.dayi35.qx_base.base.callback.OnJsonCallback;
import com.dayi35.qx_base.base.mvp.BasePresentImpl;
import com.dayi35.qx_base.utils.GsonHelper;
import com.dayi35.qx_utils.common.ToastUtils;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2021/3/31 16:44
 * 描    述: 类
 * 修订历史:
 * =========================================
 */
public class LoginPresenter extends BasePresentImpl<UserContract.LoginView> implements UserContract.LoginPresenter {

    public LoginPresenter(UserContract.LoginView mView) {
        super(mView);
    }

    /**
     * 登录
     * @param entity
     * @param password
     */
    @Override
    public void login(DyUser entity, String password) {
        mView.showLoading();
        entity.login(new SaveListener<DyUser>() {
            @Override
            public void done(DyUser entity, BmobException e) {
                mView.hideLoading();
                if (null == e){
                    UserRateHelper.get().saveUserName(entity.getUsername());
                    UserRateHelper.get().savePassWord(password);
                    getUserInfo(entity.getObjectId());
                }else {
                    ToastUtils.showShort("登陆失败");
                }
            }
        });
    }

    /**
     * 获取用户信息
     * @param userId
     */
    @Override
    public void getUserInfo(String userId) {
        mView.showLoading();
        BmobQuery<UserEntity> query = new BmobQuery<>();
        query.addWhereEqualTo("userId", userId);
        query.findObjects(new FindListener<UserEntity>() {
            @Override
            public void done(List<UserEntity> list, BmobException e) {
                mView.hideLoading();
                if (null == e && list.size() > 0){
                    UserRateHelper.get().saveUser(list.get(0));
                    mView.onGetUserInfo(list.get(0));
                }else {
                    ToastUtils.showShort("登陆失败");
                }
            }
        });
    }

}
