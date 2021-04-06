package com.dayi.jd_user.ui.activity;

import android.text.TextUtils;
import android.widget.Button;

import com.dayi.jd_user.R;
import com.dayi.jd_user.R2;
import com.dayi.jd_user.contract.UserContract;
import com.dayi.jd_user.presenter.LoginPresenter;
import com.dayi35.qx_base.base.state.BaseStateActivity;
import com.dayi35.qx_base.entity.UserEntity;
import com.dayi35.qx_base.entity.jd_user_common;
import com.dayi35.qx_utils.common.ToastUtils;
import com.dayi35.qx_utils.convert.RCaster;
import com.dayi35.qx_widget.titlebar.TitleBar;
import com.dayi35.qx_widget.widget.CusEditText;

import butterknife.BindView;
/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2021/3/31 16:44
 * 描    述: 类    用户登录界面
 * 修订历史:
 * =========================================
 */
public class UserLoginActivity extends BaseStateActivity<LoginPresenter> implements UserContract.LoginView {
    @BindView(R2.id.jduser_login)
    Button mBtnLogin;
    @BindView(R2.id.user_et_phone)
    CusEditText mEtPhone;           //手机号
    @BindView(R2.id.user_et_password)
    CusEditText mEtPassword;        //密码

    private String mUserName;
    private String mPassword;
    @Override
    protected int getMainLayoutId() {
        return R.layout.jduser_activity_login;
    }

    @Override
    protected void initStateLayout() {
        getStateManager();
    }

    @Override
    protected LoginPresenter createPresent() {
        return new LoginPresenter(this);
    }

    @Override
    protected RCaster createCaster() {
        return new RCaster(R.class, R2.class);
    }

    @Override
    protected void initView() {
        mBtnLogin.setOnClickListener(v -> {
            if (verifyInput()) {
                UserEntity entity = new UserEntity();
                entity.setUsername(mUserName);
                entity.setPassword(mPassword);
                getP().login(entity, mPassword);
            }
        });
    }

    /**
     * 检测输入情况
     * @return
     */
    private boolean verifyInput(){
        mUserName = mEtPhone.getText().toString().trim();
        mPassword = mEtPassword.getText().toString().trim();
        if (TextUtils.isEmpty(mUserName)){
            ToastUtils.showShort("请输入手机号");
            return false;
        }else if (TextUtils.isEmpty(mPassword)){
            ToastUtils.showShort("请输入密码");
            return false;
        }
        return true;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected TitleBar titleBar() {
        return null;
    }

    @Override
    public void onGetUserInfo(jd_user_common entity) {

    }
}
