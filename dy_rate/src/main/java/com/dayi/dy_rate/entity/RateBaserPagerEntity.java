package com.dayi.dy_rate.entity;

import java.util.List;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2021/5/11 14:35
 * 描    述: 类 基础列表实体类
 * 修订历史:
 * =========================================
 */
public class RateBaserPagerEntity<T> {
    private List<T> list;
    private BasePagerInfoEntity pager;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public BasePagerInfoEntity getPager() {
        return pager;
    }

    public void setPager(BasePagerInfoEntity pager) {
        this.pager = pager;
    }
}
