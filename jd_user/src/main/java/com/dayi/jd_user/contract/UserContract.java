package com.dayi.jd_user.contract;

import com.dayi35.qx_base.base.mvp.BaseView;
import com.dayi35.qx_base.entity.UserEntity;
import com.dayi35.qx_base.entity.jd_user_common;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2021/3/31 16:45
 * 描    述: 类    用户逻辑管理类
 * 修订历史:
 * =========================================
 */
public interface UserContract {
    /**
     * 登录
     */
    interface LoginPresenter{
        /**
         * 登录
         * @param entity
         */
        void login(UserEntity entity, String password);

        /**
         * 获取用户信息
         * @param userId
         */
        void getUserInfo(String userId);
    }

    interface LoginView extends BaseView{
        void onGetUserInfo(jd_user_common entity);
    }

}
