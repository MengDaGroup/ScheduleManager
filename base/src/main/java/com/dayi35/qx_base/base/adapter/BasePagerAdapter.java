package com.dayi35.qx_base.base.adapter;

import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2020/6/15 8:47
 * 描    述: 类
 * 修订历史:
 * =========================================
 */
public class BasePagerAdapter extends FragmentPagerAdapter {
    private List<?> mFragment;
    private List<String> mTitleList;
    private List<String> mTagList;                  //存好所有界面的tags（用于更新）
    private FragmentManager mFm;

    /**
     * 普通，主页使用
     */
    public BasePagerAdapter(FragmentManager fm, List<?> mFragment) {
        super(fm);
        this.mFm = fm;
        this.mFragment = mFragment;
        mTagList = new ArrayList<>();
    }

    /**
     * 接收首页传递的标题
     */
    public BasePagerAdapter(FragmentManager fm, List<?> mFragment, List<String> mTitleList) {
        super(fm);
        this.mFragment = mFragment;
        this.mTitleList = mTitleList;
    }

    @Override
    public Fragment getItem(int position) {
        return (Fragment) mFragment.get(position);
    }

    @Override
    public int getCount() {
        return mFragment==null ? 0 : mFragment.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    /**
     * 若有问题，移到对应单独页面
     */
    @Override
    public CharSequence getPageTitle(int position) {
        if (mTitleList != null) {
            return mTitleList.get(position);
        } else {
            return "";
        }
    }

    public void addFragmentList(List<?> fragment) {
        this.mFragment.clear();
        this.mFragment = null;
        this.mFragment = fragment;
        notifyDataSetChanged();
    }

}
