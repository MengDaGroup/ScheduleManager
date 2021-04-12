package com.dayi.dy_rate.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.Button;

import com.dayi.dy_rate.R;
import com.dayi.dy_rate.R2;
import com.dayi.dy_rate.contract.UserContract;
import com.dayi.dy_rate.entity.DyUser;
import com.dayi.dy_rate.entity.UserEntity;
import com.dayi.dy_rate.presenter.LoginPresenter;
import com.dayi.dy_rate.utils.UserRateHelper;
import com.dayi35.qx_base.arouter.ARouterHelper;
import com.dayi35.qx_base.arouter.ARouterPath;
import com.dayi35.qx_base.base.state.BaseStateActivity;
import com.dayi35.qx_base.utils.UserHelper;
import com.dayi35.qx_utils.common.StatusBarUtil;
import com.dayi35.qx_utils.common.ToastUtils;
import com.dayi35.qx_utils.convert.RCaster;
import com.dayi35.qx_widget.titlebar.TitleBar;
import com.dayi35.qx_widget.widget.CusEditText;

import butterknife.BindView;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2021/4/7 10:57
 * 描    述: 类
 * 修订历史:
 * =========================================
 */
public class LoginActivity extends BaseStateActivity<LoginPresenter> implements UserContract.LoginView {
    @BindView(R2.id.user_et_phone)
    CusEditText mCetUserName;               //用户名
    @BindView(R2.id.user_et_password)
    CusEditText mCetPassword;               //密码
    @BindView(R2.id.rate_user_login)
    Button mBtnLogin;                       //登录按钮

    private String mUserName;               //用户名
    private String mPassword;               //密码

    @Override
    protected int getMainLayoutId() {
        return R.layout.rate_activity_login;
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
        StatusBarUtil.transparent(this);
        StatusBarUtil.setDarkMode(this);
    }

    @Override
    protected void initData() {

        if (!TextUtils.isEmpty(UserRateHelper.get().getPassWord()) &&
                !TextUtils.isEmpty(UserRateHelper.get().getUserName()) &&
                null != UserRateHelper.get().getUserName()){
            Intent intent = new Intent(this, ProjectTeamListActivity.class);
            startActivity(intent);
            this.finish();
        }

        mBtnLogin.setOnClickListener(v -> {
            if (verifyInput()) {
                DyUser entity = new DyUser();
                entity.setUsername(mUserName);
                entity.setPassword(mPassword);
                getP().login(entity, mPassword);
            }
        });
    }

    @Override
    protected TitleBar titleBar() {
        return null;
    }

    @Override
    public void onGetUserInfo(UserEntity entity) {
//        ARouterHelper.navigation(ARouterPath.Project.ProjectList.path);
        Intent intent = new Intent(LoginActivity.this, ProjectTeamListActivity.class);
        startActivity(intent);
    }

    /**
     * 检测输入情况
     * @return
     */
    private boolean verifyInput(){
        mUserName = mCetUserName.getText().toString().trim();
        mPassword = mCetPassword.getText().toString().trim();
        if (TextUtils.isEmpty(mUserName)){
            ToastUtils.showShort("请输入用户名");
            return false;
        }else if (TextUtils.isEmpty(mPassword)){
            ToastUtils.showShort("请输入密码");
            return false;
        }
        return true;
    }
}
