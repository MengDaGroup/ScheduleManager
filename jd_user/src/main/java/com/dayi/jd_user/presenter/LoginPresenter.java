package com.dayi.jd_user.presenter;

import com.dayi.jd_user.contract.UserContract;
import com.dayi35.qx_base.base.callback.OnJsonCallback;
import com.dayi35.qx_base.base.mvp.BasePresentImpl;
import com.dayi35.qx_base.entity.UserEntity;
import com.dayi35.qx_base.entity.jd_user_common;
import com.dayi35.qx_base.utils.GsonHelper;
import com.dayi35.qx_base.utils.UserHelper;
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
    public void login(UserEntity entity, String password) {
        entity.login(new SaveListener<UserEntity>() {
            @Override
            public void done(UserEntity entity, BmobException e) {
                if (null == e){
                    UserHelper.get().saveUserName(entity.getUsername());
                    UserHelper.get().savePassWord(password);
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
        getUserInfo(userId, null);
    }

    /**
     * 获取用户信息
     * @param userId
     * @param callback
     */
    public void getUserInfo(String userId, OnJsonCallback callback){
        BmobQuery<jd_user_common> query = new BmobQuery<>();
        query.addWhereEqualTo("user_id", userId);
        query.findObjects(new FindListener<jd_user_common>() {
            @Override
            public void done(List<jd_user_common> list, BmobException e) {
                if (null == e && list.size() > 0){
                    UserHelper.get().saveUser(list.get(0));
                    if (null != mView){
                        mView.onGetUserInfo(list.get(0));
                    }else if (null != callback){
                        callback.onSuccess(GsonHelper.toJson(list.get(0)));
                    }
                }else {
                    ToastUtils.showShort("登陆失败");
                }
            }
        });
    }
}
