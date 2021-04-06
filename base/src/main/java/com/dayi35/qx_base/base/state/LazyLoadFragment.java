package com.dayi35.qx_base.base.state;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.dayi35.qx_base.base.mvp.BaseFragment;
import com.dayi35.qx_base.base.mvp.BasePresent;

import java.util.List;

/**
 * created by yao on 2021/2/2
 * Describe:延迟加载，当真正可见的时候才加载数据
 **/
public abstract class LazyLoadFragment<P extends BasePresent> extends BaseFragment {

    private boolean mIsViewCreated; // 界面是否已创建完成
    private boolean mIsVisibleToUser; // 是否对用户可见
    private boolean mIsDataLoaded; // 数据是否已请求, isNeedReload()返回false的时起作用
    private boolean mIsHidden = true; // 记录当前fragment的是否隐藏

    // 实现具体的数据请求逻辑
    protected abstract void loadData();

    /**
     * 使用ViewPager嵌套fragment时，切换ViewPager回调该方法
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        this.mIsVisibleToUser = isVisibleToUser;

        tryLoadData();
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mIsViewCreated = true;

        tryLoadData();
    }

    /**
     * 使用show()、hide()控制fragment显示、隐藏时回调该方法
     *
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        mIsHidden = hidden;
        if (!hidden) {
            tryLoadData1();
        }
    }

    /**
     * ViewPager场景下，判断父fragment是否可见
     *
     * @return
     */
    private boolean isParentVisible() {
        Fragment fragment = getParentFragment();
        return fragment == null || (fragment instanceof LazyLoadFragment && ((LazyLoadFragment) fragment).mIsVisibleToUser);
    }

    /**
     * ViewPager场景下，当前fragment可见，如果其子fragment也可见，则尝试让子fragment加载请求
     */
    private void dispatchParentVisibleState() {
        FragmentManager fragmentManager = getChildFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments.isEmpty()) {
            return;
        }
        for (Fragment child : fragments) {
            if (child instanceof LazyLoadFragment && ((LazyLoadFragment) child).mIsVisibleToUser) {
                ((LazyLoadFragment) child).tryLoadData();
            }
        }
    }

    /**
     * fragment再次可见时，是否重新请求数据，默认为flase则只请求一次数据
     *
     * @return
     */
    protected boolean isNeedReload() {
        return false;
    }

    /**
     * ViewPager场景下，尝试请求数据
     */
    public void tryLoadData() {
        if (mIsViewCreated && mIsVisibleToUser && isParentVisible() && (isNeedReload() || !mIsDataLoaded)) {
            loadData();
            mIsDataLoaded = true;
            dispatchParentVisibleState();
        }
    }

    /**
     * show()、hide()场景下，当前fragment没隐藏，如果其子fragment也没隐藏，则尝试让子fragment请求数据
     */
    private void dispatchParentHiddenState() {
        FragmentManager fragmentManager = getChildFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments.isEmpty()) {
            return;
        }
        for (Fragment child : fragments) {
            if (child instanceof LazyLoadFragment && !((LazyLoadFragment) child).mIsHidden) {
                ((LazyLoadFragment) child).tryLoadData1();
            }
        }
    }

    /**
     * show()、hide()场景下，父fragment是否隐藏
     *
     * @return
     */
    private boolean isParentHidden() {
        Fragment fragment = getParentFragment();
        if (fragment == null) {
            return false;
        } else if (fragment instanceof LazyLoadFragment && !((LazyLoadFragment) fragment).mIsHidden) {
            return false;
        }
        return true;
    }

    /**
     * show()、hide()场景下，尝试请求数据
     */
    public void tryLoadData1() {
        if (!isParentHidden() && (isNeedReload() || !mIsDataLoaded)) {
            loadData();
            mIsDataLoaded = true;
            dispatchParentHiddenState();
        }
    }

    @Override
    public void onDestroy() {
        mIsViewCreated = false;
        mIsVisibleToUser = false;
        mIsDataLoaded = false;
        mIsHidden = true;
        super.onDestroy();
    }
}
