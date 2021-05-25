package com.dayi.dy_rate.entity;

import java.util.List;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2021/5/11 15:03
 * 描    述: 类    条件实体（项目筛选过滤条件）
 * 修订历史:
 * =========================================
 */
public class ConditionsEntity {

    private List<ProjectListBean> projectList;
    private List<OsBean> os;
    private List<StatusBean> status;

    public List<ProjectListBean> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<ProjectListBean> projectList) {
        this.projectList = projectList;
    }

    public List<OsBean> getOs() {
        return os;
    }

    public void setOs(List<OsBean> os) {
        this.os = os;
    }

    public List<StatusBean> getStatus() {
        return status;
    }

    public void setStatus(List<StatusBean> status) {
        this.status = status;
    }

    public static class ProjectListBean {
        /**
         * label : 企客云
         * value : 0160a4d2a202e87f68
         */

        private String label;
        private String value;

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class OsBean {
        /**
         * value : 1
         * label : 安卓
         */

        private int value;
        private String label;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }
    }

    public static class StatusBean {
        /**
         * value : 1
         * label : 未开始
         */

        private int value;
        private String label;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }
    }
}
