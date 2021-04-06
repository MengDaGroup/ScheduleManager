package com.dayi35.qx_base.base.state;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dayi35.qx_base.R;
import com.dayi35.qx_base.arouter.ARouterHelper;
import com.dayi35.qx_base.base.mvp.BasePresent;
import com.dayi35.qx_base.base.mvp.BaseView;
import com.dayi35.qx_utils.convert.RCaster;
import com.dayi35.qx_widget.dialog.LoadingProgress;
import com.dayi35.qx_widget.state.StateLayoutManager;
import com.dayi35.qx_widget.titlebar.TitleBar;

import butterknife.ButterKnife;


/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2020/11/13 13:43
 * 描    述: 类 activity基类 1.绑定黄油刀 2.绑定路由 3.禁止横屏  其他方法具体看方法的注释
 * 修订历史:
 * =========================================
 */
public abstract class BaseStateActivity<P extends BasePresent> extends AppCompatActivity implements BaseView {

    protected P mPresent;
    protected RCaster caster;
    protected StateLayoutManager statusLayoutManager;
    private LoadingProgress progress;

    //获取xml id
    protected abstract int getMainLayoutId();
    //状态切换
    protected abstract void initStateLayout();
    //创建业务逻辑类
    protected abstract P createPresent();
    //创建id反射类(解决黄油刀R的id不为final的问题)
    protected abstract RCaster createCaster();
    //初始化视图控件
    protected abstract void initView();
    //初始化数据
    protected abstract void initData();
    //获取界面的titlebar 控件
    protected abstract TitleBar titleBar();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_activity_base_state);
        initStateLayout();
        addMainView();
        ButterKnife.bind(this);
        ARouterHelper.injectActivity(this);
        //业务
        mPresent = createPresent();
        //避免切换横竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //初始化控件
        initView();
        //初始化数据
        initData();
        //初始化title
        initTitle();
    }

    /**
     * 添加主布局
     */
    private void addMainView() {
        LinearLayout flMain = findViewById(R.id.common_ll_base_state_main);
        flMain.addView(getStateManager().getRootLayout());
    }

    /**
     * 获取业务逻辑处理对象
     * @return mPresent
     */
    protected P getP() {
        if (null == mPresent) {
            mPresent = createPresent();
        }
        return mPresent;
    }

    /**
     * 获取状态管理器
     * @return
     */
    protected StateLayoutManager getStateManager(){
        if (null == statusLayoutManager){
            statusLayoutManager = StateLayoutManager.newBuilder(this)
                    .contentView(getMainLayoutId())
                    .build();
        }
        return statusLayoutManager;
    }

    /**
     * 获取反射得到的id
     * @return
     */
    protected RCaster getCaster(){
        if (null == caster){
            caster = createCaster();
        }
        return caster;
    }

    /**title 点击事件*/
    private void initTitle(){
        if (null != titleBar()){
            titleBar().setmClick(new TitleBar.onViewClick() {
                @Override
                public void leftClick() {
                    onLeftClicke();
                }

                @Override
                public void rightClick() {
                    onRightClick();
                }
            });
        }
    }

    @Override
    public void showLoading() {
        if (null == progress){
            progress = new LoadingProgress(this, R.style.base_loading_style_dialog);
        }
        if (!progress.isShowing()){
            progress.show();
        }
    }

    @Override
    public void hideLoading() {
        if (null != progress && progress.isShowing()){
            progress.dismiss();
        }
    }

    protected void onLeftClicke(){
        this.finish();
    }

    protected void onRightClick(){}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mPresent){
            mPresent.unSubscribe();
        }
        hideLoading();
        progress = null;
        if (null != caster){
            caster = null;
        }
    }
}
