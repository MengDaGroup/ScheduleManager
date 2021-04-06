package com.dayi35.qx_base.base.mvp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dayi35.qx_base.R;
import com.dayi35.qx_base.arouter.ARouterHelper;
import com.dayi35.qx_utils.convert.RCaster;
import com.dayi35.qx_widget.dialog.LoadingProgress;

import butterknife.ButterKnife;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2020/5/11 17:21
 * 描    述: 基础fragment类 1.绑定黄油刀  2. 绑定路由
 * 修订历史:
 * =========================================
 */
public abstract class BaseFragment<P extends BasePresent> extends Fragment implements BaseView {
    protected P mPresent;
    protected RCaster caster;
    private LoadingProgress progress;
    protected boolean onAttached = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getMainLayoutId(), container , false);
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //TODO 当 fragment 贴入activity的时候
        onAttached = true;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        ARouterHelper.injectFragment(this);
        //业务
        mPresent = createPresent();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initUI();
        initData();
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
     * 获取反射得到的id
     * @return
     */
    protected RCaster getCaster(){
        if (null == caster){
            caster = createCaster();
        }
        return caster;
    }

    @Override
    public void onDestroy() {
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

    @Override
    public void showLoading() {
        if (null == progress){
            progress = new LoadingProgress(getActivity(), R.style.base_loading_style_dialog);
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

    //获取xml id
    protected abstract int getMainLayoutId();
    //创建业务逻辑类
    protected abstract P createPresent();
    //创建id反射类(解决黄油刀R的id不为final的问题)
    protected abstract RCaster createCaster();
    //初始化UI
    protected abstract void initUI();
    //初始化数据
    protected abstract void initData();
}
