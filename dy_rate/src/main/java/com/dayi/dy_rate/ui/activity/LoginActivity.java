package com.dayi.dy_rate.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;

import com.dayi.dy_rate.R;
import com.dayi.dy_rate.R2;
import com.dayi.dy_rate.contract.UserContract;
import com.dayi.dy_rate.entity.TeamUserEntity;
import com.dayi.dy_rate.entity.UserEntity;
import com.dayi.dy_rate.presenter.LoginPresenter;
import com.dayi.dy_rate.utils.UserRateHelper;
import com.dayi35.qx_base.base.state.BaseStateActivity;
import com.dayi35.qx_utils.common.StatusBarUtil;
import com.dayi35.qx_utils.common.ToastUtils;
import com.dayi35.qx_utils.common.TypefaceUtil;
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
    @BindView(R2.id.user_tv_sign)
    TextView mTvSign;                       //标签
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
        mTvSign.setTypeface(TypefaceUtil.getTypeface(TypefaceUtil.TYPE_NUMBERBLOAD));
    }

    @Override
    protected void initData() {
        //如果已登录过，直接跳转到列表界面
        if (!TextUtils.isEmpty(UserRateHelper.get().getPassWord()) &&
                !TextUtils.isEmpty(UserRateHelper.get().getUserName()) &&
                null != UserRateHelper.get().getUserName()){
            if (verifyInput()) {
                getP().login(mUserName, mPassword);
            }
        }
        //登录按钮的点击事件
        mBtnLogin.setOnClickListener(v -> {
            if (verifyInput()) {
                getP().login(mUserName, mPassword);
            }
        });
    }

    @Override
    protected TitleBar titleBar() {
        return null;
    }

    /**
     * 获取用户信息成功(暂无用)
     * @param entity
     */
    @Override
    public void onGetUserInfo(UserEntity entity) {

    }

    /**
     * 登录成功
     */
    @Override
    public void onLoginSuccess() {
        Intent intent = new Intent(LoginActivity.this, ProjectListActivity.class);
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
