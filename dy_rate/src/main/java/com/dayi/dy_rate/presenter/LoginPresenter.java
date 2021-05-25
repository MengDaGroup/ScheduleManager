package com.dayi.dy_rate.presenter;

import com.dayi.dy_rate.model.RateModel;
import com.dayi.dy_rate.contract.UserContract;
import com.dayi.dy_rate.entity.DyUser;
import com.dayi.dy_rate.entity.RateBaseEntity;
import com.dayi.dy_rate.entity.UserEntity;
import com.dayi.dy_rate.utils.UserRateHelper;
import com.dayi35.qx_base.http.RequestCallback;
import com.dayi35.qx_utils.common.ToastUtils;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2021/3/31 16:44
 * 描    述: 类
 * 修订历史:
 * =========================================
 */
public class LoginPresenter extends BasePresentHttpImp<UserContract.LoginView> implements UserContract.LoginPresenter {
    private RateModel model;
    public LoginPresenter(UserContract.LoginView mView) {
        super(mView);
        model = RateModel.getInstance();
    }

    /**
     * 用户登录
     * @param account       账号
     * @param password      密码
     */
    @Override
    public void login(String account, String password) {
        mView.showLoading();
        conver(model.userLogin(account, password), new RequestCallback<RateBaseEntity<DyUser>>() {
            @Override
            public void onSuccess(RateBaseEntity<DyUser> entity) {
                mView.hideLoading();
                UserRateHelper.get().saveUserName(account);
                UserRateHelper.get().savePassWord(password);
                mView.onLoginSuccess();
            }

            @Override
            public void onError(String err) {
                mView.hideLoading();
                ToastUtils.showShort(err);
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
