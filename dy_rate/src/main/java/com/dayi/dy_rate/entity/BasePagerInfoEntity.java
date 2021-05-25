package com.dayi.dy_rate.entity;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2021/5/11 14:40
 * 描    述: 类    pager 实体
 * 修订历史:
 * =========================================
 */
public class BasePagerInfoEntity {
    private int currentPage;    //当前页码
    private int pageSize;       //一次请求的总条数
    private int total;          //总共多少条
    private int totalPage;      //总共多少页

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
}
